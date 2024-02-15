package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BranchVO;

@Repository
public interface BranchRepo extends JpaRepository<BranchVO, Long> {

	@Query(value = "SELECT * from branch where company=?1 ", nativeQuery = true)
	List<BranchVO> findAllByCompany(String company);

}
