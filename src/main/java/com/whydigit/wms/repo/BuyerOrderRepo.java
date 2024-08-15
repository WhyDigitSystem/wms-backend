package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BuyerOrderVO;
@Repository
public interface BuyerOrderRepo extends JpaRepository<BuyerOrderVO, Long>{

	boolean existsByOrderNoAndOrgIdAndClientAndBranchCodeAndCustomer(String orderNo, Long orgId, String client,
			String branchCode, String customer);

	@Query(value ="select * from buyerorder where orgid=?1",nativeQuery =true )
	List<BuyerOrderVO> findAllBuyerOrderByOrgId(Long orgId);

	@Query(value ="select * from buyerorder where buyerorderid=?1",nativeQuery =true)
	List<BuyerOrderVO> findAllBuyerOrderById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getbuyerOrderDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	
	@Query(nativeQuery =true,value = "select partno,partdesc,batch,sum(sqty) from stockdetails where orgid=1 and branchcode=?2 and client=?3 and status='R'\n"
			+ "group by partno,partdesc,batch")
	Set<Object[]> getBoSku(Long orgId, String branchCode, String branchCode2);

}
