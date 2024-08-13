package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DepartmentVO;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentVO, Long> {

	boolean existsByCodeAndOrgId(String code, Long orgId);

	boolean existsByDepartmentNameAndOrgId(String departmentName, Long orgId);

	@Query(value = "", nativeQuery = true)
	List<DepartmentVO> findDepartmentByOrgId(Long orgId);

	@Query(value = "", nativeQuery = true)
	List<DepartmentVO> findDepartmentById(Long id);

}
