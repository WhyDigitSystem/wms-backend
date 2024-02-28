package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CustomerVO;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long> {

	@Query(value = "select a.customer,b.client from customer a,client b where a.customerid=b.customerid and a.company =?1 group by a.customer,b.client",nativeQuery = true)
	Set<Object[]> findAllCustomerAndClientByCompany(String company);

}
