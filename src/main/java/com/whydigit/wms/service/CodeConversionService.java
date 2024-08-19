package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface CodeConversionService {

	List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	CodeConversionVO getCodeConversionById(Long id);

	String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO) throws ApplicationException;

	List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin);

	List<Map<String, Object>> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(Long orgId,
		    String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku);

	List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId,String branch,
			String branchCode, String client);
	
	List<Map<String, Object>> getAllFillGridFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client);
	
	List<Map<String, Object>> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId,
			String branch, String branchCode, String client, String bin);
	
	List<Map<String, Object>> getCBinFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client);
	
	int getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc);


}
