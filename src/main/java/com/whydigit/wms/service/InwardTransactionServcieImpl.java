package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GatePassInDetailsDTO;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.GrnDetailsDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GatePassInDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.PutAwayDetailsVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.GatePassInDetailsRepo;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.GrnDetailsRepo;
import com.whydigit.wms.repo.GrnRepo;
import com.whydigit.wms.repo.HandlingStockInRepo;
import com.whydigit.wms.repo.LocationMovementDetailsRepo;
import com.whydigit.wms.repo.LocationMovementRepo;
import com.whydigit.wms.repo.PutAwayDetailsRepo;
import com.whydigit.wms.repo.PutAwayRepo;
import com.whydigit.wms.repo.SalesReturnDetailsRepo;
import com.whydigit.wms.repo.SalesReturnRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.SupplierRepo;

@Service
public class InwardTransactionServcieImpl implements InwardTransactionService {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionServcieImpl.class);

	@Autowired
	GrnRepo grnRepo;

	@Autowired
	GatePassInRepo gatePassInRepo;

	@Autowired
	GrnDetailsRepo grnDetailsRepo;

	@Autowired
	HandlingStockInRepo handlingStockInRepo;

	@Autowired
	PutAwayRepo putAwayRepo;

	@Autowired
	PutAwayDetailsRepo putAwayDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	GatePassInDetailsRepo gatePassInDetailsRepo;

	@Autowired
	SupplierRepo supplierRepo;

	@Autowired
	CarrierRepo carrierRepo;

	@Autowired
	SalesReturnRepo salesReturnRepo;

	@Autowired
	SalesReturnDetailsRepo salesReturnDetailsRepo;

	@Autowired
	LocationMovementRepo locationMovementRepo;

	@Autowired
	LocationMovementDetailsRepo locationMovementDetailsRepo;

	// Grn

	@Override
	public String getGRNdocid(Long orgId, String finYear, String branchCode, String client, String screencode) {

		return null;
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

				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
				handlingStockInVO.setScreencode(savedGrnVO.getScreenCode());
				// Set common values from savedGrnVO
				handlingStockInVO.setRefdate(savedGrnVO.getDocdate());
				handlingStockInVO.setGrnno(savedGrnVO.getDocId());
				handlingStockInVO.setGrndate(savedGrnVO.getDocdate());
				handlingStockInVO.setBranch(savedGrnVO.getBranch());
				handlingStockInVO.setOrgId(savedGrnVO.getOrgId());
				handlingStockInVO.setBranchcode(savedGrnVO.getBranchCode());
				handlingStockInVO.setCustomer(savedGrnVO.getCustomer());
				handlingStockInVO.setWarehouse(savedGrnVO.getWarehouse());
				handlingStockInVO.setClient(savedGrnVO.getClient());
				handlingStockInVO.setSdocdate(savedGrnVO.getDocdate());
				handlingStockInVO.setStockdate(savedGrnVO.getDocdate());
				handlingStockInVO.setSdocid(savedGrnVO.getDocId());
				handlingStockInVO.setFinyr(savedGrnVO.getFinYear());
				// Set values from grnDetailsVO
				handlingStockInVO.setPartno(grnDetailsVO.getPartNo());
				handlingStockInVO.setPartdesc(grnDetailsVO.getPartDesc());
				handlingStockInVO.setRpqty(grnDetailsVO.getGrnQty());
				handlingStockInVO.setSqty(grnDetailsVO.getGrnQty());
				handlingStockInVO.setLocationtype(grnDetailsVO.getBinType());
				handlingStockInVO.setInvqty(grnDetailsVO.getInvQty());
				handlingStockInVO.setRecqty(grnDetailsVO.getRecQty());
				handlingStockInVO.setShortqty(grnDetailsVO.getShortQty());
				handlingStockInVO.setPalletqty(grnDetailsVO.getBinQty());
				handlingStockInVO.setRate(grnDetailsVO.getRate());
				handlingStockInVO.setAmount(grnDetailsVO.getAmount());
				handlingStockInVO.setSku(grnDetailsVO.getSku());
				handlingStockInVO.setSsku(grnDetailsVO.getSku());
				// Check if damageqty is 0
				if (grnDetailsVO.getDamageQty() == 0) {
					handlingStockInVO.setSqty(grnDetailsVO.getGrnQty());
					handlingStockInVO.setQcflag("T");
				} else {
					// If damageqty is not 0, set sqty and damageqty in separate rows
					handlingStockInVO.setSqty(grnDetailsVO.getDamageQty());
					handlingStockInVO.setDamageqty(grnDetailsVO.getDamageQty());
					handlingStockInVO.setQcflag("F");
				}
				handlingStockInRepo.save(handlingStockInVO);
			}
		for (GrnDetailsVO grnDetailsVO : grnDetailsVOLists) {
			// create new obj to store as second row
			HandlingStockInVO handlingStockInVO2 = new HandlingStockInVO();
			handlingStockInVO2.setScreencode(grnVO.getScreenCode());
			handlingStockInVO2.setRefdate(savedGrnVO.getDocdate());
			handlingStockInVO2.setGrnno(savedGrnVO.getDocId());
			handlingStockInVO2.setGrndate(savedGrnVO.getDocdate());
			handlingStockInVO2.setBranch(savedGrnVO.getBranch());
			handlingStockInVO2.setOrgId(savedGrnVO.getOrgId());
			handlingStockInVO2.setBranchcode(savedGrnVO.getBranchCode());
			handlingStockInVO2.setCustomer(savedGrnVO.getCustomer());
			handlingStockInVO2.setWarehouse(savedGrnVO.getWarehouse());
			handlingStockInVO2.setClient(savedGrnVO.getClient());
			handlingStockInVO2.setSdocdate(savedGrnVO.getDocdate());
			handlingStockInVO2.setStockdate(savedGrnVO.getDocdate());
			handlingStockInVO2.setSdocid(savedGrnVO.getDocId());
			handlingStockInVO2.setFinyr(savedGrnVO.getFinYear());
			if (handlingStockInVO2.getDamageqty() == 0) {
				handlingStockInVO2.setQcflag("T");
				handlingStockInVO2.setDamageqty(0);
			} else {
				handlingStockInVO2.setQcflag("F");
			}
			handlingStockInVO2.setPartno(grnDetailsVO.getPartNo());
			handlingStockInVO2.setPartdesc(grnDetailsVO.getPartDesc());
			handlingStockInVO2.setLocationtype(grnDetailsVO.getBinType());
			handlingStockInVO2.setSsku(grnDetailsVO.getSku());
			handlingStockInVO2.setInvqty(grnDetailsVO.getInvQty());
			handlingStockInVO2.setRecqty(grnDetailsVO.getRecQty());

			handlingStockInVO2.setShortqty(grnDetailsVO.getShortQty());
			handlingStockInVO2.setPalletqty(grnDetailsVO.getBinQty());
			handlingStockInVO2.setRate(grnDetailsVO.getRate());
			handlingStockInVO2.setAmount(grnDetailsVO.getAmount());
			handlingStockInVO2.setSqty(grnDetailsVO.getGrnQty());
			handlingStockInVO2.setSku(grnDetailsVO.getSku());
			handlingStockInVO2.setSsku(grnDetailsVO.getSku());
			handlingStockInRepo.save(handlingStockInVO2);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("grnVO", grnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateGrnVOByGrnDTO(GrnDTO grnDTO, GrnVO grnVO) {
	    grnVO.setEntryDate(grnDTO.getEntryDate());
	    grnVO.setGrndDate(grnDTO.getGrndDate());
	    grnVO.setGatePassId(grnDTO.getGatePassId());
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
	    
	    if(ObjectUtils.isNotEmpty(grnVO.getId()))
	    {
	    	List<GrnDetailsVO> grnDetailsVO1= grnDetailsRepo.findByGrnVO(grnVO); 
	    	grnDetailsRepo.deleteAll(grnDetailsVO1);
	    }
	    
	    int totalGrnQty=0;
    	int totalNoOfPkgs=0;
    	double totalAmount = 0.0;
    	
	    List<GrnDetailsVO>grnDetailsVOs= new ArrayList<>();
	    for(GrnDetailsDTO grnDetailsDTO:grnDTO.getGrnDetailsDTO())
	    {
	    	
	    	GrnDetailsVO grnDetailsVO= new GrnDetailsVO();
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
	        
	        int shortQty= grnDetailsDTO.getInvQty() - grnDetailsDTO.getRecQty();
	        grnDetailsVO.setShortQty(shortQty);
	        grnDetailsVO.setDamageQty(grnDetailsDTO.getDamageQty());
	        
	        int grnQty=grnDetailsDTO.getRecQty() - grnDetailsDTO.getDamageQty();
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
	        grnDetailsVO.setMrp(grnDetailsDTO.getMrp());
	        
	        totalGrnQty=totalGrnQty+grnQty;
	        totalNoOfPkgs=totalNoOfPkgs+grnDetailsDTO.getPkgs();
	        totalAmount=totalAmount+grnDetailsDTO.getAmount();
	        grnDetailsVO.setGrnVO(grnVO);     
	        grnDetailsVOs.add(grnDetailsVO);
	    }
	    grnVO.setGrnDetailsVO(grnDetailsVOs);
	    grnVO.setTotalAmount(totalAmount);
	    grnVO.setTotalGrnQty(totalGrnQty);
	    grnVO.setNoOfPackage(totalNoOfPkgs);
	    
}
	// GatePassIn

	@Override
	public List<GatePassInVO> getAllGatePassIn() {
		return gatePassInRepo.findAll();
	}

	@Override
	public Optional<GatePassInVO> getGatePassInById(Long id) {
		return gatePassInRepo.findById(id);
	}

	@Override
	public Map<String, Object> createUpdateGatePassIn(GatePassInDTO gatePassInDTO) throws ApplicationException {
		GatePassInVO gatePassInVO;
		String message;
		if (ObjectUtils.isEmpty(gatePassInDTO.getId())) {

			if (gatePassInRepo.existsByEntryNoAndOrgIdAndBranchCodeAndClient(gatePassInDTO.getEntryNo(),
					gatePassInDTO.getOrgId(), gatePassInDTO.getBranchCode(), gatePassInDTO.getClient())) {
				String errorMessage = String.format("This EntryNo:%s Already Exists This Organization .",
						gatePassInDTO.getEntryNo());
				throw new ApplicationException(errorMessage);
			}

			gatePassInVO = new GatePassInVO();
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

		gatePassInVO.setTransactionType(gatePassInDTO.getTransactionType());
		gatePassInVO.setEntryNo(gatePassInDTO.getEntryNo());
		gatePassInVO.setOrgId(gatePassInDTO.getOrgId());
		gatePassInVO.setDocid(gatePassInDTO.getDocid());
		gatePassInVO.setSupplier(gatePassInDTO.getSupplier());
		gatePassInVO.setSupplierShortName(gatePassInDTO.getSupplierShortName());
		gatePassInVO.setModeOfShipment(gatePassInDTO.getModeOfShipment());
		gatePassInVO.setCarrier(gatePassInDTO.getCarrier());
		gatePassInVO.setCarrierTransport(gatePassInDTO.getCarrierTransport());
		gatePassInVO.setVehicleType(gatePassInDTO.getVehicleType());
		gatePassInVO.setVehicleNo(gatePassInDTO.getVehicleNo());
		gatePassInVO.setDriverName(gatePassInDTO.getDriverName());
		gatePassInVO.setContact(gatePassInDTO.getContact());
		gatePassInVO.setGoodsDescription(gatePassInDTO.getGoodsDescription());
		gatePassInVO.setSecurityName(gatePassInDTO.getSecurityName());
		gatePassInVO.setLotNo(gatePassInDTO.getLotNo());
		gatePassInVO.setCompany(gatePassInDTO.getCompany());
		gatePassInVO.setCancel(gatePassInDTO.isCancel());
		gatePassInVO.setCancelRemark(gatePassInDTO.getCancelRemark());
		gatePassInVO.setActive(gatePassInDTO.isActive());
		gatePassInVO.setBranchCode(gatePassInDTO.getBranchCode());
		gatePassInVO.setBranch(gatePassInDTO.getBranch());
		gatePassInVO.setScreenCode(gatePassInDTO.getScreenCode());
		gatePassInVO.setClient(gatePassInDTO.getClient());
		gatePassInVO.setCustomer(gatePassInDTO.getCustomer());
		gatePassInVO.setFinyr(gatePassInDTO.getFinyr());

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
			detailsVO.setShortQty(gatePassInDetailsDTO.getShortQty());
			detailsVO.setDamageQty(gatePassInDetailsDTO.getDamageQty());
			detailsVO.setGrnQty(gatePassInDetailsDTO.getGrnQty());
			detailsVO.setSubUnit(gatePassInDetailsDTO.getSubUnit());
			detailsVO.setSubStockShortQty(gatePassInDetailsDTO.getSubStockShortQty());
			detailsVO.setGrnPiecesQty(gatePassInDetailsDTO.getGrnPiecesQty());
			detailsVO.setWeight(gatePassInDetailsDTO.getWeight());
			detailsVO.setRate(gatePassInDetailsDTO.getRate());
			detailsVO.setRowNo(gatePassInDetailsDTO.getRowNo());
			detailsVO.setAmount(gatePassInDetailsDTO.getAmount());
			detailsVO.setRemarks(gatePassInDetailsDTO.getRemarks());
			detailsVO.setGatePassInVO(gatePassInVO);
			detailsVOList.add(detailsVO);
		}

		gatePassInVO.setGatePassDetailsVO(detailsVOList);
		return gatePassInVO;

	}

	@Override
	public Optional<GatePassInVO> updateGatePassIn(GatePassInVO gatePassInVO) {
		if (gatePassInRepo.existsById(gatePassInVO.getId())) {
			return Optional.of(gatePassInRepo.save(gatePassInVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteGatePassIn(Long id) {
		gatePassInRepo.deleteById(id);
	}

	@Override
	public Set<Object[]> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode) {
		return gatePassInRepo.findGatePassDetailsByGatePassNo(orgId, client, entryno, docid, branchcode);
	}

	@Override
	public List<CarrierVO> getAllModeOfShipment() {
		return carrierRepo.findmodeOfShipment();
	}

	@Override
	public List<CarrierVO> getActiveShipment(String shipmentMode) {
		return carrierRepo.getActiveShipment(shipmentMode);
	}

	// PutAway

	@Override
	public List<PutAwayVO> getAllPutAway(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return putAwayRepo.findAllPutAway(orgId, finYear, branch, branchCode, client, warehouse);

	}

	@Override
	public PutAwayVO getPutAwayById(Long id) {
		PutAwayVO putAwayVO = new PutAwayVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  PutAway BY Id : {}", id);
			putAwayVO = putAwayRepo.findPutAwayById(id);
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
	public Set<Object[]> getGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode) {
		return putAwayRepo.findGrnNoForPutAway(orgId, client, branch, finyr, branchcode);
	}

//	@Override
//	public PutAwayVO createPutAway(PutAwayDTO putAwayDTO) {
//		PutAwayVO putAwayVO = createPutAwayVOByPutAwayDTO(putAwayDTO);
//		putAwayVO.setScreencode("PC");
//		putAwayRepo.save(putAwayVO);
//
//		PutAwayVO savedPutAwayVO = putAwayRepo.save(putAwayVO);
//		List<PutAwayDetailsVO> putAwayDetailsVOLists = savedPutAwayVO.getPutAwayDetailsVO();
//		if (putAwayDetailsVOLists != null && !putAwayDetailsVOLists.isEmpty())
//			for (PutAwayDetailsVO putAwayDetailsVO : putAwayDetailsVOLists) {
//
//				StockDetailsVO stockDetailsVO = new StockDetailsVO();
//				savedPutAwayVO.setScreencode("PC");
//				stockDetailsVO.setCustomer(savedPutAwayVO.getCustomer());
//				stockDetailsVO.setCore(savedPutAwayVO.getCore());
//				stockDetailsVO.setGrnNo(savedPutAwayVO.getGrnno());
//				stockDetailsVO.setStockDate(savedPutAwayVO.getGrndate());
//				stockDetailsVO.setGrnDate(savedPutAwayVO.getGrndate());
//				stockDetailsVO.setLotNo(savedPutAwayVO.getLotno());
//				stockDetailsVO.setWarehouse(savedPutAwayVO.getWarehouse());
//				stockDetailsVO.setFinYear(savedPutAwayVO.getFinyr());
//				stockDetailsVO.setBranch(savedPutAwayVO.getBranch());
//				stockDetailsVO.setBranchCode(savedPutAwayVO.getBranchcode());
//				stockDetailsVO.setRefNo(savedPutAwayVO.getDocid());
//				stockDetailsVO.setRefDate(savedPutAwayVO.getDocdate());
//				stockDetailsVO.setBinType(savedPutAwayVO.getLocationtype());
//				stockDetailsVO.setCarrier(savedPutAwayVO.getCarrier());
//				stockDetailsVO.setGrnDate(savedPutAwayVO.getGrndate());
//				stockDetailsVO.setScreenCode(savedPutAwayVO.getScreencode());
//				stockDetailsVO.setInvQty(putAwayDetailsVO.getInvqty());
//				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecqty());
//				stockDetailsVO.setShortQty(putAwayDetailsVO.getShortqty());
//				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecqty());
//				stockDetailsVO.setSQty(putAwayDetailsVO.getSqty());
//				stockDetailsVO.setSSku(putAwayDetailsVO.getSsku());
//				stockDetailsVO.setBinClass(putAwayDetailsVO.getLocationclass());
//				stockDetailsVO.setWeight(putAwayDetailsVO.getWeight());
//				stockDetailsVO.setBatchDate(putAwayDetailsVO.getBatchdate());
//				stockDetailsVO.setPartno(putAwayDetailsVO.getPartno());
//				stockDetailsVO.setPartDesc(putAwayDetailsVO.getPartdescripition());
//				stockDetailsVO.setSku(putAwayDetailsVO.getSku());
//				stockDetailsVO.setAmount(putAwayDetailsVO.getAmount());
//				stockDetailsVO.setBatch(putAwayDetailsVO.getBatch());
//				stockDetailsVO.setSsQty(putAwayDetailsVO.getSsqty());
//				stockDetailsRepo.save(stockDetailsVO);
//				// putaway to handlingStockIn
//				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
//				handlingStockInVO.setScreencode("PC");
//				handlingStockInVO.setRefdate(savedPutAwayVO.getDocdate());
//				handlingStockInVO.setSdocdate(savedPutAwayVO.getDocdate());
//				handlingStockInVO.setStockdate(savedPutAwayVO.getDocdate());
//				handlingStockInVO.setGrnno(savedPutAwayVO.getGrnno());
//				handlingStockInVO.setGrndate(savedPutAwayVO.getGrndate());
//				handlingStockInVO.setBranchcode(savedPutAwayVO.getBranchcode());
//				handlingStockInVO.setBranch(savedPutAwayVO.getBranch());
//				handlingStockInVO.setClient(savedPutAwayVO.getClient());
//				handlingStockInVO.setCustomer(savedPutAwayVO.getCustomer());
//				handlingStockInVO.setFinyr(savedPutAwayVO.getFinyr());
//				handlingStockInVO.setRefno(savedPutAwayVO.getDocid());
//				handlingStockInVO.setSdocid(savedPutAwayVO.getFinyr());
//				handlingStockInVO.setWarehouse(savedPutAwayVO.getWarehouse());
//				// Putaway details to handlingStockIn
//				handlingStockInVO.setPartno(putAwayDetailsVO.getPartno());
//				handlingStockInVO.setPartdesc(putAwayDetailsVO.getPartdescripition());
//				handlingStockInVO.setSku(putAwayDetailsVO.getSku());
//				handlingStockInVO.setInvqty(-putAwayDetailsVO.getInvqty());
//				handlingStockInVO.setLocationtype(putAwayDetailsVO.getLocationtype());
//				handlingStockInVO.setRecqty(-putAwayDetailsVO.getRecqty());
//				handlingStockInVO.setSsqty(-putAwayDetailsVO.getSsqty());
//				handlingStockInVO.setRpqty(-putAwayDetailsVO.getPutawayqty());
//				handlingStockInVO.setRate(putAwayDetailsVO.getRate());
//				handlingStockInVO.setAmount(putAwayDetailsVO.getAmount());
//				handlingStockInVO.setSsku(putAwayDetailsVO.getSsku());
//				handlingStockInVO.setShortqty(-putAwayDetailsVO.getShortqty());
//				handlingStockInVO.setSqty(-putAwayDetailsVO.getSqty());
//				handlingStockInRepo.save(handlingStockInVO);
//			}
//		return putAwayVO;
//	}
//
//	private PutAwayVO createPutAwayVOByPutAwayDTO(PutAwayDTO putAwayDTO) {
//		List<PutAwayDetailsVO> putAwayDetailsVOList = new ArrayList<>();
//		PutAwayVO putAwayVO = PutAwayVO.builder().docdate(putAwayDTO.getDocdate()).grnno(putAwayDTO.getGrnno())
//				.grndate(putAwayDTO.getGrndate()).entryno(putAwayDTO.getEntryno()).core(putAwayDTO.getCore())
//				.suppliershortname(putAwayDTO.getSuppliershortname()).branch(putAwayDTO.getBranch())
//				.branchcode(putAwayDTO.getBranhcode()).customer(putAwayDTO.getCustomer()).client(putAwayDTO.getClient())
//				.orgId(putAwayDTO.getOrgId()).createdby(putAwayDTO.getCreatedby()).updatedby(putAwayDTO.getCreatedby())
//				.supplier(putAwayDTO.getSupplier()).modeodshipment(putAwayDTO.getModeodshipment())
//				.carrier(putAwayDTO.getCarrier()).locationtype(putAwayDTO.getLocationtype())
//				.status(putAwayDTO.getStatus()).lotno(putAwayDTO.getLotno()).warehouse(putAwayDTO.getWarehouse())
//				.enteredperson(putAwayDTO.getEnteredperson()).putAwayDetailsVO(putAwayDetailsVOList).build();
//
//		putAwayDetailsVOList = putAwayDTO.getPutAwayDetailsDTO().stream()
//				.map(putaway -> PutAwayDetailsVO.builder().partno(putaway.getPartno()).batch(putaway.getBatch())
//						.partdescripition(putaway.getPartdescripition()).sku(putaway.getSku())
//						.invqty(putaway.getInvqty()).recqty(putaway.getRecqty()).putawayqty(putaway.getPutawayqty())
//						.putawaypiecesqty(putaway.getPutawaypiecesqty()).location(putaway.getLocation())
//						.weight(putaway.getWeight()).amount(putaway.getAmount()).rate(putaway.getRate())
//						.remarks(putaway.getRemarks()).build())
//				.collect(Collectors.toList());
//		putAwayVO.setPutAwayDetailsVO(putAwayDetailsVOList);
//		return putAwayVO;
//	}

	@Override
	public Optional<PutAwayVO> updatePutAway(PutAwayVO putAwayVO) {
		if (putAwayRepo.existsById(putAwayVO.getId())) {
			return Optional.of(putAwayRepo.save(putAwayVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deletePutAway(Long id) {
		putAwayRepo.deleteById(id);
	}

//	SalesReturn
	@Override
	public List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return salesReturnRepo.findAllSalesReturn(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public SalesReturnVO getAllSalesReturnById(Long id) {
		SalesReturnVO salesReturnVO = new SalesReturnVO();
		salesReturnVO = salesReturnRepo.getAllSalesReturnById(id);
		return salesReturnVO;
	}

	@Override
	public Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException {

		SalesReturnVO salesReturnVO = new SalesReturnVO();
		String screenCode = "SR";
		String message;

		if (ObjectUtils.isNotEmpty(salesReturnDTO.getId())) {
			salesReturnVO = salesReturnRepo.findById(salesReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("SalesReturn not found"));

			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);
			message = "SalesReturn Updated Successfully";
		} else {
			salesReturnVO.setCreatedBy(salesReturnDTO.getCreatedBy());
			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());

			String salesReturnDocId = salesReturnRepo.getSalesReturnDocId(salesReturnDTO.getOrgId(),
					salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
					screenCode);
			salesReturnVO.setDocId(salesReturnDocId);
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(salesReturnDTO.getOrgId(),
							salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "SalesReturn  Created Successfully";
		}

		salesReturnRepo.save(salesReturnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnVO", salesReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSalesReturnVOBySalesReturnDTO(SalesReturnDTO salesReturnDTO, SalesReturnVO salesReturnVO) {

		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setTransactionType(salesReturnDTO.getTransactionType());
		salesReturnVO.setEntryNo(salesReturnDTO.getEntryNo());
		salesReturnVO.setEntryDate(salesReturnDTO.getEntryDate());
		salesReturnVO.setPrDate(salesReturnDTO.getPrDate());
		salesReturnVO.setBONo(salesReturnDTO.getBONo());
		salesReturnVO.setBODate(salesReturnDTO.getBODate());
		salesReturnVO.setPRNo(salesReturnDTO.getPRNo());
		salesReturnVO.setBuyerName(salesReturnDTO.getBuyerName());
		salesReturnVO.setBuyerType(salesReturnDTO.getBuyerType());
		salesReturnVO.setSupplier(salesReturnDTO.getSupplier());
		salesReturnVO.setDriverName(salesReturnDTO.getDriverName());
		salesReturnVO.setCarrier(salesReturnDTO.getCarrier());
		salesReturnVO.setModeOfShipment(salesReturnDTO.getModeOfShipment());
		salesReturnVO.setVehicleType(salesReturnDTO.getVehicleType());
		salesReturnVO.setVehicleNo(salesReturnDTO.getVehicleNo());
		salesReturnVO.setContact(salesReturnDTO.getContact());
		salesReturnVO.setSecurityPersonName(salesReturnDTO.getSecurityPersonName());
		salesReturnVO.setTimeIn(salesReturnDTO.getTimeIn());
		salesReturnVO.setTimeOut(salesReturnDTO.getTimeOut());
		salesReturnVO.setBriefDescOfGoods(salesReturnDTO.getBriefDescOfGoods());
		salesReturnVO.setTotalReturnQty(salesReturnDTO.getTotalReturnQty());
		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setCustomer(salesReturnDTO.getCustomer());
		salesReturnVO.setClient(salesReturnDTO.getClient());
		salesReturnVO.setFinYear(salesReturnDTO.getFinYear());
		salesReturnVO.setBranch(salesReturnDTO.getBranch());
		salesReturnVO.setBranchCode(salesReturnDTO.getBranchCode());
		salesReturnVO.setWarehouse(salesReturnDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(salesReturnVO.getId())) {
			List<SalesReturnDetailsVO> salesReturnDetailsVO1 = salesReturnDetailsRepo
					.findBySalesReturnVO(salesReturnVO);
			salesReturnDetailsRepo.deleteAll(salesReturnDetailsVO1);
		}

		List<SalesReturnDetailsVO> salesReturnDetailsVOs = new ArrayList<>();
		for (SalesReturnDetailsDTO salesReturnDetailsDTO : salesReturnDTO.getSalesReturnDetailsDTO()) {

			SalesReturnDetailsVO salesReturnDetailsVO = new SalesReturnDetailsVO();
			salesReturnDetailsVO.setLRNo(salesReturnDetailsDTO.getLRNo());
			salesReturnDetailsVO.setInvoiceNo(salesReturnDetailsDTO.getInvoiceNo());
			salesReturnDetailsVO.setPartNo(salesReturnDetailsDTO.getPartNo());
			salesReturnDetailsVO.setPartDescripition(salesReturnDetailsDTO.getPartDescripition());
			salesReturnDetailsVO.setUnit(salesReturnDetailsDTO.getUnit());
			salesReturnDetailsVO.setPickQty(salesReturnDetailsDTO.getPickQty());
			salesReturnDetailsVO.setRetQty(salesReturnDetailsDTO.getRetQty());
			salesReturnDetailsVO.setDamageQty(salesReturnDetailsDTO.getDamageQty());
			salesReturnDetailsVO.setBatchNo(salesReturnDetailsDTO.getBatchNo());
			salesReturnDetailsVO.setBatchDate(salesReturnDetailsDTO.getBatchDate());
			salesReturnDetailsVO.setExpDate(salesReturnDetailsDTO.getExpDate());
			salesReturnDetailsVO.setNoOfPallet(salesReturnDetailsDTO.getNoOfPallet());
			salesReturnDetailsVO.setPalletQty(salesReturnDetailsDTO.getPalletQty());
			salesReturnDetailsVO.setWeight(salesReturnDetailsDTO.getWeight());
			salesReturnDetailsVO.setRate(salesReturnDetailsDTO.getRate());
			salesReturnDetailsVO.setAmount(salesReturnDetailsDTO.getAmount());
			salesReturnDetailsVO.setInsAmt(salesReturnDetailsDTO.getInsAmt());
			salesReturnDetailsVO.setRemarks(salesReturnDetailsDTO.getRemarks());
			salesReturnDetailsVO.setQcFlag(salesReturnDetailsDTO.isQcFlag());
			salesReturnDetailsVO.setSalesReturnVO(salesReturnVO);

			salesReturnDetailsVOs.add(salesReturnDetailsVO);
		}
		salesReturnVO.setSalesReturnDetailsVO(salesReturnDetailsVOs);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId,
			String branchCode) {

		Set<Object[]> result = salesReturnRepo.findSalesReturnFillGridDetails(docId, client, orgId, branchCode);
		return getResult(result);
	}

	private List<Map<String, Object>> getResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partCode", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("pickQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "SR";
		String result = salesReturnRepo.getSalesReturnDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

//	LocationMovement
	@Override
	public List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return locationMovementRepo.findAllLocationMovement(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public LocationMovementVO getAllLocationMovementById(Long id) {
		LocationMovementVO locationMovementVO = new LocationMovementVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received LocationMovement BY Id : {}", id);
			locationMovementVO = locationMovementRepo.getAllLocationMovementById(id);
		} else {
			LOGGER.info("Enter the id to get details :");
		}
		return locationMovementVO;
	}

	@Override
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
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescripition());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGRNNo());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setQcFlag(detailsVO.isQcFlag());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setStatus(detailsVO.getStatus());
				stockDetailsVOFrom.setSQty(detailsVO.getFromQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOFrom.setSku(savedLocationMovementVO.getSku());
				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
				stockDetailsVOTo.setBin(detailsVO.getToBin());
				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
				stockDetailsVOTo.setBinClass(detailsVO.getBinClass());
				stockDetailsVOTo.setBinType(detailsVO.getBinType());
				stockDetailsVOTo.setQcFlag(detailsVO.isQcFlag());
				stockDetailsVOTo.setPartDesc(detailsVO.getPartDescripition());
				stockDetailsVOTo.setGrnNo(detailsVO.getGRNNo());
				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setStatus(detailsVO.getStatus());
				stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOTo.setSku(savedLocationMovementVO.getSku());
				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOTo);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("locationMovementVO", locationMovementVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateLocationMovementVOByLocationMovementDTO(LocationMovementDTO locationMovementDTO,
			LocationMovementVO locationMovementVO) {

		locationMovementVO.setOrgId(locationMovementDTO.getOrgId());
		locationMovementVO.setType(locationMovementDTO.getType());
		locationMovementVO.setCustomer(locationMovementDTO.getCustomer());
		locationMovementVO.setClient(locationMovementDTO.getClient());
		locationMovementVO.setFinYear(locationMovementDTO.getFinYear());
		locationMovementVO.setBranchCode(locationMovementDTO.getBranchCode());
		locationMovementVO.setBranch(locationMovementDTO.getBranch());
		locationMovementVO.setWarehouse(locationMovementDTO.getWarehouse());
		locationMovementVO.setSku(locationMovementDTO.getSku());
		locationMovementVO.setCore(locationMovementDTO.getCore());

		if (ObjectUtils.isNotEmpty(locationMovementVO.getId())) {
			List<LocationMovementDetailsVO> locationMovementDetailsVO1 = locationMovementDetailsRepo
					.findByLocationMovementVO(locationMovementVO);
			locationMovementDetailsRepo.deleteAll(locationMovementDetailsVO1);
		}

		List<LocationMovementDetailsVO> locationMovementDetailsVOs = new ArrayList<>();
		for (LocationMovementDetailsDTO locationMovementDetailsDTO : locationMovementDTO
				.getLocationMovementDetailsDTO()) {

			LocationMovementDetailsVO locationMovementDetailsVO = new LocationMovementDetailsVO();
			locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getBin());
			locationMovementDetailsVO.setPartNo(locationMovementDetailsDTO.getPartNo());
			locationMovementDetailsVO.setPartDescripition(locationMovementDetailsDTO.getPartDescripition());
			locationMovementDetailsVO.setGRNNo(locationMovementDetailsDTO.getGRNNo());
			locationMovementDetailsVO.setBatchNo(locationMovementDetailsDTO.getBatchNo());
			locationMovementDetailsVO.setBatchDate(locationMovementDetailsDTO.getBatchDate());
			locationMovementDetailsVO.setLotNo(locationMovementDetailsDTO.getLotNo());
			locationMovementDetailsVO.setToBin(locationMovementDetailsDTO.getToBin());
			locationMovementDetailsVO.setFromQty(locationMovementDetailsDTO.getFromQty());
			locationMovementDetailsVO.setToQty(locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO.setRemainingQty(locationMovementDetailsDTO.getFromQty() - locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO.setGrnDate(locationMovementDetailsDTO.getGrnDate());
			locationMovementDetailsVO.setSku(locationMovementDetailsDTO.getSku());
			locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getBinType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
			locationMovementDetailsVO.setStatus(locationMovementDetailsDTO.getStatus());
			locationMovementDetailsVO.setQcFlag(locationMovementDetailsDTO.isQcFlag());
			locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);

			locationMovementDetailsVOs.add(locationMovementDetailsVO);
		}
		locationMovementVO.setLocationMovementDetailsVO(locationMovementDetailsVOs);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = locationMovementRepo.findBinFromStockForLocationMovement(orgId, finYear, branch,
				branchCode, client);
		return getMovementResult(result);
	}

	private List<Map<String, Object>> getMovementResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin) {

		Set<Object[]> result = locationMovementRepo.findPartNoAndPartDescFromStockForLocationMovement(orgId, finYear,
				branch, branchCode, client, bin);
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
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin,String partNo,String partDesc,String sku) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(orgId, finYear,
				branch, branchCode, client, bin, partNo,partDesc,sku);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchNo", fs[1] != null ? fs[1].toString() : "");
			part.put("batchDate", fs[2] != null ? fs[2].toString() : "");
			part.put("LotNo", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "LM";
		String result = salesReturnRepo.getSalesReturnDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

}