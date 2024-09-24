package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.MultipleBODTO;
import com.whydigit.wms.dto.MultiplePickDTO;
import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface PickRequestService {
	
	List<PickRequestVO> getAllPickRequest(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	PickRequestVO getPickRequestById(Long id);

	Map<String, Object> createUpdatePickRequest(@Valid PickRequestDTO pickRequestDTO) throws ApplicationException;
	
	String getPickRequestDocId (Long orgId, String finYear, String branch, String branchCode, String client);
	
	List<BuyerOrderVO> getBuyerRefNoFromBuyerOrderForPickRequest(Long orgId, String finYear, String branchCode,
			String warehouse, String client);
	
	List<Map<String,Object>>getFillGridDetailsForPickRequest(Long orgId, String branchCode, String client,
			String buyerOrderDocId, String pickRequestDocId,String pickStatus);

	
	//BO details for Multiple Pick request
	List<Map<String, Object>> getPendingBuyerOrderDetailsForPickRequest(Long orgId, String finYear, String branchCode,
			String warehouse, String client);
	
	Map<String, Object> createMultiplePickRequest(List<MultiplePickDTO> multiplePickDTO1) throws ApplicationException;

	List<Map<String, Object>> getPicrequestDashboard(Long orgId, String branchCode, String client, String warehouse,
			String finyear);

	

	
	
}
