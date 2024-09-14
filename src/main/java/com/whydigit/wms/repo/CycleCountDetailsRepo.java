package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.CycleCountDetailsVO;
import com.whydigit.wms.entity.CycleCountVO;

public interface CycleCountDetailsRepo extends JpaRepository<CycleCountDetailsVO, Long>{

	List<CycleCountDetailsVO> findByCycleCountVO(CycleCountVO cycleCountVO);

}
