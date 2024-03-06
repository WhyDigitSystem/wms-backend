package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.ClientVO;

public interface ClientRepo extends JpaRepository<ClientVO, Long>{
	
	@Query(nativeQuery = true,value="select a.* from client a,customer b where a.customerid=b.id and b.org_id=?1 and b.customer=?2")
	List<ClientVO> findAllClientByCustomer(Long orgid, String customer);

	@Query(nativeQuery = true,value ="select a.* from client a ,clientbranch b,user_clientaccess c,users d,customer e where a.id=b.customerid  and a.client=c.client and d.users_id=c.users_id and a.org_id=?1  and d.user_name=?2   and b.branchcode=?3 and e.id=a.customerid and e.customer=?4")
	List<ClientVO> findAllAccessClientByUserName(Long orgid, String userName, String branchcode,String customer);

	

}
