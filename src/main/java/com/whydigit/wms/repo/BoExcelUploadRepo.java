package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BoExcelUploadVO;
@Repository
public interface BoExcelUploadRepo extends JpaRepository<BoExcelUploadVO, Long>{

	boolean existsByOrderNoAndOrgId(Integer orderNo, Long orgId);

	List<BoExcelUploadVO> findByOrderNoAndOrgIdAndClientAndBranchCode(String orderNo, Long orgId, String client,
			String branchCode);

	@Query(nativeQuery = true,value="select ROW_NUMBER() OVER () AS sno, orderno,orderdate,buyername,billto,shipto,referenceno,referencedate,invoiceno,invoicedate from boexcelupload where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and finyear=?5\r\n"
			+ "and orderno not in(select orderno from buyerorder where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and finyear=?5) \r\n"
			+ "group by orderno,orderdate,buyername,billto,shipto,referenceno,referencedate,invoiceno,invoicedate")
	Set<Object[]> getOrderDetailsFromUpload(Long orgId, String branchCode, String warehouse, String client,
			String finYear);

	@Query(value ="SELECT \r\n"
			+ "    b.orderno, \r\n"
			+ "    b.orderdate, \r\n"
			+ "    b.totalorderqty AS qty, \r\n"
			+ "    'Complete' AS status \r\n"
			+ "FROM buyerorder b \r\n"
			+ "WHERE b.orgid =?1 \r\n"
			+ "  AND b.finyear =?5 \r\n"
			+ "  AND b.branchcode =?2 \r\n"
			+ "  AND b.client =?4 \r\n"
			+ "  AND (MONTH(b.orderdate) =?6 or ?6 is null)\r\n"
			+ "  AND b.orderno NOT IN (\r\n"
			+ "      SELECT a.orderno \r\n"
			+ "      FROM boexcelupload a \r\n"
			+ "      WHERE a.orgid =?1 \r\n"
			+ "        AND a.finyear = ?5\r\n"
			+ "        AND a.branchcode = ?2 \r\n"
			+ "        AND a.warehouse = ?3\r\n"
			+ "        AND a.client = ?4\r\n"
			+ "        AND (MONTH(a.orderdate) =?6 or ?6 is null)\r\n"
			+ "  )\r\n"
			+ "UNION\r\n"
			+ "SELECT \r\n"
			+ "    a.orderno, \r\n"
			+ "    a.orderdate, \r\n"
			+ "    a.qty, \r\n"
			+ "    'Pending' AS status \r\n"
			+ "FROM boexcelupload a \r\n"
			+ "WHERE a.orgid = 1000000001 \r\n"
			+ "  AND a.finyear =?5\r\n"
			+ "  AND a.branchcode =?2\r\n"
			+ "  AND a.warehouse = ?3\r\n"
			+ "  AND a.client = ?4 \r\n"
			+ "  AND (MONTH(a.orderdate) = ?6 or ?6 is null)\r\n"
			+ "  AND a.orderno NOT IN (\r\n"
			+ "      SELECT b.orderno \r\n"
			+ "      FROM buyerorder b \r\n"
			+ "      WHERE b.orgid =?1\r\n"
			+ "        AND b.finyear =?5\r\n"
			+ "        AND b.branchcode = ?2\r\n"
			+ "        AND b.client = ?4 \r\n"
			+ "        AND (MONTH(b.orderdate) = ?6 or ?6 is null)\r\n"
			+ "  )\r\n"
			+ "UNION\r\n"
			+ "SELECT \r\n"
			+ "    a.orderno, \r\n"
			+ "    a.orderdate, \r\n"
			+ "    a.qty, \r\n"
			+ "    'Complete' AS status \r\n"
			+ "FROM boexcelupload a \r\n"
			+ "WHERE a.orgid = ?1\r\n"
			+ "  AND a.finyear = ?5 \r\n"
			+ "  AND a.branchcode = ?2\r\n"
			+ "  AND a.warehouse = ?3 \r\n"
			+ "  AND a.client = ?4\r\n"
			+ "  AND (MONTH(a.orderdate) = ?6 or ?6 is null)\r\n"
			+ "  AND a.orderno IN (\r\n"
			+ "      SELECT b.orderno \r\n"
			+ "      FROM buyerorder b \r\n"
			+ "      WHERE b.orgid =?1\r\n"
			+ "        AND b.finyear =?5\r\n"
			+ "        AND b.branchcode =?2 \r\n"
			+ "        AND b.client =?4\r\n"
			+ "        AND (MONTH(b.orderdate) = ?5 or ?5 is null)\r\n"
			+ "  )\r\n"
			+ "",nativeQuery =true)
	Set<Object[]> getBuyerorderDashboard(Long orgId, String branchCode, String warehouse, String client,
			String finYear, int month);

}
