package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.GatePassInDetailsVO;
import com.whydigit.wms.entity.GatePassInVO;

@Repository
public interface GatePassInDetailsRepo extends JpaRepository<GatePassInDetailsVO, Long> {

	List<GatePassInDetailsVO> findByGatePassInVO(GatePassInVO gatePassInVO);

}
