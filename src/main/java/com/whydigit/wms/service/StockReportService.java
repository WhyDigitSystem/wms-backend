package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockReportService {

	List<Map<String, Object>> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo);

	List<Map<String, Object>> getStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin, String status);

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

    
	List<Map<String, Object>> getPartNoFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client);
	List<Map<String, Object>> getBatchFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client,String partNo);
}
