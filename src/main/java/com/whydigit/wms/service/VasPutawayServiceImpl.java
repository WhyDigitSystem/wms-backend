package com.whydigit.wms.service;

import java.time.LocalDate;
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
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
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
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired
	MaterialRepo materialRepo;

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
		String ScreenCode = "VPC";
		String result = vasPutawayRepo.getVasPutawayDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO) throws ApplicationException {

		VasPutawayVO vasPutawayVO = new VasPutawayVO();
		String screenCode = "VPC";
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
		VasPutawayVO savedPickRequestVO = vasPutawayRepo.save(vasPutawayVO);
		List<VasPutawayDetailsVO> pickRequestDetailsVOLists = savedPickRequestVO.getVasPutawayDetailsVO();
		if (pickRequestDetailsVOLists != null && !pickRequestDetailsVOLists.isEmpty()) {
			if ("Confirm".equals(savedPickRequestVO.getStatus())) {
				for (VasPutawayDetailsVO detailsVO : pickRequestDetailsVOLists) {
					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
					stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
					stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
					stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
					stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
					stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
					stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
					stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
					stockDetailsVOFrom.setClientCode(
							clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
					stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
					stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
					stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getPutAwayQty() * -1);
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setStatus("V");
					stockDetailsVOFrom.setBinClass(detailsVO.getFromBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getFromBin());
					stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
					stockDetailsVOFrom.setPQty(detailsVO.getPutAwayQty());
					stockDetailsVOFrom.setPickedQty(detailsVO.getPutAwayQty());
					stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
					stockDetailsVOFrom.setBinType(detailsVO.getFromBinType());
					stockDetailsVOFrom.setSku(detailsVO.getSku());
					stockDetailsVOFrom.setCellType(detailsVO.getFromCellType());
					stockDetailsVOFrom.setCore(detailsVO.getFromCore());
					stockDetailsVOFrom.setSSku(detailsVO.getSku());
					stockDetailsVOFrom.setSourceScreenCode(savedPickRequestVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(savedPickRequestVO.getScreenName());
					stockDetailsVOFrom.setSourceId(detailsVO.getId());
					stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
				
				for (VasPutawayDetailsVO detailsVO : pickRequestDetailsVOLists) {
					StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
					stockDetailsVOFrom.setOrgId(savedPickRequestVO.getOrgId());
					stockDetailsVOFrom.setFinYear(savedPickRequestVO.getFinYear());
					stockDetailsVOFrom.setBranch(savedPickRequestVO.getBranch());
					stockDetailsVOFrom.setBranchCode(savedPickRequestVO.getBranchCode());
					stockDetailsVOFrom.setWarehouse(savedPickRequestVO.getWarehouse());
					stockDetailsVOFrom.setCustomer(savedPickRequestVO.getCustomer());
					stockDetailsVOFrom.setClient(savedPickRequestVO.getClient());
					stockDetailsVOFrom.setClientCode(
							clientRepo.getClientCode(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient()));
					stockDetailsVOFrom.setCreatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setRefNo(savedPickRequestVO.getDocId());
					stockDetailsVOFrom.setRefDate(savedPickRequestVO.getDocDate());
					stockDetailsVOFrom.setUpdatedBy(savedPickRequestVO.getUpdatedBy());
					stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
					stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedPickRequestVO.getOrgId(), savedPickRequestVO.getClient(), detailsVO.getPartNo()));
					stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
					stockDetailsVOFrom.setSQty(detailsVO.getPutAwayQty());
					stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
					stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
					stockDetailsVOFrom.setBinClass(detailsVO.getToBinClass());
					stockDetailsVOFrom.setBin(detailsVO.getToBin());
					stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
					if ("Defective".equals(detailsVO.getToBin())) {
						stockDetailsVOFrom.setQcFlag("F");
						stockDetailsVOFrom.setStatus("D");
					} else {
						stockDetailsVOFrom.setQcFlag("T");
						stockDetailsVOFrom.setStatus("R");
					}
					stockDetailsVOFrom.setBinType(detailsVO.getToBinType());
					stockDetailsVOFrom.setSku(detailsVO.getSku());
					stockDetailsVOFrom.setCellType(detailsVO.getToCellType());
					stockDetailsVOFrom.setCore(detailsVO.getToCore());
					stockDetailsVOFrom.setSSku(detailsVO.getSku());
					stockDetailsVOFrom.setSourceScreenCode(savedPickRequestVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(savedPickRequestVO.getScreenName());
					stockDetailsVOFrom.setSourceId(detailsVO.getId());
					stockDetailsVOFrom.setStockDate(LocalDate.now());
					stockDetailsRepo.save(stockDetailsVOFrom);
				}
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("vasPutawayVO", vasPutawayVO);
		response.put("message", message);
		return response;

	}

	private void createUpdateVasPutawayVOByVasPutawayDTO(@Valid VasPutawayDTO vasPutawayDTO,
			VasPutawayVO vasPutawayVO) throws ApplicationException {
		vasPutawayVO.setTotalGrnQty(vasPutawayDTO.getTotalGrnQty());
		vasPutawayVO.setTotalPutawayQty(vasPutawayDTO.getTotalPutawayQty());
		vasPutawayVO.setVasPickNo(vasPutawayDTO.getVasPickNo());
		vasPutawayVO.setStatus(vasPutawayDTO.getStatus());
		if ("Confirm".equals(vasPutawayDTO.getStatus())) {
			vasPutawayVO.setFreeze(true);
		}
		else
		{
			vasPutawayVO.setFreeze(false);
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
			vasPutawayDetailsVO.setPartDesc(vasPutawayDetailsDTO.getPartDesc());
			vasPutawayDetailsVO.setSku(vasPutawayDetailsDTO.getSku());
			vasPutawayDetailsVO.setGrnNo(vasPutawayDetailsDTO.getGrnNo());
			vasPutawayDetailsVO.setGrnDate(vasPutawayDetailsDTO.getGrnDate());
			vasPutawayDetailsVO.setBatchNo(vasPutawayDetailsDTO.getBatchNo());
			vasPutawayDetailsVO.setBatchDate(vasPutawayDetailsDTO.getBatchDate());
			vasPutawayDetailsVO.setPartDesc(vasPutawayDetailsDTO.getPartDesc());
			
			
			int getFromQty= stockDetailsRepo.getAvlQtyforVasPutaway(vasPutawayDTO.getOrgId(), vasPutawayDTO.getBranchCode(),vasPutawayDTO.getWarehouse(),
					vasPutawayDTO.getClient(),vasPutawayDetailsDTO.getFromBin(),vasPutawayDetailsDTO.getPartNo(),vasPutawayDetailsDTO.getGrnNo(),
					vasPutawayDetailsDTO.getBatchNo());
		    if(getFromQty>=vasPutawayDetailsDTO.getPutAwayQty())
		    {
		    	vasPutawayDetailsVO.setInvQty(vasPutawayDetailsDTO.getInvQty());
		    	vasPutawayDetailsVO.setPutAwayQty(vasPutawayDetailsDTO.getPutAwayQty());
		    }
		    else
		    {
		    	throw new ApplicationException("Putaway Qty is Must Below or Equal to InvQty Qty");
		    }
			vasPutawayDetailsVO.setInvQty(vasPutawayDetailsDTO.getInvQty());
			vasPutawayDetailsVO.setPutAwayQty(vasPutawayDetailsDTO.getPutAwayQty());
			vasPutawayDetailsVO.setFromBin(vasPutawayDetailsDTO.getFromBin());
			vasPutawayDetailsVO.setFromBinType(vasPutawayDetailsDTO.getFromBinType());
			vasPutawayDetailsVO.setRemarks(vasPutawayDetailsDTO.getRemarks());
			vasPutawayDetailsVO.setFromBinClass(vasPutawayDetailsDTO.getFromBinClass());
			vasPutawayDetailsVO.setFromCellType(vasPutawayDetailsDTO.getFromCellType());
			vasPutawayDetailsVO.setFromCore(vasPutawayDetailsDTO.getFromCore());
			vasPutawayDetailsVO.setExpDate(vasPutawayDetailsDTO.getExpDate());
			vasPutawayDetailsVO.setStockDate(vasPutawayDetailsDTO.getStockDate());
			vasPutawayDetailsVO.setToBin(vasPutawayDetailsDTO.getToBin());
			vasPutawayDetailsVO.setToBinType(vasPutawayDetailsDTO.getToBinType());
			vasPutawayDetailsVO.setToBinClass(vasPutawayDetailsDTO.getToBinClass());
			vasPutawayDetailsVO.setToCellType(vasPutawayDetailsDTO.getToCellType());
			vasPutawayDetailsVO.setToCore(vasPutawayDetailsDTO.getToCore());
			vasPutawayDetailsVO.setQcFlag(vasPutawayDetailsDTO.getQcFlag());
			vasPutawayDetailsVO.setVasPutawayVO(vasPutawayVO);
			vasPutawayDetailsVOs.add(vasPutawayDetailsVO);
		}
		vasPutawayVO.setVasPutawayDetailsVO(vasPutawayDetailsVOs);

	}

	@Transactional
	public List<Map<String, Object>> getDocIdFromVasPickForVasPutaway(Long orgId, String branch, String client,String finYear) {

		Set<Object[]> result = vasPutawayRepo.findDocIdFromVasPickForVasPutaway(orgId, branch, client, finYear);
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
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("grnNo", fs[3] != null ? fs[3].toString() : "");
			part.put("grnDate", fs[4] != null ? fs[4].toString() : "");
			part.put("batchNo", fs[5] != null ? fs[5].toString() : "");
			part.put("batchDate", fs[6] != null ? fs[6].toString() : "");
			part.put("expDate", fs[7] != null ? fs[7].toString() : "");
			part.put("stockDate", fs[8] != null ? fs[8].toString() : "");
			part.put("bin", fs[9] != null ? fs[9].toString() : "");
			part.put("binType", fs[10] != null ? fs[10].toString() : "");
			part.put("binClass", fs[11] != null ? fs[11].toString() : "");
			part.put("core", fs[12] != null ? fs[12].toString() : "");
			part.put("cellType", fs[13] != null ? fs[13].toString() : "");
			part.put("qcFlag", fs[14] != null ? fs[14].toString() : "");
			part.put("pickQty", fs[15] != null ? Integer.parseInt(fs[15].toString()) : 0);
			part.put("putawayQty", fs[15] != null ? Integer.parseInt(fs[15].toString()) : 0);
			part.put("id",fs[16]!=null ? Integer.parseInt(fs[16].toString()):0);
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getToBinDetailsVasPutaway(Long orgId, String branchCode,
			String client,String warehouse) {

		Set<Object[]> result = vasPutawayDetailsRepo.getToBinDetailsVasPutaway(orgId, branchCode, client,warehouse);
		return ToBinDetailsVasPutaway(result);
	}

	private List<Map<String, Object>> ToBinDetailsVasPutaway(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("cellType", fs[2] != null ? fs[2].toString() : "");
			part.put("core", fs[3] != null ? fs[3].toString() : "");
			part.put("binType", fs[4] != null ? fs[4].toString() : "");

			details1.add(part);
		}
		return details1;
	}

}
