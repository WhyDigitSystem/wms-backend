package com.whydigit.wms.service;

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
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DeKittingChildRepo;
import com.whydigit.wms.repo.DeKittingParentRepo;
import com.whydigit.wms.repo.DeKittingRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
@Service
public class DeKittingServiceImpl implements DeKittingService{
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
		DeKittingVO savedDeKittingVO = deKittingRepo.save(deKittingVO);

		List<DeKittingParentVO> deKittingParentVOLists = savedDeKittingVO.getDeKittingParentVO();
		if (deKittingParentVOLists != null && !deKittingParentVOLists.isEmpty()) {
			for (DeKittingParentVO parentDetailsVO : deKittingParentVOLists) {

				StockDetailsVO stockDetailsVOPar = new StockDetailsVO();
				stockDetailsVOPar.setBin(parentDetailsVO.getBin());
				stockDetailsVOPar.setPartno(parentDetailsVO.getPartNo());
				stockDetailsVOPar.setPartDesc(parentDetailsVO.getPartDesc());
				stockDetailsVOPar.setBatch(parentDetailsVO.getBatchNo());
				stockDetailsVOPar.setLotNo(parentDetailsVO.getLotNo());
				stockDetailsVOPar.setSku(parentDetailsVO.getSku());
				stockDetailsVOPar.setGrnNo(parentDetailsVO.getGrnNo());
				stockDetailsVOPar.setGrnDate(parentDetailsVO.getGrnDate());
				stockDetailsVOPar.setExpDate(parentDetailsVO.getExpDate());
				stockDetailsVOPar.setStatus(parentDetailsVO.getStatus());

				stockDetailsVOPar.setBinClass(parentDetailsVO.getBinClass());
				stockDetailsVOPar.setCellType(parentDetailsVO.getCellType());
				stockDetailsVOPar.setClientCode(parentDetailsVO.getClientCode());
				stockDetailsVOPar.setCore(parentDetailsVO.getCore());
				stockDetailsVOPar.setPcKey(parentDetailsVO.getPcKey());
				stockDetailsVOPar.setSSku(parentDetailsVO.getSsku());
				stockDetailsVOPar.setStockDate(parentDetailsVO.getStockDate());
				stockDetailsVOPar.setStockDate(parentDetailsVO.getStockDate());

				stockDetailsVOPar.setSQty(parentDetailsVO.getQty() * -1);
				stockDetailsVOPar.setStatus(parentDetailsVO.getStatus());
//				dekitting->stock
				stockDetailsVOPar.setRefNo(savedDeKittingVO.getDocId());
				stockDetailsVOPar.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOPar.setOrgId(savedDeKittingVO.getOrgId());
				stockDetailsVOPar.setCustomer(savedDeKittingVO.getCustomer());
				stockDetailsVOPar.setClient(savedDeKittingVO.getClient());
				stockDetailsVOPar.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOPar.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOPar.setCreatedBy(savedDeKittingVO.getUpdatedBy());
				stockDetailsVOPar.setBranchCode(savedDeKittingVO.getBranchCode());
				stockDetailsVOPar.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOPar.setClient(savedDeKittingVO.getClient());
				stockDetailsVOPar.setWarehouse(savedDeKittingVO.getWarehouse());
				stockDetailsVOPar.setFinYear(savedDeKittingVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOPar);
			}
		}

