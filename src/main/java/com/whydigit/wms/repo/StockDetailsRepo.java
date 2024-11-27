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

	@Query(nativeQuery = true, value = "SELECT b.partno, \r\n"
			+ "       b.partdesc, \r\n"
			+ "       COALESCE(SUM(a.sqty), 0) AS avlqty \r\n"
			+ "FROM material b \r\n"
			+ "LEFT OUTER JOIN stockdetails a \r\n"
			+ "ON a.partno = b.partno \r\n"
			+ "   AND a.orgid = b.orgid \r\n"
			+ "   AND a.customer = b.customer \r\n"
			+ "   AND (b.cbranch = a.branchcode OR b.cbranch = 'ALL') \r\n"
			+ "   AND a.client = b.client\r\n"
			+ "   AND a.orgid = ?1\r\n"
			+ "   AND a.branchcode = ?2\r\n"
			+ "   AND a.warehouse = ?3\r\n"
			+ "   AND a.customer = ?4\r\n"
			+ "   AND a.client = ?5\r\n"
			+ "   AND a.stockdate <= DATE(NOW()) \r\n"
			+ "WHERE (b.partno = ?6 OR ?6 = 'ALL') \r\n"
			+ "GROUP BY b.partno, b.partdesc")
	Set<Object[]> getConsolidateStockDetails(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo);

	@Query(nativeQuery = true, value = "SELECT bin, \r\n"
			+ "       batch, \r\n"
			+ "       partno, \r\n"
			+ "       partdesc, \r\n"
			+ "       CASE \r\n"
			+ "           WHEN status = 'R' THEN 'Release'\r\n"
			+ "           WHEN status = 'V' THEN 'Vas'\r\n"
			+ "           WHEN status = 'H' THEN 'Hold'\r\n"
			+ "           ELSE 'Defective'\r\n"
			+ "       END AS status, \r\n"
			+ "       COALESCE(SUM(sqty), 0) AS avlqty\r\n"
			+ "FROM stockdetails\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND branchcode = ?2\r\n"
			+ "  AND warehouse = ?3\r\n"
			+ "  AND customer = ?4\r\n"
			+ "  AND client = ?5\r\n"
			+ "  AND stockdate <= CURDATE()\r\n"
			+ "  AND (partno = ?6 OR 'ALL' = ?6)\r\n"
			+ "  AND (batch = ?7 OR 'ALL' = ?7)\r\n"
			+ "  AND (bin = ?8 OR 'ALL' = ?8)\r\n"
			+ "  AND (status = CASE WHEN 'Release' = ?9 THEN 'R'\r\n"
			+ "                      WHEN 'Vas' = ?9 THEN 'V'\r\n"
			+ "                      WHEN 'Hold' = ?9 THEN 'H'\r\n"
			+ "                      else  'D'\r\n"
			+ "                 END OR 'ALL' = ?9)\r\n"
			+ "GROUP BY bin, batch, partno, partdesc, status having sum(sqty)>0")
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

	@Query(nativeQuery = true, value = "SELECT a.batch \r\n"
			+ "FROM (\r\n"
			+ "    SELECT 'ALL' AS bin, 'ALL' AS batch, 'ALL' AS partno, 'ALL' AS partdesc, 'ALL' AS status, 0 AS avlqty\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT b.bin, \r\n"
			+ "           b.batch, \r\n"
			+ "           b.partno, \r\n"
			+ "           b.partdesc, \r\n"
			+ "           b.status, \r\n"
			+ "           COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "    FROM stockdetails b\r\n"
			+ "    WHERE b.orgid = ?1 \r\n"
			+ "      AND b.branchcode = ?2 \r\n"
			+ "      AND b.warehouse = ?3\r\n"
			+ "      AND b.customer = ?4 \r\n"
			+ "      AND b.client = ?5 \r\n"
			+ "      AND (b.partno = ?6 OR 'ALL' = ?6) \r\n"
			+ "      AND b.stockdate <= CURDATE()\r\n"
			+ "    GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status \r\n"
			+ "    HAVING SUM(b.sqty) > 0\r\n"
			+ ") a \r\n"
			+ "GROUP BY a.batch")
	Set<Object[]> findBatchForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo);

	@Query(nativeQuery = true, value = "SELECT a.bin \r\n"
			+ "FROM (\r\n"
			+ "    SELECT 'ALL' AS bin, 'ALL' AS batch, 'ALL' AS partno, 'ALL' AS partdesc,'ALL' status, 0 AS avlqty\r\n"
			+ "    UNION ALL\r\n"
			+ "    SELECT b.bin, \r\n"
			+ "           b.batch, \r\n"
			+ "           b.partno, \r\n"
			+ "           b.partdesc, \r\n"
			+ "           b.status, \r\n"
			+ "           COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "    FROM stockdetails b\r\n"
			+ "    WHERE b.orgid = ?1\r\n"
			+ "      AND b.branchcode = ?2\r\n"
			+ "      AND b.warehouse = ?3\r\n"
			+ "      AND b.customer = ?4\r\n"
			+ "      AND b.client = ?5\r\n"
			+ "      AND (b.partno = ?6 OR 'ALL' = ?6)\r\n"
			+ "      AND (b.batch = ?7 OR 'ALL' = ?7)\r\n"
			+ "      AND b.stockdate <= CURDATE()\r\n"
			+ "    GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status \r\n"
			+ "    HAVING SUM(b.sqty) > 0\r\n"
			+ ") a \r\n"
			+ "GROUP BY a.bin")
	Set<Object[]> findBinForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse, String customer,
			String client, String partNo, String batch);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    CASE \r\n"
			+ "        WHEN a.status = 'ALL' THEN 'ALL'  -- Handle 'ALL' status explicitly\r\n"
			+ "        WHEN a.status = 'R' THEN 'Release'\r\n"
			+ "        WHEN a.status = 'V' THEN 'Vas'\r\n"
			+ "        WHEN a.status = 'H' THEN 'Hold'\r\n"
			+ "        ELSE 'Defective'\r\n"
			+ "    END AS status\r\n"
			+ "FROM (\r\n"
			+ "    SELECT b.bin, \r\n"
			+ "           b.batch, \r\n"
			+ "           b.partno, \r\n"
			+ "           b.partdesc, \r\n"
			+ "           b.status, \r\n"
			+ "           COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
			+ "    FROM stockdetails b\r\n"
			+ "    WHERE b.orgid = ?1  -- Parameter 1 for orgid\r\n"
			+ "      AND b.branchcode = ?2  -- Parameter 2 for branchcode\r\n"
			+ "      AND b.warehouse = ?3  -- Parameter 3 for warehouse\r\n"
			+ "      AND b.customer = ?4  -- Parameter 4 for customer\r\n"
			+ "      AND b.client = ?5  -- Parameter 5 for client\r\n"
			+ "      AND (b.partno = ?6 OR ?6 = 'ALL')  -- Parameter 6 for partno\r\n"
			+ "      AND (b.batch = ?7 OR ?7 = 'ALL')  -- Parameter 7 for batch\r\n"
			+ "      AND (b.bin = ?8 OR ?8 = 'ALL')  -- Parameter 8 for bin\r\n"
			+ "      AND b.stockdate <= CURDATE()\r\n"
			+ "    GROUP BY b.bin, b.batch, b.partno, b.partdesc, b.status\r\n"
			+ "    HAVING SUM(b.sqty) > 0\r\n"
			+ "\r\n"
			+ "    UNION ALL\r\n"
			+ "\r\n"
			+ "    SELECT 'ALL' AS bin, 'ALL' AS batch, 'ALL' AS partno, 'ALL' AS partdesc, \r\n"
			+ "           'ALL' AS status, 0 AS avlqty\r\n"
			+ ") a\r\n"
			+ "GROUP BY a.status")
	Set<Object[]> findStatusForStockReportBinAndBatchWise(Long orgId, String branchCode, String warehouse,
			String customer, String client, String partNo, String batch, String bin);

