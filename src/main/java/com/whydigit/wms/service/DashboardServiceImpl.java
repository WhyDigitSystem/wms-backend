package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class DashboardServiceImpl implements DashboardService{
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Override
	public List<Map<String, Object>> getStockLowVolume(Long orgId, String branchCode, String client,
			String warehouse) {
		Set<Object[]> getStockLowVolume = stockDetailsRepo.getStock(orgId, branchCode, client,warehouse);
		return getStockLow(getStockLowVolume);
	}

	private List<Map<String, Object>> getStockLow(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", grid[0] != null ? grid[0].toString() : "");
			details.put("partDesc", grid[1] != null ? grid[1].toString() : "");
			details.put("sku", grid[2] != null ? grid[2].toString() : "");
			details.put("qty", grid[3] != null ? Integer.parseInt(grid[3].toString()) : 0);
			details.put("status", grid[4] != null ? grid[4].toString() : "");
			
			
			gridDetails.add(details);
		}
		return gridDetails;
	}

}
