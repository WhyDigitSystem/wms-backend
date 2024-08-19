package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.dto.VasPutawayDetailsDTO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.VasPutawayDetailsRepo;
import com.whydigit.wms.repo.VasPutawayRepo;

@Service
public class VasPutawayServiceImpl implements VasPutawayService {

	public static final Logger LOGGER = LoggerFactory.getLogger(VasPutawayServiceImpl.class);

	@Autowired
	VasPutawayRepo vasPutawayRepo;

	@Autowired
	VasPutawayDetailsRepo vasPutawayDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	// VASPutaway

	@Override
	public List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return vasPutawayRepo.findAllVasPutaway(orgId, finYear, branch, branchCode, client, warehouse);

	}

	@Override
	public VasPutawayVO getVasPutawayById(Long id) {
		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  VasPutaway BY Id : {}", id);
			vasPutawayVO = vasPutawayRepo.findDeliveryChallanById(id);
		} else {
			LOGGER.info("failed Received  VasPutaway BY Id : {}", id);

		}
		return vasPutawayVO;
	}

	@Override
	@Transactional
	public String getVasPutawayDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "VPW";
		String result = vasPutawayRepo.getVasPutawayDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO) throws ApplicationException {

		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		String screenCode = "VPW";
		String message;

		if (ObjectUtils.isNotEmpty(vasPutawayDTO.getId())) {
			vasPutawayVO = vasPutawayRepo.findById(vasPutawayDTO.getId())
					.orElseThrow(() -> new ApplicationException("Invalid DeliveryChallan details"));
			createUpdateVasPutawayVOByVasPutawayDTO(vasPutawayDTO, vasPutawayVO);
			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
			message = "DeliveryChallan updated Successfully";

		} else {
			vasPutawayVO.setUpdatedBy(vasPutawayDTO.getCreatedBy());
			vasPutawayVO.setCreatedBy(vasPutawayDTO.getCreatedBy());
//				GETDOCID API
			String docId = vasPutawayRepo.getVasPutawayDocId(vasPutawayDTO.getOrgId(), vasPutawayDTO.getFinYear(),
					vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(), screenCode);
			vasPutawayVO.setDocId(docId);

			createUpdateVasPutawayVOByVasPutawayDTO(vasPutawayDTO, vasPutawayVO);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(vasPutawayDTO.getOrgId(),
							vasPutawayDTO.getFinYear(), vasPutawayDTO.getBranchCode(), vasPutawayDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "VasPutaway updated Successfully";

		}
		VasPutawayVO savedVasPutawayVO = vasPutawayRepo.save(vasPutawayVO);

		List<VasPutawayDetailsVO> vasPutawayVOLists = savedVasPutawayVO.getVasPutawayDetailsVO();
		if (vasPutawayVOLists != null && !vasPutawayVOLists.isEmpty()) {
			for (VasPutawayDetailsVO vasPutawayDetailsVO : vasPutawayVOLists) {
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();

				stockDetailsVOFrom.setRefNo(vasPutawayVO.getDocId());
				stockDetailsVOFrom.setRefDate(vasPutawayVO.getDocDate());
				stockDetailsVOFrom.setDocId(vasPutawayVO.getVasPickNo());
				stockDetailsVOFrom.setStatus(vasPutawayVO.getStatus());
				stockDetailsVOFrom.setOrgId(vasPutawayVO.getOrgId());
				stockDetailsVOFrom.setCustomer(vasPutawayVO.getCustomer());
				stockDetailsVOFrom.setClient(vasPutawayVO.getClient());
				stockDetailsVOFrom.setCreatedBy(vasPutawayVO.getUpdatedBy());
				stockDetailsVOFrom.setFinYear(vasPutawayVO.getFinYear());
				stockDetailsVOFrom.setBranch(vasPutawayVO.getBranch());
				stockDetailsVOFrom.setBranchCode(vasPutawayVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(vasPutawayVO.getWarehouse());
				stockDetailsVOFrom.setSourceScreenCode(vasPutawayVO.getScreenCode());
				stockDetailsVOFrom.setSourceScreenName(vasPutawayVO.getScreenName());
				stockDetailsVOFrom.setSourceId(vasPutawayVO.getId());
				stockDetailsVOFrom.setBinClass(vasPutawayDetailsVO.getBinClass());
				stockDetailsVOFrom.setCellType(vasPutawayDetailsVO.getCellType());
				stockDetailsVOFrom.setClientCode(vasPutawayDetailsVO.getClientCode());
				stockDetailsVOFrom.setCore(vasPutawayDetailsVO.getCore());
				stockDetailsVOFrom.setStatus(vasPutawayDetailsVO.getStatus());
				stockDetailsVOFrom.setExpDate(vasPutawayDetailsVO.getExpDate());
				stockDetailsVOFrom.setPcKey(vasPutawayDetailsVO.getPckey());
				stockDetailsVOFrom.setSSku(vasPutawayDetailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(vasPutawayDetailsVO.getStockDate());
				stockDetailsVOFrom.setPartno(vasPutawayDetailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(vasPutawayDetailsVO.getPartDescription());
				stockDetailsVOFrom.setGrnNo(vasPutawayDetailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(vasPutawayDetailsVO.getGrnDate());
				stockDetailsVOFrom.setInvQty(vasPutawayDetailsVO.getInvQty());
				stockDetailsVOFrom.setSsQty(vasPutawayDetailsVO.getPutAwayQty() * -1); // NEGATIVE QUANTITY
				stockDetailsVOFrom.setBin(vasPutawayDetailsVO.getBin());
				stockDetailsVOFrom.setBin(vasPutawayDetailsVO.getFromBin());
				stockDetailsVOFrom.setSku(vasPutawayDetailsVO.getSku());
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
//					

				stockDetailsVOTo.setRefNo(vasPutawayVO.getDocId());
				stockDetailsVOTo.setRefDate(vasPutawayVO.getDocDate());
				stockDetailsVOTo.setDocId(vasPutawayVO.getVasPickNo());
				stockDetailsVOTo.setStatus(vasPutawayVO.getStatus());
				stockDetailsVOTo.setOrgId(vasPutawayVO.getOrgId());
				stockDetailsVOTo.setCustomer(vasPutawayVO.getCustomer());
				stockDetailsVOTo.setClient(vasPutawayVO.getClient());
				stockDetailsVOTo.setCreatedBy(vasPutawayVO.getUpdatedBy());
				stockDetailsVOTo.setFinYear(vasPutawayVO.getFinYear());
				stockDetailsVOTo.setBranch(vasPutawayVO.getBranch());
				stockDetailsVOTo.setBranchCode(vasPutawayVO.getBranchCode());
				stockDetailsVOTo.setWarehouse(vasPutawayVO.getWarehouse());
				stockDetailsVOTo.setSourceScreenCode(vasPutawayVO.getScreenCode());
				stockDetailsVOTo.setSourceScreenName(vasPutawayVO.getScreenName());
				stockDetailsVOTo.setSourceId(vasPutawayVO.getId());
				stockDetailsVOTo.setBinClass(vasPutawayDetailsVO.getBinClass());
				stockDetailsVOTo.setCellType(vasPutawayDetailsVO.getCellType());
				stockDetailsVOTo.setClientCode(vasPutawayDetailsVO.getClientCode());
				stockDetailsVOTo.setCore(vasPutawayDetailsVO.getCore());
				stockDetailsVOTo.setExpDate(vasPutawayDetailsVO.getExpDate());
				stockDetailsVOTo.setPcKey(vasPutawayDetailsVO.getPckey());
				stockDetailsVOTo.setSSku(vasPutawayDetailsVO.getSsku());
				stockDetailsVOTo.setStatus(vasPutawayDetailsVO.getStatus());
				stockDetailsVOTo.setStockDate(vasPutawayDetailsVO.getStockDate());
				stockDetailsVOTo.setPartno(vasPutawayDetailsVO.getPartNo());
				stockDetailsVOTo.setPartDesc(vasPutawayDetailsVO.getPartDescription());
				stockDetailsVOTo.setGrnNo(vasPutawayDetailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(vasPutawayDetailsVO.getGrnDate());
				stockDetailsVOTo.setInvQty(vasPutawayDetailsVO.getInvQty());
				stockDetailsVOTo.setSsQty(vasPutawayDetailsVO.getPutAwayQty()); // POSITIVE QUANTITY
				stockDetailsVOTo.setBin(vasPutawayDetailsVO.getBin());
				stockDetailsVOTo.setBin(vasPutawayDetailsVO.getFromBin());
				stockDetailsVOTo.setSku(vasPutawayDetailsVO.getSku());
				stockDetailsRepo.save(stockDetailsVOTo);

			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("vasPutawayVO", vasPutawayVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateVasPutawayVOByVasPutawayDTO(@Valid VasPutawayDTO vasPutawayDTO,
			VasPutawayVO vasPutawayVO) {
		vasPutawayVO.setTotalGrnQty(vasPutawayDTO.getTotalGrnQty());
		vasPutawayVO.setTotalPutawayQty(vasPutawayDTO.getTotalPutawayQty());
		vasPutawayVO.setVasPickNo(vasPutawayDTO.getVasPickNo());
		vasPutawayVO.setStatus(vasPutawayDTO.getStatus());
		if (vasPutawayDTO.getStatus() == "Submit") {
			vasPutawayVO.setFreeze(true);
		}
		vasPutawayVO.setOrgId(vasPutawayDTO.getOrgId());
		vasPutawayVO.setCustomer(vasPutawayDTO.getCustomer());
		vasPutawayVO.setClient(vasPutawayDTO.getClient());
		vasPutawayVO.setFinYear(vasPutawayDTO.getFinYear());
		vasPutawayVO.setBranch(vasPutawayDTO.getBranch());
		vasPutawayVO.setBranchCode(vasPutawayDTO.getBranchCode());
		vasPutawayVO.setWarehouse(vasPutawayDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(vasPutawayVO.getId())) {
			List<VasPutawayDetailsVO> vasPutawayDetailsVO1 = vasPutawayDetailsRepo.findByVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsRepo.deleteAll(vasPutawayDetailsVO1);
		}

		List<VasPutawayDetailsVO> vasPutawayDetailsVOs = new ArrayList<>();
		for (VasPutawayDetailsDTO vasPutawayDetailsDTO : vasPutawayDTO.getVasPutawayDetailsDTO()) {
			VasPutawayDetailsVO vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO = new VasPutawayDetailsVO();
			vasPutawayDetailsVO.setPartNo(vasPutawayDetailsDTO.getPartNo());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setGrnNo(vasPutawayDetailsDTO.getGrnNo());
			vasPutawayDetailsVO.setGrnDate(vasPutawayDetailsDTO.getGrnDate());
			vasPutawayDetailsVO.setPartDescription(vasPutawayDetailsDTO.getPartDescription());
			vasPutawayDetailsVO.setInvQty(vasPutawayDetailsDTO.getInvQty());
			vasPutawayDetailsVO.setPutAwayQty(vasPutawayDetailsDTO.getPutAwayQty());
			vasPutawayDetailsVO.setFromBin(vasPutawayDetailsDTO.getFromBin());
			vasPutawayDetailsVO.setBin(vasPutawayDetailsDTO.getBin());
			vasPutawayDetailsVO.setSku(vasPutawayDetailsDTO.getSku());
			vasPutawayDetailsVO.setRemarks(vasPutawayDetailsDTO.getRemarks());
//				vasPutawayDetailsVO.setQcFlag(vasPutawayDetailsDTO.getQcFlag());
			vasPutawayDetailsVO.setBinClass(vasPutawayDetailsDTO.getBinClass());
			vasPutawayDetailsVO.setCellType(vasPutawayDetailsDTO.getCellType());
			vasPutawayDetailsVO.setClientCode(vasPutawayDetailsDTO.getClientCode());
//				vasPutawayDetailsVO.setStatus(vasPutawayDetailsDTO.getStatus());
			vasPutawayDetailsVO.setCore(vasPutawayDetailsDTO.getCore());
			vasPutawayDetailsVO.setExpDate(vasPutawayDetailsDTO.getExpDate());
			vasPutawayDetailsVO.setPckey(vasPutawayDetailsDTO.getPckey());
			vasPutawayDetailsVO.setSsku(vasPutawayDetailsDTO.getSsku());
			vasPutawayDetailsVO.setStockDate(vasPutawayDetailsDTO.getStockDate());

			if ("Defective".equals(vasPutawayDetailsVO.getBin())) {
				vasPutawayDetailsVO.setQcFlag("F");
				vasPutawayDetailsVO.setStatus("D");
			} else {
				vasPutawayDetailsVO.setQcFlag("T");
				vasPutawayDetailsVO.setStatus("R");
			}
			vasPutawayDetailsVO.setVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsVOs.add(vasPutawayDetailsVO);
		}
		vasPutawayVO.setVasPutawayDetailsVO(vasPutawayDetailsVOs);

	}

	@Transactional
	public List<Map<String, Object>> getDocIdFromVasPickForVasPutaway(Long orgId, String branch, String client) {

		Set<Object[]> result = vasPutawayRepo.findDocIdFromVasPickForVasPutaway(orgId, branch, client);
		return getVasPutaway(result);
	}

	private List<Map<String, Object>> getVasPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("vasPickNo", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

//	@Transactional
//	public List<Map<String, Object>> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch,
//			String client, String docId) {
//
//		Set<Object[]> result = vasPutawayDetailsRepo.getAllDetailsFromVasPickDetailsForVasPutawayDetails(orgId, branch,
//				client, docId);
//		return getVasPutawayDetails(result);
//	}
//
//	private List<Map<String, Object>> getVasPutawayDetails(Set<Object[]> result) {
//		List<Map<String, Object>> details1 = new ArrayList<>();
//		for (Object[] fs : result) {
//			Map<String, Object> part = new HashMap<>();
//			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
//			part.put("partDescription", fs[1] != null ? fs[1].toString() : "");
//			part.put("grnNo ", fs[2] != null ? fs[2].toString() : "");
//			part.put("pickOty", fs[3] != null ? fs[3].toString() : "");
//			part.put("bin", fs[4] != null ? fs[4].toString() : "");
//			part.put("sku", fs[5] != null ? fs[5].toString() : "");
//
//			details1.add(part);
//		}
//		return details1;
//	}

	@Override
	@Transactional
	public List<Map<String, Object>> getAllFillGridFromVasPutaway(Long orgId, String branch, String branchCode,
			String client,String docId) {

		Set<Object[]> result = vasPutawayDetailsRepo.getAllFillGridFromVasPutaway(orgId, branch, branchCode, client,docId);
		return getAllFillGridVasPutawayResult(result);
	}

	private List<Map<String, Object>> getAllFillGridVasPutawayResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("cellType", fs[2] != null ? fs[2].toString() : "");
			part.put("clientCode", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("expDate", fs[5] != null ? fs[5].toString() : "");
			part.put("pcKey", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockDate", fs[8] != null ? fs[8].toString() : "");
			part.put("partNo", fs[9] != null ? fs[9].toString() : "");
			part.put("partDesc", fs[10] != null ? fs[10].toString() : "");
			part.put("sku", fs[11] != null ? fs[11].toString() : "");
			part.put("grnNo", fs[12] != null ? fs[12].toString() : "");
			part.put("grnDate", fs[13] != null ? fs[13].toString() : "");
			part.put("picqty", fs[14] != null ? fs[14].toString() : "");
			part.put("avlqty", fs[15] != null ? fs[15].toString() : "");

			details1.add(part);
		}
		return details1;
	}


}
