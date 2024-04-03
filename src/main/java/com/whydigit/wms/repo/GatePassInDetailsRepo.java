package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.GatePassInDetailsVO;

@Repository
public interface GatePassInDetailsRepo extends JpaRepository<GatePassInDetailsVO, Long> {

}
