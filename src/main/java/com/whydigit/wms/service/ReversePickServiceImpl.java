package com.whydigit.wms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.controller.ReversePickController;
import com.whydigit.wms.dto.ReversePickDTO;
import com.whydigit.wms.dto.ReversePickDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.ReversePickDetailsVO;
import com.whydigit.wms.entity.ReversePickVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.PickRequestRepo;
import com.whydigit.wms.repo.ReversePickDetailsRepo;
import com.whydigit.wms.repo.ReversePickRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
@Service
public class ReversePickServiceImpl implements ReversePickService {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReversePickController.class);
	
	@Autowired
	ReversePickRepo reversePickRepo;

	@Autowired
	ReversePickDetailsRepo reversePickDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	PickRequestRepo pickRequestRepo;
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	MaterialRepo materialRepo;
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Override
	public Map<String, Object> createUpdateReversePick(ReversePickDTO reversePickDTO) throws ApplicationException {

		ReversePickVO reversePickVO = new ReversePickVO();
		String screenCode = "RP";
		String message;

		if (ObjectUtils.isNotEmpty(reversePickDTO.getId())) {
			reversePickVO = reversePickRepo.findById(reversePickDTO.getId())
					.orElseThrow(() -> new ApplicationException("Reverse PickRequest not found"));

			reversePickVO.setUpdatedBy(reversePickDTO.getCreatedBy());
			getReversePickVOFromReversePickDTO(reversePickVO,reversePickDTO);
			message = "PickRequest Updated Successfully";
		} else {
			reversePickVO.setCreatedBy(reversePickDTO.getCreatedBy());
			reversePickVO.setUpdatedBy(reversePickDTO.getCreatedBy());

			String pickRequestDocId = reversePickRepo.getReversePickDocId(reversePickDTO.getOrgId(),
					reversePickDTO.getFinYear(), reversePickDTO.getBranchCode(), reversePickDTO.getClient(),
					screenCode);
			reversePickVO.setDocId(pickRequestDocId);
			getReversePickVOFromReversePickDTO(reversePickVO,reversePickDTO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(reversePickDTO.getOrgId(),
							reversePickDTO.getFinYear(), reversePickDTO.getBranchCode(), reversePickDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "PickRequest Created Successfully";
		}
		
		ReversePickVO savedPickRequestVO= reversePickRepo.save(reversePickVO);
		List<ReversePickDetailsVO> pickRequestDetailsVOLists = savedPickRequestVO.getReversePickDetailsVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			if ("Confirm".equals(savedPickRequestVO.getStatus())) {
				for (ReversePickDetailsVO detailsVO : pickRequestDetailsVOLists) {
					
					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
					stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
					stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
					stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
					stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
					stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
					stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
					stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
					stockDetailsVOFrom.setClientCode(
							clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
					stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
					stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
					stockDetailsVOFrom.setBuyerOrderNo(savedPickRequestVO.getBuyerOrderNo());
					stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getRevisedQty());
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setStatus(detailsVO.getStatus());
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getBin());
					stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
					stockDetailsVOFrom.setPQty(0);
					stockDetailsVOFrom.setPickedQty(0);
					stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
					stockDetailsVOFrom.setBinType(detailsVO.getBinType());
					stockDetailsVOFrom.setSku(detailsVO.getSku());
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setCellType(detailsVO.getCellType());
					stockDetailsVOFrom.setCore(detailsVO.getCore());
					stockDetailsVOFrom.setSSku(detailsVO.getSku());
					stockDetailsVOFrom.setSourceScreenCode(savedPickRequestVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(savedPickRequestVO.getScreenName());
					stockDetailsVOFrom.setSourceId(detailsVO.getId());
					stockDetailsVOFrom.setStockDate(LocalDate.now());
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("reversePickVO", reversePickVO);
		return response;

	}

	private ReversePickVO getReversePickVOFromReversePickDTO(ReversePickVO reversePickVO, ReversePickDTO reversePickDTO) {
		
		if ("Confirm".equals(reversePickDTO.getStatus())) {
			reversePickVO.setFreeze(true);
		} else {
			reversePickVO.setFreeze(false);
		}
		reversePickVO.setPickRequestDocId(reversePickDTO.getPickRequestDocId());
		reversePickVO.setPickRequestDocDate(reversePickDTO.getPickRequestDocDate());
		reversePickVO.setBuyerRefNo(reversePickDTO.getBuyerRefNo());
		reversePickVO.setBuyerRefDate(reversePickDTO.getBuyerRefDate());
		reversePickVO.setBuyerOrderNo(reversePickDTO.getBuyerOrderNo());
		reversePickVO.setBuyerOrderDate(reversePickDTO.getBuyerOrderDate());
		reversePickVO.setBuyersReference(reversePickDTO.getBuyersReference());
		reversePickVO.setInvoiceNo(reversePickDTO.getInvoiceNo());
		reversePickVO.setClientShortName(reversePickDTO.getClientShortName());
		reversePickVO.setClientName(reversePickDTO.getClientName());
		reversePickVO.setClientAddress(reversePickDTO.getClientAddress());
		reversePickVO.setCustomerShortName(reversePickDTO.getCustomerShortName());
		reversePickVO.setCustomerName(reversePickDTO.getCustomerName());
		reversePickVO.setCustomerAddress(reversePickDTO.getCustomerAddress());
		reversePickVO.setPickOrder(reversePickDTO.getPickOrder());
		reversePickVO.setInTime(reversePickDTO.getInTime());
		reversePickVO.setOrgId(reversePickDTO.getOrgId());
		reversePickVO.setCustomer(reversePickDTO.getCustomer());
		reversePickVO.setClient(reversePickDTO.getClient());
		reversePickVO.setFinYear(reversePickDTO.getFinYear());
		reversePickVO.setBranch(reversePickDTO.getBranch());
		reversePickVO.setBranchCode(reversePickDTO.getBranchCode());
		reversePickVO.setWarehouse(reversePickDTO.getWarehouse());
		reversePickVO.setCreatedBy(reversePickDTO.getCreatedBy());
		reversePickVO.setStatus(reversePickDTO.getStatus());
		reversePickVO.setBoAmendment(reversePickDTO.getBoAmendment());
		
		if (ObjectUtils.isNotEmpty(reversePickVO.getId())) {
			List<ReversePickDetailsVO> reversePickRequestDetailsVO1 = reversePickDetailsRepo
					.findByReversePickVO(reversePickVO);
			reversePickDetailsRepo.deleteAll(reversePickRequestDetailsVO1);
		}
		
		List<ReversePickDetailsVO> reversePickDetailsVOs = new ArrayList<>();
		for (ReversePickDetailsDTO details2dto : reversePickDTO.getReversePickDetailsDTO()) {
			ReversePickDetailsVO reversePickDetailsVO = new ReversePickDetailsVO();
			reversePickDetailsVO.setPartNo(details2dto.getPartNo());
			reversePickDetailsVO.setPartDesc(details2dto.getPartDesc());
			reversePickDetailsVO.setSku(details2dto.getSku());
			reversePickDetailsVO.setCore(details2dto.getCore());
			reversePickDetailsVO.setBin(details2dto.getBin());
			reversePickDetailsVO.setBatchNo(details2dto.getBatchNo());
			reversePickDetailsVO.setBatchDate(details2dto.getBatchDate());
			reversePickDetailsVO.setOrderQty(details2dto.getOrderQty());
			reversePickDetailsVO.setPickQty(details2dto.getPickQty());
			reversePickDetailsVO.setRevisedQty(details2dto.getRevisedQty());
			reversePickDetailsVO.setRemarks(details2dto.getRemarks());
			reversePickDetailsVO.setBinClass(details2dto.getBinClass());
			reversePickDetailsVO.setCellType(details2dto.getCellType());
			reversePickDetailsVO.setSsku(details2dto.getSku());
			reversePickDetailsVO.setBinType(details2dto.getBinType());
			reversePickDetailsVO.setExpDate(details2dto.getExpDate());
			if("Defective".equals(details2dto.getBin()))
			{
				reversePickDetailsVO.setStatus("D");
				reversePickDetailsVO.setQcFlag("F");
			}
			reversePickDetailsVO.setStatus("R");
			reversePickDetailsVO.setQcFlag("T");
			reversePickDetailsVO.setGrnNo(details2dto.getGrnNo());
			reversePickDetailsVO.setGrnDate(details2dto.getGrnDate());
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

	@Override
	public List<PickRequestVO> getPickRequestDetailsForReversePick(Long orgId, String finYear, String branch,
			String branchCode, String client) {
		return pickRequestRepo.getPickDetails(orgId, finYear, branch,
				branchCode, client);
	}
	
	@Override
	public List<Map<String,Object>> getPickRequestFillDetailsForReversePick(Long orgId,
			String branchCode, String client,String pickDocId) {
		Set<Object[]>fillDetails= pickRequestRepo.fillgridDetails(orgId,
				branchCode, client,pickDocId);
		return details(fillDetails);
	}

	private List<Map<String, Object>> details(Set<Object[]> fillDetails) {
		List<Map<String, Object>> getDetails= new ArrayList<>();
		for(Object[] gridDetails:fillDetails)
		{
			Map<String, Object>mapDetails= new HashMap<>();
			mapDetails.put("partNo", gridDetails[0] != null ? gridDetails[0].toString() : "");
			mapDetails.put("partDesc", gridDetails[1] != null ? gridDetails[1].toString() : "");
			mapDetails.put("sku", gridDetails[2] != null ? gridDetails[2].toString() : "");
			mapDetails.put("grnNo", gridDetails[3] != null ? gridDetails[3].toString() : "");
			mapDetails.put("grnDate", gridDetails[4] != null ? gridDetails[4].toString() : "");
			mapDetails.put("batchNo", gridDetails[5] != null ? gridDetails[5].toString() : "");
			mapDetails.put("batchDate", gridDetails[6] != null ? gridDetails[6].toString() : "");
			mapDetails.put("binType", gridDetails[7] != null ? gridDetails[7].toString() : "");
			mapDetails.put("binClass", gridDetails[8] != null ? gridDetails[8].toString() : "");
			mapDetails.put("cellType", gridDetails[9] != null ? gridDetails[9].toString() : "");
			mapDetails.put("core", gridDetails[10] != null ? gridDetails[10].toString() : "");
			mapDetails.put("bin", gridDetails[11] != null ? gridDetails[11].toString() : "");
			mapDetails.put("orderQty", gridDetails[12] != null ? Integer.parseInt(gridDetails[12].toString()) : 0);
			mapDetails.put("pickQty", gridDetails[13] != null ? Integer.parseInt(gridDetails[13].toString()) : 0);
			mapDetails.put("expDate", gridDetails[14] != null ? gridDetails[14].toString() : "");
			mapDetails.put("qcFlag", gridDetails[15] != null ? gridDetails[15].toString() : "");
			mapDetails.put("id", gridDetails[16] != null ? gridDetails[16].toString() : "");
			getDetails.add(mapDetails);
		}
		return getDetails;
	}
	

}
