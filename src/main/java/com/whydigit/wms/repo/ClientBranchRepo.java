package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.ClientBranchVO;

public interface ClientBranchRepo extends JpaRepository<ClientBranchVO, Long> {
	@Query(value = "select b.branchcode from client c,clientbranch b, customer a where client =?1 and  c.customerid=b.customerid and a.id=b.customerid and a.org_id =?2 group by b.branchcode", nativeQuery = true)
	Set<Object[]> findAllBranchCodeAndBranchByOrgId(String client, Long orgid);

	@Query(nativeQuery = true, value = "select a.* from clientbranch a,customer b where a.customerid=b.id and b.org_id=?1 and b.customer=?2")
	List<ClientBranchVO> findAllBranchByCustomer(Long orgid, String customer);

}
