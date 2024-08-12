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
    
    	jdbcTemplate.execute("create or replace view vw_clientlocationdetails as\r\n"
    			+ "select a.branch,a.branchcode,a.client,a.clienttype,a.orgid,b.active,b.bin,b.bincategory,b.core,b.levelno,a.bintype,b.binstatus,b.rowno,b.warehouse from locationmapping a,locationmappingdetails b\r\n"
    			+ "where a.locationmappingid=b.locationmappingid group by a.branch,a.branchcode,a.client,a.clienttype,a.orgid,b.active,b.bin,b.bincategory,b.core,b.levelno,a.bintype,b.binstatus,b.rowno,b.warehouse");
    }

}
