package com.whydigit.wms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.DataFormatter;


import com.whydigit.wms.dto.CustomerAttachmentType;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.GrnDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnExcelUploadVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.GrnDetailsRepo;
import com.whydigit.wms.repo.GrnExcelUploadRepo;
import com.whydigit.wms.repo.GrnRepo;
import com.whydigit.wms.repo.HandlingStockInRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.SupplierRepo;

@Service
public class GrnServiceImpl implements GrnService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnServiceImpl.class);
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	MaterialRepo materialRepo;
	
	@Autowired
	GrnRepo grnRepo;

	@Autowired
	GatePassInRepo gatePassInRepo;

	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	@Autowired
	HandlingStockInRepo handlingStockInRepo;
	
	@Autowired
	SupplierRepo supplierRepo;

	@Autowired
	CarrierRepo carrierRepo;
	
	@Autowired
	GrnExcelUploadRepo grnExcelUploadRepo;
	
	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Override
	public String getGRNdocid(Long orgId, String finYear, String branchCode, String client, String screencode) {

		return grnRepo.getGRNDocId(orgId, finYear, branchCode, client, screencode);
	}

	@Override
	public List<GrnVO> getAllGrn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse) {

		return grnRepo.findAllGrnDetails(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public GrnVO getGrnById(Long id) {
		GrnVO grnVO = new GrnVO();

		if (ObjectUtils.isNotEmpty(id)) {
			grnVO = grnRepo.findById(id).orElse(null);
		} else {
			LOGGER.info("Not found for this Id");
		}
		return grnVO;
	}

//	@Override
//	public Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer,
//			String branchcode) {
//		return grnRepo.findAllGatePassNumberByClientAndBranch(orgId, client, customer, branchcode);
//	}

	@Override
	public Map<String, Object> createUpdateGrn(GrnDTO grnDTO) throws ApplicationException {

		GrnVO grnVO = new GrnVO();
		String screenCode = "GN";
		String message;

		if (ObjectUtils.isNotEmpty(grnDTO.getId())) {
			grnVO = grnRepo.findById(grnDTO.getId()).orElseThrow(() -> new ApplicationException("GRN not found"));

			if (!grnVO.getEntryNo().equalsIgnoreCase(grnDTO.getEntryNo())) {
				if (grnRepo.existsByEntryNoAndOrgIdAndClientAndBranchCodeAndWarehouse(grnDTO.getEntryNo(),
						grnDTO.getOrgId(), grnDTO.getClient(), grnDTO.getBranchCode(), grnDTO.getWarehouse())) {
					throw new ApplicationException("Entry No already Exist for this Branch and Client");
				}
				grnVO.setEntryNo(grnDTO.getEntryNo());
			}
			grnVO.setUpdatedBy(grnDTO.getCreatedBy());
			createUpdateGrnVOByGrnDTO(grnDTO, grnVO);
			message = "GRN Updated Successfully";
		} else {
			if (grnRepo.existsByEntryNoAndOrgIdAndClientAndBranchCodeAndWarehouse(grnDTO.getEntryNo(),
					grnDTO.getOrgId(), grnDTO.getClient(), grnDTO.getBranchCode(), grnDTO.getWarehouse())) {
				throw new ApplicationException("Entry No already Exist for this Branch and Client");
			}
			grnVO.setEntryNo(grnDTO.getEntryNo());
			grnVO.setCreatedBy(grnDTO.getCreatedBy());
			grnVO.setUpdatedBy(grnDTO.getCreatedBy());

			String grnDocId = grnRepo.getGRNDocId(grnDTO.getOrgId(), grnDTO.getFinYear(), grnDTO.getBranchCode(),
					grnDTO.getClient(), screenCode);
			grnVO.setDocId(grnDocId);
			createUpdateGrnVOByGrnDTO(grnDTO, grnVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(grnDTO.getOrgId(), grnDTO.getFinYear(),
							grnDTO.getBranchCode(), grnDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "GRN Created Successfully";
		}

		GrnVO savedGrnVO = grnRepo.save(grnVO);
		List<GrnDetailsVO> grnDetailsVOLists = savedGrnVO.getGrnDetailsVO();
		if (grnDetailsVOLists != null && !grnDetailsVOLists.isEmpty())

			for (GrnDetailsVO grnDetailsVO : grnDetailsVOLists) {
				if (grnDetailsVO.getDamageQty() > 0) {
				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
				handlingStockInVO.setScreencode(savedGrnVO.getScreenCode());
				// Set common values from savedGrnVO
				handlingStockInVO.setRefdate(savedGrnVO.getDocDate());
				handlingStockInVO.setGrnno(savedGrnVO.getDocId());
				handlingStockInVO.setGrndate(savedGrnVO.getDocDate());
				handlingStockInVO.setBranch(savedGrnVO.getBranch());
				handlingStockInVO.setOrgId(savedGrnVO.getOrgId());
				handlingStockInVO.setBranchcode(savedGrnVO.getBranchCode());
				handlingStockInVO.setCustomer(savedGrnVO.getCustomer());
				handlingStockInVO.setWarehouse(savedGrnVO.getWarehouse());
				handlingStockInVO.setClient(savedGrnVO.getClient());
				handlingStockInVO.setSdocdate(savedGrnVO.getDocDate());
				handlingStockInVO.setStockdate(savedGrnVO.getDocDate());
				handlingStockInVO.setSdocid(savedGrnVO.getDocId());
				handlingStockInVO.setFinyr(savedGrnVO.getFinYear());
				handlingStockInVO.setBatchno(grnDetailsVO.getBatchNo());
				handlingStockInVO.setBatchdt(grnDetailsVO.getBatchDt());
				handlingStockInVO.setLrhawbhblno(grnDetailsVO.getLrNoHawbNo());
				handlingStockInVO.setSourcescreen(savedGrnVO.getScreenName());
				handlingStockInVO.setCreatedby(savedGrnVO.getCreatedBy());
				handlingStockInVO.setUpdatedby(savedGrnVO.getUpdatedBy());
				handlingStockInVO.setExpdate(grnDetailsVO.getExpDate());
				handlingStockInVO.setPartno(grnDetailsVO.getPartNo());
				handlingStockInVO.setPartdesc(grnDetailsVO.getPartDesc());
				handlingStockInVO.setLocationtype(grnDetailsVO.getBinType());
				handlingStockInVO.setRate(grnDetailsVO.getRate());
				handlingStockInVO.setAmount(grnDetailsVO.getAmount());
				handlingStockInVO.setSku(grnDetailsVO.getSku());
				handlingStockInVO.setSsku(grnDetailsVO.getSku());
				handlingStockInVO.setPalletcount(1);
				handlingStockInVO.setNoofpallet(1);
				handlingStockInVO.setInvqty(grnDetailsVO.getInvQty());
				handlingStockInVO.setRecqty(grnDetailsVO.getRecQty());
				handlingStockInVO.setShortqty(grnDetailsVO.getShortQty());
				handlingStockInVO.setDamageqty(grnDetailsVO.getDamageQty());
				handlingStockInVO.setSqty(grnDetailsVO.getDamageQty());
				handlingStockInVO.setPalletqty(grnDetailsVO.getDamageQty());
				handlingStockInVO.setRpqty(grnDetailsVO.getDamageQty());
				handlingStockInVO.setQcflag("F");
				
				handlingStockInRepo.save(handlingStockInVO);
				}
			}
		for (GrnDetailsVO grnDetailsVO : grnDetailsVOLists) {
			// create new obj to store as second row
			HandlingStockInVO handlingStockInVO2 = new HandlingStockInVO();
			handlingStockInVO2.setScreencode(grnVO.getScreenCode());
			handlingStockInVO2.setRefdate(savedGrnVO.getDocDate());
			handlingStockInVO2.setGrnno(savedGrnVO.getDocId());
			handlingStockInVO2.setGrndate(savedGrnVO.getDocDate());
			handlingStockInVO2.setBranch(savedGrnVO.getBranch());
			handlingStockInVO2.setOrgId(savedGrnVO.getOrgId());
			handlingStockInVO2.setBranchcode(savedGrnVO.getBranchCode());
			handlingStockInVO2.setCustomer(savedGrnVO.getCustomer());
			handlingStockInVO2.setWarehouse(savedGrnVO.getWarehouse());
			handlingStockInVO2.setClient(savedGrnVO.getClient());
			handlingStockInVO2.setSdocdate(savedGrnVO.getDocDate());
			handlingStockInVO2.setStockdate(savedGrnVO.getDocDate());
			handlingStockInVO2.setSdocid(savedGrnVO.getDocId());
			handlingStockInVO2.setFinyr(savedGrnVO.getFinYear());
			handlingStockInVO2.setBatchno(grnDetailsVO.getBatchNo());
			handlingStockInVO2.setBatchdt(grnDetailsVO.getBatchDt());
			handlingStockInVO2.setLrhawbhblno(grnDetailsVO.getLrNoHawbNo());
			handlingStockInVO2.setSourcescreen(savedGrnVO.getScreenName());
			handlingStockInVO2.setCreatedby(savedGrnVO.getCreatedBy());
			handlingStockInVO2.setUpdatedby(savedGrnVO.getUpdatedBy());
			handlingStockInVO2.setExpdate(grnDetailsVO.getExpDate());
			handlingStockInVO2.setPartno(grnDetailsVO.getPartNo());
			handlingStockInVO2.setPartdesc(grnDetailsVO.getPartDesc());
			handlingStockInVO2.setLocationtype(grnDetailsVO.getBinType());
			handlingStockInVO2.setSsku(grnDetailsVO.getSku());
			handlingStockInVO2.setRate(grnDetailsVO.getRate());
			handlingStockInVO2.setAmount(grnDetailsVO.getAmount());
			handlingStockInVO2.setSku(grnDetailsVO.getSku());
			handlingStockInVO2.setSsku(grnDetailsVO.getSku());
			handlingStockInVO2.setPalletcount(1);
			handlingStockInVO2.setNoofpallet(grnDetailsVO.getNoOfBins());
			handlingStockInVO2.setInvqty(grnDetailsVO.getInvQty());
			handlingStockInVO2.setRecqty(grnDetailsVO.getRecQty());
			handlingStockInVO2.setShortqty(grnDetailsVO.getShortQty());
			handlingStockInVO2.setDamageqty(0);
			handlingStockInVO2.setSqty(grnDetailsVO.getGrnQty());
			handlingStockInVO2.setPalletqty(grnDetailsVO.getBinQty());
			handlingStockInVO2.setRpqty(grnDetailsVO.getInvQty());
			handlingStockInVO2.setQcflag("T");
			handlingStockInRepo.save(handlingStockInVO2);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGrnVOByGrnDTO(GrnDTO grnDTO, GrnVO grnVO) {
		grnVO.setEntryDate(grnDTO.getEntryDate());
		grnVO.setGrnDate(grnDTO.getGrnDate());
		grnVO.setGatePassId(grnDTO.getGatePassId());

		if(!grnDTO.getGatePassId().isEmpty())
		{
		GatePassInVO gatePassInVO = gatePassInRepo.findByDocId(grnDTO.getGatePassId());
		gatePassInVO.setFreeze(true);
		gatePassInRepo.save(gatePassInVO);
		}
		grnVO.setGatePassDate(grnDTO.getGatePassDate());
		grnVO.setCustomerPo(grnDTO.getCustomerPo());
		grnVO.setSupplierShortName(grnDTO.getSupplierShortName());
		grnVO.setSupplier(grnDTO.getSupplier());
		grnVO.setCarrier(grnDTO.getCarrier());
		grnVO.setLotNo(grnDTO.getLotNo());
		grnVO.setModeOfShipment(grnDTO.getModeOfShipment());
		grnVO.setCreatedBy(grnDTO.getCreatedBy());
		grnVO.setOrgId(grnDTO.getOrgId());
		grnVO.setBranchCode(grnDTO.getBranchCode());
		grnVO.setBranch(grnDTO.getBranch());
		grnVO.setClient(grnDTO.getClient());
		grnVO.setCustomer(grnDTO.getCustomer());
		grnVO.setBillOfEnrtyNo(grnDTO.getBillOfEnrtyNo());
		grnVO.setContainerNo(grnDTO.getContainerNo());
		grnVO.setFifoFlag(grnDTO.getFifoFlag());
		grnVO.setWarehouse(grnDTO.getWarehouse());
		grnVO.setVas(grnDTO.isVas());
		grnVO.setVehicleNo(grnDTO.getVehicleNo());
		grnVO.setVehicleDetails(grnDTO.getVehicleDetails());
		grnVO.setFinYear(grnDTO.getFinYear());
		grnVO.setSealNo(grnDTO.getSealNo());
		grnVO.setVesselNo(grnDTO.getVesselNo());
		grnVO.setHsnNo(grnDTO.getHsnNo());
		grnVO.setSecurityName(grnDTO.getSecurityName());
		grnVO.setVehicleType(grnDTO.getVehicleType());
		grnVO.setVesselDetails(grnDTO.getVesselDetails());
		grnVO.setLrNo(grnDTO.getLrNo());
		grnVO.setDriverName(grnDTO.getDriverName());
		grnVO.setContact(grnDTO.getContact());
		grnVO.setLrDate(grnDTO.getLrDate());
		grnVO.setGoodsDescripition(grnDTO.getGoodsDescripition());
		grnVO.setDestinationFrom(grnDTO.getDestinationFrom());
		grnVO.setDestinationTo(grnDTO.getDestinationTo());
		grnVO.setNoOfBins(grnDTO.getNoOfBins());
		grnVO.setInvoiceNo(grnDTO.getInvoiceNo());
		grnVO.setRemarks(grnDTO.getRemarks());;

		if (ObjectUtils.isNotEmpty(grnVO.getId())) {
			List<GrnDetailsVO> grnDetailsVO1 = grnDetailsRepo.findByGrnVO(grnVO);
			grnDetailsRepo.deleteAll(grnDetailsVO1);
		}

		int totalGrnQty = 0;
		int totalNoOfPkgs = 0;
		double totalAmount = 0.0;

		List<GrnDetailsVO> grnDetailsVOs = new ArrayList<>();
		for (GrnDetailsDTO grnDetailsDTO : grnDTO.getGrnDetailsDTO()) {

			GrnDetailsVO grnDetailsVO = new GrnDetailsVO();
			grnDetailsVO.setQrCode(grnDetailsDTO.getQrCode());
			grnDetailsVO.setLrNoHawbNo(grnDetailsDTO.getLrNoHawbNo());
			grnDetailsVO.setInvoiceNo(grnDetailsDTO.getInvoiceNo());
			grnDetailsVO.setInvoiceDate(grnDetailsDTO.getInvoiceDate());
			grnDetailsVO.setPartNo(grnDetailsDTO.getPartNo());
			grnDetailsVO.setPartDesc(grnDetailsDTO.getPartDesc());
			grnDetailsVO.setBinType(grnDetailsDTO.getBinType());
			grnDetailsVO.setSku(grnDetailsDTO.getSku());
			grnDetailsVO.setInvQty(grnDetailsDTO.getInvQty());
			grnDetailsVO.setRecQty(grnDetailsDTO.getRecQty());

			int shortQty = grnDetailsDTO.getInvQty() - grnDetailsDTO.getRecQty();
			grnDetailsVO.setShortQty(shortQty);
			grnDetailsVO.setDamageQty(grnDetailsDTO.getDamageQty());

			int grnQty = grnDetailsDTO.getRecQty() - grnDetailsDTO.getDamageQty();
			grnDetailsVO.setGrnQty(grnQty);

			grnDetailsVO.setSubStockQty(grnDetailsDTO.getSubStockQty());
			grnDetailsVO.setBatchQty(grnDetailsDTO.getBatchQty());
			grnDetailsVO.setBinQty(grnDetailsDTO.getBinQty());
			grnDetailsVO.setPkgs(grnDetailsDTO.getPkgs());
			grnDetailsVO.setRate(grnDetailsDTO.getRate());
			grnDetailsVO.setWeight(grnDetailsDTO.getWeight());
			grnDetailsVO.setBatchNo(grnDetailsDTO.getBatchNo());
			grnDetailsVO.setBatchDt(grnDetailsDTO.getBatchDt());
			grnDetailsVO.setNoOfBins(grnDetailsDTO.getNoOfBins());
			grnDetailsVO.setAmount(grnDetailsDTO.getAmount());
			grnDetailsVO.setShipmentNo(grnDetailsDTO.getShipmentNo());
			grnDetailsVO.setExpDate(grnDetailsDTO.getExpdate());
			grnDetailsVO.setDamageRemark(grnDetailsDTO.getDamageRemark());
			totalGrnQty = totalGrnQty + grnDetailsDTO.getRecQty();
			totalNoOfPkgs = totalNoOfPkgs + grnDetailsDTO.getPkgs();
			totalAmount = totalAmount + grnDetailsDTO.getAmount();
			grnDetailsVO.setGrnVO(grnVO);
			grnDetailsVOs.add(grnDetailsVO);
		}
		grnVO.setGrnDetailsVO(grnDetailsVOs);
		grnVO.setTotalAmount(totalAmount);
		grnVO.setTotalGrnQty(totalGrnQty);
		grnVO.setNoOfPackage(totalNoOfPkgs);

	}
	
	@Override
	public List<Map<String, Object>> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode) {
		Set<Object[]>grn= gatePassInRepo.findGatePassDetailsByGatePassNo(orgId, client, entryno, docid, branchcode);
		return formateeParameter(grn);
	}
	
	private List<Map<String, Object>> formateeParameter(Set<Object[]> grn) {
		List<Map<String, Object>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : grn) {
			Map<String, Object> param = new HashMap<>();
			param.put("lrnohaw", parameters[0].toString());
			param.put("invoiceno", parameters[1].toString());
			param.put("invoicedate", parameters[2].toString());
			param.put("partno", parameters[3].toString());
			param.put("partdesc", parameters[4].toString());
			param.put("sku", parameters[5].toString());
			param.put("invqty", parameters[6].toString());
			param.put("recqty", parameters[7].toString());
			param.put("damageqty", parameters[8].toString());
			param.put("batchno", parameters[9].toString());
			param.put("weight", parameters[10].toString());
			param.put("rate", parameters[11].toString());
			param.put("rowno", parameters[12].toString());
			formattedParameters.add(param);
		}
		return formattedParameters;
	}
	
	@Override
	public List<GatePassInVO> getGatepassInNoForPendingGRN(Long orgId, String branchCode, String finYear,
			String client) {
		return gatePassInRepo.findAllGatePassinDetailsforPedningGRN(orgId, branchCode, finYear, client);
	}
	
	@Override
	public List<Map<String, Object>> getGatepassInGridDetailsForPendingGRN(Long orgId, String finYear,
			String branchCode, String client, String gatePassDocId) {
		Set<Object[]> gatePassInGridDetails = gatePassInRepo.getGridDetailsByDocId(orgId, finYear, branchCode, client,
				gatePassDocId);
		return gatePassDetails(gatePassInGridDetails);
	}

	private List<Map<String, Object>> gatePassDetails(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("lrNoHaw", grid[0] != null ? grid[0].toString() : "");
			details.put("invoiceNo", grid[1] != null ? grid[1].toString() : "");
			details.put("invoiceDate", grid[2] != null ? grid[2].toString() : "");
			details.put("partNo", grid[3] != null ? grid[3].toString() : "");
			details.put("partDesc", grid[4] != null ? grid[4].toString() : "");
			details.put("sku", grid[5] != null ? grid[5].toString() : "");
			details.put("invQty", grid[6] != null ? Integer.parseInt(grid[6].toString()) : 0);
			details.put("recQty", grid[7] != null ? Integer.parseInt(grid[7].toString()) : 0);
			details.put("shortQty", grid[8] != null ? Integer.parseInt(grid[8].toString()) : 0);
			details.put("damageQty", grid[9] != null ? Integer.parseInt(grid[9].toString()) : 0);
			details.put("genQty", grid[10] != null ? Integer.parseInt(grid[10].toString()) : 0);
			details.put("subStockShortQty", grid[11] != null ? Integer.parseInt(grid[11].toString()) : 0);
			details.put("batchNo", grid[12] != null ? grid[12].toString() : "");
			details.put("weight", grid[13] != null ? grid[13].toString() : "");
			gridDetails.add(details);
		}
		return gridDetails;
	}

	
	
	private int totalRows=0; // Instance variable to keep track of total rows
    private int successfulUploads =0; // Instance variable to keep track of successful uploads
    
    private final DataFormatter dataFormatter = new DataFormatter();


	 @Transactional
	    public void ExcelUploadForGrn(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
	                                   String customer, String client, String finYear,
	                                   String branch, String branchCode, String warehouse) throws ApplicationException {

	        List<GrnExcelUploadVO> grnExcelUploadVOsToSave = new ArrayList<>();
	        totalRows = 0;
	        successfulUploads = 0;

	        for (MultipartFile file : files) {
	            if (file.isEmpty()) {
	                throw new ApplicationException("The supplied file '" + file.getOriginalFilename() + "' is empty.");
	            }

	            try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
	                Sheet sheet = workbook.getSheetAt(0);

	                if (!isHeaderValid(sheet.getRow(0))) {
	                    throw new ApplicationException("Invalid Excel format in file '" + file.getOriginalFilename() + "'.");
	                }

	                for (Row row : sheet) {
	                    if (row.getRowNum() == 0 || isRowEmpty(row)) {
	                        continue;
	                    }

	                    totalRows++;

	                    try {
	                        GrnExcelUploadVO grnExcelUploadVO = new GrnExcelUploadVO();

	                        grnExcelUploadVO.setSno(getNumericCellValue(row.getCell(0)));
	                        grnExcelUploadVO.setEntryNo(getStringCellValue(row.getCell(1)));
	                        grnExcelUploadVO.setEntryDate(getStringCellValue(row.getCell(2)));
	                        grnExcelUploadVO.setSupplierShortname(getStringCellValue(row.getCell(3)));
	                        grnExcelUploadVO.setModeOfShipment(getStringCellValue(row.getCell(4)));
	                        grnExcelUploadVO.setCarrier(getStringCellValue(row.getCell(5)));
	                        grnExcelUploadVO.setLrHblNo(getStringCellValue(row.getCell(6)));
	                        grnExcelUploadVO.setInvDcNo(getStringCellValue(row.getCell(7)));
	                        grnExcelUploadVO.setInvDate(getStringCellValue(row.getCell(8)));
	                        grnExcelUploadVO.setPartNo(getStringCellValue(row.getCell(9)));
	                        grnExcelUploadVO.setPartDesc(getStringCellValue(row.getCell(10)));
	                        grnExcelUploadVO.setSku(getStringCellValue(row.getCell(11)));
	                        grnExcelUploadVO.setInvQty(getNumericCellValue(row.getCell(12)));
	                        grnExcelUploadVO.setRecQty(getNumericCellValue(row.getCell(13)));
	                        grnExcelUploadVO.setDamageQty(getNumericCellValue(row.getCell(14)));
	                        grnExcelUploadVO.setSubStockQty(getNumericCellValue(row.getCell(15)));
	                        grnExcelUploadVO.setBatchNo(getStringCellValue(row.getCell(16)));
	                        grnExcelUploadVO.setBatchDate(getStringCellValue(row.getCell(17)));
	                        grnExcelUploadVO.setExpDate(getStringCellValue(row.getCell(18)));
	                        grnExcelUploadVO.setNoOfPallet(getNumericCellValue(row.getCell(19)));
	                        grnExcelUploadVO.setPalletQty(getNumericCellValue(row.getCell(20)));
	                        grnExcelUploadVO.setWeight(getNumericCellValue1(row.getCell(21)));
	                        grnExcelUploadVO.setRate(getNumericCellValue1(row.getCell(22)));
	                        grnExcelUploadVO.setRemark(getStringCellValue(row.getCell(23)));

	                        grnExcelUploadVO.setOrgId(orgId);
	                        grnExcelUploadVO.setCustomer(customer);
	                        grnExcelUploadVO.setClient(client);
	                        grnExcelUploadVO.setFinYear(finYear);
	                        grnExcelUploadVO.setBranch(branch);
	                        grnExcelUploadVO.setBranchCode(branchCode);
	                        grnExcelUploadVO.setWarehouse(warehouse);
	                        grnExcelUploadVO.setCreatedBy(createdBy);
	                        grnExcelUploadVO.setUpdatedBy(""); // Assuming you set this later or leave it empty
	                        grnExcelUploadVO.setActive(true); // Default or based on some logic
	                        grnExcelUploadVO.setCancel(false); // Default or based on some logic
	                        grnExcelUploadVO.setCancelRemarks("");

	                        // Set additional fields if necessary
	                        grnExcelUploadVOsToSave.add(grnExcelUploadVO);
	                        successfulUploads++;
	                    } catch (Exception e) {
	                        throw new ApplicationException("Error processing row " + (row.getRowNum() + 1) + ": " + e.getMessage());
	                    }
	                }

	                grnExcelUploadRepo.saveAll(grnExcelUploadVOsToSave);
	            } catch (IOException e) {
	                throw new ApplicationException("Failed to process file: " + file.getOriginalFilename(), e);
	            }
	        }
	    }

	    private boolean isHeaderValid(Row headerRow) {
	        List<String> expectedHeaders = Arrays.asList(
	                "Sno", "Entry No*", "Entry Date", "Supplier Shortname*", "Mode of Shipment*", "Carrier*", "LR/HBL No*", "Inv/DC No*", "Inv date", "Part No*", "Part Desc*", "SKU*",
	                "Inv Qty", "Rec Qty", "Damage Qty", "Sub Stock Qty", "Batchno", "Batchdate", "Expdate", "NOOFPallet", "Pallet Qty", "Weight*", "Rate", "Remark"
	        );

	        for (int i = 0; i < expectedHeaders.size(); i++) {
	            String cellValue = getStringCellValue(headerRow.getCell(i));
	            if (!expectedHeaders.get(i).equalsIgnoreCase(cellValue)) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private boolean isRowEmpty(Row row) {
	        for (int i = 0; i < row.getLastCellNum(); i++) {
	            if (row.getCell(i) != null && !getStringCellValue(row.getCell(i)).isEmpty()) {
	                return false;
	            }
	        }
	        return true;
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
	    
	 // Adjust the getNumericCellValue method if you need it to return Double
	    private double getNumericCellValue1(Cell cell) {
	        if (cell == null) {
	            return 0.0; // Use 0.0 for Double return type
	        }
	        switch (cell.getCellType()) {
	            case NUMERIC:
	                return cell.getNumericCellValue();
	            default:
	                return 0.0; // Default value for non-numeric cells
	        }
	    }

	    public int getTotalRows() {
	        return totalRows;
	    }

	    public int getSuccessfulUploads() {
	        return successfulUploads;
	    }
 

}
