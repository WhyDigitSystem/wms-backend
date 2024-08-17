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

	@Query(nativeQuery = true,value="select a.docid,a.docdate,b.partno,b.partdesc,sum(b.pickqty) as shippedqty from pickrequest a , pickrequestdetails b where a.pickrequestid= b.pickrequestid and a.orgid=?1 and a.branch=?2 and a.branchcode=?3 and a.client=?4 and a.warehouse=?5 and buyerorderno=?6 group by a.docid,a.docdate,b.partno,b.partdesc having sum(b.pickqty) >0 ")
	Set<Object[]> getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(Long orgId,
			String branch, String branchCode, String client, String warehouse, String buyerOrderNo);


	@Query(nativeQuery = true,value="select * from pickrequest where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and warehouse=?5 and status='Confirm' and buyerorderno not in(select buyerorderno from deliverychallan where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and warehouse=?5)")
	List<PickRequestVO> findAllPickRequestFromDeliveryChallan(Long orgId, String branch,
			
			String branchCode, String client, String warehouse);

}
