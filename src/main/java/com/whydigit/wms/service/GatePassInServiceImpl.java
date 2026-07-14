package com.whydigit.wms.service;

import java.io.InputStream;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GatePassInDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GatePassInDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GatePassInDetailsRepo;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.SupplierRepo;

@RestController
@RequestMapping("/api/gatePassIn")
public class GatePassInServiceImpl implements GatePassInService {

	public static final Logger LOGGER = LoggerFactory.getLogger(GatePassInServiceImpl.class);

	@Autowired
	GatePassInRepo gatePassInRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	GatePassInDetailsRepo gatePassInDetailsRepo;

	@Autowired
	SupplierRepo supplierRepo;

	@Autowired
	CarrierRepo carrierRepo;

	// GatePassIn

	@Override
	public List<GatePassInVO> getAllGatePassIn(Long orgId, String branchCode, String finYear, String client) {
		return gatePassInRepo.findAllgatePassinDetails(orgId, branchCode, finYear, client);
	}

	@Override
	public Optional<GatePassInVO> getGatePassInById(Long id) {
		return gatePassInRepo.findById(id);
	}

	@Override
	public String getGatePassInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "GP";
		String result = gatePassInRepo.getGatePassInDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateGatePassIn(GatePassInDTO gatePassInDTO) throws ApplicationException {
		GatePassInVO gatePassInVO;
		String message;
		String screenCode = "GP";
		if (ObjectUtils.isEmpty(gatePassInDTO.getId())) {

			if (gatePassInRepo.existsByEntryNoAndOrgIdAndBranchCodeAndClient(gatePassInDTO.getEntryNo(),
					gatePassInDTO.getOrgId(), gatePassInDTO.getBranchCode(), gatePassInDTO.getClient())) {
				String errorMessage = String.format("This EntryNo:%s Already Exists This Organization .",
						gatePassInDTO.getEntryNo());
				throw new ApplicationException(errorMessage);
			}

			gatePassInVO = new GatePassInVO();

//				GETDOCID API
			String docId = gatePassInRepo.getGatePassInDocId(gatePassInDTO.getOrgId(), gatePassInDTO.getFinYear(),
					gatePassInDTO.getBranchCode(), gatePassInDTO.getClient(), screenCode);

			gatePassInVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(gatePassInDTO.getOrgId(),
							gatePassInDTO.getFinYear(), gatePassInDTO.getBranchCode(), gatePassInDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			gatePassInVO.setCreatedBy(gatePassInDTO.getCreatedBy());
			gatePassInVO.setUpdatedBy(gatePassInDTO.getCreatedBy());
			message = "GatePass Creation SucessFully";

		} else {
			gatePassInVO = gatePassInRepo.findById(gatePassInDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Not Found Any Informations,Invalid Id" + gatePassInDTO.getId()));
			gatePassInVO.setUpdatedBy(gatePassInDTO.getCreatedBy());

			if (!gatePassInVO.getEntryNo().equalsIgnoreCase(gatePassInDTO.getEntryNo())) {

				if (gatePassInRepo.existsByEntryNoAndOrgIdAndBranchCodeAndClient(gatePassInDTO.getEntryNo(),
						gatePassInDTO.getOrgId(), gatePassInDTO.getBranchCode(), gatePassInDTO.getClient())) {
					String errorMessage = String.format("This EntryNo:%s Already Exists This Organization .",
							gatePassInDTO.getEntryNo());
					throw new ApplicationException(errorMessage);
				}
				gatePassInVO.setEntryNo(gatePassInDTO.getEntryNo());
			}
			message = "GatePass Updation SucessFully";

		}
		geGatePassInVOFromGatePassInDTO(gatePassInVO, gatePassInDTO);
		gatePassInRepo.save(gatePassInVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("gatePassInVO", gatePassInVO);
		return response;
	}

	private GatePassInVO geGatePassInVOFromGatePassInDTO(GatePassInVO gatePassInVO, GatePassInDTO gatePassInDTO) {

		gatePassInVO.setEntryNo(gatePassInDTO.getEntryNo());
		gatePassInVO.setEntryDate(gatePassInDTO.getEntryDate());
		gatePassInVO.setOrgId(gatePassInDTO.getOrgId());
		gatePassInVO.setSupplier(gatePassInDTO.getSupplier());
		gatePassInVO.setSupplierShortName(gatePassInDTO.getSupplierShortName());
		gatePassInVO.setModeOfShipment(gatePassInDTO.getModeOfShipment());
		gatePassInVO.setCarrier(gatePassInDTO.getCarrier());
		gatePassInVO.setVehicleType(gatePassInDTO.getVehicleType());
		gatePassInVO.setVehicleNo(gatePassInDTO.getVehicleNo());
		gatePassInVO.setDriverName(gatePassInDTO.getDriverName());
		gatePassInVO.setContact(gatePassInDTO.getContact());
		gatePassInVO.setGoodsDescription(gatePassInDTO.getGoodsDescription());
		gatePassInVO.setSecurityName(gatePassInDTO.getSecurityName());
		gatePassInVO.setLotNo(gatePassInDTO.getLotNo());
		gatePassInVO.setBranchCode(gatePassInDTO.getBranchCode());
		gatePassInVO.setBranch(gatePassInDTO.getBranch());
		gatePassInVO.setClient(gatePassInDTO.getClient());
		gatePassInVO.setCustomer(gatePassInDTO.getCustomer());
		gatePassInVO.setFinYear(gatePassInDTO.getFinYear());
		gatePassInVO.setRemarks(gatePassInDTO.getRemarks());

		if (gatePassInDTO.getId() != null) {

			List<GatePassInDetailsVO> detailsVOs = gatePassInDetailsRepo.findByGatePassInVO(gatePassInVO);
			gatePassInDetailsRepo.deleteAll(detailsVOs);

		}

		List<GatePassInDetailsVO> detailsVOList = new ArrayList<GatePassInDetailsVO>();
		for (GatePassInDetailsDTO gatePassInDetailsDTO : gatePassInDTO.getGatePassInDetailsDTO()) {

			GatePassInDetailsVO detailsVO = new GatePassInDetailsVO();
			detailsVO.setSNo(gatePassInDetailsDTO.getSNo());
			detailsVO.setIrNoHaw(gatePassInDetailsDTO.getIrNoHaw());
			detailsVO.setInvoiceNo(gatePassInDetailsDTO.getInvoiceNo());
			detailsVO.setInvoiceDate(gatePassInDetailsDTO.getInvoiceDate());
			detailsVO.setPartNo(gatePassInDetailsDTO.getPartNo());
			detailsVO.setPartCode(gatePassInDetailsDTO.getPartCode());
			detailsVO.setPartDescription(gatePassInDetailsDTO.getPartDescription());
			detailsVO.setBatchNo(gatePassInDetailsDTO.getBatchNo());
			detailsVO.setUnit(gatePassInDetailsDTO.getUnit());
			detailsVO.setSku(gatePassInDetailsDTO.getSku());
			detailsVO.setInvQty(gatePassInDetailsDTO.getInvQty());
			detailsVO.setRecQty(gatePassInDetailsDTO.getRecQty());
			detailsVO.setQrCode(gatePassInDetailsDTO.getQrCode());

			int shortQty = gatePassInDetailsDTO.getInvQty() - gatePassInDetailsDTO.getRecQty();
			detailsVO.setShortQty(shortQty);
			detailsVO.setDamageQty(gatePassInDetailsDTO.getDamageQty());

			int grnQty = gatePassInDetailsDTO.getRecQty() - gatePassInDetailsDTO.getDamageQty();
			detailsVO.setGrnQty(grnQty);
			detailsVO.setSubUnit(gatePassInDetailsDTO.getSubUnit());
			detailsVO.setSubStockShortQty(gatePassInDetailsDTO.getSubStockShortQty());
			detailsVO.setGrnPiecesQty(gatePassInDetailsDTO.getGrnPiecesQty());
			detailsVO.setWeight(gatePassInDetailsDTO.getWeight());
			detailsVO.setRate(gatePassInDetailsDTO.getRate());
			detailsVO.setRowNo(gatePassInDetailsDTO.getRowNo());
			detailsVO.setAmount(gatePassInDetailsDTO.getAmount());
			detailsVO.setRemarks(gatePassInDetailsDTO.getRemarks());
			detailsVO.setExpDate(gatePassInDetailsDTO.getExpDate());
			detailsVO.setBatchDate(gatePassInDetailsDTO.getBatchDate());
			detailsVO.setGatePassInVO(gatePassInVO);
			detailsVOList.add(detailsVO);
		}

		gatePassInVO.setGatePassDetailsVO(detailsVOList);
		return gatePassInVO;

	}

	@Override
	public List<Map<String, Object>> getEntryDetails(Long orgId, String finYear, String branchCode, String client,
			String entryNo) {
		Set<Object[]> getEnrty = gatePassInRepo.getEntryNoDetails(orgId, finYear, branchCode, client, entryNo);
		return entryDetails(getEnrty);
	}

	private List<Map<String, Object>> entryDetails(Set<Object[]> getEnrty) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] detail : getEnrty) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("entryNo", detail[0] != null ? detail[0].toString() : "");
			mp.put("entryDate", detail[1] != null ? detail[1].toString() : "");
			mp.put("supplier", detail[2] != null ? detail[2].toString() : "");
			mp.put("supplierShortName", detail[3] != null ? detail[3].toString() : "");
			mp.put("modeOfShipment", detail[4] != null ? detail[4].toString() : "");
			mp.put("carrier", detail[5] != null ? detail[5].toString() : "");
			mp.put("carrierShortName", detail[6] != null ? detail[6].toString() : "");
			details.add(mp);
		}
		return details;
	}

	@Override
	public List<Map<String, Object>> getEntryFillDetails(Long orgId, String finYear, String branchCode, String client,
			String entryNo) {
		Set<Object[]> getEnrtyFillDetails = gatePassInRepo.getEntryNoFillDetails(orgId, finYear, branchCode, client,
				entryNo);
		return entryFillDetails(getEnrtyFillDetails);
	}

	private List<Map<String, Object>> entryFillDetails(Set<Object[]> getEnrtyFillDetails) {
		List<Map<String, Object>> details = new ArrayList<>();
		for (Object[] detail : getEnrtyFillDetails) {
			Map<String, Object> mp = new HashMap<>();
			mp.put("id", detail[0] != null ? Integer.parseInt(detail[0].toString()) : 0); // row_number() over() as id
			mp.put("irNoHaw", detail[1] != null ? detail[1].toString() : ""); // now irNoHaw from detail[1]
			mp.put("invoiceNo", detail[2] != null ? detail[2].toString() : "");
			mp.put("invoiceDate", detail[3] != null ? detail[3].toString() : "");
			mp.put("partNo", detail[4] != null ? detail[4].toString() : "");
			mp.put("partDesc", detail[5] != null ? detail[5].toString() : "");
			mp.put("sku", detail[6] != null ? detail[6].toString() : "");
			mp.put("batchNo", detail[7] != null ? detail[7].toString() : "");
			mp.put("batchDate", detail[8] != null ? detail[8].toString() : "");
			mp.put("expDate", detail[9] != null ? detail[9].toString() : "");
			mp.put("invQty", detail[10] != null ? Integer.parseInt(detail[10].toString()) : 0);
			mp.put("recQty", detail[11] != null ? Integer.parseInt(detail[11].toString()) : 0);
			mp.put("damageQty", detail[12] != null ? Integer.parseInt(detail[12].toString()) : 0);
			mp.put("binQty", detail[13] != null ? Integer.parseInt(detail[13].toString()) : 0);
			mp.put("shortQty", detail[14] != null ? Integer.parseInt(detail[14].toString()) : 0);
			mp.put("grnQty", detail[15] != null ? Integer.parseInt(detail[15].toString()) : 0);

			details.add(mp);
		}
		return details;
	}

