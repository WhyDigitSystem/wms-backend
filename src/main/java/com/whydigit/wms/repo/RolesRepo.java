package com.whydigit.wms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.RolesVO;

public interface RolesRepo extends JpaRepository<RolesVO, Long> {

	boolean existsByRoleAndOrgId(String role, Long orgId);

	

}
