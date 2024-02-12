package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.LocationTypeVO;

@Repository
public interface LocationTypeRepo extends JpaRepository<LocationTypeVO, Long> {

}
