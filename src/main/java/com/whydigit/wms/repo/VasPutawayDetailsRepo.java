package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayDetailsVO;

@Repository
public interface VasPutawayDetailsRepo extends JpaRepository<VasPutawayDetailsVO, Long> {

}
