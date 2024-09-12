package com.whydigit.wms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.DateUtil;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CustomerAttachmentType;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.entity.BoExcelUploadVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LmExcelUploadVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.LmExcelUploadRepo;
import com.whydigit.wms.repo.LocationMovementDetailsRepo;
import com.whydigit.wms.repo.LocationMovementRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class LocationMovementServiceImpl implements LocationMovementService {
	public static final Logger LOGGER = LoggerFactory.getLogger(LocationMovementServiceImpl.class);

	@Autowired
	LmExcelUploadRepo lmExcelUploadRepo;

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
				stockDetailsVOFrom.setSourceId(detailsVO.getId());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setPickedQty(detailsVO.getToQty());
				stockDetailsVOFrom.setPQty(detailsVO.getToQty());
				stockDetailsVOFrom.setSSku(detailsVO.getSku());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setSQty(detailsVO.getToQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setUpdatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOFrom.setCustomer(savedLocationMovementVO.getCustomer());
				stockDetailsVOFrom.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
				stockDetailsVOFrom.setStatus("R");
				stockDetailsVOFrom.setSourceScreenName(savedLocationMovementVO.getScreenName());
				stockDetailsVOFrom.setSourceScreenCode(savedLocationMovementVO.getScreenCode());

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
				if ("DEFECTIVE".equals(detailsVO.getToBin())) {
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
				stockDetailsVOTo.setSourceId(detailsVO.getId());
				stockDetailsVOTo.setSku(detailsVO.getSku());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setCore(detailsVO.getToCore());
				stockDetailsVOTo.setSSku(detailsVO.getSku());
				stockDetailsVOTo.setSQty(detailsVO.getToQty());
				stockDetailsVOTo.setPickedQty(detailsVO.getToQty());
				stockDetailsVOTo.setPQty(detailsVO.getToQty());// Positive quantity
				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOTo.setUpdatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOTo.setCustomer(savedLocationMovementVO.getCustomer());
				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOTo.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOTo.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOTo.setSourceScreenName(savedLocationMovementVO.getScreenName());
				stockDetailsVOTo.setSourceScreenCode(savedLocationMovementVO.getScreenCode());
				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());

				if ("DEFECTIVE".equals(detailsVO.getBin())) {
					stockDetailsVOTo.setQcFlag("F");
					stockDetailsVOTo.setStatus("D");
				} else {

					stockDetailsVOTo.setQcFlag("T");
					stockDetailsVOTo.setStatus("R");
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
			LocationMovementVO locationMovementVO) throws ApplicationException {

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
			Integer fromqty = stockDetailsRepo.findAvlQtyForLocationMovement(locationMovementDTO.getOrgId(),
					locationMovementDTO.getBranch(), locationMovementDTO.getBranchCode(),
					locationMovementDTO.getClient(), locationMovementDetailsDTO.getFromBin(),
					locationMovementDetailsDTO.getPartNo(), locationMovementDetailsDTO.getGrnNo(),
					locationMovementDetailsDTO.getBatchNo());
			if (fromqty >= locationMovementDetailsDTO.getToQty()) {
				locationMovementDetailsVO.setToQty(locationMovementDetailsDTO.getToQty());
				locationMovementDetailsVO
				.setRemainingQty(locationMovementDetailsDTO.getFromQty() - locationMovementDetailsDTO.getToQty());
			}
			else
			{
				throw new ApplicationException("Toqty Should not More then FromQty");
			}
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
			part.put("toBinType", fs[1] != null ? fs[1].toString() : "");
			part.put("toBinClass", fs[2] != null ? fs[2].toString() : "");
			part.put("toCellType", fs[3] != null ? fs[3].toString() : "");
			part.put("toCore", fs[4] != null ? fs[4].toString() : "");
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
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
				orgId, branch, branchCode, client, bin, partNo);
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
			String branchCode, String client, String bin, String partNo, String grnNo) {

		Set<Object[]> result11 = locationMovementRepo.findBatchNoAndBatchDateFromStockForLocationMovement(orgId, branch,
				branchCode, client, bin, partNo, grnNo);
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
			part.put("core", fs[3] != null ? fs[3].toString() : "");
			part.put("expDate", fs[4] != null ? fs[4].toString() : "");
			part.put("pcKey", fs[5] != null ? fs[5].toString() : "");
			part.put("partNo", fs[6] != null ? fs[6].toString() : "");
			part.put("partDesc", fs[7] != null ? fs[7].toString() : "");
			part.put("sku", fs[8] != null ? fs[8].toString() : "");
			part.put("grnNo", fs[9] != null ? fs[9].toString() : "");
			part.put("batchNo", fs[10] != null ? fs[10].toString() : "");
			part.put("batchDate", fs[11] != null ? fs[11].toString() : "");
			part.put("grnDate", fs[12] != null ? fs[12].toString() : "");
			part.put("avlQty", fs[13] != null ? Integer.parseInt(fs[13].toString()) : 0);
			part.put("binType", fs[14] != null ? fs[14].toString() : "");
			part.put("id", fs[15] != null ? Integer.parseInt(fs[15].toString()) : 0);

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
	public Integer getAvlQtyFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client,
			String bin, String partNo, String grnNo, String batchNo) {

		Integer qty = stockDetailsRepo.findAvlQtyForLocationMovement(orgId, branch, branchCode, client, bin, partNo,
				grnNo, batchNo);

		// Return 0 if qty is null
		return (qty != null) ? qty : 0;
	}
	
	 private int totalRows = 0; // Instance variable to keep track of total rows
	    private int successfulUploads = 0; // Instance variable to keep track of successful uploads

	    @Transactional
	    @Override
	    public void ExcelUploadForLm(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy, String customer, String client, String finYear, String branch, String branchCode, String warehouse) throws ApplicationException {
	        List<LmExcelUploadVO> lmExcelUploadVOVOsToSave = new ArrayList<>();
	        totalRows = 0;
	        successfulUploads = 0;

	        for (MultipartFile file : files) {
	            if (file.isEmpty()) {
	                throw new ApplicationException("The supplied file '" + file.getOriginalFilename() + "' is empty (zero bytes long).");
	            }

	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0);
	                List<String> errorMessages = new ArrayList<>();
	                System.out.println("Processing file: " + file.getOriginalFilename());

	                Row headerRow = sheet.getRow(0);
	                if (!isHeaderValid(headerRow)) {
	                    StringBuilder expectedHeaders = new StringBuilder();
	                    expectedHeaders.append("Expected headers are: ")
	                                   .append("Type, From location, From location type, Location pick, ")
	                                   .append("partno, partdesc, sku, Grn No, GRN date, Batch No, Exp date, Entry no");
	                    throw new ApplicationException("Invalid Excel format in file '" + file.getOriginalFilename() + "'. " + expectedHeaders.toString());
	                }

	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue;
	                    }

	                    totalRows++;
	                    System.out.println("Validating row: " + (row.getRowNum() + 1));

	                    try {
	                        String type1 = getStringCellValue(row.getCell(0));
	                        String fromLocation = getStringCellValue(row.getCell(1));
	                        String fromLocationType = getStringCellValue(row.getCell(2));
	                        String locationPick = getStringCellValue(row.getCell(3));
	                        String partNo = getStringCellValue(row.getCell(4));
	                        String partDesc = getStringCellValue(row.getCell(5));
	                        String sku = getStringCellValue(row.getCell(6));
	                        String grnNo = getStringCellValue(row.getCell(7));
	                        LocalDate grnDate = parseDate(row.getCell(8));
	                        String batchNo = getStringCellValue(row.getCell(9));
	                        LocalDate expDate = parseDate(row.getCell(10));
	                        String entryNo = getStringCellValue(row.getCell(11));

	                        // Add validation logic here if needed
	                    } catch (Exception e) {
	                        errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
	                    }
	                }

	                if (!errorMessages.isEmpty()) {
	                    throw new ApplicationException("Excel upload validation failed for file '" + file.getOriginalFilename() + "'. Errors: " + String.join(", ", errorMessages));
	                }

	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue;
	                    }

	                    System.out.println("Saving row: " + (row.getRowNum() + 1));

	                    try {
	                        String type1 = getStringCellValue(row.getCell(0));
	                        String fromBin = getStringCellValue(row.getCell(1));
	                        String fromBinType = getStringCellValue(row.getCell(2));
	                        String binPick = getStringCellValue(row.getCell(3));
	                        String partNo = getStringCellValue(row.getCell(4));
	                        String partDesc = getStringCellValue(row.getCell(5));
	                        String sku = getStringCellValue(row.getCell(6));
	                        String grnNo = getStringCellValue(row.getCell(7));
	                        LocalDate grnDate = parseDate(row.getCell(8));
	                        String batchNo = getStringCellValue(row.getCell(9));
	                        LocalDate expDate = parseDate(row.getCell(10));
	                        String entryNo = getStringCellValue(row.getCell(11));

	                        // Create and populate LmExcelUploadVO object
	                        LmExcelUploadVO lmExcelUploadVO = new LmExcelUploadVO();
	                        lmExcelUploadVO.setType(type1);
	                        lmExcelUploadVO.setFrombin(fromBin);
	                        lmExcelUploadVO.setFromBinType(fromBinType);
	                        lmExcelUploadVO.setBinPick(binPick);
	                        lmExcelUploadVO.setPartNo(partNo);
	                        lmExcelUploadVO.setPartDesc(partDesc);
	                        lmExcelUploadVO.setSku(sku);
	                        lmExcelUploadVO.setGrnNo(grnNo);
	                        lmExcelUploadVO.setGrnDate(grnDate);
	                        lmExcelUploadVO.setBatchNo(batchNo);
	                        lmExcelUploadVO.setExpDate(expDate);
	                        lmExcelUploadVO.setEntryNo(entryNo);
	                        lmExcelUploadVO.setOrgId(orgId);
	                        lmExcelUploadVO.setCustomer(customer);
	                        lmExcelUploadVO.setClient(client);
	                        lmExcelUploadVO.setFinYear(finYear);
	                        lmExcelUploadVO.setBranch(branch);
	                        lmExcelUploadVO.setBranchCode(branchCode);
	                        lmExcelUploadVO.setWarehouse(warehouse);
	                        lmExcelUploadVO.setCreatedBy(createdBy);
	                        lmExcelUploadVO.setUpdatedBy("");
	                        lmExcelUploadVO.setActive(true);
	                        lmExcelUploadVO.setCancel(false);
	                        lmExcelUploadVO.setCancelRemarks("");

	                        lmExcelUploadVOVOsToSave.add(lmExcelUploadVO);
	                        successfulUploads++;
	                    } catch (Exception e) {
	                        // Optionally handle specific row processing exceptions here
	                    }
	                }

	                lmExcelUploadRepo.saveAll(lmExcelUploadVOVOsToSave);
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	            }
	        }
	    }

	    private LocalDate parseDate(Cell cell) {
	        if (cell == null) {
	            return null;
	        }

	        try {
	            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	                return cell.getLocalDateTimeCellValue().toLocalDate();
	            } else if (cell.getCellType() == CellType.STRING) {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Adjusted to dd/MM/yyyy
	                return LocalDate.parse(cell.getStringCellValue(), formatter);
	            }
	        } catch (Exception e) {
	            System.err.println("Date parsing error for cell value: " + getStringCellValue(cell));
	        }
	        return null;
	    }

	    private String getStringCellValue(Cell cell) {
	        if (cell == null) {
	            return "";
	        }
	        switch (cell.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue();
	            case NUMERIC:
	                return BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
	            case BOOLEAN:
	                return String.valueOf(cell.getBooleanCellValue());
	            case FORMULA:
	                return cell.getCellFormula();
	            default:
	                return "";
	        }
	    }

	    private boolean isRowEmpty(Row row) {
	        for (Cell cell : row) {
	            if (cell.getCellType() != CellType.BLANK) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private boolean isHeaderValid(Row headerRow) throws ApplicationException {
	        if (headerRow == null) {
	            return false;
	        }
	        List<String> expectedHeaders = Arrays.asList(
	            "Type", "From location", "From location type", "Location pick",
	            "partno", "partdesc", "sku", "Grn No", "GRN date",
	            "Batch No", "Exp date", "Entry no"
	        );

	        List<String> actualHeaders = new ArrayList<>();
	        for (Cell cell : headerRow) {
	            actualHeaders.add(getStringCellValue(cell).trim());
	        }

	        if (!expectedHeaders.equals(actualHeaders)) {
	            String errorDetails = "Expected headers: " + expectedHeaders.toString()
	                + ", Found headers: " + actualHeaders.toString();
	            throw new ApplicationException("Invalid Excel format. " + errorDetails);
	        }
	        return true;
	    }

	    @Override
	    public int getTotalRows() {
	        return totalRows; // Return the correct value
	    }

	    @Override
	    public int getSuccessfulUploads() {
	        return successfulUploads; // Return the correct value
	    }
	


}
