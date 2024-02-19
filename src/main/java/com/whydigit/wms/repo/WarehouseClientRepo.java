package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.WarehouseClientVO;

public interface WarehouseClientRepo extends JpaRepository<WarehouseClientVO, Long> {

}
