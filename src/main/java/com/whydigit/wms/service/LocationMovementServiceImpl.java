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
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setPcKey(detailsVO.getPcKey());
				stockDetailsVOFrom.setSSku(detailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setSQty(detailsVO.getToQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
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

				if ("Defective".equals(detailsVO.getBin())) {
					stockDetailsVOFrom.setQcFlag("F");
					stockDetailsVOFrom.setStatus("D");
					stockDetailsVOFrom.setBinType("DAMAGE");
				} else {

					stockDetailsVOFrom.setQcFlag("T");
					stockDetailsVOFrom.setStatus("R");
					stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				}
//				if (detailsVO.getFromQty() > detailsVO.getToQty()) {
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}else {
//					throw new ApplicationException("The ToQty is greator than avlQty");
//					}
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
				stockDetailsVOTo.setBin(detailsVO.getToBin());
				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
				stockDetailsVOTo.setBinClass(detailsVO.getToBinClass());
				stockDetailsVOTo.setBinType(detailsVO.getToBinType());
				stockDetailsVOTo.setCellType(detailsVO.getToCellType());
				stockDetailsVOTo.setQcFlag(detailsVO.getQcFlag());
				stockDetailsVOTo.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOTo.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
				stockDetailsVOTo.setCellType(detailsVO.getCellType());
				stockDetailsVOTo.setPcKey(detailsVO.getPcKey());
				stockDetailsVOTo.setSSku(detailsVO.getSsku());
				stockDetailsVOTo.setSku(detailsVO.getSku());
				stockDetailsVOTo.setStockDate(detailsVO.getStockDate());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setCore(detailsVO.getCore());
				stockDetailsVOTo.setStatus(detailsVO.getStatus());
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
					stockDetailsVOTo.setBinType("DAMAGE");
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
		locationMovementVO.setType(locationMovementDTO.getType());
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
			locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getBin());
			locationMovementDetailsVO.setPartNo(locationMovementDetailsDTO.getPartNo());
			locationMovementDetailsVO.setPartDesc(locationMovementDetailsDTO.getPartDesc());
			locationMovementDetailsVO.setGrnNo(locationMovementDetailsDTO.getGrnNo());
			locationMovementDetailsVO.setBatchNo(locationMovementDetailsDTO.getBatchNo());
			locationMovementDetailsVO.setBatchDate(locationMovementDetailsDTO.getBatchDate());
			locationMovementDetailsVO.setLotNo(locationMovementDetailsDTO.getLotNo());
			locationMovementDetailsVO.setToBin(locationMovementDetailsDTO.getToBin());
			locationMovementDetailsVO.setFromQty(locationMovementDetailsDTO.getFromQty());
			locationMovementDetailsVO.setToQty(locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO
					.setRemainingQty(locationMovementDetailsDTO.getFromQty() - locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO.setGrnDate(locationMovementDetailsDTO.getGrnDate());
			locationMovementDetailsVO.setSku(locationMovementDetailsDTO.getSku());

			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setCellType(locationMovementDetailsDTO.getCellType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setPcKey(locationMovementDetailsDTO.getPcKey());
			locationMovementDetailsVO.setSsku(locationMovementDetailsDTO.getSsku());
			locationMovementDetailsVO.setStockDate(locationMovementDetailsDTO.getStockDate());
			locationMovementDetailsVO.setToBinClass(locationMovementDetailsDTO.getToBinClass());
			locationMovementDetailsVO.setToBinType(locationMovementDetailsDTO.getToBinType());
			locationMovementDetailsVO.setToCellType(locationMovementDetailsDTO.getToCellType());

			locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getBinType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
			locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);
			if ("Defective".equals(locationMovementDetailsDTO.getBin())) {
				locationMovementDetailsVO.setQcFlag("F");
				locationMovementDetailsVO.setStatus("D");
			} else {
				locationMovementDetailsVO.setQcFlag("T");
				locationMovementDetailsVO.setStatus("R");
			}

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
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("binType", fs[2] != null ? fs[2].toString() : "");
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");
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
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
				orgId, branch, branchCode, client, bin, partNo, partDesc, sku);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			part.put("batchNo", fs[2] != null ? fs[2].toString() : "");
			part.put("batchDate", fs[3] != null ? fs[3].toString() : "");
			part.put("lotNo", fs[4] != null ? fs[4].toString() : "");
			part.put("core", fs[5] != null ? fs[5].toString() : "");
			part.put("expDate", fs[6] != null ? fs[6].toString() : "");
			part.put("status", fs[7] != null ? fs[7].toString() : "");
			part.put("avlQty", fs[8] != null ? fs[8].toString() : "");
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
			part.put("avlQty", fs[17] != null ? fs[17].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
		String screenCode = "LM";
		String result = locationMovementRepo.getLocationMovementDocId(orgId, finYear, branchCode, client, screenCode);
		return result;
	}

	@Transactional
	public int getAvlQtyFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client,
			String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo) {

		Set<Object[]> result = locationMovementRepo.findAvlQtyFromStockForLocationMovement(orgId, branch, branchCode,
				client, bin, partDesc, sku, partNo, grnNo, lotNo);
		return getAvlQtyLMResult(result);
	}

	private int getAvlQtyLMResult(Set<Object[]> result) {
		int totalQty = 0;
		for (Object[] qt : result) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}
}
