package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GroupVO;

public interface GroupRepo extends JpaRepository<GroupVO, Long>{

	@Query("select a from GroupVO a where a.orgId=?1")
	List<GroupVO> findAll(Long orgid);

	boolean existsByGroupNameAndOrgId(String groupName, Long orgId);

	boolean existsByCompanyAndOrgId(String company, Long orgId);


}
