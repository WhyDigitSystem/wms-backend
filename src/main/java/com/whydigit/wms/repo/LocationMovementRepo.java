package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.LocationMovementVO;

public interface LocationMovementRepo extends JpaRepository<LocationMovementVO, Long> {

}