@Query(value ="SELECT b.partno, b.partdesc, b.bin, COALESCE(SUM(b.sqty), 0) AS avlqty \r\n"
		+ "FROM stockdetails b \r\n"
		+ "WHERE b.orgid = ?1\r\n"
		+ "AND b.branchcode = ?2\r\n"
		+ "AND (b.bin = ?3 OR 'ALL' = ?3)\r\n"
		+ "AND b.warehouse = ?4\r\n"
		+ "AND b.customer = ?5\r\n"
		+ "AND b.client = ?6\r\n"
		+ "AND b.stockdate <= DATE(NOW()) \r\n"
		+ "AND (b.partno = ?7 OR 'ALL' = ?7)\r\n"
		+ "GROUP BY b.partno, b.partdesc, b.bin \r\n"
		+ "HAVING SUM(b.sqty) > 0 \r\n"
		+ "ORDER BY b.partno, b.bin;\r\n"
		+ "",nativeQuery =true)
Set<Object[]> getStockReportBinWise(Long orgId, String branchCode, String bin, String warehouse, String customer,
		String client, String partNo);

@Query(value="SELECT b.partno, \r\n"
		+ "       b.partdesc, \r\n"
		+ "       b.batch, \r\n"
		+ "       COALESCE(SUM(b.sqty), 0) AS avlqty \r\n"
		+ "FROM stockdetails b \r\n"
		+ "WHERE b.orgid =?1 \r\n"
		+ "  AND b.branchcode =?2 \r\n"
		+ "  AND (b.batch = ?3 OR 'ALL' = ?3) \r\n"
		+ "  AND b.warehouse =?4\r\n"
		+ "  AND b.customer = ?5\r\n"
		+ "  AND b.client =?6\r\n"
		+ "  AND b.stockdate <= DATE(NOW()) \r\n"
		+ "  AND (b.partno =?7 OR 'ALL' = ?7)\r\n"
		+ "GROUP BY b.partno, b.partdesc, b.batch \r\n"
		+ "HAVING avlqty > 0 \r\n"
		+ "ORDER BY b.partno, b.batch, b.partdesc",nativeQuery = true)
