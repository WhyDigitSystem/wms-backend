package com.whydigit.wms.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.ResponsibilityVO;
import com.whydigit.wms.entity.RolesVO;

public interface ResponsibilitiesRepo extends JpaRepository<ResponsibilityVO, Long> {

	boolean existsByResponsibility(String responsibility);

	@Query(value="select a.id,a.responsibility from ResponsibilityVO a where a.orgId=?1 and a.active=true")
	Set<Object[]> findActiveByOrgId(Long orgId);

	@Query(value="select a from ResponsibilityVO a where a.orgId=?1 ")
	List<ResponsibilityVO> findAllResponsibilityByOrgId(Long orgId);

	@Query(value="select a from ResponsibilityVO a where a.orgId=?1 and a.active=true ")
	List<ResponsibilityVO> findAllActiveResponsibilityByOrgId(Long orgId);

}
