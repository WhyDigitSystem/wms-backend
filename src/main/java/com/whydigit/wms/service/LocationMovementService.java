package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface LocationMovementService {
	
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

}