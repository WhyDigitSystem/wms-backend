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
	CycleCountRepo cycleCountRepo;

	@Autowired
	CycleCountDetailsRepo cycleCountDetailsRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	

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
		String result = cycleCountRepo.getCycleCountInDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
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
