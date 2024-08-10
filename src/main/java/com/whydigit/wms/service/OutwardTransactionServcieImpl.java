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
import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.CodeConversionDetailsDTO;
import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.DeliveryChallanDetailsDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.dto.VasPutawayDetailsDTO;
import com.whydigit.wms.entity.BuyerOrderDetailsVO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DeliveryChallanDetailsVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BuyerOrderDetailsRepo;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.DeliveryChallanDetailsRepo;
import com.whydigit.wms.repo.DeliveryChallanRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
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
		deliveryChallanVO.setDate(deliveryChallanDTO.getDate());
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
			deliveryChallanDetailsVO.setGatTax(deliveryChallanDetailsDTO.getGatTax());
			deliveryChallanDetailsVO.setAmount(deliveryChallanDetailsDTO.getAmount());
			deliveryChallanDetailsVO.setSgst(deliveryChallanDetailsDTO.getSgst());
			deliveryChallanDetailsVO.setCgst(deliveryChallanDetailsDTO.getCgst());
			deliveryChallanDetailsVO.setTotalGst(deliveryChallanDetailsDTO.getTotalGst());
			deliveryChallanDetailsVO.setBillAmount(deliveryChallanDetailsDTO.getBillAmount());
			deliveryChallanDetailsVO.setRemarks(deliveryChallanDetailsDTO.getRemarks());
			deliveryChallanDetailsVO.setQcFlags(deliveryChallanDetailsDTO.isQcFlags());

			deliveryChallanDetailsVO.setDeliveryChallanVO(deliveryChallanVO);

			deliveryChallanDetailsVOs.add(deliveryChallanDetailsVO);
		}
		deliveryChallanVO.setDeliveryChallanDetailsVO(deliveryChallanDetailsVOs);

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

		if (vasPutawayDTO.getId() != null) {
			List<VasPutawayDetailsVO> vasPutawayDetailsVO1 = vasPutawayDetailsRepo.findByVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsRepo.deleteAll(vasPutawayDetailsVO1);
		}
		

		List<VasPutawayDetailsVO> vasPutawayDetailsVOs = new ArrayList<>();
		for (VasPutawayDetailsDTO vasPutawayDetailsDTO : vasPutawayDTO.getVasPutawayDetailsDTO()) {
			VasPutawayDetailsVO vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO.setPartNo(vasPutawayDetailsDTO.getPartNo());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setGrnNo(vasPutawayDetailsDTO.getGrnNo());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setInvQty(vasPutawayDetailsDTO.getInvQty());
			vasPutawayDetailsVO.setPutAwayQty(vasPutawayDetailsDTO.getPutAwayQty());
			vasPutawayDetailsVO.setFromBin(vasPutawayDetailsDTO.getFromBin());
			vasPutawayDetailsVO.setBin(vasPutawayDetailsDTO.getBin());
			vasPutawayDetailsVO.setSku(vasPutawayDetailsDTO.getSku());
			vasPutawayDetailsVO.setRemarks(vasPutawayDetailsDTO.getRemarks());
			vasPutawayDetailsVO.setQcFlags(vasPutawayDetailsDTO.isQcFlags());
			vasPutawayDetailsVO.setVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsVOs.add(vasPutawayDetailsVO);
		}
		vasPutawayVO.setVasPutawayDetailsVO(vasPutawayDetailsVOs);

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
		buyerOrderVO.setDocDate(buyerOrderDTO.getDocDate());
		buyerOrderVO.setOrderDate(buyerOrderDTO.getOrderDate());
		buyerOrderVO.setInvoiceNo(buyerOrderDTO.getInvoiceNo());
		buyerOrderVO.setRefDate(buyerOrderDTO.getRefDate());
		buyerOrderVO.setBuyerShortName(buyerOrderDTO.getBuyerShortName());
		buyerOrderVO.setCurrency(buyerOrderDTO.getCurrency());
		buyerOrderVO.setExRate(buyerOrderDTO.getExRate());
		buyerOrderVO.setLocation(buyerOrderDTO.getLocation());
		buyerOrderVO.setBillto(buyerOrderDTO.getBillto());
		buyerOrderVO.setTax(buyerOrderDTO.getTax());
		buyerOrderVO.setShipTo(buyerOrderDTO.getShipTo());
		buyerOrderVO.setReMarks(buyerOrderDTO.getReMarks());
		buyerOrderVO.setCreatedBy(buyerOrderDTO.getCreatedBy());
		buyerOrderVO.setCompany(buyerOrderDTO.getCompany());
		buyerOrderVO.setCancel(buyerOrderDTO.isCancel());
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
			detailsVO.setBuyerOrderVO(buyerOrderVO);
			detailsVOList.add(detailsVO);
		}
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
	public int getAvlQty(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
			String partDesc) {
		Set<Object[]> getAvlQty = stockDetailsRepo.getQtyDetais(orgId, client, branchCode, warehouse, branch, partNo,
				partDesc);
		return calculateTotalQty(getAvlQty);
	}

	private int calculateTotalQty(Set<Object[]> getAvlQty) {
		int totalQty = 0;
		for (Object[] qt : getAvlQty) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}

	@Override
	public String getBuyerOrderDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
		String ScreenCode = "BO";
		String result = buyerOrderRepo.getbuyerOrderDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

}
