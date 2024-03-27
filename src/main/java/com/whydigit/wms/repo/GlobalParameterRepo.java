package com.whydigit.wms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GlobalParameterVO;

public interface GlobalParameterRepo extends JpaRepository<GlobalParameterVO, Long> {

	@Query("select a from GlobalParameterVO a where a.orgId=?1 and a.userid=?2")
	Optional<GlobalParameterVO> findGlobalParamByOrgIdAndUserName(Long orgid, String username);

	@Query("select a from GlobalParameterVO a where a.orgId=?1 and a.userid=?2")
	GlobalParameterVO findGlobalParam(Long orgId, String userid);

}