//	@Override
//	@Transactional
//	public void uploadGatePassExcelUpload(MultipartFile file, Long orgId, String createdBy, String customer,
//			String client, String finYear, String branch, String branchCode, String warehouse) throws Exception {
//		List<GatePassInVO> customersDTOList = new ArrayList<>();
//
//		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
//			processCustomerSheet(workbook.getSheetAt(0), customersDTOList, orgId, createdBy, customer, client, finYear,
//					branch, branchCode, warehouse);
//			processGatePassDetailsSheet(workbook.getSheetAt(1), customersDTOList); // Pass the same list
//		}
//	}
//
//	private void processCustomerSheet(Sheet sheet, List<GatePassInVO> customersDTOList, Long orgId, String createdBy,
//			String customer, String client, String finYear, String branch, String branchCode, String warehouse) {
//		for (Row row : sheet) {
//			if (row.getRowNum() == 0)
//				continue;
//
//			GatePassInVO gatePass = new GatePassInVO();
//			gatePass.setEntryNo(getStringCellValue(row.getCell(0)));
//			gatePass.setEntryDate(getLocalDateCellValue(row.getCell(1)));
//			gatePass.setSupplierShortName(getStringCellValue(row.getCell(2)));
//			gatePass.setSupplier(getStringCellValue(row.getCell(3)));
//			gatePass.setModeOfShipment(getStringCellValue(row.getCell(4)));
//			gatePass.setCarrier(getStringCellValue(row.getCell(5)));
//			gatePass.setRemarks(getStringCellValue(row.getCell(6)));
//			gatePass.setCreatedBy(createdBy);
//			gatePass.setOrgId(orgId);
//			gatePass.setClient(client);
//			gatePass.setCustomer(customer);
//			gatePass.setBranch(branch);
//			gatePass.setBranchCode(branchCode);
//			gatePass.setFinYear(finYear);
//
//			String screenCode = "GP";
//
//			String docId = gatePassInRepo.getGatePassInDocId(orgId, finYear, branchCode, client, screenCode);
//
//			gatePass.setDocId(docId);
//
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(orgId, finYear, branchCode, client,
//							screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			// Set SNo from Excel (Column H)
//			gatePass.setSno(getIntegerCellValue(row.getCell(7)));
//
//			customersDTOList.add(gatePass);
//		}
//	}
//
//	private void processGatePassDetailsSheet(Sheet sheet, List<GatePassInVO> customersDTOList) {
//		for (Row row : sheet) {
//			if (row.getRowNum() == 0)
//				continue;
//
//			int primaryNo = getIntegerCellValue(row.getCell(10));
//
//			// Find the matching GatePassInVO using SNo
//			customersDTOList.stream().filter(c -> c.getSno() == primaryNo).findFirst().ifPresent(gatePass -> {
//				GatePassInDetailsVO details = new GatePassInDetailsVO();
//				details.setIrNoHaw(getStringCellValue(row.getCell(0)));
//				details.setInvoiceNo(getStringCellValue(row.getCell(1)));
//				details.setInvoiceDate(getLocalDateCellValue(row.getCell(2)));
//				details.setPartNo(getStringCellValue(row.getCell(3)));
//				details.setSku(getStringCellValue(row.getCell(4)));
//				details.setInvQty(getIntegerCellValue(row.getCell(5))); // Column F is Inv Qty
//				details.setRecQty(getIntegerCellValue(row.getCell(6))); // Column G is Rec Qty
//
//				int invQty = getIntegerCellValue(row.getCell(5));
//				int recQty = getIntegerCellValue(row.getCell(6));
//				int shortQty = invQty - recQty;
//				details.setShortQty(shortQty);
//
//				details.setDamageQty(getIntegerCellValue(row.getCell(7)));
//
//				int damageQty = getIntegerCellValue(row.getCell(7));
//				int grnQty = recQty - damageQty;
//				details.setGrnQty(grnQty);
//
//				details.setBatchDate(getLocalDateCellValue(row.getCell(8))); // Column I is Batch Date
//				details.setExpDate(getLocalDateCellValue(row.getCell(9))); // Column J is Exp Date
//
//				// Add details to the gate pass
//				gatePass.getGatePassDetailsVO().add(details);
//			});
//		}
//	}
//
//	private Integer getIntegerCellValue(Cell cell) {
//		if (cell == null)
//			return 0;
//		switch (cell.getCellType()) {
//		case NUMERIC:
//			return (int) cell.getNumericCellValue();
//		case STRING:
//			try {
//				return Integer.parseInt(cell.getStringCellValue().trim());
//			} catch (NumberFormatException e) {
//				return 0;
//			}
//		default:
//			return 0;
//		}
//	}
//
//	private String getStringCellValue(Cell cell) {
//		if (cell == null)
//			return "";
//
//		switch (cell.getCellType()) {
//		case STRING:
//			return cell.getStringCellValue().trim();
//		case NUMERIC:
//			double numericValue = cell.getNumericCellValue();
//			if (numericValue == Math.floor(numericValue)) {
//				return String.valueOf((long) numericValue);
//			}
//			return String.valueOf(numericValue);
//		default:
//			return "";
//		}
//	}
//
//	private LocalDate getLocalDateCellValue(Cell cell) {
//		if (cell == null || !DateUtil.isCellDateFormatted(cell)) {
//			return null;
//		}
//		Date date = cell.getDateCellValue(); // returns java.util.Date
//		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	}

	@Override
	@Transactional
	public void uploadGatePassExcelUpload(MultipartFile file, Long orgId, String createdBy, String customer,
			String client, String finYear, String branch, String branchCode, String warehouse) throws Exception {
		List<GatePassInVO> customersDTOList = new ArrayList<>();

		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {

			processCustomerSheet(workbook.getSheetAt(0), customersDTOList, orgId, createdBy, customer, client, finYear,
					branch, branchCode, warehouse);
			processGatePassDetailsSheet(workbook.getSheetAt(1), customersDTOList);

			if (!customersDTOList.isEmpty()) {
				saveGatePassData(customersDTOList);
			}
		}
	}

	private void saveGatePassData(List<GatePassInVO> customersDTOList) {
		for (GatePassInVO gatePass : customersDTOList) {
			GatePassInVO savedGatePass = gatePassInRepo.save(gatePass);

			if (gatePass.getGatePassDetailsVO() != null && !gatePass.getGatePassDetailsVO().isEmpty()) {
				for (GatePassInDetailsVO detail : gatePass.getGatePassDetailsVO()) {
					detail.setGatePassInVO(savedGatePass);
					gatePassInDetailsRepo.save(detail);
				}
			}
		}
	}

	private void processCustomerSheet(Sheet sheet, List<GatePassInVO> customersDTOList, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse) {
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			GatePassInVO gatePass = new GatePassInVO();
			gatePass.setEntryNo(getStringCellValue(row.getCell(0)));
			gatePass.setEntryDate(getLocalDateCellValue(row.getCell(1)));
			gatePass.setSupplierShortName(getStringCellValue(row.getCell(2)));
			gatePass.setSupplier(getStringCellValue(row.getCell(3)));
			gatePass.setModeOfShipment(getStringCellValue(row.getCell(4)));
			gatePass.setCarrier(getStringCellValue(row.getCell(5)));
			gatePass.setRemarks(getStringCellValue(row.getCell(6)));
			gatePass.setCreatedBy(createdBy);
			gatePass.setOrgId(orgId);
			gatePass.setClient(client);
			gatePass.setCustomer(customer);
			gatePass.setBranch(branch);
			gatePass.setBranchCode(branchCode);
			gatePass.setFinYear(finYear);

			String screenCode = "GP";

			String docId = gatePassInRepo.getGatePassInDocId(orgId, finYear, branchCode, client, screenCode);
			gatePass.setDocId(docId);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(orgId, finYear, branchCode, client,
							screenCode);

			if (documentTypeMappingDetailsVO != null) {
				documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			} else {
				documentTypeMappingDetailsVO = new DocumentTypeMappingDetailsVO();
				documentTypeMappingDetailsVO.setOrgId(orgId);
				documentTypeMappingDetailsVO.setFinYear(finYear);
				documentTypeMappingDetailsVO.setBranchCode(branchCode);
				documentTypeMappingDetailsVO.setClient(client);
				documentTypeMappingDetailsVO.setScreenCode(screenCode);
				documentTypeMappingDetailsVO.setLastno(1);
				documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			}

			gatePass.setSno(getIntegerCellValue(row.getCell(7)));

			if (gatePass.getGatePassDetailsVO() == null) {
				gatePass.setGatePassDetailsVO(new ArrayList<>());
			}

			customersDTOList.add(gatePass);
		}
	}

	private void processGatePassDetailsSheet(Sheet sheet, List<GatePassInVO> customersDTOList) {
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;

			int primaryNo = getIntegerCellValue(row.getCell(10));

			for (GatePassInVO gatePass : customersDTOList) {
				if (gatePass.getSno() == primaryNo) {

					if (gatePass.getGatePassDetailsVO() == null) {
						gatePass.setGatePassDetailsVO(new ArrayList<>());
					}

					GatePassInDetailsVO details = new GatePassInDetailsVO();
					details.setIrNoHaw(getStringCellValue(row.getCell(0)));
					details.setInvoiceNo(getStringCellValue(row.getCell(1)));
					details.setInvoiceDate(getLocalDateCellValue(row.getCell(2)));
					details.setPartNo(getStringCellValue(row.getCell(3)));
					details.setSku(getStringCellValue(row.getCell(4)));

					int invQty = getIntegerCellValue(row.getCell(5));
					details.setInvQty(invQty);

					int recQty = getIntegerCellValue(row.getCell(6));
					details.setRecQty(recQty);

					int shortQty = invQty - recQty;
					details.setShortQty(shortQty);

					int damageQty = getIntegerCellValue(row.getCell(7));
					details.setDamageQty(damageQty);

					int grnQty = recQty - damageQty;
					details.setGrnQty(grnQty);

					details.setBatchDate(getLocalDateCellValue(row.getCell(8)));
					details.setExpDate(getLocalDateCellValue(row.getCell(9)));

					details.setGatePassInVO(gatePass);

					gatePass.getGatePassDetailsVO().add(details);
					break;
				}
			}
		}
	}

	private Integer getIntegerCellValue(Cell cell) {
		if (cell == null)
			return 0;

		try {
			switch (cell.getCellType()) {
			case NUMERIC:
				return (int) cell.getNumericCellValue();
			case STRING:
				String value = cell.getStringCellValue().trim();
				if (value.isEmpty()) {
					return 0;
				}
				if (value.contains(".")) {
					return (int) Double.parseDouble(value);
				}
				return Integer.parseInt(value);
			case FORMULA:
				try {
					return (int) cell.getNumericCellValue();
				} catch (Exception e) {
					try {
						String formulaValue = cell.getStringCellValue().trim();
						if (!formulaValue.isEmpty()) {
							if (formulaValue.contains(".")) {
								return (int) Double.parseDouble(formulaValue);
							}
							return Integer.parseInt(formulaValue);
						}
					} catch (Exception ex) {
						return 0;
					}
					return 0;
				}
			default:
				return 0;
			}
		} catch (Exception e) {
			return 0;
		}
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null)
			return "";

		try {
			switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue().trim();
			case NUMERIC:
				double numericValue = cell.getNumericCellValue();
				if (numericValue == Math.floor(numericValue)) {
					return String.valueOf((long) numericValue);
				}
				return String.valueOf(numericValue);
			case FORMULA:
				try {
					return cell.getStringCellValue().trim();
				} catch (IllegalStateException e) {
					double val = cell.getNumericCellValue();
					if (val == Math.floor(val)) {
						return String.valueOf((long) val);
					}
					return String.valueOf(val);
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			default:
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	private LocalDate getLocalDateCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}

		try {
			if (cell.getCellType() == CellType.STRING) {
				String dateStr = cell.getStringCellValue().trim();
				if (!dateStr.isEmpty()) {
					try {
						if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
							return LocalDate.parse(dateStr);
						}
						if (dateStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							return LocalDate.parse(dateStr, formatter);
						}
						if (dateStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
							return LocalDate.parse(dateStr, formatter);
						}
						if (dateStr.matches("\\d{2}-\\d{2}-\\d{4}")) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
							return LocalDate.parse(dateStr, formatter);
						}
					} catch (Exception e) {
						return null;
					}
				}
				return null;
			}

			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
