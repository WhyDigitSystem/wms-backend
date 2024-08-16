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

}
