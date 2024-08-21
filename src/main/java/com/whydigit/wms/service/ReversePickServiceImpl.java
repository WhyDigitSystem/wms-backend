package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.controller.ReversePickController;
import com.whydigit.wms.dto.ReversePickDTO;
import com.whydigit.wms.dto.ReversePickDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.ReversePickDetailsVO;
import com.whydigit.wms.entity.ReversePickVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.ReversePickDetailsRepo;
import com.whydigit.wms.repo.ReversePickRepo;
@Service
public class ReversePickServiceImpl implements ReversePickService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReversePickController.class);
	
	@Autowired
	ReversePickRepo reversePickRepo;

	@Autowired
	ReversePickDetailsRepo reversePickDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public Map<String, Object> createUpdateReversePick(ReversePickDTO reversePickDTO) throws ApplicationException {

		ReversePickVO reversePickVO;
		String message;
		String screenCode = "RP";

		if (ObjectUtils.isEmpty(reversePickDTO.getId())) {

			reversePickVO = new ReversePickVO();
			reversePickVO.setCreatedBy(reversePickDTO.getCreatedBy());
			reversePickVO.setUpdatedBy(reversePickDTO.getCreatedBy());

			// GETDOCID API
			String docId = reversePickRepo.getReversePickDocId(reversePickDTO.getOrgId(), reversePickDTO.getFinYear(),
					reversePickDTO.getBranchCode(), reversePickDTO.getClient(), screenCode);
			reversePickVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(reversePickDTO.getOrgId(),
							reversePickDTO.getFinYear(), reversePickDTO.getBranchCode(), reversePickDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "ReversePick Creation Successfully";

		}else
		{
			reversePickVO=reversePickRepo.findById(reversePickDTO.getId()).orElseThrow
					(()->new ApplicationException("This Id Not Fount Any Information,Invalid Id ."+reversePickDTO.getId()));
			
			List<ReversePickDetailsVO> detailsVOs = reversePickDetailsRepo.findByReversePickVO(reversePickVO);
			reversePickDetailsRepo.deleteAll(detailsVOs);
			
			message = "ReversePick Updation Successfully";
			
		}
		
		getReversePickVOFromReversePickDTO(reversePickVO,reversePickDTO);
		reversePickRepo.save(reversePickVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("reversePickVO", reversePickVO);
		return response;

	}

	private ReversePickVO getReversePickVOFromReversePickDTO(ReversePickVO reversePickVO, ReversePickDTO reversePickDTO) {

		reversePickVO.setPickRequestId(reversePickDTO.getPickRequestId());
		reversePickVO.setDispatch(reversePickDTO.getDispatch());
		reversePickVO.setBuyerOrderNo(reversePickDTO.getBuyerOrderNo());
		reversePickVO.setShipmentMethod(reversePickDTO.getShipmentMethod());
		reversePickVO.setRefNo(reversePickDTO.getRefNo());
		reversePickVO.setBuyerOrderRefDate(reversePickDTO.getBuyerOrderRefDate());
		reversePickVO.setNoOfBoxes(reversePickDTO.getNoOfBoxes());
		reversePickVO.setBuyerOrderRefNo(reversePickDTO.getBuyerOrderRefNo());
		reversePickVO.setDueDays(reversePickDTO.getDueDays());
		reversePickVO.setClientName(reversePickDTO.getClientName());
		reversePickVO.setCustomerName(reversePickDTO.getCustomerName());
		reversePickVO.setOutTime(reversePickDTO.getOutTime());
		reversePickVO.setClientAddress(reversePickDTO.getClientAddress());
		reversePickVO.setCustomerAddress(reversePickDTO.getCustomerAddress());
		reversePickVO.setStatus(reversePickDTO.getStatus());
		reversePickVO.setBoAmmount(reversePickDTO.getBoAmmount());
		reversePickVO.setOrgId(reversePickDTO.getOrgId());
		reversePickVO.setCustomer(reversePickDTO.getCustomer());
		reversePickVO.setClient(reversePickDTO.getClient());
		reversePickVO.setFinYear(reversePickDTO.getFinYear());
		reversePickVO.setBranch(reversePickDTO.getBranch());
		reversePickVO.setBranchCode(reversePickDTO.getBranchCode());
		reversePickVO.setWarehouse(reversePickDTO.getWarehouse());
		reversePickVO.setCancelRemarks(reversePickDTO.getCancelRemarks());
		
		
		List<ReversePickDetailsVO> reversePickDetailsVOs = new ArrayList<>();
		for (ReversePickDetailsDTO details2dto : reversePickDTO.getReversePickDetailsDTO()) {
			ReversePickDetailsVO reversePickDetailsVO = new ReversePickDetailsVO();
			reversePickDetailsVO.setPartCode(details2dto.getPartCode());
			reversePickDetailsVO.setPartDesc(details2dto.getPartDesc());
			reversePickDetailsVO.setBatchNo(details2dto.getBatchNo());
			reversePickDetailsVO.setLotNo(details2dto.getLotNo());
			reversePickDetailsVO.setSku(details2dto.getSku());
			reversePickDetailsVO.setLocation(details2dto.getLocation());
			reversePickDetailsVO.setToLocation(details2dto.getToLocation());
			reversePickDetailsVO.setOrderQty(details2dto.getOrderQty());
			reversePickDetailsVO.setPickedQtyPerLocation(details2dto.getPickedQtyPerLocation());
			reversePickDetailsVO.setRevisedQtyPerLocation(details2dto.getRevisedQtyPerLocation());
			reversePickDetailsVO.setWeight(details2dto.getWeight());
			reversePickDetailsVO.setPGroup(details2dto.getPGroup());
			reversePickDetailsVO.setExpDate(details2dto.getExpDate());
			reversePickDetailsVO.setRate(details2dto.getRate());
			reversePickDetailsVO.setTax(details2dto.getTax());
			reversePickDetailsVO.setAmount(details2dto.getAmount());
			reversePickDetailsVO.setReMarks(details2dto.getRemarks());
			
			reversePickDetailsVO.setReversePickVO(reversePickVO);
			reversePickDetailsVOs.add(reversePickDetailsVO);
		}
		reversePickVO.setReversePickDetailsVO(reversePickDetailsVOs);
		
		return reversePickVO;
	}

	@Override
	public String getReversePickDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "RP";
		String result = reversePickRepo.getReversePickDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<ReversePickVO> getAllReversePick(Long orgId, String client, String branch, String branchCode,
			String finYear, String warehouse) {
		return reversePickRepo.getReversePickDetails(orgId,client,branch,branchCode,finYear,warehouse);
	}

	@Override
	public ReversePickVO getReversePickById(Long id) {
		ReversePickVO reversePickVO = new ReversePickVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  reversePick BY Id : {}", id);
			reversePickVO = reversePickRepo.findById(id).orElse(null);
		} else {
			LOGGER.info("failed Received  reversePick For All Id.");
		}
		return reversePickVO;

	}
	

}
