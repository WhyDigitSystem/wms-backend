package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.entity.PutAwayDetailsVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.GrnDetailsRepo;
import com.whydigit.wms.repo.GrnRepo;
import com.whydigit.wms.repo.HandlingStockInRepo;
import com.whydigit.wms.repo.PutAwayDetailsRepo;
import com.whydigit.wms.repo.PutAwayRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class InwardTransactionServcieImpl implements InwardTransactionService {

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

	// Grn

	@Override
	public List<GrnVO> getAllGrn() {
		return grnRepo.findAll();
	}

	@Override
	public Optional<GrnVO> getGrnById(Long id) {
		return grnRepo.findById(id);
	}
	
	@Override
	public Set<Object[]> getAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer,
			String branchcode) {
		return grnRepo.findAllGatePassNumberByClientAndBranch(orgId,client,customer,branchcode);
	}
	@Override
	public GrnVO createGrn(GrnDTO grnDTO) {
		GrnVO grnVO = createGrnVOByGrnDTO(grnDTO);
		grnVO.setScreencode("GRN");
		grnRepo.save(grnVO);

		GrnVO savedGrnVO = grnRepo.save(grnVO);
		List<GrnDetailsVO> grnDetailsVOLists = savedGrnVO.getGrnDetailsVO();
		if (grnDetailsVOLists != null && !grnDetailsVOLists.isEmpty())
			for (GrnDetailsVO grnDetailsVO : grnDetailsVOLists) {

				HandlingStockInVO handlingStockInVO = new HandlingStockInVO();
				savedGrnVO.setScreencode("GRN");
				handlingStockInVO.setScreencode("GRN");

				// Set common values from savedGrnVO
				handlingStockInVO.setRefdate(savedGrnVO.getDocdate());
				handlingStockInVO.setGrnno(savedGrnVO.getDocid());
				handlingStockInVO.setGrndate(savedGrnVO.getDocdate());
				handlingStockInVO.setBranch(savedGrnVO.getBranch());
				handlingStockInVO.setOrgId(savedGrnVO.getOrgId());
				handlingStockInVO.setBranchcode(savedGrnVO.getBranchcode());
				handlingStockInVO.setCustomer(savedGrnVO.getCustomer());
				handlingStockInVO.setWarehouse(savedGrnVO.getWarehouse());
				handlingStockInVO.setClient(savedGrnVO.getClient());
				handlingStockInVO.setSdocdate(savedGrnVO.getDocdate());
				handlingStockInVO.setStockdate(savedGrnVO.getDocdate());
				handlingStockInVO.setSdocid(savedGrnVO.getDocid());
				handlingStockInVO.setFinyr(savedGrnVO.getFinyr());
				// Set values from grnDetailsVO
				handlingStockInVO.setPartno(grnDetailsVO.getPartno());
				handlingStockInVO.setPartdesc(grnDetailsVO.getPartdesc());
				handlingStockInVO.setRpqty(grnDetailsVO.getSqty());
				handlingStockInVO.setSqty(grnDetailsVO.getSqty());
				handlingStockInVO.setLocationtype(grnDetailsVO.getLocationtype());
				// Check if damageqty is 0
				if (grnDetailsVO.getDamageqty() == 0) {
					// If damageqty is 0, set sqty and ssqty
					handlingStockInVO.setSqty(grnDetailsVO.getSqty());
					handlingStockInVO.setQcflag("T");
					grnDetailsVO.setQcflag("T");
					handlingStockInRepo.save(handlingStockInVO);
				} else {
					// If damageqty is not 0, set sqty and damageqty in separate rows
					handlingStockInVO.setSqty(grnDetailsVO.getDamageqty());
					handlingStockInVO.setDamageqty(grnDetailsVO.getDamageqty());
					handlingStockInVO.setQcflag("F");
					grnDetailsVO.setQcflag("F");
					handlingStockInRepo.save(handlingStockInVO);

					// create new obj to store as second row
					HandlingStockInVO handlingStockInVO2 = new HandlingStockInVO();
					handlingStockInVO2.setScreencode("GRN");
					handlingStockInVO2.setRefdate(savedGrnVO.getDocdate());
					handlingStockInVO2.setGrnno(savedGrnVO.getDocid());
					handlingStockInVO2.setGrndate(savedGrnVO.getDocdate());
					handlingStockInVO2.setBranch(savedGrnVO.getBranch());
					handlingStockInVO2.setOrgId(savedGrnVO.getOrgId());
					handlingStockInVO2.setBranchcode(savedGrnVO.getBranchcode());
					handlingStockInVO2.setCustomer(savedGrnVO.getCustomer());
					handlingStockInVO2.setWarehouse(savedGrnVO.getWarehouse());
					handlingStockInVO2.setClient(savedGrnVO.getClient());
					handlingStockInVO2.setSdocdate(savedGrnVO.getDocdate());
					handlingStockInVO2.setStockdate(savedGrnVO.getDocdate());
					handlingStockInVO2.setSdocid(savedGrnVO.getDocid());
					handlingStockInVO2.setFinyr(savedGrnVO.getFinyr());
					if (handlingStockInVO2.getDamageqty() == 0) {
						handlingStockInVO2.setQcflag("T");
					} else {
						handlingStockInVO2.setQcflag("F");
					}
					handlingStockInVO2.setPartno(grnDetailsVO.getPartno());
					handlingStockInVO2.setPartdesc(grnDetailsVO.getPartdesc());
					handlingStockInVO2.setLocationtype(grnDetailsVO.getLocationtype());
					handlingStockInVO2.setSsku(grnDetailsVO.getSku());
					handlingStockInVO2.setInvqty(grnDetailsVO.getInvqty());
					handlingStockInVO2.setRecqty(grnDetailsVO.getRecqty());
					handlingStockInVO2.setDamageqty(0);
					handlingStockInVO2.setShortqty(grnDetailsVO.getShortqty());
					handlingStockInVO2.setPalletqty(grnDetailsVO.getPalletqty());
					handlingStockInVO2.setRate(grnDetailsVO.getRate());
					handlingStockInVO2.setAmount(grnDetailsVO.getAmount());
					handlingStockInVO2.setSqty(grnDetailsVO.getSqty());
					handlingStockInRepo.save(handlingStockInVO2);
				}

				handlingStockInVO.setInvqty(grnDetailsVO.getInvqty());
				handlingStockInVO.setRecqty(grnDetailsVO.getRecqty());
				handlingStockInVO.setShortqty(grnDetailsVO.getShortqty());
				handlingStockInVO.setPalletqty(grnDetailsVO.getPalletqty());
				handlingStockInVO.setRate(grnDetailsVO.getRate());
				handlingStockInVO.setAmount(grnDetailsVO.getAmount());
				handlingStockInVO.setSku(grnDetailsVO.getSku());
				handlingStockInVO.setSsku(grnDetailsVO.getSku());
				handlingStockInRepo.save(handlingStockInVO);
			}

		return savedGrnVO;
	}

	private GrnVO createGrnVOByGrnDTO(GrnDTO grnDTO) {
		List<GrnDetailsVO> grnDetailsVOList = new ArrayList<>();
		GrnVO grnVO = GrnVO.builder().direct(grnDTO.getDirect()).docid(grnDTO.getDocid()).docdate(grnDTO.getDocdate())
				.entryno(grnDTO.getEntryno()).entrydate(grnDTO.getEntrydate()).gatepassid(grnDTO.getGatepassid())
				.gatepassdate(grnDTO.getGatepassdate()).customerpo(grnDTO.getCustomerpo())
				.suppliershortname(grnDTO.getSuppliershortname()).supplier(grnDTO.getSupplier())
				.carrier(grnDTO.getCarrier()).lotno(grnDTO.getLotno()).modeofshipment(grnDTO.getModeofshipment())
				.orgId(grnDTO.getOrgId()).grndate(grnDTO.getGrndate()).vas(grnDTO.isVas()).cancel(grnDTO.isCancel())
				.userid(grnDTO.getUserid()).cancelremark(grnDTO.getCancelremark()).active(grnDTO.isActive())
				.branchcode(grnDTO.getBranchcode()).branch(grnDTO.getBranch()).client(grnDTO.getClient())
				.finyr(grnDTO.getFinyr()).createdby(grnDTO.getCreatedby()).updatedby(grnDTO.getCreatedby())
				.totalgrnqty(grnDTO.getTotalgrnqty()).noofpackages(grnDTO.getNoofpackages())
				.totalamount(grnDTO.getTotalamount()).warehouse(grnDTO.getWarehouse()).customer(grnDTO.getCustomer())
				.grnDetailsVO(grnDetailsVOList).build();
		grnVO.setActive(true);

		grnDetailsVOList = grnDTO.getGrnDetailsDTO().stream()
				.map(grn -> GrnDetailsVO.builder().qrcode(grn.getQrcode()).lrnohawbno(grn.getLrnohawbno())
						.invoiceno(grn.getInvoiceno()).locationtype(grn.getLocationtype()).rate(grn.getRate())
						.invoicedate(grn.getInvoicedate()).partno(grn.getPartno()).partdesc(grn.getPartdesc())
						.amount(grn.getAmount()).sku(grn.getSku()).invqty(grn.getInvqty()).recqty(grn.getRecqty())
						.shortqty(grn.getShortqty()).damageqty(grn.getDamageqty()).substockqty(grn.getSubstockqty())
						.palletqty(grn.getPalletqty()).pkgs(grn.getPkgs()).weight(grn.getWeight())
						.batchno(grn.getBatchno()).batchdt(grn.getBatchdt()).warehouse(grn.getWarehouse())
						.qcflag(grn.getQcflag()).grnqty(grn.getGrnqty()).batchpalletno(grn.getBatchpalletno())
						.expdate(grn.getExpdate()).mrp(grn.getMrp()).amount(grn.getAmount())
						.shipmentno(grn.getShipmentno()).sqty(grn.getSqty()).build())
				.collect(Collectors.toList());
		grnVO.setGrnDetailsVO(grnDetailsVOList);
		return grnVO;
	}

	@Override
	public Optional<GrnVO> updateGrn(GrnVO grnVO) {
		if (grnRepo.existsById(grnVO.getId())) {
			grnVO.setScreencode("GRN");
			grnVO.setDupchk(grnVO.getOrgId() + grnVO.getCustomer() + grnVO.getClient() + grnVO.getWarehouse()
					+ grnVO.getBranchcode() + grnVO.getEntryno());
			return Optional.of(grnRepo.save(grnVO));
		} else {
			return Optional.empty();
		}
	}

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
	public GatePassInVO createGatePassIn(GatePassInVO gatePassInVO) {
		gatePassInVO.setScreencode("GRN");
		gatePassInVO.setDupchk(gatePassInVO.getOrgId() + gatePassInVO.getCustomer() + gatePassInVO.getClient());
		return gatePassInRepo.save(gatePassInVO);
	}

	@Override
	public Optional<GatePassInVO> updateGatePassIn(GatePassInVO gatePassInVO) {
		if (gatePassInRepo.existsById(gatePassInVO.getId())) {
			gatePassInVO.setScreencode("GRN");
			gatePassInVO.setDupchk(gatePassInVO.getOrgId() + gatePassInVO.getCustomer() + gatePassInVO.getClient());
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
	public Set<Object[]> getAllPartnoByCustomer(Long orgId, String client, String customer, String cbranch) {
		return gatePassInRepo.findAllPartnoByCustomer(orgId, client, customer,cbranch);
	}

	@Override
	public Set<Object[]> getGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid, String branchcode) {
		return gatePassInRepo.findGatePassDetailsByGatePassNo(orgId,client,entryno,docid,branchcode);
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
		return putAwayRepo.findGrnNoForPutAway(orgId,client,branch,finyr,branchcode);
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
				stockDetailsVO.setScreencode("PC");
				stockDetailsVO.setCustomer(savedPutAwayVO.getCustomer());
				stockDetailsVO.setCore(savedPutAwayVO.getCore());
				stockDetailsVO.setGrnno(savedPutAwayVO.getGrnno());
				stockDetailsVO.setStockdate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setGrndate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setLotno(savedPutAwayVO.getLotno());
				stockDetailsVO.setWarehouse(savedPutAwayVO.getWarehouse());
				stockDetailsVO.setFinyear(savedPutAwayVO.getFinyear());
				stockDetailsVO.setBranch(savedPutAwayVO.getBranch());
				stockDetailsVO.setBranchcode(savedPutAwayVO.getBranchcode());
				stockDetailsVO.setRefno(savedPutAwayVO.getDocid());
				stockDetailsVO.setRefdate(savedPutAwayVO.getDocdate());
				stockDetailsVO.setLocationtype(savedPutAwayVO.getLocationtype());
				stockDetailsVO.setCarrier(savedPutAwayVO.getCarrier());
				stockDetailsVO.setGrndate(savedPutAwayVO.getGrndate());
				stockDetailsVO.setScreencode(savedPutAwayVO.getScreencode());
				stockDetailsVO.setInvqty(putAwayDetailsVO.getInvqty());
				stockDetailsVO.setRecqty(putAwayDetailsVO.getRecqty());
				stockDetailsVO.setShortqty(putAwayDetailsVO.getShortqty());
				stockDetailsVO.setRecqty(putAwayDetailsVO.getRecqty());
				stockDetailsVO.setSqty(putAwayDetailsVO.getSqty());
				stockDetailsVO.setSsku(putAwayDetailsVO.getSsku());
				stockDetailsVO.setLocationclass(putAwayDetailsVO.getLocationclass());
				stockDetailsVO.setWeight(putAwayDetailsVO.getWeight());
				stockDetailsVO.setBatchdate(putAwayDetailsVO.getBatchdate());
				stockDetailsVO.setPartno(putAwayDetailsVO.getPartno());
				stockDetailsVO.setPartdesc(putAwayDetailsVO.getPartdescripition());
				stockDetailsVO.setSku(putAwayDetailsVO.getSku());
				stockDetailsVO.setAmount(putAwayDetailsVO.getAmount());
				stockDetailsVO.setSsqty(putAwayDetailsVO.getSsqty());
				stockDetailsVO.setBatch(putAwayDetailsVO.getBatch());
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
				handlingStockInVO.setFinyr(savedPutAwayVO.getFinyear());
				handlingStockInVO.setRefno(savedPutAwayVO.getDocid());
				handlingStockInVO.setSdocid(savedPutAwayVO.getFinyear());
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





}
