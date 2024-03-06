package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.GatePassLrNoDetailsVO;

@Repository
public interface GatePassLrNoDetailsRepo extends JpaRepository<GatePassLrNoDetailsVO, Long> {

}
