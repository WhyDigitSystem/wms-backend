package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.GatePassInVO;

@Repository
public interface GatePassInRepo extends JpaRepository<GatePassInVO, Long> {


	@Query(nativeQuery = true, value = "SELECT lrnohaw ,invoiceno , invoicedate ,c.partno,c.partdesc ,b.sku\r\n"
			+ ",invqty,recqty ,damageqty,batchno,b.weight ,rate ,b.rowno sno\r\n"
			+ "FROM gatepassin a,gatepassindetails b,material c WHERE a.branchcode=?5 and a.client = ?2\r\n"
			+ "and entryno = ?3 and a.docid = ?4 and a.orgid = ?1 and a.gatepassinid=b.gatepassindetailsid and\r\n"
			+ "b.partcode=c.partno and b.partdescription = c.partdesc")
	Set<Object[]> findGatePassDetailsByGatePassNo(Long orgId, String client, String entryno, Long docid,
			String branchcode);

	boolean existsByEntryNoAndOrgIdAndBranchCodeAndClient(String entryNo, Long orgId, String branchCode, String client);
	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getGatePassInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	

}
