package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long> {

	@Query(value = "select a.customer,b.client from customer a,client b where a.id=b.customerid and a.org_id =?1 group by a.customer,b.client",nativeQuery = true)
	Set<Object[]> findAllCustomerAndClientByOrgId(Long orgid);

	@Query(value = "SELECT a from CustomerVO a where a.orgId=?1 ")
	List<CustomerVO> findAll(Long orgid);

}
