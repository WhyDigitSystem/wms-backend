package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.RegionVO;

@Repository
public interface RegionRepo extends JpaRepository<RegionVO, Long> {

}
