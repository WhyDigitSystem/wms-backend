package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long> {

	@Query(value = "select a.customer,b.client from customer a,client b where a.customerid=b.customerid and a.orgid =?1 group by a.customer,b.client;",nativeQuery = true)
	Set<Object[]> findAllCustomerAndClientByOrgId(Long orgid);

	@Query(value = "SELECT a from CustomerVO a where a.orgId=?1 ")
	List<CustomerVO> findAll(Long orgid);

	@Query(nativeQuery = true,value ="select a.customer from customer a ,clientbranch b,user_clientaccess c,users d where a.customerid=b.customerid  and a.customer=c.customer and d.userid=c.users_id and a.orgid=?1 and d.username=?2  and b.branchcode=?3 group by a.customer")
	Set<Object[]> findAllAccessCustomerByUserName(Long orgid, String userName, String branchcode);

	boolean existsByCustomerNameAndCustomerShortNameAndOrgId(String customerName, String customerShortName, Long orgId);

	boolean existsByCustomerNameAndOrgId(String customerName, Long orgId);

	boolean existsByCustomerShortNameAndOrgId(String customerShortName, Long orgId);

	boolean existsByCustomerNameAndCustomerShortNameAndOrgIdAndIdNot(String customerName, String customerShortName,
			Long orgId, Long id);

	boolean existsByCustomerNameAndOrgIdAndIdNot(String customerName, Long orgId, Long id);

	boolean existsByCustomerShortNameAndOrgIdAndIdNot(String customerShortName, Long orgId, Long id);


}
