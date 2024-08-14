package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;

public interface PutAwayRepo extends JpaRepository<PutAwayVO, Long> {

	@Query(nativeQuery = true, value = "select * from grn where docid in(\r\n"
			+ "select grnno from (\r\n"
			+ "select branch,branchcode,customer,client,orgid,warehouse,grnno,partno,partdesc,sku,sum(sqty) from handlingstockin\r\n"
			+ "group by branch,branchcode,customer,client,orgid,warehouse,grnno,partno,partdesc,sku having sum(sqty)>0)a where a.orgid=?1 and a.client=?2 \r\n"
			+ "and branch=?3 and a.branchcode=?4 and a.warehouse=?5) order by docid asc")
	List<GrnVO> findGrnNoForPutAway(Long orgId, String client, String branch, String branchcode,String warehouse);

	@Query(value = "select * from putaway where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<PutAwayVO> findAllPutAway(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getPutAwayDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);


}
