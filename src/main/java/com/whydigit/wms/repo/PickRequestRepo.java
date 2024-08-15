package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PickRequestVO;

@Repository
public interface PickRequestRepo extends JpaRepository<PickRequestVO, Long> {
	@Query(nativeQuery = true, value = "select * from pickrequest where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<PickRequestVO> findAllPickRequest(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select * from pickrequest where pickrequestid=?1")
	PickRequestVO findPickRequestById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getPickRequestDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true,value="select orderno,orderdate from buyerorder where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5")
	Set<Object[]> findBuyerRefNoFromBuyerOrderForPickRequest(Long orgId, String finYear, String branch,
			String branchCode, String client);

}
