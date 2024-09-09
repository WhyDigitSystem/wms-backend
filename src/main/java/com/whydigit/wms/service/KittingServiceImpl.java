package com.whydigit.wms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.KittingDetails1DTO;
import com.whydigit.wms.dto.KittingDetails2DTO;
import com.whydigit.wms.entity.DeKittingChildVO;
import com.whydigit.wms.entity.DeKittingParentVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.KittingDetails1VO;
import com.whydigit.wms.entity.KittingDetails2VO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.KittingDetails1Repo;
import com.whydigit.wms.repo.KittingDetails2Repo;
import com.whydigit.wms.repo.KittingRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class KittingServiceImpl implements KittingService {

	@Autowired
	KittingRepo kittingRepo;

	@Autowired
	KittingDetails1Repo kittingDetails1Repo;

	@Autowired
	KittingDetails2Repo kittingDetails2Repo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	MaterialRepo materialRepo;

	// Kitting

	@Override
	public Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException {
		KittingVO kittingVO = new KittingVO();
		String screenCode = "KT";
		String message;

		if (ObjectUtils.isNotEmpty(kittingDTO.getId())) {
			kittingVO = kittingRepo.findById(kittingDTO.getId())
					.orElseThrow(() -> new ApplicationException("Kitting not found"));

			kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());
			createUpdateKittingVOByKittingDTO(kittingDTO, kittingVO);
			message = "Kitting Updated Successfully";
		} else {

			kittingVO.setCreatedBy(kittingDTO.getCreatedBy());
			kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());

			String kittingDocId = kittingRepo.getKittingDocId(kittingDTO.getOrgId(), kittingDTO.getFinYear(),
					kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
			kittingVO.setDocId(kittingDocId);
			createUpdateKittingVOByKittingDTO(kittingDTO, kittingVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(kittingDTO.getOrgId(), kittingDTO.getFinYear(),
							kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "Kitting Created Successfully";
		}

		KittingVO savedKittingVO = kittingRepo.save(kittingVO);

		List<KittingDetails2VO> kittingDetails2VOLists = savedKittingVO.getKittingDetails2VO();
		if (kittingDetails2VOLists != null && !kittingDetails2VOLists.isEmpty()) {
			for (KittingDetails2VO detailsVO : kittingDetails2VOLists) {
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setOrgId(savedKittingVO.getOrgId());
				stockDetailsVOFrom.setFinYear(savedKittingVO.getFinYear());
				stockDetailsVOFrom.setBranch(savedKittingVO.getBranch());
				stockDetailsVOFrom.setBranchCode(savedKittingVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(savedKittingVO.getWarehouse());
				stockDetailsVOFrom.setCustomer(savedKittingVO.getCustomer());
				stockDetailsVOFrom.setClient(savedKittingVO.getClient());
				stockDetailsVOFrom
						.setClientCode(clientRepo.getClientCode(savedKittingVO.getOrgId(), savedKittingVO.getClient()));
				stockDetailsVOFrom.setCreatedBy(savedKittingVO.getUpdatedBy());
				stockDetailsVOFrom.setRefNo(savedKittingVO.getDocId());
				stockDetailsVOFrom.setRefDate(savedKittingVO.getDocDate());
				stockDetailsVOFrom.setUpdatedBy(savedKittingVO.getUpdatedBy());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedKittingVO.getOrgId(),
						savedKittingVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setSQty(detailsVO.getQty());
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
				stockDetailsVOFrom.setSourceScreenCode(savedKittingVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(savedKittingVO.getScreenName());
				stockDetailsVOFrom.setSourceId(detailsVO.getId());
				stockDetailsVOFrom.setStockDate(LocalDate.now());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}
			List<KittingDetails1VO> kittingDetails1VOLists1 = savedKittingVO.getKittingDetails1VO();
			for (KittingDetails1VO detailsVO : kittingDetails1VOLists1) {
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setOrgId(savedKittingVO.getOrgId());
				stockDetailsVOFrom.setFinYear(savedKittingVO.getFinYear());
				stockDetailsVOFrom.setBranch(savedKittingVO.getBranch());
				stockDetailsVOFrom.setBranchCode(savedKittingVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(savedKittingVO.getWarehouse());
				stockDetailsVOFrom.setCustomer(savedKittingVO.getCustomer());
				stockDetailsVOFrom.setClient(savedKittingVO.getClient());
				stockDetailsVOFrom
						.setClientCode(clientRepo.getClientCode(savedKittingVO.getOrgId(), savedKittingVO.getClient()));
				stockDetailsVOFrom.setCreatedBy(savedKittingVO.getUpdatedBy());
				stockDetailsVOFrom.setRefNo(savedKittingVO.getDocId());
				stockDetailsVOFrom.setRefDate(savedKittingVO.getDocDate());
				stockDetailsVOFrom.setUpdatedBy(savedKittingVO.getUpdatedBy());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedKittingVO.getOrgId(),
						savedKittingVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setSQty(detailsVO.getQty());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
//				if ("Defective".equals(detailsVO.getBin())) {
//					stockDetailsVOFrom.setQcFlag("F");
//					stockDetailsVOFrom.setStatus("D");
//				} else {
//					stockDetailsVOFrom.setQcFlag("T");
//					stockDetailsVOFrom.setStatus("R");
//				}
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setSSku(detailsVO.getSku());
				stockDetailsVOFrom.setSourceScreenCode(savedKittingVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(savedKittingVO.getScreenName());
				stockDetailsVOFrom.setSourceId(detailsVO.getId());
				stockDetailsVOFrom.setStockDate(LocalDate.now());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}

		}

		// Prepare response map
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("kittingVO", kittingVO);

		return response;
	}

	private void createUpdateKittingVOByKittingDTO(KittingDTO kittingDTO, KittingVO kittingVO)
			throws ApplicationException {
		// Populate kittingVO fields from kittingDTO
		// kittingVO.setScreenName(kittingDTO.getScreenName());
		kittingVO.setOrgId(kittingDTO.getOrgId());
		kittingVO.setCustomer(kittingDTO.getCustomer());
		kittingVO.setClient(kittingDTO.getClient());
		kittingVO.setFinYear(kittingDTO.getFinYear());
		kittingVO.setBranch(kittingDTO.getBranch());
		kittingVO.setBranchCode(kittingDTO.getBranchCode());
		kittingVO.setWarehouse(kittingDTO.getWarehouse());
		kittingVO.setRefNo(kittingDTO.getRefNo());

		if (ObjectUtils.isNotEmpty(kittingVO.getId())) {
			List<KittingDetails2VO> kittingParentVO1 = kittingDetails2Repo.findByKittingVO(kittingVO);
			kittingDetails2Repo.deleteAll(kittingParentVO1);
		}
		if (ObjectUtils.isNotEmpty(kittingVO.getId())) {
			List<KittingDetails1VO> kittingChildVO1 = kittingDetails1Repo.findByKittingVO(kittingVO);
			kittingDetails1Repo.deleteAll(kittingChildVO1);
		}

		// Handle KittingDetails1VO
		List<KittingDetails1VO> kittingDetails1VOs = new ArrayList<>();
		for (KittingDetails1DTO details1dto : kittingDTO.getKittingDetails1DTO()) {
			KittingDetails1VO kittingDetails1VO = new KittingDetails1VO();
			kittingDetails1VO.setPartNo(details1dto.getPartNo());
			kittingDetails1VO.setPartDesc(details1dto.getPartDesc());
			kittingDetails1VO.setSku(details1dto.getSku());
			kittingDetails1VO.setGrnNo(details1dto.getGrnNo());
			kittingDetails1VO.setGrnDate(details1dto.getGrnDate());
			kittingDetails1VO.setBatchNo(details1dto.getBatchNo());
			kittingDetails1VO.setBatchDate(details1dto.getBatchDate());
			kittingDetails1VO.setBinType(details1dto.getBinType());
			kittingDetails1VO.setBinClass(details1dto.getBinClass());
			kittingDetails1VO.setCellType(details1dto.getCellType());
			kittingDetails1VO.setCore(details1dto.getCore());
			kittingDetails1VO.setLotNo(details1dto.getLotNo());
			kittingDetails1VO.setAvlQty(details1dto.getAvlQty());
			kittingDetails1VO.setQty(details1dto.getQty());
			kittingDetails1VO.setUnitRate(details1dto.getUnitRate());
			kittingDetails1VO.setAmount(details1dto.getAmount());
			kittingDetails1VO.setExpDate(details1dto.getExpDate());

			// Avoid recursive reference to kittingVO in KittingDetails1VO
			kittingDetails1VO.setKittingVO(kittingVO);
			kittingDetails1VOs.add(kittingDetails1VO);
		}
		kittingVO.setKittingDetails1VO(kittingDetails1VOs);

		// Handle KittingDetails2VO
		List<KittingDetails2VO> kittingDetails2VOs = new ArrayList<>();
		for (KittingDetails2DTO details2dto : kittingDTO.getKittingDetails2DTO()) {
			KittingDetails2VO kittingDetails2VO = new KittingDetails2VO();
			kittingDetails2VO.setPartNo(details2dto.getPartNo());
			kittingDetails2VO.setPartDesc(details2dto.getPartDesc());
			kittingDetails2VO.setSku(details2dto.getSku());
			kittingDetails2VO.setGrnNo(details2dto.getGrnNo());
			kittingDetails2VO.setGrnDate(details2dto.getGrnDate());
			kittingDetails2VO.setBatchNo(details2dto.getBatchNo());
			kittingDetails2VO.setBatchDate(details2dto.getBatchDate());
			kittingDetails2VO.setBin(details2dto.getBin());
			kittingDetails2VO.setBinType(details2dto.getBinType());
			kittingDetails2VO.setBinClass(details2dto.getBinClass());
			kittingDetails2VO.setCellType(details2dto.getCellType());
			kittingDetails2VO.setCore(details2dto.getCore());
			kittingDetails2VO.setLotNo(details2dto.getLotNo());
			kittingDetails2VO.setQcflag(details2dto.isQcflag());
			kittingDetails2VO.setQty(details2dto.getQty() * -1);
			kittingDetails2VO.setExpDate(details2dto.getExpDate());

			// Avoid recursive reference to kittingVO in KittingDetails2VO
			kittingDetails2VO.setKittingVO(kittingVO);
			kittingDetails2VOs.add(kittingDetails2VO);
		}
		kittingVO.setKittingDetails2VO(kittingDetails2VOs);

	}

	@Override
	public List<KittingVO> getAllKitting(Long orgId, String branchCode, String client, String customer) {
		return kittingRepo.findAllKiting(orgId, branchCode, client, customer);
	}

	@Override
	public Optional<KittingVO> getKittingById(Long id) {
		return kittingRepo.findKittingById(id);
	}

	@Override
	public String getKittingInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "KT";
		String result = kittingRepo.getKittingDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartNOByChild(Long orgId, String branchCode, String client, String warehouse) {
		Set<Object[]> getPartNo = kittingRepo.getPartNOByChild(orgId, branchCode, client, warehouse);
		return gateChildPartNo(getPartNo);
	}

	private List<Map<String, Object>> gateChildPartNo(Set<Object[]> getPartNo) {
		List<Map<String, Object>> gridDetails = new ArrayList<>(); // Correct the type here
		for (Object[] child : getPartNo) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", child[0] != null ? child[0].toString() : "");
			details.put("partDesc", child[1] != null ? child[1].toString() : "");
			details.put("Sku", child[2] != null ? child[2].toString() : "");
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getGrnNOByChild(Long orgId, String branchCode, String client, String warehouse,
			String partNo) {
		Set<Object[]> getGrnData = kittingRepo.getGrnNOByChild(orgId, branchCode, client, warehouse, partNo);

		return processGrnData(getGrnData);
	}

	private List<Map<String, Object>> processGrnData(Set<Object[]> getGrnData) {
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		for (Object[] record : getGrnData) {
			Map<String, Object> details = new HashMap<>();
			details.put("grnNo", record[0] != null ? record[0].toString() : "");
			details.put("GrnDate", record[1] != null ? record[1].toString() : "");
			grnDetails.add(details);
		}
		return grnDetails;
	}

	@Override
	public List<Map<String, Object>> getBatchByChild(Long orgId, String branchCode, String client, String warehouse,
			String partNo, String grnNo) {
		Set<Object[]> getGrnData = kittingRepo.getBatch(orgId, branchCode, client, warehouse, partNo, grnNo);

		return getBatch1(getGrnData);
	}

	private List<Map<String, Object>> getBatch1(Set<Object[]> getGrnData) {
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		for (Object[] record : getGrnData) {
			Map<String, Object> details = new HashMap<>();
			details.put("batch", record[0] != null ? record[0].toString() : "");
			details.put("batchDate", record[1] != null ? record[1].toString() : "");
			details.put("expDate", record[2] != null ? record[2].toString() : "");
			details.put("id", record[3] != null ? Integer.parseInt(record[3].toString()) : 0);
			grnDetails.add(details);
		}
		return grnDetails;
	}

	@Override
	public List<Map<String, Object>> getBinByChild(Long orgId, String branchCode, String client, String warehouse,
			String partNo, String grnNo, String batch) {
		Set<Object[]> getGrnData = kittingRepo.getBin(orgId, branchCode, client, warehouse, partNo, grnNo);

		return getBin1(getGrnData);
	}

	private List<Map<String, Object>> getBin1(Set<Object[]> getGrnData) {
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		for (Object[] record : getGrnData) {
			Map<String, Object> details = new HashMap<>();
			details.put("bin", record[0] != null ? record[0].toString() : "");
			details.put("binType", record[1] != null ? record[1].toString() : "");
			details.put("lotNo", record[2] != null ? record[2].toString() : "");
			details.put("cellType", record[3] != null ? record[3].toString() : "");
			details.put("binClass", record[4] != null ? record[4].toString() : "");
			details.put("core", record[5] != null ? record[5].toString() : "");
			details.put("qcFlag", record[6] != null ? record[6].toString() : "");
			grnDetails.add(details);
		}
		return grnDetails;
	}

	@Override
	public Integer getSqtyByKitting(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String grnNo, String batch, String bin) {
		List<Integer> qtyList = stockDetailsRepo.getQtyDetails(orgId, branchCode, client, warehouse, partNo, grnNo,
				batch, bin);
		// Return the first result if list is not empty, or 0 otherwise
		return (!qtyList.isEmpty()) ? qtyList.get(0) : 0;
	}

	@Override
	public List<Map<String, Object>> getPartNOByParent(Long orgId, String branchCode, String client) {
		Set<Object[]> getPartNo = kittingRepo.getPartNOByParent(orgId, branchCode, client);
		return gateParentPartNo(getPartNo);
	}

	private List<Map<String, Object>> gateParentPartNo(Set<Object[]> getPartNo) {
		List<Map<String, Object>> gridDetails = new ArrayList<>(); // Correct the type here
		for (Object[] child : getPartNo) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", child[0] != null ? Integer.parseInt(child[0].toString()) : 0);
			details.put("partDesc", child[1] != null ? child[1].toString() : "");
			details.put("Sku", child[2] != null ? child[2].toString() : "");
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getGrnNOByParent(Long orgId, String bin, String branch, String branchCode,
			String client, String partNo, String partDesc, String sku) {
		Set<Object[]> getGrnData = kittingRepo.getGrnNOByParent(orgId, bin, branch, branchCode, client, partNo,
				partDesc, sku);

		return processParentGrnData(getGrnData);
	}

	private List<Map<String, Object>> processParentGrnData(Set<Object[]> getGrnData) {
		List<Map<String, Object>> grnDetails = new ArrayList<>();
		for (Object[] record : getGrnData) {
			Map<String, Object> details = new HashMap<>();
			details.put("grnnNo", record[0] != null ? record[0].toString() : "");
			details.put("GrnDate", record[1] != null ? record[1].toString() : "");
			details.put("batch", record[2] != null ? record[2].toString() : "");
			details.put("batchDate", record[3] != null ? record[3].toString() : "");
			grnDetails.add(details);
		}
		return grnDetails;
	}

}
