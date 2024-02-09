package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.WarehouseVO;

@Repository
public interface WarehouseRepo  extends JpaRepository<WarehouseVO, Long> {

}

