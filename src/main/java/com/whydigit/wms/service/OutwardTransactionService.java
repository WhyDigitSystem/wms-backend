package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;

public interface OutwardTransactionService {

	 //DeliveryChallan
	
		List<DeliveryChallanVO> getAllDeliveryChallan(Long orgId, String client, String branch, String finyr, String branchcode,String warehouse );

		DeliveryChallanVO getDeliveryChallanById(Long id);

		Map<String, Object> createUpdateDeliveryChallan(DeliveryChallanDTO deliveryChallanDTO)
				throws ApplicationException;
		
		
		String getDeliveryChallanDocId(Long orgId, String finYear, String branch, String branchCode,
				String client);
		
		
		List<Map<String, Object>> getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(Long orgId,
			 String branch, String branchCode, String client, String warehouse ,String buyerOrderNo);

		List<PickRequestVO> getAllPickRequestFromDeliveryChallan(Long orgId,String branch,
				String branchCode, String client, String warehouse);

		
		List<Map<String, Object>> getBuyerShipToBillToFromBuyerOrderForDeliveryChallan(Long orgId, 
				String branch, String branchCode, String client,String buyerOrderNo);

		//VASPutaway
		
		List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode, String client,
				String warehouse);

		VasPutawayVO getVasPutawayById(Long id);

		Map<String, Object> createUpdateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO) throws ApplicationException;
	
		String getVasPutawayDocId(Long orgId, String finYear, String branch, String branchCode, String client);
		
		List<Map<String, Object>> getDocIdFromVasPickForVasPutaway(Long orgId, String branch, String client);

		List<Map<String, Object>> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch,
				String client, String docid);
		
		List<Map<String, Object>> getAllFillGridFromVasPutaway(Long orgId, String branch, String branchCode,
				String client);
		
		int getAvlQtyVasPutaway(Long orgId, String client, String branchCode, String warehouse, String branch,
				String partNo, String partDesc);

		
		//BuyerOrder
		
		Map<String, Object> createUpdateBuyerOrder(BuyerOrderDTO buyerOrderDTO) throws ApplicationException;

		List<BuyerOrderVO> getAllBuyerOrderByOrgId(Long orgId);

		Optional<BuyerOrderVO> getAllBuyerOrderById(Long id);

		String getBuyerOrderDocId (Long orgId, String finYear, String branch, String branchCode, String client);

		List<Map<String, Object>> getBoSkuDetails(Long orgId, String branchCode, String client,String batch,String warehouse);

		List<Map<String, Object>> getAvlQtyByBO(Long orgId, String client, String branchCode, String warehouse,
				String branch, String partNo, String partDesc,String batch);

		

		

		

		
	
		

		





}
