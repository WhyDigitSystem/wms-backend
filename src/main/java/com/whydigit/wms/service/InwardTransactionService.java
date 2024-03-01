package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.GrnVO;
@Service
public interface InwardTransactionService {
	
	// Grn

	List<GrnVO> getAllGrn();

	Optional<GrnVO> getGrnById(Long grnid);

	GrnVO createGrn(GrnVO grnVO);

	Optional<GrnVO> updateGrn(GrnVO grnVO);

	void deleteGrn(Long grnid);
}
