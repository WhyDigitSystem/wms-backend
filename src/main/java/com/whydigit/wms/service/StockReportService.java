package com.whydigit.wms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.exception.ApplicationException;

@Service
public interface StockReportService {

	List<Map<String, Object>> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo);

	List<Map<String, Object>> getStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin, String status);

	List<Map<String, Object>> getPartNoForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client);

	List<Map<String, Object>> getBatchForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo);

	List<Map<String, Object>> getBinForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch);

	List<Map<String, Object>> getStatusForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin);

	List<Map<String, Object>> getStockReportBinWise(Long orgId, String branchCode, String bin, String warehouse,
			String customer, String client, String partNo);

	List<Map<String, Object>> getStockReportBatchWise(Long orgId, String branchCode, String batch, String warehouse,
			String customer, String client, String partNo);

	List<Map<String, Object>> getStockLedger(Long orgId, String branchCode, String warehouse, String customer,
			String client, String startDate, String endDate, String partNo);

	List<Map<String, Object>> getStockPartNoBatchWise(Long orgId, String branchCode, String warehouse, String customer,
			String client);

	List<Map<String, Object>> getBatchNoBinWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo);

    
	List<Map<String, Object>> getPartNoForBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client);
	List<Map<String, Object>> getBatchForBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client,String partNo);
	
	void uploadStockDetails(MultipartFile[] files, Long orgId, String customer, String client, String warehouse,
			String branch, String branchCode, String createdBy, String FinYear) throws ApplicationException, EncryptedDocumentException, IOException;

	int getTotalRows();

	int getSuccessfulUploads();

}
