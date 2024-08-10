package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;

@Repository
public interface VasPutawayDetailsRepo extends JpaRepository<VasPutawayDetailsVO, Long> {

	List<VasPutawayDetailsVO> findByVasPutawayVO(VasPutawayVO vasPutawayVO);

	@Query(nativeQuery = true,value ="select a.partno,a.partdescrrption,a.grnno,a.picqty,a.bin,a.sku from vaspickdetails a,vaspick b where a.vaspicid=b.vaspicid and orgid=?1 and branch=?2 and client=?3 and docid=?4")
	Set<Object[]> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch, String client,
			String docId);


}
