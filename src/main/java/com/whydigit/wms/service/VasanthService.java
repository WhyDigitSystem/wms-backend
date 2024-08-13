package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.exception.ApplicationException;
@Service
public interface VasanthService {

	//VASPICK
	
	Map<String, Object> createUpdateVasPic(VasPickDTO vasPicDTO) throws ApplicationException;

	Optional<VasPickVO> getVaspickById(Long id);

	List<VasPickVO> getAllVaspick(Long orgId, String branchCode, String client, String customer);

	String getVasPickDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	//KITTING

	Map<String, Object> createUpdateKitting(KittingDTO kittingDTO) throws ApplicationException;

	List<KittingVO> getAllKitting(Long orgId, String branchCode, String client, String customer);

	Optional<KittingVO> getKittingById(Long id);

	String getKittingInDocId(Long orgId, String finYear, String branch, String branchCode, String client);

	//CYCLECOUNT
	
	Map<String, Object> createUpdateCycleCount(CycleCountDTO cycleCountDTO) throws ApplicationException;
	
}