Set<Object[]> getStockReportBatchWise(Long orgId, String branchCode, String batch, String warehouse, String customer,
		String client, String partNo);

@Query(value="SELECT a.partno\r\n"
		+ "FROM (\r\n"
		+ "    SELECT b.partno, \r\n"
		+ "           b.partdesc, \r\n"
		+ "           b.batch, \r\n"
		+ "           COALESCE(SUM(b.sqty), 0) AS avlqty \r\n"
		+ "    FROM stockdetails b \r\n"
		+ "    WHERE b.orgid =?1\r\n"
		+ "      AND b.branchcode =?2\r\n"
		+ "      AND b.warehouse =?3\r\n"
		+ "      AND b.customer =?4\r\n"
		+ "      AND b.client =?5\r\n"
		+ "      AND b.stockdate <= DATE(NOW())\r\n"
		+ "    GROUP BY b.partno, b.partdesc, b.batch\r\n"
		+ "    HAVING avlqty > 0 order by  b.partno, b.batch, b.partdesc\r\n"
		+ ") a\r\n"
		+ "GROUP BY a.partno",nativeQuery = true)
Set<Object[]> getPartNoFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
		String client);

@Query(nativeQuery = true,value="SELECT a.partno, a.partdesc, a.refno, a.sourcescreenname, \r\n"
		+ "       SUM(openingqty) AS oqty, \r\n"
		+ "       SUM(recqty) AS recqty, \r\n"
		+ "       SUM(ABS(dispatchqty)) AS dqty, \r\n"
		+ "       SUM(closingqty) AS cqty \r\n"
		+ "FROM (\r\n"
		+ "    SELECT partno, partdesc, refno, sourcescreenname, \r\n"
		+ "           SUM(sqty) AS openingqty, 0 AS recqty, 0 AS dispatchqty, 0 AS closingqty, createdon\r\n"
		+ "    FROM stockdetails \r\n"
		+ "    WHERE orgid = ?1\r\n"
		+ "      AND branchcode = ?2\r\n"
		+ "      AND warehouse = ?3\r\n"
		+ "      AND customer = ?4\r\n"
		+ "      AND client = ?5\r\n"
		+ "      AND stockdate < ?6\r\n"
		+ "      AND (partno = ?8 OR 'ALL' = ?8) \r\n"
		+ "    GROUP BY partno, partdesc, refno, sourcescreenname, createdon\r\n"
		+ "    HAVING SUM(sqty) > 0\r\n"
		+ "\r\n"
		+ "    UNION\r\n"
		+ "\r\n"
		+ "    SELECT partno, partdesc, refno, sourcescreenname, \r\n"
		+ "           0 AS openingqty, SUM(sqty) AS recqty, 0 AS dispatchqty, 0 AS closingqty, createdon \r\n"
		+ "    FROM stockdetails \r\n"
		+ "    WHERE orgid = ?1\r\n"
		+ "      AND branchcode = ?2\r\n"
		+ "      AND warehouse = ?3\r\n"
		+ "      AND customer = ?4\r\n"
		+ "      AND client = ?5\r\n"
		+ "      AND stockdate BETWEEN ?6 AND ?7\r\n"
		+ "      AND sqty > 0 \r\n"
		+ "      AND sourcescreenname NOT IN ('VAS PICK')\r\n"
		+ "      AND (partno = ?8 OR 'ALL' = ?8)\r\n"
		+ "    GROUP BY partno, partdesc, refno, sourcescreenname, createdon\r\n"
		+ "\r\n"
		+ "    UNION\r\n"
		+ "\r\n"
		+ "    SELECT partno, partdesc, refno, sourcescreenname, \r\n"
		+ "           0 AS openingqty, 0 AS recqty, SUM(sqty) AS dispatchqty, 0 AS closingqty, createdon\r\n"
		+ "    FROM stockdetails \r\n"
		+ "    WHERE orgid = ?1\r\n"
		+ "      AND branchcode = ?2\r\n"
		+ "      AND warehouse = ?3\r\n"
		+ "      AND customer = ?4\r\n"
		+ "      AND client = ?5\r\n"
		+ "      AND stockdate BETWEEN ?6 AND ?7\r\n"
		+ "      AND sqty < 0 \r\n"
		+ "      AND sourcescreenname NOT IN ('VAS PUTAWAY')\r\n"
		+ "      AND (partno = ?8 OR 'ALL' = ?8)\r\n"
		+ "    GROUP BY partno, partdesc, refno, sourcescreenname, createdon\r\n"
		+ "\r\n"
		+ "    UNION\r\n"
		+ "\r\n"
		+ "    SELECT partno, partdesc, refno, sourcescreenname, \r\n"
		+ "           0 AS openingqty, 0 AS recqty, 0 AS dispatchqty, SUM(sqty) AS closingqty, createdon \r\n"
		+ "    FROM stockdetails \r\n"
		+ "    WHERE orgid = ?1\r\n"
		+ "      AND branchcode = ?2\r\n"
		+ "      AND warehouse = ?3\r\n"
		+ "      AND customer = ?4\r\n"
		+ "      AND client = ?5\r\n"
		+ "      AND stockdate <= ?7\r\n"
		+ "      AND (partno = ?8 OR 'ALL' = ?8)\r\n"
		+ "    GROUP BY partno, partdesc, refno, sourcescreenname, createdon\r\n"
		+ "    HAVING SUM(sqty) > 0\r\n"
		+ "\r\n"
		+ ") a \r\n"
		+ "GROUP BY a.partno, a.partdesc, a.refno, a.sourcescreenname, a.createdon \r\n"
		+ "ORDER BY a.createdon ASC")
