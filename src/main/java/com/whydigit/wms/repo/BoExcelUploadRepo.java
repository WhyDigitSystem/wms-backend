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

}
