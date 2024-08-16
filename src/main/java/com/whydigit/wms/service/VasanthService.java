package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface VasanthService {

	// CYCLECOUNT

	Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException;

	String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode, String finYear,
			String warehouse);

	Optional<CycleCountVO> getCycleCountById(Long id);

	List<Map<String, Object>> getCycleCountGridDetails(Long orgId, String branchCode, String client, String warehouse);

}
