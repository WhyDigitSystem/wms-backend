package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.exception.ApplicationException;
@Service
public interface StockProcessService {

	List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	CodeConversionVO getCodeConversionById(Long id);

	String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO) throws ApplicationException;

//	SalesReturn
	List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	SalesReturnVO getAllSalesReturnById(Long id);

	Map<String, Object> createUpdateSalesReturn(@Valid SalesReturnDTO salesReturnDTO) throws ApplicationException;

	List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId, String branchCode);
	
	String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode,
			String client);


//	LocationMovement
	List<LocationMovementVO> getAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	LocationMovementVO getAllLocationMovementById(Long id);

	Map<String, Object> createUpdateLocationMovement(@Valid LocationMovementDTO locationMovementDTO)
			throws ApplicationException;

	List<Map<String, Object>> getBinFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client);


	List<Map<String, Object>> getPartNoAndPartDescFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client, String bin);
	
	List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client, String bin,String partNo,String partDesc,String sku);
	
	String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode,
			String client);

//	De-Kitting
	List<DeKittingVO> getAllDeKitting(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	DeKittingVO getAllDeKittingById(Long id);

	Map<String, Object> createUpdateDeKitting(@Valid DeKittingDTO dekittingDTO)
			throws ApplicationException;

}
