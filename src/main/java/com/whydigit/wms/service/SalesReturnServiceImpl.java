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

import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.SalesReturnDetailsRepo;
import com.whydigit.wms.repo.SalesReturnRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

public class SalesReturnServiceImpl implements SalesReturnService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SalesReturnServiceImpl.class);

	
	@Autowired
	SalesReturnRepo salesReturnRepo;

	@Autowired
	SalesReturnDetailsRepo salesReturnDetailsRepo;
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Override
	public List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return salesReturnRepo.findAllSalesReturn(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public SalesReturnVO getSalesReturnById(Long id) {
		SalesReturnVO salesReturnVO = new SalesReturnVO();
		
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  SalesReturn BY Id : {}", id);
			salesReturnVO = salesReturnRepo.findSalesReturnById(id);
		} else {
			LOGGER.info("failed Received SalesReturn For All Id.");
		}
		return salesReturnVO;

	}

	@Override
	public Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException {

		SalesReturnVO salesReturnVO = new SalesReturnVO();
		String screenCode = "SR";
		String message;

		if (ObjectUtils.isNotEmpty(salesReturnDTO.getId())) {
			salesReturnVO = salesReturnRepo.findById(salesReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("SalesReturn not found"));

			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);
			message = "SalesReturn Updated Successfully";
		} else {
			salesReturnVO.setCreatedBy(salesReturnDTO.getCreatedBy());
			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());

			String salesReturnDocId = salesReturnRepo.getSalesReturnDocId(salesReturnDTO.getOrgId(),
					salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
					screenCode);
			salesReturnVO.setDocId(salesReturnDocId);
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(salesReturnDTO.getOrgId(),
							salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "SalesReturn  Created Successfully";
		}

		salesReturnRepo.save(salesReturnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnVO", salesReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSalesReturnVOBySalesReturnDTO(SalesReturnDTO salesReturnDTO, SalesReturnVO salesReturnVO) {

		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setTransactionType(salesReturnDTO.getTransactionType());
		salesReturnVO.setEntryNo(salesReturnDTO.getEntryNo());
		salesReturnVO.setEntryDate(salesReturnDTO.getEntryDate());
		salesReturnVO.setPrDate(salesReturnDTO.getPrDate());
		salesReturnVO.setBONo(salesReturnDTO.getBONo());
		salesReturnVO.setBODate(salesReturnDTO.getBODate());
		salesReturnVO.setPRNo(salesReturnDTO.getPRNo());
		salesReturnVO.setBuyerName(salesReturnDTO.getBuyerName());
		salesReturnVO.setBuyerType(salesReturnDTO.getBuyerType());
		salesReturnVO.setSupplier(salesReturnDTO.getSupplier());
		salesReturnVO.setDriverName(salesReturnDTO.getDriverName());
		salesReturnVO.setCarrier(salesReturnDTO.getCarrier());
		salesReturnVO.setModeOfShipment(salesReturnDTO.getModeOfShipment());
		salesReturnVO.setVehicleType(salesReturnDTO.getVehicleType());
		salesReturnVO.setVehicleNo(salesReturnDTO.getVehicleNo());
		salesReturnVO.setContact(salesReturnDTO.getContact());
		salesReturnVO.setSecurityPersonName(salesReturnDTO.getSecurityPersonName());
		salesReturnVO.setTimeIn(salesReturnDTO.getTimeIn());
		salesReturnVO.setTimeOut(salesReturnDTO.getTimeOut());
		salesReturnVO.setBriefDescOfGoods(salesReturnDTO.getBriefDescOfGoods());
		salesReturnVO.setTotalReturnQty(salesReturnDTO.getTotalReturnQty());
		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setFreeze(salesReturnDTO.getFreeze());
		salesReturnVO.setCustomer(salesReturnDTO.getCustomer());
		salesReturnVO.setClient(salesReturnDTO.getClient());
		salesReturnVO.setFinYear(salesReturnDTO.getFinYear());
		salesReturnVO.setBranch(salesReturnDTO.getBranch());
		salesReturnVO.setBranchCode(salesReturnDTO.getBranchCode());
		salesReturnVO.setWarehouse(salesReturnDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(salesReturnVO.getId())) {
			List<SalesReturnDetailsVO> salesReturnDetailsVO1 = salesReturnDetailsRepo
					.findBySalesReturnVO(salesReturnVO);
			salesReturnDetailsRepo.deleteAll(salesReturnDetailsVO1);
		}

		List<SalesReturnDetailsVO> salesReturnDetailsVOs = new ArrayList<>();
		for (SalesReturnDetailsDTO salesReturnDetailsDTO : salesReturnDTO.getSalesReturnDetailsDTO()) {

			SalesReturnDetailsVO salesReturnDetailsVO = new SalesReturnDetailsVO();
			salesReturnDetailsVO.setLRNo(salesReturnDetailsDTO.getLRNo());
			salesReturnDetailsVO.setInvoiceNo(salesReturnDetailsDTO.getInvoiceNo());
			salesReturnDetailsVO.setPartNo(salesReturnDetailsDTO.getPartNo());
			salesReturnDetailsVO.setPartDescripition(salesReturnDetailsDTO.getPartDescripition());
			salesReturnDetailsVO.setUnit(salesReturnDetailsDTO.getUnit());
			salesReturnDetailsVO.setPickQty(salesReturnDetailsDTO.getPickQty());
			salesReturnDetailsVO.setRetQty(salesReturnDetailsDTO.getRetQty());
			salesReturnDetailsVO.setDamageQty(salesReturnDetailsDTO.getDamageQty());
			salesReturnDetailsVO.setBatchNo(salesReturnDetailsDTO.getBatchNo());
			salesReturnDetailsVO.setBatchDate(salesReturnDetailsDTO.getBatchDate());
			salesReturnDetailsVO.setExpDate(salesReturnDetailsDTO.getExpDate());
			salesReturnDetailsVO.setNoOfPallet(salesReturnDetailsDTO.getNoOfPallet());
			salesReturnDetailsVO.setPalletQty(salesReturnDetailsDTO.getPalletQty());
			salesReturnDetailsVO.setWeight(salesReturnDetailsDTO.getWeight());
			salesReturnDetailsVO.setRate(salesReturnDetailsDTO.getRate());
			salesReturnDetailsVO.setStatus(salesReturnDetailsDTO.getStatus());
			salesReturnDetailsVO.setAmount(salesReturnDetailsDTO.getAmount());
			salesReturnDetailsVO.setInsAmt(salesReturnDetailsDTO.getInsAmt());
			salesReturnDetailsVO.setRemarks(salesReturnDetailsDTO.getRemarks());
			salesReturnDetailsVO.setQcFlag(salesReturnDetailsDTO.isQcFlag());
			salesReturnDetailsVO.setSalesReturnVO(salesReturnVO);

			salesReturnDetailsVOs.add(salesReturnDetailsVO);
		}
		salesReturnVO.setSalesReturnDetailsVO(salesReturnDetailsVOs);
	}


	@Override
	@Transactional
	public List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId,
			String branchCode) {

		Set<Object[]> result = salesReturnRepo.findSalesReturnFillGridDetails(docId, client, orgId, branchCode);
		return getResult(result);
	}

	private List<Map<String, Object>> getResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partCode", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("pickQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "SR";
		String result = salesReturnRepo.getSalesReturnDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

}
