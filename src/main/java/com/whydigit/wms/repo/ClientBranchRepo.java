package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.ClientBranchVO;

public interface ClientBranchRepo extends JpaRepository<ClientBranchVO, Long> {
	@Query(value = "select b.branchcode from client c,clientbranch b, customer a where client =?1 and  c.customerid=b.customerid and a.customerid=b.customerid and a.company =?2 group by b.branchcode", nativeQuery = true)
	Set<Object[]> findAllBranchCodeAndBranchByCompany(String client, String company);

}
