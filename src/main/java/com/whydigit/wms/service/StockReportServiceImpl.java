package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.repo.StockDetailsRepo;

@Service
public class StockReportServiceImpl implements StockReportService {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockReportServiceImpl.class);

	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Override
	public List<Map<String, Object>> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getConsolidateStockDetails(orgId, branchCode, warehouse, customer,
				client, partNo);
		return stockdetails(getDetails);
	}

	private List<Map<String, Object>> stockdetails(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("avlQty", st[2] != null ? Integer.parseInt(st[2].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin, String status) {
		Set<Object[]> getReport = stockDetailsRepo.findStockReportBinAndBatchWise(orgId, branchCode, warehouse,
				customer, client, partNo, batch, bin, status);
		return StockReport(getReport);
	}

	private List<Map<String, Object>> StockReport(Set<Object[]> getReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("bin", st[0] != null ? st[0].toString() : "");
			details.put("batch", st[1] != null ? st[1].toString() : "");
			details.put("partNo", st[2] != null ? st[2].toString() : "");
			details.put("partDesc", st[3] != null ? st[3].toString() : "");
			details.put("status", st[4] != null ? st[4].toString() : "");
			details.put("avlQty", st[5] != null ? Integer.parseInt(st[5].toString()) : 0);
			stock.add(details);
		}
		return stock;
	}

}
