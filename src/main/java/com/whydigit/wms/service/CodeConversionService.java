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

	List<Map<String, Object>> getPartNoAndPartDescFromStockForCodeConversion(Long orgId,
			String branchCode, String client,String warehouse);

	List<Map<String, Object>> getGrnNoAndGrnDateFromStockForCodeConversion(Long orgId,
		     String branchCode, String client, String warehouse, String partNo);
	
	List<Map<String, Object>> getBinTypeFromStockForCodeConversion(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNo);
	
	List<Map<String, Object>> getBatchNoFromStockForCodeConversion(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNo,String binType);
	
	List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId, String branchCode, String client,
			String warehouse, String partNo, String grnNo,String binType,String batchNo);
	
	List<Map<String, Object>> getAllFillGridFromStockForCodeConversion(Long orgId, 
			String branchCode, String client,String warehouse);
	
	Integer getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String grnNo,String batchNo,String binType,String bin);



//	List<Map<String, Object>> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId,
//	String branch, String branchCode, String client, String bin);
//
//List<Map<String, Object>> getCBinFromStockForCodeConversion(Long orgId, String branch,
//	String branchCode, String client);
	
//	List<Map<String, Object>> getBinFromStockForCodeConversion(Long orgId,String branch,
//	String branchCode, String client);

}
