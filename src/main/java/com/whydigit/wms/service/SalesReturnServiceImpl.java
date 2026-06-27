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

import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SalesReturnDetailsRepo;
import com.whydigit.wms.repo.SalesReturnRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class SalesReturnServiceImpl implements SalesReturnService {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesReturnServiceImpl.class);

	@Autowired
	SalesReturnRepo salesReturnRepo;

	@Autowired
	SalesReturnDetailsRepo salesReturnDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	MaterialRepo materialRepo;

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

		SalesReturnVO savedSalesReturnVO = salesReturnRepo.save(salesReturnVO);

		List<SalesReturnDetailsVO> salesReturnDetailsVOLists = savedSalesReturnVO.getSalesReturnDetailsVO();
		if (salesReturnDetailsVOLists != null && !salesReturnDetailsVOLists.isEmpty()) {
			if ("Confirm".equals(savedSalesReturnVO.getStatus())) {
				for (SalesReturnDetailsVO detailsVO : salesReturnDetailsVOLists) {

					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
					stockDetailsVOFrom.setOrgId(savedSalesReturnVO.getOrgId());
					stockDetailsVOFrom.setFinYear(savedSalesReturnVO.getFinYear());
					stockDetailsVOFrom.setBranch(savedSalesReturnVO.getBranch());
					stockDetailsVOFrom.setBranchCode(savedSalesReturnVO.getBranchCode());
					stockDetailsVOFrom.setWarehouse(savedSalesReturnVO.getWarehouse());
					stockDetailsVOFrom.setCustomer(savedSalesReturnVO.getCustomer());
					stockDetailsVOFrom.setClient(savedSalesReturnVO.getClient());
					stockDetailsVOFrom.setClientCode(
							clientRepo.getClientCode(savedSalesReturnVO.getOrgId(), savedSalesReturnVO.getClient()));
					stockDetailsVOFrom.setCreatedBy(savedSalesReturnVO.getUpdatedBy());
					stockDetailsVOFrom.setRefNo(savedSalesReturnVO.getDocId());
					stockDetailsVOFrom.setRefDate(savedSalesReturnVO.getDocDate());
					stockDetailsVOFrom.setBuyerOrderNo(savedSalesReturnVO.getBoNo());
					stockDetailsVOFrom.setUpdatedBy(savedSalesReturnVO.getUpdatedBy());
					stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedSalesReturnVO.getOrgId(),
							savedSalesReturnVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getPickQty());
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setStatus("R");
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getBin());
					stockDetailsVOFrom.setPQty(detailsVO.getPickQty());
					stockDetailsVOFrom.setPickedQty(detailsVO.getPickQty());
					stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
					stockDetailsVOFrom.setBinType(detailsVO.getBinType());
					stockDetailsVOFrom.setSku(detailsVO.getSku());
					stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
					stockDetailsVOFrom.setCellType(detailsVO.getCellType());
					stockDetailsVOFrom.setCore(detailsVO.getCore());
					stockDetailsVOFrom.setSSku(detailsVO.getSku());
					stockDetailsVOFrom.setActive(true);
					stockDetailsVOFrom.setSourceScreenCode(savedSalesReturnVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(savedSalesReturnVO.getScreenName());
					stockDetailsVOFrom.setSourceId(detailsVO.getId());
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnVO", salesReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSalesReturnVOBySalesReturnDTO(SalesReturnDTO salesReturnDTO, SalesReturnVO salesReturnVO) {

		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setEntryNo(salesReturnDTO.getEntryNo());
		salesReturnVO.setEntryDate(salesReturnDTO.getEntryDate());
		salesReturnVO.setPrDate(salesReturnDTO.getPrDate());
		salesReturnVO.setBoNo(salesReturnDTO.getBoNo());
		salesReturnVO.setBoDate(salesReturnDTO.getBoDate());
		salesReturnVO.setPrNo(salesReturnDTO.getPrNo());
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
		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setCustomer(salesReturnDTO.getCustomer());
		salesReturnVO.setClient(salesReturnDTO.getClient());
		salesReturnVO.setFinYear(salesReturnDTO.getFinYear());
		salesReturnVO.setBranch(salesReturnDTO.getBranch());
		salesReturnVO.setBranchCode(salesReturnDTO.getBranchCode());
		salesReturnVO.setWarehouse(salesReturnDTO.getWarehouse());
		salesReturnVO.setStatus(salesReturnDTO.getStatus());

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
			salesReturnDetailsVO.setPartDesc(salesReturnDetailsDTO.getPartDesc());
			salesReturnDetailsVO.setSku(salesReturnDetailsDTO.getSku());
			salesReturnDetailsVO.setPickQty(salesReturnDetailsDTO.getPickQty());
			salesReturnDetailsVO.setRetQty(salesReturnDetailsDTO.getRetQty());
			salesReturnDetailsVO.setDamageQty(salesReturnDetailsDTO.getDamageQty());
			salesReturnDetailsVO.setBatchNo(salesReturnDetailsDTO.getBatchNo());
			salesReturnDetailsVO.setBatchDate(salesReturnDetailsDTO.getBatchDate());
			salesReturnDetailsVO.setExpDate(salesReturnDetailsDTO.getExpDate());
			salesReturnDetailsVO.setNoOfBin(salesReturnDetailsDTO.getNoOfBin());
			salesReturnDetailsVO.setBinQty(salesReturnDetailsDTO.getBinQty());
			salesReturnDetailsVO.setRemarks(salesReturnDetailsDTO.getRemarks());
			salesReturnDetailsVO.setQcFlag(salesReturnDetailsDTO.getQcFlag());
			salesReturnDetailsVO.setRemarks(salesReturnDetailsDTO.getRemarks());
			salesReturnDetailsVO.setBinClass(salesReturnDetailsDTO.getBinClass());
			salesReturnDetailsVO.setCellType(salesReturnDetailsDTO.getCellType());
			salesReturnDetailsVO.setSsku(salesReturnDetailsDTO.getSku());
			salesReturnDetailsVO.setStockDate(LocalDate.now());
			salesReturnDetailsVO.setBinType(salesReturnDetailsDTO.getBinType());
			salesReturnDetailsVO.setExpDate(salesReturnDetailsDTO.getExpDate());
			salesReturnDetailsVO.setStatus("R");
			salesReturnDetailsVO.setQcFlag(salesReturnDetailsDTO.getQcFlag());
			salesReturnDetailsVO.setBin(salesReturnDetailsDTO.getBin());
			salesReturnDetailsVO.setCore(salesReturnDetailsDTO.getCore());
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
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("pickQty", fs[3] != null ? fs[3].toString() : "");
			part.put("id", fs[4] != null ? Integer.parseInt(fs[4].toString()) : 0);
			part.put("batchNo", fs[5] != null ? fs[5].toString() : "");
			part.put("batchDate", fs[6] != null ? fs[6].toString() : "");
			part.put("expDate", fs[7] != null ? fs[7].toString() : "");
			part.put("bin", fs[8] != null ? fs[8].toString() : "");
			part.put("binClass", fs[9] != null ? fs[9].toString() : "");
			part.put("binType", fs[10] != null ? fs[10].toString() : "");
			part.put("cellType", fs[11] != null ? fs[11].toString() : "");
			part.put("core", fs[12] != null ? fs[12].toString() : "");
			part.put("qcFlag", fs[13] != null ? fs[13].toString() : "");
			part.put("sku1", fs[14] != null ? fs[14].toString() : "");

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
