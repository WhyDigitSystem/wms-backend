package com.whydigit.wms.service;

import java.util.List;

import javax.validation.Valid;

import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;

public interface OutwardTransactionService {

	 //DeliveryChallan
	
		List<DeliveryChallanVO> getAllDeliveryChallan(Long orgId, String client, String branch, String finyr, String branchcode,String warehouse );

		List<DeliveryChallanVO> getDeliveryChallanById(Long id);

		DeliveryChallanVO updateCreateDeliveryChallan(@Valid DeliveryChallanDTO deliveryChallanDTO) throws ApplicationException;

		
		
		
		//VASPutaway
		
		List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode, String client,
				String warehouse);

		List<VasPutawayVO> getVasPutawayById(Long id);
		

}
