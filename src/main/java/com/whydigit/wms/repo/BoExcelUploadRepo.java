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

	@Query(value ="select b.orderno, b.orderdate, \r\n"
			+ "case \r\n"
			+ "when b.orderno in (\r\n"
			+ "select a.orderno \r\n"
			+ "from boexcelupload a \r\n"
			+ "where a.orgid =?1 \r\n"
			+ "and a.finyear =?5 \r\n"
			+ "and a.branchcode =?2\r\n"
			+ "and a.warehouse =?3 \r\n"
			+ "and a.client = ?4\r\n"
			+ "group by a.orderno\r\n"
			+ ") \r\n"
			+ "then 'Complete' \r\n"
			+ "else 'Pending' \r\n"
			+ "end as status\r\n"
			+ "from buyerorder b \r\n"
			+ "where b.orgid =?1 \r\n"
			+ "and b.finyear =?5 \r\n"
			+ "and b.branchcode =?2 \r\n"
			+ "and b.client =?4\r\n"
			+ "group by b.orderno, \r\n"
			+ "b.orderdate",nativeQuery =true)
	Set<Object[]> getBuyerorderDashboard(Long orgId, String branchCode, String warehouse, String client,
			String finYear);

}
