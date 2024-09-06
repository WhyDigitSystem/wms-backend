package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockRestateVO;

@Repository
public interface StockRestateRepo extends JpaRepository<StockRestateVO, Long>{

	@Query(value = "select * from stockrestate where orgid=?1 and finYear=?2 and branch=?3 and branchcode=?4 and client=?5 and warehouse=?6 Order By docid desc",nativeQuery =true)
	List<StockRestateVO> findAllStockRestate(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);


	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getStockRestateDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true,value = "select a.bintype,a.binclass,a.celltype,a.bin,a.core from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.bintype,a.binclass,a.celltype,a.bin,a.core")
	Set<Object[]> getFromBinDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag);

	@Query(nativeQuery = true,value = "select a.partno,a.partdesc,a.sku from(\r\n"
			+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6\r\n"
			+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> getPartNoDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag, String fromBin);

@Query(nativeQuery = true,value = "select a.grnno,a.grndate from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.grnno,a.grndate")
	Set<Object[]> getGrnNoDetails(Long orgId, String branchCode, String warehouse, String client,
			String tranferFromFlag, String fromBin, String partNo);

@Query(nativeQuery = true,value = "select a.batch,a.batchdate,a.expdate from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7 and grnno=?8\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a group by a.batch,a.batchdate,a.expdate")
Set<Object[]> getBatchNoDetails(Long orgId, String branchCode, String warehouse, String client, String tranferFromFlag,
		String fromBin, String partNo, String grnNo);

@Query(nativeQuery = true,value = "select sum(a.sqty)sqty from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5 and bin=?6 and partno=?7 and grnno=?8\r\n"
		+ "and batch=?9 OR batch is NULL\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
int getAvlQty(Long orgId, String branchCode, String warehouse, String client, String tranferFromFlag,
		String fromBin, String partNo, String grnNo, String batchNo);

@Query(nativeQuery = true,value = "select bin,bintype,binclass,celltype,core from wv_locationstatus where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4")
Set<Object[]> getToBinDetails(Long orgId, String branchCode, String warehouse, String client);

@Query(nativeQuery = true,value = "select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,case when ?6='D' then 'Defective' else bin end toBin,case when ?6='D' then 'Defective' else bintype end toBinType,binclass tobinclass,celltype tocellType,core tocore, qcflag,sum(sqty),ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?5\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0")
Set<Object[]> getFillGridDetailsForRestate(Long orgId, String branchCode, String warehouse, String client,
		String tranferFromFlag,String tranferToFlag);

	
}

