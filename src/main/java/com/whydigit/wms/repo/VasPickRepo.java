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

	@Query(nativeQuery =true,value ="SELECT \n"
			+ "    SUM(sqty) AS avalQty,\n"
			+ "    partdesc,\n"
			+ "    partno,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    grnno,\n"
			+ "    lotno\n"
			+ "FROM \n"
			+ "    stockdetails\n"
			+ "WHERE \n"
			+ "    branch =?2\n"
			+ "    AND branchcode =?3\n"
			+ "    AND client =?4\n"
			+ "    AND warehouse =?5\n"
			+ "    AND orgid =?1\n"
			+ "    AND status='H'\n"
			+ "GROUP BY \n"
			+ "    partdesc,\n"
			+ "    partno,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    grnno,\n"
			+ "    lotno\n"
			+ "    having  SUM(sqty)>0\n"
			+ "")
	Set<Object[]> getVaspickGridDetals(Long orgId, String branch, String branchCode, String client, String warehouse);

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
			+ "    orgid=?1 and client=?3 and warehouse=?4 and status=?5 and branchcode=?2\n"
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
			+ "    celltype")
	Set<Object[]> getVasPicGrid(Long orgId, String branchCode, String client, String warehouse, char stateStatus);

	
}
