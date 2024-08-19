package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.VasPickVO;

public interface VasPickRepo extends JpaRepository<VasPickVO, Long> {

	@Query(nativeQuery =true,value = "select * from vaspick v where v.orgid=?1 and v.branchcode=?2 and v.client=?3 and v.branch=?4 and v.finYear=?5 and v.warehouse=?6")
	List<VasPickVO> findALLVasPick(Long orgId, String branchCode, String client, String branch, String finYear,
			String warehouse);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getVasPickDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);


	@Query(nativeQuery = true, value = "SELECT "
	        + "    partdesc, "
	        + "    partno, "
	        + "    sku, "
	        + "    bin, "
	        + "    batch, "
	        + "    batchdate, "
	        + "    grndate, "
	        + "    grnno, "
	        + "    bintype, "
	        + "    core, "
	        + "    expdate, "
	        + "    qcflag, "
	        + "    stockdate, "
	        + "    lotno, "
	        + "    binclass, "
	        + "    SUM(sqty) AS avalQty "
	        + "FROM "
	        + "    stockdetails "
	        + "WHERE "
	        + "    orgid = ?1 "
	        + "    AND branchcode = ?2 "
	        + "    AND client = ?3 "
	        + "    AND warehouse = ?4 "
	        + "    AND branch = ?6 "
	        + "    AND bintype = ?7 "
	        + "    AND status = ?5 "
	        + "GROUP BY "
	        + "    partdesc, "
	        + "    partno, "
	        + "    sku, "
	        + "    bin, "
	        + "    batch, "
	        + "    batchdate, "
	        + "    grndate, "
	        + "    grnno, "
	        + "    bintype, "
	        + "    core, "
	        + "    expdate, "
	        + "    qcflag, "
	        + "    stockdate, "
	        + "    lotno, "
	        + "    binclass "
	        + "HAVING "
	        + "    SUM(sqty) > 0")
	Set<Object[]> getVasPicGrid(Long orgId, String branchCode, String client, String warehouse, String stateStatus,
			String branch, String bintype);


	
}