		List<DeKittingChildVO> deKittingChildVOLists = savedDeKittingVO.getDeKittingChildVO();
		if (deKittingChildVOLists != null && !deKittingChildVOLists.isEmpty()) {
			for (DeKittingChildVO childDetailsVO : deKittingChildVOLists) {
				// child -> stock
				StockDetailsVO stockDetailsVOChi = new StockDetailsVO();
				stockDetailsVOChi.setBin(childDetailsVO.getBin());
				stockDetailsVOChi.setPartno(childDetailsVO.getPartNo());
				stockDetailsVOChi.setPartDesc(childDetailsVO.getPartDesc());
				stockDetailsVOChi.setBatch(childDetailsVO.getBatchNo());
				stockDetailsVOChi.setLotNo(childDetailsVO.getLotNo());
				stockDetailsVOChi.setSku(childDetailsVO.getSku());
				stockDetailsVOChi.setGrnNo(childDetailsVO.getGrnNo());
				stockDetailsVOChi.setGrnDate(childDetailsVO.getGrnDate());
				stockDetailsVOChi.setExpDate(childDetailsVO.getExpDate());
				stockDetailsVOChi.setStatus(childDetailsVO.getStatus());
				stockDetailsVOChi.setSQty(childDetailsVO.getQty());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setBin(childDetailsVO.getBin());
				stockDetailsVOChi.setStatus(childDetailsVO.getStatus());

				stockDetailsVOChi.setBinClass(childDetailsVO.getBinClass());
				stockDetailsVOChi.setCellType(childDetailsVO.getCellType());
				stockDetailsVOChi.setClientCode(childDetailsVO.getClientCode());
				stockDetailsVOChi.setCore(childDetailsVO.getCore());
				stockDetailsVOChi.setPcKey(childDetailsVO.getPcKey());
				stockDetailsVOChi.setSSku(childDetailsVO.getSsku());
				stockDetailsVOChi.setStockDate(childDetailsVO.getStockDate());
				stockDetailsVOChi.setStockDate(childDetailsVO.getStockDate());

//				dekitting->stock
				stockDetailsVOChi.setRefNo(savedDeKittingVO.getDocId());
				stockDetailsVOChi.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOChi.setOrgId(savedDeKittingVO.getOrgId());
				stockDetailsVOChi.setCustomer(savedDeKittingVO.getCustomer());
				stockDetailsVOChi.setClient(savedDeKittingVO.getClient());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOChi.setCreatedBy(savedDeKittingVO.getUpdatedBy());
				stockDetailsVOChi.setBranchCode(savedDeKittingVO.getBranchCode());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setClient(savedDeKittingVO.getClient());
				stockDetailsVOChi.setWarehouse(savedDeKittingVO.getWarehouse());
				stockDetailsVOChi.setFinYear(savedDeKittingVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOChi);
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("deKittingVO", deKittingVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDeKittingVOByDeKittingDTO(DeKittingDTO deKittingDTO, DeKittingVO deKittingVO) {

		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setTransactionType(deKittingDTO.getTransactionType());
		deKittingVO.setClient(deKittingDTO.getClient());
		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setCustomer(deKittingDTO.getCustomer());
		deKittingVO.setFinYear(deKittingDTO.getFinYear());
		deKittingVO.setBranch(deKittingDTO.getBranch());
		deKittingVO.setBranchCode(deKittingDTO.getBranchCode());
		deKittingVO.setWarehouse(deKittingDTO.getWarehouse());
		deKittingVO.setFreeze(deKittingDTO.getFreeze());
		deKittingVO.setActive(deKittingDTO.isActive());

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
			deKittingParentVO.setBin(deKittingParentDTO.getBin());
			deKittingParentVO.setPartNo(deKittingParentDTO.getPartNo());
			deKittingParentVO.setPartDesc(deKittingParentDTO.getPartDesc());
			deKittingParentVO.setGrnNo(deKittingParentDTO.getGrnNo());
			deKittingParentVO.setBatchNo(deKittingParentDTO.getBatchNo());
			deKittingParentVO.setLotNo(deKittingParentDTO.getLotNo());
			deKittingParentVO.setSku(deKittingParentDTO.getSku());
			deKittingParentVO.setGrnDate(deKittingParentDTO.getGrnDate());
			deKittingParentVO.setExpDate(deKittingParentDTO.getExpDate());
			deKittingParentVO.setAvlQty(deKittingParentDTO.getAvlQty());
			deKittingParentVO.setQty(deKittingParentDTO.getQty());
			deKittingParentVO.setUnitRate(deKittingParentDTO.getUnitRate());
			deKittingParentVO.setStatus(deKittingParentDTO.getStatus());
			deKittingParentVO.setAmount(deKittingParentDTO.getAmount());
			deKittingParentVO.setQcFlag(deKittingParentDTO.isQcFlag());

			deKittingParentVO.setBinClass(deKittingParentDTO.getBinClass());
			deKittingParentVO.setCellType(deKittingParentDTO.getCellType());
			deKittingParentVO.setClientCode(deKittingParentDTO.getClientCode());
			deKittingParentVO.setCore(deKittingParentDTO.getCore());
			deKittingParentVO.setPcKey(deKittingParentDTO.getPcKey());
			deKittingParentVO.setSsku(deKittingParentDTO.getSsku());
			deKittingParentVO.setStockDate(deKittingParentDTO.getStockDate());
			deKittingParentVO.setStockDate(deKittingParentDTO.getStockDate());

			deKittingParentVO.setDeKittingVO(deKittingVO);

			deKittingParentVOs.add(deKittingParentVO);
		}
		deKittingVO.setDeKittingParentVO(deKittingParentVOs);

		List<DeKittingChildVO> deKittingChildVOs = new ArrayList<>();
		for (DeKittingChildDTO deKittingChildDTO : deKittingDTO.getDeKittingChildDTO()) {

			DeKittingChildVO deKittingChildVO = new DeKittingChildVO();
			deKittingChildVO.setBin(deKittingChildDTO.getBin());
			deKittingChildVO.setPartNo(deKittingChildDTO.getPartNo());
			deKittingChildVO.setPartDesc(deKittingChildDTO.getPartDesc());
			deKittingChildVO.setGrnNo(deKittingChildDTO.getGrnNo());
			deKittingChildVO.setBatchNo(deKittingChildDTO.getBatchNo());
			deKittingChildVO.setLotNo(deKittingChildDTO.getLotNo());
			deKittingChildVO.setSku(deKittingChildDTO.getSku());
			deKittingChildVO.setGrnDate(deKittingChildDTO.getGrnDate());
			deKittingChildVO.setExpDate(deKittingChildDTO.getExpDate());
			deKittingChildVO.setQty(deKittingChildDTO.getQty());
			deKittingChildVO.setUnitRate(deKittingChildDTO.getUnitRate());
			deKittingChildVO.setStatus(deKittingChildDTO.getStatus());
			deKittingChildVO.setAmount(deKittingChildDTO.getAmount());
			deKittingChildVO.setQcFlag(deKittingChildDTO.isQcFlag());
			deKittingChildVO.setDeKittingVO(deKittingVO);

			deKittingChildVO.setBinClass(deKittingChildDTO.getBinClass());
			deKittingChildVO.setCellType(deKittingChildDTO.getCellType());
			deKittingChildVO.setClientCode(deKittingChildDTO.getClientCode());
			deKittingChildVO.setCore(deKittingChildDTO.getCore());
			deKittingChildVO.setPcKey(deKittingChildDTO.getPcKey());
			deKittingChildVO.setSsku(deKittingChildDTO.getSsku());
			deKittingChildVO.setStockDate(deKittingChildDTO.getStockDate());
			deKittingChildVO.setStockDate(deKittingChildDTO.getStockDate());

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
	public List<Map<String, Object>> getPartNoFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoFromStockForDeKittingParent(orgId, branch, branchCode,
				client);
		return getPartNoResult(result);
	}

	private List<Map<String, Object>> getPartNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}


	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findBinFromStockForDeKittingParent(orgId, branch, branchCode,
				client);
		return getDBinResult(result);
	}

	private List<Map<String, Object>> getDBinResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binclass", fs[1] != null ? fs[1].toString() : "");
			part.put("celltype", fs[2] != null ? fs[2].toString() : "");
			part.put("clientcode", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("expdate", fs[5] != null ? fs[5].toString() : "");
			part.put("pckey", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockdate", fs[8] != null ? fs[8].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(
			Long orgId, String branch, String branchCode, String client, String bin, String partNo,
			String partDesc, String sku) {

		Set<Object[]> result = deKittingRepo.findGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(
				orgId, branch, branchCode, client, bin, partNo, partDesc, sku);
		return getGrnNoResult(result);
	}

	private List<Map<String, Object>> getGrnNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchNo", fs[1] != null ? fs[1].toString() : "");
			part.put("batchDate", fs[2] != null ? fs[2].toString() : "");
			part.put("lotNo", fs[3] != null ? fs[3].toString() : "");
			part.put("expDate", fs[4] != null ? fs[4].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public int getAvlQtyFromStockForDeKittingParent(Long orgId, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo) {

		Set<Object[]> result = deKittingRepo.findAvlQtyFromStockForDeKittingParent(orgId, branch, branchCode,
				client, bin, partDesc, sku, partNo, grnNo, lotNo);
		return getAvlQtyResult(result);
	}

	private int getAvlQtyResult(Set<Object[]> result) {
		int totalQty = 0;
		for (Object[] qt : result) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}

	// CHILD
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(orgId, branch,
				branchCode, client);
		return getChildResult(result);
	}

	private List<Map<String, Object>> getChildResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("sku", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}
}
