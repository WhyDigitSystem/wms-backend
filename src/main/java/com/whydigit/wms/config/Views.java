package com.whydigit.wms.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Views {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        try {
            executeQueries();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    private void executeQueries() {
    
    	jdbcTemplate.execute("create or replace view vw_clientbindetails as\r\n"
    			+ "select a.branch,a.branchcode,a.client,a.clienttype,a.orgid,b.active,b.bin,b.bincategory,b.core,b.levelno,a.bintype,b.binstatus,b.rowno,b.warehouse from locationmapping a,locationmappingdetails b\r\n"
    			+ "where a.locationmappingid=b.locationmappingid group by a.branch,a.branchcode,a.client,a.clienttype,a.orgid,b.active,b.bin,b.bincategory,b.core,b.levelno,a.bintype,b.binstatus,b.rowno,b.warehouse");
    	jdbcTemplate.execute("create or replace view WV_HANDLINGINNEW as\r\n"
    			+ "WITH RECURSIVE seq AS (\r\n"
    			+ "    SELECT 1 AS n\r\n"
    			+ "    UNION ALL\r\n"
    			+ "    SELECT n + 1 \r\n"
    			+ "    FROM seq \r\n"
    			+ "    WHERE n < (SELECT MAX(handlingstockin.noofpallet) FROM handlingstockin)\r\n"
    			+ ")\r\n"
    			+ "SELECT \r\n"
    			+ "    a.branchcode AS branchcode,\r\n"
    			+ "    a.warehouse AS warehouse,\r\n"
    			+ "    a.client AS client,\r\n"
    			+ "    a.grnno AS grnno,\r\n"
    			+ "    a.partno AS partno,\r\n"
    			+ "    a.partdesc AS partdesc,\r\n"
    			+ "    a.locationtype AS locationtype,\r\n"
    			+ "    a.grndate AS grndate,\r\n"
    			+ "    a.damageqty AS damageqty,\r\n"
    			+ "    a.shortqty AS shortqty,\r\n"
    			+ "    a.sku AS sku,\r\n"
    			+ "    a.orgid as orgid,\r\n"
    			+ "    a.qcflag AS qcflag,\r\n"
    			+ "    a.invqty AS invqty,\r\n"
    			+ "    a.recqty AS recqty,\r\n"
    			+ "    a.palletqty AS palletqty,\r\n"
    			+ "    a.sqty AS sqty,\r\n"
    			+ "    a.noofpallet AS noofpallet,\r\n"
    			+ "    a.pq AS pq,\r\n"
    			+ "    a.batchno AS batchno,\r\n"
    			+ "    a.batchdt AS batchdt,\r\n"
    			+ "    a.expdate AS expdate,\r\n"
    			+ "    l.n AS columnvalue\r\n"
    			+ "FROM (\r\n"
    			+ "    SELECT \r\n"
    			+ "        handlingstockin.branchcode AS branchcode,\r\n"
    			+ "        handlingstockin.warehouse AS warehouse,\r\n"
    			+ "        handlingstockin.client AS client,\r\n"
    			+ "        handlingstockin.grnno AS grnno,\r\n"
    			+ "        handlingstockin.partno AS partno,\r\n"
    			+ "        handlingstockin.partdesc AS partdesc,\r\n"
    			+ "        handlingstockin.locationtype AS locationtype,\r\n"
    			+ "        handlingstockin.grndate AS grndate,\r\n"
    			+ "        SUM(handlingstockin.damageqty) AS damageqty,\r\n"
    			+ "        SUM(handlingstockin.shortqty) AS shortqty,\r\n"
    			+ "        handlingstockin.sku AS sku,\r\n"
    			+ "        handlingstockin.orgid as orgid,\r\n"
    			+ "        handlingstockin.qcflag AS qcflag,\r\n"
    			+ "        SUM(handlingstockin.invqty) AS invqty,\r\n"
    			+ "        SUM(handlingstockin.recqty) AS recqty,\r\n"
    			+ "        SUM(handlingstockin.palletqty) AS palletqty,\r\n"
    			+ "        SUM(handlingstockin.sqty) AS sqty,\r\n"
    			+ "        SUM(handlingstockin.noofpallet) AS noofpallet,\r\n"
    			+ "        SUM(handlingstockin.palletqty) AS pq,\r\n"
    			+ "        handlingstockin.batchno AS batchno,\r\n"
    			+ "        handlingstockin.batchdt AS batchdt,\r\n"
    			+ "        handlingstockin.expdate AS expdate\r\n"
    			+ "    FROM \r\n"
    			+ "        handlingstockin \r\n"
    			+ "    GROUP BY \r\n"
    			+ "        handlingstockin.branchcode,\r\n"
    			+ "        handlingstockin.warehouse,\r\n"
    			+ "        handlingstockin.client,\r\n"
    			+ "        handlingstockin.grnno,\r\n"
    			+ "        handlingstockin.partno,\r\n"
    			+ "        handlingstockin.partdesc,\r\n"
    			+ "        handlingstockin.locationtype,\r\n"
    			+ "        handlingstockin.sku,\r\n"
    			+ "        handlingstockin.orgid,\r\n"
    			+ "        handlingstockin.qcflag,\r\n"
    			+ "        handlingstockin.grndate,\r\n"
    			+ "        handlingstockin.batchno,\r\n"
    			+ "        handlingstockin.batchdt,\r\n"
    			+ "        handlingstockin.expdate\r\n"
    			+ "    HAVING \r\n"
    			+ "        SUM(handlingstockin.sqty) > 0\r\n"
    			+ ") a \r\n"
    			+ "JOIN seq l ON (l.n <= a.noofpallet) \r\n"
    			+ "ORDER BY \r\n"
    			+ "    a.grnno, a.partno, l.n\r\n"
    			+ "");
    	jdbcTemplate.execute("CREATE OR REPLACE VIEW WV_LOCATIONSTATUS AS\r\n"
    			+ "SELECT a.orgid,\r\n"
    			+ "       a.branch,\r\n"
    			+ "       a.branchcode,\r\n"
    			+ "       a.warehouse,\r\n"
    			+ "       a.client,\r\n"
    			+ "       a.binclass,\r\n"
    			+ "       a.bin,\r\n"
    			+ "       a.celltype,\r\n"
    			+ "       a.bintype,\r\n"
    			+ "       a.core,\r\n"
    			+ "       a.binstatus\r\n"
    			+ "FROM (\r\n"
    			+ "    SELECT orgid,\r\n"
    			+ "           branch,\r\n"
    			+ "           branchcode,\r\n"
    			+ "           warehouse,\r\n"
    			+ "           client,\r\n"
    			+ "           binclass,\r\n"
    			+ "           bin,\r\n"
    			+ "           celltype,\r\n"
    			+ "           bintype,\r\n"
    			+ "           core,\r\n"
    			+ "           SUM(sqty) AS total_qty,\r\n"
    			+ "           CASE WHEN SUM(sqty) > 0 THEN 'Occupied' ELSE 'Empty' END AS binstatus\r\n"
    			+ "    FROM stockdetails\r\n"
    			+ "    GROUP BY orgid, branch, branchcode, warehouse, client, binclass, bin, bintype,core, celltype\r\n"
    			+ ") a\r\n"
    			+ "UNION ALL\r\n"
    			+ "SELECT orgid,\r\n"
    			+ "       branch,\r\n"
    			+ "       branchcode,\r\n"
    			+ "       warehouse,\r\n"
    			+ "       client,\r\n"
    			+ "       bincategory AS binclass,\r\n"
    			+ "       bin,\r\n"
    			+ "       binstatus AS celltype,\r\n"
    			+ "       bintype,\r\n"
    			+ "       core,\r\n"
    			+ "       'Empty' AS binstatus\r\n"
    			+ "FROM vw_clientbindetails\r\n"
    			+ "WHERE CONCAT(orgid, branch, branchcode, warehouse, client, bin) NOT IN (\r\n"
    			+ "    SELECT CONCAT(orgid, branch, branchcode, warehouse, client, bin)\r\n"
    			+ "    FROM stockdetails\r\n"
    			+ "    GROUP BY orgid, branch, branchcode, warehouse, client, binclass, bin, bintype,core, celltype\r\n"
    			+ "    HAVING SUM(sqty) > 0\r\n"
    			+ ")\r\n"
    			+ "ORDER BY branch, warehouse, client, bin ASC");
    }

}
