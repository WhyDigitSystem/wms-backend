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
    	
    	jdbcTemplate.execute("create view vw_clientbinstatus as\r\n"
    			+ "SELECT a.orgid,\r\n"
    			+ "       a.branch,\r\n"
    			+ "       a.branchcode,\r\n"
    			+ "       a.warehouse,\r\n"
    			+ "       a.client,\r\n"
    			+ "       a.binclass,\r\n"
    			+ "       a.bin,\r\n"
    			+ "       a.celltype ,\r\n"
    			+ "       a.bintype,\r\n"
    			+ "       a.binstatus\r\n"
    			+ "FROM (\r\n"
    			+ "    SELECT orgid,\r\n"
    			+ "           branch,\r\n"
    			+ "           branchcode,\r\n"
    			+ "           warehouse,\r\n"
    			+ "           client,\r\n"
    			+ "           binclass,\r\n"
    			+ "           bin,celltype,\r\n"
    			+ "           bintype,\r\n"
    			+ "           SUM(sqty) AS total_qty,\r\n"
    			+ "           CASE WHEN SUM(sqty) > 0 THEN 'Occupied' ELSE 'Empty' END AS binstatus\r\n"
    			+ "    FROM stockdetails\r\n"
    			+ "    GROUP BY orgid, branch, branchcode, warehouse, client, binclass, bin, bintype,celltype\r\n"
    			+ ") a\r\n"
    			+ "UNION ALL\r\n"
    			+ "SELECT orgid,\r\n"
    			+ "       branch,\r\n"
    			+ "       branchcode,\r\n"
    			+ "       warehouse,\r\n"
    			+ "       client,\r\n"
    			+ "       bincategory AS binclass,\r\n"
    			+ "       bin,binstatus as celltype,\r\n"
    			+ "       bintype,\r\n"
    			+ "       'Empty' AS binstatus\r\n"
    			+ "FROM vw_clientbindetails\r\n"
    			+ "WHERE concat(orgid, branch, branchcode, warehouse, client, bin) NOT IN (\r\n"
    			+ "    SELECT concat(orgid, branch, branchcode, warehouse, client, bin)\r\n"
    			+ "    FROM stockdetails\r\n"
    			+ "    GROUP BY orgid, branch, branchcode, warehouse, client, binclass, bin, bintype,celltype\r\n"
    			+ "    HAVING SUM(sqty) > 0\r\n"
    			+ ")\r\n"
    			+ "ORDER BY branch, warehouse, client, bin ASC");
    }

}
