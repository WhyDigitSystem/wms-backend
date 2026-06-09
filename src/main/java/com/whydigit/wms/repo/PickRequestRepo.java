package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PickRequestVO;

@Repository
public interface PickRequestRepo extends JpaRepository<PickRequestVO, Long> {
	@Query(nativeQuery = true, value = "select * from pickrequest where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<PickRequestVO> findAllPickRequest(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select * from pickrequest where pickrequestid=?1")
	PickRequestVO findPickRequestById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getPickRequestDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);


	@Query(nativeQuery = true,value="select a.docid,a.docdate,b.partno,b.partdesc,sum(b.pickqty) as shippedqty from pickrequest a , pickrequestdetails b where a.pickrequestid= b.pickrequestid and a.orgid=?1 and a.branch=?2 and a.branchcode=?3 and a.client=?4 and a.warehouse=?5 and buyerorderno=?6 group by a.docid,a.docdate,b.partno,b.partdesc having sum(b.pickqty) >0 ")
	Set<Object[]> getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(Long orgId,
			String branch, String branchCode, String client, String warehouse, String buyerOrderNo);


	@Query(nativeQuery = true,value="select * from pickrequest where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and warehouse=?5 and status='Confirm' and buyerorderno not in(select buyerorderno from deliverychallan where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and warehouse=?5)")
	List<PickRequestVO> findAllPickRequestFromDeliveryChallan(Long orgId, String branch,
			
			String branchCode, String client, String warehouse);
	
	@Query(nativeQuery = true,value="select mm.partno,mm.partdesc,mm.sku,mm.grnno,mm.grndate,mm.batchno,mm.batchdate,cast((mm.sqty*-1)AS UNSIGNED)sqty,mm.binclass,mm.bintype,mm.pallet,mm.celltype,mm.core,mm.expdate,mm.tn,mm.orderqty,\r\n"
			+ "mm.remainorderqty,mm.availqty,mm.sqty prpqty,mm.trpqty,mm.qcflag,mm.stockdate,ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id from(Select jj.partno,jj.partdesc,jj.sku,jj.grnno,jj.grndate,jj.batchno,jj.batchdate,jj.sqty,jj.binclass,jj.bintype,jj.pallet,jj.celltype,jj.core,jj.expdate,jj.tn,jj.orderqty,\r\n"
			+ "jj.remainorderqty,jj.availqty,jj.sqty prpqty,jj.trpqty,jj.qcflag,jj.stockdate\r\n"
			+ "FROM (\r\n"
			+ "SELECT jm.partno,jm.partdesc,jm.binclass,jm.bintype,jm.pallet,jm.celltype,jm.core,jm.sku,jm.expdate,jm.tn,jm.orderqty,jm.remainorderqty,\r\n"
			+ "jm.availqty,jm.sqty prpqty,jm.trpqty,jm.sqty,jm.qcflag,jm.stockdate,\r\n"
			+ "IFNULL(jm.grnno, 'FROM OPSTOCK') AS grnno,\r\n"
			+ "    IFNULL(jm.grndate, jm.stockdate) AS grndate, jm.batchno,jm.batchdate\r\n"
			+ "FROM (\r\n"
			+ "SELECT jn.partno,jn.partdesc,jn.binclass,jn.bintype,jn.pallet,jn.celltype,jn.core,jn.sku,jn.expdate,jn.tn,jn.orderqty,jn.remainorderqty,\r\n"
			+ "jn.availqty,jn.prpqty,\r\n"
			+ "CASE WHEN jn.prpqty < 0 THEN (jn.trpqty + jn.prpqty) ELSE jn.trpqty END trpqty,\r\n"
			+ "CASE WHEN (jn.availqty - jn.prpqty) < 0 THEN (jn.availqty * -1)\r\n"
			+ "WHEN jn.prpqty < 0 THEN (jn.prpqty + jn.availqty) * -1\r\n"
			+ "WHEN (jn.availqty - jn.prpqty) > 0 THEN (jn.availqty * -1)\r\n"
			+ "WHEN (jn.availqty - jn.prpqty) = 0 THEN (jn.prpqty * -1) END sqty,\r\n"
			+ "jn.qcflag,jn.stockdate,jn.grnno,jn.grndate,jn.batchno,jn.batchdate\r\n"
			+ "FROM (\r\n"
			+ "SELECT jhn.partno,jhn.partdesc,jhn.binclass,jhn.bintype,jhn.pallet,jhn.celltype,jhn.core,jhn.sku,jhn.availqty,\r\n"
			+ "jhn.remainorderqty,jhn.orderqty,jhn.orderqty - IFNULL(jhn.pickedqty, 0) - SUM(jhn.availqty) \r\n"
			+ "OVER (PARTITION BY jhn.partno, jhn.batchno ORDER BY jhn.partno, jhn.partdesc, jhn.grndate, jhn.availqty, jhn.pallet, jhn.sku, jhn.orderqty, jhn.grnno) AS PRPQTY,\r\n"
			+ "SUM(jhn.availqty) OVER (PARTITION BY jhn.partno,jhn.batchno ORDER BY jhn.partno, jhn.batchno,\r\n"
			+ "jhn.partdesc,jhn.grndate,jhn.availqty,jhn.pallet,jhn.sku,jhn.orderqty) AS trpqty,jhn.qcflag,jhn.stockdate,\r\n"
			+ "jhn.expdate,jhn.grnno,jhn.grndate,jhn.tn,jhn.batchno,jhn.batchdate\r\n"
			+ "FROM (\r\n"
			+ "SELECT s.partno,s.partdesc,s.binclass,s.bintype,s.pallet,s.celltype,s.core,s.sku,s.availqty,\r\n"
			+ "sum(bp.boqty) orderqty,sum(pending) remainorderqty,s.availqty - sum(bp.boqty) sqty,\r\n"
			+ "qcflag,stockdate,s.expdate,grnno,grndate,tn,s.batchno,s.batchdate,pickedqty\r\n"
			+ "FROM ( \r\n"
			+ "SELECT\r\n"
			+ "jh.stockdate,jh.partno,jh.partdesc,jh.binclass,jh.bintype,jh.pallet,jh.celltype,jh.core,jh.sku,jh.availqty,jh.qcflag,jh.expdate,jh.grnno,\r\n"
			+ "jh.grndate,jh.tn,jh.batchno,jh.batchdate\r\n"
			+ "FROM (\r\n"
			+ "SELECT hn.stockdate,hn.partno,hn.partdesc,hn.binclass,hn.bintype,hn.pallet,hn.celltype,hn.core,hn.sku,hn.availqty,hn.qcflag,hn.expdate,\r\n"
			+ "hn.grnno,hn.grndate,ROW_NUMBER () OVER (PARTITION BY hn.partno,hn.batchno ORDER BY\r\n"
			+ "hn.stockdate ASC)\r\n"
			+ "tn,hn.batchno,hn.batchdate ,hn.branchcode ,hn.client  \r\n"
			+ "FROM (\r\n"
			+ "SELECT grndate stockdate,a.partno,a.partdesc,a.binclass,a.bintype,a.bin pallet,celltype,core,sku,\r\n"
			+ "SUM(a.sqty) availQTY,qcflag,expdate,grnno,grndate,a.batch batchno,a.batchdate,a.branchcode , a.client\r\n"
			+ "FROM stockdetails a WHERE a.branchcode = ?2 and orgid=?1 \r\n"
			+ "AND a.client = ?3\r\n"
			+ "And status = 'R'\r\n"
			+ "AND upper(bin) NOT LIKE 'DE%'\r\n"
			+ "AND upper(bin) not like 'QU%'\r\n"
			+ "AND upper(bin) not like 'DOC%'\r\n"
			+ "AND upper(bin) not like 'RACKS%'\r\n"
			+ "AND Upper(bin) not in\r\n"
			+ "(select 'ITC' s from dual where 'MAAW' = ?2 and 'MARS' = ?3\r\n"
			+ "union\r\n"
			+ "select 'DBS' s from dual where 'MAAW' = ?2 and 'MARS' = ?3\r\n"
			+ ")\r\n"
			+ "AND Status <> 'A'\r\n"
			+ "GROUP BY\r\n"
			+ "partno,a.partdesc,bin,sku,celltype,core,qcflag,expdate,grnno,grndate,\r\n"
			+ "a.batch,a.batchdate,a.branchcode, a.client,a.binclass,a.bintype\r\n"
			+ "HAVING SUM(sqty) > 0\r\n"
			+ "ORDER BY ROW_NUMBER () OVER ( PARTITION BY\r\n"
			+ "partno,batch\r\n"
			+ "ORDER BY expdate ASC)\r\n"
			+ ")hn\r\n"
			+ " )jh ORDER BY jh.tn\r\n"
			+ " ) s,                        \r\n"
			+ "( \r\n"
			+ "SELECT \r\n"
			+ "    h.client,\r\n"
			+ "    h.branchcode,\r\n"
			+ "    h.bodocid,\r\n"
			+ "    h.partno,\r\n"
			+ "    h.partdesc,\r\n"
			+ "    h.batchno,\r\n"
			+ "    h.boqty,\r\n"
			+ "    SUM(IFNULL(h.pickedqty, 0)) AS pickedqty,\r\n"
			+ "    IFNULL(h.boqty, 0) - SUM(IFNULL(h.pickedqty, 0)) AS pending,\r\n"
			+ "    h.sdate\r\n"
			+ "FROM (\r\n"
			+ "    SELECT \r\n"
			+ "        b.client,\r\n"
			+ "        b.branchcode,\r\n"
			+ "        b.orderno,\r\n"
			+ "        b.docid AS bodocid,\r\n"
			+ "        b.docdate AS bodate,\r\n"
			+ "        b.batchno,\r\n"
			+ "        b.buyername,\r\n"
			+ "        b.partno,\r\n"
			+ "        b.partdesc,\r\n"
			+ "        b.boqty,\r\n"
			+ "        p.orderqty,\r\n"
			+ "        p.pickedqty,\r\n"
			+ "        p.docid AS prdocid,\r\n"
			+ "        p.pendingqty,\r\n"
			+ "        b.sdate\r\n"
			+ "    FROM \r\n"
			+ "        vw_bo b\r\n"
			+ "    LEFT JOIN \r\n"
			+ "        vw_pr p\r\n"
			+ "    ON \r\n"
			+ "        b.docid = p.buyerordno\r\n"
			+ "        and b.orgid=p.orgid\r\n"
			+ "        AND b.partno = p.partno\r\n"
			+ "        AND b.partdesc = p.partdesc\r\n"
			+ "        AND b.branchcode = p.branchcode\r\n"
			+ "        AND b.batchno = p.batchno\r\n"
			+ "    WHERE \r\n"
			+ "        b.docid = ?4 and b.orgid=?1\r\n"
			+ ") h\r\n"
			+ "GROUP BY\r\n"
			+ "    h.client,\r\n"
			+ "    h.branchcode,\r\n"
			+ "    h.bodocid,\r\n"
			+ "    h.partno,\r\n"
			+ "    h.partdesc,\r\n"
			+ "    h.batchno,\r\n"
			+ "    h.boqty,\r\n"
			+ "    h.sdate\r\n"
			+ "HAVING \r\n"
			+ "    IFNULL(h.boqty, 0) - SUM(IFNULL(h.pickedqty, 0)) > 0\r\n"
			+ ") bp where s.partno = bp.partno and (s.batchno=bp.batchno  or bp.batchno is null)\r\n"
			+ "and REGEXP_REPLACE(s.partdesc, '[^0-9A-Za-z]', '') = REGEXP_REPLACE(bp.partdesc,\r\n"
			+ "'[^0-9A-Za-z]', '')\r\n"
			+ "and branchcode = bp.branchcode and client = bp.client\r\n"
			+ "group by\r\n"
			+ "s.partno,s.partdesc,s.pallet,s.celltype,s.core,s.sku,s.availqty,qcflag,stockdate,\r\n"
			+ "s.expdate,grnno,grndate,bp.pickedqty,tn,s.batchno,s.batchdate,s.binclass,s.bintype\r\n"
			+ " ) jhn\r\n"
			+ "GROUP BY\r\n"
			+ "jhn.partno,jhn.partdesc,jhn.pallet,jhn.celltype,jhn.core,jhn.sku,jhn.availqty,\r\n"
			+ "jhn.remainorderqty,jhn.orderqty,jhn.qcflag,jhn.stockdate,jhn.binclass,jhn.bintype,\r\n"
			+ "jhn.expdate,jhn.grnno,jhn.grndate,jhn.tn,jhn.batchno,jhn.batchdate,jhn.pickedqty\r\n"
			+ ")jn\r\n"
			+ "WHERE (CASE WHEN (jn.availqty - jn.prpqty) < 0 THEN (jn.availqty * -1)\r\n"
			+ "WHEN jn.prpqty < 0 THEN (jn.prpqty + jn.availqty) * -1\r\n"
			+ "WHEN (jn.availqty - jn.prpqty) > 0 THEN (jn.availqty * -1)\r\n"
			+ "WHEN (jn.availqty - jn.prpqty) = 0 THEN (jn.prpqty * -1)  END) < 0 \r\n"
			+ ")jm\r\n"
			+ ")jj\r\n"
			+ "where 'Edit' = ?6\r\n"
			+ "UNION\r\n"
			+ "Select partno,partdesc,sku,grnno,grndate,batchno,batchDate,binclass,bintype,bin pallet,pickqty sqty,celltype,core,expdate,\r\n"
			+ "0 tn,orderqty,remainingqty remainorderqty,avlqty availqty,0 prpqty,\r\n"
			+ "0 trpqty,qcflag,stockdate\r\n"
			+ "From pickrequest a,pickrequestdetails b\r\n"
			+ "Where a.pickrequestid = b.pickrequestid and a.branchcode=?2 and client=?3\r\n"
			+ "And a.docid = ?5 and 'Confirm' = ?6 and orgid=?1\r\n"
			+ "ORDER BY  partno,batchno,trpqty)mm")
	Set<Object[]> getPickRequestFillDetails(Long orgId, String branchCode, String client, String buyerOrderDocId,
			String pickRequestDocId, String pickStatus);

	@Query(nativeQuery = true,value = "select cast(sum(a.sqty)as unsigned)sqty from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status='R' and bin=?5 and partno=?6 and grnno=?7\r\n"
			+ "and batch=?8 or batch is null\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
	int getAvlQty(Long orgId, String branchCode, String warehouse, String client,
			String fromBin, String partNo, String grnNo, String batchNo);

	@Query(nativeQuery = true,value = "select a.* from pickrequest a where a.cancel=0 and a.status='Confirm' and a.orgid=?1 and a.finyear=?2 and a.branch=?3 and a.branchcode=?4 and a.client=?5 order by a.docid desc")
	List<PickRequestVO> getPickDetails(Long orgId, String finYear, String branch, String branchCode, String client);

	@Query(nativeQuery = true,value = "select b.partno,b.partdesc,b.sku,b.grnno,b.grndate,b.batchno,b.batchdate,b.bintype,b.binclass,b.celltype,b.core,b.bin,b.orderqty,b.pickqty,b.expdate,b.qcflag,ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id from pickrequest a, pickrequestdetails b\r\n"
			+ "where a.pickrequestid=b.pickrequestid and a.orgid=?1 and a.branchcode=?2 and a.client=?3 and a.docid=?4")
	Set<Object[]> fillgridDetails(Long orgId, String branchCode, String client, String pickDocId);

	
	@Query(nativeQuery =true,value ="SELECT a.docid, a.totalorderqty, a.totalpickqty, 'Complete' AS status \r\n"
			+ "FROM pickrequest a \r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.finyear = ?5\r\n"
			+ "  AND a.branchcode = ?2 \r\n"
			+ "  AND (MONTH(a.docdate) = ?4 OR ?4 IS NULL)\r\n"
			+ "  AND a.client = ?3 \r\n"
			+ "  AND a.status = 'Confirm'\r\n"
			+ "GROUP BY a.docid, a.totalorderqty, a.totalpickqty\r\n"
			+ "\r\n"
			+ "UNION\r\n"
			+ "\r\n"
			+ "SELECT a.docid, a.totalorderqty, a.totalpickqty, 'Yet to Confirm' AS status \r\n"
			+ "FROM pickrequest a \r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.finyear = ?5\r\n"
			+ "  AND a.branchcode = ?2 \r\n"
			+ "  AND (MONTH(a.docdate) = ?4 OR ?4 IS NULL)\r\n"
			+ "  AND a.client = ?3  \r\n"
			+ "  AND a.status = 'Edit'\r\n"
			+ "GROUP BY a.docid, a.totalorderqty, a.totalpickqty\r\n"
			+ "\r\n"
			+ "UNION\r\n"
			+ "\r\n"
			+ "SELECT a.docid, a.totalorderqty, a.totalpickqty, 'Pending' AS status \r\n"
			+ "FROM pickrequest a \r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.finyear = ?5\r\n"
			+ "  AND a.branchcode = ?2 \r\n"
			+ "  AND (MONTH(a.docdate) = ?4 OR ?4 IS NULL)\r\n"
			+ "  AND a.client = ?3   \r\n"
			+ "  AND a.buyerrefno NOT IN (\r\n"
			+ "      SELECT b.orderno \r\n"
			+ "      FROM buyerorder b \r\n"
			+ "      WHERE b.orgid = ?1 \r\n"
			+ "        AND b.finyear = ?5\r\n"
			+ "        AND b.branchcode = ?2 \r\n"
			+ "        AND b.client = ?3 \r\n"
			+ "      GROUP BY b.orderno\r\n"
			+ "  )\r\n"
			+ "GROUP BY a.docid, a.totalorderqty, a.totalpickqty\r\n"
			+ "")
	Set<Object[]> getPicrequestDashboard(Long orgId, String branchCode, String client, String month, String finyear);

	@Query(value="select a.totalPickQty from PickRequestVO a where a.docId=?1")
	int getTotalPickQty(String pickRequestDocId);

	
}
