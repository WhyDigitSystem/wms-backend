package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockRestateVO;

@Repository
public interface StockRestateRepo extends JpaRepository<StockRestateVO, Long>{

	@Query(value = "select * from stockrestate where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<StockRestateVO> findAllStockRestate(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);


	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getStockRestateDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true,value = "select a.bintype,a.binclass,a.celltype,a.bin,a.core from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.bintype,a.binclass,a.celltype,a.bin,a.core")
	Set<Object[]> getFromBinDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag);

	@Query(nativeQuery = true,value = "select a.partno,a.partdesc,a.sku from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> getPartNoDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag, String fromBin);

@Query(nativeQuery = true,value = "select a.grnno,a.grndate from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.grnno,a.grndate")
	Set<Object[]> getGrnNoDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag, String fromBin, String partNo);

@Query(nativeQuery = true,value = "select a.batch,a.batchdate,a.expdate from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7 and grnno=?8\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.batch,a.batchdate,a.expdate")
Set<Object[]> getBatchNoDetails(Long orgId, String branchCode, String warehouse, String client, String tranferFromFlag,
		String fromBin, String partNo, String grnNo);

@Query(nativeQuery = true,value = "select sum(a.sqty)sqty from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7 and grnno=?8\r\n"
		+ "and batch=?9 OR batch is NULL\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
int getAvlQty(Long orgId, String branchCode, String warehouse, String client, String tranferFromFlag,
		String fromBin, String partNo, String grnNo, String batchNo);

@Query(nativeQuery = true,value = "select bin,bintype,binclass,celltype,core from wv_locationstatus where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4")
Set<Object[]> getToBinDetails(Long orgId, String branchCode, String warehouse, String client);

@Query(nativeQuery = true,value = "SELECT \r\n"
		+ "    partno,\r\n"
		+ "    partdesc,\r\n"
		+ "    sku,\r\n"
		+ "    grnno,\r\n"
		+ "    grndate,\r\n"
		+ "    batch,\r\n"
		+ "    batchdate,\r\n"
		+ "    expdate,\r\n"
		+ "    bintype,\r\n"
		+ "    binclass,\r\n"
		+ "    celltype,\r\n"
		+ "    core,\r\n"
		+ "    bin,\r\n"
		+ "    CASE WHEN ?6 = 'D' THEN 'DEFECTIVE' ELSE bin END AS toBin,\r\n"
		+ "    CASE WHEN ?6 = 'D' THEN 'DEFECTIVE' ELSE bintype END AS toBinType,\r\n"
		+ "    binclass AS toBinClass,\r\n"
		+ "    celltype AS toCellType,\r\n"
		+ "    core AS toCore,\r\n"
		+ "    qcflag,\r\n"
		+ "    SUM(sqty) AS sqty,\r\n"
		+ "    ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id\r\n"
		+ "FROM \r\n"
		+ "    stockdetails\r\n"
		+ "WHERE \r\n"
		+ "    orgid = ?1 \r\n"
		+ "    AND branchcode = ?2\r\n"
		+ "    AND warehouse = ?3 \r\n"
		+ "    AND client = ?4\r\n"
		+ "    AND status = ?5\r\n"
		+ "    AND (?7 IS NULL \r\n"
		+ "         OR ?7 NOT IN (\r\n"
		+ "             SELECT entryno \r\n"
		+ "             FROM srsexcelupload\r\n"
		+ "             WHERE orgid = ?1 \r\n"
		+ "    AND branchcode = ?2\r\n"
		+ "    AND warehouse = ?3 \r\n"
		+ "    AND client = ?4\r\n"
		+ "             GROUP BY entryno\r\n"
		+ "         ))\r\n"
		+ "GROUP BY \r\n"
		+ "    partno, partdesc, sku, grnno, grndate, batch, batchdate, expdate,\r\n"
		+ "    bintype, binclass, celltype, core, bin, qcflag\r\n"
		+ "HAVING \r\n"
		+ "    SUM(sqty) > 0\r\n"
		+ "UNION\r\n"
		+ "SELECT \r\n"
		+ "    a.partno,\r\n"
		+ "    a.partdesc,\r\n"
		+ "    a.sku,\r\n"
		+ "    a.grnno,\r\n"
		+ "    a.grndate,\r\n"
		+ "    a.batch,\r\n"
		+ "    a.batchdate,\r\n"
		+ "    a.expdate,\r\n"
		+ "    a.bintype,\r\n"
		+ "    a.binclass,\r\n"
		+ "    a.celltype,\r\n"
		+ "    a.core,\r\n"
		+ "    a.bin,\r\n"
		+ "    CASE WHEN ?6 = 'D' THEN 'DEFECTIVE' ELSE bin END AS toBin,\r\n"
		+ "    CASE WHEN ?6 = 'D' THEN 'DEFECTIVE' ELSE bintype END AS toBinType,\r\n"
		+ "    a.binclass AS toBinClass,\r\n"
		+ "    a.celltype AS toCellType,\r\n"
		+ "    a.core AS toCore,\r\n"
		+ "    a.qcflag,\r\n"
		+ "    SUM(a.sqty) AS sqty,\r\n"
		+ "    ROW_NUMBER() OVER (ORDER BY a.partdesc, a.partno) AS id\r\n"
		+ "FROM \r\n"
		+ "    stockdetails a\r\n"
		+ "JOIN \r\n"
		+ "    srsexcelupload b \r\n"
		+ "    ON a.orgid = b.orgid\r\n"
		+ "    AND a.partno = b.partno\r\n"
		+ "    AND a.grnno = b.grnno\r\n"
		+ "    AND a.batch = b.batchno\r\n"
		+ "    AND a.bin = b.frombin\r\n"
		+ "    AND a.status = CASE \r\n"
		+ "                     WHEN b.fromstatus = 'RELEASE' THEN 'R' \r\n"
		+ "                     WHEN b.fromstatus = 'HOLD' THEN 'H'\r\n"
		+ "                     WHEN b.fromstatus = 'VAS' THEN 'V'\r\n"
		+ "                     WHEN b.fromstatus = 'DEFECTIVE' THEN 'D'\r\n"
		+ "                   END\r\n"
		+ "WHERE \r\n"
		+ "    b.orgid = ?1 \r\n"
		+ "    AND b.branchcode = ?2\r\n"
		+ "    AND b.warehouse = ?3 \r\n"
		+ "    AND b.client = ?4\r\n"
		+ "    AND b.entryno = ?7\r\n"
		+ "    AND ?7 NOT IN (\r\n"
		+ "        SELECT entryno \r\n"
		+ "        FROM stockrestate \r\n"
		+ "        WHERE orgid = ?1 \r\n"
		+ "    AND branchcode = ?2\r\n"
		+ "    AND warehouse = ?3 \r\n"
		+ "    AND client = ?4\r\n"
		+ "        AND entryno IS NOT NULL\r\n"
		+ "        GROUP BY entryno\r\n"
		+ "    )\r\n"
		+ "    AND b.fromstatus = CASE \r\n"
		+ "                         WHEN 'R' = ?5 THEN 'RELEASE'\r\n"
		+ "                         WHEN 'H' = ?5 THEN 'HOLD'\r\n"
		+ "                         WHEN 'V' = ?5 THEN 'VAS'\r\n"
		+ "                         WHEN 'D' = ?5 THEN 'DEFECTIVE'\r\n"
		+ "                       END\r\n"
		+ "    AND b.tostatus = CASE \r\n"
		+ "                       WHEN 'R' = ?6 THEN 'RELEASE'\r\n"
		+ "                       WHEN 'H' = ?6 THEN 'HOLD'\r\n"
		+ "                       WHEN 'V' = ?6 THEN 'VAS'\r\n"
		+ "                       WHEN 'D' = ?6 THEN 'DEFECTIVE'\r\n"
		+ "                     END\r\n"
		+ "GROUP BY \r\n"
		+ "    a.partno, a.partdesc, a.sku, a.grnno, a.grndate, a.batch, a.batchdate, \r\n"
		+ "    a.expdate, a.bintype, a.binclass, a.celltype, a.core, a.bin, a.qcflag\r\n"
		+ "HAVING \r\n"
		+ "    SUM(a.sqty) > 0")
Set<Object[]> getFillGridDetailsForRestate(Long orgId, String branchCode, String warehouse, String client,
		String tranferFromFlag,String tranferToFlag,String entryNo);


boolean existsByEntryNoAndOrgIdAndClient(String entryNo, Long orgId, String client);

	
}

