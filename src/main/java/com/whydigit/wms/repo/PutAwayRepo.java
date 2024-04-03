package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.PutAwayVO;

public interface PutAwayRepo extends JpaRepository<PutAwayVO, Long> {

	@Query(nativeQuery = true , value = "SELECT a.docid, a.docdate, DATE_FORMAT(a.grndate, '%Y-%m-%d') AS grndate, a.entryno, a.entrydate, a.vehicleno, a.drivername, a.contact, a.goodsdescripition, a.modeofshipment, a.vehicletype, a.securityname,h.putqty\r\n"
			+ "FROM grn a\r\n"
			+ "RIGHT JOIN (\r\n"
			+ "    SELECT grnno, SUM(sqty) AS putqty\r\n"
			+ "    FROM handlingstockin\r\n"
			+ "    WHERE branchcode = ?5 AND client = ?2 \r\n"
			+ "    GROUP BY grnno\r\n"
			+ ") h ON a.docid = h.grnno\r\n"
			+ "WHERE a.orgid= ?1 AND a.cancel = 0 AND branch = ?3 AND finyr = ?4 \r\n"
			+ "AND putqty > 0")
	Set<Object[]> findGrnNoForPutAway(Long orgId, String client, String branch, String finyr, String branchcode);

}
