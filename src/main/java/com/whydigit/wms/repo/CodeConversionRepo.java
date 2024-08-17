package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CodeConversionVO;

@Repository
public interface CodeConversionRepo extends JpaRepository<CodeConversionVO, Long>{

	@Query(value = "select * from codeconversion where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<CodeConversionVO> findAllCodeConversion(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	@Query(nativeQuery = true, value = "select * from codeconversion where codeconversionid=?1")
	CodeConversionVO findCodeConversionById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getCodeConversionDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true,value="select partno,partdesc,sku from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5")
	Set<Object[]> findPartNoAndPartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin);

	@Query(nativeQuery = true,value ="select grnno,bintype,batch,batchdate,lotno from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 and partno=?6 and partdesc=?7 and sku=?8")
	Set<Object[]> findGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(Long orgId,
			 String branch, String branchCode, String client, String bin, String partNo, String partDesc,
			String sku);

	@Query(nativeQuery = true,value = " select bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4")
	Set<Object[]> findBinFromStockForCodeConversion(Long orgId,String branch, String branchCode,
			String client);

	@Query(nativeQuery = true,value="SELECT bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate,partno,partdesc,sku,grnno,batch,batchdate,lotno from stockdetails where orgid=?1 and branch=?2 AND branchcode=?3 and client=?4 AND status='R'")
	Set<Object[]> getAllFillGridFromStockForCodeConversion(Long orgId, String branch, String branchCode, String client);

	@Query(nativeQuery = true,value="select partno,partdesc,sku from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5")
	Set<Object[]> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client, String bin);

	@Query(nativeQuery = true,value = " select bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4")
	Set<Object[]> findCBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
			String client);

	@Query(nativeQuery = true, value = "SELECT SUM(sqty) FROM stockdetails WHERE orgid = ?1 AND client = ?2 AND branchcode = ?3 AND branch = ?4 AND warehouse = ?5 AND partno = ?6 AND partdesc = ?7 AND status = 'R'")
	Set<Object[]> getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc);
	
	

}
