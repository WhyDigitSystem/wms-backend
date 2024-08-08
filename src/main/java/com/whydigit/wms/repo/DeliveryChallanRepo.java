package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DeliveryChallanVO;

@Repository
public interface DeliveryChallanRepo extends JpaRepository<DeliveryChallanVO, Long> {

	@Query(value = "select * from deliverychallan where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<DeliveryChallanVO> findAllDeliveryChallan(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	@Query(nativeQuery = true, value = "select * from deliverychallan where deliverychallanid=?1")
	List<DeliveryChallanVO> findDeliveryChallanById(Long id);

	boolean existsByBuyerOrderNoAndOrgId(String buyerOrderNo, Long orgId);

}