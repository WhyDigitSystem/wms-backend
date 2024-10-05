package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.WarehouseBranchVO;

public interface WarehouseBranchRepo extends JpaRepository<WarehouseBranchVO, Long>{

//	List<WarehouseBranchVO> findByWarehouseVO(WarehouseVO warehouseVO);
//
//	boolean existsByCustomerBranchCodeAndOrgId(String customerBranchCode, Long orgId);

}
