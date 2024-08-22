package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;

@Repository
public interface VasPutawayDetailsRepo extends JpaRepository<VasPutawayDetailsVO, Long> {

	List<VasPutawayDetailsVO> findByVasPutawayVO(VasPutawayVO vasPutawayVO);

	@Query(nativeQuery = true,value ="select a.partno,a.partdescription,a.grnno,a.picqty,a.bin,a.sku from vaspickdetails a,vaspick b where a.vaspickid=b.vaspickid and b.orgid=?1 and b.branch=?2 and b.client=?3 and b.docid=?4")
	Set<Object[]> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch, String client,
			String docId);

	@Query(nativeQuery = true,value="SELECT bin,binclass,celltype,core,expdate,stockdate,partno,partdescription,sku,grnno,grndate,picqty,avlqty,ROW_NUMBER() OVER (ORDER BY partdescription, partno) AS id from vaspickdetails a , vaspick b where  a.vaspickid=b.vaspickid  and b.orgid=?1 and b.branch=?2 AND b.branchcode=?3 and b.client=?4 and b.docid=?5")
	Set<Object[]> getAllFillGridFromVasPutaway(Long orgId, String branch, String branchCode, String client,String docId);


	@Query(nativeQuery = true,value="SELECT buyerRefDate,invoiceNo,customername,clientname from pickrequest  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and buyerrefno=?5 ")
	Set<Object[]> getBuyerRefDateInvoiceBillToShipToFromPickRequestForDeliveryChallan(Long orgId,
			String branch,	String branchCode, String client, String pickRequestDocId);

	
	@Query(nativeQuery = true, value = "SELECT SUM(sqty) FROM stockdetails WHERE orgid = ?1 AND client = ?2 AND branchcode = ?3 AND branch = ?4 AND warehouse = ?5 AND partno = ?6 AND partdesc = ?7 AND status = 'R'")
	Set<Object[]> getAvlQtyVasPutaway(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc);

	@Query(nativeQuery = true, value = "select bin,binclass,celltype,core,bintype from wv_locationstatus where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 ")
	Set<Object[]> getToBinDetailsVasPutaway(Long orgId, String branchCode, String client, String warehouse);

}
