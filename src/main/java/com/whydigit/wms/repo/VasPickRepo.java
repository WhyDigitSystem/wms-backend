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

	@Query(nativeQuery = true, value = "SELECT\n"
			+ "    partdesc,\n"
			+ "    partno,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    batchdate,\n"
			+ "    grndate,\n"
			+ "    grnno,\n"
			+ "    bintype,\n"
			+ "    core,\n"
			+ "    expdate,\n"
			+ "    qcflag,\n"
			+ "    stockdate,\n"
			+ "    SUM(sqty) AS avalQty\n"
			+ "FROM\n"
			+ "    stockdetails\n"
			+ "WHERE\n"
			+ "    orgid =?1\n"
			+ "    AND branchcode =?2\n"
			+ "    AND client =?3\n"
			+ "    AND warehouse =?4\n"
			+ "    AND branch =?6\n"
			+ "    AND bintype =?7\n"
			+ "    AND status=?5\n"
			+ "GROUP BY\n"
			+ "    partdesc,\n"
			+ "    partno,\n"
			+ "    sku,\n"
			+ "    bin,\n"
			+ "    batch,\n"
			+ "    batchdate,\n"
			+ "    grndate,\n"
			+ "    grnno,\n"
			+ "    bintype,\n"
			+ "    core,\n"
			+ "    expdate,\n"
			+ "    qcflag,\n"
			+ "    stockdate\n"
			+ "HAVING\n"
			+ "    SUM(sqty) > 0")
	Set<Object[]> getVasPicGrid(Long orgId, String branchCode, String client, String warehouse, String stateStatus,
			String branch, String bintype);


	
}
