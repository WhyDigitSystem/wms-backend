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
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CodeConversionDetailsRepo;
import com.whydigit.wms.repo.CodeConversionRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class CodeConversionServiceImpl implements CodeConversionService{

	
	public static final Logger LOGGER = LoggerFactory.getLogger(CodeConversionServiceImpl.class);
	
	@Autowired
	CodeConversionRepo codeConversionRepo;

	@Autowired
	CodeConversionDetailsRepo codeConversionDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;
	
	@Autowired
	ClientRepo clientRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	// CodeConversion
		@Override
		public List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
				String client, String warehouse) {
			return codeConversionRepo.findAllCodeConversion(orgId, finYear, branch, branchCode, client, warehouse);
		}

		@Override
		public CodeConversionVO getCodeConversionById(Long id) {
			CodeConversionVO codeConversionVO = new CodeConversionVO();
			if (ObjectUtils.isNotEmpty(id)) {
				LOGGER.info("Successfully Received  CodeConversion BY Id : {}", id);
				codeConversionVO = codeConversionRepo.findCodeConversionById(id);
			} else {
				LOGGER.info("failed Received  CodeConversion For All Id.");
			}
			return codeConversionVO;

		}

		@Override
		@Transactional
		public String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
			String ScreenCode = "CC";
			String result = codeConversionRepo.getCodeConversionDocId(orgId, finYear, branchCode, client, ScreenCode);
			return result;
		}

		@Override
		public Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO)
				throws ApplicationException {
			CodeConversionVO codeConversionVO = new CodeConversionVO();
			String screenCode = "CC";
			String message;

			if (ObjectUtils.isNotEmpty(codeConversionDTO.getId())) {
				codeConversionVO = codeConversionRepo.findById(codeConversionDTO.getId())
						.orElseThrow(() -> new ApplicationException("CodeConversion not found"));

				codeConversionVO.setUpdatedBy(codeConversionDTO.getCreatedBy());
				createUpdateCodeConversionVOByCodeConversionDTO(codeConversionDTO, codeConversionVO);
				message = "CodeConversion Updated Successfully";
			} else {

				codeConversionVO.setCreatedBy(codeConversionDTO.getCreatedBy());
				codeConversionVO.setUpdatedBy(codeConversionDTO.getCreatedBy());

				String codeConversionDocId = codeConversionRepo.getCodeConversionDocId(codeConversionDTO.getOrgId(),
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

			CodeConversionVO savedCodeConversionVO = codeConversionRepo.save(codeConversionVO);
			List<CodeConversionDetailsVO> codeConversionDetailsVOLists = savedCodeConversionVO.getCodeConversionDetailsVO();
			if (codeConversionDetailsVOLists != null && !codeConversionDetailsVOLists.isEmpty()) {
				for (CodeConversionDetailsVO codeConversionDetailsVO : codeConversionDetailsVOLists) {
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
					stockDetailsVOFrom.setSourceScreenCode(codeConversionVO.getScreenCode());
					stockDetailsVOFrom.setSourceScreenName(codeConversionVO.getScreenName());
					stockDetailsVOFrom.setSourceId(codeConversionVO.getId());
					stockDetailsVOFrom.setBinClass(codeConversionDetailsVO.getBinClass());
					stockDetailsVOFrom.setCellType(codeConversionDetailsVO.getCellType());
					stockDetailsVOFrom.setClientCode(clientRepo.getClientCode(codeConversionVO.getOrgId(),codeConversionVO.getClient()));
					stockDetailsVOFrom.setCore(codeConversionDetailsVO.getCore());
					stockDetailsVOFrom.setExpDate(codeConversionDetailsVO.getExpDate());
					stockDetailsVOFrom.setPcKey(codeConversionDetailsVO.getPckey());
					stockDetailsVOFrom.setSSku(codeConversionDetailsVO.getSsku());
					stockDetailsVOFrom.setStockDate(codeConversionDetailsVO.getStockDate());
					stockDetailsVOFrom.setPartno(codeConversionDetailsVO.getPartNo());
					stockDetailsVOFrom.setPartDesc(codeConversionDetailsVO.getPartDesc());
					stockDetailsVOFrom.setGrnNo(codeConversionDetailsVO.getGrnNo());
					stockDetailsVOFrom.setGrnDate(codeConversionDetailsVO.getGrnDate());
					stockDetailsVOFrom.setStatus(codeConversionDetailsVO.getStatus());
					stockDetailsVOFrom.setSku(codeConversionDetailsVO.getSku());
					stockDetailsVOFrom.setBinType(codeConversionDetailsVO.getBinType());
					stockDetailsVOFrom.setBatch(codeConversionDetailsVO.getBatchNo());
					stockDetailsVOFrom.setBatchDate(codeConversionDetailsVO.getBatchDate());
					stockDetailsVOFrom.setLotNo(codeConversionDetailsVO.getLotNo());
					stockDetailsVOFrom.setBin(codeConversionDetailsVO.getBin());
					stockDetailsVOFrom.setSQty(codeConversionDetailsVO.getActualQty() * -1); // NEGATIVE QUANTITY
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
					stockDetailsVOTo.setSourceScreenCode(codeConversionVO.getScreenCode());
					stockDetailsVOTo.setSourceScreenName(codeConversionVO.getScreenName());
					stockDetailsVOTo.setSourceId(codeConversionVO.getId());
					stockDetailsVOTo.setBinClass(codeConversionDetailsVO.getBinClass());
					stockDetailsVOTo.setCellType(codeConversionDetailsVO.getCellType());
					stockDetailsVOTo.setClientCode(clientRepo.getClientCode(codeConversionVO.getOrgId(),codeConversionVO.getClient()));
					stockDetailsVOTo.setCore(codeConversionDetailsVO.getCore());
					stockDetailsVOTo.setExpDate(codeConversionDetailsVO.getExpDate());
					stockDetailsVOTo.setPcKey(codeConversionDetailsVO.getPckey());
					stockDetailsVOTo.setSSku(codeConversionDetailsVO.getSsku());
					stockDetailsVOTo.setStockDate(codeConversionDetailsVO.getStockDate());
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
					stockDetailsVOTo.setSQty(codeConversionDetailsVO.getActualQty()); // positive QUANTITY
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
				codeConversionDetailsVO.setPartDesc(codeConversionDetailsDTO.getPartDesc());
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
				codeConversionDetailsVO.setBinClass(codeConversionDetailsDTO.getBinClass());
				codeConversionDetailsVO.setCellType(codeConversionDetailsDTO.getCellType());
				codeConversionDetailsVO.setClientCode(clientRepo.getClientCode(codeConversionVO.getOrgId(),codeConversionVO.getClient()));
				codeConversionDetailsVO.setCore(codeConversionDetailsDTO.getCore());
				codeConversionDetailsVO.setExpDate(codeConversionDetailsDTO.getExpDate());
				codeConversionDetailsVO.setPckey(codeConversionDetailsDTO.getPckey());
				codeConversionDetailsVO.setSsku(codeConversionDetailsDTO.getSsku());
				codeConversionDetailsVO.setStockDate(codeConversionDetailsDTO.getStockDate());
				
				codeConversionDetailsVO.setCodeConversionVO(codeConversionVO);
				codeConversionDetailsVOs.add(codeConversionDetailsVO);

			}
			codeConversionVO.setCodeConversionDetailsVO(codeConversionDetailsVOs);
		}
		

//		
//		@Override
//		public int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
//				String grnNo,String batchNo,String binType,String bin) {
//			Set<Object[]> getAvlQtyCodeConversion = codeConversionRepo.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
//					grnNo,batchNo,binType,bin);
//			return calculateTotalQtyCodeConversion(getAvlQtyCodeConversion);
//		}
//
//		private int calculateTotalQtyCodeConversion(Set<Object[]> getAvlQtyCodeConversion) {
//			int totalQty = 0;
//			for (Object[] qt : getAvlQtyCodeConversion) {
//				totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
//			}
//			return totalQty;
//		}
		
//		@Override
//		public int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
//		                                   String grnNo, String batchNo, String binType, String bin) {
//		    Set<Object[]> getAvlQtyCodeConversion = codeConversionRepo.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
//		            grnNo, batchNo, binType, bin);
//
//		    return calculateTotalQtyCodeConversion(getAvlQtyCodeConversion);
//		}
//
//		private int calculateTotalQtyCodeConversion(Set<Object[]> getAvlQtyCodeConversion) {
//		    int totalQty = 0;
//		    for (Object[] qt : getAvlQtyCodeConversion) {
//					totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
//		        }
//		    		    return totalQty;
//		}

//		@Override
//		public int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
//                String grnNo, String batchNo, String binType, String bin) {
//			int getFromQty= codeConversionRepo.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
//		            grnNo, batchNo, binType, bin);
//			return getFromQty;
//		}
		
		@Override
		public Integer getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse,
		                                    String branch, String partNo, String grnNo, String batchNo, String binType, String bin) {
		    Integer getAvgQty = codeConversionRepo.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
		            grnNo, batchNo, binType, bin);

		    // Return 0 if getAvgQty is null
		    return getAvgQty != null ? getAvgQty : 0;
		}

		
		
		@Override
		@Transactional
		public List<Map<String, Object>> getAllFillGridFromStockForCodeConversion(Long orgId,
				String branchCode, String client,String warehouse) {

			Set<Object[]> result = codeConversionRepo.getAllFillGridFromStockForCodeConversion(orgId, branchCode,
					client,warehouse);
			return getAllFillGridCodeConversionResult(result);
		}

		private List<Map<String, Object>> getAllFillGridCodeConversionResult(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("partno", fs[0] != null ? fs[0].toString() : "");
				part.put("partdesc", fs[1] != null ? fs[1].toString() : "");
				part.put("sku", fs[2] != null ? fs[2].toString() : "");
				part.put("grnno", fs[3] != null ? fs[3].toString() : "");
				part.put("grndate", fs[4] != null ? fs[4].toString() : "");
				part.put("batch", fs[5] != null ? fs[5].toString() : "");
				part.put("batchdate", fs[6] != null ? fs[6].toString() : "");
				part.put("expdate", fs[7] != null ? fs[7].toString() : "");
				part.put("bintype", fs[8] != null ? fs[8].toString() : "");
				part.put("binclass", fs[9] != null ? fs[9].toString() : "");
				part.put("celltype", fs[10] != null ? fs[10].toString() : "");
				part.put("core", fs[11] != null ? fs[11].toString() : "");
				part.put("bin", fs[12] != null ? fs[12].toString() : "");
				part.put("status", fs[13] != null ? fs[13].toString() : "");
				part.put("qcflag", fs[14] != null ? fs[14].toString() : "");
				part.put("totalQty", fs[15] != null ? fs[15].toString() : "");
				part.put("id",fs[16]!=null ? Integer.parseInt(fs[16].toString()):0);
				details1.add(part);
			}
			return details1;
		}

		@Override
		@Transactional
		public List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId,
				 String branchCode, String client,String warehouse) {

			Set<Object[]> result = codeConversionRepo.findPartNoAndPartDescFromStockForCodeConversion(orgId,
					 branchCode, client,warehouse);
			return getCodeConeversionPartResult(result);
		}

		private List<Map<String, Object>> getCodeConeversionPartResult(Set<Object[]> result) {
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
		public List<Map<String, Object>> getGrnNoAndGrnDateFromStockForCodeConversion(
				Long orgId,String branchCode, String client, String warehouse, String partNo) {

			Set<Object[]> result = codeConversionRepo
					.findGrnNoAndGrnDateFromStockForCodeConversion(orgId,
							branchCode, client, warehouse, partNo);
			return getGrnCodeConversionResult(result);
		}

		private List<Map<String, Object>> getGrnCodeConversionResult(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
				part.put("grndate", fs[1] != null ? fs[1].toString() : "");
				details1.add(part);
			}
			return details1;
		}

		@Transactional
		public List<Map<String, Object>> getBinTypeFromStockForCodeConversion(
				Long orgId,String branchCode, String client, String warehouse, String partNo, String grnNo) {

			Set<Object[]> result = codeConversionRepo
					.findBinTypeFromStockForCodeConversion(orgId,
							branchCode, client, warehouse, partNo,grnNo);
			return BinTypeFromStockForCodeConversion(result);
		}

		private List<Map<String, Object>> BinTypeFromStockForCodeConversion(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("binType", fs[0] != null ? fs[0].toString() : "");
				details1.add(part);
			}
			return details1;
		}
		
		@Transactional
		public List<Map<String, Object>> getBatchNoFromStockForCodeConversion(
				Long orgId,String branchCode, String client, String warehouse, String partNo, String grnNo,String binType) {

			Set<Object[]> result = codeConversionRepo
					.findBatchNoFromStockForCodeConversion(orgId,
							branchCode, client, warehouse, partNo,grnNo,binType);
			return getbatchnoCodeConversionResult(result);
		}

		private List<Map<String, Object>> getbatchnoCodeConversionResult(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("batchNo", fs[0] != null ? fs[0].toString() : "");
				part.put("batchDate", fs[1] != null ? fs[1].toString() : "");
				part.put("expDate", fs[2] != null ? fs[2].toString() : "");
				part.put("lotNo", fs[3] != null ? fs[3].toString() : "");
				details1.add(part);
			}
			return details1;
		}
		
		@Transactional
		public List<Map<String, Object>> getBinFromStockForCodeConversion(
				Long orgId,String branchCode, String client, String warehouse, String partNo, String grnNo,String binType,String batchNo) {

			Set<Object[]> result = codeConversionRepo
					.findBinFromStockForCodeConversion(orgId,
							branchCode, client, warehouse, partNo,grnNo,binType,batchNo);
			return BinFromStockForCodeConversion(result);
		}

		private List<Map<String, Object>> BinFromStockForCodeConversion(Set<Object[]> result) {
			List<Map<String, Object>> details1 = new ArrayList<>();
			for (Object[] fs : result) {
				Map<String, Object> part = new HashMap<>();
				part.put("bin", fs[0] != null ? fs[0].toString() : "");
				details1.add(part);
			}
			return details1;
		}

		
