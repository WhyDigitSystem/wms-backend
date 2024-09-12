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

	public List<Map<String, Object>> getPartNoForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client) {
		Set<Object[]> getPartNoReport = stockDetailsRepo.findPartNoForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client);
		return partNoStockReport(getPartNoReport);
	}

	private List<Map<String, Object>> partNoStockReport(Set<Object[]> getPartNoReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getPartNoReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getBatchForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo) {
		Set<Object[]> getBatchReport = stockDetailsRepo.findBatchForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client, partNo);
		return batchStockReport(getBatchReport);
	}

	private List<Map<String, Object>> batchStockReport(Set<Object[]> getBatchReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getBatchReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("batch", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getBinForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo, String batch) {
		Set<Object[]> getBinReport = stockDetailsRepo.findBinForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
				customer, client, partNo, batch);
		return binStockReport(getBinReport);
	}

	private List<Map<String, Object>> binStockReport(Set<Object[]> getBinReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getBinReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("bin", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getStatusForStockReportBinAndBatchWise(Long orgId, String branchCode,
			String warehouse, String customer, String client, String partNo, String batch, String bin) {
		Set<Object[]> getStatusReport = stockDetailsRepo.findStatusForStockReportBinAndBatchWise(orgId, branchCode,
				warehouse, customer, client, partNo, batch, bin);
		return statusStockReport(getStatusReport);
	}

	private List<Map<String, Object>> statusStockReport(Set<Object[]> getStatusReport) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getStatusReport) {
			Map<String, Object> details = new HashMap<>();
			details.put("status", st[0] != null ? st[0].toString() : "");
			stock.add(details);
		}
		return stock;
	}

	public List<Map<String, Object>> getStockReportBinWise(Long orgId, String branchCode, String bin, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockReportBinWise(orgId, branchCode, bin, warehouse, customer,
				client, partNo);
		return getStockReportBin(getDetails);
	}

	private List<Map<String, Object>> getStockReportBin(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("bin", st[2] != null ? st[2].toString() : "");
			stockDetails.put("avlQty", st[3] != null ? Integer.parseInt(st[3].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockReportBatchWise(Long orgId, String branchCode, String batch,
			String warehouse, String customer, String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockReportBatchWise(orgId, branchCode, batch, warehouse,
				customer, client, partNo);
		return getStockReportBatch(getDetails);
	}

	private List<Map<String, Object>> getStockReportBatch(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("batch", st[2] != null ? st[2].toString() : "");
			stockDetails.put("avlQty", st[3] != null ? Integer.parseInt(st[3].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockLedger(Long orgId, String branchCode, String warehouse, String customer,
			String client, String startDate, String endDate, String partNo) {
		Set<Object[]> getLedgerDetails = stockDetailsRepo.getLedgerDetails(orgId, branchCode, warehouse, customer,
				client, startDate, endDate, partNo);
		return getStockLedgerReport(getLedgerDetails);
	}


	@Override
	public List<Map<String, Object>> getPartNoForBatchWiseReport(Long orgId, String branchCode, String warehouse,
			String customer, String client) {
		Set<Object[]>getDetails=stockDetailsRepo. getPartNoFromBatchWiseReport( orgId,  branchCode, warehouse,
				 customer,  client);
		return getPartNoFromBatchWise(getDetails);
	}

	private List<Map<String, Object>> getPartNoFromBatchWise(Set<Object[]> getDetails) {
		List<Map<String, Object>>stock=new ArrayList<>();
		for(Object[] st:getDetails)
		{
			Map<String,Object>stockDetails=new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getBatchForBatchWiseReport(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo) {
		Set<Object[]>getDetails=stockDetailsRepo.getBatchFromBatchWiseReport( orgId,  branchCode, warehouse,
				 customer,client,partNo);
		return getBatchFromBatchWise(getDetails);
	}

	private List<Map<String, Object>> getBatchFromBatchWise(Set<Object[]> getDetails) {
		List<Map<String, Object>>stock=new ArrayList<>();
		for(Object[] st:getDetails)
		{
			Map<String,Object>stockDetails=new HashMap<>();
			stockDetails.put("batch", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}



	private List<Map<String, Object>> getStockLedgerReport(Set<Object[]> getLedgerDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getLedgerDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stockDetails.put("partDesc", st[1] != null ? st[1].toString() : "");
			stockDetails.put("refNo", st[2] != null ? st[2].toString() : "");
			stockDetails.put("sourceScreen", st[3] != null ? st[3].toString() : "");
			stockDetails.put("oQty", st[4] != null ? Integer.parseInt(st[4].toString()) : 0);
			stockDetails.put("rQty", st[5] != null ? Integer.parseInt(st[5].toString()) : 0);
			stockDetails.put("qQty", st[6] != null ? Integer.parseInt(st[6].toString()) : 0);
			stockDetails.put("cQty", st[7] != null ? Integer.parseInt(st[7].toString()) : 0);
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getStockPartNoBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client) {
		Set<Object[]> getDetails = stockDetailsRepo.getStockPartNoBatch(orgId, branchCode, warehouse, customer, client);
		return getStockPartNo(getDetails);
	}

	private List<Map<String, Object>> getStockPartNo(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("partNo", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}

	@Override
	public List<Map<String, Object>> getBatchNoBinWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo) {
		Set<Object[]> getDetails = stockDetailsRepo.getBatchNoBin(orgId, branchCode, warehouse, customer, client,
				partNo);
		return getBatchNo(getDetails);
	}

	private List<Map<String, Object>> getBatchNo(Set<Object[]> getDetails) {
		List<Map<String, Object>> stock = new ArrayList<>();
		for (Object[] st : getDetails) {
			Map<String, Object> stockDetails = new HashMap<>();
			stockDetails.put("BatchNo", st[0] != null ? st[0].toString() : "");
			stock.add(stockDetails);
		}
		return stock;
	}
}
