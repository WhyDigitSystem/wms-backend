package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.DeKittingVO;

public interface DeKittingRepo extends JpaRepository<DeKittingVO, Long> {

	@Query(nativeQuery = true, value = "select * from dekitting where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<DeKittingVO> findAllDeKitting(Long orgId, String finYear, String branch, String branchCode, String client,
			String warehouse);

	@Query(nativeQuery = true, value = "select * from dekitting where dekittingid=?1")
	DeKittingVO findDeKittingById(Long id);

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getDeKittingDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select partno,partdesc,sku,sum(sqty) as sqty from stockdetails where orgid=?1 and branch=?2 AND branchcode=?3 and client=?4  and parentchildkey='PARENT' GROUP BY partno,partdesc,sku HAVING sum(sqty)>0")
	Set<Object[]> findPartNoFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client);

	@Query(nativeQuery = true, value = "select bin,binclass,bintype,sum(sqty) as avlqty from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and parentchildkey='PARENT' GROUP BY bin,binclass,bintype HAVING SUM(SQTY) > 0")
	Set<Object[]> findBinFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client);

	@Query(nativeQuery = true, value = "select grnno,batch,batchdate,expdate,sum(sqty) as sqty from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 and partno=?6 and partdesc=?7 and sku=?8 and parentchildkey='PARENT' GROUP BY grnno,batch,batchdate,expdate HAVING sum(sqty)>0")
	Set<Object[]> findGrnNoAndBatchAndBatchDateAndExpDateFromStockForDeKittingParent(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo, String partDesc, String sku);

	@Query(nativeQuery = true, value = "SELECT partno,partdesc,sku,sum(sqty) as avlqty from material where orgid=?1  and branch=?2 and branchcode=?3 and client=?4 and parentchildkey='CHILD' GROUP BY partno,partdesc,sku HAVING sum(sqty)>0")
	Set<Object[]> findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true,value="select sum(a.avlqty)fromqty from(select partno,partdesc,grnno,branch,branchcode,client,bin,sku,sum(sqty)as avlqty from stockdetails where  orgid=?1 and branch=?2 AND branchcode=?3 and client=?4 and bin =?5 and partdesc =?6 and sku=?7  and partno=?8 and grnno=?9 and parentchildkey='PARENT' group by partno,partdesc,grnno,branch,branchcode,client,bin,sku having sum(sqty)>0) a")
	Set<Object[]> findAvlQtyFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client,
			String bin, String partDesc, String sku, String partNo, String grnNo);
}
