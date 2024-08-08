package com.whydigit.wms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DeliveryChallanDetailsRepo;
import com.whydigit.wms.repo.DeliveryChallanRepo;
import com.whydigit.wms.repo.VasPutawayDetailsRepo;
import com.whydigit.wms.repo.VasPutawayRepo;

@Service
public class OutwardTransactionServcieImpl implements OutwardTransactionService{

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionServcieImpl.class);

	@Autowired
	DeliveryChallanRepo deliveryChallanRepo;

	@Autowired
	DeliveryChallanDetailsRepo deliveryChallanDetailsRepo;

	@Autowired
	VasPutawayRepo vasPutawayRepo;

	@Autowired
	VasPutawayDetailsRepo vasPutawayDetailsRepo;

	
	// DeliveryChallan

		@Override
		public List<DeliveryChallanVO> getAllDeliveryChallan(Long orgId, String finYear, String branch, String branchCode,
				String client, String warehouse) {
				return deliveryChallanRepo.findAllDeliveryChallan(orgId, finYear, branch, branchCode, client,
						warehouse);

		}

		@Override
		public List<DeliveryChallanVO> getDeliveryChallanById(Long id) {
			List<DeliveryChallanVO> deliveryChallanVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(id)) {
				LOGGER.info("Successfully Received  DeliveryChallan BY Id : {}", id);
				deliveryChallanVO = deliveryChallanRepo.findDeliveryChallanById(id);
			} else {
				LOGGER.info("failed Received  DeliveryChallan For All Id.");
			}
			return deliveryChallanVO;
		}
		
		@Override
		public DeliveryChallanVO updateCreateDeliveryChallan(
				@Valid DeliveryChallanDTO deliveryChallanDTO) throws ApplicationException {
			DeliveryChallanVO deliveryChallanVO = new DeliveryChallanVO();
			boolean isUpdate = false;
			if (ObjectUtils.isNotEmpty(deliveryChallanDTO.getId())) {
				isUpdate = true;
				deliveryChallanVO = deliveryChallanRepo.findById(deliveryChallanDTO.getId())
						.orElseThrow(() -> new ApplicationException("Invalid DeliveryChallan details"));
				deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
			} else {
				  if (deliveryChallanRepo.existsByBuyerOrderNoAndOrgId(deliveryChallanDTO.getBuyerOrderNo(), deliveryChallanDTO.getOrgId())) {
			            throw new ApplicationException("The given BuyerOrderNo already exists.");
			        }
			        
				deliveryChallanVO.setUpdatedBy(deliveryChallanDTO.getCreatedBy());
				deliveryChallanVO.setCreatedBy(deliveryChallanDTO.getCreatedBy());
			}

			List<DeliveryChallanDetailsVO> deliveryChallanDetailsVOs = new ArrayList<>();
			if (deliveryChallanDTO.getDeliveryChallanDetailsDTO() != null) {
				for (DeliveryChallanDetailsDTO deliveryChallanDetailsDTO : deliveryChallanDTO
						.getDeliveryChallanDetailsDTO()) {
					DeliveryChallanDetailsVO deliveryChallanDetailsVO;
					if (deliveryChallanDetailsDTO.getId() != null
							&& ObjectUtils.isNotEmpty(deliveryChallanDetailsDTO.getId())) {
						deliveryChallanDetailsVO = deliveryChallanDetailsRepo
								.findById(deliveryChallanDetailsDTO.getId()).orElse(new DeliveryChallanDetailsVO());
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

					deliveryChallanDetailsVO.setDeliveryChallanVO(deliveryChallanVO);;
					deliveryChallanDetailsVOs.add(deliveryChallanDetailsVO);
				}
			}
			getDeliveryChallanVOFromDeliveryChallanDTO(deliveryChallanDTO, deliveryChallanVO);
			deliveryChallanVO.setDeliveryChallanDetailsVO(deliveryChallanDetailsVOs);;
			return deliveryChallanRepo.save(deliveryChallanVO);
		}
		private void getDeliveryChallanVOFromDeliveryChallanDTO(
				@Valid DeliveryChallanDTO deliveryChallanDTO, DeliveryChallanVO deliveryChallanVO) {

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
			deliveryChallanVO.setScreenName("DELIVERY CHALLAN");
			deliveryChallanVO.setScreenCode("DC");
			deliveryChallanVO.setDocDate(LocalDate.now());
			deliveryChallanVO.setCancel(true);
			deliveryChallanVO.setActive(false);
		}

		
		
		//VASPutaway
		
		@Override
		public List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode,
				String client, String warehouse) {
				return vasPutawayRepo.findAllVasPutaway(orgId, finYear, branch, branchCode, client,
						warehouse);

		}
		
		@Override
		public List<VasPutawayVO> getVasPutawayById(Long id) {
			List<VasPutawayVO> vasPutawayVO = new ArrayList<>();
			if (ObjectUtils.isNotEmpty(id)) {
				LOGGER.info("Successfully Received  VasPutaway BY Id : {}", id);
				vasPutawayVO = vasPutawayRepo.findDeliveryChallanById(id);
			} else {
				LOGGER.info("failed Received  VasPutaway BY Id : {}", id);
			}
			return vasPutawayVO;
		}
}
