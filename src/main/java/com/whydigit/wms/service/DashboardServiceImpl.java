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

	@Override
	public List<Map<String, Object>> getPutAwayOrderPerDay(Long orgId, String branchCode, String client,
			String warehouse) {
		Set<Object[]> getPutAwayOrderPerDay = stockDetailsRepo.getPutAway(orgId, branchCode, client,warehouse);
		return getPutAwayOrder(getPutAwayOrderPerDay);
	}

	private List<Map<String, Object>> getPutAwayOrder(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("orderDate", grid[0] != null ? grid[0].toString() : "");
			details.put("orderCount", grid[1] != null ? Integer.parseInt(grid[1].toString()) : 0);
			
			
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getPickRequestOrderPerDay(Long orgId, String branchCode, String client,
			String warehouse) {
		Set<Object[]> getPickRequestOrder = stockDetailsRepo.getPutAway(orgId, branchCode, client,warehouse);
		return getPick(getPickRequestOrder);
	}
 
	private List<Map<String, Object>> getPick(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("orderDate", grid[0] != null ? grid[0].toString() : "");
			details.put("orderCount", grid[1] != null ? Integer.parseInt(grid[1].toString()) : 0);
			
			
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getBinDetails(Long orgId, String branchCode, String client, String warehouse,String bin) {
		Set<Object[]> getBinDetails1 = stockDetailsRepo.getBinDetails3(orgId, branchCode, client,warehouse,bin);
		return getBinDetails2(getBinDetails1);
	}
 
	private List<Map<String, Object>> getBinDetails2(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("partNo", grid[0] != null ? grid[0].toString() : "");
			details.put("partDesc", grid[1] != null ? grid[1].toString() : "");
			details.put("sku", grid[2] != null ? grid[2].toString() : "");
			details.put("status", grid[3] != null ? grid[3].toString() : "");
			details.put("batch", grid[4] != null ? grid[4].toString() : "");
			details.put("avilQty", grid[5] != null ? Integer.parseInt(grid[5].toString()) : 0);
			
			
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getStorageDetails(Long orgId, String branchCode, String warehouse) {
		Set<Object[]> getStorage = stockDetailsRepo.getStorageDetails2(orgId, branchCode,warehouse);
		return getStorageDetails1(getStorage);
	}
 
	private List<Map<String, Object>> getStorageDetails1(Set<Object[]> gatePassInGridDetails) {
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		for (Object[] grid : gatePassInGridDetails) {
			Map<String, Object> details = new HashMap<>();
			details.put("rowNo", grid[0] != null ? grid[0].toString() : "");
			details.put("level", grid[1] != null ? grid[1].toString() : "");
			details.put("bin", grid[2] != null ? grid[2].toString() : "");
			
			
			gridDetails.add(details);
		}
		return gridDetails;
	}

	@Override
	public List<Map<String, Object>> getGrnOrderDetails(Long orgId, String branchCode, String warehouse, String client,
			int finYear,int month) {
		Set<Object[]> getGrnOrder = stockDetailsRepo.getGrnOrderDetailsPerIn(orgId, branchCode,warehouse,client,finYear,month);
		return getGrnOrderDetails1(getGrnOrder);
		}
	 
		private List<Map<String, Object>> getGrnOrderDetails1(Set<Object[]> gatePassInGridDetails) {
			List<Map<String, Object>> gridDetails = new ArrayList<>();
			for (Object[] grid : gatePassInGridDetails) {
				Map<String, Object> details = new HashMap<>();
				details.put("count", grid[0] != null ? Integer.parseInt(grid[0].toString()) : 0);
				
				
				gridDetails.add(details);
			}
			return gridDetails;
		}

		@Override
		public List<Map<String, Object>> getBinDetailsForClientWise(Long orgId, String branchCode, String client,
				String warehouse) {
			Set<Object[]> getBin = stockDetailsRepo.getBinDetailsForClient(orgId, branchCode,client,warehouse);
			return getBinDetails(getBin);
		}
	 
		private List<Map<String, Object>> getBinDetails(Set<Object[]> gatePassInGridDetails) {
			List<Map<String, Object>> gridDetails = new ArrayList<>();
			for (Object[] grid : gatePassInGridDetails) {
				Map<String, Object> details = new HashMap<>();
				details.put("rowNo", grid[0] != null ? grid[0].toString() : "");
				details.put("level", grid[1] != null ? grid[1].toString() : "");
				details.put("bin", grid[2] != null ? grid[2].toString() : "");
				details.put("binStatus", grid[3] != null ? grid[3].toString() : "");
				
				
				gridDetails.add(details);
			}
			return gridDetails;
		}

		@Override
		public List<Map<String, Object>> getGrnOrderDetailsYear(Long orgId, String branchCode, String warehouse,
				String client, int finYear) {
			Set<Object[]> getGrn = stockDetailsRepo.getGrnOrderDetails(orgId, branchCode,warehouse,client,finYear);
			return getGrnOrderDetailsYear1(getGrn);
			}
		 
			private List<Map<String, Object>> getGrnOrderDetailsYear1(Set<Object[]> gatePassInGridDetails) {
				List<Map<String, Object>> gridDetails = new ArrayList<>();
				for (Object[] grid : gatePassInGridDetails) {
					Map<String, Object> details = new HashMap<>();
					details.put("count", grid[0] != null ? Integer.parseInt(grid[0].toString()) : 0);
					
					
					gridDetails.add(details);
				}
				return gridDetails;
			}

			@Override
			public List<Map<String, Object>> getOutBoundOrderPerMonth(Long orgId, String branchCode, String warehouse,
					String client,int finYear,int month) {
				Set<Object[]> getOutBoundOrder = stockDetailsRepo.getOutBoundOrderPerMonth1(orgId, branchCode,warehouse,client,finYear,month);
				return getOutBound(getOutBoundOrder);
				}
			 
				private List<Map<String, Object>> getOutBound(Set<Object[]> gatePassInGridDetails) {
					List<Map<String, Object>> gridDetails = new ArrayList<>();
					for (Object[] grid : gatePassInGridDetails) {
						Map<String, Object> details = new HashMap<>();
						details.put("count", grid[0] != null ? Integer.parseInt(grid[0].toString()) : 0);
						
						
						gridDetails.add(details);
					}
					return gridDetails;
				}

			@Override
			public List<Map<String, Object>> getOutBoundOrderPerYear(Long orgId, String branchCode, String warehouse,
					String client, int finYear) {
				Set<Object[]> getOutBoundOrder = stockDetailsRepo.getOutBoundOrderPerYear1(orgId, branchCode,warehouse,client,finYear);
				return getOutBound1(getOutBoundOrder);
				}
			 
				private List<Map<String, Object>> getOutBound1(Set<Object[]> gatePassInGridDetails) {
					List<Map<String, Object>> gridDetails = new ArrayList<>();
					for (Object[] grid : gatePassInGridDetails) {
						Map<String, Object> details = new HashMap<>();
						details.put("count", grid[0] != null ? Integer.parseInt(grid[0].toString()) : 0);
						
						
						gridDetails.add(details);
					}
					return gridDetails;
				}

				@Override
				public List<Map<String, Object>> getHoldMaterialCount(Long orgId, String branchCode, String warehouse,
						String client) {
					Set<Object[]> getHoldMaterial = stockDetailsRepo.getHoldMaterialCount1(orgId, branchCode, warehouse,client);
					return getHold(getHoldMaterial);
				}

				private List<Map<String, Object>> getHold(Set<Object[]> getHoldMaterial) {
					List<Map<String, Object>> gridDetails1 = new ArrayList<>();
					for (Object[] grid : getHoldMaterial) {
						Map<String, Object> details = new HashMap<>();
						details.put("partNo", grid[0] != null ? grid[0].toString() : "");
						details.put("partDesc", grid[1] != null ? grid[1].toString() : "");
						details.put("sku", grid[2] != null ? grid[2].toString() : "");
						details.put("bin", grid[3] != null ? grid[3].toString() : "");
						details.put("grnNo", grid[4] != null ? grid[4].toString() : "");
						details.put("grnDate", grid[5] != null ? grid[5].toString() : "");
						details.put("expDate", grid[6] != null ? grid[6].toString() : "");
						details.put("holdQty", grid[7] != null ? Integer.parseInt(grid[7].toString()) : 0);	
						
						gridDetails1.add(details);
					}
					return gridDetails1;
				}

				@Override
				public List<Map<String, Object>> getBinDetailsClientWiseForEmpty(Long orgId, String branchCode,
						String client, String warehouse) {
					Set<Object[]> getBinDetailsForEmpty = stockDetailsRepo.getBinDetailsClientWiseForEmpty(orgId, branchCode, client,warehouse);
					return getBinDetailsClientWise(getBinDetailsForEmpty);
				}

				private List<Map<String, Object>> getBinDetailsClientWise(Set<Object[]> getHoldMaterial) {
					List<Map<String, Object>> gridDetails1 = new ArrayList<>();
					for (Object[] grid : getHoldMaterial) {
						Map<String, Object> details = new HashMap<>();
						details.put("bin", grid[0] != null ? grid[0].toString() : "");
						details.put("binStatus", grid[1] != null ? grid[1].toString() : "");
						gridDetails1.add(details);
					}
					return gridDetails1;
				}

				@Override
				public List<Map<String, Object>> getExpDetailsForMaterials(Long orgId, String branchCode, String client,
						String warehouse) {
					Set<Object[]> getExpDetails = stockDetailsRepo.getExpDetailsForMaterials(orgId, branchCode, client,warehouse);
					return getExpDetailsForMaterials1(getExpDetails);
				}

				private List<Map<String, Object>> getExpDetailsForMaterials1(Set<Object[]> getHoldMaterial) {
					List<Map<String, Object>> gridDetails1 = new ArrayList<>();
					for (Object[] grid : getHoldMaterial) {
						Map<String, Object> details = new HashMap<>();
						details.put("partNo", grid[0] != null ? grid[0].toString() : "");
						details.put("partDesc", grid[1] != null ? grid[1].toString() : "");
						details.put("sku", grid[2] != null ? grid[2].toString() : "");
						details.put("batch", grid[3] != null ? grid[3].toString() : "");
						details.put("batchDate", grid[4] != null ? grid[4].toString() : "");
						details.put("grnNo", grid[5] != null ? grid[5].toString() : "");
						details.put("grnDate", grid[6] != null ? grid[6].toString() : "");
						details.put("expDate", grid[7] != null ? grid[7].toString() : "");
						details.put("qty", grid[8] != null  ? Integer.parseInt(grid[8].toString()) : 0);
						details.put("days", grid[9] != null ? Integer.parseInt(grid[9].toString()) : 0);
						details.put("bin", grid[10] != null ? grid[10].toString() : "");
						gridDetails1.add(details);
					}
					return gridDetails1;
				}




}
