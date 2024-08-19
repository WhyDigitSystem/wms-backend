package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
				detailsVO.setGatePassInVO(gatePassInVO);
				detailsVOList.add(detailsVO);
			}

			gatePassInVO.setGatePassDetailsVO(detailsVOList);
			return gatePassInVO;

		}

}
