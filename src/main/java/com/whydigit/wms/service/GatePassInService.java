package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface GatePassInService {
	
	// GatePassIn

		List<GatePassInVO> getAllGatePassIn(Long orgId, String branchCode, String finYear, String client);

		Optional<GatePassInVO> getGatePassInById(Long id);

		Map<String, Object> createUpdateGatePassIn(GatePassInDTO gatePassInDTO) throws ApplicationException;

		String getGatePassInDocId(Long orgId, String finYear, String branch, String branchCode, String client);


}
