package com.whydigit.wms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.DeKittingChildDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.DeKittingParentDTO;
import com.whydigit.wms.entity.DeKittingChildVO;
import com.whydigit.wms.entity.DeKittingParentVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DeKittingChildRepo;
import com.whydigit.wms.repo.DeKittingParentRepo;
import com.whydigit.wms.repo.DeKittingRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class DeKittingServiceImpl implements DeKittingService {
	public static final Logger LOGGER = LoggerFactory.getLogger(DeKittingServiceImpl.class);

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	DeKittingRepo deKittingRepo;

	@Autowired
	DeKittingChildRepo deKittingChildRepo;

	@Autowired
	DeKittingParentRepo deKittingParentRepo;
	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	ClientRepo clientRepo;

	@Override
	public List<DeKittingVO> getAllDeKitting(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return deKittingRepo.findAllDeKitting(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public DeKittingVO getDeKittingById(Long id) {
		DeKittingVO deKittingVO = new DeKittingVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received DeKittingBY Id : {}", id);
			deKittingVO = deKittingRepo.findDeKittingById(id);
		} else {
			LOGGER.info("failed Received DeKitting For All Id.");
		}
		return deKittingVO;

	}

	@Override
	public Map<String, Object> createUpdateDeKitting(DeKittingDTO deKittingDTO) throws ApplicationException {

		DeKittingVO deKittingVO = new DeKittingVO();
		String screenCode = "DK";
		String message;

		if (ObjectUtils.isNotEmpty(deKittingDTO.getId())) {
			deKittingVO = deKittingRepo.findById(deKittingDTO.getId())
					.orElseThrow(() -> new ApplicationException("DeKitting not found"));

			deKittingVO.setUpdatedBy(deKittingDTO.getCreatedBy());
			createUpdateDeKittingVOByDeKittingDTO(deKittingDTO, deKittingVO);
			message = "DeKitting Updated Successfully";
		} else {
			deKittingVO.setCreatedBy(deKittingDTO.getCreatedBy());
			deKittingVO.setUpdatedBy(deKittingDTO.getCreatedBy());

			String deKittingDocId = deKittingRepo.getDeKittingDocId(deKittingDTO.getOrgId(), deKittingDTO.getFinYear(),
					deKittingDTO.getBranchCode(), deKittingDTO.getClient(), screenCode);
			deKittingVO.setDocId(deKittingDocId);
			createUpdateDeKittingVOByDeKittingDTO(deKittingDTO, deKittingVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(deKittingDTO.getOrgId(), deKittingDTO.getFinYear(),
							deKittingDTO.getBranchCode(), deKittingDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "DeKitting Created Successfully";
		}
		DeKittingVO savedPickRequestVO = deKittingRepo.save(deKittingVO);

		List<DeKittingParentVO> pickRequestDetailsVOLists = savedPickRequestVO.getDeKittingParentVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			for (DeKittingParentVO detailsVO : pickRequestDetailsVOLists) {
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
				stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
				stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
				stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
				stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setClientCode(
						clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
				stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
				stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
				stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(),
						savedPickRequestVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setSQty(detailsVO.getQty() * -1);
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setStatus("R");
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBin(detailsVO.getBin());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOFrom.setPQty(detailsVO.getQty());
				stockDetailsVOFrom.setPickedQty(detailsVO.getQty());
				stockDetailsVOFrom.setQcFlag("T");
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setSSku(detailsVO.getSku());
				stockDetailsVOFrom.setSourceScreenCode(savedPickRequestVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(savedPickRequestVO.getScreenName());
				stockDetailsVOFrom.setSourceId(detailsVO.getId());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}
			List<DeKittingChildVO> pickRequestDetailsVOLists1 = savedPickRequestVO.getDeKittingChildVO();
			for (DeKittingChildVO detailsVO : pickRequestDetailsVOLists1) {
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
				stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
				stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
				stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
				stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setClientCode(
						clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
				stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
				stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
				stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(),
						savedPickRequestVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setSQty(detailsVO.getQty());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setBin(detailsVO.getBin());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				if ("DEFECTIVE".equals(detailsVO.getBin())) {
					stockDetailsVOFrom.setQcFlag("F");
					stockDetailsVOFrom.setStatus("D");
				} else {
					stockDetailsVOFrom.setQcFlag("T");
					stockDetailsVOFrom.setStatus("R");
				}
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setSSku(detailsVO.getSku());
				stockDetailsVOFrom.setSourceScreenCode(savedPickRequestVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(savedPickRequestVO.getScreenName());
				stockDetailsVOFrom.setSourceId(detailsVO.getId());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}

		}
		Map<String, Object> response = new HashMap<>();
		response.put("deKittingVO", deKittingVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDeKittingVOByDeKittingDTO(DeKittingDTO deKittingDTO, DeKittingVO deKittingVO)
			throws ApplicationException {

		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setClient(deKittingDTO.getClient());
		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setCustomer(deKittingDTO.getCustomer());
		deKittingVO.setFinYear(deKittingDTO.getFinYear());
		deKittingVO.setBranch(deKittingDTO.getBranch());
		deKittingVO.setBranchCode(deKittingDTO.getBranchCode());
		deKittingVO.setWarehouse(deKittingDTO.getWarehouse());
		if (ObjectUtils.isNotEmpty(deKittingVO.getId())) {
			List<DeKittingParentVO> deKittingParentVO1 = deKittingParentRepo.findByDeKittingVO(deKittingVO);
			deKittingParentRepo.deleteAll(deKittingParentVO1);
		}
		if (ObjectUtils.isNotEmpty(deKittingVO.getId())) {
			List<DeKittingChildVO> deKittingChildVO1 = deKittingChildRepo.findByDeKittingVO(deKittingVO);
			deKittingChildRepo.deleteAll(deKittingChildVO1);
		}

		List<DeKittingParentVO> deKittingParentVOs = new ArrayList<>();
		for (DeKittingParentDTO deKittingParentDTO : deKittingDTO.getDeKittingParentDTO()) {

			DeKittingParentVO deKittingParentVO = new DeKittingParentVO();

			deKittingParentVO.setPartNo(deKittingParentDTO.getPartNo());
			deKittingParentVO.setPartDesc(deKittingParentDTO.getPartDesc());
			deKittingParentVO.setSku(deKittingParentDTO.getSku());
			deKittingParentVO.setGrnNo(deKittingParentDTO.getGrnNo());
			deKittingParentVO.setGrnDate(deKittingParentDTO.getGrnDate());
			deKittingParentVO.setBatchNo(deKittingParentDTO.getBatchNo());
			deKittingParentVO.setBatchDate(deKittingParentDTO.getBatchDate());
			deKittingParentVO.setExpDate(deKittingParentDTO.getExpDate());
			deKittingParentVO.setBin(deKittingParentDTO.getBin());
			deKittingParentVO.setBinClass(deKittingParentDTO.getBinClass());
			deKittingParentVO.setCellType(deKittingParentDTO.getCellType());
			deKittingParentVO.setCore(deKittingParentDTO.getCore());
			deKittingParentVO.setBinType(deKittingParentDTO.getBinType());

			int avlqty = deKittingRepo.findAvlQtyFromStockForDeKittingParent(deKittingDTO.getOrgId(),
					deKittingDTO.getBranch(), deKittingDTO.getBranchCode(), deKittingDTO.getClient(),
					deKittingParentDTO.getPartNo(), deKittingParentDTO.getGrnNo(), deKittingParentDTO.getBatchNo(),
					deKittingParentDTO.getBin());

			if (avlqty >= deKittingParentDTO.getQty()) {
				deKittingParentVO.setAvlQty(deKittingParentDTO.getAvlQty());
				deKittingParentVO.setQty(deKittingParentDTO.getQty());
			} else {
				throw new ApplicationException("Qty should not More then Avl Qty");
			}
			deKittingParentVO.setQcFlag("T");
			deKittingParentVO.setDeKittingVO(deKittingVO);
			deKittingParentVOs.add(deKittingParentVO);
		}
		deKittingVO.setDeKittingParentVO(deKittingParentVOs);

		List<DeKittingChildVO> deKittingChildVOs = new ArrayList<>();
		for (DeKittingChildDTO deKittingChildDTO : deKittingDTO.getDeKittingChildDTO()) {

			DeKittingChildVO deKittingChildVO = new DeKittingChildVO();
			deKittingChildVO.setBin(deKittingChildDTO.getBin());
			deKittingChildVO.setBinType(deKittingChildDTO.getBinType());
			deKittingChildVO.setPartNo(deKittingChildDTO.getPartNo());
			deKittingChildVO.setPartDesc(deKittingChildDTO.getPartDesc());
			deKittingChildVO.setSku(deKittingChildDTO.getSku());
			deKittingChildVO.setGrnNo(deKittingChildDTO.getGrnNo());
			deKittingChildVO.setGrnDate(deKittingChildDTO.getGrnDate());
			deKittingChildVO.setBatchNo(deKittingChildDTO.getBatchNo());
			deKittingChildVO.setBatchDate(deKittingChildDTO.getBatchDate());
			deKittingChildVO.setExpDate(deKittingChildDTO.getExpDate());
			deKittingChildVO.setQty(deKittingChildDTO.getQty());

			if ("DEFECTIVE".equals(deKittingChildDTO.getBin())) {
				deKittingChildVO.setQcFlag("F");
				deKittingChildVO.setStatus("D");
			} else {
				deKittingChildVO.setQcFlag("T");
				deKittingChildVO.setStatus("R");
			}
			deKittingChildVO.setDeKittingVO(deKittingVO);
			deKittingChildVO.setBinClass(deKittingChildDTO.getBinClass());
			deKittingChildVO.setCellType(deKittingChildDTO.getCellType());
			deKittingChildVO.setCore(deKittingChildDTO.getCore());
			deKittingChildVO.setStockDate(LocalDate.now());

			deKittingChildVOs.add(deKittingChildVO);
		}
		deKittingVO.setDeKittingChildVO(deKittingChildVOs);
	}

	@Override
	@Transactional
	public String getDeKittingDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "DK";
		String result = deKittingRepo.getDeKittingDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	// PARENT
	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoFromStockForDeKittingParent(Long orgId, String branch, String branchCode,
			String client) {

		Set<Object[]> result = deKittingRepo.findPartNoFromStockForDeKittingParent(orgId, branch, branchCode, client);
		return getPartNoResult(result);
	}

	private List<Map<String, Object>> getPartNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getGrnNoFromStockForDeKittingParent(Long orgId, String branch, String branchCode,
			String client, String partNo) {
		Set<Object[]> result = deKittingRepo.findGrnNoFromStockForDeKittingParent(orgId, branch, branchCode, client,
				partNo);
		return getGrnNoResult(result);
	}

	private List<Map<String, Object>> getGrnNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getBatchAndBatchFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client, String partNo, String grnNo) {
		Set<Object[]> resultBatch = deKittingRepo.findBatchDetails(orgId, branch, branchCode, client, partNo, grnNo);
		return batchNoResult(resultBatch);
	}

	private List<Map<String, Object>> batchNoResult(Set<Object[]> resultBatch) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : resultBatch) {
			Map<String, Object> part = new HashMap<>();
			part.put("batchNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchDate", fs[1] != null ? fs[1].toString() : "");
			part.put("expDate", fs[2] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String branch, String branchCode,
			String client, String partno, String grnno, String batchNo) {

		Set<Object[]> result = deKittingRepo.findBinFromStockForDeKittingParent(orgId, branch, branchCode, client,
				partno, grnno, batchNo);
		return getDBinResult(result);
	}

	private List<Map<String, Object>> getDBinResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binclass", fs[1] != null ? fs[1].toString() : "");
			part.put("celltype", fs[2] != null ? fs[2].toString() : "");
			part.put("core", fs[3] != null ? fs[3].toString() : "");
			part.put("binType", fs[4] != null ? fs[4].toString() : "");
			part.put("qcFlag", fs[5] != null ? fs[5].toString() : "");
			part.put("status", "R");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public Integer getAvlQtyFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client,
			String partno, String grnno, String batchNo, String bin) {

		Integer qty = deKittingRepo.findAvlQtyFromStockForDeKittingParent(orgId, branch, branchCode, client, partno,
				grnno, batchNo, bin);
		return (qty != null) ? qty : 0;

	}

	// CHILD
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(orgId, branchCode,
				client);
		return getChildResult(result);
	}

	private List<Map<String, Object>> getChildResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;
	}

}
