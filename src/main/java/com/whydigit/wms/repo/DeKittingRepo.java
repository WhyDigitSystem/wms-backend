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

	@Query(nativeQuery = true, value = "select partno,partdesc,sku from(\r\n"
			+ "			(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails\r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3  and client=?4 and status='R' and pckey='PARENT'\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0))\r\n"
			+ "            a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> findPartNoFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client);

	@Query(nativeQuery = true, value = "select a.bin,a.binclass,a.celltype,a.core,a.bintype,a.qcflag from(\r\n"
			+ "			(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails\r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3  and client=?4 and status='R' and pckey='PARENT' and partno=?5 and grnno=?6 and batch=?7 batch is null\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0))\r\n"
			+ "            a group by a.bintype,a.binclass,a.celltype,a.core,a.bin,a.qcflag")
	Set<Object[]> findBinFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client,
			String partNo, String grnNo, String batchNo);

	@Query(nativeQuery = true, value = "select a.grnno,a.grndate from(\r\n"
			+ "			(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails\r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3  and client=?4 and status='R' and pckey='PARENT' and partno=?5\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0))\r\n"
			+ "            a group by a.grnno,a.grndate")
	Set<Object[]> findGrnNoFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client,
			String partNo);

	@Query(nativeQuery = true, value = "select partno,partdesc,sku from material where active=1 and orgid=?1 and client=?3 and cbranch='ALL' or cbranch=?2 and parentchildkey='CHILD'\r\n"
			+ "            group by partno,partdesc,sku")
	Set<Object[]> findPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(Long orgId, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "select a.sqty from(\r\n"
			+ "			(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails\r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3  and client=?4 and status='R' and pckey='PARENT' and partno=?5 and grnno=?6 and batch=?7 or batch is null and bin=?8\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0))\r\n"
			+ "            a group by a.sqty")
	Integer findAvlQtyFromStockForDeKittingParent(Long orgId, String branch, String branchCode, String client,
			String partNo, String grnNo, String batchNo, String bin);

	@Query(nativeQuery = true, value = "select a.batch,a.batchDate,a.expdate from(\r\n"
			+ "			(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails\r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3  and client=?4 and status='R' and pckey='PARENT' and partno=?5 and grnno=?6\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0))\r\n"
			+ "            a group by a.batch,a.batchDate,a.expdate")
	Set<Object[]> findBatchDetails(Long orgId, String branch, String branchCode, String client, String partNo,
			String grnNo);

}
