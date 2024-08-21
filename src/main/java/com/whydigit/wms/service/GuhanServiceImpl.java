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

import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.dto.PickRequestDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.PickRequestDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.PickRequestDetailsRepo;
import com.whydigit.wms.repo.PickRequestRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class GuhanServiceImpl implements GuhanSerivce {

	public static final Logger LOGGER = LoggerFactory.getLogger(GuhanServiceImpl.class);

	@Autowired
	PickRequestRepo pickRequestRepo;

	@Autowired
	PickRequestDetailsRepo pickRequestDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

//	PickRequest
	@Override
	public List<PickRequestVO> getAllPickRequest(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return pickRequestRepo.findAllPickRequest(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public PickRequestVO getPickRequestById(Long id) {
		PickRequestVO pickRequestVO = new PickRequestVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  PickRequest BY Id : {}", id);
			pickRequestVO = pickRequestRepo.findPickRequestById(id);
		} else {
			LOGGER.info("failed Received PickRequest For All Id.");
		}
		return pickRequestVO;

	}

	

	@Override
	public String getPickRequestDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "PR";
		String result = pickRequestRepo.getPickRequestDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}
	

}
