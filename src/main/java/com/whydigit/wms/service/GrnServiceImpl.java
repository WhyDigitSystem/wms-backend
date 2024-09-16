package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.GrnDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.GrnDetailsRepo;
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

	@Override
	public List<Map<String, Object>> getGrnStatusForDashBoard(Long orgId, String finYear, String branchCode,
			String client,String warehouse) {
		Set<Object[]> getGrnStatus = gatePassInRepo.getGrnDetails(orgId, finYear, branchCode, client,warehouse);
		return getGrnStatus(getGrnStatus);
	}

	private List<Map<String, Object>> getGrnStatus(Set<Object[]> gatePassInGridDetails) {
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
