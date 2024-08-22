package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface GuhanSerivce {

//	PickRequest
	List<PickRequestVO> getAllPickRequest(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	PickRequestVO getPickRequestById(Long id);

//	Map<String, Object> createUpdatePickRequest(@Valid PickRequestDTO pickRequestDTO) throws ApplicationException;
	
	String getPickRequestDocId (Long orgId, String finYear, String branch, String branchCode, String client);
	
	
	
}
