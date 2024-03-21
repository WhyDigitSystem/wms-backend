package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.UserLoginBranchAccessibleVO;

public interface UserBranchAccessRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

	@Query(nativeQuery = true,value = "SELECT a.branch,a.branchcode FROM userbranchaccess a,users b where a.usersid=b.usersid and b.orgid=?1 and b.username=?2 group by a.branch,a.branchcode")
	Set<Object[]> findGlobalParametersBranchByUserName(Long orgid, String userName);

	

}
