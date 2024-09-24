package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;

public interface PutAwayRepo extends JpaRepository<PutAwayVO, Long> {

	@Query(value = "select a.* from putaway a where a.orgid=?1 and a.finYear=?2 and a.branch=?3 and a.branchcode=?4 and a.client=?5 and a.warehouse=?6 Order By a.docid desc", nativeQuery = true)
	List<PutAwayVO> findAllPutAwayDetails(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getPutAwayDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = " select j.partno,j.partdesc,j.sku,j.bintype,j.invqty,j.recqty,j.shortqty,j.damageqty,j.grnqty,j.noofbin,j.bin,j.pq,j.batchno,j.batchdt,j.expdate,ROW_NUMBER() OVER (ORDER BY j.partno, j.grnno, j.partdesc) id from (\r\n"
			+ "WITH a AS (\r\n" + "    SELECT \r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY branchcode, grnno, qcflag DESC) AS rowseq, \r\n"
			+ "        branchcode, \r\n" + "        warehouse, \r\n" + "        client, \r\n" + "        grnno, \r\n"
			+ "        partno, \r\n" + "        partdesc, \r\n" + "        locationtype, \r\n" + "        grndate, \r\n"
			+ "        damageqty, \r\n" + "        shortqty, \r\n" + "        sku,\r\n" + "        qcflag, \r\n"
			+ "        invqty, \r\n" + "        recqty, \r\n" + "        sqty, \r\n" + "        noofpallet, \r\n"
			+ "        CAST(pq AS SIGNED) AS pq, \r\n" + "        CAST(columnvalue AS SIGNED) AS columnvalue,\r\n"
			+ "        batchno, \r\n" + "        batchdt, \r\n" + "        expdate\r\n"
			+ "    FROM wv_handlinginnew \r\n" + "    WHERE grnno = ?5 and orgid=?1  and qcflag = 'T'\r\n"
			+ "    ORDER BY qcflag DESC\r\n" + "),\r\n" + "z AS (\r\n" + "    SELECT \r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY branchcode, grnno, qcflag DESC) AS rowseq, \r\n"
			+ "        branchcode, \r\n" + "        warehouse, \r\n" + "        client, \r\n" + "        grnno, \r\n"
			+ "        partno, \r\n" + "        partdesc, \r\n" + "        locationtype, \r\n" + "        grndate, \r\n"
			+ "        damageqty, \r\n" + "        shortqty, \r\n" + "        sku,\r\n" + "        qcflag, \r\n"
			+ "        invqty, \r\n" + "        recqty, \r\n" + "        sqty, \r\n" + "        noofpallet, \r\n"
			+ "        CAST(pq AS SIGNED) AS pq, -- Ensure pq is treated as integer\r\n"
			+ "        CAST(columnvalue AS SIGNED) AS columnvalue, -- Ensure columnvalue is treated as integer\r\n"
			+ "        batchno, \r\n" + "        batchdt, \r\n" + "        expdate\r\n"
			+ "    FROM wv_handlinginnew \r\n" + "    WHERE grnno = ?5 and orgid=?1  and qcflag = 'F'\r\n"
			+ "    ORDER BY qcflag DESC\r\n" + "),\r\n" + "c AS (\r\n" + "    SELECT \r\n"
			+ "        ROW_NUMBER() OVER (ORDER BY bin DESC) AS binseq, \r\n" + "        bin AS pallet\r\n"
			+ "    FROM (\r\n" + "        SELECT \r\n" + "            bin, \r\n" + "            binstatus\r\n"
			+ "        FROM wv_locationstatus l \r\n" + "        WHERE l.branchcode = ?2 \r\n"
			+ "        AND l.warehouse = ?3 \r\n" + "        AND l.client = ?4 \r\n" + "        and orgid=?1\r\n"
			+ "        AND l.bintype = ?6 \r\n" + "        AND l.binclass = ?7 \r\n"
			+ "        AND (l.binstatus = ?8 or 'Both'=?8 ) \r\n" + "        AND l.bin NOT IN ('Inbound', 'Bulk')\r\n"
			+"AND NOT EXISTS (\r\n"
			+ "      SELECT 1 \r\n"
			+ "      FROM pcexcelupload \r\n"
			+ "      WHERE orgid = ?1 \r\n"
			+ "      AND branchcode = ?2\r\n"
			+ "      AND client = ?4\r\n"
			+ "      GROUP BY grnno\r\n"
			+ "  )union all\r\n"
			+"SELECT a.bin, a.binstatus\r\n"
			+ "FROM wv_locationstatus a\r\n"
			+ "JOIN pcexcelupload b\r\n"
			+ "  ON a.orgid = b.orgid\r\n"
			+ "  AND a.client = b.client\r\n"
			+ "  AND a.branchcode = b.branchcode\r\n"
			+ "  AND a.bin = b.binno\r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.branchcode = ?2\r\n"
			+ "  AND a.client = ?4\r\n"
			+ "  AND b.grnno = ?5\r\n"
			+ "  AND EXISTS (\r\n"
			+ "      SELECT 1 \r\n"
			+ "      FROM pcexcelupload \r\n"
			+ "      WHERE orgid = ?1 \r\n"
			+ "      AND branchcode = ?2\r\n"
			+ "      AND client = ?4\r\n"
			+ "      GROUP BY grnno)\r\n"
			+ "        UNION ALL\r\n" + "        SELECT \r\n" + "            'Inbound' AS bin, \r\n"
			+ "            'Empty' AS binstatus \r\n" + "        WHERE 'Inbound' = ?6 AND 'Empty' = ?8\r\n"
			+ "        \r\n" + "        UNION ALL\r\n" + "        \r\n" + "        SELECT \r\n"
			+ "            'Bulk' AS bin, \r\n" + "            'Empty' AS binstatus \r\n"
			+ "        WHERE 'Bulk' = ?6 AND 'Empty' = ?8\r\n" + "    ) x\r\n" + "),\r\n" + "d AS (\r\n"
			+ "    SELECT \r\n" + "        a.rowseq AS ro, \r\n" + "        1 AS sno, \r\n" + "        a.grnno, \r\n"
			+ "        a.partno, \r\n" + "        a.partdesc, \r\n" + "        a.locationtype, \r\n"
			+ "        a.grndate, \r\n" + "        a.damageqty, \r\n"
			+ "        CAST(a.shortqty AS SIGNED) AS shortqty, \r\n" + "        a.sku, \r\n" + "        a.qcflag, \r\n"
			+ "        a.invqty, \r\n" + "        a.recqty, \r\n" + "        a.sqty, \r\n"
			+ "        CAST(a.columnvalue AS SIGNED) AS column_value, \r\n" + "        a.noofpallet, \r\n"
			+ "        c.pallet, \r\n" + "        a.pq, \r\n"
			+ "        a.sqty - SUM(a.pq) OVER (PARTITION BY a.grnno,a.partno ORDER BY a.grnno,a.partno, c.pallet) AS rpq, \r\n"
			+ "        a.batchno, \r\n" + "        a.batchdt, \r\n" + "        DATE(a.expdate) AS expdate\r\n"
			+ "    FROM a\r\n" + "    JOIN c ON a.rowseq = c.binseq\r\n" + "    union all\r\n" + "SELECT \r\n"
			+ "        z.rowseq AS ro, \r\n" + "        1 AS sno, \r\n" + "        z.grnno, \r\n"
			+ "        z.partno, \r\n" + "        z.partdesc, \r\n" + "        'DEFECTIVE' locationtype, \r\n"
			+ "        z.grndate, \r\n" + "        z.damageqty, \r\n"
			+ "        CAST(z.shortqty AS SIGNED) AS shortqty, \r\n" + "        z.sku, \r\n" + "        z.qcflag, \r\n"
			+ "        z.invqty, \r\n" + "        z.recqty, \r\n" + "        z.sqty, \r\n"
			+ "        CAST(z.columnvalue AS SIGNED) AS column_value, \r\n" + "        1 noofpallet, \r\n"
			+ "        'DEFECTIVE' pallet, \r\n" + "        z.pq, \r\n"
			+ "        z.sqty - SUM(z.pq) OVER (PARTITION BY z.partno ORDER BY z.partno) AS rpq, \r\n"
			+ "        z.batchno, \r\n" + "        z.batchdt, \r\n" + "        DATE(z.expdate) AS expdate\r\n"
			+ "    FROM z \r\n" + ")\r\n" + "SELECT \r\n" + "    d.column_value, \r\n" + "    d.ro, \r\n"
			+ "    d.sno, \r\n" + "    d.grnno, \r\n" + "    d.grndate, \r\n" + "    d.partno, \r\n"
			+ "    d.partdesc,\r\n" + "    d.sku,\r\n" + "    d.locationtype bintype, \r\n" + "    d.invqty,\r\n"
			+ "     d.recqty, \r\n" + "     d.shortqty,\r\n" + "    d.damageqty, \r\n" + "    d.sqty grnqty, \r\n"
			+ "    d.noofpallet noofbin, \r\n" + "    d.pallet bin, \r\n"
			+ "    case when rpq < 0 then pq + rpq else pq end  pq,\r\n" + "    d.rpq, \r\n" + "    d.batchno, \r\n"
			+ "    d.batchdt, \r\n" + "    d.expdate\r\n" + "FROM d  where qcflag = 'T'\r\n" + " union\r\n"
			+ " SELECT \r\n" + "    d.column_value, \r\n" + "    d.ro, \r\n" + "    d.sno, \r\n" + "    d.grnno, \r\n"
			+ "    d.grndate, \r\n" + "    d.partno, \r\n" + "    d.partdesc,\r\n" + "    d.sku,\r\n"
			+ "    d.locationtype bintype, \r\n" + "    d.invqty,\r\n" + "     d.recqty, \r\n" + "     d.shortqty,\r\n"
			+ "    d.damageqty, \r\n" + "    d.sqty grnqty, \r\n" + "    d.noofpallet noofbin, \r\n"
			+ "    d.pallet bin, \r\n" + "    d.pq,\r\n" + "    d.rpq, \r\n" + "    d.batchno, \r\n"
			+ "    d.batchdt, \r\n" + "    d.expdate\r\n" + "FROM d  where qcflag = 'F')j where j.pq>0")
	Set<Object[]> getPutawayGridDetails(Long orgId, String branchCode, String warehouse, String client, String grnNo,
			String binType, String binClass, String binPick);