Set<Object[]>getLedgerDetails(Long orgId, String branchCode, String warehouse, String customer,
		String client, String startDate,String endDate,String partNo);

@Query(value ="select a.partno from(\r\n"
		+ "SELECT b.partno, b.partdesc, b.bin, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
		+ "FROM stockdetails b\r\n"
		+ "WHERE b.orgid = ?1 \r\n"
		+ "  AND b.branchcode = ?2 \r\n"
		+ "  AND b.warehouse = ?3\r\n"
		+ "  AND b.customer = ?4\r\n"
		+ "  AND b.client = ?5\r\n"
		+ "AND b.stockdate <= DATE(NOW())\r\n"
		+ "GROUP BY b.partno, b.partdesc, b.bin\r\n"
		+ "HAVING SUM(b.sqty) > 0\r\n"
		+ "ORDER BY b.partno, b.bin)a group by a.partno" ,nativeQuery =true)
Set<Object[]> getStockPartNoBatch(Long orgId, String branchCode, String warehouse, String customer, String client);

@Query(value ="SELECT a.bin \r\n"
		+ "FROM (\r\n"
		+ "    SELECT 'ALL' AS partno, 'ALL' AS partdesc, 'ALL' AS bin, 0 AS avlqty\r\n"
		+ "    UNION ALL\r\n"
		+ "    SELECT b.partno, \r\n"
		+ "           b.partdesc, \r\n"
		+ "           b.bin, \r\n"
		+ "           COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
		+ "    FROM stockdetails b\r\n"
		+ "    WHERE b.orgid = ?1 \r\n"
		+ "      AND b.branchcode = ?2 \r\n"
		+ "      AND b.warehouse = ?3\r\n"
		+ "      AND b.customer = ?4\r\n"
		+ "      AND b.client = ?5\r\n"
		+ "      AND b.stockdate <= DATE(NOW())\r\n"
		+ "      AND (b.partno = ?6 OR 'ALL' = ?6)\r\n"
		+ "    GROUP BY b.partno, b.partdesc, b.bin\r\n"
		+ "    HAVING SUM(b.sqty) > 0\r\n"
		+ ") a \r\n"
		+ "GROUP BY a.bin",nativeQuery =true)
Set<Object[]> getBatchNoBin(Long orgId, String branchCode, String warehouse, String customer, String client,
		String partNo);

@Query(nativeQuery = true,value="SELECT a.batch\r\n"
		+ "FROM (\r\n"
		+ "    SELECT 'ALL' AS partno, 'ALL' AS partdesc, 'ALL' AS batch, 0 AS avlqty\r\n"
		+ "    UNION\r\n"
		+ "    SELECT b.partno, \r\n"
		+ "           b.partdesc, \r\n"
		+ "           b.batch, \r\n"
		+ "           COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
		+ "    FROM stockdetails b\r\n"
		+ "    WHERE b.orgid = ?1\r\n"
		+ "      AND b.branchcode = ?2\r\n"
		+ "      AND b.warehouse = ?3\r\n"
		+ "      AND b.customer = ?4\r\n"
		+ "      AND b.client = ?5\r\n"
		+ "      AND (b.partno = ?6 OR 'ALL' = ?6)\r\n"
		+ "      AND b.stockdate <= DATE(NOW())\r\n"
		+ "    GROUP BY b.partno, b.partdesc, b.batch\r\n"
		+ "    HAVING avlqty > 0\r\n"
		+ ") a\r\n"
		+ "GROUP BY a.batch")
