package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CycleCountVO;

public interface CycleCountRepo extends JpaRepository<CycleCountVO, Long> {

	@Query(nativeQuery = true, value = "select  concat(a.prefixfield ,LPAD(a.lastno, 6, '0')) from m_documenttypemappingdetails a where a.orgId=?1 and a.finYear=?2 and a.branchCode=?3 and a.client=?4 and a.screenCode=?5")
	String getCycleCountInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select * from cyclecount where orgid=?1 and client=?2 and branch=?3 and branchcode=?4 and finyear=?5 and warehouse=?6")
	List<CycleCountVO> findAllCycleCount(Long orgId, String client, String branch, String branchCode, String finYear,
			String warehouse);

	@Query(value = "SELECT " + "    partno, " + "    partdesc, " + "    sku, " + "    bin, " + "    batch, "
			+ "    batchdate, " + "    lotno, " + "    grnno, " + "    grndate, " + "    binclass, " + "    bintype, "
			+ "    status, " + "    qcflag, " + "    expdate, " + "    core, " + "    celltype, "
			+ "    SUM(sqty) AS total_sqty, " + "    ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id " + "FROM "
			+ "    stockdetails " + "WHERE " + "    orgid = ?1 " + "    AND client = ?3 " + "    AND warehouse = ?4 "
			+ "    AND branchcode = ?2 " + "GROUP BY " + "    partno, " + "    partdesc, " + "    sku, " + "    bin, "
			+ "    batch, " + "    batchdate, " + "    lotno, " + "    grnno, " + "    grndate, " + "    binclass, "
			+ "    bintype, " + "    status, " + "    qcflag, " + "    expdate, " + "    core, "
			+ "    celltype ", nativeQuery = true)
	Set<Object[]> getCycleCountGrid(Long orgId, String branchCode, String client, String warehouse);

	@Query(nativeQuery = true, value = "select partno,partdesc,sku from stockdetails where orgid=?1 and client=?3 \n"
			+ "and branchcode=?2 and warehouse=?4 group by partno,partdesc,sku")
	Set<Object[]> getPartNoByCycleCount(Long orgId, String branchCode, String client, String warehouse);

	
	@Query(nativeQuery =true,value ="select grnno,grndate from stockdetails where orgid=?1 and client=?3 \n"
			+ "and branchcode=?2 and warehouse=?4 and partno=?5 group by grnno,grndate")
	Set<Object[]> getGrnNo(Long orgId, String branchCode, String client, String warehouse, String partNo);

	@Query(nativeQuery=true,value ="select batch,batchdate,expdate from stockdetails where orgid=?1 and client=?3 \n"
			+ "and branchcode=?2 and warehouse=?4 and partno=?5 and grnno=?6 group by batch,batchdate,expdate")
	Set<Object[]> getBatch(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNO);

	@Query(nativeQuery =true,value ="select bin,bintype,lotno,celltype from stockdetails  where orgid=?1 and client=?3 \n"
			+ "and branchcode=?2 and warehouse=?4 and partno=?5 and grnno=?6 and batch=?7 group by bin,bintype,lotno,celltype")
	Set<Object[]> getBinDetails(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String grnNO, String batch);

	@Query(nativeQuery =true,value ="select sum(sqty) as avlqty,status from stockdetails  where orgid=1000000001 and client='CASIO' \n"
			+ "and branchcode='MAAW' and warehouse='RAMAMOORTHYNAGER' and partno='P12345' and grnno='G12345' and batch='B12345' and bin=?8 \n"
			+ "and bintype=?8 and lotno=?10 group by status")
	Set<Object[]> getAvlQty(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNO,
			String batch, String bin, String binType, String lotNo);

}
