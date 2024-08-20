package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockRestateVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface StockProcessService {

	// CodeConversion
	List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	CodeConversionVO getCodeConversionById(Long id);

	String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO) throws ApplicationException;

	
//	SalesReturn
	List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	SalesReturnVO getSalesReturnById(Long id);

	Map<String, Object> createUpdateSalesReturn(@Valid SalesReturnDTO salesReturnDTO) throws ApplicationException;

	List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId, String branchCode);

	String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode, String client);

//	LocationMovement
	List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	LocationMovementVO getLocationMovementById(Long id);

	Map<String, Object> createUpdateLocationMovement(@Valid LocationMovementDTO locationMovementDTO)
			throws ApplicationException;

	List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String branch, String branchCode,
			String client);

	List<Map<String, Object>> getToBinFromLocationStatusForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String warehouse);

	List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin);

	List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku);

	String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getAllForLocationMovementDetailsFillGrid(Long id, String branch, String branchCode,
			String client);

	int getAvlQtyFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client, String bin,
			String partDesc, String sku, String partNo, String grnNo, String lotNo);

//	De-Kitting
	List<DeKittingVO> getAllDeKitting(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	DeKittingVO getDeKittingById(Long id);

	Map<String, Object> createUpdateDeKitting(@Valid DeKittingDTO dekittingDTO) throws ApplicationException;

	String getDeKittingDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	// PARENT
	List<Map<String, Object>> getPartNoFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client);

	List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String finYear, String branch,
			String branchCode, String client);

	List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(Long orgId,
			String finYear, String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku);

	int getAvlQtyFromStockForDeKittingParent(Long orgId, String finYear, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo);

	// CHILD
	List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch,
			String branchCode, String client);

	// StockRestate

	List<StockRestateVO> getAllStockRestate(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	StockRestateVO getStockRestateById(Long id);

	String getStockRestateDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getfromBinForStockRestate(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag);

	List<Map<String, Object>> getPartNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String tranferFromFlag, String fromBin);

	List<Map<String, Object>> getGrnNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String tranferFromFlag, String fromBin, String partNo);

	List<Map<String, Object>> getBatchNoDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client, String tranferFromFlag, String fromBin, String partNo, String grnNo);

	int getFromQtyForStockRestate(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag, String fromBin, String partNo, String grnNo, String batchNo);

	List<Map<String, Object>> getToBinDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client);
	
	List<Map<String, Object>> getFillGridDetailsForStockRestate(Long orgId, String branchCode, String warehouse,
			String client,String tranferFromFlag,String tranferToFlag);

	Map<String, Object> createStockRestate(StockRestateDTO stockRestateDTO) throws ApplicationException;

	// CYCLECOUNT

	Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException;

	String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode, String finYear,
			String warehouse);

	Optional<CycleCountVO> getCycleCountById(Long id);

	List<Map<String, Object>> getCycleCountGridDetails(Long orgId, String branchCode, String client, String warehouse);

}
