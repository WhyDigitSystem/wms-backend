package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.ClientVO;

public interface ClientRepo extends JpaRepository<ClientVO, Long>{
	
	@Query(nativeQuery = true,value="select a.* from client a,customer b where a.customerid=b.customerid and b.orgid=?1 and b.customer=?2")
	List<ClientVO> findAllClientByCustomer(Long orgid, String customer);

	@Query(nativeQuery = true,value ="select a.client from client a ,clientbranch b,user_clientaccess c,users d,customer e where a.clientid=b.customerid and a.client=c.client and d.userid=c.users_id and a.orgid=?1  and d.username=?2   and b.branchcode=?3 and e.customerid=a.customerid and e.customer=?4 group by a.client")
	Set<Object[]> findAllAccessClientByUserName(Long orgid, String userName, String branchcode,String customer);

	@Query(nativeQuery = true,value = "select a.client,a.clientcode from client a,customer b,clientbranch c where a.customerid=b.customerid and b.customerid=c.customerid and a.orgid=?1 and c.branchcode=?2 group by a.client,a.clientcode")
	Set<Object[]> findAllClientByOrgIdAndAccessBranch(Long orgid, String branchcode);

	

	

}