Set<Object[]> getBatchFromBatchWiseReport(Long orgId, String branchCode,String warehouse, String customer,
		String client,String partNo);

boolean existsByPartnoAndOrgIdAndClient(String partNo, Long orgId, String client);

boolean existsByPartnoAndBatchAndOrgIdAndClient(String partNo, String batchNo, Long orgId, String client);


@Query(value = "SELECT\r\n"
		+ "    s.partno,\r\n"
		+ "    s.partdesc,\r\n"
		+ "    s.sku,\r\n"
		+ "    s.total_sqtyt AS qty,\r\n"
		+ "    'Low Qty' AS status\r\n"
		+ "FROM\r\n"
		+ "    (SELECT \r\n"
		+ "        partno,\r\n"
		+ "        partdesc,\r\n"
		+ "        sku,\r\n"
		+ "        SUM(sqty) AS total_sqtyt\r\n"
		+ "     FROM stockdetails\r\n"
		+ "     WHERE\r\n"
		+ "        status = 'R'\r\n"
		+ "        AND orgid =?1\r\n"
		+ "    AND client =?3\r\n"
		+ "    AND branchcode =?2\r\n"
		+ "    AND warehouse =?4\r\n"
		+ "     GROUP BY \r\n"
		+ "        partno, partdesc, sku\r\n"
		+ "    ) s\r\n"
		+ "JOIN \r\n"
		+ "    material m\r\n"
		+ "ON \r\n"
		+ "    s.partno = m.partno\r\n"
		+ "WHERE \r\n"
		+ "     m.orgid =?1\r\n"
		+ "    AND m.client =?3\r\n"
		+ "    AND m.branchcode =?2\r\n"
		+ "    AND m.warehouse =?4\r\n"
		+ "    AND s.total_sqtyt <= m.lowqty\r\n"
		,nativeQuery =true)
Set<Object[]> getStock(Long orgId,String branchCode, String client, String warehouse);

@Query(nativeQuery =true,value ="SELECT \r\n"
		+ "DATE(docdate) AS orderdate,\r\n"
		+ "COUNT(*) AS ordercount\r\n"
		+ "FROM  putaway\r\n"
		+ "WHERE \r\n"
		+ "DATE(docdate) = CURDATE() and orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4\r\n"
		+ "GROUP BY \r\n"
		+ "DATE(docdate)")
Set<Object[]> getPutAway(Long orgId, String branchCode, String client, String warehouse);

@Query(value ="select partno,PARTDESC,SKU,STATUS,BATCH,sum(sqty) as availqty from stockdetails\r\n"
		+ " where orgid=?1 and warehouse=?4 and branchcode=?2 and client=?3 AND BIN=?5\r\n"
		+ "  group by partno,PARTDESC,SKU,STATUS,BATCH HAVING sum(sqty) > 0",nativeQuery =true)
Set<Object[]> getBinDetails3(Long orgId, String branchCode, String client, String warehouse,String bin);

@Query(value ="select w.rowno,w.level,W.bin from warehouselocationdetails w where orgid=?1  AND \r\n"
		+ " branchcode=?2 AND warehouse=?3 GROUP BY w.rowno,w.level,W.bin ORDER BY W.ROWNO,W.bin",nativeQuery =true)
Set<Object[]> getStorageDetails2(Long orgId, String branchCode, String warehouse);


