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

	List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin);

	List<Map<String, Object>> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku);

	List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client);

	List<Map<String, Object>> getAllFillGridFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client);

	List<Map<String, Object>> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin);

	List<Map<String, Object>> getCBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client);

	int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc);


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
	

}
