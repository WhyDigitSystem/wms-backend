package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GatePassInDetailsDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GatePassInDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;
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

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	// Grn

	@Override
	public List<GrnVO> getAllGrn() {
		return grnRepo.findAll();
	}

	@Override
	public Optional<GrnVO> getGrnById(Long id) {
		return grnRepo.findById(id);
	}

//	@Override
//	public Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer,
//			String branchcode) {
//		return grnRepo.findAllGatePassNumberByClientAndBranch(orgId, client, customer, branchcode);
//	}

	@Override
	public void deleteGrn(Long id) {
		grnRepo.deleteById(id);
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
	public List<PutAwayVO> getAllPutAway() {
		return putAwayRepo.findAll();
	}

	@Override
	public Optional<PutAwayVO> getPutAwayById(Long id) {
		return putAwayRepo.findById(id);
	}

	@Override
	public Set<Object[]> getGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode) {
		return putAwayRepo.findGrnNoForPutAway(orgId, client, branch, finyr, branchcode);
	}

	@Override
	public PutAwayVO createPutAway(PutAwayDTO putAwayDTO) {
		PutAwayVO putAwayVO = createPutAwayVOByPutAwayDTO(putAwayDTO);
		putAwayVO.setScreencode("PC");
		putAwayRepo.save(putAwayVO);

		PutAwayVO savedPutAwayVO = putAwayRepo.save(putAwayVO);
		List<PutAwayDetailsVO> putAwayDetailsVOLists = savedPutAwayVO.getPutAwayDetailsVO();
		if (putAwayDetailsVOLists != null && !putAwayDetailsVOLists.isEmpty())
			for (PutAwayDetailsVO putAwayDetailsVO : putAwayDetailsVOLists) {

				StockDetailsVO stockDetailsVO = new StockDetailsVO();
				savedPutAwayVO.setScreencode("PC");
				stockDetailsVO.setCustomer(savedPutAwayVO.getCustomer());
				stockDetailsVO.setCore(savedPutAwayVO.getCore());
				stockDetailsVO.setGrnNo(savedPutAwayVO.getGrnno());
				stockDetailsVO.setStockDate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setGrnDate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setLotNo(savedPutAwayVO.getLotno());
				stockDetailsVO.setWarehouse(savedPutAwayVO.getWarehouse());
				stockDetailsVO.setFinYear(savedPutAwayVO.getFinyr());
				stockDetailsVO.setBranch(savedPutAwayVO.getBranch());
				stockDetailsVO.setBranchCode(savedPutAwayVO.getBranchcode());
				stockDetailsVO.setRefNo(savedPutAwayVO.getDocid());
				stockDetailsVO.setRefDate(savedPutAwayVO.getDocdate());
				stockDetailsVO.setBinType(savedPutAwayVO.getLocationtype());
				stockDetailsVO.setCarrier(savedPutAwayVO.getCarrier());
				stockDetailsVO.setGrnDate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setScreenCode(savedPutAwayVO.getScreencode());
				stockDetailsVO.setInvQty(putAwayDetailsVO.getInvqty());
				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecqty());
				stockDetailsVO.setShortQty(putAwayDetailsVO.getShortqty());
				stockDetailsVO.setRecQty(putAwayDetailsVO.getRecqty());
				stockDetailsVO.setSQty(putAwayDetailsVO.getSqty());
				stockDetailsVO.setSSku(putAwayDetailsVO.getSsku());
				stockDetailsVO.setBinClass(putAwayDetailsVO.getLocationclass());
				stockDetailsVO.setWeight(putAwayDetailsVO.getWeight());
				stockDetailsVO.setBatchDate(putAwayDetailsVO.getBatchdate());
				stockDetailsVO.setPartno(putAwayDetailsVO.getPartno());
				stockDetailsVO.setPartDesc(putAwayDetailsVO.getPartdescripition());
				stockDetailsVO.setSku(putAwayDetailsVO.getSku());
				stockDetailsVO.setAmount(putAwayDetailsVO.getAmount());
				stockDetailsVO.setBatch(putAwayDetailsVO.getBatch());
				stockDetailsVO.setSsQty(putAwayDetailsVO.getSsqty());
				stockDetailsRepo.save(stockDetailsVO);
				// putaway to handlingStockIn
				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
				handlingStockInVO.setScreencode("PC");
				handlingStockInVO.setRefdate(savedPutAwayVO.getDocdate());
				handlingStockInVO.setSdocdate(savedPutAwayVO.getDocdate());
				handlingStockInVO.setStockdate(savedPutAwayVO.getDocdate());
				handlingStockInVO.setGrnno(savedPutAwayVO.getGrnno());
				handlingStockInVO.setGrndate(savedPutAwayVO.getGrndate());
				handlingStockInVO.setBranchcode(savedPutAwayVO.getBranchcode());
				handlingStockInVO.setBranch(savedPutAwayVO.getBranch());
				handlingStockInVO.setClient(savedPutAwayVO.getClient());
				handlingStockInVO.setCustomer(savedPutAwayVO.getCustomer());
				handlingStockInVO.setFinyr(savedPutAwayVO.getFinyr());
				handlingStockInVO.setRefno(savedPutAwayVO.getDocid());
				handlingStockInVO.setSdocid(savedPutAwayVO.getFinyr());
				handlingStockInVO.setWarehouse(savedPutAwayVO.getWarehouse());
				// Putaway details to handlingStockIn
				handlingStockInVO.setPartno(putAwayDetailsVO.getPartno());
				handlingStockInVO.setPartdesc(putAwayDetailsVO.getPartdescripition());
				handlingStockInVO.setSku(putAwayDetailsVO.getSku());
				handlingStockInVO.setInvqty(-putAwayDetailsVO.getInvqty());
				handlingStockInVO.setLocationtype(putAwayDetailsVO.getLocationtype());
				handlingStockInVO.setRecqty(-putAwayDetailsVO.getRecqty());
				handlingStockInVO.setSsqty(-putAwayDetailsVO.getSsqty());
				handlingStockInVO.setRpqty(-putAwayDetailsVO.getPutawayqty());
				handlingStockInVO.setRate(putAwayDetailsVO.getRate());
				handlingStockInVO.setAmount(putAwayDetailsVO.getAmount());
				handlingStockInVO.setSsku(putAwayDetailsVO.getSsku());
				handlingStockInVO.setShortqty(-putAwayDetailsVO.getShortqty());
				handlingStockInVO.setSqty(-putAwayDetailsVO.getSqty());
				handlingStockInRepo.save(handlingStockInVO);
			}
		return putAwayVO;
	}

	private PutAwayVO createPutAwayVOByPutAwayDTO(PutAwayDTO putAwayDTO) {
		List<PutAwayDetailsVO> putAwayDetailsVOList = new ArrayList<>();
		PutAwayVO putAwayVO = PutAwayVO.builder().docdate(putAwayDTO.getDocdate()).grnno(putAwayDTO.getGrnno())
				.grndate(putAwayDTO.getGrndate()).entryno(putAwayDTO.getEntryno()).core(putAwayDTO.getCore())
				.suppliershortname(putAwayDTO.getSuppliershortname()).branch(putAwayDTO.getBranch())
				.branchcode(putAwayDTO.getBranhcode()).customer(putAwayDTO.getCustomer()).client(putAwayDTO.getClient())
				.orgId(putAwayDTO.getOrgId()).createdby(putAwayDTO.getCreatedby()).updatedby(putAwayDTO.getCreatedby())
				.supplier(putAwayDTO.getSupplier()).modeodshipment(putAwayDTO.getModeodshipment())
				.carrier(putAwayDTO.getCarrier()).locationtype(putAwayDTO.getLocationtype())
				.status(putAwayDTO.getStatus()).lotno(putAwayDTO.getLotno()).warehouse(putAwayDTO.getWarehouse())
				.enteredperson(putAwayDTO.getEnteredperson()).putAwayDetailsVO(putAwayDetailsVOList).build();

		putAwayDetailsVOList = putAwayDTO.getPutAwayDetailsDTO().stream()
				.map(putaway -> PutAwayDetailsVO.builder().partno(putaway.getPartno()).batch(putaway.getBatch())
						.partdescripition(putaway.getPartdescripition()).sku(putaway.getSku())
						.invqty(putaway.getInvqty()).recqty(putaway.getRecqty()).putawayqty(putaway.getPutawayqty())
						.putawaypiecesqty(putaway.getPutawaypiecesqty()).location(putaway.getLocation())
						.weight(putaway.getWeight()).amount(putaway.getAmount()).rate(putaway.getRate())
						.remarks(putaway.getRemarks()).build())
				.collect(Collectors.toList());
		putAwayVO.setPutAwayDetailsVO(putAwayDetailsVOList);
		return putAwayVO;
	}

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

	@Override
	public Set<Object> getGRNdocid(String branch, String client, String screencode, String finyr) {
		// TODO Auto-generated method stub
		return null;
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
	public SalesReturnVO updateCreateSalesReturn(@Valid SalesReturnDTO salesReturnDTO) throws ApplicationException {
		SalesReturnVO salesReturnVO = new SalesReturnVO();
		String screenCode = "SR";
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(salesReturnDTO.getId())) {
			isUpdate = true;
			salesReturnVO = salesReturnRepo.findById(salesReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid SalesReturn details"));
			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());
		} else {
			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());
			salesReturnVO.setCreatedBy(salesReturnDTO.getCreatedBy());
//			getDocIdAPI
			String docId = salesReturnRepo.getSalesReturnDocId(salesReturnDTO.getOrgId(), salesReturnDTO.getFinYear(),
					salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(), screenCode);
			salesReturnVO.setDocId(docId);
//			Add +1 in docId
			DocumentTypeMappingDetailsVO docMapping = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(salesReturnDTO.getOrgId(),
							salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
							screenCode);
			docMapping.setLastno(docMapping.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docMapping);
		}

		List<SalesReturnDetailsVO> salesReturnDetailsVOs = new ArrayList<>();
		if (salesReturnDTO.getSalesReturnDetailsDTO() != null) {
			for (SalesReturnDetailsDTO salesReturnDetailsDTO : salesReturnDTO.getSalesReturnDetailsDTO()) {
				SalesReturnDetailsVO salesReturnDetailsVO;
				if (salesReturnDetailsDTO.getId() != null && ObjectUtils.isNotEmpty(salesReturnDetailsDTO.getId())) {
					salesReturnDetailsVO = salesReturnDetailsRepo.findById(salesReturnDetailsDTO.getId())
							.orElse(new SalesReturnDetailsVO());
				} else {
					salesReturnDetailsVO = new SalesReturnDetailsVO();
				}
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
		}

		getSalesReturnVOFromSalesReturnDTO(salesReturnDTO, salesReturnVO);
		salesReturnVO.setSalesReturnDetailsVO(salesReturnDetailsVOs);
		return salesReturnRepo.save(salesReturnVO);
	}

	private void getSalesReturnVOFromSalesReturnDTO(@Valid SalesReturnDTO salesReturnDTO, SalesReturnVO salesReturnVO) {
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
		salesReturnVO.setScreenName("SALES RETURN");
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
	public LocationMovementVO updateCreateLocationMovement(@Valid LocationMovementDTO locationMovementDTO)
			throws ApplicationException {
		String screenCode = "LM";
		LocationMovementVO locationMovementVO = new LocationMovementVO();
		boolean isUpdate = false;
		if (ObjectUtils.isNotEmpty(locationMovementDTO.getId())) {
			isUpdate = true;
			locationMovementVO = locationMovementRepo.findById(locationMovementDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid LocationMovement details"));
			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());
		} else {
			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());
			locationMovementVO.setCreatedBy(locationMovementDTO.getCreatedBy());
//			getDocIdAPI
			String docId = locationMovementRepo.getLocationMovementDocId(locationMovementDTO.getOrgId(),
					locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
					locationMovementDTO.getClient(), screenCode);
			locationMovementVO.setDocId(docId);
//			Add +1 in docId
			DocumentTypeMappingDetailsVO docMapping = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(locationMovementDTO.getOrgId(),
							locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
							locationMovementDTO.getClient(), screenCode);
			docMapping.setLastno(docMapping.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(docMapping);
		}

		List<LocationMovementDetailsVO> locationMovementDetailsVOs = new ArrayList<>();
		if (locationMovementDTO.getLocationMovementDetailsDTO() != null) {
			for (LocationMovementDetailsDTO locationMovementDetailsDTO : locationMovementDTO
					.getLocationMovementDetailsDTO()) {
				LocationMovementDetailsVO locationMovementDetailsVO;
				if (locationMovementDetailsDTO.getId() != null
						&& ObjectUtils.isNotEmpty(locationMovementDetailsDTO.getId())) {
					locationMovementDetailsVO = locationMovementDetailsRepo.findById(locationMovementDetailsDTO.getId())
							.orElse(new LocationMovementDetailsVO());
				} else {
					locationMovementDetailsVO = new LocationMovementDetailsVO();
				}
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
				locationMovementDetailsVO.setRemainingQty(locationMovementDetailsDTO.getRemainingQty());
				locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getBin());
				locationMovementDetailsVO.setGrnDate(locationMovementDetailsDTO.getGrnDate());
				locationMovementDetailsVO.setSku(locationMovementDetailsDTO.getSku());
				locationMovementDetailsVO.setLotNo(locationMovementDetailsDTO.getLotNo());
				locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getBinType());
				locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
				locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
				locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
				locationMovementDetailsVO.setStatus(locationMovementDetailsDTO.getStatus());
				locationMovementDetailsVO.setBatchDate(locationMovementDetailsDTO.getBatchDate());
				locationMovementDetailsVO.setQcFlag(locationMovementDetailsDTO.isQcFlag());
				locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);
				locationMovementDetailsVOs.add(locationMovementDetailsVO);
			}
		}

		getLocationMovementVOFromLocationMovementDTO(locationMovementDTO, locationMovementVO);
		locationMovementVO.setLocationMovementDetailsVO(locationMovementDetailsVOs);
