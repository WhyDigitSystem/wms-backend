package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CycleCountVO;

public interface CycleCountRepo extends JpaRepository<CycleCountVO, Long>{

	@Query(nativeQuery = true ,value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	String getCycleCountInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);


}
