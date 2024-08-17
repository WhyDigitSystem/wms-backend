package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BuyerOrderVO;
@Repository
public interface BuyerOrderRepo extends JpaRepository<BuyerOrderVO, Long>{

	boolean existsByOrderNoAndOrgIdAndClientAndBranchCodeAndCustomer(String orderNo, Long orgId, String client,
			String branchCode, String customer);

	@Query(value ="select * from buyerorder where orgid=?1",nativeQuery =true )
	List<BuyerOrderVO> findAllBuyerOrderByOrgId(Long orgId);

	@Query(value ="select * from buyerorder where buyerorderid=?1",nativeQuery =true)
	List<BuyerOrderVO> findAllBuyerOrderById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getbuyerOrderDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	
	@Query(nativeQuery =true,value = "SELECT partno,\n"
			+ "       partdesc,\n"
			+ "       batch,\n"
			+ "       SUM(sqty) AS total_sqty\n"
			+ "FROM stockdetails\n"
			+ "WHERE orgid =?1\n"
			+ "  AND  branchcode =?2\n"
			+ "  AND client = ?3\n"
			+ "  AND status = 'R'\n"
			+ "  AND BATCH=?4\n"
			+ "  AND warehouse=?5\n"
			+ "GROUP BY partno, partdesc, batch\n"
			+ "HAVING SUM(sqty) > 0")
	Set<Object[]> getBoSku(Long orgId, String branchCode, String client,String batch,String warehouse);

	
	@Query(nativeQuery =true,value = "select sum(sqty) from stockdetails where orgid=?1 and partno=?6 and branchcode=?3 and\n"
			+ "partdesc=?7 and batch?8 and warehouse=?4 \n"
			+ "and branch=?5 and client=?2  and status='R'\n"
			+ " group by partno,partdesc,batch,warehouse")
	Set<Object[]> getAvilableQty(Long orgId, String client, String branchCode, String warehouse, String branch,
			String partNo, String partDesc, String batch);

	@Query(nativeQuery = true,value="select buyer,buyershortname,shipto,billto from buyerorder where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and docid=?5")
	Set<Object[]> findBuyerShipToBillToFromBuyerOrderForDeliveryChallan(Long orgId, String branch,
			String branchCode, String client, String buyerOrderNo);


	
}
