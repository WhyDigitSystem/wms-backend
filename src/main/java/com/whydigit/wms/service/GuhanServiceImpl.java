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
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
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
				stockDetailsVOFrom.setClientCode(detailsVO.getClientCode());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setPcKey(detailsVO.getPcKey());
				stockDetailsVOFrom.setSSku(detailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
				stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
				
				stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
				stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
				stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
				stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
				stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("pickRequestVO", pickRequestVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePickRequestVOByPickRequestDTO(PickRequestDTO pickRequestDTO, PickRequestVO pickRequestVO) {

		pickRequestVO.setOrgId(pickRequestDTO.getOrgId());
		pickRequestVO.setCustomer(pickRequestDTO.getCustomer());
		pickRequestVO.setClient(pickRequestDTO.getClient());
		pickRequestVO.setFinYear(pickRequestDTO.getFinYear());
		pickRequestVO.setBranchCode(pickRequestDTO.getBranchCode());
		pickRequestVO.setBranch(pickRequestDTO.getBranch());
		pickRequestVO.setWarehouse(pickRequestDTO.getWarehouse());
		pickRequestVO.setTransactionType(pickRequestDTO.getTransactionType());
		pickRequestVO.setBuyerRefDate(pickRequestDTO.getBuyerRefDate());
		pickRequestVO.setShipmentMethod(pickRequestDTO.getShipmentMethod());
		pickRequestVO.setBuyerOrderNo(pickRequestDTO.getBuyerOrderNo());
		pickRequestVO.setInvoiceNo(pickRequestDTO.getInvoiceNo());
		pickRequestVO.setClientShortName(pickRequestDTO.getClientShortName());
		pickRequestVO.setClientAddress(pickRequestDTO.getClientAddress());
		pickRequestVO.setDispatch(pickRequestDTO.getDispatch());
		pickRequestVO.setCustomerName(pickRequestDTO.getCustomerName());
		pickRequestVO.setCustomerAddress(pickRequestDTO.getCustomerAddress());
		pickRequestVO.setDueDays(pickRequestDTO.getDueDays());
		pickRequestVO.setNoOfBoxes(pickRequestDTO.getNoOfBoxes());
		pickRequestVO.setPickOrder(pickRequestDTO.getPickOrder());
		pickRequestVO.setOutTime(pickRequestDTO.getOutTime());
		pickRequestVO.setDueDays(pickRequestDTO.getDueDays());
		

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
			pickRequestDetailsVO.setLocation(pickRequestDetailsDTO.getLocation());
			pickRequestDetailsVO.setBatchNo(pickRequestDetailsDTO.getBatchNo());
			pickRequestDetailsVO.setBatchDate(pickRequestDetailsDTO.getBatchDate());
			pickRequestDetailsVO.setLotNo(pickRequestDetailsDTO.getLotNo());
			pickRequestDetailsVO.setOrderQty(pickRequestDetailsDTO.getOrderQty());
			pickRequestDetailsVO.setPickQty(pickRequestDetailsDTO.getPickQty());
			pickRequestDetailsVO.setAvlQty(pickRequestDetailsDTO.getAvlQty());
			pickRequestDetailsVO.setRunningQty(pickRequestDetailsDTO.getRunningQty());
			pickRequestDetailsVO.setPickQtyPerLocation(pickRequestDetailsDTO.getPickQtyPerLocation());
			pickRequestDetailsVO.setRemainingQty(pickRequestDetailsDTO.getRemainingQty());
			
			pickRequestDetailsVO.setWeight(pickRequestDetailsDTO.getWeight());
			pickRequestDetailsVO.setRate(pickRequestDetailsDTO.getRate());
			pickRequestDetailsVO.setTax(pickRequestDetailsDTO.getTax());
			pickRequestDetailsVO.setAmount(pickRequestDetailsDTO.getAmount());
			pickRequestDetailsVO.setRemarks(pickRequestDetailsDTO.getRemarks());
			pickRequestDetailsVO.setBinClass(pickRequestDetailsDTO.getBinClass());
			pickRequestDetailsVO.setCellType(pickRequestDetailsDTO.getCellType());
			pickRequestDetailsVO.setClientCode(pickRequestDetailsDTO.getClientCode());
			pickRequestDetailsVO.setPcKey(pickRequestDetailsDTO.getPcKey());
			pickRequestDetailsVO.setSsku(pickRequestDetailsDTO.getSsku());
			pickRequestDetailsVO.setStockDate(pickRequestDetailsDTO.getStockDate());
			pickRequestDetailsVO.setBinType(pickRequestDetailsDTO.getBinType());
			pickRequestDetailsVO.setExpDate(pickRequestDetailsDTO.getExpDate());
			pickRequestDetailsVO.setStatus(pickRequestDetailsDTO.getStatus());
			pickRequestDetailsVO.setPickRequestVO(pickRequestVO);

			pickRequestDetailsVOs.add(pickRequestDetailsVO);
		}
		pickRequestVO.setPickRequestDetailsVO(pickRequestDetailsVOs);
	}

}
