package com.whydigit.wms.service;

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
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.KittingDetails1VO;
import com.whydigit.wms.entity.KittingDetails2VO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.KittingDetails1Repo;
import com.whydigit.wms.repo.KittingDetails2Repo;
import com.whydigit.wms.repo.KittingRepo;
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

	// Kitting

	@Override
	public Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException {
		KittingVO kittingVO;
		String message;
		String screenCode = "KT";
		if (ObjectUtils.isEmpty(kittingDTO.getId())) {
			// Creating a new KittingVO
			kittingVO = new KittingVO();
			kittingVO.setCreatedBy(kittingDTO.getCreatedBy());
			kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());

			// GETDOCID API
			String docId = kittingRepo.getKittingInDocId(kittingDTO.getOrgId(), kittingDTO.getFinYear(),
					kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
			kittingVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(kittingDTO.getOrgId(),
							kittingDTO.getFinYear(), kittingDTO.getBranchCode(), kittingDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "Kitting Creation Successful";
		} else {
			// Updating an existing KittingVO
			kittingVO = kittingRepo.findById(kittingDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Not Found Any Information, Invalid Id: " + kittingDTO.getId()));
			kittingVO.setUpdatedBy(kittingDTO.getCreatedBy());
			message = "Kitting Update Successful";

			// Deleting existing details
			List<KittingDetails1VO> details1vos = kittingDetails1Repo.findByKittingVO(kittingVO);
			kittingDetails1Repo.deleteAll(details1vos);

			List<KittingDetails2VO> details2vos = kittingDetails2Repo.findByKittingVO(kittingVO);
			kittingDetails2Repo.deleteAll(details2vos);
		}

		// Populate KittingVO from KittingDTO
		getKittingVOFromKittingDTO(kittingVO, kittingDTO);

		// Save KittingVO to repository
		kittingRepo.save(kittingVO);

		// Prepare response map
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("kittingVO", kittingVO);

		return response;
	}

	private KittingVO getKittingVOFromKittingDTO(KittingVO kittingVO, KittingDTO kittingDTO) {
		// Populate kittingVO fields from kittingDTO
		kittingVO.setScreenName(kittingDTO.getScreenName());
		kittingVO.setOrgId(kittingDTO.getOrgId());
		kittingVO.setCustomer(kittingDTO.getCustomer());
		kittingVO.setClient(kittingDTO.getClient());
		kittingVO.setFinYear(kittingDTO.getFinYear());
		kittingVO.setBranch(kittingDTO.getBranch());
		kittingVO.setBranchCode(kittingDTO.getBranchCode());
		kittingVO.setWarehouse(kittingDTO.getWarehouse());
		kittingVO.setActive(kittingDTO.isActive());
		kittingVO.setCancel(kittingDTO.isCancel());
		kittingVO.setCancelRemarks(kittingDTO.getCancelRemarks());
		kittingVO.setFreeze(kittingDTO.isFreeze());
		kittingVO.setRefNo(kittingDTO.getRefNo());
		kittingVO.setSku(kittingDTO.getSku());

		// Handle KittingDetails1VO
		List<KittingDetails1VO> kittingDetails1VOs = new ArrayList<>();
		for (KittingDetails1DTO details1dto : kittingDTO.getKittingDetails1DTO()) {
			KittingDetails1VO kittingDetails1VO = new KittingDetails1VO();
			kittingDetails1VO.setPallet(details1dto.getPallet());
			kittingDetails1VO.setPartNo(details1dto.getPartNo());
			kittingDetails1VO.setPartDescription(details1dto.getPartDescription());
			kittingDetails1VO.setBatchNo(details1dto.getBatchNo());
			kittingDetails1VO.setLotNo(details1dto.getLotNo());
			kittingDetails1VO.setGrnNo(details1dto.getGrnNo());
			kittingDetails1VO.setGrnDate(details1dto.getGrnDate());
			kittingDetails1VO.setSku(details1dto.getSku());
			kittingDetails1VO.setAvlQty(details1dto.getAvlQty());
			kittingDetails1VO.setQty(details1dto.getQty());
			kittingDetails1VO.setUnitRate(details1dto.getUnitRate());
			kittingDetails1VO.setAmount(details1dto.getAmount());
			kittingDetails1VO.setQcflag(details1dto.isQcflag());

			// Avoid recursive reference to kittingVO in KittingDetails1VO
			kittingDetails1VO.setKittingVO(kittingVO);
			kittingDetails1VOs.add(kittingDetails1VO);
		}
		kittingVO.setKittingDetails1VO(kittingDetails1VOs);

		// Handle KittingDetails2VO
		List<KittingDetails2VO> kittingDetails2VOs = new ArrayList<>();
		for (KittingDetails2DTO details2dto : kittingDTO.getKittingDetails2DTO()) {
			KittingDetails2VO kittingDetails2VO = new KittingDetails2VO();
			kittingDetails2VO.setPPartDescription(details2dto.getPPartDescription());
			kittingDetails2VO.setPPartNo(details2dto.getPPartNo());
			kittingDetails2VO.setPBatchNo(details2dto.getPBatchNo());
			kittingDetails2VO.setPLotNo(details2dto.getPLotNo());
			kittingDetails2VO.setPGrnNo(details2dto.getPGrnNo());
			kittingDetails2VO.setPGrnDate(details2dto.getPGrnDate());
			kittingDetails2VO.setQQcflag(details2dto.isQQcflag());
			kittingDetails2VO.setPQty(details2dto.getPQty());
			kittingDetails2VO.setPExpDate(details2dto.getPExpDate());

			// Avoid recursive reference to kittingVO in KittingDetails2VO
			kittingDetails2VO.setKittingVO(kittingVO);
			kittingDetails2VOs.add(kittingDetails2VO);
		}
		kittingVO.setKittingDetails2VO(kittingDetails2VOs);

		return kittingVO;
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
		String result = kittingRepo.getKittingInDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getPartNOByChild(Long orgId, String bin, String branch, String branchCode,
			String client) {
		Set<Object[]> getPartNo = kittingRepo.getPartNOByChild(orgId, bin, branch, branchCode, client);
		return gateChildPartNo(getPartNo);
	}

	private List<Map<String, Object>> gateChildPartNo(Set<Object[]> getPartNo) {
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
	public List<Map<String, Object>> getGrnNOByChild(Long orgId, String bin, String branch, String branchCode,
			String client, String partNo, String partDesc, String sku) {
		Set<Object[]> getGrnData = kittingRepo.getGrnNOByChild(orgId, bin, branch, branchCode, client, partNo, partDesc,
				sku);

		return processGrnData(getGrnData);
	}

	private List<Map<String, Object>> processGrnData(Set<Object[]> getGrnData) {
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

	@Override
	public List<Map<String, Object>> getSqtyByKitting(Long orgId, String branchCode, String client, String partNo,
			String warehouse, String grnno) {
		Set<Object[]> getQty = stockDetailsRepo.getQtyDetais(orgId, branchCode, client, partNo, warehouse, grnno);
		return getQtys(getQty);
	}

	private List<Map<String, Object>> getQtys(Set<Object[]> getPartNo) {
		List<Map<String, Object>> gridDetails = new ArrayList<>(); // Correct the type here
		for (Object[] child : getPartNo) {
			Map<String, Object> details = new HashMap<>();
			details.put("sQTY", child[0] != null ? Integer.parseInt(child[0].toString()) : 0);
			gridDetails.add(details);
		}
		return gridDetails;
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
