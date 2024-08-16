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

	@Query(nativeQuery = true, value = "select partno,partdesc,sku,sum(sqty) as sqty from stockdetails where orgid=?1 and finyear=?2 and branch=?3 AND branchcode=?4 and client=?5 GROUP BY partno,partdesc,sku HAVING sum(sqty)>0")
	Set<Object[]> findPartNoFromStockForDeKittingParent(Long orgId, String finYear, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "select bin,binclass,bintype,sum(sqty) as avlqty from stockdetails  where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5 GROUP BY bin,binclass,bintype HAVING SUM(SQTY) > 0")
	Set<Object[]> findBinFromStockForDeKittingParent(Long orgId, String finYear, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "grnno,batch,batchdate,lotno,expdate,sum(sqty) as sqty from stockdetails  where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5 and bin=?6 and partno=?7 and partdesc=?8 and sku=?9 GROUP BY grnno,batch,batchdate,lotno,expdate HAVING sum(sqty)>0")
	Set<Object[]> findGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku);

	@Query(nativeQuery = true, value = "select sum(sqty) as avlqty from stockdetails where orgid=?1 and finyear=?2 and branch=?3 AND branchcode=?4 and client=?5 and bin =?6 and partdesc =?7 and sku=?8  and partno=?9 and grnno=?10 and lotno=?11")
	Set<Object[]> findAvlQtyFromStockForDeKittingParent(Long orgId, String finYear, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo);

	@Query(nativeQuery = true, value = "SELECT partno,partdesc,sku from material where orgid=?1  and branch=?2 and branchcode=?3 and client=?4 and parentchildkey='CHILD'")
	Set<Object[]> findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branch, String branchCode,
			String client);
}
