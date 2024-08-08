package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.MaterialVO;

public interface MaterialRepo extends JpaRepository<MaterialVO, Long>{

	
	@Query("select a from MaterialVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL'or a.cbranch=?3)")
	List<MaterialVO> findAllByOrgIdAndClient(Long orgid, String client, String cbranch);

	boolean existsByOrgIdAndCustomerAndClientAndPartno(Long orgId, String customer, String client, String partno);

	boolean existsByOrgIdAndCustomerAndClientAndPartDesc(Long orgId, String customer, String client, String partDesc);


	@Query(value ="select partno,sku,partdesc from material where orgid=?1 and client=?2 and branch=?3 and branchcode=?4 and customer=?5 and active=true",nativeQuery =true )
	Set<Object[]> findPartNo(Long orgId, String client, String branch, String branchCode, String customer);

}
