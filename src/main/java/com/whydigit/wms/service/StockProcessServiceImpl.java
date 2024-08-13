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
import com.whydigit.wms.dto.DeKittingChildDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.DeKittingParentDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.LocationMovementDetailsDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.SalesReturnDetailsDTO;
import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DeKittingChildVO;
import com.whydigit.wms.entity.DeKittingParentVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockDetailsVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CodeConversionDetailsRepo;
import com.whydigit.wms.repo.CodeConversionRepo;
import com.whydigit.wms.repo.DeKittingChildRepo;
import com.whydigit.wms.repo.DeKittingParentRepo;
import com.whydigit.wms.repo.DeKittingRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.LocationMovementDetailsRepo;
import com.whydigit.wms.repo.LocationMovementRepo;
import com.whydigit.wms.repo.SalesReturnDetailsRepo;
import com.whydigit.wms.repo.SalesReturnRepo;
import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class StockProcessServiceImpl implements StockProcessService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessServiceImpl.class);

	@Autowired
	CodeConversionRepo codeConcersionRepo;

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
//				stockDetailsVOFrom.setSourceScreenCode(codeConversionVO.getScreenCode());
//				stockDetailsVOFrom.setSourceScreenName(codeConversionVO.getScreenName());
//				stockDetailsVOFrom.setScree(codeConversionDetailsVO.getPartNo());

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
				stockDetailsVOTo.setRemarks(codeConversionDetailsVO.getRemarks());
				stockDetailsRepo.save(stockDetailsVOTo);
			}
		}
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

