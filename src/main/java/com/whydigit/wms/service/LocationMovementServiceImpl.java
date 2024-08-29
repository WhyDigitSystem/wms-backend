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

import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.LocationMovementDetailsRepo;
import com.whydigit.wms.repo.LocationMovementRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class LocationMovementServiceImpl implements LocationMovementService {
	public static final Logger LOGGER = LoggerFactory.getLogger(LocationMovementServiceImpl.class);

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	LocationMovementRepo locationMovementRepo;

	@Autowired
	LocationMovementDetailsRepo locationMovementDetailsRepo;

	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	ClientRepo clientRepo;

	@Transactional
	public List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return locationMovementRepo.findAllLocationMovement(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Transactional
	public LocationMovementVO getLocationMovementById(Long id) {
		LocationMovementVO locationMovementVO = new LocationMovementVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  LocationMovement BY Id : {}", id);
			locationMovementVO = locationMovementRepo.findLocationMovementById(id);
		} else {
			LOGGER.info("failed Received LocationMovement For All Id.");
		}
		return locationMovementVO;

	}

	@Transactional
	public Map<String, Object> createUpdateLocationMovement(LocationMovementDTO locationMovementDTO)
			throws ApplicationException {

		LocationMovementVO locationMovementVO = new LocationMovementVO();
		String screenCode = "LM";
		String message;

		if (ObjectUtils.isNotEmpty(locationMovementDTO.getId())) {
			locationMovementVO = locationMovementRepo.findById(locationMovementDTO.getId())
					.orElseThrow(() -> new ApplicationException("LocationMovement not found"));

			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());
			createUpdateLocationMovementVOByLocationMovementDTO(locationMovementDTO, locationMovementVO);
			message = "LocationMovement Updated Successfully";
		} else {
			locationMovementVO.setCreatedBy(locationMovementDTO.getCreatedBy());
			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());

			String locationMovementDocId = locationMovementRepo.getLocationMovementDocId(locationMovementDTO.getOrgId(),
					locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
					locationMovementDTO.getClient(), screenCode);
			locationMovementVO.setDocId(locationMovementDocId);
			createUpdateLocationMovementVOByLocationMovementDTO(locationMovementDTO, locationMovementVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(locationMovementDTO.getOrgId(),
							locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
							locationMovementDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "LocationMovement Created Successfully";
		}
		LocationMovementVO savedLocationMovementVO = locationMovementRepo.save(locationMovementVO);

		List<LocationMovementDetailsVO> locationMovementDetailsVOLists = savedLocationMovementVO
				.getLocationMovementDetailsVO();
		if (locationMovementDetailsVOLists != null && !locationMovementDetailsVOLists.isEmpty()) {
			for (LocationMovementDetailsVO detailsVO : locationMovementDetailsVOLists) {
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setBin(detailsVO.getBin());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setSQty(detailsVO.getToQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOFrom.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsVOFrom.setStockDate(LocalDate.now());
				stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
				stockDetailsVOFrom.setStatus(detailsVO.getStatus());
				stockDetailsRepo.save(stockDetailsVOFrom);
			}
			for (LocationMovementDetailsVO detailsVO : locationMovementDetailsVOLists) {
				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
				stockDetailsVOTo.setBin(detailsVO.getToBin());
				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
				stockDetailsVOTo.setBinClass(detailsVO.getToBinClass());
				stockDetailsVOTo.setBinType(detailsVO.getToBinType());
				stockDetailsVOTo.setCellType(detailsVO.getToCellType());
				if ("Defective".equals(detailsVO.getToBin())) {
					stockDetailsVOTo.setQcFlag("F");
					stockDetailsVOTo.setStatus("D");
				} else {
					stockDetailsVOTo.setQcFlag("T");
					stockDetailsVOTo.setStatus("R");
				}
				stockDetailsVOTo.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOTo.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
				stockDetailsVOTo.setCellType(detailsVO.getCellType());
				stockDetailsVOTo.setSku(detailsVO.getSku());
				stockDetailsVOTo.setStockDate(LocalDate.now());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setCore(detailsVO.getCore());
				stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOTo.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOTo.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());
				
				if ("Defective".equals(detailsVO.getBin())) {
					stockDetailsVOTo.setQcFlag("F");
					stockDetailsVOTo.setStatus("D");
				} else {

					stockDetailsVOTo.setQcFlag("T");
					stockDetailsVOTo.setStatus("R");
					stockDetailsVOTo.setBinType(detailsVO.getBinType());
				}
//				if (detailsVO.getFromQty() > detailsVO.getToQty()) {
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}else {
//					throw new ApplicationException("The ToQty is greator than avlQty");
//					}
				stockDetailsRepo.save(stockDetailsVOTo);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("locationMovementVO", locationMovementVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateLocationMovementVOByLocationMovementDTO(LocationMovementDTO locationMovementDTO,
			LocationMovementVO locationMovementVO) {

		locationMovementVO.setOrgId(locationMovementDTO.getOrgId());
		locationMovementVO.setCustomer(locationMovementDTO.getCustomer());
		locationMovementVO.setFinYear(locationMovementDTO.getFinYear());
		locationMovementVO.setClient(locationMovementDTO.getClient());
		locationMovementVO.setBranchCode(locationMovementDTO.getBranchCode());
		locationMovementVO.setBranch(locationMovementDTO.getBranch());
		locationMovementVO.setWarehouse(locationMovementDTO.getWarehouse());
		locationMovementVO.setMovedQty(locationMovementDTO.getMovedQty());

		if (ObjectUtils.isNotEmpty(locationMovementVO.getId())) {
			List<LocationMovementDetailsVO> locationMovementDetailsVO1 = locationMovementDetailsRepo
					.findByLocationMovementVO(locationMovementVO);
			locationMovementDetailsRepo.deleteAll(locationMovementDetailsVO1);
		}

		List<LocationMovementDetailsVO> locationMovementDetailsVOs = new ArrayList<>();
		for (LocationMovementDetailsDTO locationMovementDetailsDTO : locationMovementDTO
				.getLocationMovementDetailsDTO()) {
			if (locationMovementDetailsDTO.getToQty() > locationMovementDetailsDTO.getFromQty()) {
				throw new IllegalArgumentException("ToQuantity cannot be greater than FromQuantity for partNo: "
						+ locationMovementDetailsDTO.getPartNo());
			}
			LocationMovementDetailsVO locationMovementDetailsVO = new LocationMovementDetailsVO();
			
			locationMovementDetailsVO.setPartNo(locationMovementDetailsDTO.getPartNo());
			locationMovementDetailsVO.setPartDesc(locationMovementDetailsDTO.getPartDesc());
			locationMovementDetailsVO.setSku(locationMovementDetailsDTO.getSku());
			locationMovementDetailsVO.setGrnNo(locationMovementDetailsDTO.getGrnNo());
			locationMovementDetailsVO.setGrnDate(locationMovementDetailsDTO.getGrnDate());
			locationMovementDetailsVO.setBatchNo(locationMovementDetailsDTO.getBatchNo());
			locationMovementDetailsVO.setBatchDate(locationMovementDetailsDTO.getBatchDate());
			locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getFromBin());
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getFromBinClass());
			locationMovementDetailsVO.setCellType(locationMovementDetailsDTO.getFromCellType());
			locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getFromBinType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getFromCore());
			locationMovementDetailsVO.setToBin(locationMovementDetailsDTO.getToBin());
			locationMovementDetailsVO.setToBinClass(locationMovementDetailsDTO.getToBinClass());
			locationMovementDetailsVO.setToBinType(locationMovementDetailsDTO.getToBinType());
			locationMovementDetailsVO.setToCellType(locationMovementDetailsDTO.getToCellType());
			locationMovementDetailsVO.setToCore(locationMovementDetailsDTO.getToCore());
			locationMovementDetailsVO.setFromQty(locationMovementDetailsDTO.getFromQty());
			locationMovementDetailsVO.setToQty(locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO
					.setRemainingQty(locationMovementDetailsDTO.getFromQty() - locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
			locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);
			locationMovementDetailsVO.setQcFlag(locationMovementDetailsDTO.getQcFlag());
			locationMovementDetailsVO.setStatus(locationMovementDetailsDTO.getStatus());
			locationMovementDetailsVOs.add(locationMovementDetailsVO);
		}
		locationMovementVO.setLocationMovementDetailsVO(locationMovementDetailsVOs);
	}

	@Transactional
	public List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String branch, String branchCode,
			String client) {

		Set<Object[]> result = locationMovementRepo.findBinFromStockForLocationMovement(orgId, branch, branchCode,
				client);
		return getMovementResult(result);
	}

	private List<Map<String, Object>> getMovementResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("fromBinType", fs[0] != null ? fs[0].toString() : "");
			part.put("fromBinClass", fs[1] != null ? fs[1].toString() : "");
			part.put("fromCellType", fs[2] != null ? fs[2].toString() : "");
			part.put("fromBin", fs[3] != null ? fs[3].toString() : "");
			part.put("fromCore", fs[4] != null ? fs[4].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getToBinFromLocationStatusForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String warehouse) {

		Set<Object[]> result = locationMovementRepo.findToBinFromLocationStatusForLocationMovement(orgId, branch,
				branchCode, client, warehouse);
		return getToBinResult(result);
	}

	private List<Map<String, Object>> getToBinResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("toBin", fs[0] != null ? fs[0].toString() : "");
			part.put("toBinClass", fs[1] != null ? fs[1].toString() : "");
			part.put("toBinType", fs[2] != null ? fs[2].toString() : "");
			part.put("toCellType", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin) {

		Set<Object[]> result = locationMovementRepo.findPartNoAndPartDescFromStockForLocationMovement(orgId, branch,
				branchCode, client, bin);
		return getPartResult(result);
	}

	private List<Map<String, Object>> getPartResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Transactional
	@Override
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(orgId, branch, branchCode, client, bin, partNo);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Transactional
	@Override
	public List<Map<String, Object>> getBatchNoAndBatchDateFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo,String grnNo) {

		Set<Object[]> result11 = locationMovementRepo.findBatchNoAndBatchDateFromStockForLocationMovement(orgId, branch, branchCode, client, bin, partNo,grnNo);
		return getBatchResult(result11);
	}

	private List<Map<String, Object>> getBatchResult(Set<Object[]> result11) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result11) {
			Map<String, Object> part = new HashMap<>();
			part.put("batchNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchDate", fs[1] != null ? fs[1].toString() : "");
			part.put("expDate", fs[2] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getAllForLocationMovementDetailsFillGrid(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = locationMovementRepo.findAllForLocationMovementDetailsFillGrid(orgId, branch, branchCode,
				client);
		return getFillGridResult(result);
	}

	private List<Map<String, Object>> getFillGridResult(Set<Object[]> result) {
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
			part.put("batchNo", fs[13] != null ? fs[13].toString() : "");
			part.put("batchDate", fs[14] != null ? fs[14].toString() : "");
			part.put("lotNo", fs[15] != null ? fs[15].toString() : "");
			part.put("grnDate", fs[16] != null ? fs[16].toString() : "");
			part.put("avlQty", fs[17] != null ? Integer.parseInt(fs[17].toString()): 0);
			part.put("binType", fs[18] != null ? fs[18].toString() : "");
			part.put("id", fs[19] != null ? Integer.parseInt(fs[19].toString()): 0);
			
			details1.add(part);
		}
		return details1;
	}

	
	@Override
	public String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
		String screenCode = "LM";
		String result = locationMovementRepo.getLocationMovementDocId(orgId, finYear, branchCode, client, screenCode);
		return result;
	}

	
	@Override
	public Integer getAvlQtyFromStockForLocationMovement(Long orgId, String branch,
	        String branchCode, String client, String bin, String partNo, String grnNo, String batchNo) {

	    Integer qty = stockDetailsRepo.findAvlQtyForLocationMovement(orgId, branch, branchCode, client, bin, partNo, grnNo, batchNo);
	    
	    // Return 0 if qty is null
	    return (qty != null) ? qty : 0;
	}


}
