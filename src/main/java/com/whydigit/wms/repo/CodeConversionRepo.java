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

	@Query(nativeQuery = true,value="select a.partno,a.partdesc,a.sku from(SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,status,qcflag,SUM(sqty) AS total_qty    FROM stockdetails   WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4   GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,qcflag  HAVING SUM(sqty) > 0)a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> findPartNoAndPartDescFromStockForCodeConversion(Long orgId, String branch,
			String branchCode, String client);

	@Query(nativeQuery = true,value ="select a.grnno,a.grndate from(  SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,status,qcflag,SUM(sqty) AS total_qty  FROM stockdetails WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4 AND partno=?5  GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,qcflag  HAVING SUM(sqty) > 0)a group by a.grnno,a.grndate ")
	Set<Object[]> findGrnNoAndGrnDateFromStockForCodeConversion(Long orgId,
			 String branchCode, String client, String warehouse, String partNo);

	@Query(nativeQuery = true,value ="select a.bintype from( SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,status,qcflag,SUM(sqty) AS total_qty FROM stockdetails  WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4 AND partno=?5  AND grnno=?6 GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,qcflag  HAVING SUM(sqty) > 0)a group by a.bintype ")
	Set<Object[]> findBinTypeFromStockForCodeConversion(Long orgId, String branchCode, String client, String warehouse,
			String partNo, String grnNo);
	
	@Query(nativeQuery = true,value ="select a.batch,a.batchdate,a.expdate,a.lotno from( SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,lotno,status,qcflag,SUM(sqty) AS total_qty FROM stockdetails WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4 AND partno=?5 and grnno=?6 and bintype=?7 GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,lotno,qcflag HAVING SUM(sqty) > 0)a group by a.batch,a.batchdate,a.expdate,a.lotno")
	Set<Object[]> findBatchNoFromStockForCodeConversion(Long orgId, String branchCode, String client, String warehouse,
			String partNo, String grnNo,String binType);


	@Query(nativeQuery = true,value=" SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,status,qcflag,SUM(sqty) AS total_qty,ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id  FROM stockdetails WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4 GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,qcflag HAVING SUM(sqty) > 0")
	Set<Object[]> getAllFillGridFromStockForCodeConversion(Long orgId, String branchCode, String client,String warehouse);

	
	
	@Query(nativeQuery = true, value = "SELECT SUM(sqty) AS totalqty FROM stockdetails WHERE orgid = ?1 AND client = ?2 AND branchcode = ?3 AND warehouse = ?4 AND branch = ?5  AND partno = ?6 AND grnno = ?7 and batch=?8 OR NULL and bintype=?9 and bin=?10 AND status = 'R'")
	Integer getAvlQtyCodeConversion(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String grnNo,String batchNo,String binType,String bin);

	@Query(nativeQuery = true,value ="select a.bin,a.binclass,a.celltype,a.core from( SELECT partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,status,qcflag,SUM(sqty) AS total_qty FROM stockdetails WHERE orgid =?1 AND branchcode =?2 AND client =?3 AND warehouse =?4 AND partno=?5 and grnno=?6 and bintype=?7 and batch=?8 OR NULL GROUP BY partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,status,binclass,celltype,core,bin,qcflag HAVING SUM(sqty) > 0)a group by a.bin,a.binclass,a.celltype,a.core")
	Set<Object[]> findBinFromStockForCodeConversion(Long orgId, String branchCode, String client, String warehouse,
			String partNo, String grnNo, String binType, String batchNo);



	
//	@Query(nativeQuery = true,value="select partno,partdesc,sku from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5")
//	Set<Object[]> getCpartNoAndCpartDescFromStockForCodeConversion(Long orgId, String branch,
//			String branchCode, String client, String bin);
//
//	@Query(nativeQuery = true,value = " select bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4")
//	Set<Object[]> findCBinFromStockForCodeConversion(Long orgId, String branch, String branchCode,
//			String client);

//	@Query(nativeQuery = true,value = " select bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate from stockdetails  where orgid=?1 and branch=?2 and branchcode=?3 and client=?4")
//	Set<Object[]> findBinFromStockForCodeConversion(Long orgId,String branch, String branchCode,
//			String client);
//	
	
	

}
