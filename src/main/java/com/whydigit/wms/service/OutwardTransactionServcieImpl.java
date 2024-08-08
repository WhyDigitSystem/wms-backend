package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.DeliveryChallanDetailsDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.dto.VasPutawayDetailsDTO;
import com.whydigit.wms.entity.DeliveryChallanDetailsVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DeliveryChallanDetailsRepo;
import com.whydigit.wms.repo.DeliveryChallanRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.VasPutawayDetailsRepo;
import com.whydigit.wms.repo.VasPutawayRepo;

@Service
public class OutwardTransactionServcieImpl implements OutwardTransactionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionServcieImpl.class);

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

//	@Override
//	public DeliveryChallanVO getDeliveryChallanDocId(Long orgId, String finYear, String branch, String branchCode,
//			String client, String warehouse) {
//		return deliveryChallanRepo.findDeliveryChallanDocId(orgId,finYear,branch,branchCode,client,warehouse);
//	}
	
	 @Override
	    @Transactional
	    public String getDeliveryChallanDocId(Long orgId, String finYear, String branch, String branchCode,
				String client) {
          String  ScreenCode="DC";
	        String result = deliveryChallanRepo.getDeliveryChallanDocId(orgId, finYear, branchCode, client, ScreenCode);
	        return result;
	    }
	

//	
	
	@Override
	public DeliveryChallanVO updateCreateDeliveryChallan(@Valid DeliveryChallanDTO deliveryChallanDTO)
			throws ApplicationException {
		String screenCode = "DC";
		DeliveryChallanVO deliveryChallanVO = new DeliveryChallanVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(deliveryChallanDTO.getId())) {
			isUpdate = true;
			deliveryChallanVO = deliveryChallanRepo.findById(deliveryChallanDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChallan details"));
			deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
		} else {
			if (deliveryChallanRepo.existsByBuyerOrderNoAndOrgId(deliveryChallanDTO.getBuyerOrderNo(),
					deliveryChallanDTO.getOrgId())) {
				throw new ApplicationException("The given BuyerOrderNo already exists.");
			}

			deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
			deliveryChallanVO.setCreatedBy(deliveryChallanDTO.getCreatedBy());

//			GETDOCID API
			String docId = deliveryChallanRepo.getDeliveryChallanDocId(deliveryChallanDTO.getOrgId(),deliveryChallanDTO.getFinYear()
					,deliveryChallanDTO.getBranchCode(), deliveryChallanDTO.getClient(), screenCode);
			
			deliveryChallanVO.setDocId(docId);
			
			//GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(deliveryChallanDTO.getOrgId(),deliveryChallanDTO.getFinYear()
					,deliveryChallanDTO.getBranchCode(), deliveryChallanDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno()+1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

		}

		List<DeliveryChallanDetailsVO> deliveryChallanDetailsVOs = new ArrayList<>();
		if (deliveryChallanDTO.getDeliveryChallanDetailsDTO() != null) {
			for (DeliveryChallanDetailsDTO deliveryChallanDetailsDTO : deliveryChallanDTO
					.getDeliveryChallanDetailsDTO()) {
				DeliveryChallanDetailsVO deliveryChallanDetailsVO;
				if (deliveryChallanDetailsDTO.getId() != null
						&& ObjectUtils.isNotEmpty(deliveryChallanDetailsDTO.getId())) {
					deliveryChallanDetailsVO = deliveryChallanDetailsRepo.findById(deliveryChallanDetailsDTO.getId())
							.orElse(new DeliveryChallanDetailsVO());
				} else {
					deliveryChallanDetailsVO = new DeliveryChallanDetailsVO();
				}
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
		}
		getDeliveryChallanVOFromDeliveryChallanDTO(deliveryChallanDTO, deliveryChallanVO);
		deliveryChallanVO.setDeliveryChallanDetailsVO(deliveryChallanDetailsVOs);
		
		return deliveryChallanRepo.save(deliveryChallanVO);
	}

	private void getDeliveryChallanVOFromDeliveryChallanDTO(@Valid DeliveryChallanDTO deliveryChallanDTO,
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
    public String getVasPutawayDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
      String  ScreenCode="DC";
        String result = vasPutawayRepo.getVasPutawayDocId(orgId, finYear, branchCode, client, ScreenCode);
        return result;
    }

	@Override
	public VasPutawayVO updateCreateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO)
			throws ApplicationException {
		String screenCode = "VPW";
		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(vasPutawayDTO.getId())) {
			isUpdate = true;
			vasPutawayVO = vasPutawayRepo.findById(vasPutawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid VasPutaway details"));
			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
		} else {
		

			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
			vasPutawayVO.setCreatedBy(vasPutawayDTO.getCreatedBy());

//			GETDOCID API
			String docId = vasPutawayRepo.getVasPutawayDocId(vasPutawayDTO.getOrgId(),vasPutawayDTO.getFinYear()
					,vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(), screenCode);
			
			vasPutawayVO.setDocId(docId);
			
			//GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(vasPutawayDTO.getOrgId(),vasPutawayDTO.getFinYear()
					,vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno()+1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

		}

		List<VasPutawayDetailsVO> vasPutawayDetailsVOs = new ArrayList<>();
		if (vasPutawayDTO.getVasPutawayDetailsDTO() != null) {
			for (VasPutawayDetailsDTO vasPutawayDetailsDTO : vasPutawayDTO
					.getVasPutawayDetailsDTO()) {
				VasPutawayDetailsVO vasPutawayDetailsVO;
				if (vasPutawayDetailsDTO.getId() != null
						&& ObjectUtils.isNotEmpty(vasPutawayDetailsDTO.getId())) {
					vasPutawayDetailsVO = vasPutawayDetailsRepo.findById(vasPutawayDetailsDTO.getId())
							.orElse(new VasPutawayDetailsVO());
				} else {
					vasPutawayDetailsVO = new VasPutawayDetailsVO();
				}
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
				vasPutawayDetailsVO.setVasPutawayVO(vasPutawayVO);;
				
				vasPutawayDetailsVOs.add(vasPutawayDetailsVO);
			}
		}
		getVasPutawayVOFromVasPutawayDTO(vasPutawayDTO, vasPutawayVO);
		vasPutawayVO.setVasPutawayDetailsVO(vasPutawayDetailsVOs);;
		
		return vasPutawayRepo.save(vasPutawayVO);
	}

	private void getVasPutawayVOFromVasPutawayDTO(@Valid VasPutawayDTO vasPutawayDTO,
			VasPutawayVO vasPutawayVO) {
		vasPutawayVO.setTotalGrnQty(vasPutawayDTO.getTotalGrnQty());
		vasPutawayVO.setTotalPutawayQty(vasPutawayDTO.getTotalPutawayQty());
		vasPutawayVO.setVasPickNo(vasPutawayDTO.getVasPickNo());
		vasPutawayVO.setStatus(vasPutawayDTO.getStatus());
		if(vasPutawayDTO.getStatus()=="Submit") {
			vasPutawayVO.setFreeze(true);
		}
		vasPutawayVO.setOrgId(vasPutawayDTO.getOrgId());
		vasPutawayVO.setCustomer(vasPutawayDTO.getCustomer());
		vasPutawayVO.setClient(vasPutawayDTO.getClient());
		vasPutawayVO.setFinYear(vasPutawayDTO.getFinYear());
		vasPutawayVO.setBranch(vasPutawayDTO.getBranch());
		vasPutawayVO.setBranchCode(vasPutawayDTO.getBranchCode());
		vasPutawayVO.setWarehouse(vasPutawayDTO.getWarehouse());
	}

	
}