	@Query(nativeQuery = true, value = "select celltype from wv_locationstatus where orgid=?1 and bin=?2 group by celltype")
	String getCelltype(Long orgId, String bin);

	@Query(value = "select a.docid,a.grnno, a.totalputawayqty,'Complete'status from putaway a where a.orgid =?1\r\n"
			+ "  AND a.finyear =?2 \r\n"
			+ "  AND a.branchcode =?3 and (month(a.docdate)=?5 or ?5 is null)\r\n"
			+ "  AND a.client =?4 and a.status='Confirm'\r\n"
			+ "GROUP BY a.docid,a.grnno, a.totalputawayqty\r\n"
			+ "union\r\n"
			+ "select a.docid,a.grnno, a.totalputawayqty,'Yet to Confirm'status from putaway a where a.orgid =?1\r\n"
			+ "  AND a.finyear =?2 \r\n"
			+ "  AND a.branchcode =?3 and (month(a.docdate)=?5 or ?5 is null)\r\n"
			+ "  AND a.client =?4 and a.status='Edit'\r\n"
			+ "GROUP BY a.docid,a.grnno, a.totalputawayqty\r\n"
			+ "union\r\n"
			+ "select a.docid,a.entryno ,a.totalgrnqty,'Pending' status from grn a  where a.orgid =?1 \r\n"
			+ "  AND a.finyear =?2  and (month(a.docdate)=?5 or ?5 is null)\r\n"
			+ "  AND a.branchcode =?3\r\n"
			+ "  AND a.client =?4 and a.docid not in(select a.grnno from putaway a where a.orgid =?1 \r\n"
			+ "  AND a.finyear =?2 \r\n"
			+ "  AND a.branchcode =?3\r\n"
			+ "  AND a.client =?4 group by a.grnno)",
    nativeQuery = true)

	Set<Object[]> getPutaway(Long orgId, String finYear, String branchCode, String client, int month);

}