@Query(nativeQuery =true,value ="select count(*) from(\r\n"
		+ "SELECT \r\n"
		+ "    b.entryno, \r\n"
		+ "    b.entrydate, \r\n"
		+ "    SUM(b.totalgrnqty) AS qty, \r\n"
		+ "    'Complete' AS status\r\n"
		+ "FROM grn b \r\n"
		+ "WHERE b.orgid = ?1\r\n"
		+ "  AND b.finyear = ?5 \r\n"
		+ "  AND b.branchcode = ?2\r\n"
		+ "  AND b.client = ?4\r\n"
		+ "  AND b.warehouse = ?3\r\n"
		+ "  AND (MONTH(b.docdate) = ?6 OR ?6 IS NULL)\r\n"
		+ "GROUP BY b.entryno, b.entrydate\r\n"
		+ "UNION\r\n"
		+ "SELECT \r\n"
		+ "    a.entryno, \r\n"
		+ "    a.entrydate, \r\n"
		+ "    sum((a.recqty-a.damageqty)) AS qty, \r\n"
		+ "    'Pending' AS status \r\n"
		+ "FROM grnexcelupload a \r\n"
		+ "WHERE a.orgid = ?1\r\n"
		+ "  AND a.finyear = ?5\r\n"
		+ "  AND a.branchcode = ?2\r\n"
		+ "  AND a.warehouse = ?3\r\n"
		+ "  AND a.client = ?4\r\n"
		+ "  AND (MONTH(a.entrydate) = ?6 OR ?6 IS NULL)\r\n"
		+ "  AND a.entryno NOT IN (\r\n"
		+ "      SELECT b.entryno \r\n"
		+ "      FROM grn b \r\n"
		+ "      WHERE b.orgid = ?1\r\n"
		+ "        AND b.finyear = ?5\r\n"
		+ "        AND b.branchcode = ?2\r\n"
		+ "        AND b.client = ?4\r\n"
		+ "  )\r\n"
		+ "GROUP BY a.entryno, a.entrydate)z")
Set<Object[]> getGrnOrderDetailsPerIn(Long orgId, String branchCode, String warehouse, String client,
		 int finYear, String month);


@Query(nativeQuery =true,value ="SELECT A.rowno,A.levelno,A.bin,B.binstatus FROM vw_clientbindetails A,wv_locationstatus B where\r\n"
		+ "  A.orgid =B.ORGID\r\n"
		+ "  AND A.BIN=B.BIN\r\n"
		+ "  AND A.warehouse =B.WAREHOUSE\r\n"
		+ "  AND A.branchcode =B.branchcode\r\n"
		+ "  AND A.client =B.client AND\r\n"
		+ "  A.orgid =?1\r\n"
		+ "  AND A.warehouse =?4\r\n"
		+ "  AND A.branchcode =?2\r\n"
		+ "  AND A.client =?3 \r\n"
		+ " group by A.rowno,A.levelno,A.bin,B.binstatus ORDER BY A.ROWNO,A.bin")
Set<Object[]> getBinDetailsForClient(Long orgId, String branchCode, String client, String warehouse);

@Query(value ="select count(*) from(\r\n"
		+ "SELECT \r\n"
		+ "    b.entryno, \r\n"
		+ "    b.entrydate, \r\n"
		+ "    SUM(b.totalgrnqty) AS qty, \r\n"
		+ "    'Complete' AS status\r\n"
		+ "FROM grn b \r\n"
		+ "WHERE b.orgid = ?1\r\n"
		+ "  AND b.finyear = ?5 \r\n"
		+ "  AND b.branchcode = ?2\r\n"
		+ "  AND b.client = ?4\r\n"
		+ "  AND b.warehouse = ?3\r\n"
		+ "GROUP BY b.entryno, b.entrydate\r\n"
		+ "UNION\r\n"
		+ "SELECT \r\n"
		+ "    a.entryno, \r\n"
		+ "    a.entrydate, \r\n"
		+ "    sum((a.recqty-a.damageqty)) AS qty, \r\n"
		+ "    'Pending' AS status \r\n"
		+ "FROM grnexcelupload a \r\n"
		+ "WHERE a.orgid = ?1\r\n"
		+ "  AND a.finyear = ?5\r\n"
		+ "  AND a.branchcode = ?2\r\n"
		+ "  AND a.warehouse = ?3\r\n"
		+ "  AND a.client = ?4\r\n"
		+ "  AND a.entryno NOT IN (\r\n"
		+ "      SELECT b.entryno \r\n"
		+ "      FROM grn b \r\n"
		+ "      WHERE b.orgid = ?1\r\n"
		+ "        AND b.finyear = ?5\r\n"
		+ "        AND b.branchcode = ?2\r\n"
		+ "        AND b.client = ?4\r\n"
		+ "  )\r\n"
		+ "GROUP BY a.entryno, a.entrydate)z",nativeQuery=true)
Set<Object[]> getGrnOrderDetails(Long orgId, String branchCode, String warehouse, String client, int finYear);

