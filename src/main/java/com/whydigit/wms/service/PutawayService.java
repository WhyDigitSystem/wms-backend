package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface PutawayService {
	
	List<PutAwayVO> getAllPutAway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	PutAwayVO getPutAwayById(Long id);

	List<Map<String, Object>> getFillGridDetailsForPutaway(Long orgId, String branchCode, String warehouse,
			String client, String grnNo, String binType, String binClass, String binPick);

	String getPutAwayDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	Map<String, Object> createUpdatePutAway(PutAwayDTO putAwayDTO) throws ApplicationException;

	List<GrnVO> getGrnNoForPutAway(Long orgId, String client, String branch, String branchcode, String warehouse);

}
