package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.ReversePickDTO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.ReversePickVO;
import com.whydigit.wms.exception.ApplicationException;
@Service
public interface ReversePickService {

	public Map<String, Object> createUpdateReversePick(ReversePickDTO reversePickDTO) throws ApplicationException;

	public String getReversePickDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	public List<ReversePickVO> getAllReversePick(Long orgId, String client, String branch, String branchCode,
			String finYear, String warehouse);

	public ReversePickVO getReversePickById(Long id);
	
	List<PickRequestVO>getPickRequestDetailsForReversePick(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getPickRequestFillDetailsForReversePick(Long orgId, String branchCode, String client,
			String pickDocId);


}
