package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayVO;

@Repository
public interface VasPutawayRepo extends JpaRepository<VasPutawayVO, Long> {

	@Query(value = "select * from vasputaway where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<VasPutawayVO> findAllVasPutaway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select * from vasputaway where vasputawayid=?1")
	VasPutawayVO findDeliveryChallanById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getVasPutawayDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true,value ="select docid from vaspick where orgid=?1 and branch=?2 and client=?3 and finyear=?4 and cancel=0 and docid NOT IN (SELECT VASPICKNO FROM vasputaway)")
	Set<Object[]> findDocIdFromVasPickForVasPutaway(Long orgId, String branch, String client,String finYear);

}
