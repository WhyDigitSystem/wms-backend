package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.CodeConversionDetailsDTO;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.GrnDetailsDTO;
import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.HandlingStockInVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CodeConversionDetailsRepo;
import com.whydigit.wms.repo.CodeConversionRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;

@Service
public class StockProcessServiceImpl implements StockProcessService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessServiceImpl.class);

	@Autowired
	CodeConversionRepo codeConcersionRepo;

	@Autowired
	CodeConversionDetailsRepo codeConversionDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

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

//		List<CodeConversionDetailsVO> codeConversionDetailsVOLists = savedCodeConversionVO
//				.getCodeConversionDetailsVO();
//		if (codeConversionDetailsVOLists != null && !codeConversionDetailsVOLists.isEmpty()) {
//			for (CodeConversionDetailsVO detailsVO : codeConversionDetailsVOLists
//					) {
//				// Create StockDetails for fromBin with negative quantity
//				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
//				stockDetailsVOFrom.setBin(detailsVO.getBin());
//				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
//				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescripition());
//				stockDetailsVOFrom.setGrnNo(detailsVO.getGRNNo());
//				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
//				stockDetailsVOFrom.setQcFlag(detailsVO.isQcFlag());
//				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
//				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
//				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
//				stockDetailsVOFrom.setStatus(detailsVO.getStatus());
//				stockDetailsVOFrom.setSQty(detailsVO.getFromQty() * -1); // Negative quantity
//				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
//				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
//				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
//				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
//				stockDetailsVOFrom.setSku(savedLocationMovementVO.getSku());
//				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
//				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
//				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
//				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
//				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
//				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
//				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());
//				stockDetailsRepo.save(stockDetailsVOFrom);
//
//				// Create StockDetails for toBin with positive quantity
//				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
//				stockDetailsVOTo.setBin(detailsVO.getToBin());
//				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
//				stockDetailsVOTo.setBinClass(detailsVO.getBinClass());
//				stockDetailsVOTo.setBinType(detailsVO.getBinType());
//				stockDetailsVOTo.setQcFlag(detailsVO.isQcFlag());
//				stockDetailsVOTo.setPartDesc(detailsVO.getPartDescripition());
//				stockDetailsVOTo.setGrnNo(detailsVO.getGRNNo());
//				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
//				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
//				stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
//				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
//				stockDetailsVOTo.setStatus(detailsVO.getStatus());
//				stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
//				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
//				stockDetailsVOTo.setSku(savedLocationMovementVO.getSku());
//				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
//				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
//				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
//				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
//				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
//				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
//				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
//				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());
//				stockDetailsRepo.save(stockDetailsVOTo);
//			}
//		}

		
		
		
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
			codeConversionDetailsVO.setSku(codeConversionDetailsDTO.getSku());
			codeConversionDetailsVO.setBinType(codeConversionDetailsDTO.getBinType());
			codeConversionDetailsVO.setBatchNo(codeConversionDetailsDTO.getBatchNo());
			codeConversionDetailsVO.setLotNo(codeConversionDetailsDTO.getLotNo());
			codeConversionDetailsVO.setPallet(codeConversionDetailsDTO.getPallet());
			codeConversionDetailsVO.setQty(codeConversionDetailsDTO.getQty());
			codeConversionDetailsVO.setActualQty(codeConversionDetailsDTO.getActualQty());
			codeConversionDetailsVO.setRate(codeConversionDetailsDTO.getRate());
			codeConversionDetailsVO.setConvertQty(codeConversionDetailsDTO.getConvertQty());
			codeConversionDetailsVO.setCRate(codeConversionDetailsDTO.getCRate());
			codeConversionDetailsVO.setCPartNo(codeConversionDetailsDTO.getCPartNo());
			codeConversionDetailsVO.setCPartDesc(codeConversionDetailsDTO.getCPartDesc());
			codeConversionDetailsVO.setCSku(codeConversionDetailsDTO.getCSku());
			codeConversionDetailsVO.setCBatchNo(codeConversionDetailsDTO.getCBatchNo());
			codeConversionDetailsVO.setCLotNo(codeConversionDetailsDTO.getCLotNo());
			codeConversionDetailsVO.setCbin(codeConversionDetailsDTO.getCbin());
			codeConversionDetailsVO.setRemarks(codeConversionDetailsDTO.getRemarks());
			codeConversionDetailsVO.setQcFlags(codeConversionDetailsDTO.isQcFlags());
			codeConversionDetailsVO.setCodeConversionVO(codeConversionVO);
			codeConversionDetailsVOs.add(codeConversionDetailsVO);

		}
		codeConversionVO.setCodeConversionDetailsVO(codeConversionDetailsVOs);

	}

}
