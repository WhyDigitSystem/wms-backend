package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface VasPutawayService {

	List<VasPutawayVO> getAllVasPutaway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	VasPutawayVO getVasPutawayById(Long id);

	Map<String, Object> createUpdateVasPutaway(@Valid VasPutawayDTO vasPutawayDTO) throws ApplicationException;

	String getVasPutawayDocId(Long orgId, String finYear, String branch, String branchCode, String client);
	
	List<Map<String, Object>> getDocIdFromVasPickForVasPutaway(Long orgId, String branch, String client);

//	List<Map<String, Object>> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch,
//			String client, String docid);
	
	List<Map<String, Object>> getAllFillGridFromVasPutaway(Long orgId, String branch, String branchCode,
			String client,String docId);
	
	
}
