package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.exception.ApplicationException;

public interface VasService {

	//KITTING

		Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException;

		List<KittingVO> getAllKitting(Long orgId, String branchCode, String client, String customer);

		Optional<KittingVO> getKittingById(Long id);

		String getKittingInDocId(Long orgId, String finYear, String branch, String branchCode, String client);
}
