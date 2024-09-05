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

	@Query(nativeQuery = true, value = "select a.partno,a.partdesc,a.sku from(\r\n"
			+ "select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 \r\n"
			+ " and status='R'\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag having sum(sqty)>0 )a group by a.partno,partdesc,a.sku")
	Set<Object[]> getPartNoByCycleCount(Long orgId, String branchCode, String client, String warehouse);

	@Query(nativeQuery = true, value = "select a.grnno,a.grndate from(\r\n"
			+ "select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 and status='R'\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag having sum(sqty)>0 )a group by a.grnno,a.grndate")
	Set<Object[]> getGrnNo(Long orgId, String branchCode, String client, String warehouse, String partNo);

	@Query(nativeQuery = true, value = "select a.batch,a.batchdate,a.expdate from(\r\n"
			+ "select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 and grnno=?6\r\n"
			+ "and status='R'\r\n"
			+ "group by partno,partdesc,sku,grnno,expdate,grndate,batch,batchdate,bintype,bin,binclass,celltype,core,status,qcflag having sum(sqty)>0 )a group by a.batch,a.batchdate,a.expdate")
	Set<Object[]> getBatch(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNO);

	@Query(nativeQuery = true, value = "select a.bin,a.bintype,a.celltype,a.binclass,a.core from(\r\n"
			+ "select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 and grnno=?6\r\n"
			+ " and batch=?7 or batch IS null and status='R'\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,bin,binclass,celltype,core,status,qcflag having sum(sqty)>0 )a group by a.bin,a.bintype,a.celltype,a.binclass,a.core")
	Set<Object[]> getBinDetails(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String grnNO, String batch);

	@Query(value = "select a.sqty from(\r\n"
			+ "select sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 and grnno=?6\r\n"
			+ " and batch=?7 or batch IS null  and bin=?8 and status='R'\r\n"
			+ " having sum(sqty)>0 )a", nativeQuery = true)
	Set<Object[]> getAvlQty(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNO,
			String batch, String bin);
	
	@Query(value = "select a.sqty from(\r\n"
			+ "select sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 and grnno=?6\r\n"
			+ " and batch=?7 or batch IS null  and bin=?8 and status='R'\r\n"
			+ " having sum(sqty)>0 )a", nativeQuery = true)
	int getAvailQty(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNo,
			String batchNo, String bin);

}
