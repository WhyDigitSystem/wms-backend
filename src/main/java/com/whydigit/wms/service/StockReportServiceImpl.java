package com.whydigit.wms.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.LocationMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

import io.jsonwebtoken.io.IOException;

@Service
public class StockReportServiceImpl implements StockReportService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockReportServiceImpl.class);

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	LocationMappingDetailsRepo locationMappingDetailsRepo;

	@Autowired
	ClientRepo clientRepo;

	@Override
	public List<Map<String, Object>> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getConsolidateStockDetails(orgId, branchCode, warehouse, customer,
				client, partNo);
		return stockdetails(getDetails);
	}

	private List<Map<String, Object>> stockdetails(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("avlQty", st[2] != null ? Integer.parseInt(st[2].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override

	public List<Map<String, Object>> getStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin, String status) {
		Set<Object[]> getReport = stockDetailsRepo.findStockReportBinAndBatchWise(orgId, branchCode, warehouse,
				customer, client, partNo, batch, bin, status);
		return StockReport(getReport);
	}

	private List<Map<String, Object>> StockReport(Set<Object[]> getReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("bin", st[0] != null ? st[0].toString() : "");
			details.put("batch", st[1] != null ? st[1].toString() : "");
			details.put("partNo", st[2] != null ? st[2].toString() : "");
			details.put("partDesc", st[3] != null ? st[3].toString() : "");
			details.put("status", st[4] != null ? st[4].toString() : "");
			details.put("avlQty", st[5] != null ? Integer.parseInt(st[5].toString()) : 0);
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getPartNoForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client) {
		Set<Object[]> getPartNoReport = stockDetailsRepo.findPartNoForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client);
		return partNoStockReport(getPartNoReport);
	}

	private List<Map<String, Object>> partNoStockReport(Set<Object[]> getPartNoReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getPartNoReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", st[0] != null ? st[0].toString() : "");

			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getBatchForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo) {
		Set<Object[]> getBatchReport = stockDetailsRepo.findBatchForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client, partNo);
		return batchStockReport(getBatchReport);
	}

	private List<Map<String, Object>> batchStockReport(Set<Object[]> getBatchReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getBatchReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("batch", st[0] != null ? st[0].toString() : "");

			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getBinForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo, String batch) {
		Set<Object[]> getBinReport = stockDetailsRepo.findBinForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
				customer, client, partNo, batch);
		return binStockReport(getBinReport);
	}

	private List<Map<String, Object>> binStockReport(Set<Object[]> getBinReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getBinReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("bin", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getStatusForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo, String batch, String bin) {
		Set<Object[]> getStatusReport = stockDetailsRepo.findStatusForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client, partNo, batch, bin);
		return statusStockReport(getStatusReport);
	}

	private List<Map<String, Object>> statusStockReport(Set<Object[]> getStatusReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getStatusReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("status", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getStockReportBinWise(Long orgId, String branchCode, String bin, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockReportBinWise(orgId, branchCode, bin, warehouse, customer,
				client, partNo);
		return getStockReportBin(getDetails);
	}

	private List<Map<String, Object>> getStockReportBin(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("bin", st[2] != null ? st[2].toString() : "");
			stockDetails.put("avlQty", st[3] != null ? Integer.parseInt(st[3].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockReportBatchWise(Long orgId, String branchCode, String batch,
			String warehouse, String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockReportBatchWise(orgId, branchCode, batch, warehouse,
				customer, client, partNo);
		return getStockReportBatch(getDetails);
	}

	private List<Map<String, Object>> getStockReportBatch(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("batch", st[2] != null ? st[2].toString() : "");
			stockDetails.put("avlQty", st[3] != null ? Integer.parseInt(st[3].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockLedger(Long orgId, String branchCode, String warehouse, String customer,
			String client, String startDate, String endDate, String partNo) {
		Set<Object[]> getLedgerDetails = stockDetailsRepo.getLedgerDetails(orgId, branchCode, warehouse, customer,
				client, startDate, endDate, partNo);
		return getStockLedgerReport(getLedgerDetails);
	}

	@Override
	public List<Map<String, Object>> getPartNoForBatchWiseReport(Long orgId, String branchCode, String warehouse,
			String customer, String client) {
		Set<Object[]> getDetails = stockDetailsRepo.getPartNoFromBatchWiseReport(orgId, branchCode, warehouse, customer,
				client);
		return getPartNoFromBatchWise(getDetails);
	}

	private List<Map<String, Object>> getPartNoFromBatchWise(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getBatchForBatchWiseReport(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getBatchFromBatchWiseReport(orgId, branchCode, warehouse, customer,
				client, partNo);
		return getBatchFromBatchWise(getDetails);
	}

	private List<Map<String, Object>> getBatchFromBatchWise(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("batch", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	private List<Map<String, Object>> getStockLedgerReport(Set<Object[]> getLedgerDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getLedgerDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("refNo", st[2] != null ? st[2].toString() : "");
			stockDetails.put("sourceScreen", st[3] != null ? st[3].toString() : "");
			stockDetails.put("oQty", st[4] != null ? Integer.parseInt(st[4].toString()) : 0);
			stockDetails.put("rQty", st[5] != null ? Integer.parseInt(st[5].toString()) : 0);
			stockDetails.put("dQty", st[6] != null ? Integer.parseInt(st[6].toString()) : 0);
			stockDetails.put("cQty", st[7] != null ? Integer.parseInt(st[7].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockPartNoBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockPartNoBatch(orgId, branchCode, warehouse, customer, client);
		return getStockPartNo(getDetails);
	}

	private List<Map<String, Object>> getStockPartNo(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getBatchNoBinWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getBatchNoBin(orgId, branchCode, warehouse, customer, client,
				partNo);
		return getBatchNo(getDetails);
	}

	private List<Map<String, Object>> getBatchNo(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("Bin", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	private int totalRows = 0; // Instance variable to keep track of total rows
	private int successfulUploads = 0; // Instance variable to keep track of successful uploads

	private final DataFormatter dataFormatter = new DataFormatter();

	@Transactional
	public void uploadStockDetails(MultipartFile[] files, Long orgId, String customer, String client, String warehouse,
			String branch, String branchCode, String createdBy, String FinYear) throws ApplicationException, EncryptedDocumentException, java.io.IOException {

		List<StockDetailsVO> stockDetailsToSave = new ArrayList<>();
		totalRows = 0; // Reset totalRows at the beginning of the method
		successfulUploads = 0; // Reset successfulUploads at the beginning of the method

		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				throw new ApplicationException("The supplied file '" + file.getOriginalFilename() + "' is empty.");
			}

			try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
				Sheet sheet = workbook.getSheetAt(0);

				if (!isStockHeaderValid(sheet.getRow(0))) {
					throw new ApplicationException(
							"Invalid Excel format in file '" + file.getOriginalFilename() + "'.");
				}

				for (Row row : sheet) {
					if (row.getRowNum() == 0 || isRowEmpty(row)) {
						continue;
					}

					totalRows++; // Increment totalRows for each processed row

					try {
						StockDetailsVO stockDetailsVO = new StockDetailsVO();

						MaterialVO materialVO = materialRepo.findPartNoDetails(orgId, customer, client,
								getStringCellValue(row.getCell(0)));

						Set<Object[]> bindetails = locationMappingDetailsRepo.getBinDetails(orgId, branchCode,
								warehouse, client, getStringCellValue(row.getCell(8)));
						if (!bindetails.isEmpty()) {
							for (Object[] bin : bindetails) {
								stockDetailsVO.setBin(bin[0].toString());
								stockDetailsVO.setBinType(bin[1].toString());
								stockDetailsVO.setBinClass(bin[2].toString());
								stockDetailsVO.setCore(bin[3].toString());
								stockDetailsVO.setCellType(bin[4].toString());

							}
						}
						
						String grnNo=getStringCellValue(row.getCell(3));
						String batchNo=getStringCellValue(row.getCell(5));
						// Extract values from the Excel file for specific fields
						stockDetailsVO.setPartno(materialVO.getPartno());
						stockDetailsVO.setPartDesc(materialVO.getPartDesc());
						stockDetailsVO.setSku(materialVO.getSku());
						stockDetailsVO.setBin(getStringCellValue(row.getCell(8)));
						stockDetailsVO.setSQty(getNumericCellValue(row.getCell(9)));
						
						if (grnNo == null || grnNo.trim().isEmpty()) {
							stockDetailsVO.setGrnNo("OPSTOCK");
							stockDetailsVO.setGrnDate(LocalDate.now());
						}
						else
						{
							stockDetailsVO.setGrnNo(grnNo);
						    stockDetailsVO.setGrnDate(parseDate(getStringCellValue(row.getCell(4))));
						}
						
						if (batchNo == null || batchNo.trim().isEmpty()) {
						    stockDetailsVO.setBatch(null);
						    stockDetailsVO.setBatchDate(null);
						    stockDetailsVO.setExpDate(null);
						}
						else
						{
							stockDetailsVO.setBatch(batchNo);
						    stockDetailsVO.setBatchDate(parseDate(getStringCellValue(row.getCell(6))));
						    stockDetailsVO.setExpDate(parseDate(getStringCellValue(row.getCell(7))));
						}
						
						stockDetailsVO.setSSku(materialVO.getSku());
						stockDetailsVO.setUpdatedBy(createdBy);
						stockDetailsVO.setOrgId(orgId);
						stockDetailsVO.setRefNo("OPSTOCK");
						stockDetailsVO.setRefDate(LocalDate.now());
						stockDetailsVO.setPcKey(materialVO.getParentChildKey());
						stockDetailsVO.setCreatedBy(createdBy);
						stockDetailsVO.setBranchCode(branchCode);
						stockDetailsVO.setBranch(branch);
						stockDetailsVO.setCustomer(customer);
						stockDetailsVO.setClient(client);
						stockDetailsVO.setClientCode(clientRepo.getClientCode(orgId, client));
						stockDetailsVO.setWarehouse(warehouse);
						stockDetailsVO.setFinYear(FinYear);
						stockDetailsVO.setQcFlag("T");
						stockDetailsVO.setStatus("R");
						stockDetailsVO.setSourceScreenName("OPSTOCK");
						stockDetailsVO.setSourceScreenCode("OB");
						stockDetailsVO.setActive(true); // Assuming default values

						// Add to the list of stock details to save
						stockDetailsToSave.add(stockDetailsVO);
						successfulUploads++; // Increment successfulUploads for each added stock detail
					} catch (Exception e) {
						throw new ApplicationException(
								"Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
					}
				}

				// Save all stock detail records in batch
				stockDetailsRepo.saveAll(stockDetailsToSave);

			} catch (IOException e) {
				throw new ApplicationException("Failed to process file: " + file.getOriginalFilename(), e);
			}
		}
	}

	private boolean isRowEmpty(Row row) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isStockHeaderValid(Row row) {
		List<String> expectedHeaders = Arrays.asList("Part No", "Part Desc", "SKU","Grn No","Grn Date","Batch No","Batch Date","Exp Date", "Bin", "Qty");

		for (int i = 0; i < expectedHeaders.size(); i++) {
			String cellValue = getStringCellValue(row.getCell(i));
			if (!expectedHeaders.get(i).equalsIgnoreCase(cellValue)) {
				return false; // Return false if any header does not match
			}
		}
		return true;
	}

	// Getter for totalRows
	public int getTotalRows() {
		return totalRows;
	}

	// Getter for successfulUploads
	public int getSuccessfulUploads() {
		return successfulUploads;
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return ""; // Return empty string if cell is null
		}

		// Use DataFormatter to get the cell value as a string
		return dataFormatter.formatCellValue(cell);
	}

	private int getNumericCellValue(Cell cell) {
		if (cell == null) {
			return 0; // or throw an exception if you prefer
		}
		switch (cell.getCellType()) {
		case NUMERIC:
			return (int) cell.getNumericCellValue();
		default:
			return 0; // or throw an exception if the cell type is not numeric
		}
	}
	
	 private LocalDate parseDate(String stringCellValue) {
	    	try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
				return LocalDate.parse(stringCellValue, formatter);
			} catch (Exception e) {
				return null;
			}
	}

}
