package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.CycleCountDetailsDTO;
import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.KittingDetails1DTO;
import com.whydigit.wms.dto.KittingDetails2DTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.dto.VasPickDetailsDTO;
import com.whydigit.wms.entity.CycleCountDetailsVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.KittingDetails1VO;
import com.whydigit.wms.entity.KittingDetails2VO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CycleCountDetailsRepo;
import com.whydigit.wms.repo.CycleCountRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.KittingDetails1Repo;
import com.whydigit.wms.repo.KittingDetails2Repo;
import com.whydigit.wms.repo.KittingRepo;
import com.whydigit.wms.repo.VasPickDetailsRepo;
import com.whydigit.wms.repo.VasPickRepo;

@Service
public class VasanthServiceImpl implements VasanthService {

	@Autowired
	VasPickRepo vasPickRepo;

	@Autowired
	VasPickDetailsRepo vasPickDetailsRepo;

	@Autowired
	KittingRepo kittingRepo;

	@Autowired
	KittingDetails1Repo kittingDetails1Repo;

	@Autowired
	KittingDetails2Repo kittingDetails2Repo;

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
			detailsVO.setVasPickVO(vasPickVO); // Set the parent reference
			vasPickDetailsVOs.add(detailsVO);
		}
		vasPickVO.setVasPickDetailsVO(vasPickDetailsVOs);
		return vasPickVO;
	}

	@Override
	public Optional<VasPickVO> getVaspickById(Long id) {
		return vasPickRepo.findVasPickById(id);
	}

	@Override
	public List<VasPickVO> getAllVaspick(Long orgId, String branchCode, String client, String customer) {
		return vasPickRepo.findAllVasPick(orgId, branchCode, client, customer);
	}

	@Override
	public String getVasPickDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "VP";
		String result = vasPickRepo.getVasPickDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

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

	    //	GETDOCID API
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
		kittingVO.setDocDate(kittingDTO.getDocDate());
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
		kittingVO.setRefDate(kittingDTO.getRefDate());

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
			
			 //	GETDOCID API
			String docId = cycleCountRepo.getCycleCountInDocId(cycleCountDTO.getOrgId(), cycleCountDTO.getFinYear(),
					cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), screenCode);
			cycleCountVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(cycleCountDTO.getOrgId(),
							cycleCountDTO.getFinYear(), cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "CycleCountDTO Creation Successfully";
		} else {
			cycleCountVO = cycleCountRepo.findById(cycleCountDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Fount Any Information,Invalid Id ." + cycleCountDTO.getId()));
			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());
			
			List<CycleCountDetailsVO> countDetailsVOs=cycleCountDetailsRepo.findByCycleCountVO(cycleCountVO);
			cycleCountDetailsRepo.deleteAll(countDetailsVOs);
			message = "CycleCountDTO Updation Successfully";
		}
		getCycleCountVOFromCycleCountDTO(cycleCountVO,cycleCountDTO);
		cycleCountRepo.save(cycleCountVO);
		Map<String, Object> response=new HashMap<String, Object>();
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

}
