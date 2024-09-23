package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			Set<Object[]>getEnrty=gatePassInRepo.getEntryNoDetails(orgId, finYear, branchCode, client,entryNo);
			return entryDetails(getEnrty);
		}

		private List<Map<String, Object>> entryDetails(Set<Object[]> getEnrty) {
			List<Map<String,Object>>details= new ArrayList<>();
			for(Object[] detail:getEnrty)
			{
				Map<String, Object>mp= new HashMap<>();
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
			Set<Object[]>getEnrtyFillDetails=gatePassInRepo.getEntryNoFillDetails(orgId, finYear, branchCode, client,entryNo);
			return entryFillDetails(getEnrtyFillDetails);
		}

		private List<Map<String, Object>> entryFillDetails(Set<Object[]> getEnrtyFillDetails) {
			List<Map<String,Object>>details= new ArrayList<>();
			for(Object[] detail:getEnrtyFillDetails)
			{
				Map<String, Object>mp= new HashMap<>();
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

				details.add(mp);
			}
			return details;
		}

}
