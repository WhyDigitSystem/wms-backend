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
			salesReturnVO = salesReturnRepo.findSalesReturnById(id);
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
		salesReturnVO.setFreeze(salesReturnDTO.getFreeze());
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
			locationMovementVO = locationMovementRepo.findLocationMovementById(id);
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
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setPcKey(detailsVO.getPcKey());
				stockDetailsVOFrom.setSSku(detailsVO.getSsku());
				stockDetailsVOFrom.setStockDate(detailsVO.getStockDate());
				stockDetailsVOFrom.setPartno(detailsVO.getPartNo());
				stockDetailsVOFrom.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOFrom.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOFrom.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOFrom.setBatch(detailsVO.getBatchNo());
				stockDetailsVOFrom.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOFrom.setSku(detailsVO.getSku());
				stockDetailsVOFrom.setCore(detailsVO.getCore());
				stockDetailsVOFrom.setLotNo(detailsVO.getLotNo());
				stockDetailsVOFrom.setExpDate(detailsVO.getExpDate());
				stockDetailsVOFrom.setSQty(detailsVO.getToQty() * -1); // Negative quantity
				stockDetailsVOFrom.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOFrom.setBinClass(detailsVO.getBinClass());
				stockDetailsVOFrom.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOFrom.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOFrom.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOFrom.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOFrom.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOFrom.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOFrom.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOFrom.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOFrom.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOFrom.setFinYear(savedLocationMovementVO.getFinYear());

				if ("Defective".equals(detailsVO.getBin())) {
					stockDetailsVOFrom.setQcFlag("F");
					stockDetailsVOFrom.setStatus("D");
					stockDetailsVOFrom.setBinType("DAMAGE");
				} else {

					stockDetailsVOFrom.setQcFlag("T");
					stockDetailsVOFrom.setStatus("R");
					stockDetailsVOFrom.setBinType(detailsVO.getBinType());
				}
