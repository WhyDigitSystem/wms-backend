package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.DeliveryChallanDetailsDTO;
import com.whydigit.wms.entity.DeliveryChallanDetailsVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderDetailsRepo;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.DeliveryChallanDetailsRepo;
import com.whydigit.wms.repo.DeliveryChallanRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.PickRequestRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class DeliveryChallanServiceImpl implements DeliveryChallanService{

	public static final Logger LOGGER = LoggerFactory.getLogger(DeliveryChallanServiceImpl.class);

	@Autowired
	DeliveryChallanRepo deliveryChallanRepo;

	@Autowired
	DeliveryChallanDetailsRepo deliveryChallanDetailsRepo;
	

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	BuyerOrderRepo buyerOrderRepo;

	@Autowired
	BuyerOrderDetailsRepo buyerOrderDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	PickRequestRepo pickRequestRepo;
	
	
	// DeliveryChallan

	@Override
	public List<DeliveryChallanVO> getAllDeliveryChallan(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return deliveryChallanRepo.findAllDeliveryChallan(orgId, finYear, branch, branchCode, client, warehouse);

	}

	@Override
	public DeliveryChallanVO getDeliveryChallanById(Long id) {
		DeliveryChallanVO deliveryChallanVO = new DeliveryChallanVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  DeliveryChallan BY Id : {}", id);
			deliveryChallanVO = deliveryChallanRepo.findDeliveryChallanById(id);
		} else {
			LOGGER.info("failed Received  DeliveryChallan For All Id.");
		}
		return deliveryChallanVO;

	}

	@Override
	@Transactional
	public String getDeliveryChallanDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "DC";
		String result = deliveryChallanRepo.getDeliveryChallanDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

