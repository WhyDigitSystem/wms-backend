package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.dto.VasPickDetailsDTO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.VasPickDetailsRepo;
import com.whydigit.wms.repo.VasPickRepo;
@Service
public class VasanthServiceImpl implements VasanthService{

	@Autowired
	VasPickRepo vasPickRepo;
	
	@Autowired
	VasPickDetailsRepo vasPickDetailsRepo;
	
	@Override
	public Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException {
	    VasPickVO vasPickVO;
	    String message = null;

	    if (ObjectUtils.isEmpty(vasPicDTO.getId())) {
	        vasPickVO = new VasPickVO();
	        vasPickVO.setCreatedBy(vasPicDTO.getCreatedBy());
	        vasPickVO.setUpdatedBy(vasPicDTO.getCreatedBy());
	        message = "VasPicK Creation Successfully";
	    } else {
	        vasPickVO = vasPickRepo.findById(vasPicDTO.getId())
	                .orElseThrow(() -> new ApplicationException(
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
	    vasPickVO.setScreenName(vasPicDTO.getScreenName());
	    vasPickVO.setScreenCode(vasPicDTO.getScreenCode());
	    vasPickVO.setDocId(vasPicDTO.getDocId());
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
	        detailsVO.setVasPickVO(vasPickVO);  // Set the parent reference
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
		return vasPickRepo.findAllVasPick(orgId,branchCode,client,customer);
	}

}
