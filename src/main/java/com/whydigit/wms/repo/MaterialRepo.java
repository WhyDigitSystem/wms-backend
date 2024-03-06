package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.MaterialVO;

public interface MaterialRepo extends JpaRepository<MaterialVO, Long>{

	
	@Query("select a from MaterialVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL'or a.cbranch=?3)")
	List<MaterialVO> findAllByOrgIdAndClient(Long orgid, String client, String cbranch);

}
