package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockReportService {
	
	List<Map<String,Object>> getConsolidateStockDetails(Long orgId,String branchCode,String warehouse,String customer,String client,String partNo);

	List<Map<String, Object>> getStockReportBinWise(Long orgId, String branchCode, String bin, String warehouse,
			String customer, String client, String partNo);
	
	List<Map<String, Object>> getStockReportBatchWise(Long orgId, String branchCode, String batch, String warehouse,
			String customer, String client, String partNo);
    
	List<Map<String, Object>> getPartNoFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client);
	List<Map<String, Object>> getBatchFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
			String client,String partNo);
}
