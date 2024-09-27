package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CustomerAttachmentType;
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

	String getLocationMovementDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getAllForLocationMovementDetailsFillGrid(Long id, String branch, String branchCode,
			String client,String entryNo) throws ApplicationException;

	Integer getAvlQtyFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo, String grnno,String batchNo);

	List<Map<String, Object>> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId,
			String branch, String branchCode, String client, String bin, String partNo);

	List<Map<String, Object>> getBatchNoAndBatchDateFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo, String grnno);


	int getTotalRows();

	int getSuccessfulUploads();

	void ExcelUploadForLm(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse) throws ApplicationException;

	
}
