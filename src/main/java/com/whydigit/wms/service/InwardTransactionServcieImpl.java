package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.repo.GrnRepo;

@Service
public class InwardTransactionServcieImpl implements InwardTransactionService {

	@Autowired
	GrnRepo grnRepo;

	// Grn

	@Override
	public List<GrnVO> getAllGrn() {
		return grnRepo.findAll();
	}

	@Override
	public Optional<GrnVO> getGrnById(Long grnid) {
		return grnRepo.findById(grnid);
	}

	@Override
	public GrnVO createGrn(GrnVO grnVO) {
		return grnRepo.save(grnVO);
	}

	@Override
	public Optional<GrnVO> updateGrn(GrnVO grnVO) {
		if (grnRepo.existsById(grnVO.getGrnid())) {
			return Optional.of(grnRepo.save(grnVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteGrn(Long grnid) {
		grnRepo.deleteById(grnid);
	}

}
