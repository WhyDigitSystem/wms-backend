package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.exception.ApplicationException;

public interface VasService {

	// KITTING

	Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException;

	List<KittingVO> getAllKitting(Long orgId, String branchCode, String client, String customer);

	Optional<KittingVO> getKittingById(Long id);

	String getKittingInDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	List<Map<String, Object>> getPartNOByChild(Long orgId, String bin, String branch, String branchCode, String client);

	List<Map<String, Object>> getGrnNOByChild(Long orgId, String bin, String branch, String branchCode, String client,
			String partNo, String partDesc, String sku);

	List<Map<String, Object>> getSqtyByKitting(Long orgId, String bin, String branch, String branchCode, String client,
			String partNo, String partDesc, String sku, String grnNO);

	List<Map<String, Object>> getPartNOByParent(Long orgId, String bin, String branch, String branchCode,
			String client);

	List<Map<String, Object>> getGrnNOByParent(Long orgId, String bin, String branch, String branchCode, String client,
			String partNo, String partDesc, String sku);
}
