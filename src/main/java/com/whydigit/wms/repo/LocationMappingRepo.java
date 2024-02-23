package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.LocationMappingVO;

public interface LocationMappingRepo extends JpaRepository<LocationMappingVO, Long>{

}
