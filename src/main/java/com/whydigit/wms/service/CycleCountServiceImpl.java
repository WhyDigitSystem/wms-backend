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
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.CycleCountDetailsDTO;
import com.whydigit.wms.entity.CycleCountDetailsVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CycleCountDetailsRepo;
import com.whydigit.wms.repo.CycleCountRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;

@Service
public class CycleCountServiceImpl implements CycleCountService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessServiceImpl.class);

	@Autowired
	CycleCountRepo cycleCountRepo;

	@Autowired
	CycleCountDetailsRepo cycleCountDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	// CYCLECOUNT
	@Override

	public Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException {
		CycleCountVO cycleCountVO;
		String screenCode = "CY";
		String message;

		if (ObjectUtils.isEmpty(cycleCountDTO.getId())) {

			cycleCountVO = new CycleCountVO();
			cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());

			// GETDOCID API
			String docId = cycleCountRepo.getCycleCountInDocId(cycleCountDTO.getOrgId(), cycleCountDTO.getFinYear(),
					cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), screenCode);
			cycleCountVO.setDocId(docId);

			// GETDOCID LASTNO +1
			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(cycleCountDTO.getOrgId(),
							cycleCountDTO.getFinYear(), cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

			message = "CycleCount Creation Successfully";
		} else {
			cycleCountVO = cycleCountRepo.findById(cycleCountDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Fount Any Information,Invalid Id ." + cycleCountDTO.getId()));
			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());

			List<CycleCountDetailsVO> countDetailsVOs = cycleCountDetailsRepo.findByCycleCountVO(cycleCountVO);
			cycleCountDetailsRepo.deleteAll(countDetailsVOs);
			message = "CycleCount Updation Successfully";
		}
		getCycleCountVOFromCycleCountDTO(cycleCountVO, cycleCountDTO);
		cycleCountRepo.save(cycleCountVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("cycleCountVO", cycleCountVO);
		return response;
	}

	private CycleCountVO getCycleCountVOFromCycleCountDTO(CycleCountVO cycleCountVO, CycleCountDTO cycleCountDTO) {
		cycleCountVO.setDocDate(cycleCountDTO.getDocDate());
		cycleCountVO.setOrgId(cycleCountDTO.getOrgId());
		cycleCountVO.setCustomer(cycleCountDTO.getCustomer());
		cycleCountVO.setClient(cycleCountDTO.getClient());
		cycleCountVO.setFinYear(cycleCountDTO.getFinYear());
		cycleCountVO.setBranch(cycleCountDTO.getBranch());
		cycleCountVO.setBranchCode(cycleCountDTO.getBranchCode());
		cycleCountVO.setWarehouse(cycleCountDTO.getWarehouse());
		cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
		cycleCountVO.setCancelRemarks(cycleCountDTO.getCancelRemarks());
		cycleCountVO.setFreeze(cycleCountDTO.getFreeze());
		cycleCountVO.setCycleCountNo(cycleCountDTO.getCycleCountNo());
		cycleCountVO.setCycleCountDate(cycleCountDTO.getCycleCountDate());

		List<CycleCountDetailsVO> cycleCountDetailsVOs = new ArrayList<>();
		for (CycleCountDetailsDTO details2dto : cycleCountDTO.getCycleCountDetailsDTO()) {
			CycleCountDetailsVO cycleCountDetailsVO = new CycleCountDetailsVO();
			cycleCountDetailsVO.setPartNo(details2dto.getPartNo());
			cycleCountDetailsVO.setParetDescription(details2dto.getParetDescription());
			cycleCountDetailsVO.setGrnNo(details2dto.getGrnNo());
			cycleCountDetailsVO.setSku(details2dto.getSku());
			cycleCountDetailsVO.setBinType(details2dto.getBinType());
			cycleCountDetailsVO.setBatchNo(details2dto.getBatchNo());
			cycleCountDetailsVO.setBatchDate(details2dto.getBatchDate());
			cycleCountDetailsVO.setBin(details2dto.getBin());
			cycleCountDetailsVO.setQty(details2dto.getQty());
			cycleCountDetailsVO.setActualQty(details2dto.getActualQty());

			// Avoid recursive reference to kittingVO in KittingDetails2VO
			cycleCountDetailsVO.setCycleCountVO(cycleCountVO);
			cycleCountDetailsVOs.add(cycleCountDetailsVO);
		}
		cycleCountVO.setCycleCountDetailsVO(cycleCountDetailsVOs);

		return cycleCountVO;
	}

	@Override
	public String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "CY";
		String result = cycleCountRepo.getCycleCountInDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode,
			String finYear, String warehouse) {
		return cycleCountRepo.findAllCycleCount(orgId, client, branch, branchCode, finYear, warehouse);
	}

	@Override
	public Optional<CycleCountVO> getCycleCountById(Long id) {
		return cycleCountRepo.findById(id);
	}

	@Override
	public List<Map<String, Object>> getCycleCountGridDetails(Long orgId, String branchCode, String client,
			String warehouse) {
		Set<Object[]> result = cycleCountRepo.getCycleCountGrid(orgId, branchCode, client, warehouse);
		return getCycleCount(result);
	}

	private List<Map<String, Object>> getCycleCount(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("partNo", fs[0] != null ? fs[0].toString():"");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("bin", fs[3] != null ? fs[3].toString() : "");
			part.put("batch", fs[4] != null ? fs[4].toString() : "");
			part.put("batchDate", fs[5] != null ? fs[5].toString() : "");
			part.put("lotNo", fs[6] != null ? fs[6].toString() : "");
			part.put("grnNo", fs[7] != null ? fs[7].toString() : "");
			part.put("grnDate", fs[8] != null ? fs[8].toString() : "");
			part.put("binclass", fs[9] != null ? fs[9].toString() : "");
			part.put("bintype", fs[10] != null ? fs[10].toString() : "");
			part.put("status", fs[11] != null ? fs[11].toString() : "");
			part.put("qcflag", fs[12] != null ? fs[12].toString() : "");
			part.put("stockdate", fs[13] != null ? fs[13].toString() : "");
			part.put("expdate", fs[14] != null ? fs[14].toString() : "");
			part.put("core", fs[15] != null ? fs[15].toString() : "");
			part.put("cellType", fs[16] != null ? fs[16].toString() : "");
			part.put("avlQty", fs[17] != null ? Integer.parseInt(fs[17].toString()) : 0);
			part.put("id", fs[18] != null ? Integer.parseInt(fs[18].toString()) : 0);

			details1.add(part);
		}
		return details1;

	}

}
