package com.whydigit.wms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		buyerOrderVO.setBillToShortName(buyerOrderDTO.getBillToShortName());
		buyerOrderVO.setBillToName(buyerOrderDTO.getBillToName());
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

	private int totalRows = 0; // Instance variable to keep track of total rows
	private int successfulUploads = 0; // Instance variable to keep track of successful uploads

	@Transactional
	@Override
	public void ExcelUploadForBo(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse)
			throws ApplicationException {
		List<BoExcelUploadVO> boExcelUploadVOVOsToSave = new ArrayList<>();
		totalRows = 0; // Reset totalRows for each execution
		successfulUploads = 0; // Reset successfulUploads for each execution

		for (MultipartFile file : files) {
			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming only one sheet
				List<String> errorMessages = new ArrayList<>();
				System.out.println("Processing file: " + file.getOriginalFilename()); // Debug statement
				Row headerRow = sheet.getRow(0);
				if (!isHeaderValid(headerRow)) {
					throw new ApplicationException("Invalid Excel format. Please refer to the sample file.");
				}

				// Check all rows for validity first
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty(row)) {
						continue; // Skip header row and empty rows
					}

					totalRows++; // Increment totalRows
					System.out.println("Validating row: " + (row.getRowNum() + 1)); // Debug statement

					try {
						// Retrieve cell values based on the provided order
						String type1 = getStringCellValue(row.getCell(0));
						Integer orderNo = parseInteger(getStringCellValue(row.getCell(1)));
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
						Integer qty = parseInteger(getStringCellValue(row.getCell(14)));
						Double unitRate = parseDouble(getStringCellValue(row.getCell(15)));
						String remark = getStringCellValue(row.getCell(16));

						// Example validation logic here, you might need to adjust based on your
						// business rules
						// if (boExcelUploadRepo.existsByOrderNoAndOrgId(orderNo, orgId)) {
						// errorMessages.add("Order No " + orderNo + " already exists for this
						// organization. Row: " + (row.getRowNum() + 1));
						// }
					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// If there are errors, throw ApplicationException and do not save any rows
				if (!errorMessages.isEmpty()) {
					throw new ApplicationException(
							"Excel upload validation failed. Errors: " + String.join(", ", errorMessages));
				}

				// No errors found, now save all rows
				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty(row)) {
						continue; // Skip header row and empty rows
					}

					System.out.println("Saving row: " + (row.getRowNum() + 1)); // Debug statement

					try {

						String partNo1 = getStringCellValue(row.getCell(10));
						String batchNo1 = getStringCellValue(row.getCell(12));

						if (partNo1 == null || partNo1.trim().isEmpty()) {
							errorMessages.add("Part No is missing in row " + (row.getRowNum() + 1));
							continue;
						}

						if (batchNo1 == null || batchNo1.trim().isEmpty()) {
							errorMessages.add("Batch No is missing in row " + (row.getRowNum() + 1));
							continue;
						}

						// Validate each row

						if (stockDetailsRepo.existsByPartnoAndOrgIdAndClient(partNo1, orgId, client)) {
							if (stockDetailsRepo.existsByPartnoAndBatchAndOrgIdAndClient(partNo1, batchNo1, orgId,
									client)) {

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
								Integer qty = parseInteger(getStringCellValue(row.getCell(14)));
								Double unitRate = parseDouble(getStringCellValue(row.getCell(15)));
								String remark = getStringCellValue(row.getCell(16));

								// Create BoExcelUploadVO and add to list for batch saving
								BoExcelUploadVO boExcelUploadVO = new BoExcelUploadVO();
								boExcelUploadVO.setType(type1);
								boExcelUploadVO.setOrderNo(orderNo);
								boExcelUploadVO.setOrderDate(orderDate);
								boExcelUploadVO.setInvoiceNo(invoiceNo);
								boExcelUploadVO.setInvoiceDate(invoiceDate);
								boExcelUploadVO.setReferenceNo(referenceNo);
								boExcelUploadVO.setReferenceDate(referenceDate);
								boExcelUploadVO.setBuyerName(buyerName);
								boExcelUploadVO.setBillTo(billTo);
								boExcelUploadVO.setShipTo(shipTo);
								boExcelUploadVO.setPartNo(partNo);
								boExcelUploadVO.setPartDesc(partDesc);
								boExcelUploadVO.setBatchNo(batchNo);
								boExcelUploadVO.setSku(sku);
								boExcelUploadVO.setQty(qty);
								boExcelUploadVO.setUnitRate(unitRate);
								boExcelUploadVO.setRemark(remark);

								boExcelUploadVO.setOrgId(orgId);
								boExcelUploadVO.setCustomer(customer);
								boExcelUploadVO.setClient(client);
								boExcelUploadVO.setFinYear(finYear);
								boExcelUploadVO.setBranch(branch);
								boExcelUploadVO.setBranchCode(branchCode);
								boExcelUploadVO.setWarehouse(warehouse);
								boExcelUploadVO.setCreatedBy(createdBy);
								boExcelUploadVO.setUpdatedBy(""); // Assuming you set this later or leave it empty
								boExcelUploadVO.setActive(true); // Default or based on some logic
								boExcelUploadVO.setCancel(false); // Default or based on some logic
								boExcelUploadVO.setCancelRemarks("");

								if (buyerOrderRepo.existsByOrderNoAndOrgIdAndClientAndCustomer(orderNo, orgId, client,
										customer)) {
									String errorMessage = String.format("This orderNo:%s Already Exists This Client.",
											orderNo);
									throw new ApplicationException(errorMessage);
								}

								boExcelUploadVOVOsToSave.add(boExcelUploadVO);
								successfulUploads++; // Increment successfulUploads
							}
						}

						else {
							errorMessages.add("Part No " + partNo1 + " and Batch No " + batchNo1
									+ " do not exist in the stock details.");
						}

					} catch (Exception e) {
						errorMessages.add("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}
				// Save all valid rows
				boExcelUploadRepo.saveAll(boExcelUploadVOVOsToSave);
			} catch (IOException e) {
				throw new ApplicationException(
						"Failed to process file: " + file.getOriginalFilename() + " - " + e.getMessage());
			}
		}

	}

	private LocalDate parseDate(String stringCellValue) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
			return LocalDate.parse(stringCellValue, formatter);
		} catch (Exception e) {
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
		int expectedColumnCount = 17; // Adjust based on the actual number of columns
		if (headerRow.getPhysicalNumberOfCells() != expectedColumnCount) {
			return false;
		}
		return "type".equalsIgnoreCase(getStringCellValue(headerRow.getCell(0)))
				&& "order no".equalsIgnoreCase(getStringCellValue(headerRow.getCell(1)))
				&& "order date".equalsIgnoreCase(getStringCellValue(headerRow.getCell(2)))
				&& "invoice no".equalsIgnoreCase(getStringCellValue(headerRow.getCell(3)))
				&& "invoice date".equalsIgnoreCase(getStringCellValue(headerRow.getCell(4)))
				&& "reference no".equalsIgnoreCase(getStringCellValue(headerRow.getCell(5)))
				&& "reference date".equalsIgnoreCase(getStringCellValue(headerRow.getCell(6)))
				&& "buyer name".equalsIgnoreCase(getStringCellValue(headerRow.getCell(7)))
				&& "bill to".equalsIgnoreCase(getStringCellValue(headerRow.getCell(8)))
				&& "ship to".equalsIgnoreCase(getStringCellValue(headerRow.getCell(9)))
				&& "part no".equalsIgnoreCase(getStringCellValue(headerRow.getCell(10)))
				&& "part desc".equalsIgnoreCase(getStringCellValue(headerRow.getCell(11)))
				&& "batchno".equalsIgnoreCase(getStringCellValue(headerRow.getCell(12)))
				&& "sku".equalsIgnoreCase(getStringCellValue(headerRow.getCell(13)))
				&& "qty".equalsIgnoreCase(getStringCellValue(headerRow.getCell(14)))
				&& "unit rate".equalsIgnoreCase(getStringCellValue(headerRow.getCell(15)))
				&& "remark".equalsIgnoreCase(getStringCellValue(headerRow.getCell(16)));
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim(); // Trim spaces
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// Handle date
				return new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
			} else {
				// Check if the numeric value is an integer
				double numericValue = cell.getNumericCellValue();
				if (numericValue == (int) numericValue) {
					return String.valueOf((int) numericValue); // Return as integer
				} else {
					return BigDecimal.valueOf(numericValue).toPlainString(); // Return as double
				}
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		default:
			return "";
		}
	}

	private Integer parseInteger(String stringCellValue) {
		try {
			// Remove any potential decimal points
			return new BigDecimal(stringCellValue).intValue();
		} catch (NumberFormatException e) {
			System.err.println("Error parsing integer: " + stringCellValue); // Debugging output
			return null;
		}
	}

	private Double parseDouble(String stringCellValue) {
		try {
			return Double.parseDouble(stringCellValue);
		} catch (NumberFormatException e) {
			System.err.println("Error parsing double: " + stringCellValue); // Debugging output
			return null;
		}
	}

	@Override
	public int getTotalRows() {
		return totalRows; // Return the correct value
	}

	@Override
	public int getSuccessfulUploads() {
		return successfulUploads; // Return the correct value
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

		BuyerVO buyerVO1 = buyerRepo.findByBuyerAndOrgId(multipleBODTO.getBillToName(), multipleBODTO.getOrgId());
		buyerOrderVO.setBillToName(multipleBODTO.getBillToName());
		buyerOrderVO.setBillToShortName(buyerVO1.getBuyerShortName());

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
			part.put("buyerName", fs[3] != null ? fs[1].toString() : "");
			part.put("billToName", fs[4] != null ? fs[2].toString() : "");
			part.put("shipToName", fs[5] != null ? fs[1].toString() : "");
			part.put("refNo", fs[6] != null ? fs[2].toString() : "");
			part.put("refDate", fs[7] != null ? fs[1].toString() : "");
			part.put("invoiceNo", fs[8] != null ? fs[2].toString() : "");
			part.put("invoiceDate", fs[9] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;

	}

}
