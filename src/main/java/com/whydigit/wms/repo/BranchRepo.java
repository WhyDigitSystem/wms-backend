package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BranchVO;

@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long> {

	@Query(value = "SELECT a from BranchVO a where a.orgId=?1 ")
	List<BranchVO> findAll(Long orgid);

	boolean existsByBranchAndOrgId(String branch, Long orgId);

	boolean existsByBranchCodeAndOrgId(String branchCode, Long orgId);

}
