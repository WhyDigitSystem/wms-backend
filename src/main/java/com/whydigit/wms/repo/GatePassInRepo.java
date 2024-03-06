package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.GatePassInVO;

@Repository
public interface GatePassInRepo extends JpaRepository<GatePassInVO, Long> {

}
