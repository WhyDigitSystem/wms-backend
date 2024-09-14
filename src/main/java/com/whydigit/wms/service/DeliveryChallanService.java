package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface DeliveryChallanService {

	 //DeliveryChallan
	
	List<DeliveryChallanVO> getAllDeliveryChallan(Long orgId, String client, String branch, String finyear, String branchcode,String warehouse );

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

}
