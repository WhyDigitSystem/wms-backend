package com.whydigit.wms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.DataFormatter;


import org.apache.commons.lang3.ObjectUtils;
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
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.dto.StockRestateDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.SrsExcelUploadVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.StockRestateDetailsVO;
import com.whydigit.wms.entity.StockRestateVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SrsExcelUploadRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.StockRestateRepo;

@Service
public class StockRestateServiceImpl implements StockRestateService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(StockRestateServiceImpl.class);
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	StockRestateRepo stockRestateRepo;
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	MaterialRepo materialRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	SrsExcelUploadRepo srsExcelUploadRepo;
	
	//StockRestate
	@Override
	public List<StockRestateVO> getAllStockRestate(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return stockRestateRepo.findAllStockRestate(orgId, finYear, branch, branchCode, client, warehouse);
	}
	
	@Override
	public StockRestateVO getStockRestateById(Long id) {
		StockRestateVO stockRestateVO = new StockRestateVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  StockRestate BY Id : {}", id);
			stockRestateVO = stockRestateRepo.findById(id).orElse(null);
		} else {
			LOGGER.info("failed Received  StockRestate For All Id.");
		}
		return stockRestateVO;

	}
	
	@Override
	@Transactional
	public String getStockRestateDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "SRS";
		String result = stockRestateRepo.getStockRestateDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createStockRestate(StockRestateDTO stockRestateDTO) throws ApplicationException {
		
		StockRestateVO stockRestateVO = new StockRestateVO();
		String message = "Stock Restate Created Successfully";
		String screenCode = "SRS";
		
		String docId = stockRestateRepo.getStockRestateDocId(stockRestateDTO.getOrgId(), stockRestateDTO.getFinYear(),
				stockRestateDTO.getBranchCode(), stockRestateDTO.getClient(), screenCode);

		stockRestateVO.setDocId(docId);		
		stockRestateVO.setTransferFrom(stockRestateDTO.getTransferFrom());
		stockRestateVO.setTransferTo(stockRestateDTO.getTransferTo());
		stockRestateVO.setTransferFromFlag(stockRestateDTO.getTransferFromFlag());
		stockRestateVO.setTransferToFlag(stockRestateDTO.getTransferToFlag());
		stockRestateVO.setEntryNo(stockRestateDTO.getEntryNo());
		stockRestateVO.setOrgId(stockRestateDTO.getOrgId());
		stockRestateVO.setCustomer(stockRestateDTO.getCustomer());
		stockRestateVO.setClient(stockRestateDTO.getClient());
		stockRestateVO.setFinYear(stockRestateDTO.getFinYear());
		stockRestateVO.setBranch(stockRestateDTO.getBranch());
		stockRestateVO.setBranchCode(stockRestateDTO.getBranchCode());
		stockRestateVO.setWarehouse(stockRestateDTO.getWarehouse());
		stockRestateVO.setCreatedBy(stockRestateDTO.getCreatedBy());
		stockRestateVO.setUpdatedBy(stockRestateDTO.getCreatedBy());
		
		List<StockRestateDetailsVO> stockRestateDetailsVO= new ArrayList<>();
		List<StockRestateDetailsDTO>stockRestateDetailsDTOList= stockRestateDTO.getStockRestateDetailsDTO();
		if(stockRestateDetailsDTOList!=null)
		{
			for(StockRestateDetailsDTO stockRestateDetailsDTO:stockRestateDetailsDTOList)
			{
				StockRestateDetailsVO stockRestateDetailsVOs= new StockRestateDetailsVO();
				stockRestateDetailsVOs.setFromBin(stockRestateDetailsDTO.getFromBin());
			    stockRestateDetailsVOs.setFromBinClass(stockRestateDetailsDTO.getFromBinClass());
			    stockRestateDetailsVOs.setFromBinType(stockRestateDetailsDTO.getFromBinType());
			    stockRestateDetailsVOs.setFromCellType(stockRestateDetailsDTO.getFromCellType());
			    stockRestateDetailsVOs.setFromCore(stockRestateDetailsDTO.getFromCore());
			    stockRestateDetailsVOs.setPartNo(stockRestateDetailsDTO.getPartNo());
			    stockRestateDetailsVOs.setPartDesc(stockRestateDetailsDTO.getPartDesc());
			    stockRestateDetailsVOs.setSku(stockRestateDetailsDTO.getSku());
			    stockRestateDetailsVOs.setGrnNo(stockRestateDetailsDTO.getGrnNo());
			    stockRestateDetailsVOs.setGrnDate(stockRestateDetailsDTO.getGrnDate());
			    stockRestateDetailsVOs.setBatch(stockRestateDetailsDTO.getBatch());
			    stockRestateDetailsVOs.setBatchDate(stockRestateDetailsDTO.getBatchDate());
			    stockRestateDetailsVOs.setToBin(stockRestateDetailsDTO.getToBin());
			    stockRestateDetailsVOs.setToBinClass(stockRestateDetailsDTO.getToBinClass());
			    stockRestateDetailsVOs.setToBinType(stockRestateDetailsDTO.getToBinType());
			    stockRestateDetailsVOs.setToCellType(stockRestateDetailsDTO.getToCellType());
			    stockRestateDetailsVOs.setToCore(stockRestateDetailsDTO.getToCore());
			    
			    int getFromQty= stockRestateRepo.getAvlQty(stockRestateDTO.getOrgId(), stockRestateDTO.getBranchCode(),stockRestateDTO.getWarehouse(),
			    		stockRestateDTO.getClient(), stockRestateDTO.getTransferFromFlag(),stockRestateDetailsDTO.getFromBin(),stockRestateDetailsDTO.getPartNo(),stockRestateDetailsDTO.getGrnNo(),
			    		stockRestateDetailsDTO.getBatch());
			    if(getFromQty>=stockRestateDetailsDTO.getToQty())
			    {
			    stockRestateDetailsVOs.setFromQty(getFromQty);
			    stockRestateDetailsVOs.setToQty(stockRestateDetailsDTO.getToQty());
			    }
			    else
			    {
			    	throw new ApplicationException("ToQty is Must Below or Equal to FromQty");
			    }
			    stockRestateDetailsVOs.setExpDate(stockRestateDetailsDTO.getExpDate());
			    stockRestateDetailsVOs.setQcFlag(stockRestateDetailsDTO.getQcFlag());
			    stockRestateDetailsVOs.setStockRestateVO(stockRestateVO);
			    stockRestateDetailsVO.add(stockRestateDetailsVOs);
			}
		}
		else
		{
			throw new ApplicationException("Grid Details is Should not Empty");
		}
		stockRestateVO.setStockRestateDetailsVO(stockRestateDetailsVO);
		StockRestateVO restateVO= stockRestateRepo.save(stockRestateVO);
		DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
				.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(stockRestateDTO.getOrgId(), stockRestateDTO.getFinYear(),
						stockRestateDTO.getBranchCode(), stockRestateDTO.getClient(), screenCode);
		documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
		documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
		List<StockRestateDetailsVO> restateDetailsVOs= restateVO.getStockRestateDetailsVO();
		for(StockRestateDetailsVO restateDetailsVO:restateDetailsVOs)
		{
			StockDetailsVO stockDetailsVO= new StockDetailsVO();
			stockDetailsVO.setOrgId(restateVO.getOrgId());
			stockDetailsVO.setBranch(restateVO.getBranch());
			stockDetailsVO.setBranchCode(restateVO.getBranchCode());
			stockDetailsVO.setWarehouse(restateVO.getWarehouse());
			stockDetailsVO.setCustomer(restateVO.getCustomer());
			stockDetailsVO.setClient(restateVO.getClient());
			stockDetailsVO.setClientCode(clientRepo.getClientCode(restateVO.getOrgId(), restateVO.getClient()));
			stockDetailsVO.setFinYear(restateVO.getFinYear());
			stockDetailsVO.setRefNo(restateVO.getDocId());
			stockDetailsVO.setRefDate(restateVO.getDocDate());
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsVO.setCreatedBy(restateVO.getCreatedBy());
			stockDetailsVO.setUpdatedBy(restateVO.getUpdatedBy());
			stockDetailsVO.setSourceId(restateDetailsVO.getId());
			stockDetailsVO.setPartno(restateDetailsVO.getPartNo());
			stockDetailsVO.setPartDesc(restateDetailsVO.getPartDesc());
			stockDetailsVO.setSku(restateDetailsVO.getSku());
			stockDetailsVO.setSSku(restateDetailsVO.getSku());
			stockDetailsVO.setGrnNo(restateDetailsVO.getGrnNo());
			stockDetailsVO.setGrnDate(restateDetailsVO.getGrnDate());
			stockDetailsVO.setBatch(restateDetailsVO.getBatch());
			stockDetailsVO.setBatchDate(restateDetailsVO.getBatchDate());
			stockDetailsVO.setBin(restateDetailsVO.getFromBin());
			stockDetailsVO.setBinType(restateDetailsVO.getFromBinType());
			stockDetailsVO.setPcKey(materialRepo.getParentChildKey(restateVO.getOrgId(), restateVO.getClient(), restateDetailsVO.getPartNo()));
			stockDetailsVO.setBinClass(restateDetailsVO.getFromBinClass());
			stockDetailsVO.setCellType(restateDetailsVO.getFromCellType());
			stockDetailsVO.setQcFlag(restateDetailsVO.getQcFlag());
			stockDetailsVO.setStatus(restateVO.getTransferFromFlag());
			stockDetailsVO.setExpDate(restateDetailsVO.getExpDate());
			stockDetailsVO.setCore(restateDetailsVO.getFromCore());
			stockDetailsVO.setSQty(restateDetailsVO.getToQty()*-1);
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsRepo.save(stockDetailsVO);
			
		}
		for(StockRestateDetailsVO restateDetailsVO:restateDetailsVOs)
		{
			StockDetailsVO stockDetailsVO= new StockDetailsVO();
			stockDetailsVO.setOrgId(restateVO.getOrgId());
			stockDetailsVO.setBranch(restateVO.getBranch());
			stockDetailsVO.setBranchCode(restateVO.getBranchCode());
			stockDetailsVO.setWarehouse(restateVO.getWarehouse());
			stockDetailsVO.setCustomer(restateVO.getCustomer());
			stockDetailsVO.setClient(restateVO.getClient());
			stockDetailsVO.setClientCode(clientRepo.getClientCode(restateVO.getOrgId(), restateVO.getClient()));
			stockDetailsVO.setFinYear(restateVO.getFinYear());
			stockDetailsVO.setRefNo(restateVO.getDocId());
			stockDetailsVO.setRefDate(restateVO.getDocDate());
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsVO.setCreatedBy(restateVO.getCreatedBy());
			stockDetailsVO.setUpdatedBy(restateVO.getUpdatedBy());
			stockDetailsVO.setSourceId(restateDetailsVO.getId());
			stockDetailsVO.setPartno(restateDetailsVO.getPartNo());
			stockDetailsVO.setPartDesc(restateDetailsVO.getPartDesc());
			stockDetailsVO.setSku(restateDetailsVO.getSku());
			stockDetailsVO.setSSku(restateDetailsVO.getSku());
			stockDetailsVO.setGrnNo(restateDetailsVO.getGrnNo());
			stockDetailsVO.setGrnDate(restateDetailsVO.getGrnDate());
			stockDetailsVO.setBatch(restateDetailsVO.getBatch());
			stockDetailsVO.setBatchDate(restateDetailsVO.getBatchDate());
			stockDetailsVO.setBin(restateDetailsVO.getToBin());
			stockDetailsVO.setBinType(restateDetailsVO.getToBinType());
			stockDetailsVO.setPcKey(materialRepo.getParentChildKey(restateVO.getOrgId(), restateVO.getClient(), restateDetailsVO.getPartNo()));
			stockDetailsVO.setBinClass(restateDetailsVO.getToBinClass());
			stockDetailsVO.setCellType(restateDetailsVO.getToCellType());
			if("D".equals(restateVO.getTransferToFlag())){
			stockDetailsVO.setQcFlag("F");
			}
			else
			{
			stockDetailsVO.setQcFlag("T");
			}
			stockDetailsVO.setStatus(restateVO.getTransferToFlag());
			stockDetailsVO.setExpDate(restateDetailsVO.getExpDate());
			stockDetailsVO.setCore(restateDetailsVO.getToCore());
			stockDetailsVO.setSQty(restateDetailsVO.getToQty());
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsRepo.save(stockDetailsVO);
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("restateVO", restateVO);
		return response;
	}
	
	@Override
	public List<Map<String, Object>> getfromBinForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag) {
		Set<Object[]>getFromBinDetails= stockRestateRepo.getFromBinDetails(orgId, branchCode, warehouse,
				client, transferFromFlag);
		return fromBinDetails(getFromBinDetails);
	}

	private List<Map<String, Object>> fromBinDetails(Set<Object[]> getFromBinDetails) {
		List<Map<String, Object>>binDetails= new ArrayList<>();
		for(Object[] fromBins:getFromBinDetails)
		{
			Map<String, Object>fromBin= new HashMap<>();
			fromBin.put("fromBinType",fromBins[0]!=null ? fromBins[0].toString():"");
			fromBin.put("fromBinClass",fromBins[1]!=null ? fromBins[1].toString():"");
			fromBin.put("fromCellType",fromBins[2]!=null ? fromBins[2].toString():"");
			fromBin.put("fromBin",fromBins[3]!=null ? fromBins[3].toString():"");
			fromBin.put("fromCore",fromBins[4]!=null ? fromBins[4].toString():"");
			binDetails.add(fromBin);
		}
		return binDetails;
	}

	@Override
	public List<Map<String, Object>> getPartNoDetailsForStockRestate(Long orgId, String branchCode,
			String warehouse, String client, String transferFromFlag, String fromBin) {
		Set<Object[]>getPartNoDetails= stockRestateRepo.getPartNoDetails(orgId, branchCode, warehouse,
				client, transferFromFlag,fromBin);
		return partNoDetails(getPartNoDetails);
	}

	private List<Map<String, Object>> partNoDetails(Set<Object[]> getPartNoDetails) {
		List<Map<String, Object>>partNoDetails= new ArrayList<>();
		for(Object[] partNo:getPartNoDetails)
		{
			Map<String, Object>fromBin= new HashMap<>();
			fromBin.put("partNo",partNo[0]!=null ? partNo[0].toString():"");
			fromBin.put("partDesc",partNo[1]!=null ? partNo[1].toString():"");
			fromBin.put("sku",partNo[2]!=null ? partNo[2].toString():"");
			partNoDetails.add(fromBin);
		}
		return partNoDetails;
	}

	@Override
	public List<Map<String, Object>> getGrnNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String fromBin, String partNo) {
		Set<Object[]>getGrnNoDetails= stockRestateRepo.getGrnNoDetails(orgId, branchCode, warehouse,
				client, transferFromFlag,fromBin,partNo);
		return grnNoDetails(getGrnNoDetails);
	}

	private List<Map<String, Object>> grnNoDetails(Set<Object[]> getGrnNoDetails) {
		List<Map<String, Object>>grnNoDetails= new ArrayList<>();
		for(Object[] grnNo:getGrnNoDetails)
		{
			Map<String, Object>grnNoDetail= new HashMap<>();
			grnNoDetail.put("grnNo",grnNo[0]!=null ? grnNo[0].toString():"");
			grnNoDetail.put("grnDate",grnNo[1]!=null ? grnNo[1].toString():"");
			grnNoDetails.add(grnNoDetail);
		}
		return grnNoDetails;
	}

	@Override
	public List<Map<String, Object>> getBatchNoDetailsForStockRestate(Long orgId, String branchCode,
			String warehouse, String client, String transferFromFlag, String fromBin, String partNo,String grnNo) {
		Set<Object[]>getBatchNoDetails= stockRestateRepo.getBatchNoDetails(orgId, branchCode, warehouse,
				client, transferFromFlag,fromBin,partNo,grnNo);
		return batchNoDetails(getBatchNoDetails);
	}

	private List<Map<String, Object>> batchNoDetails(Set<Object[]> getBatchNoDetails) {
		List<Map<String, Object>>batchNoDetails= new ArrayList<>();
		for(Object[] batchNo:getBatchNoDetails)
		{
			Map<String, Object>batchNoDetail= new HashMap<>();
			batchNoDetail.put("batchNo",batchNo[0]!=null ? batchNo[0].toString():"");
			batchNoDetail.put("batchDate",batchNo[1]!=null ? batchNo[1].toString():"");
			batchNoDetail.put("expDate",batchNo[2]!=null ? batchNo[2].toString():"");
			batchNoDetails.add(batchNoDetail);
		}
		return batchNoDetails;
	}

	@Override
	public int getFromQtyForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String fromBin, String partNo, String grnNo, String batchNo) {
		int getFromQty= stockRestateRepo.getAvlQty(orgId, branchCode, warehouse,
				client, transferFromFlag,fromBin,partNo,grnNo,batchNo);
		return getFromQty;
	}

	

	@Override
	public List<Map<String, Object>> getToBinDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client) {
		
		Set<Object[]>getToBinDetails= stockRestateRepo.getToBinDetails(orgId, branchCode, warehouse,
				client);
		return toBinDetails(getToBinDetails);
	}

	private List<Map<String, Object>> toBinDetails(Set<Object[]> getToBinDetails) {
		List<Map<String, Object>>binDetails= new ArrayList<>();
		for(Object[] toBins:getToBinDetails)
		{
			Map<String, Object>fromBin= new HashMap<>();
			fromBin.put("toBin",toBins[0]!=null ? toBins[0].toString():"");
			fromBin.put("tobinType",toBins[1]!=null ? toBins[1].toString():"");
			fromBin.put("toBinClass",toBins[2]!=null ? toBins[2].toString():"");
			fromBin.put("toCellType",toBins[3]!=null ? toBins[3].toString():"");
			fromBin.put("toCore",toBins[4]!=null ? toBins[4].toString():"");
			binDetails.add(fromBin);
		}
		return binDetails;
	}

	@Override
	public List<Map<String, Object>> getFillGridDetailsForStockRestate(Long orgId, String branchCode,
			String warehouse, String client, String transferFromFlag,String transferToFlag) {
		
		Set<Object[]>getFillGridDetails= stockRestateRepo.getFillGridDetailsForRestate(orgId, branchCode,
				 warehouse, client, transferFromFlag,transferToFlag);
		return fillGridDetails(getFillGridDetails);
	}

	private List<Map<String, Object>> fillGridDetails(Set<Object[]> getFillGridDetails) {
		List<Map<String, Object>>gridDetails= new ArrayList<>();
		for(Object[] details:getFillGridDetails)
		{
			Map<String, Object>fillDetails= new HashMap<>();
			fillDetails.put("partNo",details[0]!=null ? details[0].toString():"");
			fillDetails.put("partDesc",details[1]!=null ? details[1].toString():"");
			fillDetails.put("sku",details[2]!=null ? details[2].toString():"");
			fillDetails.put("grnNo",details[3]!=null ? details[3].toString():"");
			fillDetails.put("grnDate",details[4]!=null ? details[4].toString():"");
			fillDetails.put("batchNo",details[5]!=null ? details[5].toString():"");
			fillDetails.put("batchDate",details[6]!=null ? details[6].toString():"");
			fillDetails.put("expDate",details[7]!=null ? details[7].toString():"");
			fillDetails.put("fromBinType",details[8]!=null ? details[8].toString():"");
			fillDetails.put("fromBinClass",details[9]!=null ? details[9].toString():"");
			fillDetails.put("fromCellType",details[10]!=null ? details[10].toString():"");
			fillDetails.put("fromCore",details[11]!=null ? details[11].toString():"");
			fillDetails.put("fromBin",details[12]!=null ? details[12].toString():"");
			fillDetails.put("toBin",details[13]!=null ? details[13].toString():"");
			fillDetails.put("ToBinType",details[14]!=null ? details[14].toString():"");
			fillDetails.put("ToBinClass",details[15]!=null ? details[15].toString():"");
			fillDetails.put("ToCellType",details[16]!=null ? details[16].toString():"");
			fillDetails.put("ToCore",details[17]!=null ? details[17].toString():"");
			fillDetails.put("qcFlag",details[18]!=null ? details[18].toString():"");
			fillDetails.put("fromQty",details[19]!=null ? Integer.parseInt(details[19].toString()):0);
			fillDetails.put("toQty",details[19]!=null ? Integer.parseInt(details[19].toString()):0);
			fillDetails.put("id",details[20]!=null ? Integer.parseInt(details[20].toString()):0);
			
			gridDetails.add(fillDetails);
		}
		return gridDetails;
	}
	
	 private int totalRows = 0; // Instance variable to keep track of total rows
	    private int successfulUploads = 0; // Instance variable to keep track of successful uploads

	    private final DataFormatter dataFormatter = new DataFormatter();


	    @Transactional
	    public void ExcelUploadForStockRestate(MultipartFile[] files, CustomerAttachmentType type, Long orgId,
				String createdBy, String customer, String client, String finYear, String branch, String branchCode,
				String warehouse) throws ApplicationException {
	        List<SrsExcelUploadVO> srsExcelUploadVOsToSave = new ArrayList<>();
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
	                    throw new ApplicationException("Invalid Excel format in file '" + file.getOriginalFilename() + "'. Expected headers are: Type, From location, From location type, Location pick, partno, partdesc, sku, Grn No, GRN date, Batch No, Exp date, Entry no, From Status, To Status");
	                }

	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue;
	                    }

	                    totalRows++;
	                    System.out.println("Validating row: " + (row.getRowNum() + 1));
	                    
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
	                        String fromStatus = getStringCellValue(row.getCell(12));
	                        String toStatus = getStringCellValue(row.getCell(13));

	                        // Create and populate SrsExcelUploadVO object
	                        SrsExcelUploadVO srsExcelUploadVO = new SrsExcelUploadVO();
	                        srsExcelUploadVO.setType(type1);
	                        srsExcelUploadVO.setFrombin(fromBin);
	                        srsExcelUploadVO.setFromBinType(fromBinType);
	                        srsExcelUploadVO.setBinPick(binPick);
	                        srsExcelUploadVO.setPartNo(partNo);
	                        srsExcelUploadVO.setPartDesc(partDesc);
	                        srsExcelUploadVO.setSku(sku);
	                        srsExcelUploadVO.setGrnNo(grnNo);
	                        srsExcelUploadVO.setGrnDate(grnDate);
	                        srsExcelUploadVO.setBatchNo(batchNo);
	                        srsExcelUploadVO.setExpDate(expDate);
	                        srsExcelUploadVO.setEntryNo(entryNo);
	                        srsExcelUploadVO.setFromStatus(fromStatus);
	                        srsExcelUploadVO.setToStatus(toStatus);
	                        srsExcelUploadVO.setOrgId(orgId);
	                        srsExcelUploadVO.setCustomer(customer);
	                        srsExcelUploadVO.setClient(client);
	                        srsExcelUploadVO.setFinYear(finYear);
	                        srsExcelUploadVO.setBranch(branch);
	                        srsExcelUploadVO.setBranchCode(branchCode);
	                        srsExcelUploadVO.setWarehouse(warehouse);
	                        srsExcelUploadVO.setCreatedBy(createdBy);
	                        srsExcelUploadVO.setUpdatedBy("");
	                        srsExcelUploadVO.setActive(true);
	                        srsExcelUploadVO.setCancel(false);
	                        srsExcelUploadVO.setCancelRemarks("");

	                        srsExcelUploadVOsToSave.add(srsExcelUploadVO);
	                        successfulUploads++;
	                    } catch (Exception e) {
	                        // Optionally handle specific row processing exceptions here
	                    }
	                }

	                srsExcelUploadRepo.saveAll(srsExcelUploadVOsToSave);
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
	            return ""; // Return empty string if cell is null
	        }

	        // Use DataFormatter to get the cell value as a string
	        return dataFormatter.formatCellValue(cell);
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
	            "Batch No", "Exp date", "Entry no", "From Status", "To Status"
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

	    public int getTotalRows() {
	        return totalRows;
	    }

	    public int getSuccessfulUploads() {
	        return successfulUploads;
	    }

	

}
