package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.CycleCountVO;

public interface CycleCountRepo extends JpaRepository<CycleCountVO, Long>{

	String getCycleCountInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

}
