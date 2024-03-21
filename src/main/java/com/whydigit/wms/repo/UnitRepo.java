package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.UnitVO;

public interface UnitRepo extends JpaRepository<UnitVO, Long> {

	@Query("select a from UnitVO a where a.orgId=?1")
	List<UnitVO> findAll(Long orgid);

}
