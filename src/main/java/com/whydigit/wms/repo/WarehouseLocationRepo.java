package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.WarehouseLocationVO;

public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Long> { 
	
}
