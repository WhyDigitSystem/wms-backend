package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

	List<Map<String, Object>> getStockLowVolume(Long orgId, String branchCode, String client,
			String warehouse);

}
