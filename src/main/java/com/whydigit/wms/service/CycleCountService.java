package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface CycleCountService {

	// CYCLECOUNT

		Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException;

		String getCycleCountInDocId(Long orgId, String finYear, String branch, String branchCode, String client);

		List<CycleCountVO> getAllCycleCount(Long orgId, String client, String branch, String branchCode, String finYear,
				String warehouse);

		Optional<CycleCountVO> getCycleCountById(Long id);

		List<Map<String, Object>> getCycleCountGridDetails(Long orgId, String branchCode, String client, String warehouse,String status);

		List<Map<String, Object>> getPartNoByCycleCount(Long orgId, String branchCode, String client, String warehouse,String status);

		List<Map<String, Object>> getGrnNoByCycleCount(Long orgId, String branchCode, String client, String warehouse,
				String partNo,String status);

		List<Map<String, Object>> getBatchByCycleCount(Long orgId, String branchCode, String client, String warehouse,
				String partNo, String grnNO,String status);

		List<Map<String, Object>> getBinDetailsByCycleCount(Long orgId, String branchCode, String client,
				String warehouse, String partNo, String grnNO, String batch,String status);

		List<Map<String, Object>> getAvlQtyByCycleCount(Long orgId, String branchCode, String client, String warehouse,
				String partNo, String grnNO, String batch, String bin,String status);
	
}
