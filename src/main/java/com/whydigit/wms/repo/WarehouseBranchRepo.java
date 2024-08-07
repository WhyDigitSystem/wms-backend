package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.WarehouseBranchVO;
import com.whydigit.wms.entity.WarehouseVO;

public interface WarehouseBranchRepo extends JpaRepository<WarehouseBranchVO, Long>{

	List<WarehouseBranchVO> findByWarehouseVO(WarehouseVO warehouseVO);

	boolean existsByCustomerBranchCodeAndOrgId(String customerBranchCode, Long orgId);

}