@Query(nativeQuery =true,value ="SELECT count(*) \r\n"
		+ "FROM (\r\n"
		+ "    SELECT \r\n"
		+ "        b.orderno, \r\n"
		+ "        b.orderdate, \r\n"
		+ "        SUM(b.totalorderqty) AS qty, \r\n"
		+ "        'Complete' AS status\r\n"
		+ "    FROM buyerorder b \r\n"
		+ "    WHERE b.orgid = ?1  -- Set this to 1000000001\r\n"
		+ "      AND b.finyear = ?5 -- Set this to '2024'\r\n"
		+ "      AND b.branchcode = ?2 -- Set this to 'BLRW'\r\n"
		+ "      AND b.client = ?4 -- Set this to 'BACARDI'\r\n"
		+"AND (MONTH(B.DOCDATE) = ?6 OR ?6 IS NULL)\r\n"
		+ "    GROUP BY b.orderno, b.orderdate \r\n"
		+ "\r\n"
		+ "    UNION\r\n"
		+ "\r\n"
		+ "    SELECT \r\n"
		+ "        a.orderno, \r\n"
		+ "        a.orderdate, \r\n"
		+ "        SUM(a.qty) AS qty, \r\n"
		+ "        'Pending' AS status \r\n"
		+ "    FROM boexcelupload a \r\n"
		+ "    WHERE a.orgid = ?1  -- Set this to 1000000001\r\n"
		+ "      AND a.finyear = ?5  -- Set this to '2024'\r\n"
		+ "      AND a.branchcode = ?2  -- Set this to 'BLRW'\r\n"
		+ "      AND a.warehouse = ?3  -- Set this to 'BLRWAREHOUSE'\r\n"
		+ "      AND a.client = ?4  -- Set this to 'BACARDI'\r\n"
		+ "      AND (MONTH(a.orderdate) = ?6 OR ?6 IS NULL) -- Set this to NULL or a specific month\r\n"
		+ "      AND a.orderno NOT IN (\r\n"
		+ "          SELECT b.orderno \r\n"
		+ "          FROM buyerorder b \r\n"
		+ "          WHERE b.orgid = ?1\r\n"
		+ "            AND b.finyear = ?5\r\n"
		+ "            AND b.branchcode = ?2\r\n"
		+ "            AND b.client = ?4\r\n"
		+ "      )\r\n"
		+ "    GROUP BY a.orderno, a.orderdate\r\n"
		+ ") z\r\n")
Set<Object[]> getOutBoundOrderPerMonth1(Long orgId, String branchCode, String warehouse, String client,
		int finYear, String month);

@Query(value ="SELECT count(*) \r\n"
		+ "FROM (\r\n"
		+ "    SELECT \r\n"
		+ "        b.orderno, \r\n"
		+ "        b.orderdate, \r\n"
		+ "        SUM(b.totalorderqty) AS qty, \r\n"
		+ "        'Complete' AS status\r\n"
		+ "    FROM buyerorder b \r\n"
		+ "    WHERE b.orgid = ?1  -- Set this to 1000000001\r\n"
		+ "      AND b.finyear = ?5 -- Set this to '2024'\r\n"
		+ "      AND b.branchcode = ?2 -- Set this to 'BLRW'\r\n"
		+ "      AND b.client = ?4 -- Set this to 'BACARDI'\r\n"
		+ "    GROUP BY b.orderno, b.orderdate \r\n"
		+ "\r\n"
		+ "    UNION\r\n"
		+ "\r\n"
		+ "    SELECT \r\n"
		+ "        a.orderno, \r\n"
		+ "        a.orderdate, \r\n"
		+ "        SUM(a.qty) AS qty, \r\n"
		+ "        'Pending' AS status \r\n"
		+ "    FROM boexcelupload a \r\n"
		+ "    WHERE a.orgid = ?1  -- Set this to 1000000001\r\n"
		+ "      AND a.finyear = ?5  -- Set this to '2024'\r\n"
		+ "      AND a.branchcode = ?2  -- Set this to 'BLRW'\r\n"
		+ "      AND a.warehouse = ?3  -- Set this to 'BLRWAREHOUSE'\r\n"
		+ "      AND a.client = ?4  -- Set this to 'BACARDI'\r\n"
		+ "      AND a.orderno NOT IN (\r\n"
		+ "          SELECT b.orderno \r\n"
		+ "          FROM buyerorder b \r\n"
		+ "          WHERE b.orgid = ?1\r\n"
		+ "            AND b.finyear = ?5\r\n"
		+ "            AND b.branchcode = ?2\r\n"
		+ "            AND b.client = ?4\r\n"
		+ "      )\r\n"
		+ "    GROUP BY a.orderno, a.orderdate\r\n"
		+ ") z",nativeQuery=true)
Set<Object[]> getOutBoundOrderPerYear1(Long orgId, String branchCode, String warehouse, String client, int finYear);

