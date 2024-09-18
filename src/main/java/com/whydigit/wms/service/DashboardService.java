package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

	List<Map<String, Object>> getStockLowVolume(Long orgId, String branchCode, String client,
			String warehouse);

	List<Map<String, Object>> getPutAwayOrderPerDay(Long orgId, String branchCode, String client, String warehouse);

	List<Map<String, Object>> getPickRequestOrderPerDay(Long orgId, String branchCode, String client, String warehouse);

	List<Map<String, Object>> getBinDetails(Long orgId, String branchCode, String client, String warehouse,String bin);

	List<Map<String, Object>> getStorageDetails(Long orgId, String branchCode, String warehouse);

	List<Map<String, Object>> getGrnOrderDetails(Long orgId, String branchCode, String warehouse, String client,
			int finYear);

	List<Map<String, Object>> getBinDetailsForClientWise(Long orgId, String branchCode, String client,
			String warehouse);

	List<Map<String, Object>> getGrnOrderDetailsYear(Long orgId, String branchCode, String warehouse, String client,
			int finYear);

	List<Map<String, Object>> getOutBoundOrderPerMonth(Long orgId, String branchCode, String warehouse, String client,
			 int finYear);

	List<Map<String, Object>> getOutBoundOrderPerYear(Long orgId, String branchCode, String warehouse, String client,
			int finYear);

	List<Map<String, Object>> getHoldMaterialCount(Long orgId, String branchCode, String warehouse, String client);

}
