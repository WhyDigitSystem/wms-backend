package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.CustomerAttachmentType;
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.entity.StockRestateVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface StockRestateService {
	
	
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
			String client,String tranferFromFlag,String tranferToFlag,String entryNo);

	Map<String, Object> createStockRestate(StockRestateDTO stockRestateDTO) throws ApplicationException;

	void ExcelUploadForStockRestate(MultipartFile[] files, CustomerAttachmentType type, Long orgId, String createdBy,
			String customer, String client, String finYear, String branch, String branchCode, String warehouse) throws ApplicationException;

	int getTotalRows();

	int getSuccessfulUploads();
	
	
	
	String stockFreeze(Long orgid,String branch,String branchCode,String client,String freezeStatus);

	boolean getStockFreezeStatus(Long orgId, String branch, String branchCode, String client);

}
