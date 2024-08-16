package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CycleCountVO;

public interface CycleCountRepo extends JpaRepository<CycleCountVO, Long>{

	
	@Query(nativeQuery = true ,value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	String getCycleCountInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery=true,value ="select * from cyclecount where orgid=?1 and client=?2 and branch=?3 and branchcode=?4 and finyear=?5 and warehouse=?6")
	List<CycleCountVO> findAllCycleCount(Long orgId, String client, String branch, String branchCode, String finYear,
			String warehouse);

	@Query(nativeQuery =true,value ="SELECT \n"
			+ "    partno,\n"
			+ "    partdesc,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    batchdate,\n"
			+ "    lotno,\n"
			+ "    grnno,\n"
			+ "    grndate,\n"
			+ "    binclass,\n"
			+ "    bintype,\n"
			+ "    status,\n"
			+ "    qcflag,\n"
			+ "    stockdate,\n"
			+ "    expdate,\n"
			+ "    core,\n"
			+ "    celltype,\n"
			+ "    SUM(sqty) AS total_sqty\n"
			+ "FROM \n"
			+ "    stockdetails\n"
			+ "WHERE \n"
			+ "    orgid=?1 and client=?3 and warehouse=?4  and branchcode=?2 \n"
			+ "GROUP BY \n"
			+ "    partno,\n"
			+ "    partdesc,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    batchdate,\n"
			+ "    lotno,\n"
			+ "    grnno,\n"
			+ "    grndate,\n"
			+ "    binclass,\n"
			+ "    bintype,\n"
			+ "    status,\n"
			+ "    qcflag,\n"
			+ "    stockdate,\n"
			+ "    expdate,\n"
			+ "    core,\n"
			+ "    celltype;")
	Set<Object[]> getCycleCountGrid(Long orgId, String branchCode, String client, String warehouse);

}
