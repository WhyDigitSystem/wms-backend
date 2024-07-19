package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.WarehouseClientVO;
import com.whydigit.wms.entity.WarehouseVO;

public interface WarehouseClientRepo extends JpaRepository<WarehouseClientVO, Long> {

	List<WarehouseClientVO> findByWarehouseVO(WarehouseVO warehouseVO);

	boolean existsByClientAndOrgId(String client, Long orgId);

	boolean existsByClientCodeAndOrgId(String clientCode, Long orgId);

}
