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

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.CycleCountDetailsDTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.dto.VasPickDetailsDTO;
import com.whydigit.wms.entity.CycleCountDetailsVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CycleCountDetailsRepo;
import com.whydigit.wms.repo.CycleCountRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.VasPickDetailsRepo;
import com.whydigit.wms.repo.VasPickRepo;

@Service
public class VasanthServiceImpl implements VasanthService {

	@Autowired
	VasPickRepo vasPickRepo;

	@Autowired
	VasPickDetailsRepo vasPickDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	CycleCountRepo cycleCountRepo;

	@Autowired
	CycleCountDetailsRepo cycleCountDetailsRepo;

	@Override
	public Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException {
		VasPickVO vasPickVO;
		String message = null;
		String screenCode = "VP";
		if (ObjectUtils.isEmpty(vasPicDTO.getId())) {
			vasPickVO = new VasPickVO();
			vasPickVO.setCreatedBy(vasPicDTO.getCreatedBy());
			vasPickVO.setUpdatedBy(vasPicDTO.getCreatedBy());

			// GETDOCID API
			String docId = vasPickRepo.getVasPickDocId(vasPicDTO.getOrgId(), vasPicDTO.getFinYear(),
					vasPicDTO.getBranchCode(), vasPicDTO.getClient(), screenCode);
			vasPickVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(vasPicDTO.getOrgId(),
							vasPicDTO.getFinYear(), vasPicDTO.getBranchCode(), vasPicDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "VasPicK Creation Successfully";
		} else {
			vasPickVO = vasPickRepo.findById(vasPicDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Found Any Information, Invalid Id: " + vasPicDTO.getId()));

			vasPickVO.setUpdatedBy(vasPicDTO.getCreatedBy());
			message = "VasPicK Update Successfully";

			// Remove existing details if updating
			List<VasPickDetailsVO> detailsVOs = vasPickDetailsRepo.findByVasPickVO(vasPickVO);
			vasPickDetailsRepo.deleteAll(detailsVOs);
		}

		vasPickVO = getVasPickVOFromVasPickDTO(vasPickVO, vasPicDTO);

		// Save parent entity along with its details
		vasPickRepo.save(vasPickVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("vasPickVO", vasPickVO);
		return response;
	}

	private VasPickVO getVasPickVOFromVasPickDTO(VasPickVO vasPickVO, VasPickDTO vasPicDTO) {
		vasPickVO.setPicBin(vasPicDTO.getPicBin());
		vasPickVO.setDocDate(vasPicDTO.getDocDate());
		vasPickVO.setOrgId(vasPicDTO.getOrgId());

		vasPickVO.setCustomer(vasPicDTO.getCustomer());
		vasPickVO.setClient(vasPicDTO.getClient());
		vasPickVO.setFinYear(vasPicDTO.getFinYear());
		vasPickVO.setBranch(vasPicDTO.getBranch());
		vasPickVO.setBranchCode(vasPicDTO.getBranchCode());
		vasPickVO.setWarehouse(vasPicDTO.getWarehouse());
		vasPickVO.setCancelRemarks(vasPicDTO.getCancelRemarks());
		vasPickVO.setActive(vasPicDTO.isActive());
		vasPickVO.setCancel(vasPicDTO.isCancel());
		vasPickVO.setPicBin(vasPicDTO.getPicBin());
		vasPickVO.setFreeze(vasPicDTO.isFreeze());

		int totalOrderQty = 0;
		int pickedQty = 0;

		List<VasPickDetailsVO> vasPickDetailsVOs = new ArrayList<>();
		for (VasPickDetailsDTO vasPickDTO : vasPicDTO.getVasPickDetailsDTO()) {
			VasPickDetailsVO detailsVO = new VasPickDetailsVO();
			detailsVO.setPartCode(vasPickDTO.getPartCode());
			detailsVO.setPartDescription(vasPickDTO.getPartDescription());
			detailsVO.setPartNo(vasPickDTO.getPartNo());
			detailsVO.setSku(vasPickDTO.getSku());
			detailsVO.setBin(vasPickDTO.getBin());
			detailsVO.setBatchNo(vasPickDTO.getBatchNo());
			detailsVO.setLotNo(vasPickDTO.getLotNo());
			detailsVO.setGrnNo(vasPickDTO.getGrnNo());
			detailsVO.setAvlQty(vasPickDTO.getAvlQty());
			detailsVO.setPicQty(vasPickDTO.getPicQty());
			detailsVO.setRemaningQty(vasPickDTO.getRemaningQty());
			detailsVO.setManufactureDate(vasPickDTO.getManufactureDate());
			detailsVO.setQcflag(vasPickDTO.isQcflag());

			totalOrderQty = totalOrderQty + vasPickDTO.getAvlQty();
			pickedQty = pickedQty + vasPickDTO.getPicQty();

			detailsVO.setVasPickVO(vasPickVO); // Set the parent reference
			vasPickDetailsVOs.add(detailsVO);
		}
		vasPickVO.setVasPickDetailsVO(vasPickDetailsVOs);
		vasPickVO.setTotalOrderQty(totalOrderQty);
		vasPickVO.setPickedQty(pickedQty);
		return vasPickVO;
	}

	@Override
	public Optional<VasPickVO> getVaspickById(Long id) {
		return vasPickRepo.findVasPickById(id);
	}

	@Override
	public String getVasPickDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "VP";
		String result = vasPickRepo.getVasPickDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<VasPickVO> getAllVaspick(Long orgId, String branchCode, String client, String branch, String finYear,
			String warehouse) {
		return vasPickRepo.AllVaspick(orgId, branchCode, client, branch, finYear, warehouse);
	}

	// CYCLECOUNT

	@Override

	public Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException {
		CycleCountVO cycleCountVO;
		String screenCode = "CT";
		String message;

		if (ObjectUtils.isEmpty(cycleCountDTO.getId())) {

			cycleCountVO = new CycleCountVO();
			cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());

			// GETDOCID API
			String docId = cycleCountRepo.getCycleCountInDocId(cycleCountDTO.getOrgId(), cycleCountDTO.getFinYear(),
					cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), screenCode);
			cycleCountVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(cycleCountDTO.getOrgId(),
							cycleCountDTO.getFinYear(), cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "CycleCountDTO Creation Successfully";
		} else {
			cycleCountVO = cycleCountRepo.findById(cycleCountDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Fount Any Information,Invalid Id ." + cycleCountDTO.getId()));
			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());

			List<CycleCountDetailsVO> countDetailsVOs = cycleCountDetailsRepo.findByCycleCountVO(cycleCountVO);
			cycleCountDetailsRepo.deleteAll(countDetailsVOs);
			message = "CycleCountDTO Updation Successfully";
		}
		getCycleCountVOFromCycleCountDTO(cycleCountVO, cycleCountDTO);
		cycleCountRepo.save(cycleCountVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("cycleCountVO", cycleCountVO);
		return response;
	}

	private CycleCountVO getCycleCountVOFromCycleCountDTO(CycleCountVO cycleCountVO, CycleCountDTO cycleCountDTO) {
		cycleCountVO.setDocDate(cycleCountDTO.getDocDate());
		cycleCountVO.setOrgId(cycleCountDTO.getOrgId());
		cycleCountVO.setCustomer(cycleCountDTO.getCustomer());
		cycleCountVO.setClient(cycleCountDTO.getClient());
		cycleCountVO.setFinYear(cycleCountDTO.getFinYear());
		cycleCountVO.setBranch(cycleCountDTO.getBranch());
		cycleCountVO.setBranchCode(cycleCountDTO.getBranchCode());
		cycleCountVO.setWarehouse(cycleCountDTO.getWarehouse());
		cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
		cycleCountVO.setCancelRemarks(cycleCountDTO.getCancelRemarks());
		cycleCountVO.setFreeze(cycleCountDTO.isFreeze());
		cycleCountVO.setCycleCountNo(cycleCountDTO.getCycleCountNo());
		cycleCountVO.setCycleCountDate(cycleCountDTO.getCycleCountDate());

		List<CycleCountDetailsVO> cycleCountDetailsVOs = new ArrayList<>();
		for (CycleCountDetailsDTO details2dto : cycleCountDTO.getCycleCountDetailsDTO()) {
			CycleCountDetailsVO cycleCountDetailsVO = new CycleCountDetailsVO();
			cycleCountDetailsVO.setPartNo(details2dto.getPartNo());
			cycleCountDetailsVO.setParetDescription(details2dto.getParetDescription());
			cycleCountDetailsVO.setGrnNo(details2dto.getGrnNo());
			cycleCountDetailsVO.setSku(details2dto.getSku());
			cycleCountDetailsVO.setBinType(details2dto.getBinType());
			cycleCountDetailsVO.setBatchNo(details2dto.getBatchNo());
			cycleCountDetailsVO.setBatchDate(details2dto.getBatchDate());
			cycleCountDetailsVO.setBin(details2dto.getBin());
			cycleCountDetailsVO.setQty(details2dto.getQty());
			cycleCountDetailsVO.setActualQty(details2dto.getActualQty());
			cycleCountDetailsVO.setQQcflag(details2dto.isQQcflag());

			// Avoid recursive reference to kittingVO in KittingDetails2VO
			cycleCountDetailsVO.setCycleCountVO(cycleCountVO);
			cycleCountDetailsVOs.add(cycleCountDetailsVO);
		}
		cycleCountVO.setCycleCountDetailsVO(cycleCountDetailsVOs);

		return cycleCountVO;
	}

	@Override
	public String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "CT";
		String result = vasPickRepo.getVasPickDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getVaspickGrid(Long orgId, String branch, String branchCode, String client,
			String warehouse) {
		Set<Object[]> result = vasPickRepo.getVaspickGridDetals(orgId, branch, branchCode, client, warehouse);
		return getVaspickFullGrids(result);
	}

	private List<Map<String, Object>> getVaspickFullGrids(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("avlQty", fs[0] != null ? Integer.parseInt(fs[0].toString()) : 0);
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("partNo", fs[2] != null ? fs[2].toString() : "");
			part.put("sku", fs[3] != null ? fs[3].toString() : "");
			part.put("bin", fs[4] != null ? fs[4].toString() : "");
			part.put("batch", fs[5] != null ? fs[5].toString() : "");
			part.put("grnNo", fs[6] != null ? fs[6].toString() : "");
			part.put("lotNo", fs[8] != null ? fs[7].toString() : "");

			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode,
			String finYear, String warehouse) {
		return cycleCountRepo.findAllCycleCount(orgId, client, branch, branchCode, finYear, warehouse);
	}

	@Override
	public Optional<CycleCountVO> getCycleCountById(Long id) {
		return cycleCountRepo.findById(id);
	}

}
