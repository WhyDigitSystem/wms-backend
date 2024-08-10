package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.CodeConversionDetailsDTO;
import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CodeConversionDetailsRepo;
import com.whydigit.wms.repo.CodeConversionRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class StockProcessServiceImpl implements StockProcessService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessServiceImpl.class);

	@Autowired
	CodeConversionRepo codeConcersionRepo;

	@Autowired
	CodeConversionDetailsRepo codeConversionDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	// CodeConversion
	@Override
	public List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return codeConcersionRepo.findAllCodeConversion(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public CodeConversionVO getCodeConversionById(Long id) {
		CodeConversionVO codeConversionVO = new CodeConversionVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  CodeConversion BY Id : {}", id);
			codeConversionVO = codeConcersionRepo.findCodeConversionById(id);
		} else {
			LOGGER.info("failed Received  CodeConversion For All Id.");
		}
		return codeConversionVO;

	}

	@Override
	@Transactional
	public String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "CC";
		String result = codeConcersionRepo.getCodeConversionDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO)
			throws ApplicationException {

		CodeConversionVO codeConversionVO = new CodeConversionVO();
		String screenCode = "CC";
		String message;

		if (ObjectUtils.isNotEmpty(codeConversionDTO.getId())) {
			codeConversionVO = codeConcersionRepo.findById(codeConversionDTO.getId())
					.orElseThrow(() -> new ApplicationException("CodeConversion not found"));

			codeConversionVO.setUpdatedBy(codeConversionDTO.getCreatedBy());
			createUpdateCodeConversionVOByCodeConversionDTO(codeConversionDTO, codeConversionVO);
			message = "CodeConversion Updated Successfully";
		} else {

			codeConversionVO.setCreatedBy(codeConversionDTO.getCreatedBy());
			codeConversionVO.setUpdatedBy(codeConversionDTO.getCreatedBy());

			String codeConversionDocId = codeConcersionRepo.getCodeConversionDocId(codeConversionDTO.getOrgId(),
					codeConversionDTO.getFinYear(), codeConversionDTO.getBranchCode(), codeConversionDTO.getClient(),
					screenCode);
			codeConversionVO.setDocId(codeConversionDocId);
			createUpdateCodeConversionVOByCodeConversionDTO(codeConversionDTO, codeConversionVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(codeConversionDTO.getOrgId(),
							codeConversionDTO.getFinYear(), codeConversionDTO.getBranchCode(),
							codeConversionDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "CodeConversion Created Successfully";
		}

		CodeConversionVO savedCodeConversionVO = codeConcersionRepo.save(codeConversionVO);

		List<CodeConversionDetailsVO> codeConversionDetailsVOLists = savedCodeConversionVO
				.getCodeConversionDetailsVO();
		if (codeConversionDetailsVOLists != null && !codeConversionDetailsVOLists.isEmpty()) {
			for (CodeConversionDetailsVO codeConversionDetailsVO : codeConversionDetailsVOLists
					) {
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();

				stockDetailsVOFrom.setRefNo(codeConversionVO.getDocId());
				stockDetailsVOFrom.setRefDate(codeConversionVO.getDocDate());
				stockDetailsVOFrom.setOrgId(codeConversionVO.getOrgId());
				stockDetailsVOFrom.setCustomer(codeConversionVO.getCustomer());
				stockDetailsVOFrom.setClient(codeConversionVO.getClient());
		     	stockDetailsVOFrom.setCreatedBy(codeConversionVO.getUpdatedBy());
				stockDetailsVOFrom.setFinYear(codeConversionVO.getFinYear());
				stockDetailsVOFrom.setBranch(codeConversionVO.getBranch());
				stockDetailsVOFrom.setBranchCode(codeConversionVO.getBranchCode());
				stockDetailsVOFrom.setWarehouse(codeConversionVO.getWarehouse());
		        stockDetailsVOFrom.setPartno(codeConversionDetailsVO.getPartNo());
		        stockDetailsVOFrom.setPartDesc(codeConversionDetailsVO.getPartDescription());
	            stockDetailsVOFrom.setGrnNo(codeConversionDetailsVO.getGrnNo());
		        stockDetailsVOFrom.setGrnDate(codeConversionDetailsVO.getGrnDate());
		        stockDetailsVOFrom.setStatus(codeConversionDetailsVO.getStatus());
		        stockDetailsVOFrom.setSku(codeConversionDetailsVO.getSku());
		        stockDetailsVOFrom.setBinType(codeConversionDetailsVO.getBinType());
		        stockDetailsVOFrom.setBatch(codeConversionDetailsVO.getBatchNo());
		        stockDetailsVOFrom.setBatchDate(codeConversionDetailsVO.getBatchDate());
		        stockDetailsVOFrom.setLotNo(codeConversionDetailsVO.getLotNo());
		        stockDetailsVOFrom.setBin(codeConversionDetailsVO.getBin());
		        stockDetailsVOFrom.setSQty(codeConversionDetailsVO.getActualQty()* -1); //NEGATIVE QUANTITY
		        stockDetailsVOFrom.setRate(codeConversionDetailsVO.getRate());
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
//				
				
				stockDetailsVOTo.setRefNo(codeConversionVO.getDocId());
				stockDetailsVOTo.setRefDate(codeConversionVO.getDocDate());
				stockDetailsVOTo.setOrgId(codeConversionVO.getOrgId());
				stockDetailsVOTo.setCustomer(codeConversionVO.getCustomer());
				stockDetailsVOTo.setClient(codeConversionVO.getClient());
				stockDetailsVOTo.setCreatedBy(codeConversionVO.getUpdatedBy());
				stockDetailsVOTo.setFinYear(codeConversionVO.getFinYear());
				stockDetailsVOTo.setBranch(codeConversionVO.getBranch());
				stockDetailsVOTo.setBranchCode(codeConversionVO.getBranchCode());
				stockDetailsVOTo.setWarehouse(codeConversionVO.getWarehouse());
				stockDetailsVOTo.setSQty(codeConversionDetailsVO.getConvertQty());
				stockDetailsVOTo.setRate(codeConversionDetailsVO.getCRate());
				stockDetailsVOTo.setPartno(codeConversionDetailsVO.getCPartNo());
				stockDetailsVOTo.setPartDesc(codeConversionDetailsVO.getCPartDesc());
				stockDetailsVOTo.setGrnNo(codeConversionDetailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(codeConversionDetailsVO.getGrnDate());
				stockDetailsVOTo.setStatus(codeConversionDetailsVO.getStatus());
				stockDetailsVOTo.setSku(codeConversionDetailsVO.getCSku());
				stockDetailsVOTo.setBinType(codeConversionDetailsVO.getCbinType());
				stockDetailsVOTo.setBatch(codeConversionDetailsVO.getCBatchNo());
				stockDetailsVOTo.setBatchDate(codeConversionDetailsVO.getBatchDate());
				stockDetailsVOTo.setLotNo(codeConversionDetailsVO.getCLotNo());
				stockDetailsVOTo.setBin(codeConversionDetailsVO.getCbin());
				stockDetailsVOTo.setSQty(codeConversionDetailsVO.getActualQty()); //positive QUANTITY
				stockDetailsVOTo.setRemarks(codeConversionDetailsVO.getRemarks());
				stockDetailsRepo.save(stockDetailsVOTo);
			}
		}

	
		
		Map<String, Object> response = new HashMap<>();
		response.put("codeConversionVO", codeConversionVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateCodeConversionVOByCodeConversionDTO(CodeConversionDTO codeConversionDTO,
			CodeConversionVO codeConversionVO) {

		codeConversionVO.setOrgId(codeConversionDTO.getOrgId());
		codeConversionVO.setCustomer(codeConversionDTO.getCustomer());
		codeConversionVO.setClient(codeConversionDTO.getClient());
		codeConversionVO.setFinYear(codeConversionDTO.getFinYear());
		codeConversionVO.setBranch(codeConversionDTO.getBranch());
		codeConversionVO.setBranchCode(codeConversionDTO.getBranchCode());
		codeConversionVO.setWarehouse(codeConversionDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(codeConversionVO.getId())) {
			List<CodeConversionDetailsVO> CodeConversionDetailsVO1 = codeConversionDetailsRepo
					.findByCodeConversionVO(codeConversionVO);
			codeConversionDetailsRepo.deleteAll(CodeConversionDetailsVO1);
		}

		List<CodeConversionDetailsVO> codeConversionDetailsVOs = new ArrayList<>();
		for (CodeConversionDetailsDTO codeConversionDetailsDTO : codeConversionDTO.getCodeConversionDetailsDTO()) {

			CodeConversionDetailsVO codeConversionDetailsVO = new CodeConversionDetailsVO();
			codeConversionDetailsVO.setPartNo(codeConversionDetailsDTO.getPartNo());
			codeConversionDetailsVO.setPartDescription(codeConversionDetailsDTO.getPartDescription());
			codeConversionDetailsVO.setGrnNo(codeConversionDetailsDTO.getGrnNo());
			codeConversionDetailsVO.setGrnDate(codeConversionDetailsDTO.getGrnDate());
			codeConversionDetailsVO.setStatus(codeConversionDetailsDTO.getStatus());
			codeConversionDetailsVO.setSku(codeConversionDetailsDTO.getSku());
			codeConversionDetailsVO.setBinType(codeConversionDetailsDTO.getBinType());
			codeConversionDetailsVO.setBatchNo(codeConversionDetailsDTO.getBatchNo());
			codeConversionDetailsVO.setBatchDate(codeConversionDetailsDTO.getBatchDate());
			codeConversionDetailsVO.setLotNo(codeConversionDetailsDTO.getLotNo());
			codeConversionDetailsVO.setBin(codeConversionDetailsDTO.getBin());
			codeConversionDetailsVO.setQty(codeConversionDetailsDTO.getQty());
			codeConversionDetailsVO.setActualQty(codeConversionDetailsDTO.getActualQty());
			codeConversionDetailsVO.setRate(codeConversionDetailsDTO.getRate());

			codeConversionDetailsVO.setConvertQty(codeConversionDetailsDTO.getConvertQty());
			codeConversionDetailsVO.setCRate(codeConversionDetailsDTO.getCRate());
			codeConversionDetailsVO.setCPartNo(codeConversionDetailsDTO.getCPartNo());
			codeConversionDetailsVO.setCPartDesc(codeConversionDetailsDTO.getCPartDesc());
			codeConversionDetailsVO.setCSku(codeConversionDetailsDTO.getCSku());
			codeConversionDetailsVO.setCBatchNo(codeConversionDetailsDTO.getCBatchNo());
			codeConversionDetailsVO.setCBatchDate(codeConversionDetailsDTO.getCBatchDate());
			codeConversionDetailsVO.setCLotNo(codeConversionDetailsDTO.getCLotNo());
			codeConversionDetailsVO.setCbin(codeConversionDetailsDTO.getCbin());
			codeConversionDetailsVO.setCbinType(codeConversionDetailsDTO.getCbinType());
			codeConversionDetailsVO.setRemarks(codeConversionDetailsDTO.getRemarks());
			codeConversionDetailsVO.setQcFlags(codeConversionDetailsDTO.isQcFlags());
			codeConversionDetailsVO.setCodeConversionVO(codeConversionVO);
			codeConversionDetailsVOs.add(codeConversionDetailsVO);

		}
		codeConversionVO.setCodeConversionDetailsVO(codeConversionDetailsVOs);

	}

	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin) {

		Set<Object[]> result = codeConcersionRepo.findPartNoAndPartDescFromStockForCodeConversion(orgId, finYear,
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
	public List<Map<String, Object>> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin,String partNo,String partDesc,String sku) {

		Set<Object[]> result = codeConcersionRepo.findGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(orgId, finYear,
				branch, branchCode, client, bin, partNo,partDesc,sku);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("bintype", fs[1] != null ? fs[1].toString() : "");
			part.put("batchNo", fs[2] != null ? fs[2].toString() : "");
			part.put("batchDate", fs[3] != null ? fs[3].toString() : "");
			part.put("LotNo", fs[4] != null ? fs[4].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = codeConcersionRepo.findBinFromStockForCodeConversion(orgId, finYear, branch,
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
}
