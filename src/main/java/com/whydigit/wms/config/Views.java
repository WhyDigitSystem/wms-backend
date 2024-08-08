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
    			+ "select branch,branchcode,client,clienttype,orgid,active,bin,cellcategory,core,levelno,locationtype,lstatus,rowno,warehouse from locationmappingdetails\r\n"
    			+ "group by branch,branchcode,client,clienttype,orgid,active,bin,cellcategory,core,levelno,locationtype,lstatus,rowno,warehouse order by branch,branchcode,warehouse,client,locationtype,rowno,levelno,bin asc");
    }

}
