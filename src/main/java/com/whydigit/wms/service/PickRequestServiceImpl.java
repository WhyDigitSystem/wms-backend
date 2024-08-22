package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.dto.PickRequestDetailsDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.PickRequestDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.PickRequestDetailsRepo;
import com.whydigit.wms.repo.PickRequestRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class PickRequestServiceImpl implements PickRequestService {

	public static final Logger LOGGER = LoggerFactory.getLogger(PickRequestServiceImpl.class);

	@Autowired
	PickRequestRepo pickRequestRepo;

	@Autowired
	PickRequestDetailsRepo pickRequestDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	BuyerOrderRepo buyerOrderRepo;
	
	@Autowired
	ClientRepo clientRepo;

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
	public Map<String, Object> createUpdatePickRequest(PickRequestDTO pickRequestDTO) throws ApplicationException {

		PickRequestVO pickRequestVO = new PickRequestVO();
		String screenCode = "PR";
		String message;

		if (ObjectUtils.isNotEmpty(pickRequestDTO.getId())) {
			pickRequestVO = pickRequestRepo.findById(pickRequestDTO.getId())
					.orElseThrow(() -> new ApplicationException("PickRequest not found"));

			pickRequestVO.setUpdatedBy(pickRequestDTO.getCreatedBy());
			createUpdatePickRequestVOByPickRequestDTO(pickRequestDTO, pickRequestVO);
			message = "PickRequest Updated Successfully";
		} else {
			pickRequestVO.setCreatedBy(pickRequestDTO.getCreatedBy());
			pickRequestVO.setUpdatedBy(pickRequestDTO.getCreatedBy());

			String pickRequestDocId = pickRequestRepo.getPickRequestDocId(pickRequestDTO.getOrgId(),
					pickRequestDTO.getFinYear(), pickRequestDTO.getBranchCode(), pickRequestDTO.getClient(),
					screenCode);
			pickRequestVO.setDocId(pickRequestDocId);
			createUpdatePickRequestVOByPickRequestDTO(pickRequestDTO, pickRequestVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(pickRequestDTO.getOrgId(),
							pickRequestDTO.getFinYear(), pickRequestDTO.getBranchCode(), pickRequestDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "PickRequest Created Successfully";
		}
		PickRequestVO savedPickRequestVO = pickRequestRepo.save(pickRequestVO);

		List<PickRequestDetailsVO> pickRequestDetailsVOLists = savedPickRequestVO.getPickRequestDetailsVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			for (PickRequestDetailsVO detailsVO : pickRequestDetailsVOLists) {
				
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
				stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
				stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
				stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
				stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setClientCode(clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
				stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
				stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
				stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setSQty(detailsVO.getPickQty());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setStatus(detailsVO.getStatus());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setSSku(detailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
				
				stockDetailsRepo.save(stockDetailsVOFrom);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("pickRequestVO", pickRequestVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePickRequestVOByPickRequestDTO(PickRequestDTO pickRequestDTO, PickRequestVO pickRequestVO) {

		if("Confirm".equals(pickRequestDTO.getStatus()))
		{
		pickRequestVO.setFreeze(true);
		}
		else
		{
			pickRequestVO.setFreeze(false);
		}
		pickRequestVO.setOrgId(pickRequestDTO.getOrgId());
		pickRequestVO.setCustomer(pickRequestDTO.getCustomer());
		pickRequestVO.setClient(pickRequestDTO.getClient());
		pickRequestVO.setFinYear(pickRequestDTO.getFinYear());
		pickRequestVO.setBranchCode(pickRequestDTO.getBranchCode());
		pickRequestVO.setBranch(pickRequestDTO.getBranch());
		pickRequestVO.setWarehouse(pickRequestDTO.getWarehouse());
		pickRequestVO.setBuyerRefDate(pickRequestDTO.getBuyerRefDate());
		pickRequestVO.setShipmentMethod(pickRequestDTO.getShipmentMethod());
		pickRequestVO.setBuyerOrderNo(pickRequestDTO.getBuyerOrderNo());
		pickRequestVO.setShipmentMethod(pickRequestDTO.getShipmentMethod());
		pickRequestVO.setBuyerRefNo(pickRequestDTO.getBuyerRefNo());
		pickRequestVO.setStatus(pickRequestDTO.getStatus());
		pickRequestVO.setInvoiceNo(pickRequestDTO.getInvoiceNo());
		pickRequestVO.setClientShortName(pickRequestDTO.getClientShortName());
		pickRequestVO.setClientName(pickRequestDTO.getClientName());
		pickRequestVO.setClientAddress(pickRequestDTO.getClientAddress());
		pickRequestVO.setCustomerShortName(pickRequestDTO.getCustomerShortName());
		pickRequestVO.setCustomerName(pickRequestDTO.getCustomerName());
		pickRequestVO.setCustomerAddress(pickRequestDTO.getCustomerAddress());
		pickRequestVO.setPickOrder(pickRequestDTO.getPickOrder());
		pickRequestVO.setOutTime(pickRequestDTO.getOutTime());
		
		if (ObjectUtils.isNotEmpty(pickRequestVO.getId())) {
			List<PickRequestDetailsVO> pickRequestDetailsVO1 = pickRequestDetailsRepo
					.findByPickRequestVO(pickRequestVO);
			pickRequestDetailsRepo.deleteAll(pickRequestDetailsVO1);
		}

		List<PickRequestDetailsVO> pickRequestDetailsVOs = new ArrayList<>();
		for (PickRequestDetailsDTO pickRequestDetailsDTO : pickRequestDTO.getPickRequestDetailsDTO()) {
			PickRequestDetailsVO pickRequestDetailsVO = new PickRequestDetailsVO();
			pickRequestDetailsVO.setPartNo(pickRequestDetailsDTO.getPartNo());
			pickRequestDetailsVO.setPartDesc(pickRequestDetailsDTO.getPartDesc());
			pickRequestDetailsVO.setSku(pickRequestDetailsDTO.getSku());
			pickRequestDetailsVO.setCore(pickRequestDetailsDTO.getCore());
			pickRequestDetailsVO.setBin(pickRequestDetailsDTO.getBin());
			pickRequestDetailsVO.setBatchNo(pickRequestDetailsDTO.getBatchNo());
			pickRequestDetailsVO.setBatchDate(pickRequestDetailsDTO.getBatchDate());
			pickRequestDetailsVO.setLotNo(pickRequestDetailsDTO.getLotNo());
			pickRequestDetailsVO.setOrderQty(pickRequestDetailsDTO.getOrderQty());
			pickRequestDetailsVO.setPickQty(pickRequestDetailsDTO.getPickQty());
			pickRequestDetailsVO.setAvlQty(pickRequestDetailsDTO.getAvlQty());
			pickRequestDetailsVO.setRunningQty(pickRequestDetailsDTO.getRunningQty());
			pickRequestDetailsVO.setPickQtyPerBin(pickRequestDetailsDTO.getPickQtyPerBin());
			pickRequestDetailsVO.setRemainingQty(pickRequestDetailsDTO.getRemainingQty());
			pickRequestDetailsVO.setRemarks(pickRequestDetailsDTO.getRemarks());
			pickRequestDetailsVO.setBinClass(pickRequestDetailsDTO.getBinClass());
			pickRequestDetailsVO.setCellType(pickRequestDetailsDTO.getCellType());
			pickRequestDetailsVO.setSsku(pickRequestDetailsDTO.getSku());
			pickRequestDetailsVO.setStockDate(pickRequestDetailsDTO.getStockDate());
			pickRequestDetailsVO.setBinType(pickRequestDetailsDTO.getBinType());
			pickRequestDetailsVO.setExpDate(pickRequestDetailsDTO.getExpDate());
			pickRequestDetailsVO.setStatus(pickRequestDetailsDTO.getStatus());
			pickRequestDetailsVO.setQcFlag(pickRequestDetailsDTO.getQcFlag());
			pickRequestDetailsVO.setGrnNo(pickRequestDetailsDTO.getGrnNo());
			pickRequestDetailsVO.setGrnDate(pickRequestDetailsDTO.getGrnDate());
			pickRequestDetailsVO.setPickRequestVO(pickRequestVO);
			pickRequestDetailsVOs.add(pickRequestDetailsVO);
		}
		pickRequestVO.setPickRequestDetailsVO(pickRequestDetailsVOs);
	}

	@Override
	public String getPickRequestDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "PR";
		String result = pickRequestRepo.getPickRequestDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	@Transactional
	public List<BuyerOrderVO> getBuyerRefNoFromBuyerOrderForPickRequest(Long orgId, String finYear, String branchCode,
			String warehouse, String client) {

		return buyerOrderRepo.findBuyerRefNoFromBuyerOrderForPickRequest(orgId, finYear, branchCode, warehouse, client);
		
	}


}
