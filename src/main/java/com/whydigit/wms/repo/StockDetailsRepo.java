package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockDetailsVO;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetailsVO, Long> {

	@Query(nativeQuery = true, value = "select sum(sqty) from stockdetails  WHERE orgid = ?1 and pckey='CHILD' AND client = ?3 AND branchcode = ?2 AND warehouse = ?5 AND partno =?4 and grnno=?6 and bin='BULK' AND status = 'V';")
	Set<Object[]> getQtyDetais(Long orgId, String branchCode, String client, String partNo, String warehouse,
			String grnno);

	@Query(nativeQuery = true, value = "select sum(a.sqty)sqty from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status='R' and bin=?5 and partno=?6 and grnno=?7\r\n"
			+ "and batch=?8 or batch is null \r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
	int getAvlQty(Long orgId, String branchCode, String warehouse, String client, String fromBin, String partNo,
			String grnNo, String batchNo);

	@Query(nativeQuery = true, value = "select sum(a.sqty)sqty from\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?9 and bin=?5 and partno=?6 and grnno=?7\r\n"
			+ "and batch=?8 or batch is null\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0) a")
	int getAvlQtyforVasPick(Long orgId, String branchCode, String warehouse, String client, String fromBin,
			String partNo, String grnNo, String batchNo, String status);

	@Query(value = "select batch,expdate from stockdetails where orgid=?1 and branchcode=?2 and client=?3 and status='R'and warehouse=?4 and partno=?5\r\n"
			+ "		group by batch,expdate", nativeQuery = true)
	Set<Object[]> getDetails(Long orgId, String branchCode, String client, String warehouse, String partNo);

	@Query(nativeQuery = true, value = "select sum(a.sqty)sqty from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status='V' and bin=?5 and partno=?6 and grnno=?7\r\n"
			+ "and batch=?8 or batch is null\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
	int getAvlQtyforVasPutaway(Long orgId, String branchCode, String warehouse, String client, String fromBin,
			String partNo, String grnNo, String batchNo);

	@Query(nativeQuery = true, value = "select cast(a.sqty as unsigned)sqty from(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 AND PARTNO=?6 and grnno=?7 and batch=?8 or batch is null\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            ) a")
	Integer findAvlQtyForLocationMovement(Long orgId, String branch, String branchCode, String client, String bin,
			String partNo, String grnNo, String batchNo);

	@Query(nativeQuery = true, value = "SELECT CAST(a.sqty AS UNSIGNED) AS sqty " + "FROM (" + "    SELECT "
			+ "        SUM(sqty) AS sqty " + "    FROM stockdetails " + "    WHERE " + "        orgid = ?1 "
			+ "        AND branchcode = ?2 " + "        AND client = ?3 " + "        AND warehouse = ?4 "
			+ "        AND bin = ?8 " + "        AND partno = ?5 " + "        AND grnno = ?6 "
			+ "        AND (batch = ?7 or batch is null) " + "        AND status = 'R' "
			+ "        AND pckey = 'CHILD' " + "    GROUP BY "
			+ "        partno, partdesc, sku, grnno, grndate, batch, batchdate, expdate, "
			+ "        bintype, binclass, celltype, core, bin, qcflag " + "    HAVING SUM(sqty) > 0 " + ") a")
	int getKittingQtyDetails(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String grnNo, String batch, String bin);

	@Query(nativeQuery = true, value = "select partno,partdesc,sku from stockdetails where orgid=?1 and branchcode=?2 and client=?3 and status='R'and warehouse=?4\r\n"
			+ "		group by partno,partdesc,sku")
	Set<Object[]> getPartNo(Long orgId, String branchCode, String client, String warehouse);

	@Query(nativeQuery = true, value = "select cast(sum(sqty) as unsigned)sqty from stockdetails where orgid=?1 and branchcode=?2 and client=?3 and status='R'and warehouse=?4 and partno=?5 and batch=?6 or batch is null")
	int getAvlQtyforBuyerOrder(Long orgId, String branchCode, String client, String warehouse, String partNo,
			String batchNo);

	@Query(nativeQuery = true, value = "SELECT b.partno, \r\n" + "       b.partdesc, \r\n"
			+ "       COALESCE(SUM(a.sqty), 0) AS avlqty \r\n" + "FROM material b \r\n"
			+ "LEFT OUTER JOIN stockdetails a \r\n"
			+ "ON a.partno = b.partno and a.orgid=b.orgid and a.customer=b.customer and b.cbranch=a.branchcode or b.cbranch='ALL' and a.client=b.client\r\n"
			+ "  AND a.orgid = ?1 \r\n" + "  AND a.branchcode = ?2 \r\n" + "  AND a.warehouse = ?3  \r\n"
			+ "  AND a.customer = ?4 \r\n" + "  AND a.client = ?5 \r\n" + "  AND a.stockdate <= DATE(NOW()) \r\n"
			+ "WHERE (b.partno = ?6 OR 'ALL' = ?6) \r\n" + "GROUP BY b.partno, b.partdesc")
	Set<Object[]> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo);

	@Query(nativeQuery = true, value = "SELECT bin, batch, partno, partdesc, status, COALESCE(SUM(sqty), 0) AS avlqty\r\n"
			+ "FROM stockdetails\r\n" + "WHERE orgid = ?1\r\n" + "  AND branchcode = ?2\r\n"
			+ "  AND warehouse = ?3\r\n" + "  AND customer = ?4\r\n" + "  AND client = ?5\r\n"
			+ "  AND stockdate <= CURDATE()\r\n" + "  AND (partno = ?6 OR ?6 = 'ALL')\r\n"
			+ "  AND (batch = ?7 OR ?7 = 'ALL')\r\n" + "  AND (bin = ?8 OR ?8 = 'ALL')\r\n"
			+ "  AND (status = ?9 OR ?9 = 'ALL')\r\n" + "GROUP BY bin, batch, partno, partdesc, status")
	Set<Object[]> findStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo, String batch, String bin, String status);

	@Query(nativeQuery = true, value = "select a.partno from(\r\n"
			+ "SELECT b.bin, b.batch, b.partno, b.partdesc, b.status, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "FROM stockdetails b\r\n" + "WHERE b.orgid = ?1 \r\n" + "  AND b.branchcode = ?2 \r\n"
			+ "  AND b.warehouse = ?3\r\n" + "  AND b.customer = ?4\r\n" + "  AND b.client = ?5\r\n"
			+ "  AND b.stockdate <= CURDATE()\r\n"
			+ "GROUP BY b.bin, b.batch, b.partno, b.partdesc, status having sum(b.sqty)>0)a group by a.partno")
	Set<Object[]> findPartNoForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client);

	@Query(nativeQuery = true, value = "select a.batch from(\r\n"
			+ "SELECT b.bin, b.batch, b.partno, b.partdesc, b.status, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "FROM stockdetails b\r\n" + "WHERE b.orgid = ?1 \r\n" + "  AND b.branchcode = ?2 \r\n"
			+ "  AND b.warehouse = ?3\r\n" + "  AND b.customer = ?4\r\n" + "  AND b.client = ?5\r\n"
			+ "  AND (b.partno = ?6 OR 'ALL' = ?6) \r\n" + "  AND b.stockdate <= CURDATE()\r\n"
			+ "GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status having sum(b.sqty)>0)a group by a.batch")
	Set<Object[]> findBatchForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo);

	@Query(nativeQuery = true, value = "select a.bin from(\r\n"
			+ "SELECT b.bin, b.batch, b.partno, b.partdesc, b.status, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "FROM stockdetails b\r\n" + "WHERE b.orgid = ?1 \r\n" + "  AND b.branchcode = ?2 \r\n"
			+ "  AND b.warehouse = ?3\r\n" + "  AND b.customer = ?4\r\n" + "  AND b.client = ?5\r\n"
			+ "  AND (b.partno = ?6 OR 'ALL' = ?6) \r\n" + "  AND (b.batch = ?7 OR 'ALL' = ?7)\r\n"
			+ "  AND b.stockdate <= CURDATE()\r\n"
			+ "GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status having sum(b.sqty)>0)a group by a.bin")
	Set<Object[]> findBinForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo, String batch);

	@Query(nativeQuery = true, value = "SELECT \r\n" + "    CASE \r\n"
			+ "        WHEN a.status = 'R' THEN 'Release'\r\n" + "        WHEN a.status = 'V' THEN 'Vas'\r\n"
			+ "        WHEN a.status = 'H' THEN 'Hold'\r\n" + "        else 'Defective'\r\n" + "    END AS status\r\n"
			+ "FROM (\r\n"
			+ "    SELECT b.bin, b.batch, b.partno, b.partdesc, b.status, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "    FROM stockdetails b\r\n" + "    WHERE b.orgid = ?1 \r\n" + "  AND b.branchcode = ?2 \r\n"
			+ "  AND b.warehouse = ?3\r\n" + "  AND b.customer = ?4\r\n" + "  AND b.client = ?5\r\n"
			+ "  AND (b.partno = ?6 OR 'ALL' = ?6) \r\n" + "  AND (b.batch = ?7 OR 'ALL' = ?7)\r\n"
			+ "  and (b.bin=?8 or 'ALL'=?8)\r\n" + "      AND b.stockdate <= CURDATE()\r\n"
			+ "    GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status\r\n" + "    HAVING SUM(b.sqty) > 0\r\n"
			+ ") a\r\n" + "GROUP BY a.status")
	Set<Object[]> findStatusForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin);
}