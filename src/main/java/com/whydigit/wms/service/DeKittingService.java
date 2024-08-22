package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface DeKittingService {
	List<DeKittingVO> getAllDeKitting(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	DeKittingVO getDeKittingById(Long id);

	Map<String, Object> createUpdateDeKitting(@Valid DeKittingDTO dekittingDTO) throws ApplicationException;

	String getDeKittingDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	// PARENT
	List<Map<String, Object>> getPartNoFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client);

	List<Map<String, Object>> getBinFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client);

	List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku);

	int getAvlQtyFromStockForDeKittingParent(Long orgId, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo);

	// CHILD
	List<Map<String, Object>> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch,
			String branchCode, String client);

}
