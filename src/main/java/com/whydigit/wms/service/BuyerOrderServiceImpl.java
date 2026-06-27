package com.whydigit.wms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.BuyerOrderDetailsDTO;
import com.whydigit.wms.dto.CustomerAttachmentType;
import com.whydigit.wms.dto.MultipleBODTO;
import com.whydigit.wms.entity.BoExcelUploadVO;
import com.whydigit.wms.entity.BuyerOrderDetailsVO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.HandlingStockOutVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BoExcelUploadRepo;
import com.whydigit.wms.repo.BuyerOrderDetailsRepo;
import com.whydigit.wms.repo.BuyerOrderRepo;
import com.whydigit.wms.repo.BuyerRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.HandlingStockOutRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class BuyerOrderServiceImpl implements BuyerOrderService {

	@Autowired
	BuyerOrderRepo buyerOrderRepo;

	@Autowired
	BuyerOrderDetailsRepo buyerOrderDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	BuyerRepo buyerRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	HandlingStockOutRepo handlingStockOutRepo;

	@Autowired
	BoExcelUploadRepo boExcelUploadRepo;

	// BuyerOrder

//	private LocalDate parseDate(String dateStr) {
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH); // Define expected input format
//	    return LocalDate.parse(dateStr, formatter); // Parse date string into LocalDate
//	}

	@Override
	public Map<String, Object> createUpdateBuyerOrder(BuyerOrderDTO buyerOrderDTO) throws ApplicationException {
		String screenCode = "BO";
		BuyerOrderVO buyerOrderVO;
		String message = null;

		if (ObjectUtils.isEmpty(buyerOrderDTO.getId())) {

			if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(buyerOrderDTO.getOrderNo(),
					buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(), buyerOrderDTO.getCustomer())) {
				String errorMessage = String.format("This orderNo:%s Already Exists This Client.",
						buyerOrderDTO.getOrderNo());
				throw new ApplicationException(errorMessage);
			}

			buyerOrderVO = new BuyerOrderVO();

			// GETDOCID API
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

				if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(buyerOrderDTO.getOrderNo(),
						buyerOrderDTO.getOrgId(), buyerOrderDTO.getClient(), buyerOrderDTO.getCustomer())) {
					String errorMessage = String.format("This orderNo:%s Already Exists This Client.",
							buyerOrderDTO.getOrderNo());
					throw new ApplicationException(errorMessage);
				}
				buyerOrderVO.setOrderNo(buyerOrderDTO.getOrderNo());
			}
			message = "BuyerOrder Updation Successfully";
		}

		getBuyerOrderVOfromBuyerOrderDTO(buyerOrderVO, buyerOrderDTO);
		BuyerOrderVO buyerOrderVO2 = buyerOrderRepo.save(buyerOrderVO);
		List<BuyerOrderDetailsVO> buyerOrderDetailsVO2 = buyerOrderVO2.getBuyerOrderDetailsVO();

		List<HandlingStockOutVO> handling = handlingStockOutRepo.findBySDocid(buyerOrderVO2.getDocId());

		if (!handling.isEmpty()) {
			List<HandlingStockOutVO> handlingStockOutVOs = handlingStockOutRepo.findBySDocid(buyerOrderVO2.getDocId());
			handlingStockOutRepo.deleteAll(handlingStockOutVOs);
		}

		for (BuyerOrderDetailsVO buyerOrderDetailsVOs2 : buyerOrderDetailsVO2) {
			HandlingStockOutVO handlingStockOutVO = new HandlingStockOutVO();

			handlingStockOutVO.setOrgId(buyerOrderVO2.getOrgId());
			handlingStockOutVO.setBranch(buyerOrderVO2.getBranch());
			handlingStockOutVO.setBranchCode(buyerOrderVO2.getBranchCode());
			handlingStockOutVO.setWarehouse(buyerOrderVO2.getWarehouse());
			handlingStockOutVO.setCustomer(buyerOrderVO2.getCustomer());
			handlingStockOutVO.setClient(buyerOrderVO2.getClient());
			handlingStockOutVO.setRefNo(buyerOrderVO2.getOrderNo());
			handlingStockOutVO.setRefDate(buyerOrderVO2.getOrderDate());
			handlingStockOutVO.setPartNo(buyerOrderDetailsVOs2.getPartNo());
			handlingStockOutVO.setPartDesc(buyerOrderDetailsVOs2.getPartDesc());
			handlingStockOutVO.setSku(buyerOrderDetailsVOs2.getSku());
			handlingStockOutVO.setBuyerOrderNo(buyerOrderVO2.getOrderNo());
			handlingStockOutVO.setBuyerOrderDate(buyerOrderVO2.getOrderDate());
			handlingStockOutVO.setBuyerOrdNo(buyerOrderVO.getDocId());
			handlingStockOutVO.setSDocid(buyerOrderVO2.getDocId());
			handlingStockOutVO.setRpQty(buyerOrderDetailsVOs2.getQty());
			handlingStockOutVO.setSQty(buyerOrderDetailsVOs2.getQty());
			handlingStockOutVO.setPickQty(0);
			handlingStockOutVO.setScreenCode(buyerOrderVO.getScreenCode());
			handlingStockOutVO.setBuyerOrdDate(buyerOrderVO.getDocDate());
			handlingStockOutRepo.save(handlingStockOutVO);
		}

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
		buyerOrderVO.setBuyer(buyerOrderDTO.getBuyer());
		buyerOrderVO.setBuyerShortName(buyerOrderDTO.getBuyerShortName());
		BuyerVO buyerVO = buyerRepo.findByBuyerAndOrgId(buyerOrderDTO.getBuyer(), buyerOrderDTO.getOrgId());
		buyerOrderVO
				.setBuyerAddress(buyerVO.getAddressLine1() + "," + buyerVO.getAddressLine2() + "," + buyerVO.getCity()
						+ "," + buyerVO.getState() + "," + buyerVO.getCountry() + "," + buyerVO.getZipCode());
		buyerOrderVO.setBillToShortName(buyerOrderDTO.getBillToShortName());
		buyerOrderVO.setBillToName(buyerOrderDTO.getBillToName());
		BuyerVO buyerVO1 = buyerRepo.findByBuyerAndOrgId(buyerOrderDTO.getBillToName(), buyerOrderDTO.getOrgId());
		buyerOrderVO.setBillToAddress(
				buyerVO1.getAddressLine1() + "," + buyerVO1.getAddressLine2() + "," + buyerVO1.getCity() + ","
						+ buyerVO1.getState() + "," + buyerVO1.getCountry() + "," + buyerVO1.getZipCode());
		buyerOrderVO.setShipToShortName(buyerOrderDTO.getShipToShortName());
		buyerOrderVO.setShipToName(buyerOrderDTO.getShipToName());
		buyerOrderVO.setInvoiceDate(buyerOrderDTO.getInvoiceDate());
		buyerOrderVO.setRefNo(buyerOrderDTO.getRefNo());
		buyerOrderVO.setCustomer(buyerOrderDTO.getCustomer());
		buyerOrderVO.setClient(buyerOrderDTO.getClient());
		buyerOrderVO.setFinYear(buyerOrderDTO.getFinYear());
		buyerOrderVO.setBranch(buyerOrderDTO.getBranch());
		buyerOrderVO.setBranchCode(buyerOrderDTO.getBranchCode());
		buyerOrderVO.setWarehouse(buyerOrderDTO.getWarehouse());
		buyerOrderVO.setRemarks(buyerOrderDTO.getRemarks());

		if (buyerOrderDTO.getId() != null) {
			List<BuyerOrderDetailsVO> detailsVOs = buyerOrderDetailsRepo.findByBuyerOrderVO(buyerOrderVO);
			buyerOrderDetailsRepo.deleteAll(detailsVOs);
		}

		int orderQty = 0;
		int avilQty = 0;

		List<BuyerOrderDetailsVO> detailsVOList = new ArrayList<BuyerOrderDetailsVO>();
		for (BuyerOrderDetailsDTO buyerOrderDetailsDTO : buyerOrderDTO.getBuyerOrderDetailsDTO()) {

			BuyerOrderDetailsVO detailsVO = new BuyerOrderDetailsVO();
			detailsVO.setPartNo(buyerOrderDetailsDTO.getPartNo());
			detailsVO.setPartDesc(buyerOrderDetailsDTO.getPartDesc());
			detailsVO.setQty(buyerOrderDetailsDTO.getQty());
			detailsVO.setBatchNo(buyerOrderDetailsDTO.getBatchNo());
			detailsVO.setAvailQty(buyerOrderDetailsDTO.getAvailQty());
			detailsVO.setSku(buyerOrderDetailsDTO.getSku());
			detailsVO.setExpDate(buyerOrderDetailsDTO.getExpDate());
			detailsVO.setBatchDate(buyerOrderDetailsDTO.getBatchDate());

			avilQty = avilQty + buyerOrderDetailsDTO.getAvailQty();
			orderQty = orderQty + buyerOrderDetailsDTO.getQty();

			detailsVO.setBuyerOrderVO(buyerOrderVO);
			detailsVOList.add(detailsVO);
		}
		buyerOrderVO.setTotalOrderQty(orderQty);
		buyerOrderVO.setTotalAvailQty(avilQty);
		buyerOrderVO.setBuyerOrderDetailsVO(detailsVOList);
		return buyerOrderVO;
	}

	@Override
	public Optional<BuyerOrderVO> getAllBuyerOrderById(Long id) {

		return buyerOrderRepo.findAllBuyerOrderById(id);
	}

	@Override
	public String getBuyerOrderDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "BO";
		String result = buyerOrderRepo.getbuyerOrderDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<Map<String, Object>> getBoSkuDetails(Long orgId, String branchCode, String client, String warehouse) {
		Set<Object[]> result = buyerOrderRepo.getBoSku(orgId, branchCode, client, warehouse);
		return getAllSkuDetails(result);
	}

	private List<Map<String, Object>> getAllSkuDetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("batch", fs[2] != null ? fs[2].toString() : "");
			part.put("expDate", fs[3] != null ? fs[3].toString() : "");
			part.put("sqty", fs[4] != null ? Integer.parseInt(fs[4].toString()) : 0);
			part.put("sku", fs[5] != null ? fs[5].toString() : "");
			part.put("id", fs[6] != null ? Integer.parseInt(fs[6].toString()) : 0);

			details1.add(part);
		}
		return details1;

	}

	@Override
	public int getAvlQtyByBO(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String batch) {
		int result = buyerOrderRepo.getAvilableQty(orgId, client, branchCode, warehouse, branch, partNo, batch);
		return result;
	}

	@Override
	public List<BuyerOrderVO> getAllBuyerOrderByOrgId(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return buyerOrderRepo.findByBo(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public List<Map<String, Object>> getBatchByBuyerOrder(Long orgId, String branchCode, String client,
			String warehouse, String partNo) {
		Set<Object[]> result = stockDetailsRepo.getDetails(orgId, branchCode, client, warehouse, partNo);
		return getBatch1(result);
	}

	private List<Map<String, Object>> getBatch1(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("batch", fs[0] != null ? fs[0].toString() : "");
			part.put("expDate", fs[1] != null ? fs[1].toString() : "");
			part.put("batchDate", fs[2] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) fs[2]) : "");
			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getPartNoByBuyerOrder(Long orgId, String branchCode, String client,
			String warehouse) {
		Set<Object[]> result = stockDetailsRepo.getPartNo(orgId, branchCode, client, warehouse);
		return getPartNoByBO(result);
	}

	private List<Map<String, Object>> getPartNoByBO(Set<Object[]> result) {
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

	@Override
	public int getAvlQtyForBuyerOrder(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String batchNo) {

		return stockDetailsRepo.getAvlQtyforBuyerOrder(orgId, branchCode, client, warehouse, partNo, batchNo);
	}

	private int totalRows = 0;
	private int successfulUploads = 0;

	@Transactional
	@Override
	public void ExcelUploadForBo(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
	        String customer, String client, String finYear, String branch, String branchCode, String warehouse)
	        throws ApplicationException {

	    List<BoExcelUploadVO> boExcelUploadVOVOsToSave = new ArrayList<>();
	    List<String> allErrorMessages = new ArrayList<>();
	    totalRows = 0;
	    successfulUploads = 0;

	    if (files == null || files.length == 0) {
	        throw new ApplicationException("No files uploaded. Please select at least one Excel file.");
	    }

	    for (MultipartFile file : files) {
	        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            List<String> fileErrors = new ArrayList<>();
	            System.out.println("Processing file: " + file.getOriginalFilename());

	            if (sheet.getPhysicalNumberOfRows() <= 1) {
	                fileErrors.add("File '" + file.getOriginalFilename() + "' has no data rows.");
	                allErrorMessages.addAll(fileErrors);
	                continue;
	            }

	            Row headerRow = sheet.getRow(0);
	            if (!isHeaderValid(headerRow)) {
	                throw new ApplicationException("Invalid Excel format for file: " + file.getOriginalFilename()
	                        + ". Please refer to the sample file.");
	            }

	            for (Row row : sheet) {
	                if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                    continue;
	                }

	                totalRows++;
	                int rowNum = row.getRowNum() + 1;
	                System.out.println("Processing row: " + rowNum);

	                try {
	                    // Get all cell values
	                    String type1 = getStringCellValue(row.getCell(0));
	                    String orderNo = getStringCellValue(row.getCell(1));
	                    LocalDate orderDate = parseDate(getStringCellValue(row.getCell(2)));
	                    String invoiceNo = getStringCellValue(row.getCell(3));
	                    LocalDate invoiceDate = parseDate(getStringCellValue(row.getCell(4)));
	                    String referenceNo = getStringCellValue(row.getCell(5));
	                    LocalDate referenceDate = parseDate(getStringCellValue(row.getCell(6)));
	                    String buyerName = getStringCellValue(row.getCell(7));
	                    String billTo = getStringCellValue(row.getCell(8));
	                    String shipTo = getStringCellValue(row.getCell(9));
	                    String partNo = getStringCellValue(row.getCell(10));
	                    String partDesc = getStringCellValue(row.getCell(11));
	                    String batchNo = getStringCellValue(row.getCell(12));
	                    String sku = getStringCellValue(row.getCell(13));
	                    String qtyStr = getStringCellValue(row.getCell(14));
	                    String unitRateStr = getStringCellValue(row.getCell(15));
	                    String remark = getStringCellValue(row.getCell(16));

	                    System.out.println("Row " + rowNum + " - PartNo: '" + partNo + "', BatchNo: '" + batchNo
	                            + "', Qty: '" + qtyStr + "', UnitRate: '" + unitRateStr + "'");

	                    // ===== VALIDATION SECTION =====
	                    boolean hasError = false;

	                    // 1. Validate Order No (Required)
	                    if (orderNo == null || orderNo.trim().isEmpty()) {
	                        fileErrors.add("Order No is missing in row " + rowNum);
	                        hasError = true;
	                    }

	                    // 2. Validate Part No (Required)
	                    if (partNo == null || partNo.trim().isEmpty()) {
	                        fileErrors.add("Part No is missing in row " + rowNum);
	                        hasError = true;
	                    }

	                    // 3. Validate Batch No (Required)
	                    if (batchNo == null || batchNo.trim().isEmpty()) {
	                        fileErrors.add("Batch No is missing in row " + rowNum);
	                        hasError = true;
	                    }

	                    // 4. Validate Quantity (Required)
	                    Integer qty = null;
	                    if (qtyStr == null || qtyStr.trim().isEmpty()) {
	                        fileErrors.add("Quantity is missing in row " + rowNum);
	                        hasError = true;
	                    } else {
	                        qty = parseInteger(qtyStr);
	                        if (qty == null || qty <= 0) {
	                            fileErrors.add("Invalid quantity '" + qtyStr + "' in row " + rowNum
	                                    + ". Must be a positive number.");
	                            hasError = true;
	                        }
	                    }

	                    // 5. Validate Unit Rate (Optional - only if provided)
	                    Double unitRate = null;
	                    if (unitRateStr != null && !unitRateStr.trim().isEmpty()) {
	                        unitRate = parseDouble(unitRateStr);
	                        if (unitRate == null) {
	                            fileErrors.add("Invalid unit rate '" + unitRateStr + "' in row " + rowNum);
	                            hasError = true;
	                        }
	                    }

	                    // If there are validation errors, skip this row
	                    if (hasError) {
	                        continue;
	                    }

	                    // 6. Check if stock exists for this part
	                    if (partNo != null && !partNo.trim().isEmpty()) {
	                        boolean partExists = stockDetailsRepo.existsByPartnoAndOrgIdAndClient(partNo.trim(), orgId,
	                                client);
	                        System.out.println("Part exists: " + partExists + " for PartNo: " + partNo);

	                        if (!partExists) {
	                            fileErrors.add("Part No '" + partNo + "' does not exist in stock. Row: " + rowNum);
	                            continue;
	                        }

	                        // 7. Check if batch exists for this part
	                        if (batchNo != null && !batchNo.trim().isEmpty()) {
	                            boolean batchExists = stockDetailsRepo.existsByPartnoAndBatchAndOrgIdAndClient(
	                                    partNo.trim(), batchNo.trim(), orgId, client);
	                            System.out.println("Batch exists: " + batchExists + " for BatchNo: " + batchNo);

	                            if (!batchExists) {
	                                fileErrors.add("Batch No '" + batchNo + "' for Part '" + partNo
	                                        + "' does not exist in stock. Row: " + rowNum);
	                                continue;
	                            }
	                        }
	                    }

	                    // 8. Check duplicate order
	                    boolean orderExists = buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(orderNo.trim(),
	                            orgId, client, customer);
	                    if (orderExists) {
	                        fileErrors.add("Order No '" + orderNo + "' already exists. Row: " + rowNum);
	                        continue;
	                    }

	                    // ===== CREATE AND SAVE OBJECT =====
	                    BoExcelUploadVO boExcelUploadVO = new BoExcelUploadVO();
	                    boExcelUploadVO.setType(type1 != null ? type1.trim() : "");
	                    boExcelUploadVO.setOrderNo(orderNo != null ? orderNo.trim() : "");
	                    boExcelUploadVO.setOrderDate(orderDate);
	                    boExcelUploadVO.setInvoiceNo(invoiceNo != null ? invoiceNo.trim() : "");
	                    boExcelUploadVO.setInvoiceDate(invoiceDate);
	                    boExcelUploadVO.setReferenceNo(referenceNo != null ? referenceNo.trim() : "");
	                    boExcelUploadVO.setReferenceDate(referenceDate);
	                    boExcelUploadVO.setBuyerName(buyerName != null ? buyerName.trim() : "");
	                    boExcelUploadVO.setBillTo(billTo != null ? billTo.trim() : "");
	                    boExcelUploadVO.setShipTo(shipTo != null ? shipTo.trim() : "");
	                    boExcelUploadVO.setPartNo(partNo != null ? partNo.trim() : "");
	                    boExcelUploadVO.setPartDesc(partDesc != null ? partDesc.trim() : "");
	                    boExcelUploadVO.setBatchNo(batchNo != null ? batchNo.trim() : "");
	                    boExcelUploadVO.setSku(sku != null ? sku.trim() : "");
	                    boExcelUploadVO.setQty(qty);
	                    boExcelUploadVO.setUnitRate(unitRate);
	                    boExcelUploadVO.setRemark(remark != null ? remark.trim() : "");
	                    boExcelUploadVO.setOrgId(orgId);
	                    boExcelUploadVO.setCustomer(customer != null ? customer.trim() : "");
	                    boExcelUploadVO.setClient(client != null ? client.trim() : "");
	                    boExcelUploadVO.setFinYear(finYear != null ? finYear.trim() : "");
	                    boExcelUploadVO.setBranch(branch != null ? branch.trim() : "");
	                    boExcelUploadVO.setBranchCode(branchCode != null ? branchCode.trim() : "");
	                    boExcelUploadVO.setWarehouse(warehouse != null ? warehouse.trim() : "");
	                    boExcelUploadVO.setCreatedBy(createdBy != null ? createdBy.trim() : "");
	                    boExcelUploadVO.setUpdatedBy("");
	                    boExcelUploadVO.setActive(true);
	                    boExcelUploadVO.setCancel(false);
	                    boExcelUploadVO.setCancelRemarks("");

	                    boExcelUploadVOVOsToSave.add(boExcelUploadVO);
	                    successfulUploads++;
	                    System.out.println("Row " + rowNum + " validated and added for saving");

	                } catch (Exception e) {
	                    fileErrors.add("Error processing row " + rowNum + ": " + e.getMessage());
	                    e.printStackTrace();
	                }
	            }

	            if (!fileErrors.isEmpty()) {
	                allErrorMessages.addAll(fileErrors);
	                System.out.println("Found " + fileErrors.size() + " errors in file: " + file.getOriginalFilename());
	                for (String error : fileErrors) {
	                    System.out.println("  - " + error);
	                }
	            }

	        } catch (IOException e) {
	            throw new ApplicationException(
	                    "Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
	        }
	    }

	    if (!allErrorMessages.isEmpty()) {
	        String errorSummary = "Excel upload validation failed with " + allErrorMessages.size() + " errors: "
	                + String.join("; ", allErrorMessages);
	        System.out.println("ERROR SUMMARY: " + errorSummary);
	        throw new ApplicationException(errorSummary);
	    }

	    if (!boExcelUploadVOVOsToSave.isEmpty()) {
	        boExcelUploadRepo.saveAll(boExcelUploadVOVOsToSave);
	        System.out.println("Successfully saved " + boExcelUploadVOVOsToSave.size() + " records");
	    } else {
	        if (totalRows == 0) {
	            throw new ApplicationException("No data rows found in the uploaded file(s). Please check your data.");
	        } else {
	            throw new ApplicationException("No valid records found to save. " + totalRows
	                    + " row(s) processed, but all failed validation.");
	        }
	    }
	}

	// ===== HELPER METHODS =====

	private LocalDate parseDate(String stringCellValue) {
	    if (stringCellValue == null || stringCellValue.trim().isEmpty()) {
	        return null;
	    }
	    try {
	        String value = stringCellValue.trim();
	        String[] formats = { "dd/MM/yyyy", "dd/M/yyyy", "d/M/yyyy", "yyyy-MM-dd", "dd-MM-yyyy" };
	        for (String format : formats) {
	            try {
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	                return LocalDate.parse(value, formatter);
	            } catch (DateTimeParseException e) {
	                // Try next format
	            }
	        }
	        return null;
	    } catch (Exception e) {
	        System.err.println("Error parsing date: " + stringCellValue);
	        return null;
	    }
	}

	private boolean isRowEmpty(Row row) {
	    if (row == null)
	        return true;
	    for (Cell cell : row) {
	        if (cell != null && cell.getCellType() != CellType.BLANK) {
	            String value = getStringCellValue(cell);
	            if (value != null && !value.trim().isEmpty()) {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	private boolean isHeaderValid(Row headerRow) {
	    if (headerRow == null) {
	        return false;
	    }

	    String[] expectedHeaders = { "type", "order no", "order date", "invoice no", "invoice date", "reference no",
	            "reference date", "buyer name", "bill to", "ship to", "part no", "part desc", "batchno", "sku", "qty",
	            "unit rate", "remark" };

	    for (int i = 0; i < expectedHeaders.length; i++) {
	        String headerValue = getStringCellValue(headerRow.getCell(i)).toLowerCase().trim();
	        if (!expectedHeaders[i].equalsIgnoreCase(headerValue)) {
	            System.out.println("Header mismatch at index " + i + ": Expected '" + expectedHeaders[i] + "', Found '"
	                    + headerValue + "'");
	            return false;
	        }
	    }
	    return true;
	}

	private String getStringCellValue(Cell cell) {
	    if (cell == null) {
	        return "";
	    }

	    try {
	        switch (cell.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue().trim();
	            case NUMERIC:
	                if (DateUtil.isCellDateFormatted(cell)) {
	                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                    return sdf.format(cell.getDateCellValue());
	                } else {
	                    double numericValue = cell.getNumericCellValue();
	                    if (numericValue == (int) numericValue) {
	                        return String.valueOf((int) numericValue);
	                    } else {
	                        return BigDecimal.valueOf(numericValue).toPlainString();
	                    }
	                }
	            case BOOLEAN:
	                return String.valueOf(cell.getBooleanCellValue());
	            case FORMULA:
	                try {
	                    return String.valueOf(cell.getNumericCellValue());
	                } catch (Exception e) {
	                    return cell.getCellFormula();
	                }
	            case BLANK:
	                return "";
	            default:
	                return "";
	        }
	    } catch (Exception e) {
	        System.err.println("Error reading cell: " + e.getMessage());
	        return "";
	    }
	}

	private Integer parseInteger(String stringCellValue) {
	    if (stringCellValue == null || stringCellValue.trim().isEmpty()) {
	        return null;
	    }
	    try {
	        String value = stringCellValue.trim().replace(",", "").replace(" ", "");
	        return new BigDecimal(value).intValue();
	    } catch (NumberFormatException e) {
	        System.err.println("Error parsing integer: '" + stringCellValue + "'");
	        return null;
	    }
	}

	private Double parseDouble(String stringCellValue) {
	    if (stringCellValue == null || stringCellValue.trim().isEmpty()) {
	        return null;
	    }
	    try {
	        String value = stringCellValue.trim().replace(",", "").replace(" ", "");
	        return Double.parseDouble(value);
	    } catch (NumberFormatException e) {
	        System.err.println("Error parsing double: '" + stringCellValue + "'");
	        return null;
	    }
	}

	@Override
	public int getTotalRows() {
	    return totalRows;
	}

	@Override
	public int getSuccessfulUploads() {
	    return successfulUploads;
	}

	// multiple Buyer Order

	@Override
	public Map<String, Object> createMultipleBuyerOrder(List<MultipleBODTO> multipleBODTO1)
			throws ApplicationException {

		String errorMessage;
		String message = null;
		for (MultipleBODTO multipleBODTO : multipleBODTO1) {
			List<BoExcelUploadVO> boExcelUploadVO = boExcelUploadRepo.findByOrderNoAndOrgIdAndClientAndBranchCode(
					multipleBODTO.getOrderNo(), multipleBODTO.getOrgId(), multipleBODTO.getClient(),
					multipleBODTO.getBranchCode());

			if (boExcelUploadVO == null || boExcelUploadVO.isEmpty()) {
				errorMessage = String.format("This orderNo:%s Not found ", multipleBODTO.getOrderNo());
				throw new ApplicationException(errorMessage);
			}

			String screenCode = "BO";
			BuyerOrderVO buyerOrderVO;

			if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(multipleBODTO.getOrderNo(),
					multipleBODTO.getOrgId(), multipleBODTO.getClient(), multipleBODTO.getCustomer())) {
				errorMessage = String.format("This orderNo:%s Already Exists This Client.", multipleBODTO.getOrderNo());
				throw new ApplicationException(errorMessage);
			}

			buyerOrderVO = new BuyerOrderVO();

			// GETDOCID API
			String docId = buyerOrderRepo.getbuyerOrderDocId(multipleBODTO.getOrgId(), multipleBODTO.getFinYear(),
					multipleBODTO.getBranchCode(), multipleBODTO.getClient(), screenCode);
			buyerOrderVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(multipleBODTO.getOrgId(),
							multipleBODTO.getFinYear(), multipleBODTO.getBranchCode(), multipleBODTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			buyerOrderVO.setCreatedBy(multipleBODTO.getCreatedBy());
			buyerOrderVO.setUpdatedBy(multipleBODTO.getCreatedBy());

			message = "BuyerOrder Creation Successfully";

			getBuyerOrderVOfromMultipleBODTO(buyerOrderVO, boExcelUploadVO, multipleBODTO);
			BuyerOrderVO buyerOrderVO2 = buyerOrderRepo.save(buyerOrderVO);
			List<BuyerOrderDetailsVO> buyerOrderDetailsVO2 = buyerOrderVO2.getBuyerOrderDetailsVO();

			List<HandlingStockOutVO> handling = handlingStockOutRepo.findBySDocid(buyerOrderVO2.getDocId());

			if (!handling.isEmpty()) {
				List<HandlingStockOutVO> handlingStockOutVOs = handlingStockOutRepo
						.findBySDocid(buyerOrderVO2.getDocId());
				handlingStockOutRepo.deleteAll(handlingStockOutVOs);
			}

			for (BuyerOrderDetailsVO buyerOrderDetailsVOs2 : buyerOrderDetailsVO2) {
				HandlingStockOutVO handlingStockOutVO = new HandlingStockOutVO();

				handlingStockOutVO.setOrgId(buyerOrderVO2.getOrgId());
				handlingStockOutVO.setBranch(buyerOrderVO2.getBranch());
				handlingStockOutVO.setBranchCode(buyerOrderVO2.getBranchCode());
				handlingStockOutVO.setWarehouse(buyerOrderVO2.getWarehouse());
				handlingStockOutVO.setCustomer(buyerOrderVO2.getCustomer());
				handlingStockOutVO.setClient(buyerOrderVO2.getClient());
				handlingStockOutVO.setRefNo(buyerOrderVO2.getOrderNo());
				handlingStockOutVO.setRefDate(buyerOrderVO2.getOrderDate());
				handlingStockOutVO.setPartNo(buyerOrderDetailsVOs2.getPartNo());
				handlingStockOutVO.setPartDesc(buyerOrderDetailsVOs2.getPartDesc());
				handlingStockOutVO.setSku(buyerOrderDetailsVOs2.getSku());
				handlingStockOutVO.setBuyerOrderNo(buyerOrderVO2.getOrderNo());
				handlingStockOutVO.setBuyerOrderDate(buyerOrderVO2.getOrderDate());
				handlingStockOutVO.setBuyerOrdNo(buyerOrderVO.getDocId());
				handlingStockOutVO.setSDocid(buyerOrderVO2.getDocId());
				handlingStockOutVO.setRpQty(buyerOrderDetailsVOs2.getQty());
				handlingStockOutVO.setSQty(buyerOrderDetailsVOs2.getQty());
				handlingStockOutVO.setPickQty(0);
				handlingStockOutVO.setScreenCode(buyerOrderVO.getScreenCode());
				handlingStockOutVO.setBuyerOrdDate(buyerOrderVO.getDocDate());
				handlingStockOutRepo.save(handlingStockOutVO);
			}
		}
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		return response;
	}

	private BuyerOrderVO getBuyerOrderVOfromMultipleBODTO(BuyerOrderVO buyerOrderVO,
			List<BoExcelUploadVO> boExcelUploadVO, MultipleBODTO multipleBODTO) {
		buyerOrderVO.setOrderNo(multipleBODTO.getOrderNo());
		buyerOrderVO.setOrgId(multipleBODTO.getOrgId());
		buyerOrderVO.setOrderDate(multipleBODTO.getOrderDate());
		buyerOrderVO.setInvoiceNo(multipleBODTO.getInvoiceNo());
		buyerOrderVO.setRefDate(multipleBODTO.getRefDate());
		buyerOrderVO.setBuyer(multipleBODTO.getBuyerName());

		BuyerVO buyerVO = buyerRepo.findByBuyerAndOrgId(multipleBODTO.getBuyerName(), multipleBODTO.getOrgId());
		buyerOrderVO.setBuyerShortName(buyerVO.getBuyerShortName());
		buyerOrderVO
				.setBuyerAddress(buyerVO.getAddressLine1() + "," + buyerVO.getAddressLine2() + "," + buyerVO.getCity()
						+ "," + buyerVO.getState() + "," + buyerVO.getCountry() + "," + buyerVO.getZipCode());

		BuyerVO buyerVO1 = buyerRepo.findByBuyerAndOrgId(multipleBODTO.getBillToName(), multipleBODTO.getOrgId());
		buyerOrderVO.setBillToName(multipleBODTO.getBillToName());
		buyerOrderVO.setBillToShortName(buyerVO1.getBuyerShortName());
		buyerOrderVO.setBillToAddress(
				buyerVO1.getAddressLine1() + "," + buyerVO1.getAddressLine2() + "," + buyerVO1.getCity() + ","
						+ buyerVO1.getState() + "," + buyerVO1.getCountry() + "," + buyerVO1.getZipCode());
		BuyerVO buyerVO2 = buyerRepo.findByBuyerAndOrgId(multipleBODTO.getShipToName(), multipleBODTO.getOrgId());
		buyerOrderVO.setShipToName(multipleBODTO.getShipToName());
		buyerOrderVO.setShipToShortName(buyerVO2.getBuyerShortName());
		buyerOrderVO.setInvoiceDate(multipleBODTO.getInvoiceDate());
		buyerOrderVO.setRefNo(multipleBODTO.getRefNo());
		buyerOrderVO.setCustomer(multipleBODTO.getCustomer());
		buyerOrderVO.setClient(multipleBODTO.getClient());
		buyerOrderVO.setFinYear(multipleBODTO.getFinYear());
		buyerOrderVO.setBranch(multipleBODTO.getBranch());
		buyerOrderVO.setBranchCode(multipleBODTO.getBranchCode());
		buyerOrderVO.setWarehouse(multipleBODTO.getWarehouse());

		int orderQty = 0;
		int avilQty = 0;

		List<BuyerOrderDetailsVO> detailsVOList = new ArrayList<BuyerOrderDetailsVO>();
		for (BoExcelUploadVO boExcelUploadVO2 : boExcelUploadVO) {

			BuyerOrderDetailsVO detailsVO = new BuyerOrderDetailsVO();
			int avlqty = getAvlQtyByBO(multipleBODTO.getOrgId(), multipleBODTO.getClient(),
					multipleBODTO.getBranchCode(), multipleBODTO.getWarehouse(), multipleBODTO.getBranch(),
					boExcelUploadVO2.getPartNo(), boExcelUploadVO2.getBatchNo());
			int orderqtyupload = boExcelUploadVO2.getQty();
			if (avlqty >= orderqtyupload) {
				detailsVO.setAvailQty(getAvlQtyByBO(multipleBODTO.getOrgId(), multipleBODTO.getClient(),
						multipleBODTO.getBranchCode(), multipleBODTO.getWarehouse(), multipleBODTO.getBranch(),
						boExcelUploadVO2.getPartNo(), boExcelUploadVO2.getBatchNo()));
				detailsVO.setPartNo(boExcelUploadVO2.getPartNo());
				detailsVO.setPartDesc(boExcelUploadVO2.getPartDesc());
				detailsVO.setQty(boExcelUploadVO2.getQty());
				detailsVO.setBatchNo(boExcelUploadVO2.getBatchNo());
				detailsVO.setSku(boExcelUploadVO2.getSku());

				avilQty = avilQty + avlqty;
				orderQty = orderQty + orderqtyupload;
				detailsVO.setBuyerOrderVO(buyerOrderVO);
			} else {
				continue;
			}
			detailsVOList.add(detailsVO);
		}
		buyerOrderVO.setTotalOrderQty(orderQty);
		buyerOrderVO.setTotalAvailQty(avilQty);
		buyerOrderVO.setBuyerOrderDetailsVO(detailsVOList);
		return buyerOrderVO;

	}

	// Pending Buyer Order
	@Override
	public List<Map<String, Object>> getPendingBuyerOrderDetails(Long orgId, String branchCode, String warehouse,
			String client, String finYear) {
		Set<Object[]> resultq = boExcelUploadRepo.getOrderDetailsFromUpload(orgId, branchCode, warehouse, client,
				finYear);
		return PendingBODetails(resultq);
	}

	private List<Map<String, Object>> PendingBODetails(Set<Object[]> resultq) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : resultq) {
			Map<String, Object> part = new HashMap<>();
			part.put("sno", fs[0] != null ? fs[0].toString() : "");
			part.put("orderNo", fs[1] != null ? fs[1].toString() : "");
			part.put("orderDate", fs[2] != null ? fs[2].toString() : "");
			part.put("buyerName", fs[3] != null ? fs[3].toString() : "");
			part.put("billToName", fs[4] != null ? fs[4].toString() : "");
			part.put("shipToName", fs[5] != null ? fs[5].toString() : "");
			part.put("refNo", fs[6] != null ? fs[6].toString() : "");
			part.put("refDate", fs[7] != null ? fs[7].toString() : "");
			part.put("invoiceNo", fs[8] != null ? fs[8].toString() : "");
			part.put("invoiceDate", fs[9] != null ? fs[9].toString() : "");
			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getBuyerorderDashboard(Long orgId, String branchCode, String warehouse,
			String client, String finYear, String month) {
		Set<Object[]> resultq = boExcelUploadRepo.getBuyerorderDashboard(orgId, branchCode, warehouse, client, finYear,
				month);
		return getBuyerorder(resultq);
	}

	private List<Map<String, Object>> getBuyerorder(Set<Object[]> resultq) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : resultq) {
			Map<String, Object> part = new HashMap<>();
			part.put("orderNo", fs[0] != null ? fs[0].toString() : "");
			part.put("orderDate", fs[1] != null ? fs[1].toString() : "");
			part.put("qty", fs[2] != null ? Integer.parseInt(fs[2].toString()) : 0);
			part.put("status", fs[3] != null ? fs[3].toString() : "");

			details1.add(part);
		}
		return details1;

	}

}
