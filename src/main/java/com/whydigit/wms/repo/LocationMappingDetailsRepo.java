package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.LocationMappingDetailsVO;

public interface LocationMappingDetailsRepo extends JpaRepository<LocationMappingDetailsVO, Long> {

}
