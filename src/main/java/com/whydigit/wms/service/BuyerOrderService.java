package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface BuyerOrderService {
	// BuyerOrder

	Map<String, Object> createUpdateBuyerOrder(BuyerOrderDTO buyerOrderDTO) throws ApplicationException;

	List<BuyerOrderVO> getAllBuyerOrderByOrgId(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);
	
	Optional<BuyerOrderVO> getAllBuyerOrderById(Long id);

	String getBuyerOrderDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getBoSkuDetails(Long orgId, String branchCode, String client, String batch,
			String warehouse);

	int getAvlQtyByBO(Long orgId, String client, String branchCode, String warehouse, String branch, String partNo,
			String batch);

	List<Map<String, Object>> getBatchByBuyerOrder(Long orgId, String branchCode, String client, String warehouse,
			String partNo);

	


}
