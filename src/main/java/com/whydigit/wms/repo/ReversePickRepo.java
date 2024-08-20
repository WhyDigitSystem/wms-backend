package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.ReversePickVO;


@Repository
public interface ReversePickRepo extends JpaRepository<ReversePickVO, Long>{

	@Query(nativeQuery = true ,value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	String getReversePickDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery =true,value ="SELECT * FROM reversepick where orgid=?1 and client=2 and branch=?3 and branchcode=?4 and finyear=?5 and warehouse=?6")
	List<ReversePickVO> getReversePickDetails(Long orgId, String client, String branch, String branchCode,
			String finYear, String warehouse);

}
