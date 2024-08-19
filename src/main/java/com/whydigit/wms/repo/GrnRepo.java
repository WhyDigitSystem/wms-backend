package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GrnVO;

public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(value="select a from GrnVO a where a.orgId=?1 and a.finYear=?2 and a.branch=?3 and a.branchCode=?4 and a.client=?5 and a.warehouse=?6 order by a.docId desc")
	List<GrnVO> findAllGrnDetails(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);


	@Query(nativeQuery = true ,value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	String getGRNDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	boolean existsByEntryNoAndOrgIdAndClientAndBranchCodeAndWarehouse(String entryNo, Long orgId, String client,
			String branchCode, String warehouse);

	@Query(value = "select a from GrnVO a where a.docId=?1")
	GrnVO findByDocId(String grnNo);


	@Query(nativeQuery = true, value = "select a.* from grn a where a.docid in(\r\n"
			+ "select grnno from (\r\n"
			+ "select branch,branchcode,customer,client,orgid,warehouse,grnno,partno,partdesc,sku,sum(sqty) from handlingstockin\r\n"
			+ "group by branch,branchcode,customer,client,orgid,warehouse,grnno,partno,partdesc,sku having sum(sqty)>0)a where a.orgid=?1 and a.client=?2 \r\n"
			+ "and branch=?3 and a.branchcode=?4 and a.warehouse=?5) order by a.docid asc")
	List<GrnVO> findGrnNoDetailsForPutAway(Long orgId, String client, String branch, String branchcode,String warehouse);


}
