package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.SalesReturnVO;

public interface SalesReturnRepo extends JpaRepository<SalesReturnVO, Long> {

	@Query(nativeQuery = true, value = "select * from salesreturn where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<SalesReturnVO> findAllSalesReturn(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true,value = "select * from salesreturn where salesreturnid=?1")
	SalesReturnVO findSalesReturnById(Long id);

	@Query(nativeQuery = true,value = "select d.partno,d.partdesc,d.sku,d.pickqty from pickrequestdetails d , pickrequest p where p.pickrequestid=d.pickrequestid and p.docid=?1 and p.client =?2 and p.orgid =?3 and p.branchcode=?4")
	Set<Object[]> findSalesReturnFillGridDetails(String docId, String client, Long orgId, String branchCode);

	@Query(nativeQuery = true,value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getSalesReturnDocId(Long orgId,String finYear, String branchCode, String client, String screenCode);
	
}
