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

import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.PutAwayDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.entity.PutAwayDetailsVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GrnRepo;
import com.whydigit.wms.repo.HandlingStockInRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.PutAwayDetailsRepo;
import com.whydigit.wms.repo.PutAwayRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

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
				handlingStockInVO.setExpdate(putAwayDetailsVO.getExpdate());
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
				stockDetailsVO.setExpDate(putAwayDetailsVO.getExpdate());
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
					stockDetailsVO.setBinType("DAMAGE");
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
			putAwayDetailsVOs.setExpdate(putAwayDetailsDTO.getExpdate());
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

}
