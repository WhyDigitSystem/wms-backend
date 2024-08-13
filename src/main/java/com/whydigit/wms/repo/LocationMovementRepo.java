package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;

public interface LocationMovementRepo extends JpaRepository<LocationMovementVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getLocationMovementDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select * from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	DocumentTypeMappingDetailsVO findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(Long orgId, String finYear,
			String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select * from locationmovement where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<LocationMovementVO> findAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	@Query(nativeQuery = true, value = "select * from locationmovement where locationmovementid =?1")
	LocationMovementVO findLocationMovementById(Long id);

	@Query(nativeQuery = true, value = " select bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate from stockdetails  where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5")
	Set<Object[]> findBinFromStockForLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "select partno,partdesc,sku from stockdetails  where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5 and bin=?6")
	Set<Object[]> findPartNoAndPartDescFromStockForLocationMovement(Long orgId, String finYear, String branch,
			String branchCode, String client, String bin);

	@Query(nativeQuery = true, value = "select grnno,batch,batchdate,lotno from stockdetails  where orgid=?1 and finyear=?2 and branch=?3 and branchcode=?4 and client=?5 and bin=?6 and partno=?7 and partdesc=?8 and sku=?9")
	Set<Object[]> findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId, String finYear,
			String branch, String branchCode, String client, String bin, String partNo, String partDesc, String sku);

	@Query(nativeQuery = true, value = "SELECT bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate,partno,partdesc,sku,grnno,batch,batchdate,lotno from stockdetails where orgid=?1 and branch=?2 AND branchcode=?3 and client=?4 AND status=\"R\"")
	Set<Object[]> findAllForLocationMovementDetailsFillGrid(Long orgId, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "select sum(sqty) as avlqty from stockdetails where orgid=?1 and finyear=?2 and branch=?3 AND branchcode=?4 and client=?5 and bin =?6 and partdesc =?7 and sku=?8  and partno=?9 and grnno=?10 and lotno=?11")
	Set<Object[]> findAvlQtyFromStockForLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String bin, String partDesc, String sku, String partNo, String grnNo, String lotNo);

}
