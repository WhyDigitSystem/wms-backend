package com.whydigit.wms.service;

import java.time.LocalDate;
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
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CycleCountDetailsRepo;
import com.whydigit.wms.repo.CycleCountRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class CycleCountServiceImpl implements CycleCountService {
 
	public static final Logger LOGGER = LoggerFactory.getLogger(CycleCountServiceImpl.class);

	@Autowired 
	CycleCountRepo cycleCountRepo;

	@Autowired
	CycleCountDetailsRepo cycleCountDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	ClientRepo clientRepo;
	
	@Autowired 
	MaterialRepo materialRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
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
	        String docId = cycleCountRepo.getCycleCountInDocId(
	            cycleCountDTO.getOrgId(),
	            cycleCountDTO.getFinYear(),
	            cycleCountDTO.getBranchCode(),
	            cycleCountDTO.getClient(),
	            screenCode
	        );
	        cycleCountVO.setDocId(docId);

	        // GETDOCID LASTNO +1
	        DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(
	            cycleCountDTO.getOrgId(),
	            cycleCountDTO.getFinYear(),
	            cycleCountDTO.getBranchCode(),
	            cycleCountDTO.getClient(),
	            screenCode
	        );
	        documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
	        documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);

	        message = "CycleCount Creation Successfully";
	    } else {
	        cycleCountVO = cycleCountRepo.findById(cycleCountDTO.getId())
	            .orElseThrow(() -> new ApplicationException(
	                "This Id Is Not Found Any Information, Invalid Id: " + cycleCountDTO.getId()
	            ));
	        cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());

	        List<CycleCountDetailsVO> countDetailsVOs = cycleCountDetailsRepo.findByCycleCountVO(cycleCountVO);
	        cycleCountDetailsRepo.deleteAll(countDetailsVOs);
	        message = "CycleCount Updation Successfully";
	    }

	    getCycleCountVOFromCycleCountDTO(cycleCountVO, cycleCountDTO);
	    CycleCountVO savedCycleCount = cycleCountRepo.save(cycleCountVO);

	    List<CycleCountDetailsVO> cycleCountDetailsVOLists = savedCycleCount.getCycleCountDetailsVO();
	    if (cycleCountDetailsVOLists != null && !cycleCountDetailsVOLists.isEmpty()) {
	        for (CycleCountDetailsVO detailsVO : cycleCountDetailsVOLists) {
	            StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
	            stockDetailsVOFrom.setOrgId(savedCycleCount.getOrgId());
	            stockDetailsVOFrom.setFinYear(savedCycleCount.getFinYear());
	            stockDetailsVOFrom.setBranch(savedCycleCount.getBranch());
	            stockDetailsVOFrom.setBranchCode(savedCycleCount.getBranchCode());
	            stockDetailsVOFrom.setWarehouse(savedCycleCount.getWarehouse());
	            stockDetailsVOFrom.setCustomer(savedCycleCount.getCustomer());
	            stockDetailsVOFrom.setClient(savedCycleCount.getClient());
	            stockDetailsVOFrom.setClientCode(
	                clientRepo.getClientCode(savedCycleCount.getOrgId(), savedCycleCount.getClient())
	            );
	            stockDetailsVOFrom.setCreatedBy(savedCycleCount.getUpdatedBy());
	            stockDetailsVOFrom.setRefNo(savedCycleCount.getDocId());
	            stockDetailsVOFrom.setRefDate(savedCycleCount.getDocDate());
	            stockDetailsVOFrom.setUpdatedBy(savedCycleCount.getUpdatedBy());
	            stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
	            stockDetailsVOFrom.setPcKey(
	                materialRepo.getParentChildKey(savedCycleCount.getOrgId(), savedCycleCount.getClient(), detailsVO.getPartNo())
	            );
	            stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescription());
	            
	            if(detailsVO.getAvlQty()<detailsVO.getActualQty()) {
	            	
	            	 stockDetailsVOFrom.setSQty(1*-detailsVO.getAvlQty() + detailsVO.getActualQty());
	            	 
	            }
	            if(detailsVO.getAvlQty() > detailsVO.getActualQty()) {
	            	
	            	 stockDetailsVOFrom.setSQty(-detailsVO.getAvlQty() + detailsVO.getActualQty());
	            	 
	            }
	            stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
	            stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
	            stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
	            stockDetailsVOFrom.setStatus(detailsVO.getStatus());
	            stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
	            stockDetailsVOFrom.setBin(detailsVO.getBin());
	            stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
	            stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
	            stockDetailsVOFrom.setQcFlag(detailsVO.getQcFlag());
	            stockDetailsVOFrom.setBinType(detailsVO.getBinType());
	            stockDetailsVOFrom.setSku(detailsVO.getSku());
	            stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
	            stockDetailsVOFrom.setCellType(detailsVO.getCellType());
	            stockDetailsVOFrom.setCore(detailsVO.getCore());
	            stockDetailsVOFrom.setSSku(detailsVO.getSku());
	            stockDetailsVOFrom.setSourceScreenCode(savedCycleCount.getScreenCode());
	            stockDetailsVOFrom.setSourceScreenName(savedCycleCount.getScreenName());
	            stockDetailsVOFrom.setSourceId(detailsVO.getId());
	            stockDetailsRepo.save(stockDetailsVOFrom);
	        }
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("message", message);
	    response.put("cycleCountVO", cycleCountVO);
	    return response;
	}

	private CycleCountVO getCycleCountVOFromCycleCountDTO(CycleCountVO cycleCountVO, CycleCountDTO cycleCountDTO) {
	    cycleCountVO.setOrgId(cycleCountDTO.getOrgId());
	    cycleCountVO.setCustomer(cycleCountDTO.getCustomer());
	    cycleCountVO.setClient(cycleCountDTO.getClient());
	    cycleCountVO.setFinYear(cycleCountDTO.getFinYear());
	    cycleCountVO.setBranch(cycleCountDTO.getBranch());
	    cycleCountVO.setBranchCode(cycleCountDTO.getBranchCode());
	    cycleCountVO.setWarehouse(cycleCountDTO.getWarehouse());
	    cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
	    cycleCountVO.setStatusFlag(cycleCountDTO.getStatusFlag());
	    cycleCountVO.setStockStatus(cycleCountDTO.getStockStatus());

	    List<CycleCountDetailsVO> cycleCountDetailsVOs = new ArrayList<>();
	    for (CycleCountDetailsDTO details2dto : cycleCountDTO.getCycleCountDetailsDTO()) {
	        CycleCountDetailsVO cycleCountDetailsVO = new CycleCountDetailsVO();
	        cycleCountDetailsVO.setPartNo(details2dto.getPartNo());
	        cycleCountDetailsVO.setPartDescription(details2dto.getPartDescription());
	        cycleCountDetailsVO.setGrnNo(details2dto.getGrnNo());
	        cycleCountDetailsVO.setSku(details2dto.getSku());
	        cycleCountDetailsVO.setBinType(details2dto.getBinType());
	        cycleCountDetailsVO.setBatchNo(details2dto.getBatchNo());
	        cycleCountDetailsVO.setBatchDate(details2dto.getBatchDate());
	        cycleCountDetailsVO.setBin(details2dto.getBin());
	        
	        int avlqty=cycleCountRepo.getAvailQty(cycleCountDTO.getOrgId(), cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), cycleCountDTO.getWarehouse(),
	        		details2dto.getPartNo(), details2dto.getGrnNo(), details2dto.getBatchNo(), details2dto.getBin(),cycleCountDTO.getStatusFlag());
	       	cycleCountDetailsVO.setAvlQty(details2dto.getAvlQty());
		    cycleCountDetailsVO.setActualQty(details2dto.getActualQty());
	       	cycleCountDetailsVO.setAvlQty(details2dto.getAvlQty());
		    cycleCountDetailsVO.setActualQty(details2dto.getActualQty());
	        cycleCountDetailsVO.setGrnDate(details2dto.getGrnDate());
	        cycleCountDetailsVO.setExpDate(details2dto.getExpDate());
	        cycleCountDetailsVO.setBinClass(details2dto.getBinClass());
	        cycleCountDetailsVO.setCellType(details2dto.getCellType());
	        cycleCountDetailsVO.setCore(details2dto.getCore());
	        cycleCountDetailsVO.setStatus(cycleCountDTO.getStatusFlag());
	        cycleCountDetailsVO.setQcFlag(details2dto.getQcFlag());

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
			String warehouse,String status) {
		Set<Object[]> result = cycleCountRepo.getCycleCountGrid(orgId, branchCode, client, warehouse,status);
		return getCycleCount(result);
	}

	private List<Map<String, Object>> getCycleCount(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("bin", fs[3] != null ? fs[3].toString() : "");
			part.put("batch", fs[4] != null ? fs[4].toString() : "");
			part.put("batchDate", fs[5] != null ? fs[5].toString() : "");
			part.put("grnNo", fs[6] != null ? fs[6].toString() : "");
			part.put("grnDate", fs[7] != null ? fs[7].toString() : "");
			part.put("binclass", fs[8] != null ? fs[8].toString() : "");
			part.put("bintype", fs[9] != null ? fs[9].toString() : "");
			part.put("status", fs[10] != null ? fs[10].toString() : "");
			part.put("qcflag", fs[11] != null ? fs[11].toString() : "");
			part.put("expdate", fs[12] != null ? fs[12].toString() : "");
			part.put("core", fs[13] != null ? fs[13].toString() : "");
			part.put("cellType", fs[14] != null ? fs[14].toString() : "");
			part.put("avlQty", fs[15] != null ? Integer.parseInt(fs[15].toString()) : 0);
			part.put("id", fs[16] != null ? Integer.parseInt(fs[16].toString()) : 0);

			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getPartNoByCycleCount(Long orgId, String branchCode, String client,
			String warehouse,String status) {
		Set<Object[]> result = cycleCountRepo.getPartNoByCycleCount(orgId, branchCode, client, warehouse,status);
		return getPart(result);
	}

	private List<Map<String, Object>> getPart(Set<Object[]> result) {
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

	@Override
	public List<Map<String, Object>> getGrnNoByCycleCount(Long orgId, String branchCode, String client,
			String warehouse, String partNo,String status) {
		Set<Object[]> result = cycleCountRepo.getGrnNo(orgId, branchCode, client, warehouse, partNo,status);
		return getGrn(result);
	}

	private List<Map<String, Object>> getGrn(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");

			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getBatchByCycleCount(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNO,String status) {
		Set<Object[]> result = cycleCountRepo.getBatch(orgId, branchCode, client, warehouse, partNo, grnNO,status);
		return getBatchDetails(result);
	}

	private List<Map<String, Object>> getBatchDetails(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("batch", fs[0] != null ? fs[0].toString() : "");
			part.put("batchDate", fs[1] != null ? fs[1].toString() : "");
			part.put("expDate", fs[2] != null ? fs[2].toString() : "");

			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getBinDetailsByCycleCount(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNO, String batch,String status) {
		Set<Object[]> result = cycleCountRepo.getBinDetails(orgId, branchCode, client, warehouse, partNo, grnNO, batch,status);
		return getBin(result);
	}

	private List<Map<String, Object>> getBin(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binType", fs[1] != null ? fs[1].toString() : "");
			part.put("cellType", fs[2] != null ? fs[2].toString() : "");
			part.put("binClass", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("qcFlag",fs[5] != null ? fs[5].toString() : "");

			details1.add(part);
		}
		return details1;

	}

	@Override
	public List<Map<String, Object>> getAvlQtyByCycleCount(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNO, String batch, String bin,String status) {
		Set<Object[]> result = cycleCountRepo.getAvlQty(orgId, branchCode, client, warehouse, partNo, grnNO, batch,
				bin,status);
		return getAvlQty1(result);
	}

	private List<Map<String, Object>> getAvlQty1(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();

			part.put("avlQty", fs[0] != null ? Integer.parseInt(fs[0].toString()) : 0);

			details1.add(part);
		}
		return details1;

	}
}