//	SalesReturn
	@Override
	public List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return salesReturnRepo.findAllSalesReturn(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public SalesReturnVO getSalesReturnById(Long id) {
		SalesReturnVO salesReturnVO = new SalesReturnVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  SalesReturn BY Id : {}", id);
			salesReturnVO= salesReturnRepo.findSalesReturnById(id);
		} else {
			LOGGER.info("failed Received SalesReturn For All Id.");
		}
		return salesReturnVO;

	}

	@Override
	public Map<String, Object> createUpdateSalesReturn(SalesReturnDTO salesReturnDTO) throws ApplicationException {

		SalesReturnVO salesReturnVO = new SalesReturnVO();
		String screenCode = "SR";
		String message;

		if (ObjectUtils.isNotEmpty(salesReturnDTO.getId())) {
			salesReturnVO = salesReturnRepo.findById(salesReturnDTO.getId())
					.orElseThrow(() -> new ApplicationException("SalesReturn not found"));

			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);
			message = "SalesReturn Updated Successfully";
		} else {
			salesReturnVO.setCreatedBy(salesReturnDTO.getCreatedBy());
			salesReturnVO.setUpdatedBy(salesReturnDTO.getCreatedBy());

			String salesReturnDocId = salesReturnRepo.getSalesReturnDocId(salesReturnDTO.getOrgId(),
					salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
					screenCode);
			salesReturnVO.setDocId(salesReturnDocId);
			createUpdateSalesReturnVOBySalesReturnDTO(salesReturnDTO, salesReturnVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(salesReturnDTO.getOrgId(),
							salesReturnDTO.getFinYear(), salesReturnDTO.getBranchCode(), salesReturnDTO.getClient(),
							screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "SalesReturn  Created Successfully";
		}

		salesReturnRepo.save(salesReturnVO);

		Map<String, Object> response = new HashMap<>();
		response.put("salesReturnVO", salesReturnVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateSalesReturnVOBySalesReturnDTO(SalesReturnDTO salesReturnDTO, SalesReturnVO salesReturnVO) {

		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setTransactionType(salesReturnDTO.getTransactionType());
		salesReturnVO.setEntryNo(salesReturnDTO.getEntryNo());
		salesReturnVO.setEntryDate(salesReturnDTO.getEntryDate());
		salesReturnVO.setPrDate(salesReturnDTO.getPrDate());
		salesReturnVO.setBONo(salesReturnDTO.getBONo());
		salesReturnVO.setBODate(salesReturnDTO.getBODate());
		salesReturnVO.setPRNo(salesReturnDTO.getPRNo());
		salesReturnVO.setBuyerName(salesReturnDTO.getBuyerName());
		salesReturnVO.setBuyerType(salesReturnDTO.getBuyerType());
		salesReturnVO.setSupplier(salesReturnDTO.getSupplier());
		salesReturnVO.setDriverName(salesReturnDTO.getDriverName());
		salesReturnVO.setCarrier(salesReturnDTO.getCarrier());
		salesReturnVO.setModeOfShipment(salesReturnDTO.getModeOfShipment());
		salesReturnVO.setVehicleType(salesReturnDTO.getVehicleType());
		salesReturnVO.setVehicleNo(salesReturnDTO.getVehicleNo());
		salesReturnVO.setContact(salesReturnDTO.getContact());
		salesReturnVO.setSecurityPersonName(salesReturnDTO.getSecurityPersonName());
		salesReturnVO.setTimeIn(salesReturnDTO.getTimeIn());
		salesReturnVO.setTimeOut(salesReturnDTO.getTimeOut());
		salesReturnVO.setBriefDescOfGoods(salesReturnDTO.getBriefDescOfGoods());
		salesReturnVO.setTotalReturnQty(salesReturnDTO.getTotalReturnQty());
		salesReturnVO.setOrgId(salesReturnDTO.getOrgId());
		salesReturnVO.setCustomer(salesReturnDTO.getCustomer());
		salesReturnVO.setClient(salesReturnDTO.getClient());
		salesReturnVO.setFinYear(salesReturnDTO.getFinYear());
		salesReturnVO.setBranch(salesReturnDTO.getBranch());
		salesReturnVO.setBranchCode(salesReturnDTO.getBranchCode());
		salesReturnVO.setWarehouse(salesReturnDTO.getWarehouse());

		if (ObjectUtils.isNotEmpty(salesReturnVO.getId())) {
			List<SalesReturnDetailsVO> salesReturnDetailsVO1 = salesReturnDetailsRepo
					.findBySalesReturnVO(salesReturnVO);
			salesReturnDetailsRepo.deleteAll(salesReturnDetailsVO1);
		}

		List<SalesReturnDetailsVO> salesReturnDetailsVOs = new ArrayList<>();
		for (SalesReturnDetailsDTO salesReturnDetailsDTO : salesReturnDTO.getSalesReturnDetailsDTO()) {

			SalesReturnDetailsVO salesReturnDetailsVO = new SalesReturnDetailsVO();
			salesReturnDetailsVO.setLRNo(salesReturnDetailsDTO.getLRNo());
			salesReturnDetailsVO.setInvoiceNo(salesReturnDetailsDTO.getInvoiceNo());
			salesReturnDetailsVO.setPartNo(salesReturnDetailsDTO.getPartNo());
			salesReturnDetailsVO.setPartDescripition(salesReturnDetailsDTO.getPartDescripition());
			salesReturnDetailsVO.setUnit(salesReturnDetailsDTO.getUnit());
			salesReturnDetailsVO.setPickQty(salesReturnDetailsDTO.getPickQty());
			salesReturnDetailsVO.setRetQty(salesReturnDetailsDTO.getRetQty());
			salesReturnDetailsVO.setDamageQty(salesReturnDetailsDTO.getDamageQty());
			salesReturnDetailsVO.setBatchNo(salesReturnDetailsDTO.getBatchNo());
			salesReturnDetailsVO.setBatchDate(salesReturnDetailsDTO.getBatchDate());
			salesReturnDetailsVO.setExpDate(salesReturnDetailsDTO.getExpDate());
			salesReturnDetailsVO.setNoOfPallet(salesReturnDetailsDTO.getNoOfPallet());
			salesReturnDetailsVO.setPalletQty(salesReturnDetailsDTO.getPalletQty());
			salesReturnDetailsVO.setWeight(salesReturnDetailsDTO.getWeight());
			salesReturnDetailsVO.setRate(salesReturnDetailsDTO.getRate());
			salesReturnDetailsVO.setStatus(salesReturnDetailsDTO.getStatus());
			salesReturnDetailsVO.setAmount(salesReturnDetailsDTO.getAmount());
			salesReturnDetailsVO.setInsAmt(salesReturnDetailsDTO.getInsAmt());
			salesReturnDetailsVO.setRemarks(salesReturnDetailsDTO.getRemarks());
			salesReturnDetailsVO.setQcFlag(salesReturnDetailsDTO.isQcFlag());
			salesReturnDetailsVO.setSalesReturnVO(salesReturnVO);

			salesReturnDetailsVOs.add(salesReturnDetailsVO);
		}
		salesReturnVO.setSalesReturnDetailsVO(salesReturnDetailsVOs);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin) {

		Set<Object[]> result = codeConcersionRepo.findPartNoAndPartDescFromStockForCodeConversion(orgId, finYear,
				branch, branchCode, client, bin);
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
	public List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId,
			String branchCode) {

		Set<Object[]> result = salesReturnRepo.findSalesReturnFillGridDetails(docId, client, orgId, branchCode);
		return getResult(result);
	}

	private List<Map<String, Object>> getResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partCode", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("pickQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "SR";
		String result = salesReturnRepo.getSalesReturnDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

//	LocationMovement
	@Override
	public List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return locationMovementRepo.findAllLocationMovement(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public LocationMovementVO getLocationMovementById(Long id) {
		LocationMovementVO locationMovementVO = new LocationMovementVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received  LocationMovement BY Id : {}", id);
			locationMovementVO= locationMovementRepo.findLocationMovementById(id);
		} else {
			LOGGER.info("failed Received LocationMovement For All Id.");
		}
		return locationMovementVO;

	}

	@Override
	public Map<String, Object> createUpdateLocationMovement(LocationMovementDTO locationMovementDTO)
			throws ApplicationException {

		LocationMovementVO locationMovementVO = new LocationMovementVO();
		String screenCode = "LM";
		String message;

		if (ObjectUtils.isNotEmpty(locationMovementDTO.getId())) {
			locationMovementVO = locationMovementRepo.findById(locationMovementDTO.getId())
					.orElseThrow(() -> new ApplicationException("LocationMovement not found"));

			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());
			createUpdateLocationMovementVOByLocationMovementDTO(locationMovementDTO, locationMovementVO);
			message = "LocationMovement Updated Successfully";
		} else {
			locationMovementVO.setCreatedBy(locationMovementDTO.getCreatedBy());
			locationMovementVO.setUpdatedBy(locationMovementDTO.getCreatedBy());

			String locationMovementDocId = locationMovementRepo.getLocationMovementDocId(locationMovementDTO.getOrgId(),
					locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
					locationMovementDTO.getClient(), screenCode);
			locationMovementVO.setDocId(locationMovementDocId);
			createUpdateLocationMovementVOByLocationMovementDTO(locationMovementDTO, locationMovementVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(locationMovementDTO.getOrgId(),
							locationMovementDTO.getFinYear(), locationMovementDTO.getBranchCode(),
							locationMovementDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "LocationMovement Created Successfully";
		}
		LocationMovementVO savedLocationMovementVO = locationMovementRepo.save(locationMovementVO);

		List<LocationMovementDetailsVO> locationMovementDetailsVOLists = savedLocationMovementVO
				.getLocationMovementDetailsVO();
		if (locationMovementDetailsVOLists != null && !locationMovementDetailsVOLists.isEmpty()) {
			for (LocationMovementDetailsVO detailsVO : locationMovementDetailsVOLists) {
				// Create StockDetails for fromBin with negative quantity
				StockDetailsVO stockDetailsVOFrom = new StockDetailsVO();
				stockDetailsVOFrom.setBin(detailsVO.getBin());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setCellType(detailsVO.getCellType());
				stockDetailsVOFrom.setClientCode(detailsVO.getClientCode());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setPcKey(detailsVO.getPcKey());
				stockDetailsVOFrom.setSSku(detailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDescripition());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGRNNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setQcFlag(detailsVO.isQcFlag());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setStatus(detailsVO.getStatus());
				stockDetailsVOFrom.setSQty(detailsVO.getToQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOFrom.setSku(savedLocationMovementVO.getSku());
				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
				stockDetailsVOTo.setBin(detailsVO.getToBin());
				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
				stockDetailsVOTo.setBinClass(detailsVO.getBinClass());
				stockDetailsVOTo.setBinType(detailsVO.getBinType());
				stockDetailsVOTo.setQcFlag(detailsVO.isQcFlag());
				stockDetailsVOTo.setPartDesc(detailsVO.getPartDescripition());
				stockDetailsVOTo.setGrnNo(detailsVO.getGRNNo());
				stockDetailsVOTo.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
				stockDetailsVOTo.setCellType(detailsVO.getCellType());
				stockDetailsVOTo.setClientCode(detailsVO.getClientCode());
				stockDetailsVOTo.setCore(detailsVO.getCore());
				stockDetailsVOTo.setPcKey(detailsVO.getPcKey());
				stockDetailsVOTo.setSSku(detailsVO.getSsku());
				stockDetailsVOTo.setStockDate(detailsVO.getStockDate());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setStatus(detailsVO.getStatus());
				stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOTo.setSku(savedLocationMovementVO.getSku());
				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOTo);
			}
		}

		Map<String, Object> response = new HashMap<>();
		response.put("locationMovementVO", locationMovementVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateLocationMovementVOByLocationMovementDTO(LocationMovementDTO locationMovementDTO,
			LocationMovementVO locationMovementVO) {

		locationMovementVO.setOrgId(locationMovementDTO.getOrgId());
		locationMovementVO.setType(locationMovementDTO.getType());
		locationMovementVO.setCustomer(locationMovementDTO.getCustomer());
		locationMovementVO.setClient(locationMovementDTO.getClient());
		locationMovementVO.setFinYear(locationMovementDTO.getFinYear());
		locationMovementVO.setBranchCode(locationMovementDTO.getBranchCode());
		locationMovementVO.setBranch(locationMovementDTO.getBranch());
		locationMovementVO.setWarehouse(locationMovementDTO.getWarehouse());
		locationMovementVO.setSku(locationMovementDTO.getSku());
		locationMovementVO.setCore(locationMovementDTO.getCore());

		if (ObjectUtils.isNotEmpty(locationMovementVO.getId())) {
			List<LocationMovementDetailsVO> locationMovementDetailsVO1 = locationMovementDetailsRepo
					.findByLocationMovementVO(locationMovementVO);
			locationMovementDetailsRepo.deleteAll(locationMovementDetailsVO1);
		}

		List<LocationMovementDetailsVO> locationMovementDetailsVOs = new ArrayList<>();
		for (LocationMovementDetailsDTO locationMovementDetailsDTO : locationMovementDTO
				.getLocationMovementDetailsDTO()) {

			LocationMovementDetailsVO locationMovementDetailsVO = new LocationMovementDetailsVO();
			locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getBin());
			locationMovementDetailsVO.setPartNo(locationMovementDetailsDTO.getPartNo());
			locationMovementDetailsVO.setPartDescripition(locationMovementDetailsDTO.getPartDescripition());
			locationMovementDetailsVO.setGRNNo(locationMovementDetailsDTO.getGRNNo());
			locationMovementDetailsVO.setBatchNo(locationMovementDetailsDTO.getBatchNo());
			locationMovementDetailsVO.setBatchDate(locationMovementDetailsDTO.getBatchDate());
			locationMovementDetailsVO.setLotNo(locationMovementDetailsDTO.getLotNo());
			locationMovementDetailsVO.setToBin(locationMovementDetailsDTO.getToBin());
			locationMovementDetailsVO.setFromQty(locationMovementDetailsDTO.getFromQty());
			locationMovementDetailsVO.setToQty(locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO
					.setRemainingQty(locationMovementDetailsDTO.getFromQty() - locationMovementDetailsDTO.getToQty());
			locationMovementDetailsVO.setGrnDate(locationMovementDetailsDTO.getGrnDate());
			locationMovementDetailsVO.setSku(locationMovementDetailsDTO.getSku());
			
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setCellType(locationMovementDetailsDTO.getCellType());
			locationMovementDetailsVO.setClientCode(locationMovementDetailsDTO.getClientCode());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setPcKey(locationMovementDetailsDTO.getPcKey());
			locationMovementDetailsVO.setSsku(locationMovementDetailsDTO.getSsku());
			locationMovementDetailsVO.setStockDate(locationMovementDetailsDTO.getStockDate());
			locationMovementDetailsVO.setStockDate(locationMovementDetailsDTO.getStockDate());
			
			locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getBinType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
			locationMovementDetailsVO.setStatus(locationMovementDetailsDTO.getStatus());
			locationMovementDetailsVO.setQcFlag(locationMovementDetailsDTO.isQcFlag());
			locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);

			locationMovementDetailsVOs.add(locationMovementDetailsVO);
		}
		locationMovementVO.setLocationMovementDetailsVO(locationMovementDetailsVOs);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = locationMovementRepo.findBinFromStockForLocationMovement(orgId, finYear, branch,
				branchCode, client);
		return getMovementResult(result);
	}

	private List<Map<String, Object>> getMovementResult(Set<Object[]> result) {
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
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin) {

		Set<Object[]> result = locationMovementRepo.findPartNoAndPartDescFromStockForLocationMovement(orgId, finYear,
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
	public List<Map<String, Object>> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(
			Long orgId, String finYear, String branch, String branchCode, String client, String bin, String partNo,
			String partDesc, String sku) {

		Set<Object[]> result = codeConcersionRepo
				.findGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(orgId, finYear, branch,
						branchCode, client, bin, partNo, partDesc, sku);
		return getGrnCodeConversionResult(result);
	}

	private List<Map<String, Object>> getGrnCodeConversionResult(Set<Object[]> result) {
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

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String finYear, String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
				orgId, finYear, branch, branchCode, client, bin, partNo, partDesc, sku);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchNo", fs[1] != null ? fs[1].toString() : "");
			part.put("batchDate", fs[2] != null ? fs[2].toString() : "");
			part.put("LotNo", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Transactional
	public List<Map<String, Object>> getAllForLocationMovementDetailsFillGrid(Long orgId,
			 String branch, String branchCode, String client) {

		Set<Object[]> result = locationMovementRepo.findAllForLocationMovementDetailsFillGrid(
				orgId, branch, branchCode, client);
		return getFillGridResult(result);
	}

	private List<Map<String, Object>> getFillGridResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("bin", fs[0] != null ? fs[0].toString() : "");
			part.put("binClass", fs[1] != null ? fs[1].toString() : "");
			part.put("cellType", fs[2] != null ? fs[2].toString() : "");
			part.put("clientCode", fs[3] != null ? fs[3].toString() : "");
			part.put("core", fs[4] != null ? fs[4].toString() : "");
			part.put("expDate", fs[5] != null ? fs[5].toString() : "");
			part.put("pcKet", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockDate", fs[8] != null ? fs[8].toString() : "");
			part.put("partNo", fs[9] != null ? fs[9].toString() : "");
			part.put("partDesc", fs[10] != null ? fs[10].toString() : "");
			part.put("sku", fs[11] != null ? fs[11].toString() : "");
			part.put("grnNo", fs[12] != null ? fs[12].toString() : "");
			part.put("batchNo", fs[13] != null ? fs[13].toString() : "");
			part.put("batchDate", fs[14] != null ? fs[14].toString() : "");
			part.put("LotNo", fs[15] != null ? fs[15].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode,
			String client) {
		String screenCode = "LM";
		String result = locationMovementRepo.getLocationMovementDocId(orgId, finYear, branchCode, client, screenCode);
		return result;
	}
	
	@Transactional
	public List<Map<String, Object>> getAvlQtyFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client, String bin, String partDesc, String sku, String partNo, String grnNo,
			String lotNo) {

		Set<Object[]> result = locationMovementRepo.findAvlQtyFromStockForLocationMovement(
				orgId, finYear, branch, branchCode, client, bin, partDesc, sku,partNo,grnNo,lotNo);
		return getAvlQtyLMResult(result);
	}

	private List<Map<String, Object>> getAvlQtyLMResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("avlQty", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

//	DeKitting
	@Override
	public List<DeKittingVO> getAllDeKitting(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse) {
		return deKittingRepo.findAllDeKitting(orgId, finYear, branch, branchCode, client, warehouse);
	}

	@Override
	public DeKittingVO getDeKittingById(Long id) {
		DeKittingVO deKittingVO = new DeKittingVO();
		if (ObjectUtils.isNotEmpty(id)) {
			LOGGER.info("Successfully Received DeKittingBY Id : {}", id);
			deKittingVO= deKittingRepo.findDeKittingById(id);
		} else {
			LOGGER.info("failed Received DeKitting For All Id.");
		}
		return deKittingVO;

	}

	@Override
	public Map<String, Object> createUpdateDeKitting(DeKittingDTO deKittingDTO) throws ApplicationException {

		DeKittingVO deKittingVO = new DeKittingVO();
		String screenCode = "DK";
		String message;

		if (ObjectUtils.isNotEmpty(deKittingDTO.getId())) {
			deKittingVO = deKittingRepo.findById(deKittingDTO.getId())
					.orElseThrow(() -> new ApplicationException("DeKitting not found"));

			deKittingVO.setUpdatedBy(deKittingDTO.getCreatedBy());
			createUpdateDeKittingVOByDeKittingDTO(deKittingDTO, deKittingVO);
			message = "DeKitting Updated Successfully";
		} else {
			deKittingVO.setCreatedBy(deKittingDTO.getCreatedBy());
			deKittingVO.setUpdatedBy(deKittingDTO.getCreatedBy());

			String deKittingDocId = deKittingRepo.getDeKittingDocId(deKittingDTO.getOrgId(), deKittingDTO.getFinYear(),
					deKittingDTO.getBranchCode(), deKittingDTO.getClient(), screenCode);
			deKittingVO.setDocId(deKittingDocId);
			createUpdateDeKittingVOByDeKittingDTO(deKittingDTO, deKittingVO);

			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
					.findByBranchAndClientAndFinYearAndScreenCode(deKittingDTO.getOrgId(), deKittingDTO.getFinYear(),
							deKittingDTO.getBranchCode(), deKittingDTO.getClient(), screenCode);
			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
			message = "DeKitting Created Successfully";
		}
		DeKittingVO savedDeKittingVO = deKittingRepo.save(deKittingVO);

		List<DeKittingParentVO> deKittingParentVOLists = savedDeKittingVO.getDeKittingParentVO();
		if (deKittingParentVOLists != null && !deKittingParentVOLists.isEmpty()) {
			for (DeKittingParentVO parentDetailsVO : deKittingParentVOLists) {

				StockDetailsVO stockDetailsVOPar = new StockDetailsVO();
				stockDetailsVOPar.setBin(parentDetailsVO.getBin());
				stockDetailsVOPar.setPartno(parentDetailsVO.getPartNo());
				stockDetailsVOPar.setPartDesc(parentDetailsVO.getPartDesc());
				stockDetailsVOPar.setBatch(parentDetailsVO.getBatchNo());
				stockDetailsVOPar.setLotNo(parentDetailsVO.getLotNo());
				stockDetailsVOPar.setSku(parentDetailsVO.getSku());
				stockDetailsVOPar.setGrnNo(parentDetailsVO.getGrnNo());
				stockDetailsVOPar.setGrnDate(parentDetailsVO.getGrnDate());
				stockDetailsVOPar.setExpDate(parentDetailsVO.getExpDate());
				stockDetailsVOPar.setStatus(parentDetailsVO.getStatus());
				
				stockDetailsVOPar.setBinClass(parentDetailsVO.getBinClass());
				stockDetailsVOPar.setCellType(parentDetailsVO.getCellType());
				stockDetailsVOPar.setClientCode(parentDetailsVO.getClientCode());
				stockDetailsVOPar.setCore(parentDetailsVO.getCore());
				stockDetailsVOPar.setPcKey(parentDetailsVO.getPcKey());
				stockDetailsVOPar.setSSku(parentDetailsVO.getSsku());
				stockDetailsVOPar.setStockDate(parentDetailsVO.getStockDate());
				stockDetailsVOPar.setStockDate(parentDetailsVO.getStockDate());
				
				stockDetailsVOPar.setSQty(parentDetailsVO.getQty() * -1);
				stockDetailsVOPar.setStatus(parentDetailsVO.getStatus());
//				dekitting->stock
				stockDetailsVOPar.setRefNo(savedDeKittingVO.getDocId());
				stockDetailsVOPar.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOPar.setOrgId(savedDeKittingVO.getOrgId());
				stockDetailsVOPar.setCustomer(savedDeKittingVO.getCustomer());
				stockDetailsVOPar.setClient(savedDeKittingVO.getClient());
				stockDetailsVOPar.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOPar.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOPar.setCreatedBy(savedDeKittingVO.getUpdatedBy());
				stockDetailsVOPar.setBranchCode(savedDeKittingVO.getBranchCode());
				stockDetailsVOPar.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOPar.setClient(savedDeKittingVO.getClient());
				stockDetailsVOPar.setWarehouse(savedDeKittingVO.getWarehouse());
				stockDetailsVOPar.setFinYear(savedDeKittingVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOPar);
			}
		}

		List<DeKittingChildVO> deKittingChildVOLists = savedDeKittingVO.getDeKittingChildVO();
		if (deKittingChildVOLists != null && !deKittingChildVOLists.isEmpty()) {
			for (DeKittingChildVO childDetailsVO : deKittingChildVOLists) {
				// child -> stock
				StockDetailsVO stockDetailsVOChi = new StockDetailsVO();
				stockDetailsVOChi.setBin(childDetailsVO.getBin());
				stockDetailsVOChi.setPartno(childDetailsVO.getPartNo());
				stockDetailsVOChi.setPartDesc(childDetailsVO.getPartDesc());
				stockDetailsVOChi.setBatch(childDetailsVO.getBatchNo());
				stockDetailsVOChi.setLotNo(childDetailsVO.getLotNo());
				stockDetailsVOChi.setSku(childDetailsVO.getSku());
				stockDetailsVOChi.setGrnNo(childDetailsVO.getGrnNo());
				stockDetailsVOChi.setGrnDate(childDetailsVO.getGrnDate());
				stockDetailsVOChi.setExpDate(childDetailsVO.getExpDate());
				stockDetailsVOChi.setStatus(childDetailsVO.getStatus());
				stockDetailsVOChi.setSQty(childDetailsVO.getQty());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setBin(childDetailsVO.getBin());
				stockDetailsVOChi.setStatus(childDetailsVO.getStatus());
				
				stockDetailsVOChi.setBinClass(childDetailsVO.getBinClass());
				stockDetailsVOChi.setCellType(childDetailsVO.getCellType());
				stockDetailsVOChi.setClientCode(childDetailsVO.getClientCode());
				stockDetailsVOChi.setCore(childDetailsVO.getCore());
				stockDetailsVOChi.setPcKey(childDetailsVO.getPcKey());
				stockDetailsVOChi.setSSku(childDetailsVO.getSsku());
				stockDetailsVOChi.setStockDate(childDetailsVO.getStockDate());
				stockDetailsVOChi.setStockDate(childDetailsVO.getStockDate());
				

//				dekitting->stock
				stockDetailsVOChi.setRefNo(savedDeKittingVO.getDocId());
				stockDetailsVOChi.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOChi.setOrgId(savedDeKittingVO.getOrgId());
				stockDetailsVOChi.setCustomer(savedDeKittingVO.getCustomer());
				stockDetailsVOChi.setClient(savedDeKittingVO.getClient());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setRefDate(savedDeKittingVO.getDocDate());
				stockDetailsVOChi.setCreatedBy(savedDeKittingVO.getUpdatedBy());
				stockDetailsVOChi.setBranchCode(savedDeKittingVO.getBranchCode());
				stockDetailsVOChi.setBranch(savedDeKittingVO.getBranch());
				stockDetailsVOChi.setClient(savedDeKittingVO.getClient());
				stockDetailsVOChi.setWarehouse(savedDeKittingVO.getWarehouse());
				stockDetailsVOChi.setFinYear(savedDeKittingVO.getFinYear());
				stockDetailsRepo.save(stockDetailsVOChi);
			}
		}
		Map<String, Object> response = new HashMap<>();
		response.put("deKittingVO", deKittingVO);
		response.put("message", message);
		return response;
	}

	private void createUpdateDeKittingVOByDeKittingDTO(DeKittingDTO deKittingDTO, DeKittingVO deKittingVO) {

		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setTransactionType(deKittingDTO.getTransactionType());
		deKittingVO.setClient(deKittingDTO.getClient());
		deKittingVO.setOrgId(deKittingDTO.getOrgId());
		deKittingVO.setCustomer(deKittingDTO.getCustomer());
		deKittingVO.setFinYear(deKittingDTO.getFinYear());
		deKittingVO.setBranch(deKittingDTO.getBranch());
		deKittingVO.setBranchCode(deKittingDTO.getBranchCode());
		deKittingVO.setWarehouse(deKittingDTO.getWarehouse());
		deKittingVO.setActive(deKittingDTO.isActive());

		if (ObjectUtils.isNotEmpty(deKittingVO.getId())) {
			List<DeKittingParentVO> deKittingParentVO1 = deKittingParentRepo.findByDeKittingVO(deKittingVO);
			deKittingParentRepo.deleteAll(deKittingParentVO1);
		}
		if (ObjectUtils.isNotEmpty(deKittingVO.getId())) {
			List<DeKittingChildVO> deKittingChildVO1 = deKittingChildRepo.findByDeKittingVO(deKittingVO);
			deKittingChildRepo.deleteAll(deKittingChildVO1);
		}

		List<DeKittingParentVO> deKittingParentVOs = new ArrayList<>();
		for (DeKittingParentDTO deKittingParentDTO : deKittingDTO.getDeKittingParentDTO()) {

			DeKittingParentVO deKittingParentVO = new DeKittingParentVO();
			deKittingParentVO.setBin(deKittingParentDTO.getBin());
			deKittingParentVO.setPartNo(deKittingParentDTO.getPartNo());
			deKittingParentVO.setPartDesc(deKittingParentDTO.getPartDesc());
			deKittingParentVO.setGrnNo(deKittingParentDTO.getGrnNo());
			deKittingParentVO.setBatchNo(deKittingParentDTO.getBatchNo());
			deKittingParentVO.setLotNo(deKittingParentDTO.getLotNo());
			deKittingParentVO.setSku(deKittingParentDTO.getSku());
			deKittingParentVO.setGrnDate(deKittingParentDTO.getGrnDate());
			deKittingParentVO.setExpDate(deKittingParentDTO.getExpDate());
			deKittingParentVO.setAvlQty(deKittingParentDTO.getAvlQty());
			deKittingParentVO.setQty(deKittingParentDTO.getQty());
			deKittingParentVO.setUnitRate(deKittingParentDTO.getUnitRate());
			deKittingParentVO.setStatus(deKittingParentDTO.getStatus());
			deKittingParentVO.setAmount(deKittingParentDTO.getAmount());
			deKittingParentVO.setQcFlag(deKittingParentDTO.isQcFlag());
			
			deKittingParentVO.setBinClass(deKittingParentDTO.getBinClass());
			deKittingParentVO.setCellType(deKittingParentDTO.getCellType());
			deKittingParentVO.setClientCode(deKittingParentDTO.getClientCode());
			deKittingParentVO.setCore(deKittingParentDTO.getCore());
			deKittingParentVO.setPcKey(deKittingParentDTO.getPcKey());
			deKittingParentVO.setSsku(deKittingParentDTO.getSsku());
			deKittingParentVO.setStockDate(deKittingParentDTO.getStockDate());
			deKittingParentVO.setStockDate(deKittingParentDTO.getStockDate());
			
			deKittingParentVO.setDeKittingVO(deKittingVO);

			deKittingParentVOs.add(deKittingParentVO);
		}
		deKittingVO.setDeKittingParentVO(deKittingParentVOs);

		List<DeKittingChildVO> deKittingChildVOs = new ArrayList<>();
		for (DeKittingChildDTO deKittingChildDTO : deKittingDTO.getDeKittingChildDTO()) {

			DeKittingChildVO deKittingChildVO = new DeKittingChildVO();
			deKittingChildVO.setBin(deKittingChildDTO.getBin());
			deKittingChildVO.setPartNo(deKittingChildDTO.getPartNo());
			deKittingChildVO.setPartDesc(deKittingChildDTO.getPartDesc());
			deKittingChildVO.setGrnNo(deKittingChildDTO.getGrnNo());
			deKittingChildVO.setBatchNo(deKittingChildDTO.getBatchNo());
			deKittingChildVO.setLotNo(deKittingChildDTO.getLotNo());
			deKittingChildVO.setSku(deKittingChildDTO.getSku());
			deKittingChildVO.setGrnDate(deKittingChildDTO.getGrnDate());
			deKittingChildVO.setExpDate(deKittingChildDTO.getExpDate());
			deKittingChildVO.setQty(deKittingChildDTO.getQty());
			deKittingChildVO.setUnitRate(deKittingChildDTO.getUnitRate());
			deKittingChildVO.setStatus(deKittingChildDTO.getStatus());
			deKittingChildVO.setAmount(deKittingChildDTO.getAmount());
			deKittingChildVO.setQcFlag(deKittingChildDTO.isQcFlag());
			deKittingChildVO.setDeKittingVO(deKittingVO);
			
			deKittingChildVO.setBinClass(deKittingChildDTO.getBinClass());
			deKittingChildVO.setCellType(deKittingChildDTO.getCellType());
			deKittingChildVO.setClientCode(deKittingChildDTO.getClientCode());
			deKittingChildVO.setCore(deKittingChildDTO.getCore());
			deKittingChildVO.setPcKey(deKittingChildDTO.getPcKey());
			deKittingChildVO.setSsku(deKittingChildDTO.getSsku());
			deKittingChildVO.setStockDate(deKittingChildDTO.getStockDate());
			deKittingChildVO.setStockDate(deKittingChildDTO.getStockDate());

			deKittingChildVOs.add(deKittingChildVO);
		}
		deKittingVO.setDeKittingChildVO(deKittingChildVOs);
	}

	@Override
	@Transactional
	public String getDeKittingDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
		String ScreenCode = "DK";
		String result = deKittingRepo.getDeKittingDocId(orgId, finYear, branchCode, client, ScreenCode);
		return result;
	}

	//PARENT
	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoFromStockForDeKittingParent(orgId, finYear, branch, branchCode,
				client);
		return getPartNoResult(result);
	}

	private List<Map<String, Object>> getPartNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = codeConcersionRepo.findBinFromStockForCodeConversion(orgId, finYear, branch, branchCode,
				client);
		return getBinCodeConversionResult(result);
	}

	private List<Map<String, Object>> getBinCodeConversionResult(Set<Object[]> result) {
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

	public List<Map<String, Object>> getPartDescAndSkuFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client, String partNo) {

		Set<Object[]> result = deKittingRepo.findPartDescAndSkuFromStockForDeKittingParent(orgId, finYear, branch, branchCode,
				client, partNo);
		return getPartDescResult(result);
	}

	private List<Map<String, Object>> getPartDescResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partDesc", fs[0] != null ? fs[0].toString() : "");
			part.put("sku", fs[1] != null ? fs[1].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findBinFromStockForDeKittingParent(orgId, finYear, branch, branchCode, client);
		return getDBinResult(result);
	}

	private List<Map<String, Object>> getDBinResult(Set<Object[]> result) {
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

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(Long orgId,
			String finYear, String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku) {

		Set<Object[]> result = deKittingRepo.findGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(orgId,
				finYear, branch, branchCode, client, bin, partNo, partDesc, sku);
		return getGrnNoResult(result);
	}

	private List<Map<String, Object>> getGrnNoResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("batchNo", fs[1] != null ? fs[1].toString() : "");
			part.put("batchDate", fs[2] != null ? fs[2].toString() : "");
			part.put("lotNo", fs[3] != null ? fs[3].toString() : "");
			part.put("expDate", fs[4] != null ? fs[4].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	@Override
	@Transactional
	public List<Map<String, Object>> getAvlQtyFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client, String bin, String partDesc, String sku, String partNo, String grnNo,
			String lotNo) {

		Set<Object[]> result = deKittingRepo.findAvlQtyFromStockForDeKittingParent(
				orgId, finYear, branch, branchCode, client, bin, partDesc, sku,partNo,grnNo,lotNo);
		return getAvlQtyResult(result);
	}

	private List<Map<String, Object>> getAvlQtyResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("avlQty", fs[0] != null ? fs[0].toString() : "");
			details1.add(part);
		}
		return details1;
	}
	
	//CHILD
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(
				orgId, branch, branchCode, client);
		return getChildResult(result);
	}

	private List<Map<String, Object>> getChildResult(Set<Object[]> result) {
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
}
