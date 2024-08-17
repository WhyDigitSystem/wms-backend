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

import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.BuyerOrderDetailsDTO;
import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.DeliveryChallanDetailsDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.dto.VasPutawayDetailsDTO;
import com.whydigit.wms.entity.BuyerOrderDetailsVO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.DeliveryChallanDetailsVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderDetailsRepo;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.DeliveryChallanDetailsRepo;
import com.whydigit.wms.repo.DeliveryChallanRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.PickRequestRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.VasPutawayDetailsRepo;
import com.whydigit.wms.repo.VasPutawayRepo;

@Service
public class OutwardTransactionServcieImpl implements OutwardTransactionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(OutwardTransactionServcieImpl.class);

	@Autowired
	DeliveryChallanRepo deliveryChallanRepo;

	@Autowired
	DeliveryChallanDetailsRepo deliveryChallanDetailsRepo;

	@Autowired
	VasPutawayRepo vasPutawayRepo;

	@Autowired
	VasPutawayDetailsRepo vasPutawayDetailsRepo;

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
			deliveryChallanDetailsVO.setQcFlags(deliveryChallanDetailsDTO.getQcFlags());

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
	// VASPutaway

	@Override
	public List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return vasPutawayRepo.findAllVasPutaway(orgId, finYear, branch, branchCode, client, warehouse);

	}

	@Override
	public VasPutawayVO getVasPutawayById(Long id) {
		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  VasPutaway BY Id : {}", id);
			vasPutawayVO = vasPutawayRepo.findDeliveryChallanById(id);
		} else {
			LOGGER.info("failed Received  VasPutaway BY Id : {}", id);

		}
		return vasPutawayVO;
	}

	@Override
	@Transactional
	public String getVasPutawayDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "DC";
		String result = vasPutawayRepo.getVasPutawayDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO) throws ApplicationException {

		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		String screenCode = "VPW";
		String message;

		if (ObjectUtils.isNotEmpty(vasPutawayDTO.getId())) {
			vasPutawayVO = vasPutawayRepo.findById(vasPutawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChallan details"));
			createUpdateVasPutawayVOByVasPutawayDTO(vasPutawayDTO, vasPutawayVO);
			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
			message = "DeliveryChallan updated Successfully";
			

		} else {
			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
			vasPutawayVO.setCreatedBy(vasPutawayDTO.getCreatedBy());
//			GETDOCID API
			String docId = vasPutawayRepo.getVasPutawayDocId(vasPutawayDTO.getOrgId(), vasPutawayDTO.getFinYear(),
					vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(), screenCode);
			vasPutawayVO.setDocId(docId);

			createUpdateVasPutawayVOByVasPutawayDTO(vasPutawayDTO, vasPutawayVO);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(vasPutawayDTO.getOrgId(),
							vasPutawayDTO.getFinYear(), vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "VasPutaway updated Successfully";
			
		}
		VasPutawayVO savedVasPutawayVO = vasPutawayRepo.save(vasPutawayVO);

		List<VasPutawayDetailsVO> vasPutawayVOLists = savedVasPutawayVO.getVasPutawayDetailsVO();
		if (vasPutawayVOLists != null && !vasPutawayVOLists.isEmpty()) {
			for (VasPutawayDetailsVO vasPutawayDetailsVO : vasPutawayVOLists) {
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();

				stockDetailsVOFrom.setRefNo(vasPutawayVO.getDocId());
				stockDetailsVOFrom.setRefDate(vasPutawayVO.getDocDate());
				stockDetailsVOFrom.setDocId(vasPutawayVO.getVasPickNo());
				stockDetailsVOFrom.setStatus(vasPutawayVO.getStatus());
				stockDetailsVOFrom.setOrgId(vasPutawayVO.getOrgId());
				stockDetailsVOFrom.setCustomer(vasPutawayVO.getCustomer());
				stockDetailsVOFrom.setClient(vasPutawayVO.getClient());
				stockDetailsVOFrom.setCreatedBy(vasPutawayVO.getUpdatedBy());
				stockDetailsVOFrom.setFinYear(vasPutawayVO.getFinYear());
				stockDetailsVOFrom.setBranch(vasPutawayVO.getBranch());
				stockDetailsVOFrom.setBranchCode(vasPutawayVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(vasPutawayVO.getWarehouse());
				stockDetailsVOFrom.setSourceScreenCode(vasPutawayVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(vasPutawayVO.getScreenName());
				stockDetailsVOFrom.setSourceId(vasPutawayVO.getId());
				stockDetailsVOFrom.setBinClass(vasPutawayDetailsVO.getBinClass());
				stockDetailsVOFrom.setCellType(vasPutawayDetailsVO.getCellType());
				stockDetailsVOFrom.setClientCode(vasPutawayDetailsVO.getClientCode());
				stockDetailsVOFrom.setCore(vasPutawayDetailsVO.getCore());
				stockDetailsVOFrom.setStatus(vasPutawayDetailsVO.getStatus());
				stockDetailsVOFrom.setExpDate(vasPutawayDetailsVO.getExpDate());
				stockDetailsVOFrom.setPcKey(vasPutawayDetailsVO.getPckey());
				stockDetailsVOFrom.setSSku(vasPutawayDetailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(vasPutawayDetailsVO.getStockDate());
				stockDetailsVOFrom.setPartno(vasPutawayDetailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(vasPutawayDetailsVO.getPartDescription());
				stockDetailsVOFrom.setGrnNo(vasPutawayDetailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(vasPutawayDetailsVO.getGrnDate());
				stockDetailsVOFrom.setInvQty(vasPutawayDetailsVO.getInvQty());
				stockDetailsVOFrom.setSsQty(vasPutawayDetailsVO.getPutAwayQty() *-1); // NEGATIVE QUANTITY
				stockDetailsVOFrom.setBin(vasPutawayDetailsVO.getBin());
				stockDetailsVOFrom.setBin(vasPutawayDetailsVO.getFromBin());
				stockDetailsVOFrom.setSku(vasPutawayDetailsVO.getSku());
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
//				

				stockDetailsVOTo.setRefNo(vasPutawayVO.getDocId());
				stockDetailsVOTo.setRefDate(vasPutawayVO.getDocDate());
				stockDetailsVOTo.setDocId(vasPutawayVO.getVasPickNo());
				stockDetailsVOTo.setStatus(vasPutawayVO.getStatus());
				stockDetailsVOTo.setOrgId(vasPutawayVO.getOrgId());
				stockDetailsVOTo.setCustomer(vasPutawayVO.getCustomer());
				stockDetailsVOTo.setClient(vasPutawayVO.getClient());
				stockDetailsVOTo.setCreatedBy(vasPutawayVO.getUpdatedBy());
				stockDetailsVOTo.setFinYear(vasPutawayVO.getFinYear());
				stockDetailsVOTo.setBranch(vasPutawayVO.getBranch());
				stockDetailsVOTo.setBranchCode(vasPutawayVO.getBranchCode());
				stockDetailsVOTo.setWarehouse(vasPutawayVO.getWarehouse());
				stockDetailsVOTo.setSourceScreenCode(vasPutawayVO.getScreenCode());
				stockDetailsVOTo.setSourceScreenName(vasPutawayVO.getScreenName());
				stockDetailsVOTo.setSourceId(vasPutawayVO.getId());
				stockDetailsVOTo.setBinClass(vasPutawayDetailsVO.getBinClass());
				stockDetailsVOTo.setCellType(vasPutawayDetailsVO.getCellType());
				stockDetailsVOTo.setClientCode(vasPutawayDetailsVO.getClientCode());
				stockDetailsVOTo.setCore(vasPutawayDetailsVO.getCore());
				stockDetailsVOTo.setExpDate(vasPutawayDetailsVO.getExpDate());
				stockDetailsVOTo.setPcKey(vasPutawayDetailsVO.getPckey());
				stockDetailsVOTo.setSSku(vasPutawayDetailsVO.getSsku());
				stockDetailsVOTo.setStatus(vasPutawayDetailsVO.getStatus());
				stockDetailsVOTo.setStockDate(vasPutawayDetailsVO.getStockDate());
				stockDetailsVOTo.setPartno(vasPutawayDetailsVO.getPartNo());
				stockDetailsVOTo.setPartDesc(vasPutawayDetailsVO.getPartDescription());
				stockDetailsVOTo.setGrnNo(vasPutawayDetailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(vasPutawayDetailsVO.getGrnDate());
				stockDetailsVOTo.setInvQty(vasPutawayDetailsVO.getInvQty());
				stockDetailsVOTo.setSsQty(vasPutawayDetailsVO.getPutAwayQty() ); // POSITIVE QUANTITY
				stockDetailsVOTo.setBin(vasPutawayDetailsVO.getBin());
				stockDetailsVOTo.setBin(vasPutawayDetailsVO.getFromBin());
				stockDetailsVOTo.setSku(vasPutawayDetailsVO.getSku());
				stockDetailsRepo.save(stockDetailsVOTo);

			}
		}
		
		Map<String, Object> response = new HashMap<>();
		response.put("vasPutawayVO", vasPutawayVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateVasPutawayVOByVasPutawayDTO(@Valid VasPutawayDTO vasPutawayDTO,
			VasPutawayVO vasPutawayVO) {
		vasPutawayVO.setTotalGrnQty(vasPutawayDTO.getTotalGrnQty());
		vasPutawayVO.setTotalPutawayQty(vasPutawayDTO.getTotalPutawayQty());
		vasPutawayVO.setVasPickNo(vasPutawayDTO.getVasPickNo());
		vasPutawayVO.setStatus(vasPutawayDTO.getStatus());
		if (vasPutawayDTO.getStatus() == "Submit") {
			vasPutawayVO.setFreeze(true);
		}
		vasPutawayVO.setOrgId(vasPutawayDTO.getOrgId());
		vasPutawayVO.setCustomer(vasPutawayDTO.getCustomer());
		vasPutawayVO.setClient(vasPutawayDTO.getClient());
		vasPutawayVO.setFinYear(vasPutawayDTO.getFinYear());
		vasPutawayVO.setBranch(vasPutawayDTO.getBranch());
		vasPutawayVO.setBranchCode(vasPutawayDTO.getBranchCode());
		vasPutawayVO.setWarehouse(vasPutawayDTO.getWarehouse());

		
		if (ObjectUtils.isNotEmpty(vasPutawayVO.getId())) {
			List<VasPutawayDetailsVO> vasPutawayDetailsVO1 = vasPutawayDetailsRepo
					.findByVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsRepo.deleteAll(vasPutawayDetailsVO1);
		}


		List<VasPutawayDetailsVO> vasPutawayDetailsVOs = new ArrayList<>();
		for (VasPutawayDetailsDTO vasPutawayDetailsDTO : vasPutawayDTO.getVasPutawayDetailsDTO()) {
			VasPutawayDetailsVO vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO.setPartNo(vasPutawayDetailsDTO.getPartNo());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setGrnNo(vasPutawayDetailsDTO.getGrnNo());
			vasPutawayDetailsVO.setGrnDate(vasPutawayDetailsDTO.getGrnDate());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setInvQty(vasPutawayDetailsDTO.getInvQty());
			vasPutawayDetailsVO.setPutAwayQty(vasPutawayDetailsDTO.getPutAwayQty());
			vasPutawayDetailsVO.setFromBin(vasPutawayDetailsDTO.getFromBin());
			vasPutawayDetailsVO.setBin(vasPutawayDetailsDTO.getBin());
			vasPutawayDetailsVO.setSku(vasPutawayDetailsDTO.getSku());
			vasPutawayDetailsVO.setRemarks(vasPutawayDetailsDTO.getRemarks());
			vasPutawayDetailsVO.setQcFlags(vasPutawayDetailsDTO.getQcFlags());
			vasPutawayDetailsVO.setBinClass(vasPutawayDetailsDTO.getBinClass());
			vasPutawayDetailsVO.setCellType(vasPutawayDetailsDTO.getCellType());
			vasPutawayDetailsVO.setClientCode(vasPutawayDetailsDTO.getClientCode());
			vasPutawayDetailsVO.setStatus(vasPutawayDetailsDTO.getStatus());
			vasPutawayDetailsVO.setCore(vasPutawayDetailsDTO.getCore());
			vasPutawayDetailsVO.setExpDate(vasPutawayDetailsDTO.getExpDate());
			vasPutawayDetailsVO.setPckey(vasPutawayDetailsDTO.getPckey());
			vasPutawayDetailsVO.setSsku(vasPutawayDetailsDTO.getSsku());
			vasPutawayDetailsVO.setStockDate(vasPutawayDetailsDTO.getStockDate());
			
			vasPutawayDetailsVO.setVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsVOs.add(vasPutawayDetailsVO);
		}
		vasPutawayVO.setVasPutawayDetailsVO(vasPutawayDetailsVOs);

	}
	
	
	@Transactional
	public List<Map<String, Object>> getDocIdFromVasPickForVasPutaway(Long orgId,String branch, String client) {

		Set<Object[]> result = vasPutawayRepo.findDocIdFromVasPickForVasPutaway(orgId,branch, client);
		return getVasPutaway(result);
	}

	private List<Map<String, Object>> getVasPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("vasPickNo", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Transactional
	public List<Map<String, Object>> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId,String branch, String client,String docId) {

		Set<Object[]> result = vasPutawayDetailsRepo.getAllDetailsFromVasPickDetailsForVasPutawayDetails(orgId,branch, client ,docId);
		return getVasPutawayDetails(result);
	}

	private List<Map<String, Object>> getVasPutawayDetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDescription", fs[1] != null ? fs[1].toString() : "");
			part.put("grnNo ", fs[2] != null ? fs[2].toString() : "");
			part.put("pickOty", fs[3] != null ? fs[3].toString() : "");
			part.put("bin", fs[4] != null ? fs[4].toString() : "");
			part.put("sku", fs[5] != null ? fs[5].toString() : "");

			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getAllFillGridFromVasPutaway(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = vasPutawayDetailsRepo.getAllFillGridFromVasPutaway(orgId, branch, branchCode,
				client);
		return getAllFillGridVasPutawayResult(result);
	}
	private List<Map<String, Object>> getAllFillGridVasPutawayResult(Set<Object[]> result) {
    List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("cellType", fs[2] != null ? fs[2].toString() : "");
			part.put("clientCode", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("expDate", fs[5] != null ? fs[5].toString() : "");
			part.put("pcKey", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockDate", fs[8] != null ? fs[8].toString() : "");
			part.put("partNo", fs[9] != null ? fs[9].toString() : "");
			part.put("partDesc", fs[10] != null ? fs[10].toString() : "");
			part.put("sku", fs[11] != null ? fs[11].toString() : "");
			part.put("grnNo", fs[12] != null ? fs[12].toString() : "");
			part.put("grnDate", fs[13] != null ? fs[13].toString() : "");
			part.put("picqty", fs[14] != null ? fs[14].toString() : "");
			part.put("avlqty", fs[15] != null ? fs[15].toString() : "");


			details1.add(part);
		}
		return details1;
	}
	
	@Override
	public int getAvlQtyVasPutaway(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
			String partDesc) {
		Set<Object[]> getAvlQtyVasPutaway = vasPutawayDetailsRepo.getAvlQtyVasPutaway(orgId, client, branchCode, warehouse, branch, partNo,
				partDesc);
		return calculateTotalQtyVasPutaway(getAvlQtyVasPutaway);
	}

	private int calculateTotalQtyVasPutaway(Set<Object[]> getAvlQtyVasPutaway) {
		int totalQty = 0;
		for (Object[] qt : getAvlQtyVasPutaway) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}

	// BuyerOrder

	@Override
	public Map<String, Object> createUpdateBuyerOrder(BuyerOrderDTO buyerOrderDTO) throws ApplicationException {
        String screenCode="BO";
		BuyerOrderVO buyerOrderVO;
		String message = null;

		if (ObjectUtils.isEmpty(buyerOrderDTO.getId())) {

			if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndBranchCodeAndCustomer(buyerOrderDTO.getOrderNo(),
					buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(), buyerOrderDTO.getBranchCode(),
					buyerOrderDTO.getCustomer())) {
				String errorMessage = String.format("This orderNo:%s Already Exists This Organization.",
						buyerOrderDTO.getOrderNo());
			}

			buyerOrderVO = new BuyerOrderVO();
			
		//	GETDOCID API
			String docId = buyerOrderRepo.getbuyerOrderDocId(buyerOrderDTO.getOrgId(), buyerOrderDTO.getFinYear(),
					buyerOrderDTO.getBranchCode(), buyerOrderDTO.getClient(), screenCode);
			buyerOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(buyerOrderDTO.getOrgId(),
							buyerOrderDTO.getFinYear(), buyerOrderDTO.getBranchCode(), buyerOrderDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			
			buyerOrderVO.setCreatedBy(buyerOrderDTO.getCreatedBy());
			buyerOrderVO.setUpdatedBy(buyerOrderDTO.getCreatedBy());

			message = "BuyerOrder Creation Successfully";
		} else {
			buyerOrderVO = buyerOrderRepo.findById(buyerOrderDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Not Found Any Informations,Invalid Id" + buyerOrderDTO.getId()));
			buyerOrderVO.setUpdatedBy(buyerOrderDTO.getCreatedBy());

			if (!buyerOrderVO.getOrderNo().equalsIgnoreCase(buyerOrderDTO.getOrderNo())) {

				if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndBranchCodeAndCustomer(buyerOrderDTO.getOrderNo(),
						buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(), buyerOrderDTO.getBranchCode(),
						buyerOrderDTO.getCustomer())) {
					String errorMessage = String.format("This orderNo:%s Already Exists This Organization.",
							buyerOrderDTO.getOrderNo());
				}
				buyerOrderVO.setOrderNo(buyerOrderDTO.getOrderNo());
			}
			message = "BuyerOrder Updation Successfully";
		}

		getBuyerOrderVOfromBuyerOrderDTO(buyerOrderVO, buyerOrderDTO);
		buyerOrderRepo.save(buyerOrderVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("buyerOrderVO", buyerOrderVO);
		return response;

	}

	private BuyerOrderVO getBuyerOrderVOfromBuyerOrderDTO(BuyerOrderVO buyerOrderVO, BuyerOrderDTO buyerOrderDTO) {
		buyerOrderVO.setOrderNo(buyerOrderDTO.getOrderNo());
		buyerOrderVO.setOrgId(buyerOrderDTO.getOrgId());
		buyerOrderVO.setOrderDate(buyerOrderDTO.getOrderDate());
		buyerOrderVO.setInvoiceNo(buyerOrderDTO.getInvoiceNo());
		buyerOrderVO.setRefDate(buyerOrderDTO.getRefDate());
		buyerOrderVO.setBuyerShortName(buyerOrderDTO.getBuyerShortName());
		buyerOrderVO.setCurrency(buyerOrderDTO.getCurrency());
		buyerOrderVO.setExRate(buyerOrderDTO.getExRate());
		buyerOrderVO.setBin(buyerOrderDTO.getBin());
		buyerOrderVO.setBillto(buyerOrderDTO.getBillto());
		buyerOrderVO.setTax(buyerOrderDTO.getTax());
		buyerOrderVO.setShipTo(buyerOrderDTO.getShipTo());
		buyerOrderVO.setReMarks(buyerOrderDTO.getReMarks());
		buyerOrderVO.setCreatedBy(buyerOrderDTO.getCreatedBy());
		buyerOrderVO.setCompany(buyerOrderDTO.getCompany());
		buyerOrderVO.setCancel(buyerOrderDTO.isCancel());
		buyerOrderVO.setInvoiceDate(buyerOrderDTO.getInvoiceDate());
		buyerOrderVO.setRefNo(buyerOrderDTO.getRefNo());
		buyerOrderVO.setCancelRemark(buyerOrderDTO.getCancelRemark());
//		buyerOrderVO.setScreenCode(buyerOrderDTO.getScreenCode());
		buyerOrderVO.setScreenName(buyerOrderDTO.getScreenName());
		buyerOrderVO.setCustomer(buyerOrderDTO.getCustomer());
		buyerOrderVO.setClient(buyerOrderDTO.getClient());
		buyerOrderVO.setFinYear(buyerOrderDTO.getFinYear());
		buyerOrderVO.setBranch(buyerOrderDTO.getBranch());
		buyerOrderVO.setBranchCode(buyerOrderDTO.getBranchCode());
		buyerOrderVO.setFreeze(buyerOrderDTO.isFreeze());

		if (buyerOrderDTO.getId() != null) {

			List<BuyerOrderDetailsVO> detailsVOs = buyerOrderDetailsRepo.findByBuyerOrderVO(buyerOrderVO);
			buyerOrderDetailsRepo.deleteAll(detailsVOs);
		}
		
		
         int orderQty=0;
		 int avilQty=0;
		
		
		List<BuyerOrderDetailsVO> detailsVOList = new ArrayList<BuyerOrderDetailsVO>();
		for (BuyerOrderDetailsDTO buyerOrderDetailsDTO : buyerOrderDTO.getBuyerOrderDetailsDTO()) {

			BuyerOrderDetailsVO detailsVO = new BuyerOrderDetailsVO();
			detailsVO.setPartNo(buyerOrderDetailsDTO.getPartNo());
			detailsVO.setPartDesc(buyerOrderDetailsDTO.getPartDesc());
			detailsVO.setQty(buyerOrderDetailsDTO.getQty());
			detailsVO.setBatchNo(buyerOrderDetailsDTO.getBatchNo());
			detailsVO.setAvailQty(buyerOrderDetailsDTO.getAvailQty());
			detailsVO.setSku(buyerOrderDetailsDTO.getSku());
			detailsVO.setReMarks(buyerOrderDetailsDTO.getRemarks());
			detailsVO.setQcflag(buyerOrderDetailsDTO.isQcflag());
			
			avilQty=avilQty+buyerOrderDetailsDTO.getAvailQty();
			orderQty=orderQty+buyerOrderDetailsDTO.getQty();
			
			detailsVO.setBuyerOrderVO(buyerOrderVO);
			detailsVOList.add(detailsVO);
		}
		buyerOrderVO.setOrderQty(orderQty);
		buyerOrderVO.setAvilQty(avilQty);
		buyerOrderVO.setBuyerOrderDetailsVO(detailsVOList);
		return buyerOrderVO;

	}

	@Override
	public List<BuyerOrderVO> getAllBuyerOrderByOrgId(Long orgId) {

		return buyerOrderRepo.findAllBuyerOrderByOrgId(orgId);
	}

	@Override
	public List<BuyerOrderVO> getAllBuyerOrderById(Long id) {

		return buyerOrderRepo.findAllBuyerOrderById(id);
	}


	@Override
	public String getBuyerOrderDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
		String ScreenCode = "BO";
		String result = buyerOrderRepo.getbuyerOrderDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
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

	@Override
	public List<Map<String, Object>> getBoSkuDetails(Long orgId, String branchCode, String client, String batch,
			String warehouse) {
		Set<Object[]> result = buyerOrderRepo.getBoSku(orgId,branchCode,client,batch,warehouse);
		return getAllSkuDetails(result);
	}

	private List<Map<String, Object>> getAllSkuDetails(Set<Object[]> result) {
		 List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("partNo", fs[0] != null ? fs[0].toString() : "");
				part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
				part.put("batch", fs[2] != null ? fs[2].toString() : "");
				part.put("sqty", fs[3] != null ? Integer.parseInt(fs[3].toString()):0);

				details1.add(part);
			}
			return details1;

}

	@Override
	public List<Map<String, Object>> getAvlQtyByBO(Long orgId, String client, String branchCode, String warehouse,
			String branch, String partNo, String partDesc,String batch) {
		Set<Object[]> result = buyerOrderRepo.getAvilableQty(orgId,client,branchCode,warehouse,branch,partNo,partDesc,batch);
		return getAvlQty(result);
	}

	private List<Map<String, Object>> getAvlQty(Set<Object[]> result) {
		 List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("avlQty", fs[0] != null ? Integer.parseInt(fs[0].toString()):0);

				details1.add(part);
			}
			return details1;

}

}