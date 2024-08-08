package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;

@Repository
public interface VasPutawayRepo extends JpaRepository<VasPutawayDetailsVO, Long> {

	@Query(value = "select * from vasputaway where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<VasPutawayVO> findAllVasPutaway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select * from vasputaway where vasputawayid=?1")
	List<VasPutawayVO> findDeliveryChallanById(Long id);

}
