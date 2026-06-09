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

	@Query(value ="select partno,sku,partdesc from material where orgid=?1 and client=?2 and branch=?3 and (cbranch='ALL' or cbranch=?4) and customer=?5 ",nativeQuery =true )
	Set<Object[]> findPartNo(Long orgId, String client, String branch, String branchCode, String customer);

	@Query(value = "select a.parentChildKey from MaterialVO a where a.orgId=?1 and a.client=?2 and a.partno=?3")
	String getParentChildKey(Long orgId, String client, String partNo);

	boolean existsByOrgIdAndCustomerAndClientAndPartDescIgnoreCase(Long orgId, String customer, String client,
			String partNo);

	boolean existsByOrgIdAndCustomerAndClientAndPartnoIgnoreCase(Long orgId, String customer, String client,
			String partDesc);

	@Query(value = "select a from MaterialVO a where a.orgId=?1 and a.customer=?2 and a.client=?3 and a.partno=?4 and a.active=true")
	MaterialVO findPartNoDetails(Long orgId, String customer, String client, String stringCellValue);


}