//		@Override
//		@Transactional
//		public List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String branch,
//				String branchCode, String client) {
//
//			Set<Object[]> result = codeConversionRepo.findBinFromStockForCodeConversion(orgId,branch, branchCode,
//					client);
//			return getBinCodeConversionResult(result);
//		}
//
//		private List<Map<String, Object>> getBinCodeConversionResult(Set<Object[]> result) {
//			List<Map<String, Object>> details1 = new ArrayList<>();
//			for (Object[] fs : result) {
//				Map<String, Object> part = new HashMap<>();
//				part.put("bin", fs[0] != null ? fs[0].toString() : "");
//				part.put("binClass", fs[1] != null ? fs[1].toString() : "");
//				part.put("binType", fs[2] != null ? fs[2].toString() : "");
//				part.put("avlQty", fs[3] != null ? fs[3].toString() : "");
//				details1.add(part);
//			}
//			return details1;
//		}
		
//		@Override
//		@Transactional
//		public List<Map<String, Object>> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId,
//				String branch, String branchCode, String client, String bin) {
//
//
//			Set<Object[]> result = codeConversionRepo.getCpartNoAndCpartDescFromStockForCodeConversion(orgId,
//	    branch, branchCode, client, bin);
//			return getCodeConeversionCPartNoAndCPartDescResult(result);
//		}
//
//		private List<Map<String, Object>> getCodeConeversionCPartNoAndCPartDescResult(Set<Object[]> result) {
//			List<Map<String, Object>> details1 = new ArrayList<>();
//			for (Object[] fs : result) {
//				Map<String, Object> part = new HashMap<>();
//				part.put("cpartNo", fs[0] != null ? fs[0].toString() : "");
//				part.put("cpartDesc", fs[1] != null ? fs[1].toString() : "");
//				part.put("csku", fs[2] != null ? fs[2].toString() : "");
//				details1.add(part);
//			}
//			return details1;
//		}
//		
//		
//		@Override
//		@Transactional
//		public List<Map<String, Object>> getCBinFromStockForCodeConversion(Long orgId, String branch,
//				String branchCode, String client) {
//
//			Set<Object[]> result = codeConversionRepo.findCBinFromStockForCodeConversion(orgId, branch, branchCode,
//					client);
//			return getCBinCodeConversionResult(result);
//		}
//
//		private List<Map<String, Object>> getCBinCodeConversionResult(Set<Object[]> result) {
//			List<Map<String, Object>> details1 = new ArrayList<>();
//			for (Object[] fs : result) {
//				Map<String, Object> part = new HashMap<>();
//				part.put("bin", fs[0] != null ? fs[0].toString() : "");
//				part.put("binclass", fs[1] != null ? fs[1].toString() : "");
//				part.put("celltype", fs[2] != null ? fs[2].toString() : "");
//				part.put("clientcode", fs[3] != null ? fs[3].toString() : "");
//				part.put("core", fs[4] != null ? fs[4].toString() : "");
//				part.put("expdate", fs[5] != null ? fs[5].toString() : "");
//				part.put("pckey", fs[6] != null ? fs[6].toString() : "");
//				part.put("ssku", fs[7] != null ? fs[7].toString() : "");
//				part.put("stockdate", fs[8] != null ? fs[8].toString() : "");
//				details1.add(part);
//			}
//			return details1;
//		}
//		

		
}
