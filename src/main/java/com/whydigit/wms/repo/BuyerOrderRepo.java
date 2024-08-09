package com.whydigit.wms.repo;

import java.util.List;

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

}