@Query(nativeQuery =true,value ="SELECT \r\n"
		+ "    A.PARTNO,\r\n"
		+ "    A.PARTDESC,\r\n"
		+ "    A.SKU,\r\n"
		+ "    A.bin,\r\n"
		+ "    A.grnno,\r\n"
		+ "    A.grndate,\r\n"
		+ "    A.expdate,\r\n"
		+ "    SUM(A.SQTY) AS holdQty \r\n"
		+ "FROM \r\n"
		+ "    (SELECT \r\n"
		+ "         partno,\r\n"
		+ "         partdesc,\r\n"
		+ "         sku,\r\n"
		+ "         grnno,\r\n"
		+ "         grndate,\r\n"
		+ "         batch,\r\n"
		+ "         batchdate,\r\n"
		+ "         expdate,\r\n"
		+ "         bin,\r\n"
		+ "         SUM(sqty) AS SQTY \r\n"
		+ "     FROM \r\n"
		+ "         stockdetails \r\n"
		+ "     WHERE \r\n"
		+ "         orgid =?1\r\n"
		+ "         AND warehouse =?3 \r\n"
		+ "         AND branchcode =?2\r\n"
		+ "         AND client =?4\r\n"
		+ "         AND status = 'H' \r\n"
		+ "     GROUP BY \r\n"
		+ "         partno, partdesc, sku, grnno, grndate, batch, batchdate, expdate, bin \r\n"
		+ "     HAVING \r\n"
		+ "         SUM(sqty) > 0\r\n"
		+ "    ) A \r\n"
		+ "GROUP BY \r\n"
		+ "A.PARTNO,\r\n"
		+ "    A.PARTDESC,\r\n"
		+ "    A.SKU,\r\n"
		+ "    A.bin,\r\n"
		+ "    A.grnno,\r\n"
		+ "    A.grndate,\r\n"
		+ "    A.expdate")
Set<Object[]> getHoldMaterialCount1(Long orgId, String branchCode, String warehouse, String client);

@Query(nativeQuery =true,value ="select bin,binstatus from wv_locationstatus where orgid=?1 and branchcode=?2 and client=?3\r\n"
		+ "and warehouse=?4 group by bin,binstatus order by bin")
Set<Object[]> getBinDetailsClientWiseForEmpty(Long orgId, String branchCode, String client, String warehouse);


@Query(nativeQuery =true,value ="SELECT \r\n"
		+ "    partno,\r\n"
		+ "    partdesc,\r\n"
		+ "    sku,\r\n"
		+ "    batch,\r\n"
		+ "    batchdate,\r\n"
		+ "    grnno,\r\n"
		+ "    grndate,\r\n"
		+ "    expdate,\r\n"
		+ "    SUM(sqty) AS total_quantity,\r\n"
		+ "    DATEDIFF(expdate, CURDATE()) AS days_until_expiration,\r\n"
		+ "    bin\r\n"
		+ "FROM \r\n"
		+ "    stockdetails\r\n"
		+ "WHERE \r\n"
		+ "    orgid =?1\r\n"
		+ "    AND branchcode =?2\r\n"
		+ "    AND client =?3\r\n"
		+ "    AND warehouse =?4\r\n"
		+ "    AND DATEDIFF(expdate, CURDATE()) < 20\r\n"
		+ "GROUP BY \r\n"
		+ "    partno,\r\n"
		+ "    partdesc,\r\n"
		+ "    sku,\r\n"
		+ "    batch,\r\n"
		+ "    batchdate,\r\n"
		+ "    grnno,\r\n"
		+ "    grndate,\r\n"
		+ "    expdate,\r\n"
		+ "    bin\r\n"
		+ "    having  SUM(sqty)>0\r\n"
		+ "ORDER BY \r\n"
		+ "    days_until_expiration DESC,\r\n"
		+ "    bin")
Set<Object[]> getExpDetailsForMaterials(Long orgId, String branchCode, String client, String warehouse);

@Query(nativeQuery =true,value ="SELECT \r\n"
		+ "DATE(docdate) AS orderdate,\r\n"
		+ "COUNT(*) AS ordercount\r\n"
		+ "FROM  pickrequest\r\n"
		+ "WHERE \r\n"
		+ "DATE(docdate) = CURDATE() and orgid=?1 and branchcode=?2 and client=?3  and warehouse=?4\r\n"
		+ "GROUP BY \r\n"
		+ "DATE(docdate)")
Set<Object[]> getPickRequest(Long orgId, String branchCode, String client, String warehouse);

List<StockDetailsVO> findByOrgIdAndBranchAndBranchCodeAndClient(Long orgid, String branch, String branch2,
		String client);

@Query(nativeQuery = true,value = "select case when stockfreeze=1 then 'true' else 'false' end freezestatus from stockdetails where orgid=?1 and branch=?2  and branchcode=?3 and client=?4  group by stockfreeze")
boolean getStockFreezeStatus(Long orgId, String branch, String branchCode, String client);




}