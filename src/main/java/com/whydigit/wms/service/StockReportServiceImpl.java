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
public class StockReportServiceImpl implements StockReportService{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(StockReportServiceImpl.class);
	
	@Autowired
	StockDetailsRepo stockDetailsRepo;

	@Override
	public List<Map<String, Object>> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]>getDetails=stockDetailsRepo.getConsolidateStockDetails( orgId,  branchCode,  warehouse,
				 customer,  client,  partNo);
		return stockdetails(getDetails);
	}

	private List<Map<String, Object>> stockdetails(Set<Object[]> getDetails) {
		List<Map<String, Object>>stock=new ArrayList<>();
		for(Object[] st:getDetails)
		{
			Map<String,Object>stockDetails=new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("avlQty", st[2] != null ? Integer.parseInt(st[2].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

}
