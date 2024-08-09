package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.WarehouseVO;

@Repository
public interface WarehouseRepo extends JpaRepository<WarehouseVO, Long> {

	@Query(value = "select a from WarehouseVO a where a.orgId=?1 and a.branch=?2")
	List<WarehouseVO> findAll(Long orgid, String branch);

	@Query(nativeQuery = true, value = "select a.warehouse from warehouse a where a.branchcode=?2 and a.orgid=?1  and active=1"
			+ "group by a.warehouse")
	Set<Object[]> findAllWarehouseByBranch(Long orgid, String branchcode);

	boolean existsByWarehouseAndOrgId(String warehouse, Long orgId);

	@Query(nativeQuery =true,value ="select * from warehouse where orgid=?1 " )
	List<WarehouseVO> findAllWarehouse(Long orgId);

}
