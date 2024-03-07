package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.WarehouseVO;

@Repository
public interface WarehouseRepo  extends JpaRepository<WarehouseVO, Long> {

	@Query(value="select a from WarehouseVO a where a.orgId=?1 and a.branch=?2")
	List<WarehouseVO> findAll(Long orgid, String branch);

}

