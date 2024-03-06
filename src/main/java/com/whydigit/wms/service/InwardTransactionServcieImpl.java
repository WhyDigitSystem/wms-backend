package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.repo.GatePassInRepo;
import com.whydigit.wms.repo.GrnRepo;

@Service
public class InwardTransactionServcieImpl implements InwardTransactionService {

	@Autowired
	GrnRepo grnRepo;

	@Autowired
	GatePassInRepo gatePassInRepo;

	// Grn

	@Override
	public List<GrnVO> getAllGrn() {
		return grnRepo.findAll();
	}

	@Override
	public Optional<GrnVO> getGrnById(Long grnid) {
		return grnRepo.findById(grnid);
	}

	@Override
	public GrnVO createGrn(GrnDTO grnDTO) {
		GrnVO grnVO = createGrnVOByGrnDTO(grnDTO);
		return grnRepo.save(grnVO);
	}

	private GrnVO createGrnVOByGrnDTO(GrnDTO grnDTO) {
		List<GrnDetailsVO> grnDetailsVOList = new ArrayList<>();
		GrnVO grnVO = GrnVO.builder().direct(grnDTO.getDirect()).docid(grnDTO.getDirect()).docdate(grnDTO.getDocdate())
				.entryno(grnDTO.getEntryno()).entrydate(grnDTO.getEntrydate()).gatepassid(grnDTO.getGatepassid())
				.gatepassdate(grnDTO.getGatepassdate()).customerpo(grnDTO.getCustomerpo())
				.suppliershortname(grnDTO.getSuppliershortname()).supplier(grnDTO.getSupplier())
				.carrier(grnDTO.getCarrier()).lotno(grnDTO.getLotno()).modeofshipment(grnDTO.getModeofshipment())
				.orgId(grnDTO.getOrgId()).cancel(grnDTO.getCancel()).userid(grnDTO.getUserid())
				.cancelremark(grnDTO.getCancelremark()).active(grnDTO.getActive()).branchcode(grnDTO.getBranchcode())
				.branch(grnDTO.getBranch()).client(grnDTO.getClient()).customer(grnDTO.getCustomer())
				.grnDetailsVO(grnDetailsVOList).build();

		grnDetailsVOList = grnDTO.getGrnDetailsDTO().stream()
				.map(grn -> GrnDetailsVO.builder().qrcode(grn.getQrcode()).lrnohawbno(grn.getLrnohawbno())
						.invoiceno(grn.getInvoiceno()).invoicedate(grn.getInvoicedate()).partno(grn.getPartno())
						.partdescription(grn.getPartdescription()).sku(grn.getSku()).invqty(grn.getInvqty())
						.recqty(grn.getRecqty()).shortqty(grn.getShortqty()).damageqty(grn.getDamageqty())
						.substockqty(grn.getSubstockqty()).pallteqty(grn.getPallteqty()).noofpallet(grn.getNoofpallet())
						.pkgs(grn.getPkgs()).weight(grn.getWeight()).batchno(grn.getBatchno()).batchdt(grn.getBatchdt())
						.qcflag(grn.getQcflag()).shipmentno(grn.getShipmentno()).sqty(grn.getSqty()).build())
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
	public void deleteGrn(Long grnid) {
		grnRepo.deleteById(grnid);
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

}
