package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.EmployeeVO;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeVO, Long>{

	@Query(value="SELECT e.employeecode , e.employeename FROM EmployeeVO e WHERE e.orgId=?1")
	Set<Object[]> findAllNameAndEmployeeCodeByOrgId(Long orgid);

	List<EmployeeVO> findAllEmployeeByOrgId(Long orgid);

	

}
