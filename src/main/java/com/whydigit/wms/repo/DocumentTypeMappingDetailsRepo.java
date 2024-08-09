package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;

public interface DocumentTypeMappingDetailsRepo extends JpaRepository<DocumentTypeMappingDetailsVO, Long> {


	@Query(nativeQuery = true ,value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.branch=?1 and a.client=?2 and a.finyear=?3 and a.screencode=?4")
	String getGRNDocId(String branch, String client, String finYear, String screenCode);

	@Query(value = "select a from DocumentTypeMappingDetailsVO a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	DocumentTypeMappingDetailsVO findByBranchAndClientAndFinYearAndScreenCode(Long orgId, String finYear,String branchCode, String client, String screenCode);


	@Query(nativeQuery = true, value = "select * from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	DocumentTypeMappingDetailsVO findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(Long orgId, String finYear,
	String branchCode, String client, String screenCode);


}
