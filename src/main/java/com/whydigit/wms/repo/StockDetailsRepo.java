package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockDetailsVO;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetailsVO, Long> {

@Query(nativeQuery =true,value ="SELECT SUM(sqty)\n"
		+ "FROM stockdetails\n"
		+ "WHERE orgid = ?1\n"
		+ "  AND client = ?4\n"
		+ "  AND branchcode = ?3\n"
		+ "  AND branch = ?2\n"
		+ "  AND warehouse = ?7\n"
		+ "  AND partno = ?5\n"
		+ "  AND partdesc = ?6\n"
		+ "  AND status = 'V'\n"
		+ "")
	Set<Object[]> getQtyDetais(Long orgId, String branch, String branchCode, String client, String partNo,
			String partDesc, String warehouse);

}