//		save the location movement
		LocationMovementVO savedLocationMovement = locationMovementRepo.save(locationMovementVO);

		if (!isUpdate) {
			// Create StockDetails when creating a new LocationMovement
			 for (LocationMovementDetailsVO detailsVO : locationMovementDetailsVOs) {
		            // Create StockDetails for fromBin with negative quantity
		            StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
		            stockDetailsVOFrom.setBin(detailsVO.getBin());
		            stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
		            stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescripition());
		            stockDetailsVOFrom.setGrnNo(detailsVO.getGRNNo());
		            stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
		            stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
		            stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
		            stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
		            stockDetailsVOFrom.setStatus(detailsVO.getStatus());
		            stockDetailsVOFrom.setSQty(detailsVO.getFromQty()*-1); // Negative quantity
		            stockDetailsVOFrom.setRefNo(savedLocationMovement.getDocId());
		            stockDetailsVOFrom.setRefDate(savedLocationMovement.getDocDate());
		            stockDetailsVOFrom.setCreatedBy(savedLocationMovement.getUpdatedBy());
		            stockDetailsVOFrom.setBranchCode(savedLocationMovement.getBranchCode());
		            stockDetailsVOFrom.setBranch(savedLocationMovement.getBranch());
		            stockDetailsVOFrom.setClient(savedLocationMovement.getClient());
		            stockDetailsVOFrom.setWarehouse(savedLocationMovement.getWarehouse());
		            stockDetailsVOFrom.setFinYear(savedLocationMovement.getFinYear());
		            stockDetailsRepo.save(stockDetailsVOFrom);

		            // Create StockDetails for toBin with positive quantity
		            StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
		            stockDetailsVOTo.setBin(detailsVO.getToBin());
		            stockDetailsVOTo.setPartno(detailsVO.getPartNo());
		            stockDetailsVOTo.setPartDesc(detailsVO.getPartDescripition());
		            stockDetailsVOTo.setGrnNo(detailsVO.getGRNNo());
		            stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
		            stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
		            stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
		            stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
		            stockDetailsVOTo.setStatus(detailsVO.getStatus());
		            stockDetailsVOTo.setToQty(detailsVO.getToQty()); // Positive quantity
		            stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
		            stockDetailsVOTo.setRefNo(savedLocationMovement.getDocId());
		            stockDetailsVOTo.setRefDate(savedLocationMovement.getDocDate());
		            stockDetailsVOTo.setCreatedBy(savedLocationMovement.getUpdatedBy());
		            stockDetailsVOTo.setBranchCode(savedLocationMovement.getBranchCode());
		            stockDetailsVOTo.setBranch(savedLocationMovement.getBranch());
		            stockDetailsVOTo.setClient(savedLocationMovement.getClient());
		            stockDetailsVOTo.setWarehouse(savedLocationMovement.getWarehouse());
		            stockDetailsVOTo.setFinYear(savedLocationMovement.getFinYear());
		            stockDetailsRepo.save(stockDetailsVOTo);
		        }
		    }

		return locationMovementRepo.save(locationMovementVO);
	}

	private void getLocationMovementVOFromLocationMovementDTO(@Valid LocationMovementDTO locationMovementDTO,
			LocationMovementVO locationMovementVO) {
		locationMovementVO.setOrgId(locationMovementDTO.getOrgId());
		locationMovementVO.setType(locationMovementDTO.getType());
		locationMovementVO.setCustomer(locationMovementDTO.getCustomer());
		locationMovementVO.setClient(locationMovementDTO.getClient());
		locationMovementVO.setFinYear(locationMovementDTO.getFinYear());
		locationMovementVO.setBranchCode(locationMovementDTO.getBranchCode());
		locationMovementVO.setBranch(locationMovementDTO.getBranch());
		locationMovementVO.setWarehouse(locationMovementDTO.getWarehouse());
	}
}