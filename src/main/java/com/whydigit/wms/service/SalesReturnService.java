package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface SalesReturnService {

	List<SalesReturnVO> getAllSalesReturn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	SalesReturnVO getSalesReturnById(Long id);

	Map<String, Object> createUpdateSalesReturn(@Valid SalesReturnDTO salesReturnDTO) throws ApplicationException;

	List<Map<String, Object>> getSalesReturnFillGridDetails(String docId, String client, Long orgId, String branchCode);

	String getSalesReturnDocId(Long orgId, String finYear, String branch, String branchCode, String client);
}
