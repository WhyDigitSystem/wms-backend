package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.WarehouseVO;

@Repository
public interface WarehouseRepo  extends JpaRepository<WarehouseVO, Long> {

	@Query(nativeQuery = true,value="select a from WarehouseVO a where a.orgid=?1 and a.branch=?2")
	List<WarehouseVO> findAll(Long orgid, String branch);

	@Query(nativeQuery = true,value = "select a.warehouse from warehouse a where a.branchcode='MAAW'and a.orgid='5'group by a.warehouse")
	Set<Object[]> findAllWarehouseByBranch(Long orgid, String branchcode);

}

