package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockDetailsVO;

@Repository
public interface StockDetailsRepo extends JpaRepository<StockDetailsVO, Long> {

@Query(nativeQuery =true,value ="select sum(sqty) from stockdetails  WHERE orgid = ?1 and pckey='CHILD' AND client = ?3 AND branchcode = ?2 AND warehouse = ?5 AND partno =?4 and grnno=?6 and bin='BULK' AND status = 'V';")
	Set<Object[]> getQtyDetais(Long orgId, String branchCode, String client, String partNo, String warehouse,String grnno);



@Query(nativeQuery = true,value = "select sum(a.sqty)sqty from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status='R' and bin=?5 and partno=?6 and grnno=?7\r\n"
		+ "and batch=?8\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
int getAvlQty(Long orgId, String branchCode, String warehouse, String client,
		String fromBin, String partNo, String grnNo, String batchNo);

@Query(nativeQuery = true,value = "select sum(a.sqty)sqty from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status=?6 and bin=?5 and partno=?6 and grnno=?7\r\n"
		+ "and batch=?8\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
int getAvlQtyforVasPick(Long orgId, String branchCode, String warehouse, String client,
		String fromBin, String partNo, String grnNo, String batchNo,String status);


@Query(value ="select batch from stockdetails where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 and partno=?5 group by batch",nativeQuery =true)
Set<Object[]> getDetails(Long orgId, String branchCode, String client, String warehouse, String partNo);


@Query(nativeQuery = true,value = "select sum(a.sqty)sqty from(\r\n"
		+ "(select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
		+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and status='V' and bin=?5 and partno=?6 and grnno=?7\r\n"
		+ "and batch=?8\r\n"
		+ "group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0)) a")
int getAvlQtyforVasPutaway(Long orgId, String branchCode, String warehouse, String client, String fromBin,
		String partNo, String grnNo, String batchNo);

}