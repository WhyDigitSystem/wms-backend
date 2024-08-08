package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.BuyerVO;

public interface BuyerRepo extends JpaRepository<BuyerVO, Long> {

	@Query("select a from BuyerVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL' or a.cbranch=?3)")
	List<BuyerVO> findAllByOrgIdAndClient(Long orgid, String client, String cbranch);

	boolean existsByOrgIdAndCustomerAndClientAndBuyer(Long orgId, String customer, String client, String buyer);

	boolean existsByOrgIdAndCustomerAndClientAndBuyerShortName(Long orgId, String customer, String client,
			String buyerShortName);

}
