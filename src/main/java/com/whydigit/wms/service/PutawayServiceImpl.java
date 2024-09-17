package com.whydigit.wms.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.DataFormatter;

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
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.PutAwayDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.entity.PutAwayDetailsVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.PutawayExcelUploadVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GrnDetailsRepo;
import com.whydigit.wms.repo.GrnRepo;
import com.whydigit.wms.repo.HandlingStockInRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.PutAwayDetailsRepo;
import com.whydigit.wms.repo.PutAwayRepo;
import com.whydigit.wms.repo.PutawayExcelUploadRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class PutawayServiceImpl implements PutawayService {

	@Autowired
	GrnRepo grnRepo;

	@Autowired
	HandlingStockInRepo handlingStockInRepo;

	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	PutAwayRepo putAwayRepo;

	@Autowired
	PutAwayDetailsRepo putAwayDetailsRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	PutawayExcelUploadRepo putawayExcelUploadRepo;

	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	public static final Logger LOGGER = LoggerFactory.getLogger(PutawayServiceImpl.class);

	@Override
	public List<PutAwayVO> getAllPutAway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse) {
		return putAwayRepo.findAllPutAwayDetails(orgId, finYear, branch, branchCode, client, warehouse);

	}

	@Override
	public PutAwayVO getPutAwayById(Long id) {
		PutAwayVO putAwayVO = new PutAwayVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  PutAway BY Id : {}", id);
			putAwayVO = putAwayRepo.findById(id).orElse(null);
		} else {
			LOGGER.info("failed Received  PutAway For All Id.");
		}
		return putAwayVO;
	}

	@Override
	@Transactional
	public String getPutAwayDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "PC";
		String result = putAwayRepo.getPutAwayDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<GrnVO> getGrnNoForPutAway(Long orgId, String client, String branch, String branchcode,
			String warehouse) {
		return grnRepo.findGrnNoDetailsForPutAway(orgId, client, branch, branchcode, warehouse);
	}

	@Override
	public Map<String, Object> createUpdatePutAway(PutAwayDTO putAwayDTO) throws ApplicationException {
		PutAwayVO putAwayVO = new PutAwayVO();
		String message;
		String screenCode = "PC";

		if (ObjectUtils.isEmpty(putAwayDTO.getId())) {

//			GETDOCID API
			String docId = putAwayRepo.getPutAwayDocId(putAwayDTO.getOrgId(), putAwayDTO.getFinYear(),
					putAwayDTO.getBranchCode(), putAwayDTO.getClient(), screenCode);

			putAwayVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(putAwayDTO.getOrgId(),
							putAwayDTO.getFinYear(), putAwayDTO.getBranchCode(), putAwayDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			getPutawayVOFromPutawayDTO(putAwayDTO, putAwayVO);
			putAwayVO.setCreatedBy(putAwayDTO.getCreatedBy());
			putAwayVO.setUpdatedBy(putAwayDTO.getCreatedBy());
			message = "PutAway Creation SucessFully";

		} else {
			putAwayVO = putAwayRepo.findById(putAwayDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Not Found Any Informations,Invalid Id" + putAwayDTO.getId()));
			putAwayVO.setUpdatedBy(putAwayDTO.getCreatedBy());
			getPutawayVOFromPutawayDTO(putAwayDTO, putAwayVO);
			message = "Putaway Updation SucessFully";

		}
		if ("Confirm".equals(putAwayDTO.getStatus())) {
			putAwayVO.setFreeze(true);
		} else {
			putAwayVO.setFreeze(false);
		}
		PutAwayVO savedPutAwayVO = putAwayRepo.save(putAwayVO);
		List<PutAwayDetailsVO> putAwayDetailsVOs = savedPutAwayVO.getPutAwayDetailsVO();

		List<HandlingStockInVO> handlingStockInVOs = handlingStockInRepo.findBySdocid(savedPutAwayVO.getDocId());
		if (handlingStockInVOs != null) {
			handlingStockInRepo.deleteAll(handlingStockInVOs);
		}
		if (putAwayDetailsVOs != null && !putAwayDetailsVOs.isEmpty())

			for (PutAwayDetailsVO putAwayDetailsVO : putAwayDetailsVOs) {

				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
				handlingStockInVO.setScreencode(savedPutAwayVO.getScreenCode());
				handlingStockInVO.setSourcescreen(savedPutAwayVO.getScreenName());
				// Set common values from savedGrnVO
				handlingStockInVO.setRefno(savedPutAwayVO.getDocId());
				handlingStockInVO.setRefdate(savedPutAwayVO.getDocDate());
				handlingStockInVO.setGrnno(savedPutAwayVO.getGrnNo());
				handlingStockInVO.setGrndate(savedPutAwayVO.getGrnDate());
				handlingStockInVO.setBranch(savedPutAwayVO.getBranch());
				handlingStockInVO.setOrgId(savedPutAwayVO.getOrgId());
				handlingStockInVO.setBranchcode(savedPutAwayVO.getBranchCode());
				handlingStockInVO.setCustomer(savedPutAwayVO.getCustomer());
				handlingStockInVO.setWarehouse(savedPutAwayVO.getWarehouse());
				handlingStockInVO.setClient(savedPutAwayVO.getClient());
				handlingStockInVO.setSdocdate(savedPutAwayVO.getDocDate());
				handlingStockInVO.setStockdate(savedPutAwayVO.getGrnDate());
				handlingStockInVO.setSdocid(savedPutAwayVO.getDocId());
				handlingStockInVO.setFinyr(savedPutAwayVO.getFinYear());
				handlingStockInVO.setCreatedby(savedPutAwayVO.getCreatedBy());
				handlingStockInVO.setUpdatedby(savedPutAwayVO.getUpdatedBy());

				// Set values from grnDetailsVO
				handlingStockInVO.setPartno(putAwayDetailsVO.getPartNo());
				handlingStockInVO.setPartdesc(putAwayDetailsVO.getPartDesc());
				handlingStockInVO.setRpqty(putAwayDetailsVO.getPutAwayQty() * -1);
				handlingStockInVO.setSqty(putAwayDetailsVO.getPutAwayQty() * -1);
				handlingStockInVO.setLocationtype(putAwayDetailsVO.getBinType());
				handlingStockInVO.setInvqty(0);
				handlingStockInVO.setRecqty(0);
				handlingStockInVO.setShortqty(0);
				handlingStockInVO.setPalletqty(0);
				handlingStockInVO.setSku(putAwayDetailsVO.getSku());
				handlingStockInVO.setSsku(putAwayDetailsVO.getSku());
				handlingStockInVO.setExpdate(putAwayDetailsVO.getExpDate());
				handlingStockInVO.setBatchno(putAwayDetailsVO.getBatch());
				handlingStockInVO.setBatchdt(putAwayDetailsVO.getBatchDate());
				// Check if damageqty is 0
				if ("DEFECTIVE".equals(putAwayDetailsVO.getBin())) {
					handlingStockInVO.setQcflag("F");
					handlingStockInVO.setDamageqty(putAwayDetailsVO.getPutAwayQty());
				} else {

					handlingStockInVO.setQcflag("T");
				}
				handlingStockInRepo.save(handlingStockInVO);
			}
		if ("Confirm".equals(savedPutAwayVO.getStatus())) {
			for (PutAwayDetailsVO putAwayDetailsVO : putAwayDetailsVOs) {
				StockDetailsVO stockDetailsVO = new StockDetailsVO();
				stockDetailsVO.setCustomer(savedPutAwayVO.getCustomer());
				stockDetailsVO.setBranch(savedPutAwayVO.getBranch());
				stockDetailsVO.setOrgId(savedPutAwayVO.getOrgId());
				stockDetailsVO.setBranchCode(savedPutAwayVO.getBranchCode());
				stockDetailsVO.setClient(savedPutAwayVO.getClient());
				stockDetailsVO
						.setClientCode(clientRepo.getClientCode(savedPutAwayVO.getOrgId(), savedPutAwayVO.getClient()));
				stockDetailsVO.setCore(savedPutAwayVO.getCore());
				stockDetailsVO.setGrnNo(savedPutAwayVO.getGrnNo());
				stockDetailsVO.setStockDate(savedPutAwayVO.getGrnDate());
				stockDetailsVO.setGrnDate(savedPutAwayVO.getGrnDate());
				stockDetailsVO.setLotNo(savedPutAwayVO.getLotNo());
				stockDetailsVO.setWarehouse(savedPutAwayVO.getWarehouse());
				stockDetailsVO.setFinYear(savedPutAwayVO.getFinYear());
				stockDetailsVO.setBranch(savedPutAwayVO.getBranch());
				stockDetailsVO.setBranchCode(savedPutAwayVO.getBranchCode());
				stockDetailsVO.setRefNo(savedPutAwayVO.getDocId());
				stockDetailsVO.setRefDate(savedPutAwayVO.getDocDate());
				stockDetailsVO.setBin(putAwayDetailsVO.getBin());
				stockDetailsVO.setCarrier(savedPutAwayVO.getCarrier());
				stockDetailsVO.setSourceScreenCode(savedPutAwayVO.getScreenCode());
				stockDetailsVO.setSourceScreenName(savedPutAwayVO.getScreenName());
				stockDetailsVO.setInvQty(putAwayDetailsVO.getInvQty());
				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecQty());
				stockDetailsVO.setShortQty(putAwayDetailsVO.getShortQty());
				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecQty());
				stockDetailsVO.setSQty(putAwayDetailsVO.getPutAwayQty());
				stockDetailsVO.setSSku(putAwayDetailsVO.getSSku());
				stockDetailsVO.setBinClass(putAwayDetailsVO.getBinClass());
				stockDetailsVO.setBatchDate(putAwayDetailsVO.getBatchDate());
				stockDetailsVO.setPartno(putAwayDetailsVO.getPartNo());
				stockDetailsVO.setPartDesc(putAwayDetailsVO.getPartDesc());
				stockDetailsVO.setSku(putAwayDetailsVO.getSku());
				stockDetailsVO.setExpDate(putAwayDetailsVO.getExpDate());
				stockDetailsVO.setCellType(putAwayDetailsVO.getCellType());
				stockDetailsVO.setCore(putAwayVO.getCore());
				stockDetailsVO.setBinClass(putAwayVO.getBinClass());
				stockDetailsVO.setBatch(putAwayDetailsVO.getBatch());
				stockDetailsVO.setCreatedBy(savedPutAwayVO.getCreatedBy());
				stockDetailsVO.setUpdatedBy(savedPutAwayVO.getUpdatedBy());
				stockDetailsVO.setPcKey(materialRepo.getParentChildKey(savedPutAwayVO.getOrgId(),
						savedPutAwayVO.getClient(), putAwayDetailsVO.getPartNo()));
				stockDetailsVO.setSourceId(putAwayDetailsVO.getId());
				if ("DEFECTIVE".equals(putAwayDetailsVO.getBin())) {
					stockDetailsVO.setQcFlag("F");
					stockDetailsVO.setStatus("D");
					stockDetailsVO.setBinType("DEFECTIVE");
				} else {

					stockDetailsVO.setQcFlag("T");
					stockDetailsVO.setStatus("R");
					stockDetailsVO.setBinType(savedPutAwayVO.getBinType());
				}
				stockDetailsRepo.save(stockDetailsVO);
			}
		}

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("putAwayVO", putAwayVO);
		return response;
	}

	private void getPutawayVOFromPutawayDTO(PutAwayDTO putAwayDTO, PutAwayVO putAwayVO) {

		GrnVO grnVO = grnRepo.findByDocId(putAwayDTO.getGrnNo());
		if ("Confirm".equals(putAwayDTO.getStatus())) {
			grnVO.setFreeze(true);
		} else {
			grnVO.setFreeze(false);
		}
		grnRepo.save(grnVO);

		putAwayVO.setGrnNo(putAwayDTO.getGrnNo());
		putAwayVO.setGrnDate(putAwayDTO.getGrnDate());
		putAwayVO.setEntryNo(putAwayDTO.getEntryNo());
		putAwayVO.setCore(putAwayDTO.getCore());
		putAwayVO.setSupplierShortName(putAwayDTO.getSupplierShortName());
		putAwayVO.setSupplier(putAwayDTO.getSupplier());
		putAwayVO.setModeOfShipment(putAwayDTO.getModeOfShipment());
		putAwayVO.setCarrier(putAwayDTO.getCarrier());
		putAwayVO.setBinType(putAwayDTO.getBinType());

		putAwayVO.setStatus(putAwayDTO.getStatus());

		putAwayVO.setLotNo(putAwayDTO.getLotNo());
		putAwayVO.setEntryDate(putAwayDTO.getEntryDate());
		putAwayVO.setEnteredPerson(putAwayDTO.getEnteredPerson());
		putAwayVO.setOrgId(putAwayDTO.getOrgId());
		putAwayVO.setCustomer(putAwayDTO.getCustomer());
		putAwayVO.setClient(putAwayDTO.getClient());
		putAwayVO.setFinYear(putAwayDTO.getFinYear());
		putAwayVO.setBranch(putAwayDTO.getBranch());
		putAwayVO.setBranchCode(putAwayDTO.getBranchCode());
		putAwayVO.setWarehouse(putAwayDTO.getWarehouse());
		putAwayVO.setCreatedBy(putAwayDTO.getCreatedBy());
		putAwayVO.setBinClass(putAwayDTO.getBinClass());
		putAwayVO.setBinPick(putAwayDTO.getBinPick());
		putAwayVO.setContact(putAwayDTO.getContact());
		putAwayVO.setVehicleNo(putAwayDTO.getVehicleNo());
		putAwayVO.setVehicleType(putAwayDTO.getVehicleType());
		putAwayVO.setDriverName(putAwayDTO.getDriverName());
		if (putAwayDTO.getId() != null) {

			List<PutAwayDetailsVO> detailsVOs = putAwayDetailsRepo.findByPutAwayVO(putAwayVO);
			putAwayDetailsRepo.deleteAll(detailsVOs);
		}

		int totalPutawayQty = 0;

		List<PutAwayDetailsVO> putAwayDetailsVO = new ArrayList<>();
		for (PutAwayDetailsDTO putAwayDetailsDTO : putAwayDTO.getPutAwayDetailsDTO()) {
			PutAwayDetailsVO putAwayDetailsVOs = new PutAwayDetailsVO();
			putAwayDetailsVOs.setPartNo(putAwayDetailsDTO.getPartNo());
			putAwayDetailsVOs.setBatch(putAwayDetailsDTO.getBatch());
			putAwayDetailsVOs.setPartDesc(putAwayDetailsDTO.getPartDesc());
			putAwayDetailsVOs.setSku(putAwayDetailsDTO.getSku());
			putAwayDetailsVOs.setInvoiceNo(putAwayDetailsDTO.getInvoiceNo());
			putAwayDetailsVOs.setInvQty(putAwayDetailsDTO.getInvQty());
			putAwayDetailsVOs.setRecQty(putAwayDetailsDTO.getRecQty());
			putAwayDetailsVOs.setGrnQty(putAwayDetailsDTO.getGrnQty());
			putAwayDetailsVOs.setPutAwayQty(putAwayDetailsDTO.getPutAwayQty());
			putAwayDetailsVOs.setPutAwayPiecesQty(putAwayDetailsDTO.getPutAwayPiecesQty());
			putAwayDetailsVOs.setBin(putAwayDetailsDTO.getBin());
			putAwayDetailsVOs.setRemarks(putAwayDetailsDTO.getRemarks());
			putAwayDetailsVOs.setBinType(putAwayDetailsDTO.getBinType());
			putAwayDetailsVOs.setSSku(putAwayDetailsDTO.getSSku());
			putAwayDetailsVOs.setCellType(putAwayDetailsDTO.getCellType());
			String celltype = putAwayRepo.getCelltype(putAwayDTO.getOrgId(), putAwayDetailsDTO.getBin());
			putAwayDetailsVOs.setCellType(celltype);
			putAwayDetailsVOs.setBinClass(putAwayDTO.getBinClass());
			putAwayDetailsVOs.setBatchDate(putAwayDetailsDTO.getBatchDate());
			putAwayDetailsVOs.setExpDate(putAwayDetailsDTO.getExpdate());
			putAwayDetailsVOs.setPutAwayVO(putAwayVO);
			totalPutawayQty = totalPutawayQty + putAwayDetailsDTO.getPutAwayQty();

			if ("DEFECTIVE".equals(putAwayDetailsDTO.getBin())) {
				putAwayDetailsVOs.setQcFlag("F");
				putAwayDetailsVOs.setStatus("D");
			} else {
				putAwayDetailsVOs.setQcFlag("T");
				putAwayDetailsVOs.setStatus("R");
			}
			putAwayDetailsVO.add(putAwayDetailsVOs);
		}
		putAwayVO.setTotalGrnQty(grnVO.getTotalGrnQty());
		putAwayVO.setTotalPutawayQty(totalPutawayQty);
		putAwayVO.setPutAwayDetailsVO(putAwayDetailsVO);
	}

	@Override
	public List<Map<String, Object>> getFillGridDetailsForPutaway(Long orgId, String branchCode, String warehouse,
			String client, String grnNo, String binType, String binClass, String binPick) {
		Set<Object[]> getGridDetails = putAwayRepo.getPutawayGridDetails(orgId, branchCode, warehouse, client, grnNo,
				binType, binClass, binPick);
		return PutawayGridDetails(getGridDetails);
	}

	private List<Map<String, Object>> PutawayGridDetails(Set<Object[]> getGridDetails) {
		List<Map<String, Object>> getDetails = new ArrayList<>();
		for (Object[] gridDetails : getGridDetails) {
			Map<String, Object> mapDetails = new HashMap<>();
			mapDetails.put("partNo", gridDetails[0] != null ? gridDetails[0].toString() : "");
			mapDetails.put("partDesc", gridDetails[1] != null ? gridDetails[1].toString() : "");
			mapDetails.put("sku", gridDetails[2] != null ? gridDetails[2].toString() : "");
			mapDetails.put("ssku", gridDetails[2] != null ? gridDetails[2].toString() : "");
			mapDetails.put("binType", gridDetails[3] != null ? gridDetails[3].toString() : "");

			// Handle integers and potential floats
			mapDetails.put("invQty", gridDetails[4] != null ? parseStringToInt(gridDetails[4].toString()) : 0);
			mapDetails.put("recQty", gridDetails[5] != null ? parseStringToInt(gridDetails[5].toString()) : 0);
			mapDetails.put("shortQty", gridDetails[6] != null ? parseStringToInt(gridDetails[6].toString()) : 0);
			mapDetails.put("damageQty", gridDetails[7] != null ? parseStringToInt(gridDetails[7].toString()) : 0);
			mapDetails.put("grnQty", gridDetails[8] != null ? parseStringToInt(gridDetails[8].toString()) : 0);
			mapDetails.put("noOfBins", gridDetails[9] != null ? parseStringToInt(gridDetails[9].toString()) : 0);
			mapDetails.put("bin", gridDetails[10] != null ? gridDetails[10].toString() : "");
			mapDetails.put("pQty", gridDetails[11] != null ? parseStringToInt(gridDetails[11].toString()) : 0);
			mapDetails.put("batchNo", gridDetails[12] != null ? gridDetails[12].toString() : "");
			mapDetails.put("batchDate", gridDetails[13] != null ? gridDetails[13].toString() : "");
			mapDetails.put("expDate", gridDetails[14] != null ? gridDetails[14].toString() : "");
			mapDetails.put("id", gridDetails[15] != null ? parseStringToInt(gridDetails[15].toString()) : 0);
			getDetails.add(mapDetails);
		}
		return getDetails;
	}

	private int parseStringToInt(String value) {
		try {
			return (int) Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return 0; // default value in case of parsing failure
		}
	}

	private int totalRows = 0;
	private int successfulUploads = 0;
	
    private final DataFormatter dataFormatter = new DataFormatter();


	@Transactional
	@Override
	public void ExcelUploadForPutAway(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse)
			throws ApplicationException, EncryptedDocumentException, java.io.IOException {
		
		 totalRows = 0;
		 successfulUploads = 0;
		    
		List<PutawayExcelUploadVO> putawayExcelUploadVOVOsToSave = new ArrayList<>();
		List<String> errorMessages = new ArrayList<>();

		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				Row headerRow = sheet.getRow(0);

				if (!isHeaderValid(headerRow)) {
					throw new ApplicationException("Invalid Excel format. Please refer to the sample file.");
				}

				// Validate all rows first
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty(row)) {
						continue; // Skip header row and empty rows
					}

					totalRows++; // Increment totalRows
                    System.out.println("Validating row: " + (row.getRowNum() + 1));

					try {
						// Retrieve cell values based on the provided order
						String grnNo1 = getStringCellValue(row.getCell(0));
						String partNo = getStringCellValue(row.getCell(13));
						String binType1 = getStringCellValue(row.getCell(12));
						String binNo1 = getStringCellValue(row.getCell(27));

						// Validate each row

						if (grnRepo.existsByDocIdAndOrgIdAndBranchCodeAndWarehouseAndClient(grnNo1, orgId, branchCode, warehouse, client)) {
							GrnVO grnVO = grnRepo.findByDocId(grnNo1);

//							if(grnDetailsRepo.findByPartNoAndGrnNo(partNo,grnNo1,orgId)) {
//								
//								GrnDetailsVO grnDetailsVO = grnDetailsRepo.findByPartNo(partNo);
                            String binType2=grnDetailsRepo.getBinType(binType1, orgId, branchCode, warehouse, client);
							if (binType2!=null) {
								String binNo2 =grnDetailsRepo.getBinNo(binNo1, binType1,orgId, branchCode, warehouse, client);
								if (binNo2!=null) {

									// Retrieve cell values based on the provided order
									String grnNo = getStringCellValue(row.getCell(0));
									String grnDate = getStringCellValue(row.getCell(1));

									String entryNo = getStringCellValue(row.getCell(2));
									String entryDate = getStringCellValue(row.getCell(3));
									String shortName = getStringCellValue(row.getCell(4));
									String modeOfShipment = getStringCellValue(row.getCell(5));
									String carrier = getStringCellValue(row.getCell(6));
									String type1 = getStringCellValue(row.getCell(7));
									String core = getStringCellValue(row.getCell(8));
									String binPick = getStringCellValue(row.getCell(9));
									String lrHawbhblNo = getStringCellValue(row.getCell(10));
									String indcNo = getStringCellValue(row.getCell(11));
									String binType = getStringCellValue(row.getCell(12));
									String partNo1 = getStringCellValue(row.getCell(13));
									String batchNo1 = getStringCellValue(row.getCell(14));
									String partDesc = getStringCellValue(row.getCell(15));
									String sku = getStringCellValue(row.getCell(16));
									String ssku = getStringCellValue(row.getCell(17));
									Integer invQty = parseInteger(getStringCellValue(row.getCell(18)));
									Integer recQty = parseInteger(getStringCellValue(row.getCell(19)));
									Integer shortQty = parseInteger(getStringCellValue(row.getCell(20)));
									Integer damageQty = parseInteger(getStringCellValue(row.getCell(21)));
									Integer grnQty = parseInteger(getStringCellValue(row.getCell(22)));
									Integer sqty = parseInteger(getStringCellValue(row.getCell(23)));
									Integer ssQty = parseInteger(getStringCellValue(row.getCell(24)));
									Integer sssQty = parseInteger(getStringCellValue(row.getCell(25)));
									Integer binQty = parseInteger(getStringCellValue(row.getCell(26)));
									String binNo = getStringCellValue(row.getCell(27));
									Double weight = parseDouble(getStringCellValue(row.getCell(28)));
									Double rate = parseDouble(getStringCellValue(row.getCell(29)));
									String remarks = getStringCellValue(row.getCell(30));
									String vehicleType = getStringCellValue(row.getCell(31));
									String vehicleNo = getStringCellValue(row.getCell(32));
									String driverName = getStringCellValue(row.getCell(33));
									String contact = getStringCellValue(row.getCell(34));
									String goodsDesc = getStringCellValue(row.getCell(35));
									String securityPersonName = getStringCellValue(row.getCell(36));

									// Validate each row
									// if (putawayExcelUploadRepo.existsByGrnNoAndOrgId(grnNo, orgId)) {
									// errorMessages.add("GRN No " + grnNo + " already exists for this organization.
									// Row: " + (row.getRowNum() + 1));
									// }

									// Create and add the entity to the list
									PutawayExcelUploadVO putawayExcelUploadVO = new PutawayExcelUploadVO();

//									if (!grnVO.getGrnDate().equals(grnDate)) {
//										throw new ApplicationException(
//												"GrnDate " + grnDate + " does not exist for the Grn" +grnNo);
//
//									}

									if (!grnVO.getEntryNo().equals(entryNo)) {

										throw new ApplicationException(
												"EntryNo " + entryNo + " does not exist in the Grn "+grnNo);
									}
//									if (!grnVO.getEntryDate().equals(entryDate)) {
//
//										throw new ApplicationException(
//												"EntryDate " + entryDate + " does not exist in the Grn "+grnNo);
//									}
									if (!grnVO.getSupplierShortName().equals(shortName)) {

										throw new ApplicationException(
												"ShortName " + shortName + " does not exist in the Grn "+grnNo);
									}
									if (!grnVO.getModeOfShipment().equals(modeOfShipment)) {

										throw new ApplicationException("ModeOfShipment " + modeOfShipment
												+ "does not exist in the Grn "+grnNo);
									}
									if (!grnVO.getCarrier().equals(carrier)) {

										throw new ApplicationException(
												"Carrier " + carrier + "does not exist in the Grn "+grnNo);
									}

//								if (!grnDetailsVO.getPartDesc().equals(partDesc)) {
//
//									throw new ApplicationException("partDesc " + partDesc + " do not exist in the Grn Details.");
//								}
//								if (!grnDetailsVO.getBatchNo().equals(batchNo1)) {
//
//									throw new ApplicationException("BatchNo " + batchNo1 + " do not exist in the Grn Details.");
//								}
//								if (!grnDetailsVO.getSku().equals(sku)) {
//
//									throw new ApplicationException("Sku " + sku + " do not exist in the Grn Details.");
//								}

									putawayExcelUploadVO.setGrnNo(grnNo);
									putawayExcelUploadVO.setGrnDate(grnDate);
									putawayExcelUploadVO.setEntryNo(entryNo);
									putawayExcelUploadVO.setEntryDate(entryDate);
									putawayExcelUploadVO.setShortName(shortName);
									putawayExcelUploadVO.setModeOfShipment(modeOfShipment);
									putawayExcelUploadVO.setCarrier(carrier);
									putawayExcelUploadVO.setType(type1);
									putawayExcelUploadVO.setCore(core);
									putawayExcelUploadVO.setBinPick(binPick);
									putawayExcelUploadVO.setLrHawbhblNo(lrHawbhblNo);
									putawayExcelUploadVO.setIndcNo(indcNo);
									putawayExcelUploadVO.setBinType(binType);
									putawayExcelUploadVO.setPartNo(partNo1);
									putawayExcelUploadVO.setBatchNo(batchNo1);
									putawayExcelUploadVO.setPartDesc(partDesc);
									putawayExcelUploadVO.setSku(sku);
									putawayExcelUploadVO.setSsku(ssku);
									putawayExcelUploadVO.setInvQty(invQty);
									putawayExcelUploadVO.setRecQty(recQty);
									putawayExcelUploadVO.setShortQty(shortQty);
									putawayExcelUploadVO.setDamageQty(damageQty);
									putawayExcelUploadVO.setGrnQty(grnQty);
									putawayExcelUploadVO.setSqty(sqty);
									putawayExcelUploadVO.setSsQty(ssQty);
									putawayExcelUploadVO.setSssQty(sssQty);
									putawayExcelUploadVO.setBinQty(binQty);
									putawayExcelUploadVO.setBinNo(binNo);
									putawayExcelUploadVO.setWeight(weight);
									putawayExcelUploadVO.setRate(rate);
									putawayExcelUploadVO.setRemarks(remarks);
									putawayExcelUploadVO.setVehicleType(vehicleType);
									putawayExcelUploadVO.setVehicleNo(vehicleNo);
									putawayExcelUploadVO.setDriverName(driverName);
									putawayExcelUploadVO.setContact(contact);
									putawayExcelUploadVO.setGoodsDesc(goodsDesc);
									putawayExcelUploadVO.setSecurityPersonName(securityPersonName);
									putawayExcelUploadVO.setOrgId(orgId);
									putawayExcelUploadVO.setCustomer(customer);
									putawayExcelUploadVO.setClient(client);
									putawayExcelUploadVO.setFinYear(finYear);
									putawayExcelUploadVO.setBranch(branch);
									putawayExcelUploadVO.setBranchCode(branchCode);
									putawayExcelUploadVO.setWarehouse(warehouse);
									putawayExcelUploadVO.setCreatedBy(createdBy);
									putawayExcelUploadVO.setUpdatedBy(""); // Assuming you set this later or leave it
																			// empty
									putawayExcelUploadVO.setActive(true); // Default or based on some logic
									putawayExcelUploadVO.setCancel(false); // Default or based on some logic
									putawayExcelUploadVO.setCancelRemarks(""); // Default or based on some logic

									putawayExcelUploadVOVOsToSave.add(putawayExcelUploadVO);
									successfulUploads++; // Increment successfulUploads

								} else {
									throw new ApplicationException( "BinNo " + binNo1
											+ " does not exist for this client.");
								}
							} else {
								throw new ApplicationException("BinType" + binType1 + " does not exists for this client");

							}
						}

						else {
							throw new ApplicationException("GrnNo " + grnNo1 + " does not exist for this client.");
						}
					} catch (Exception e) {
						throw new ApplicationException("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// Save all valid rows
				putawayExcelUploadRepo.saveAll(putawayExcelUploadVOVOsToSave);

			} catch (IOException e) {
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}
	}

	private Double parseDouble(String stringCellValue) {
		try {
			return Double.parseDouble(stringCellValue);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private LocalDate parseDate(String stringCellValue) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
			return LocalDate.parse(stringCellValue, formatter);
		} catch (Exception e) {
			return null;
		}
	}

	private Integer parseInteger(String stringCellValue) {
		try {
			return Integer.parseInt(stringCellValue);
		} catch (NumberFormatException e) {
			return null;
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

	private boolean isHeaderValid(Row headerRow) {
		if (headerRow == null) {
			return false;
		}
		int expectedColumnCount = 37; // Adjust based on the actual number of columns
		if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
			return false;
		}
		return "grnno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
				&& "grndate".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)))
				&& "entryno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2)))
				&& "entrydate".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)))
				&& "shortname".equalsIgnoreCase(getStringCellValue(headerRow.getCell(4)))
				&& "modeofshipment".equalsIgnoreCase(getStringCellValue(headerRow.getCell(5)))
				&& "carrier".equalsIgnoreCase(getStringCellValue(headerRow.getCell(6)))
				&& "type".equalsIgnoreCase(getStringCellValue(headerRow.getCell(7)))
				&& "core".equalsIgnoreCase(getStringCellValue(headerRow.getCell(8)))
				&& "binpick".equalsIgnoreCase(getStringCellValue(headerRow.getCell(9)))
				&& "lrhawbhblno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(10)))
				&& "indcno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(11)))
				&& "bintype".equalsIgnoreCase(getStringCellValue(headerRow.getCell(12)))
				&& "partno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(13)))
				&& "batchno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(14)))
				&& "partdesc".equalsIgnoreCase(getStringCellValue(headerRow.getCell(15)))
				&& "sku".equalsIgnoreCase(getStringCellValue(headerRow.getCell(16)))
				&& "ssku".equalsIgnoreCase(getStringCellValue(headerRow.getCell(17)))
				&& "invqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(18)))
				&& "recqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(19)))
				&& "shortqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(20)))
				&& "damageqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(21)))
				&& "grnqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(22)))
				&& "sqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(23)))
				&& "ssqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(24)))
				&& "sssqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(25)))
				&& "binqty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(26)))
				&& "binno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(27)))
				&& "weight".equalsIgnoreCase(getStringCellValue(headerRow.getCell(28)))
				&& "rate".equalsIgnoreCase(getStringCellValue(headerRow.getCell(29)))
				&& "remarks".equalsIgnoreCase(getStringCellValue(headerRow.getCell(30)))
				&& "vehicletype".equalsIgnoreCase(getStringCellValue(headerRow.getCell(31)))
				&& "vehicleno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(32)))
				&& "drivername".equalsIgnoreCase(getStringCellValue(headerRow.getCell(33)))
				&& "contact".equalsIgnoreCase(getStringCellValue(headerRow.getCell(34)))
				&& "goodsdesc".equalsIgnoreCase(getStringCellValue(headerRow.getCell(35)))
				&& "securitypersonname".equalsIgnoreCase(getStringCellValue(headerRow.getCell(36)));
	}

	 private String getStringCellValue(Cell cell) {
	        if (cell == null) {
	            return ""; // Return empty string if cell is null
	        }

	        // Use DataFormatter to get the cell value as a string
	        return dataFormatter.formatCellValue(cell);
	    }

	@Override
	public int getTotalRows() {
		return totalRows; // Return the correct value
	}

	@Override
	public int getSuccessfulUploads() {
		return successfulUploads; // Return the correct value
	}

	@Override
	public List<Map<String, Object>> getPutawayForDashBoard(Long orgId, String finYear, String branchCode,
			String client, String warehouse) {
		Set<Object[]> getputawayStatus = putAwayRepo.getPutaway(orgId, finYear, branchCode, client,warehouse);
		return getPutawayForDashBoard(getputawayStatus);
	}

	private List<Map<String, Object>> getPutawayForDashBoard(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("entryNo", grid[0] != null ? grid[0].toString() : "");
			details.put("entryDate", grid[1] != null ? grid[1].toString() : "");
			details.put("status", grid[2] != null ? grid[2].toString() : "");
			
			gridDetails.add(details);
		}
		return gridDetails;
	}


}
