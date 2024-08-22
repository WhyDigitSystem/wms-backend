package com.whydigit.wms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.CodeConversionDetailsDTO;
import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.CycleCountDetailsDTO;
import com.whydigit.wms.dto.DeKittingChildDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.DeKittingParentDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.dto.StockRestateDetailsDTO;
import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.CycleCountDetailsVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DeKittingChildVO;
import com.whydigit.wms.entity.DeKittingParentVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.entity.StockRestateDetailsVO;
import com.whydigit.wms.entity.StockRestateVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CodeConversionDetailsRepo;
import com.whydigit.wms.repo.CodeConversionRepo;
import com.whydigit.wms.repo.CycleCountDetailsRepo;
import com.whydigit.wms.repo.CycleCountRepo;
import com.whydigit.wms.repo.DeKittingChildRepo;
import com.whydigit.wms.repo.DeKittingParentRepo;
import com.whydigit.wms.repo.DeKittingRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.LocationMovementDetailsRepo;
import com.whydigit.wms.repo.LocationMovementRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SalesReturnDetailsRepo;
import com.whydigit.wms.repo.SalesReturnRepo;
import com.whydigit.wms.repo.StockDetailsRepo;
import com.whydigit.wms.repo.StockRestateRepo;

import net.bytebuddy.asm.Advice.Return;

