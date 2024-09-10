package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface StockReportService {
	
	List<Map<String,Object>> getConsolidateStockDetails(Long orgId,String branchCode,String warehouse,String customer,String client,String partNo);

}