//				if (detailsVO.getFromQty() > detailsVO.getToQty()) {
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}else {
//					throw new ApplicationException("The ToQty is greator than avlQty");
//					}
				stockDetailsRepo.save(stockDetailsVOFrom);

				// Create StockDetails for toBin with positive quantity
				StockDetailsVO stockDetailsVOTo = new StockDetailsVO();
				stockDetailsVOTo.setBin(detailsVO.getToBin());
				stockDetailsVOTo.setPartno(detailsVO.getPartNo());
				stockDetailsVOTo.setBinClass(detailsVO.getToBinClass());
				stockDetailsVOTo.setBinType(detailsVO.getToBinType());
				stockDetailsVOTo.setCellType(detailsVO.getToCellType());
				stockDetailsVOTo.setQcFlag(detailsVO.getQcFlag());
				stockDetailsVOTo.setPartDesc(detailsVO.getPartDesc());
				stockDetailsVOTo.setGrnNo(detailsVO.getGrnNo());
				stockDetailsVOTo.setGrnDate(detailsVO.getGrnDate());
				stockDetailsVOTo.setBatch(detailsVO.getBatchNo());
				stockDetailsVOTo.setCellType(detailsVO.getCellType());
				stockDetailsVOTo.setPcKey(detailsVO.getPcKey());
				stockDetailsVOTo.setSSku(detailsVO.getSsku());
				stockDetailsVOTo.setSku(detailsVO.getSku());
				stockDetailsVOTo.setStockDate(detailsVO.getStockDate());
				stockDetailsVOTo.setBatchDate(detailsVO.getBatchDate());
				stockDetailsVOTo.setLotNo(detailsVO.getLotNo());
				stockDetailsVOTo.setExpDate(detailsVO.getExpDate());
				stockDetailsVOTo.setCore(detailsVO.getCore());
				stockDetailsVOTo.setStatus(detailsVO.getStatus());
				stockDetailsVOTo.setSQty(detailsVO.getToQty()); // Positive quantity
				stockDetailsVOTo.setRefNo(savedLocationMovementVO.getDocId());
				stockDetailsVOTo.setOrgId(savedLocationMovementVO.getOrgId());
				stockDetailsVOTo.setRefDate(savedLocationMovementVO.getDocDate());
				stockDetailsVOTo.setCreatedBy(savedLocationMovementVO.getUpdatedBy());
				stockDetailsVOTo.setBranchCode(savedLocationMovementVO.getBranchCode());
				stockDetailsVOTo.setBranch(savedLocationMovementVO.getBranch());
				stockDetailsVOTo.setClient(savedLocationMovementVO.getClient());
				stockDetailsVOTo.setPcKey(materialRepo.getParentChildKey(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient(), detailsVO.getPartNo()));
				stockDetailsVOTo.setClientCode(clientRepo.getClientCode(savedLocationMovementVO.getOrgId(),
						savedLocationMovementVO.getClient()));
				stockDetailsVOTo.setWarehouse(savedLocationMovementVO.getWarehouse());
				stockDetailsVOTo.setFinYear(savedLocationMovementVO.getFinYear());
				if ("Defective".equals(detailsVO.getBin())) {
					stockDetailsVOTo.setQcFlag("F");
					stockDetailsVOTo.setStatus("D");
					stockDetailsVOTo.setBinType("DAMAGE");
				} else {

					stockDetailsVOTo.setQcFlag("T");
					stockDetailsVOTo.setStatus("R");
					stockDetailsVOTo.setBinType(detailsVO.getBinType());
				}
//				if (detailsVO.getFromQty() > detailsVO.getToQty()) {
//					stockDetailsRepo.save(stockDetailsVOFrom);
//				}else {
//					throw new ApplicationException("The ToQty is greator than avlQty");
//					}
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
		locationMovementVO.setFinYear(locationMovementDTO.getFinYear());
		locationMovementVO.setClient(locationMovementDTO.getClient());
		locationMovementVO.setBranchCode(locationMovementDTO.getBranchCode());
		locationMovementVO.setBranch(locationMovementDTO.getBranch());
		locationMovementVO.setWarehouse(locationMovementDTO.getWarehouse());
		locationMovementVO.setMovedQty(locationMovementDTO.getMovedQty());

		if (ObjectUtils.isNotEmpty(locationMovementVO.getId())) {
			List<LocationMovementDetailsVO> locationMovementDetailsVO1 = locationMovementDetailsRepo
					.findByLocationMovementVO(locationMovementVO);
			locationMovementDetailsRepo.deleteAll(locationMovementDetailsVO1);
		}

		List<LocationMovementDetailsVO> locationMovementDetailsVOs = new ArrayList<>();
		for (LocationMovementDetailsDTO locationMovementDetailsDTO : locationMovementDTO
				.getLocationMovementDetailsDTO()) {
			if (locationMovementDetailsDTO.getToQty() > locationMovementDetailsDTO.getFromQty()) {
				throw new IllegalArgumentException("ToQuantity cannot be greater than FromQuantity for partNo: "
						+ locationMovementDetailsDTO.getPartNo());
			}
			LocationMovementDetailsVO locationMovementDetailsVO = new LocationMovementDetailsVO();
			locationMovementDetailsVO.setBin(locationMovementDetailsDTO.getBin());
			locationMovementDetailsVO.setPartNo(locationMovementDetailsDTO.getPartNo());
			locationMovementDetailsVO.setPartDesc(locationMovementDetailsDTO.getPartDesc());
			locationMovementDetailsVO.setGrnNo(locationMovementDetailsDTO.getGrnNo());
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
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setPcKey(locationMovementDetailsDTO.getPcKey());
			locationMovementDetailsVO.setSsku(locationMovementDetailsDTO.getSsku());
			locationMovementDetailsVO.setStockDate(locationMovementDetailsDTO.getStockDate());
			locationMovementDetailsVO.setToBinClass(locationMovementDetailsDTO.getToBinClass());
			locationMovementDetailsVO.setToBinType(locationMovementDetailsDTO.getToBinType());
			locationMovementDetailsVO.setToCellType(locationMovementDetailsDTO.getToCellType());

			locationMovementDetailsVO.setBinType(locationMovementDetailsDTO.getBinType());
			locationMovementDetailsVO.setCore(locationMovementDetailsDTO.getCore());
			locationMovementDetailsVO.setBinClass(locationMovementDetailsDTO.getBinClass());
			locationMovementDetailsVO.setExpDate(locationMovementDetailsDTO.getExpDate());
			locationMovementDetailsVO.setLocationMovementVO(locationMovementVO);
			if ("Defective".equals(locationMovementDetailsDTO.getBin())) {
				locationMovementDetailsVO.setQcFlag("F");
				locationMovementDetailsVO.setStatus("D");
			} else {
				locationMovementDetailsVO.setQcFlag("T");
				locationMovementDetailsVO.setStatus("R");
			}

			locationMovementDetailsVOs.add(locationMovementDetailsVO);
		}
		locationMovementVO.setLocationMovementDetailsVO(locationMovementDetailsVOs);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String branch, String branchCode,
			String client) {

		Set<Object[]> result = locationMovementRepo.findBinFromStockForLocationMovement(orgId, branch, branchCode,
				client);
		return getMovementResult(result);
	}

	private List<Map<String, Object>> getMovementResult(Set<Object[]> result) {
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

	@Transactional
	public List<Map<String, Object>> getToBinFromLocationStatusForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String warehouse) {

		Set<Object[]> result = locationMovementRepo.findToBinFromLocationStatusForLocationMovement(orgId, branch,
				branchCode, client, warehouse);
		return getToBinResult(result);
	}

	private List<Map<String, Object>> getToBinResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("toBin", fs[0] != null ? fs[0].toString() : "");
			part.put("toBinClass", fs[1] != null ? fs[1].toString() : "");
			part.put("toBinType", fs[2] != null ? fs[2].toString() : "");
			part.put("toCellType", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin) {

		Set<Object[]> result = locationMovementRepo.findPartNoAndPartDescFromStockForLocationMovement(orgId, branch,
				branchCode, client, bin);
		return getPartResult(result);
	}

	private List<Map<String, Object>> getPartResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("partNo", fs[0] != null ? fs[0].toString() : "");
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");

			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku) {

		Set<Object[]> result = locationMovementRepo.findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
				orgId, branch, branchCode, client, bin, partNo, partDesc, sku);
		return getGrnResult(result);
	}

	private List<Map<String, Object>> getGrnResult(Set<Object[]> result) {
		List<Map<String, Object>> details1 = new ArrayList<>();
		for (Object[] fs : result) {
			Map<String, Object> part = new HashMap<>();
			part.put("grnNo", fs[0] != null ? fs[0].toString() : "");
			part.put("grnDate", fs[1] != null ? fs[1].toString() : "");
			part.put("batchNo", fs[2] != null ? fs[2].toString() : "");
			part.put("batchDate", fs[3] != null ? fs[3].toString() : "");
			part.put("lotNo", fs[4] != null ? fs[4].toString() : "");
			part.put("core", fs[5] != null ? fs[5].toString() : "");
			part.put("expDate", fs[6] != null ? fs[6].toString() : "");
			part.put("status", fs[7] != null ? fs[7].toString() : "");
			part.put("avlQty", fs[8] != null ? fs[8].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Transactional
	public List<Map<String, Object>> getAllForLocationMovementDetailsFillGrid(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = locationMovementRepo.findAllForLocationMovementDetailsFillGrid(orgId, branch, branchCode,
				client);
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
			part.put("pcKey", fs[6] != null ? fs[6].toString() : "");
			part.put("ssku", fs[7] != null ? fs[7].toString() : "");
			part.put("stockDate", fs[8] != null ? fs[8].toString() : "");
			part.put("partNo", fs[9] != null ? fs[9].toString() : "");
			part.put("partDesc", fs[10] != null ? fs[10].toString() : "");
			part.put("sku", fs[11] != null ? fs[11].toString() : "");
			part.put("grnNo", fs[12] != null ? fs[12].toString() : "");
			part.put("batchNo", fs[13] != null ? fs[13].toString() : "");
			part.put("batchDate", fs[14] != null ? fs[14].toString() : "");
			part.put("lotNo", fs[15] != null ? fs[15].toString() : "");
			part.put("grnDate", fs[16] != null ? fs[16].toString() : "");
			part.put("avlQty", fs[17] != null ? fs[17].toString() : "");
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
	public int getAvlQtyFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client,
			String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo) {

		Set<Object[]> result = locationMovementRepo.findAvlQtyFromStockForLocationMovement(orgId, branch, branchCode,
				client, bin, partDesc, sku, partNo, grnNo, lotNo);
		return getAvlQtyLMResult(result);
	}

	private int getAvlQtyLMResult(Set<Object[]> result) {
		int totalQty = 0;
		for (Object[] qt : result) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
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
			deKittingVO = deKittingRepo.findDeKittingById(id);
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
		deKittingVO.setFreeze(deKittingDTO.getFreeze());
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

	// PARENT
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
			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
			part.put("sku", fs[2] != null ? fs[2].toString() : "");
			part.put("avlQty", fs[3] != null ? fs[3].toString() : "");
			details1.add(part);
		}
		return details1;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findBinFromStockForDeKittingParent(orgId, finYear, branch, branchCode,
				client);
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
	public List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(
			Long orgId, String finYear, String branch, String branchCode, String client, String bin, String partNo,
			String partDesc, String sku) {

		Set<Object[]> result = deKittingRepo.findGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(
				orgId, finYear, branch, branchCode, client, bin, partNo, partDesc, sku);
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
	public int getAvlQtyFromStockForDeKittingParent(Long orgId, String finYear, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo) {

		Set<Object[]> result = deKittingRepo.findAvlQtyFromStockForDeKittingParent(orgId, finYear, branch, branchCode,
				client, bin, partDesc, sku, partNo, grnNo, lotNo);
		return getAvlQtyResult(result);
	}

	private int getAvlQtyResult(Set<Object[]> result) {
		int totalQty = 0;
		for (Object[] qt : result) {
			totalQty += (qt[0] != null ? Integer.parseInt(qt[0].toString()) : 0);
		}
		return totalQty;
	}

	// CHILD
	@Transactional
	public List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch,
			String branchCode, String client) {

		Set<Object[]> result = deKittingRepo.findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(orgId, branch,
				branchCode, client);
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

//	// CYCLECOUNT
//
//	@Override
//
//	public Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException {
//		CycleCountVO cycleCountVO;
//		String screenCode = "CT";
//		String message;
//
//		if (ObjectUtils.isEmpty(cycleCountDTO.getId())) {
//
//			cycleCountVO = new CycleCountVO();
//			cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
//			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());
//
//			// GETDOCID API
//			String docId = cycleCountRepo.getCycleCountInDocId(cycleCountDTO.getOrgId(), cycleCountDTO.getFinYear(),
//					cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(), screenCode);
//			cycleCountVO.setDocId(docId);
//
//			// GETDOCID LASTNO +1
//			DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO = documentTypeMappingDetailsRepo
//					.findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(cycleCountDTO.getOrgId(),
//							cycleCountDTO.getFinYear(), cycleCountDTO.getBranchCode(), cycleCountDTO.getClient(),
//							screenCode);
//			documentTypeMappingDetailsVO.setLastno(documentTypeMappingDetailsVO.getLastno() + 1);
//			documentTypeMappingDetailsRepo.save(documentTypeMappingDetailsVO);
//
//			message = "CycleCountDTO Creation Successfully";
//		} else {
//			cycleCountVO = cycleCountRepo.findById(cycleCountDTO.getId()).orElseThrow(() -> new ApplicationException(
//					"This Id Is Not Fount Any Information,Invalid Id ." + cycleCountDTO.getId()));
//			cycleCountVO.setUpdatedBy(cycleCountDTO.getCreatedBy());
//
//			List<CycleCountDetailsVO> countDetailsVOs = cycleCountDetailsRepo.findByCycleCountVO(cycleCountVO);
//			cycleCountDetailsRepo.deleteAll(countDetailsVOs);
//			message = "CycleCountDTO Updation Successfully";
//		}
//		getCycleCountVOFromCycleCountDTO(cycleCountVO, cycleCountDTO);
//		cycleCountRepo.save(cycleCountVO);
//		Map<String, Object> response = new HashMap<String, Object>();
//		response.put("message", message);
//		response.put("cycleCountVO", cycleCountVO);
//		return response;
//	}
//
//	private CycleCountVO getCycleCountVOFromCycleCountDTO(CycleCountVO cycleCountVO, CycleCountDTO cycleCountDTO) {
//		cycleCountVO.setDocDate(cycleCountDTO.getDocDate());
//		cycleCountVO.setOrgId(cycleCountDTO.getOrgId());
//		cycleCountVO.setCustomer(cycleCountDTO.getCustomer());
//		cycleCountVO.setClient(cycleCountDTO.getClient());
//		cycleCountVO.setFinYear(cycleCountDTO.getFinYear());
//		cycleCountVO.setBranch(cycleCountDTO.getBranch());
//		cycleCountVO.setBranchCode(cycleCountDTO.getBranchCode());
//		cycleCountVO.setWarehouse(cycleCountDTO.getWarehouse());
//		cycleCountVO.setCreatedBy(cycleCountDTO.getCreatedBy());
//		cycleCountVO.setCancelRemarks(cycleCountDTO.getCancelRemarks());
//		cycleCountVO.setFreeze(cycleCountDTO.isFreeze());
//		cycleCountVO.setCycleCountNo(cycleCountDTO.getCycleCountNo());
//		cycleCountVO.setCycleCountDate(cycleCountDTO.getCycleCountDate());
//
//		List<CycleCountDetailsVO> cycleCountDetailsVOs = new ArrayList<>();
//		for (CycleCountDetailsDTO details2dto : cycleCountDTO.getCycleCountDetailsDTO()) {
//			CycleCountDetailsVO cycleCountDetailsVO = new CycleCountDetailsVO();
//			cycleCountDetailsVO.setPartNo(details2dto.getPartNo());
//			cycleCountDetailsVO.setParetDescription(details2dto.getParetDescription());
//			cycleCountDetailsVO.setGrnNo(details2dto.getGrnNo());
//			cycleCountDetailsVO.setSku(details2dto.getSku());
//			cycleCountDetailsVO.setBinType(details2dto.getBinType());
//			cycleCountDetailsVO.setBatchNo(details2dto.getBatchNo());
//			cycleCountDetailsVO.setBatchDate(details2dto.getBatchDate());
//			cycleCountDetailsVO.setBin(details2dto.getBin());
//			cycleCountDetailsVO.setQty(details2dto.getQty());
//			cycleCountDetailsVO.setActualQty(details2dto.getActualQty());
//			cycleCountDetailsVO.setQQcflag(details2dto.isQQcflag());
//
//			// Avoid recursive reference to kittingVO in KittingDetails2VO
//			cycleCountDetailsVO.setCycleCountVO(cycleCountVO);
//			cycleCountDetailsVOs.add(cycleCountDetailsVO);
//		}
//		cycleCountVO.setCycleCountDetailsVO(cycleCountDetailsVOs);
//
//		return cycleCountVO;
//	}
//
//	@Override
//	public String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client) {
//		String ScreenCode = "CT";
//		String result = cycleCountRepo.getCycleCountInDocId(orgId, finYear, branchCode, client, ScreenCode);
//		return result;
//	}
//
//	@Override
//	public List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode,
//			String finYear, String warehouse) {
//		return cycleCountRepo.findAllCycleCount(orgId, client, branch, branchCode, finYear, warehouse);
//	}
//
//	@Override
//	public Optional<CycleCountVO> getCycleCountById(Long id) {
//		return cycleCountRepo.findById(id);
//	}
//
//	@Override
//	public List<Map<String, Object>> getCycleCountGridDetails(Long orgId, String branchCode, String client,
//			String warehouse) {
//		Set<Object[]> result = cycleCountRepo.getCycleCountGrid(orgId, branchCode, client, warehouse);
//		return getCycleCount(result);
//	}
//
//	private List<Map<String, Object>> getCycleCount(Set<Object[]> result) {
//		List<Map<String, Object>> details1 = new ArrayList<>();
//		for (Object[] fs : result) {
//			Map<String, Object> part = new HashMap<>();
//
//			part.put("partNo", fs[0] != null ? Integer.parseInt(fs[0].toString()) : 0);
//			part.put("partDesc", fs[1] != null ? fs[1].toString() : "");
//			part.put("sku", fs[2] != null ? fs[2].toString() : "");
//			part.put("bin", fs[3] != null ? fs[3].toString() : "");
//			part.put("batch", fs[4] != null ? fs[4].toString() : "");
//			part.put("batchDate", fs[5] != null ? fs[5].toString() : "");
//			part.put("lotNo", fs[6] != null ? fs[6].toString() : "");
//			part.put("grnNo", fs[7] != null ? fs[7].toString() : "");
//			part.put("grnDate", fs[8] != null ? fs[8].toString() : "");
//			part.put("binclass", fs[9] != null ? fs[9].toString() : "");
//			part.put("bintype", fs[10] != null ? fs[10].toString() : "");
//			part.put("status", fs[11] != null ? fs[11].toString() : "");
//			part.put("qcflag", fs[12] != null ? fs[12].toString() : "");
//			part.put("stockdate", fs[13] != null ? fs[13].toString() : "");
//			part.put("expdate", fs[14] != null ? fs[14].toString() : "");
//			part.put("core", fs[15] != null ? fs[15].toString() : "");
//			part.put("cellType", fs[16] != null ? fs[16].toString() : "");
//			part.put("avlQty", fs[17] != null ? Integer.parseInt(fs[17].toString()) : 0);
//
//			details1.add(part);
//		}
//		return details1;
//
//	}

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