@Service
public class StockProcessServiceImpl implements StockProcessService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessServiceImpl.class);

	@Autowired
	CodeConversionRepo codeConversionRepo;

	@Autowired
	CodeConversionDetailsRepo codeConversionDetailsRepo;

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	SalesReturnRepo salesReturnRepo;

	@Autowired
	SalesReturnDetailsRepo salesReturnDetailsRepo;

	@Autowired
	LocationMovementRepo locationMovementRepo;

	@Autowired
	LocationMovementDetailsRepo locationMovementDetailsRepo;

	@Autowired
	DeKittingRepo deKittingRepo;

	@Autowired
	DeKittingChildRepo deKittingChildRepo;

	@Autowired
	DeKittingParentRepo deKittingParentRepo;

	@Autowired
	StockRestateRepo stockRestateRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	CycleCountRepo cycleCountRepo;

	@Autowired
	CycleCountDetailsRepo cycleCountDetailsRepo;

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
				stockDetailsVOFrom.setClientCode(
						clientRepo.getClientCode(codeConversionVO.getOrgId(), codeConversionVO.getClient()));
				stockDetailsVOFrom.setCore(codeConversionDetailsVO.getCore());
				stockDetailsVOFrom.setExpDate(codeConversionDetailsVO.getExpDate());
				stockDetailsVOFrom.setPcKey(codeConversionDetailsVO.getPckey());
				stockDetailsVOFrom.setSSku(codeConversionDetailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(codeConversionDetailsVO.getStockDate());
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
				stockDetailsVOTo.setClientCode(
						clientRepo.getClientCode(codeConversionVO.getOrgId(), codeConversionVO.getClient()));
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
			codeConversionDetailsVO.setBinClass(codeConversionDetailsDTO.getBinClass());
			codeConversionDetailsVO.setCellType(codeConversionDetailsDTO.getCellType());
			codeConversionDetailsVO
					.setClientCode(clientRepo.getClientCode(codeConversionVO.getOrgId(), codeConversionVO.getClient()));
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

	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin) {

		Set<Object[]> result = codeConversionRepo.findPartNoAndPartDescFromStockForCodeConversion(orgId, branch,
				branchCode, client, bin);
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

	@Override
	@Transactional
	public List<Map<String, Object>> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin) {

		Set<Object[]> result = codeConversionRepo.getCpartNoAndCpartDescFromStockForCodeConversion(orgId, branch,
				branchCode, client, bin);
		return getCodeConeversionCPartNoAndCPartDescResult(result);
	}

	private List<Map<String, Object>> getCodeConeversionCPartNoAndCPartDescResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("cpartNo", fs[0] != null ? fs[0].toString() : "");
			part.put("cpartDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("csku", fs[2] != null ? fs[2].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getCBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client) {

		Set<Object[]> result = codeConversionRepo.findCBinFromStockForCodeConversion(orgId, branch, branchCode, client);
		return getCBinCodeConversionResult(result);
	}

	private List<Map<String, Object>> getCBinCodeConversionResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binclass", fs[1] != null ? fs[1].toString() : "");
			part.put("celltype", fs[2] != null ? fs[2].toString() : "");
			part.put("clientcode", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("expdate", fs[5] != null ? fs[5].toString() : "");
			part.put("pckey", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockdate", fs[8] != null ? fs[8].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	public int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc) {
		Set<Object[]> getAvlQtyCodeConversion = codeConversionRepo.getAvlQtyCodeConversion(orgId, client, branchCode,
				warehouse, branch, partNo, partDesc);
		return calculateTotalQtyCodeConversion(getAvlQtyCodeConversion);
	}

	private int calculateTotalQtyCodeConversion(Set<Object[]> getAvlQtyCodeConversion) {
		int totalQty = 0;
		for (Object[] qt : getAvlQtyCodeConversion) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(
			Long orgId, String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku) {

		Set<Object[]> result = codeConversionRepo
				.findGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(orgId, branch, branchCode,
						client, bin, partNo, partDesc, sku);
		return getGrnCodeConversionResult(result);
	}

	private List<Map<String, Object>> getGrnCodeConversionResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("binType", fs[1] != null ? fs[1].toString() : "");
			part.put("batchNo", fs[2] != null ? fs[2].toString() : "");
			part.put("batchDate", fs[3] != null ? fs[3].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client) {

		Set<Object[]> result = codeConversionRepo.findBinFromStockForCodeConversion(orgId, branch, branchCode, client);
		return getBinCodeConversionResult(result);
	}

	private List<Map<String, Object>> getBinCodeConversionResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("binType", fs[2] != null ? fs[2].toString() : "");
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getAllFillGridFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = codeConversionRepo.getAllFillGridFromStockForCodeConversion(orgId, branch, branchCode,
				client);
		return getAllFillGridCodeConversionResult(result);
	}

	private List<Map<String, Object>> getAllFillGridCodeConversionResult(Set<Object[]> result) {
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
			part.put("batch", fs[13] != null ? fs[13].toString() : "");
			part.put("batchDate", fs[14] != null ? fs[14].toString() : "");
			part.put("lotNo", fs[15] != null ? fs[15].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	// StockRestate
	@Override
	public List<StockRestateVO> getAllStockRestate(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return stockRestateRepo.findAllStockRestate(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public StockRestateVO getStockRestateById(Long id) {
		StockRestateVO stockRestateVO = new StockRestateVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  StockRestate BY Id : {}", id);
			stockRestateVO = stockRestateRepo.findById(id).orElse(null);
		} else {
			LOGGER.info("failed Received  StockRestate For All Id.");
		}
		return stockRestateVO;

	}

	@Override
	@Transactional
	public String getStockRestateDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "SRS";
		String result = stockRestateRepo.getStockRestateDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	@Override
	public Map<String, Object> createStockRestate(StockRestateDTO stockRestateDTO) throws ApplicationException {

		StockRestateVO stockRestateVO = new StockRestateVO();
		String message = "Stock Restate Created Successfully";
		String screenCode = "SRS";

		String docId = stockRestateRepo.getStockRestateDocId(stockRestateDTO.getOrgId(), stockRestateDTO.getFinYear(),
				stockRestateDTO.getBranchCode(), stockRestateDTO.getClient(), screenCode);

		stockRestateVO.setDocId(docId);
		stockRestateVO.setTransferFrom(stockRestateDTO.getTransferFrom());
		stockRestateVO.setTransferTo(stockRestateDTO.getTransferTo());
		stockRestateVO.setTransferFromFlag(stockRestateDTO.getTransferFromFlag());
		stockRestateVO.setTransferToFlag(stockRestateDTO.getTransferToFlag());
		stockRestateVO.setEntryNo(stockRestateDTO.getEntryNo());
		stockRestateVO.setOrgId(stockRestateDTO.getOrgId());
		stockRestateVO.setCustomer(stockRestateDTO.getCustomer());
		stockRestateVO.setClient(stockRestateDTO.getClient());
		stockRestateVO.setFinYear(stockRestateDTO.getFinYear());
		stockRestateVO.setBranch(stockRestateDTO.getBranch());
		stockRestateVO.setBranchCode(stockRestateDTO.getBranchCode());
		stockRestateVO.setWarehouse(stockRestateDTO.getWarehouse());
		stockRestateVO.setCreatedBy(stockRestateDTO.getCreatedBy());
		stockRestateVO.setUpdatedBy(stockRestateDTO.getCreatedBy());

		List<StockRestateDetailsVO> stockRestateDetailsVO = new ArrayList<>();
		List<StockRestateDetailsDTO> stockRestateDetailsDTOList = stockRestateDTO.getStockRestateDetailsDTO();
		if (stockRestateDetailsDTOList != null) {
			for (StockRestateDetailsDTO stockRestateDetailsDTO : stockRestateDetailsDTOList) {
				StockRestateDetailsVO stockRestateDetailsVOs = new StockRestateDetailsVO();
				stockRestateDetailsVOs.setFromBin(stockRestateDetailsDTO.getFromBin());
				stockRestateDetailsVOs.setFromBinClass(stockRestateDetailsDTO.getFromBinClass());
				stockRestateDetailsVOs.setFromBinType(stockRestateDetailsDTO.getFromBinType());
				stockRestateDetailsVOs.setFromCellType(stockRestateDetailsDTO.getFromCellType());
				stockRestateDetailsVOs.setFromCore(stockRestateDetailsDTO.getFromCore());
				stockRestateDetailsVOs.setPartNo(stockRestateDetailsDTO.getPartNo());
				stockRestateDetailsVOs.setPartDesc(stockRestateDetailsDTO.getPartDesc());
				stockRestateDetailsVOs.setSku(stockRestateDetailsDTO.getSku());
				stockRestateDetailsVOs.setGrnNo(stockRestateDetailsDTO.getGrnNo());
				stockRestateDetailsVOs.setGrnDate(stockRestateDetailsDTO.getGrnDate());
				stockRestateDetailsVOs.setBatch(stockRestateDetailsDTO.getBatch());
				stockRestateDetailsVOs.setBatchDate(stockRestateDetailsDTO.getBatchDate());
				stockRestateDetailsVOs.setToBin(stockRestateDetailsDTO.getToBin());
				stockRestateDetailsVOs.setToBinClass(stockRestateDetailsDTO.getToBinClass());
				stockRestateDetailsVOs.setToBinType(stockRestateDetailsDTO.getToBinType());
				stockRestateDetailsVOs.setToCellType(stockRestateDetailsDTO.getToCellType());
				stockRestateDetailsVOs.setToCore(stockRestateDetailsDTO.getToCore());

				int getFromQty = stockRestateRepo.getAvlQty(stockRestateDTO.getOrgId(), stockRestateDTO.getBranchCode(),
						stockRestateDTO.getWarehouse(), stockRestateDTO.getClient(),
						stockRestateDTO.getTransferFromFlag(), stockRestateDetailsDTO.getFromBin(),
						stockRestateDetailsDTO.getPartNo(), stockRestateDetailsDTO.getGrnNo(),
						stockRestateDetailsDTO.getBatch());
				if (getFromQty >= stockRestateDetailsDTO.getToQty()) {
					stockRestateDetailsVOs.setFromQty(stockRestateDetailsDTO.getFromQty());
					stockRestateDetailsVOs.setToQty(stockRestateDetailsDTO.getToQty());
				} else {
					throw new ApplicationException("ToQty is Must Below or Equal to FromQty");
				}
				stockRestateDetailsVOs.setExpDate(stockRestateDetailsDTO.getExpDate());
				stockRestateDetailsVOs.setQcFlag(stockRestateDetailsDTO.getQcFlag());
				stockRestateDetailsVOs.setStockRestateVO(stockRestateVO);
				stockRestateDetailsVO.add(stockRestateDetailsVOs);
			}
		} else {
			throw new ApplicationException("Grid Details is Should not Empty");
		}
		stockRestateVO.setStockRestateDetailsVO(stockRestateDetailsVO);
		StockRestateVO restateVO = stockRestateRepo.save(stockRestateVO);
		DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
				.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(stockRestateDTO.getOrgId(),
						stockRestateDTO.getFinYear(), stockRestateDTO.getBranchCode(), stockRestateDTO.getClient(),
						screenCode);
		documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
		documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
		List<StockRestateDetailsVO> restateDetailsVOs = restateVO.getStockRestateDetailsVO();
		for (StockRestateDetailsVO restateDetailsVO : restateDetailsVOs) {
			StockDetailsVO stockDetailsVO = new StockDetailsVO();
			stockDetailsVO.setOrgId(restateVO.getOrgId());
			stockDetailsVO.setBranch(restateVO.getBranch());
			stockDetailsVO.setBranchCode(restateVO.getBranchCode());
			stockDetailsVO.setWarehouse(restateVO.getWarehouse());
			stockDetailsVO.setCustomer(restateVO.getCustomer());
			stockDetailsVO.setClient(restateVO.getClient());
			stockDetailsVO.setClientCode(clientRepo.getClientCode(restateVO.getOrgId(), restateVO.getClient()));
			stockDetailsVO.setFinYear(restateVO.getFinYear());
			stockDetailsVO.setRefNo(restateVO.getDocId());
			stockDetailsVO.setRefDate(restateVO.getDocDate());
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsVO.setCreatedBy(restateVO.getCreatedBy());
			stockDetailsVO.setUpdatedBy(restateVO.getUpdatedBy());
			stockDetailsVO.setSourceId(restateDetailsVO.getId());
			stockDetailsVO.setPartno(restateDetailsVO.getPartNo());
			stockDetailsVO.setPartDesc(restateDetailsVO.getPartDesc());
			stockDetailsVO.setSku(restateDetailsVO.getSku());
			stockDetailsVO.setSSku(restateDetailsVO.getSku());
			stockDetailsVO.setGrnNo(restateDetailsVO.getGrnNo());
			stockDetailsVO.setGrnDate(restateDetailsVO.getGrnDate());
			stockDetailsVO.setBatch(restateDetailsVO.getBatch());
			stockDetailsVO.setBatchDate(restateDetailsVO.getBatchDate());
			stockDetailsVO.setBin(restateDetailsVO.getFromBin());
			stockDetailsVO.setBinType(restateDetailsVO.getFromBinType());
			stockDetailsVO.setPcKey(materialRepo.getParentChildKey(restateVO.getOrgId(), restateVO.getClient(),
					restateDetailsVO.getPartNo()));
			stockDetailsVO.setBinClass(restateDetailsVO.getFromBinClass());
			stockDetailsVO.setCellType(restateDetailsVO.getFromCellType());
			stockDetailsVO.setQcFlag(restateDetailsVO.getQcFlag());
			stockDetailsVO.setStatus(restateVO.getTransferFromFlag());
			stockDetailsVO.setExpDate(restateDetailsVO.getExpDate());
			stockDetailsVO.setCore(restateDetailsVO.getFromCore());
			stockDetailsVO.setStockDate(LocalDate.now());
			stockDetailsVO.setSQty(restateDetailsVO.getToQty() * -1);
			stockDetailsRepo.save(stockDetailsVO);
		}
		for (StockRestateDetailsVO restateDetailsVO : restateDetailsVOs) {
			StockDetailsVO stockDetailsVO = new StockDetailsVO();
			stockDetailsVO.setOrgId(restateVO.getOrgId());
			stockDetailsVO.setBranch(restateVO.getBranch());
			stockDetailsVO.setBranchCode(restateVO.getBranchCode());
			stockDetailsVO.setWarehouse(restateVO.getWarehouse());
			stockDetailsVO.setCustomer(restateVO.getCustomer());
			stockDetailsVO.setClient(restateVO.getClient());
			stockDetailsVO.setClientCode(clientRepo.getClientCode(restateVO.getOrgId(), restateVO.getClient()));
			stockDetailsVO.setFinYear(restateVO.getFinYear());
			stockDetailsVO.setRefNo(restateVO.getDocId());
			stockDetailsVO.setRefDate(restateVO.getDocDate());
			stockDetailsVO.setSourceScreenCode(restateVO.getScreenCode());
			stockDetailsVO.setSourceScreenName(restateVO.getScreenName());
			stockDetailsVO.setCreatedBy(restateVO.getCreatedBy());
			stockDetailsVO.setUpdatedBy(restateVO.getUpdatedBy());
			stockDetailsVO.setSourceId(restateDetailsVO.getId());
			stockDetailsVO.setPartno(restateDetailsVO.getPartNo());
			stockDetailsVO.setPartDesc(restateDetailsVO.getPartDesc());
			stockDetailsVO.setSku(restateDetailsVO.getSku());
			stockDetailsVO.setSSku(restateDetailsVO.getSku());
			stockDetailsVO.setGrnNo(restateDetailsVO.getGrnNo());
			stockDetailsVO.setGrnDate(restateDetailsVO.getGrnDate());
			stockDetailsVO.setBatch(restateDetailsVO.getBatch());
			stockDetailsVO.setBatchDate(restateDetailsVO.getBatchDate());
			stockDetailsVO.setBin(restateDetailsVO.getToBin());
			stockDetailsVO.setBinType(restateDetailsVO.getToBinType());
			stockDetailsVO.setPcKey(materialRepo.getParentChildKey(restateVO.getOrgId(), restateVO.getClient(),
					restateDetailsVO.getPartNo()));
			stockDetailsVO.setBinClass(restateDetailsVO.getToBinClass());
			stockDetailsVO.setCellType(restateDetailsVO.getToCellType());
			if ("D".equals(restateVO.getTransferToFlag())) {
				stockDetailsVO.setQcFlag("F");
			}
			stockDetailsVO.setQcFlag(restateDetailsVO.getQcFlag());
			stockDetailsVO.setStatus(restateVO.getTransferToFlag());
			stockDetailsVO.setExpDate(restateDetailsVO.getExpDate());
			stockDetailsVO.setCore(restateDetailsVO.getToCore());
			stockDetailsVO.setStockDate(LocalDate.now());
			stockDetailsVO.setSQty(restateDetailsVO.getToQty());
			stockDetailsRepo.save(stockDetailsVO);
		}

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("restateVO", restateVO);
		return response;
	}

	@Override
	public List<Map<String, Object>> getfromBinForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag) {
		Set<Object[]> getFromBinDetails = stockRestateRepo.getFromBinDetails(orgId, branchCode, warehouse, client,
				transferFromFlag);
		return fromBinDetails(getFromBinDetails);
	}

	private List<Map<String, Object>> fromBinDetails(Set<Object[]> getFromBinDetails) {
		List<Map<String, Object>> binDetails = new ArrayList<>();
		for (Object[] fromBins : getFromBinDetails) {
			Map<String, Object> fromBin = new HashMap<>();
			fromBin.put("fromBinType", fromBins[0] != null ? fromBins[0].toString() : "");
			fromBin.put("fromBinClass", fromBins[1] != null ? fromBins[1].toString() : "");
			fromBin.put("fromCellType", fromBins[2] != null ? fromBins[2].toString() : "");
			fromBin.put("fromBin", fromBins[3] != null ? fromBins[3].toString() : "");
			fromBin.put("fromCore", fromBins[4] != null ? fromBins[4].toString() : "");
			binDetails.add(fromBin);
		}
		return binDetails;
	}

	@Override
	public List<Map<String, Object>> getPartNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String fromBin) {
		Set<Object[]> getPartNoDetails = stockRestateRepo.getPartNoDetails(orgId, branchCode, warehouse, client,
				transferFromFlag, fromBin);
		return partNoDetails(getPartNoDetails);
	}

	private List<Map<String, Object>> partNoDetails(Set<Object[]> getPartNoDetails) {
		List<Map<String, Object>> partNoDetails = new ArrayList<>();
		for (Object[] partNo : getPartNoDetails) {
			Map<String, Object> fromBin = new HashMap<>();
			fromBin.put("partNo", partNo[0] != null ? partNo[0].toString() : "");
			fromBin.put("partDesc", partNo[1] != null ? partNo[1].toString() : "");
			fromBin.put("sku", partNo[2] != null ? partNo[2].toString() : "");
			partNoDetails.add(fromBin);
		}
		return partNoDetails;
	}

	@Override
	public List<Map<String, Object>> getGrnNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String fromBin, String partNo) {
		Set<Object[]> getGrnNoDetails = stockRestateRepo.getGrnNoDetails(orgId, branchCode, warehouse, client,
				transferFromFlag, fromBin, partNo);
		return grnNoDetails(getGrnNoDetails);
	}

	private List<Map<String, Object>> grnNoDetails(Set<Object[]> getGrnNoDetails) {
		List<Map<String, Object>> grnNoDetails = new ArrayList<>();
		for (Object[] grnNo : getGrnNoDetails) {
			Map<String, Object> grnNoDetail = new HashMap<>();
			grnNoDetail.put("grnNo", grnNo[0] != null ? grnNo[0].toString() : "");
			grnNoDetail.put("grnDate", grnNo[1] != null ? grnNo[1].toString() : "");
			grnNoDetails.add(grnNoDetail);
		}
		return grnNoDetails;
	}

	@Override
	public List<Map<String, Object>> getBatchNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String fromBin, String partNo, String grnNo) {
		Set<Object[]> getBatchNoDetails = stockRestateRepo.getBatchNoDetails(orgId, branchCode, warehouse, client,
				transferFromFlag, fromBin, partNo, grnNo);
		return batchNoDetails(getBatchNoDetails);
	}

	private List<Map<String, Object>> batchNoDetails(Set<Object[]> getBatchNoDetails) {
		List<Map<String, Object>> batchNoDetails = new ArrayList<>();
		for (Object[] batchNo : getBatchNoDetails) {
			Map<String, Object> batchNoDetail = new HashMap<>();
			batchNoDetail.put("batchNo", batchNo[0] != null ? batchNo[0].toString() : "");
			batchNoDetail.put("batchDate", batchNo[1] != null ? batchNo[1].toString() : "");
			batchNoDetail.put("expDate", batchNo[2] != null ? batchNo[2].toString() : "");
			batchNoDetails.add(batchNoDetail);
		}
		return batchNoDetails;
	}

	@Override
	public int getFromQtyForStockRestate(Long orgId, String branchCode, String warehouse, String client,
			String transferFromFlag, String fromBin, String partNo, String grnNo, String batchNo) {
		int getFromQty = stockRestateRepo.getAvlQty(orgId, branchCode, warehouse, client, transferFromFlag, fromBin,
				partNo, grnNo, batchNo);
		return getFromQty;
	}

	@Override
	public List<Map<String, Object>> getToBinDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client) {

		Set<Object[]> getToBinDetails = stockRestateRepo.getToBinDetails(orgId, branchCode, warehouse, client);
		return toBinDetails(getToBinDetails);
	}

	private List<Map<String, Object>> toBinDetails(Set<Object[]> getToBinDetails) {
		List<Map<String, Object>> binDetails = new ArrayList<>();
		for (Object[] toBins : getToBinDetails) {
			Map<String, Object> fromBin = new HashMap<>();
			fromBin.put("toBin", toBins[0] != null ? toBins[0].toString() : "");
			fromBin.put("tobinType", toBins[1] != null ? toBins[1].toString() : "");
			fromBin.put("toBinClass", toBins[2] != null ? toBins[2].toString() : "");
			fromBin.put("toCellType", toBins[3] != null ? toBins[3].toString() : "");
			fromBin.put("toCore", toBins[4] != null ? toBins[4].toString() : "");
			binDetails.add(fromBin);
		}
		return binDetails;
	}

	@Override
	public List<Map<String, Object>> getFillGridDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String transferFromFlag, String transferToFlag) {

		Set<Object[]> getFillGridDetails = stockRestateRepo.getFillGridDetailsForRestate(orgId, branchCode, warehouse,
				client, transferFromFlag, transferToFlag);
		return fillGridDetails(getFillGridDetails);
	}

	private List<Map<String, Object>> fillGridDetails(Set<Object[]> getFillGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] details : getFillGridDetails) {
			Map<String, Object> fillDetails = new HashMap<>();
			fillDetails.put("partNo", details[0] != null ? details[0].toString() : "");
			fillDetails.put("partDesc", details[1] != null ? details[1].toString() : "");
			fillDetails.put("sku", details[2] != null ? details[2].toString() : "");
			fillDetails.put("grnNo", details[3] != null ? details[3].toString() : "");
			fillDetails.put("grnDate", details[4] != null ? details[4].toString() : "");
			fillDetails.put("batchNo", details[5] != null ? details[5].toString() : "");
			fillDetails.put("batchDate", details[6] != null ? details[6].toString() : "");
			fillDetails.put("expDate", details[7] != null ? details[7].toString() : "");
			fillDetails.put("fromBinType", details[8] != null ? details[8].toString() : "");
			fillDetails.put("fromBinClass", details[9] != null ? details[9].toString() : "");
			fillDetails.put("fromCellType", details[10] != null ? details[10].toString() : "");
			fillDetails.put("fromCore", details[11] != null ? details[11].toString() : "");
			fillDetails.put("fromBin", details[12] != null ? details[12].toString() : "");
			fillDetails.put("toBin", details[13] != null ? details[13].toString() : "");
			fillDetails.put("ToBinType", details[14] != null ? details[14].toString() : "");
			fillDetails.put("ToBinClass", details[15] != null ? details[15].toString() : "");
			fillDetails.put("ToCellType", details[16] != null ? details[16].toString() : "");
			fillDetails.put("ToCore", details[17] != null ? details[17].toString() : "");
			fillDetails.put("qcFlag", details[18] != null ? details[18].toString() : "");
			fillDetails.put("fromQty", details[19] != null ? Integer.parseInt(details[19].toString()) : 0);
			fillDetails.put("toQty", details[19] != null ? Integer.parseInt(details[19].toString()) : 0);

			gridDetails.add(fillDetails);
		}
		return gridDetails;
	}

}
