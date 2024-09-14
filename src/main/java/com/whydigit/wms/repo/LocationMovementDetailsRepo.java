package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.LocationMovementDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;

public interface LocationMovementDetailsRepo extends JpaRepository<LocationMovementDetailsVO, Long> {

	List<LocationMovementDetailsVO> findByLocationMovementVO(LocationMovementVO locationMovementVO);

}
