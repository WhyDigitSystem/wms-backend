package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockDetailsVO;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetailsVO, Long> {

	@Query(nativeQuery = true,value = "SELECT SUM(sqty) \r\n"
			+ "FROM stockdetails \r\n"
			+ "WHERE orgid = ?1 \r\n"
			+ "AND client = ?2 \r\n"
			+ "AND branchcode = ?3 \r\n"
			+ "AND branchc = ?4 \r\n"
			+ "AND warehouse = ?5 \r\n"
			+ "AND partno = ?6 \r\n"
			+ "AND partdesc = ?7 \r\n"
			+ "AND status = 'R'\r\n"
			+ "")
	
	Set<Object[]> getQtyDetais(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc);

	

}
