package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.exception.ApplicationException;

public interface StockProcessService {

	List<CodeConversionVO> getAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	CodeConversionVO getCodeConversionById(Long id);

	String getCodeConversionDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	Map<String, Object> createUpdateCodeConversion(CodeConversionDTO codeConversionDTO) throws ApplicationException;

}
