package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.UserLoginBranchAccessibleVO;

public interface UserBranchAccessRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

	@Query(nativeQuery = true,value = "SELECT a.branch,a.branchcode FROM user_branchaccess a,users b where a.users_id=b.users_id and b.org_id=?1 and b.user_name=?2 group by a.branch,a.branchcode")
	Set<Object[]> findGlobalParametersBranchByUserName(Long orgid, String userName);

	

}
