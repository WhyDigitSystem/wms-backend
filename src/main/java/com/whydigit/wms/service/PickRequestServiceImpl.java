package com.whydigit.wms.service;

import java.time.LocalDate;
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

import com.whydigit.wms.dto.MultiplePickDTO;
import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.dto.PickRequestDetailsDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.HandlingStockOutVO;
import com.whydigit.wms.entity.PickRequestDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.BuyerRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.HandlingStockOutRepo;
import com.whydigit.wms.repo.MaterialRepo;
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
	MaterialRepo materialRepo;

	@Autowired
	BuyerOrderRepo buyerOrderRepo;
	
	@Autowired
	BuyerRepo buyerRepo;

	@Autowired
	HandlingStockOutRepo handlingStockOutRepo;

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

		List<HandlingStockOutVO> handlingStockOutVOs = handlingStockOutRepo.findBySDocid(savedPickRequestVO.getDocId());
		if (handlingStockOutVOs != null) {
			handlingStockOutRepo.deleteAll(handlingStockOutVOs);
		}

		List<PickRequestDetailsVO> pickRequestDetailsListVO = savedPickRequestVO.getPickRequestDetailsVO();
		for (PickRequestDetailsVO PickRequestDetailsVO : pickRequestDetailsListVO) {
			HandlingStockOutVO handlingStockOutVO = new HandlingStockOutVO();
			handlingStockOutVO.setOrgId(savedPickRequestVO.getOrgId());
			handlingStockOutVO.setBranch(savedPickRequestVO.getBranch());
			handlingStockOutVO.setBranchCode(savedPickRequestVO.getBranchCode());
			handlingStockOutVO.setWarehouse(savedPickRequestVO.getWarehouse());
			handlingStockOutVO.setCustomer(savedPickRequestVO.getCustomer());
			handlingStockOutVO.setClient(savedPickRequestVO.getClient());
			handlingStockOutVO.setPartNo(PickRequestDetailsVO.getPartNo());
			handlingStockOutVO.setPartDesc(PickRequestDetailsVO.getPartDesc());
			handlingStockOutVO.setSku(PickRequestDetailsVO.getSku());
			handlingStockOutVO.setBuyerOrderNo(savedPickRequestVO.getBuyerRefNo());
			handlingStockOutVO.setBuyerOrderDate(savedPickRequestVO.getBuyerRefDate());
			handlingStockOutVO.setPickRequestNo(savedPickRequestVO.getDocId());
			handlingStockOutVO.setPickRequestDate(savedPickRequestVO.getDocDate());
			handlingStockOutVO.setBuyerOrdNo(savedPickRequestVO.getBuyerOrderNo());
			handlingStockOutVO.setSDocid(savedPickRequestVO.getDocId());
			handlingStockOutVO.setRpQty(0);
			handlingStockOutVO.setSQty(PickRequestDetailsVO.getPickQty() * -1);
			handlingStockOutVO.setPickQty(PickRequestDetailsVO.getPickQty() * -1);
			handlingStockOutVO.setScreenCode(savedPickRequestVO.getScreenCode());
			handlingStockOutVO.setBuyerOrdDate(savedPickRequestVO.getBuyerOrderDate());
			handlingStockOutRepo.save(handlingStockOutVO);
		}

		List<PickRequestDetailsVO> pickRequestDetailsVOLists = savedPickRequestVO.getPickRequestDetailsVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			if ("Confirm".equals(savedPickRequestVO.getStatus())) {
				for (PickRequestDetailsVO detailsVO : pickRequestDetailsVOLists) {

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
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(),
							savedPickRequestVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getPickQty() * -1);
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setStatus(detailsVO.getStatus());
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getBin());
					stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
					stockDetailsVOFrom.setPQty(detailsVO.getPickQty());
					stockDetailsVOFrom.setPickedQty(detailsVO.getPickQty());
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
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("pickRequestVO", pickRequestVO);
		response.put("message", message);
		return response;
	}

	private void createUpdatePickRequestVOByPickRequestDTO(PickRequestDTO pickRequestDTO, PickRequestVO pickRequestVO)
			throws ApplicationException {

		BuyerOrderVO buyerOrderVO = buyerOrderRepo.findByDocId(pickRequestDTO.getBuyerOrderNo());
		if ("Confirm".equals(pickRequestDTO.getStatus())) {
			buyerOrderVO.setFreeze(true);
		} else {
			buyerOrderVO.setFreeze(false);
		}
		pickRequestRepo.save(pickRequestVO);

		if ("Confirm".equals(pickRequestDTO.getStatus())) {
			pickRequestVO.setFreeze(true);
		} else {
			pickRequestVO.setFreeze(false);
		}
		pickRequestVO.setBuyerRefNo(pickRequestDTO.getBuyerRefNo());
		pickRequestVO.setBuyerRefDate(pickRequestDTO.getBuyerRefDate());
		pickRequestVO.setBuyerOrderNo(pickRequestDTO.getBuyerOrderNo());
		pickRequestVO.setBuyerOrderDate(pickRequestDTO.getBuyerOrderDate());
		pickRequestVO.setBuyersReference(pickRequestDTO.getBuyersReference());
		pickRequestVO.setInvoiceNo(pickRequestDTO.getInvoiceNo());
		pickRequestVO.setClientShortName(pickRequestDTO.getClientShortName());
		pickRequestVO.setClientName(pickRequestDTO.getClientName());
		pickRequestVO.setClientAddress(pickRequestDTO.getClientAddress());
		pickRequestVO.setCustomerShortName(pickRequestDTO.getCustomerShortName());
		pickRequestVO.setCustomerName(pickRequestDTO.getCustomerName());
		pickRequestVO.setCustomerAddress(pickRequestDTO.getCustomerAddress());
		pickRequestVO.setPickOrder(pickRequestDTO.getPickOrder());
		pickRequestVO.setOutTime(pickRequestDTO.getOutTime());
		pickRequestVO.setOrgId(pickRequestDTO.getOrgId());
		pickRequestVO.setCustomer(pickRequestDTO.getCustomer());
		pickRequestVO.setClient(pickRequestDTO.getClient());
		pickRequestVO.setFinYear(pickRequestDTO.getFinYear());
		pickRequestVO.setBranch(pickRequestDTO.getBranch());
		pickRequestVO.setBranchCode(pickRequestDTO.getBranchCode());
		pickRequestVO.setWarehouse(pickRequestDTO.getWarehouse());
		pickRequestVO.setCreatedBy(pickRequestDTO.getCreatedBy());
		pickRequestVO.setStatus(pickRequestDTO.getStatus());

		int totalPickQty = 0;
		int totalOrderQty = buyerOrderRepo.getTotalOrderQty(pickRequestDTO.getBuyerOrderNo());

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
			pickRequestDetailsVO.setOrderQty(pickRequestDetailsDTO.getOrderQty());
			int avlqty = pickRequestRepo.getAvlQty(pickRequestDTO.getOrgId(), pickRequestDTO.getBranchCode(),
					pickRequestDTO.getWarehouse(), pickRequestDTO.getClient(), pickRequestDetailsDTO.getBin(),
					pickRequestDetailsDTO.getPartNo(), pickRequestDetailsDTO.getGrnNo(),
					pickRequestDetailsDTO.getBatchNo());
			if (avlqty >= pickRequestDetailsDTO.getPickQty()) {
				pickRequestDetailsVO.setPickQty(pickRequestDetailsDTO.getPickQty());
				pickRequestDetailsVO.setAvailQty(pickRequestDetailsDTO.getAvailQty());
				pickRequestDetailsVO.setPickQtyPerBin(pickRequestDetailsDTO.getPickQty());
				int remainqty = pickRequestDetailsDTO.getAvailQty() - pickRequestDetailsDTO.getPickQty();
				pickRequestDetailsVO.setRemainingQty(remainqty);
				totalPickQty = totalPickQty + pickRequestDetailsDTO.getPickQty();
			} else {
				throw new ApplicationException("Pick Qty Should not More then AvailQty");
			}
			pickRequestDetailsVO.setRemarks(pickRequestDetailsDTO.getRemarks());
			pickRequestDetailsVO.setBinClass(pickRequestDetailsDTO.getBinClass());
			pickRequestDetailsVO.setCellType(pickRequestDetailsDTO.getCellType());
			pickRequestDetailsVO.setSsku(pickRequestDetailsDTO.getSku());
			pickRequestDetailsVO.setStockDate(pickRequestDetailsDTO.getStockDate());
			pickRequestDetailsVO.setBinType(pickRequestDetailsDTO.getBinType());
			pickRequestDetailsVO.setExpDate(pickRequestDetailsDTO.getExpDate());
			pickRequestDetailsVO.setStatus("R");
			pickRequestDetailsVO.setQcFlag(pickRequestDetailsDTO.getQcFlag());
			pickRequestDetailsVO.setGrnNo(pickRequestDetailsDTO.getGrnNo());
			pickRequestDetailsVO.setGrnDate(pickRequestDetailsDTO.getGrnDate());
			pickRequestDetailsVO.setStockDate(pickRequestDetailsDTO.getStockDate());
			pickRequestDetailsVO.setPickRequestVO(pickRequestVO);
			pickRequestDetailsVOs.add(pickRequestDetailsVO);
		}
		pickRequestVO.setTotalPickQty(totalPickQty);
		pickRequestVO.setTotalOrderQty(totalOrderQty);
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

	@Override
	public List<Map<String, Object>> getFillGridDetailsForPickRequest(Long orgId, String branchCode, String client,
			String buyerOrderDocId, String pickRequestDocId, String pickStatus) {
		Set<Object[]> getFillDetails = pickRequestRepo.getPickRequestFillDetails(orgId, branchCode, client,
				buyerOrderDocId, pickRequestDocId, pickStatus);
		return fillDetails(getFillDetails);
	}

	private List<Map<String, Object>> fillDetails(Set<Object[]> getFillDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] gridDetails : getFillDetails) {
			Map<String, Object> mapDetails = new HashMap<>();
			mapDetails.put("partNo", gridDetails[0] != null ? gridDetails[0].toString() : "");
			mapDetails.put("partDesc", gridDetails[1] != null ? gridDetails[1].toString() : "");
			mapDetails.put("sku", gridDetails[2] != null ? gridDetails[2].toString() : "");
			mapDetails.put("grnNo", gridDetails[3] != null ? gridDetails[3].toString() : "");
			mapDetails.put("grnDate", gridDetails[4] != null ? gridDetails[4].toString() : "");
			mapDetails.put("batchNo", gridDetails[5] != null ? gridDetails[5].toString() : "");
			mapDetails.put("batchDate", gridDetails[6] != null ? gridDetails[6].toString() : "");
			mapDetails.put("pickQty", gridDetails[7] != null ? Integer.parseInt(gridDetails[7].toString()) : 0);
			mapDetails.put("binClass", gridDetails[8] != null ? gridDetails[8].toString() : "");
			mapDetails.put("binType", gridDetails[9] != null ? gridDetails[9].toString() : "");
			mapDetails.put("bin", gridDetails[10] != null ? gridDetails[10].toString() : "");
			mapDetails.put("cellType", gridDetails[11] != null ? gridDetails[11].toString() : "");
			mapDetails.put("core", gridDetails[12] != null ? gridDetails[12].toString() : "");
			mapDetails.put("expDate", gridDetails[13] != null ? gridDetails[13].toString() : "");
			mapDetails.put("tn", gridDetails[14] != null ? gridDetails[14].toString() : "");
			mapDetails.put("orderQty", gridDetails[15] != null ? Integer.parseInt(gridDetails[15].toString()) : 0);
			mapDetails.put("remainOrderQty",
					gridDetails[16] != null ? Integer.parseInt(gridDetails[16].toString()) : 0);
			mapDetails.put("availQty", gridDetails[17] != null ? Integer.parseInt(gridDetails[17].toString()) : 0);
			mapDetails.put("prpQty", gridDetails[18] != null ? Integer.parseInt(gridDetails[18].toString()) : 0);
			mapDetails.put("trpQty", gridDetails[19] != null ? Integer.parseInt(gridDetails[19].toString()) : 0);
			mapDetails.put("qcFlag", gridDetails[20] != null ? gridDetails[20].toString() : "");
			mapDetails.put("stockDate", gridDetails[21] != null ? gridDetails[21].toString() : "");
			mapDetails.put("id", gridDetails[22] != null ? gridDetails[22].toString() : "");
			details.add(mapDetails);
		}
		return details;
	}

	@Override
	public List<Map<String, Object>> getPendingBuyerOrderDetailsForPickRequest(Long orgId, String finYear,
			String branchCode, String warehouse, String client) {
		Set<Object[]> getBODetails = buyerOrderRepo.getPendingBuyerOrderDetails(orgId, finYear, branchCode, warehouse,
				client);
		return fillPendingBODetails(getBODetails);
	}

	private List<Map<String, Object>> fillPendingBODetails(Set<Object[]> getBODetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] gridDetails : getBODetails) {
			Map<String, Object> mapDetails = new HashMap<>();
			mapDetails.put("buyerOrderNo", gridDetails[0] != null ? gridDetails[0].toString() : "");
			mapDetails.put("buyerOrderDate", gridDetails[1] != null ? gridDetails[1].toString() : "");
			mapDetails.put("buyerRefNo", gridDetails[2] != null ? gridDetails[2].toString() : "");
			mapDetails.put("buyerRefDate", gridDetails[3] != null ? gridDetails[3].toString() : "");
			mapDetails.put("buyersReference", gridDetails[4] != null ? gridDetails[4].toString() : "");
			mapDetails.put("buyersReferenceDate", gridDetails[5] != null ? gridDetails[5].toString() : "");
			mapDetails.put("invoiceNo", gridDetails[6] != null ? gridDetails[6].toString() : "");
			mapDetails.put("clientName", gridDetails[7] != null ? gridDetails[7].toString() : "");
			mapDetails.put("clientShortName", gridDetails[8] != null ? gridDetails[8].toString() : "");
			mapDetails.put("customerName", gridDetails[9] != null ? gridDetails[9].toString() : "");
			mapDetails.put("customerShortName", gridDetails[10] != null ? gridDetails[10].toString() : "");
			mapDetails.put("sno", gridDetails[11] != null ? gridDetails[11].toString() : "");
			details.add(mapDetails);
		}
		return details;
	}

	@Override
	public Map<String, Object> createMultiplePickRequest(List<MultiplePickDTO> multiplePickDTO1)
			throws ApplicationException {
		PickRequestVO pickRequestVO = new PickRequestVO();
		String screenCode = "PR";
		String message = null;
		for (MultiplePickDTO multiplePickDTO : multiplePickDTO1) {

			pickRequestVO.setCreatedBy(multiplePickDTO.getCreatedBy());
			pickRequestVO.setUpdatedBy(multiplePickDTO.getCreatedBy());

			String pickRequestDocId = pickRequestRepo.getPickRequestDocId(multiplePickDTO.getOrgId(),
					multiplePickDTO.getFinYear(), multiplePickDTO.getBranchCode(), multiplePickDTO.getClient(),
					screenCode);
			pickRequestVO.setDocId(pickRequestDocId);

			saveFillGridDetails(multiplePickDTO, pickRequestVO);

//			createUpdatePickRequestVOByMultiplePickRequestDTOs(multiplePickDTO, pickRequestVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(multiplePickDTO.getOrgId(),
							multiplePickDTO.getFinYear(), multiplePickDTO.getBranchCode(), multiplePickDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "PickRequest Created Successfully";

		}
		PickRequestVO savedPickRequestVO = pickRequestRepo.save(pickRequestVO);

		List<HandlingStockOutVO> handlingStockOutVOs = handlingStockOutRepo.findBySDocid(savedPickRequestVO.getDocId());
		if (handlingStockOutVOs != null) {
			handlingStockOutRepo.deleteAll(handlingStockOutVOs);
		}

		List<PickRequestDetailsVO> pickRequestDetailsListVO = savedPickRequestVO.getPickRequestDetailsVO();
		for (PickRequestDetailsVO PickRequestDetailsVO : pickRequestDetailsListVO) {
			HandlingStockOutVO handlingStockOutVO = new HandlingStockOutVO();
			handlingStockOutVO.setOrgId(savedPickRequestVO.getOrgId());
			handlingStockOutVO.setBranch(savedPickRequestVO.getBranch());
			handlingStockOutVO.setBranchCode(savedPickRequestVO.getBranchCode());
			handlingStockOutVO.setWarehouse(savedPickRequestVO.getWarehouse());
			handlingStockOutVO.setCustomer(savedPickRequestVO.getCustomer());
			handlingStockOutVO.setClient(savedPickRequestVO.getClient());
			handlingStockOutVO.setPartNo(PickRequestDetailsVO.getPartNo());
			handlingStockOutVO.setPartDesc(PickRequestDetailsVO.getPartDesc());
			handlingStockOutVO.setSku(PickRequestDetailsVO.getSku());
			handlingStockOutVO.setBuyerOrderNo(savedPickRequestVO.getBuyerRefNo());
			handlingStockOutVO.setBuyerOrderDate(savedPickRequestVO.getBuyerRefDate());
			handlingStockOutVO.setPickRequestNo(savedPickRequestVO.getDocId());
			handlingStockOutVO.setPickRequestDate(savedPickRequestVO.getDocDate());
			handlingStockOutVO.setBuyerOrdNo(savedPickRequestVO.getBuyerOrderNo());
			handlingStockOutVO.setSDocid(savedPickRequestVO.getDocId());
			handlingStockOutVO.setRpQty(0);
			handlingStockOutVO.setSQty(PickRequestDetailsVO.getPickQty() * -1);
			handlingStockOutVO.setPickQty(PickRequestDetailsVO.getPickQty() * -1);
			handlingStockOutVO.setScreenCode(savedPickRequestVO.getScreenCode());
			handlingStockOutVO.setBuyerOrdDate(savedPickRequestVO.getBuyerOrderDate());
			handlingStockOutRepo.save(handlingStockOutVO);
		}

		List<PickRequestDetailsVO> pickRequestDetailsVOLists = savedPickRequestVO.getPickRequestDetailsVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			if ("Confirm".equals(savedPickRequestVO.getStatus())) {
				for (PickRequestDetailsVO detailsVO : pickRequestDetailsVOLists) {

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
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(),
							savedPickRequestVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getPickQty() * -1);
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setStatus(detailsVO.getStatus());
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getBin());
					stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
					stockDetailsVOFrom.setPQty(detailsVO.getPickQty());
					stockDetailsVOFrom.setPickedQty(detailsVO.getPickQty());
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
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		return response;
	}

	private void saveFillGridDetails(MultiplePickDTO multiplePickDTO, PickRequestVO pickRequestVO) throws ApplicationException {

		BuyerOrderVO buyerOrderVO = buyerOrderRepo.findByDocId(multiplePickDTO.getBuyerOrderNo());

		buyerOrderVO.setFreeze(true);

		pickRequestVO.setFreeze(false);

		pickRequestVO.setBuyerRefNo(multiplePickDTO.getBuyerRefNo());
		pickRequestVO.setBuyerRefDate(multiplePickDTO.getBuyerRefDate());
		pickRequestVO.setBuyerOrderNo(multiplePickDTO.getBuyerOrderNo());
		pickRequestVO.setBuyerOrderDate(multiplePickDTO.getBuyerOrderDate());
		pickRequestVO.setBuyersReference(multiplePickDTO.getBuyersReference());
		pickRequestVO.setInvoiceNo(multiplePickDTO.getInvoiceNo());
		pickRequestVO.setClientShortName(multiplePickDTO.getClientShortName());
		pickRequestVO.setClientName(multiplePickDTO.getClientName());
		
		BuyerVO buyerVO = buyerRepo.findByBuyerAndOrgId(multiplePickDTO.getClientName(), multiplePickDTO.getOrgId());
		pickRequestVO.setClientAddress(buyerVO.getAddressLine1()+","+buyerVO.getAddressLine2()+","+buyerVO.getCity()+","+buyerVO.getState()+","+buyerVO.getCountry()+","+buyerVO.getZipCode());
		pickRequestVO.setCustomerShortName(multiplePickDTO.getCustomerShortName());
		pickRequestVO.setCustomerName(multiplePickDTO.getCustomerName());
		
		BuyerVO buyerVO1 = buyerRepo.findByBuyerAndOrgId(multiplePickDTO.getCustomerName(), multiplePickDTO.getOrgId());
		pickRequestVO.setCustomerAddress(buyerVO1.getAddressLine1()+","+buyerVO1.getAddressLine2()+","+buyerVO1.getCity()+","+buyerVO1.getState()+","+buyerVO1.getCountry()+","+buyerVO1.getZipCode());
		pickRequestVO.setPickOrder("FIFO");
		pickRequestVO.setOrgId(multiplePickDTO.getOrgId());
		pickRequestVO.setCustomer(multiplePickDTO.getCustomer());
		pickRequestVO.setClient(multiplePickDTO.getClient());
		pickRequestVO.setFinYear(multiplePickDTO.getFinYear());
		pickRequestVO.setBranch(multiplePickDTO.getBranch());
		pickRequestVO.setBranchCode(multiplePickDTO.getBranchCode());
		pickRequestVO.setWarehouse(multiplePickDTO.getWarehouse());
		pickRequestVO.setCreatedBy(multiplePickDTO.getCreatedBy());
		pickRequestVO.setStatus("Edit");

		int totalPickQty = 0;
		int totalOrderQty = buyerOrderRepo.getTotalOrderQty(multiplePickDTO.getBuyerOrderNo());
		String pickRequestDocId=null;
		String pickStatus="Edit";
		
		List<Map<String,Object>>fillDetails=getFillGridDetailsForPickRequest(multiplePickDTO.getOrgId(), multiplePickDTO.getBranchCode(), multiplePickDTO.getClient(),
				multiplePickDTO.getBuyerOrderNo(), pickRequestDocId, pickStatus);
		
		List<PickRequestDetailsVO> pickRequestDetailsVOs = new ArrayList<>();
		for (Map<String, Object> detailsMap : fillDetails) {
			PickRequestDetailsVO pickRequestDetailsVO = new PickRequestDetailsVO();
			pickRequestDetailsVO.setPartNo(detailsMap.get("partNo") != null ? detailsMap.get("partNo").toString() : "");
		    pickRequestDetailsVO.setPartDesc(detailsMap.get("partDesc") != null ? detailsMap.get("partDesc").toString() : "");
		    pickRequestDetailsVO.setSku(detailsMap.get("sku") != null ? detailsMap.get("sku").toString() : "");
		    pickRequestDetailsVO.setCore(detailsMap.get("core") != null ? detailsMap.get("core").toString() : "");
		    pickRequestDetailsVO.setBin(detailsMap.get("bin") != null ? detailsMap.get("bin").toString() : "");
		    pickRequestDetailsVO.setBatchNo(detailsMap.get("batchNo") != null ? detailsMap.get("batchNo").toString() : "");
		    if (detailsMap.get("batchDate") != null && !detailsMap.get("batchDate").toString().isEmpty()) {
		        // Convert String to LocalDate
		        LocalDate batchDate = LocalDate.parse(detailsMap.get("batchDate").toString());
		        pickRequestDetailsVO.setBatchDate(batchDate);
		    } else {
		        // Handle the case when batchDate is null or empty
		        pickRequestDetailsVO.setBatchDate(null);
		    }
		    pickRequestDetailsVO.setOrderQty(detailsMap.get("orderQty") != null ? Integer.parseInt(detailsMap.get("orderQty").toString()) : 0);
		    int avlqty = pickRequestRepo.getAvlQty(
		        multiplePickDTO.getOrgId(), 
		        multiplePickDTO.getBranchCode(),
		        multiplePickDTO.getWarehouse(), 
		        multiplePickDTO.getClient(), 
		        detailsMap.get("bin").toString(),
		        detailsMap.get("partNo").toString(), 
		        detailsMap.get("grnNo") != null ? detailsMap.get("grnNo").toString() : "",
		        detailsMap.get("batchNo") != null ? detailsMap.get("batchNo").toString() : ""
		    );
		    if (avlqty >= Integer.parseInt(detailsMap.get("pickQty").toString())) {
		        pickRequestDetailsVO.setPickQty(Integer.parseInt(detailsMap.get("pickQty").toString()));
		        pickRequestDetailsVO.setAvailQty(Integer.parseInt(detailsMap.get("availQty").toString()));
		        pickRequestDetailsVO.setPickQtyPerBin(Integer.parseInt(detailsMap.get("pickQty").toString()));
		        
		        int remainQty = Integer.parseInt(detailsMap.get("availQty").toString()) - Integer.parseInt(detailsMap.get("pickQty").toString());
		        pickRequestDetailsVO.setRemainingQty(remainQty);

		        totalPickQty += Integer.parseInt(detailsMap.get("pickQty").toString());
		    } else {
		        throw new ApplicationException("Pick Qty Should not be More than AvailQty");
		    }
		    pickRequestDetailsVO.setRemarks(detailsMap.get("remarks") != null ? detailsMap.get("remarks").toString() : "");
		    pickRequestDetailsVO.setBinClass(detailsMap.get("binClass") != null ? detailsMap.get("binClass").toString() : "");
		    pickRequestDetailsVO.setCellType(detailsMap.get("cellType") != null ? detailsMap.get("cellType").toString() : "");
		    pickRequestDetailsVO.setSsku(detailsMap.get("sku") != null ? detailsMap.get("sku").toString() : "");
		    pickRequestDetailsVO.setBinType(detailsMap.get("binType") != null ? detailsMap.get("binType").toString() : "");
		    if (detailsMap.get("expDate") != null && !detailsMap.get("expDate").toString().isEmpty()) {
		        LocalDate expDate = LocalDate.parse(detailsMap.get("expDate").toString());
		        pickRequestDetailsVO.setExpDate(expDate);
		    } else {
		        pickRequestDetailsVO.setExpDate(null);
		    }
		    if (detailsMap.get("grnDate") != null && !detailsMap.get("grnDate").toString().isEmpty()) {
		        LocalDate grnDate = LocalDate.parse(detailsMap.get("grnDate").toString());
		        pickRequestDetailsVO.setGrnDate(grnDate);
		    } else {
		        pickRequestDetailsVO.setGrnDate(null);
		    }
		    if (detailsMap.get("stockDate") != null && !detailsMap.get("stockDate").toString().isEmpty()) {
		        LocalDate stockDate = LocalDate.parse(detailsMap.get("stockDate").toString());
		        pickRequestDetailsVO.setStockDate(stockDate);
		    } else {
		        pickRequestDetailsVO.setStockDate(null);
		    }
		    pickRequestDetailsVO.setStatus("R");
		    pickRequestDetailsVO.setQcFlag(detailsMap.get("qcFlag") != null ? detailsMap.get("qcFlag").toString() : "");
		    pickRequestDetailsVO.setGrnNo(detailsMap.get("grnNo") != null ? detailsMap.get("grnNo").toString() : "");
			pickRequestDetailsVO.setPickRequestVO(pickRequestVO);
			pickRequestDetailsVOs.add(pickRequestDetailsVO);
		}
		pickRequestVO.setTotalPickQty(totalPickQty);
		pickRequestVO.setTotalOrderQty(totalOrderQty);
		pickRequestVO.setPickRequestDetailsVO(pickRequestDetailsVOs);
	}

  @Override
	public List<Map<String, Object>> getPicrequestDashboard(Long orgId, String branchCode, String client,
			String warehouse, String finyear) {
		Set<Object[]> resultq = pickRequestRepo.getPicrequestDashboard(orgId, branchCode, warehouse, client,finyear);
		return getPicrequest(resultq);
	}

	private List<Map<String, Object>> getPicrequest(Set<Object[]> resultq) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : resultq) {
			Map<String, Object> part = new HashMap<>();
			part.put("orderNo", fs[0] != null ? fs[0].toString() : "");
			part.put("orderDate", fs[1] != null ? fs[1].toString() : "");
			part.put("status", fs[2] != null ? fs[2].toString() : "");
			
			details1.add(part);
		}
		return details1;

	}


}
