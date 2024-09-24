package com.whydigit.wms.repo;

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

@Query(nativeQuery = true,value="select a.partno,a.partdesc,a.refno,a.sourcescreenname,sum(openingqty)oqty,sum(recqty)recqty,sum(abs(dispatchqty)) dqty,sum(closingqty)cqty from(\r\n"
		+ "select partno,partdesc,refno,sourcescreenname,sum(sqty) openingqty,0 recqty,0 dispatchqty,0 closingqty from stockdetails \r\n"
		+ "where orgid = ?1 \r\n"
		+ "   AND branchcode = ?2\r\n"
		+ "   AND warehouse = ?3  \r\n"
		+ "   AND customer = ?4\r\n"
		+ "   AND client = ?5 \r\n"
		+ "   AND stockdate <?6 \r\n"
		+ "and (partno = ?8 OR 'ALL' = ?8) group by partno,partdesc,refno,sourcescreenname having sum(sqty)>0\r\n"
		+ "union\r\n"
		+ "select partno,partdesc,refno,sourcescreenname,0 openingqty,sum(sqty) recqty,0 dispatchqty,0 closingqty from stockdetails \r\n"
		+ "where orgid = ?1 \r\n"
		+ "   AND branchcode = ?2\r\n"
		+ "   AND warehouse = ?3  \r\n"
		+ "   AND customer = ?4\r\n"
		+ "   AND client = ?5 \r\n"
		+ "   AND stockdate between ?6 and ?7 and sqty>0\r\n"
		+ "and (partno = ?8 OR 'ALL' = ?8) group by partno,partdesc,refno,sourcescreenname \r\n"
		+ "union\r\n"
		+ "select partno,partdesc,refno,sourcescreenname,0 openingqty,0 recqty,sum(sqty) dispatchqty,0 closingqty from stockdetails \r\n"
		+ "where orgid = ?1 \r\n"
		+ "   AND branchcode = ?2\r\n"
		+ "   AND warehouse = ?3  \r\n"
		+ "   AND customer = ?4\r\n"
		+ "   AND client = ?5 \r\n"
		+ "   AND stockdate between ?6 and ?7 and sqty<0\r\n"
		+ "and (partno = ?8 OR 'ALL' = ?8) group by partno,partdesc,refno,sourcescreenname \r\n"
		+ "union\r\n"
		+ "select partno,partdesc,refno,sourcescreenname,0 openingqty,0 recqty,0 dispatchqty,sum(sqty) closingqty from stockdetails \r\n"
		+ "where orgid = ?1 \r\n"
		+ "   AND branchcode = ?2\r\n"
		+ "   AND warehouse = ?3  \r\n"
		+ "   AND customer = ?4\r\n"
		+ "   AND client = ?5 \r\n"
		+ "   AND stockdate <=?7\r\n"
		+ "and (partno = ?8 OR 'ALL' = ?8) group by partno,partdesc,refno,sourcescreenname having sum(sqty)>0 )a group by a.partno,a.partdesc,a.refno,a.sourcescreenname")
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

@Query(value ="select a.bin from(\r\n"
		+ "SELECT b.partno, b.partdesc, b.bin, COALESCE(SUM(b.sqty), 0) AS avlqty\r\n"
		+ "FROM stockdetails b\r\n"
		+ "WHERE b.orgid = ?1 \r\n"
		+ "  AND b.branchcode = ?2 \r\n"
		+ "  AND b.warehouse = ?3\r\n"
		+ "  AND b.customer = ?4\r\n"
		+ "  AND b.client = ?5\r\n"
		+ "AND b.stockdate <= DATE(NOW())\r\n"
		+ "AND (b.partno = ?6 OR 'ALL' = ?6)\r\n"
		+ "GROUP BY b.partno, b.partdesc, b.bin\r\n"
		+ "HAVING SUM(b.sqty) > 0\r\n"
		+ "ORDER BY b.partno, b.bin) a group by a.bin",nativeQuery =true)
Set<Object[]> getBatchNoBin(Long orgId, String branchCode, String warehouse, String customer, String client,
		String partNo);

@Query(nativeQuery = true,value="SELECT  a.batch\r\n"
		+ "FROM (\r\n"
		+ "    SELECT b.partno, \r\n"
		+ "           b.partdesc, \r\n"
		+ "           b.batch, \r\n"
		+ "           COALESCE(SUM(b.sqty), 0) AS avlqty \r\n"
		+ "    FROM stockdetails b \r\n"
		+ "    WHERE b.orgid =?1 \r\n"
		+ "      AND b.branchcode =?2 \r\n"
		+ "      AND b.warehouse =?3\r\n"
		+ "      AND b.customer =?4\r\n"
		+ "      AND b.client =?5\r\n"
		+ "      AND (b.partno =?6 OR 'ALL' =?6)  \r\n"
		+ "      AND b.stockdate <= DATE(NOW()) \r\n"
		+ "    GROUP BY b.partno, b.partdesc, b.batch \r\n"
		+ "    HAVING avlqty > 0 \r\n"
		+ "    ORDER BY b.partno, b.batch, b.partdesc\r\n"
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
		+ "    m.status = 'R'\r\n"
		+ "    AND m.orgid =?1\r\n"
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


@Query(nativeQuery =true,value ="select count(docid) from grn where month(docdate)=?6 and cancel=0 AND finyear=?5 AND orgid=?1\r\n"
		+ "and branchcode=?2 and client=?4 and warehouse=?3")
Set<Object[]> getGrnOrderDetailsPerIn(Long orgId, String branchCode, String warehouse, String client,
		 int finYear, int month);


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

@Query(value ="select count(docid) from grn where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and  finyear=?5 and cancel=0 ",nativeQuery=true)
Set<Object[]> getGrnOrderDetails(Long orgId, String branchCode, String warehouse, String client, int finYear);

@Query(nativeQuery =true,value ="select count(docid) from buyerorder where month(docdate)=?6 and cancel=0 AND finyear=?5 AND orgid=?1\r\n"
		+ "and branchcode=?2 and client=?4 and warehouse=?3")
Set<Object[]> getOutBoundOrderPerMonth1(Long orgId, String branchCode, String warehouse, String client,
		int finYear, int month);

@Query(value ="select count(docid) from buyerorder where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and  finyear=?5 and cancel=0 ",nativeQuery=true)
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
		+ "    bin\r\n"
		+ "")
Set<Object[]> getExpDetailsForMaterials(Long orgId, String branchCode, String client, String warehouse);




}