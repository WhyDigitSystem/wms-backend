package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PickRequestDetailsVO;

@Repository
public interface PickRequestDetailsRepo extends JpaRepository<PickRequestDetailsVO, Long>{

}