//	

	@Override
	public Map<String, Object> createUpdateDeliveryChallan(DeliveryChallanDTO deliveryChallanDTO)
			throws ApplicationException {
		DeliveryChallanVO deliveryChallanVO = new DeliveryChallanVO();
		String screenCode = "DC";
		String message;

		if (ObjectUtils.isNotEmpty(deliveryChallanDTO.getId())) {
			deliveryChallanVO = deliveryChallanRepo.findById(deliveryChallanDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChallan details"));
			deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
			createUpdateDeliveryChallanVOByDeliveryChallanDTO(deliveryChallanDTO, deliveryChallanVO);

			message = "DeliveryChallan updated Successfully";

		} else {
			if (deliveryChallanRepo.existsByBuyerOrderNoAndOrgId(deliveryChallanDTO.getBuyerOrderNo(),
					deliveryChallanDTO.getOrgId())) {
				throw new ApplicationException("The given BuyerOrderNo already exists.");
			}

			deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
			deliveryChallanVO.setCreatedBy(deliveryChallanDTO.getCreatedBy());

//			GETDOCID API
			String docId = deliveryChallanRepo.getDeliveryChallanDocId(deliveryChallanDTO.getOrgId(),
					deliveryChallanDTO.getFinYear(), deliveryChallanDTO.getBranchCode(), deliveryChallanDTO.getClient(),
					screenCode);

			deliveryChallanVO.setDocId(docId);

			createUpdateDeliveryChallanVOByDeliveryChallanDTO(deliveryChallanDTO, deliveryChallanVO);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(deliveryChallanDTO.getOrgId(),
							deliveryChallanDTO.getFinYear(), deliveryChallanDTO.getBranchCode(),
							deliveryChallanDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "DeliveryChallan created Successfully";

		}

		DeliveryChallanVO savedDeliveryChallanVO = deliveryChallanRepo.save(deliveryChallanVO);

		Map<String, Object> response = new HashMap<>();
		response.put("deliveryChallanVO", deliveryChallanVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateDeliveryChallanVOByDeliveryChallanDTO(@Valid DeliveryChallanDTO deliveryChallanDTO,
			DeliveryChallanVO deliveryChallanVO) {

		deliveryChallanVO.setBuyerOrderNo(deliveryChallanDTO.getBuyerOrderNo());
		deliveryChallanVO.setPickReqDate(deliveryChallanDTO.getPickReqDate());
		deliveryChallanVO.setInvoiceNo(deliveryChallanDTO.getInvoiceNo());
		deliveryChallanVO.setContainerNO(deliveryChallanDTO.getContainerNO());
		deliveryChallanVO.setVechileNo(deliveryChallanDTO.getVechileNo());
		deliveryChallanVO.setExciseInvoiceNo(deliveryChallanDTO.getExciseInvoiceNo());
		deliveryChallanVO.setCommercialInvoiceNo(deliveryChallanDTO.getCommercialInvoiceNo());
		deliveryChallanVO.setBoDate(deliveryChallanDTO.getBoDate());
		deliveryChallanVO.setBuyer(deliveryChallanDTO.getBuyer());
		deliveryChallanVO.setDeliveryTerms(deliveryChallanDTO.getDeliveryTerms());
		deliveryChallanVO.setPayTerms(deliveryChallanDTO.getPayTerms());
		deliveryChallanVO.setGrWaiverNo(deliveryChallanDTO.getGrWaiverNo());
		deliveryChallanVO.setGrWaiverDate(deliveryChallanDTO.getGrWaiverDate());
		deliveryChallanVO.setBankName(deliveryChallanDTO.getBankName());
		deliveryChallanVO.setGrWaiverClosureDate(deliveryChallanDTO.getGrWaiverClosureDate());
		deliveryChallanVO.setGatePassNo(deliveryChallanDTO.getGatePassNo());
		deliveryChallanVO.setGatePassDate(deliveryChallanDTO.getGatePassDate());
		deliveryChallanVO.setInsuranceNo(deliveryChallanDTO.getInsuranceNo());
		deliveryChallanVO.setBillTo(deliveryChallanDTO.getBillTo());
		deliveryChallanVO.setShipTo(deliveryChallanDTO.getShipTo());

		deliveryChallanVO.setAutomailerGroup(deliveryChallanDTO.getAutomailerGroup());
		deliveryChallanVO.setDocketNo(deliveryChallanDTO.getDocketNo());
		deliveryChallanVO.setNoOfBoxes(deliveryChallanDTO.getNoOfBoxes());
		deliveryChallanVO.setPkgUom(deliveryChallanDTO.getPkgUom());
		deliveryChallanVO.setGrossWeight(deliveryChallanDTO.getGrossWeight());
		deliveryChallanVO.setGwtUom(deliveryChallanDTO.getGwtUom());
		deliveryChallanVO.setTransportName(deliveryChallanDTO.getTransportName());
		deliveryChallanVO.setTransporterDate(deliveryChallanDTO.getTransporterDate());
		deliveryChallanVO.setPackingSlipNo(deliveryChallanDTO.getPackingSlipNo());
		deliveryChallanVO.setBin(deliveryChallanDTO.getBin());
		deliveryChallanVO.setTaxType(deliveryChallanDTO.getTaxType());
		deliveryChallanVO.setRemarks(deliveryChallanDTO.getRemarks());

		deliveryChallanVO.setOrgId(deliveryChallanDTO.getOrgId());
		deliveryChallanVO.setCustomer(deliveryChallanDTO.getCustomer());
		deliveryChallanVO.setClient(deliveryChallanDTO.getClient());
		deliveryChallanVO.setFinYear(deliveryChallanDTO.getFinYear());
		deliveryChallanVO.setBranch(deliveryChallanDTO.getBranch());
		deliveryChallanVO.setBranchCode(deliveryChallanDTO.getBranchCode());
		deliveryChallanVO.setWarehouse(deliveryChallanDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(deliveryChallanVO.getId())) {
			List<DeliveryChallanDetailsVO> deliveryChallanDetailsVO1 = deliveryChallanDetailsRepo
					.findByDeliveryChallanVO(deliveryChallanVO);
			deliveryChallanDetailsRepo.deleteAll(deliveryChallanDetailsVO1);
		}

		List<DeliveryChallanDetailsVO> deliveryChallanDetailsVOs = new ArrayList<>();
		for (DeliveryChallanDetailsDTO deliveryChallanDetailsDTO : deliveryChallanDTO.getDeliveryChallanDetailsDTO()) {
			DeliveryChallanDetailsVO deliveryChallanDetailsVO = new DeliveryChallanDetailsVO();
			deliveryChallanDetailsVO.setPickRequestNo(deliveryChallanDetailsDTO.getPickRequestNo());
			deliveryChallanDetailsVO.setPrDate(deliveryChallanDetailsDTO.getPrDate());
			deliveryChallanDetailsVO.setPartNo(deliveryChallanDetailsDTO.getPartNo());
			deliveryChallanDetailsVO.setPartDescription(deliveryChallanDetailsDTO.getPartDescription());
			deliveryChallanDetailsVO.setOutBoundBin(deliveryChallanDetailsDTO.getOutBoundBin());
			deliveryChallanDetailsVO.setShippedQty(deliveryChallanDetailsDTO.getShippedQty());
			deliveryChallanDetailsVO.setUnitRate(deliveryChallanDetailsDTO.getUnitRate());
			deliveryChallanDetailsVO.setSkuValue(deliveryChallanDetailsDTO.getSkuValue());
			deliveryChallanDetailsVO.setDiscount(deliveryChallanDetailsDTO.getDiscount());
			deliveryChallanDetailsVO.setTax(deliveryChallanDetailsDTO.getTax());
			deliveryChallanDetailsVO.setGstTax(deliveryChallanDetailsDTO.getGstTax());
			deliveryChallanDetailsVO.setAmount(deliveryChallanDetailsDTO.getAmount());
			deliveryChallanDetailsVO.setSgst(deliveryChallanDetailsDTO.getSgst());
			deliveryChallanDetailsVO.setCgst(deliveryChallanDetailsDTO.getCgst());
			deliveryChallanDetailsVO.setIgst(deliveryChallanDetailsDTO.getIgst());
			deliveryChallanDetailsVO.setTotalGst(deliveryChallanDetailsDTO.getTotalGst());
			deliveryChallanDetailsVO.setBillAmount(deliveryChallanDetailsDTO.getBillAmount());
			deliveryChallanDetailsVO.setRemarks(deliveryChallanDetailsDTO.getRemarks());
			deliveryChallanDetailsVO.setDeliveryChallanVO(deliveryChallanVO);

			deliveryChallanDetailsVOs.add(deliveryChallanDetailsVO);
		}
		deliveryChallanVO.setDeliveryChallanDetailsVO(deliveryChallanDetailsVOs);

	}

	@Override
	public List<PickRequestVO> getAllPickRequestFromDeliveryChallan(Long orgId,String branch, String branchCode,
			String client, String warehouse) {
		return pickRequestRepo.findAllPickRequestFromDeliveryChallan(orgId, branch, branchCode, client, warehouse);

	}

	@Transactional
	public List<Map<String, Object>> getBuyerShipToBillToFromBuyerOrderForDeliveryChallan(Long orgId,String branch,String branchCode, String client,String buyerOrderNo) {

		Set<Object[]> result = buyerOrderRepo.findBuyerShipToBillToFromBuyerOrderForDeliveryChallan(orgId,branch,branchCode, client,buyerOrderNo);
		return getBuyerFromDeliveryChallan(result);
	}

	private List<Map<String, Object>> getBuyerFromDeliveryChallan(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("buyer", fs[0] != null ? fs[0].toString() : "");
			part.put("buyerShortName", fs[1] != null ? fs[1].toString() : "");
			part.put("billTo", fs[2] != null ? fs[2].toString() : "");
			part.put("shipTo", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(Long orgId,
			String branch,	String branchCode, String client,String warehouse,String buyerOrderNo) {

		Set<Object[]> result = pickRequestRepo.getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(orgId,branch, branchCode,
				client,warehouse,buyerOrderNo);
		return getAllDocidDocdatepartnofromDeliveryChallan(result);
	}

	private List<Map<String, Object>> getAllDocidDocdatepartnofromDeliveryChallan(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("docId", fs[0] != null ? fs[0].toString() : "");
			part.put("docDate", fs[1] != null ? fs[1].toString() : "");
			part.put("partno", fs[2] != null ? fs[2].toString() : "");
			part.put("partDesc", fs[3] != null ? fs[3].toString() : "");
			part.put("shippedQty", fs[4] != null ? fs[4].toString() : "");

			details1.add(part);
		}
		return details1;
	}
}
