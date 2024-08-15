package com.whydigit.wms.repo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.KittingVO;

@Repository
public interface KittingRepo extends JpaRepository<KittingVO, Long> {
	@Query(nativeQuery = true, value = "select * from kitting k where k.orgid=?1 and k.branchcode=?2 and k.client=?3 and k.customer=?4")
	List<KittingVO> findAllKiting(Long orgId, String branchCode, String client, String customer);

	@Query(nativeQuery = true, value = "select * from kitting where kittingid=?1")
	Optional<KittingVO> findKittingById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getKittingInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery =true,value ="select a.partno,a.partdesc,a.sku from(\n"
			+ "select  partno,partdesc,sku,batch,batchdate,grnno,grndate,status,bin,bintype,binclass,celltype,qcflag,sum(sqty) from stockdetails\n"
			+ " where pckey='CHILD' and client=?5 and branchcode=?4 and orgid=?1 and branch=?3 and bin=?2  group by \n"
			+ " partno,partdesc,sku,grnno,grndate,status,bin,batch,batchdate,bintype,binclass,celltype,qcflag having sum(sqty)>0) a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> getPartNOByChild(Long orgId,String bin, String branch, String branchCode, String client);

	@Query(nativeQuery =true,value ="select a.grnno,a.grndate,a.batch,a.batchdate from(\n"
			+ "select  partno,partdesc,sku,batch,batchdate,grnno,grndate,status,bin,bintype,binclass,celltype,qcflag,sum(sqty) from stockdetails\n"
			+ " where pckey='CHILD' and client=?5 and branchcode=?4 and orgid=?1 and branch=?3 and bin=?2 and partno=?6\n"
			+ " and partdesc=?7 \n"
			+ " and sku=?8   group by \n"
			+ " partno,partdesc,sku,grnno,grndate,status,bin,batch,batchdate,bintype,binclass,celltype,qcflag having sum(sqty)>0) a \n"
			+ " group by a.grnno,a.grndate,a.batch,a.batchdate")
	Set<Object[]> getGrnNOByChild(Long orgId, String bin, String branch, String branchCode, String client,
			String partNo, String partDesc, String sku);

	@Query(nativeQuery =true,value ="select partno,partdesc,sku from material where parentchildkey='PARENT' and client=?3 and cbranch='ALL' or cbranch=?2 and orgid=?1 and active=1 group by partno,partdesc,sku")
	Set<Object[]> getPartNOByParent(Long orgId,String branchCode, String client);
	
	
	@Query(nativeQuery =true,value ="select a.grnno,a.grndate,a.batch,a.batchdate from(\n"
			+ "select  partno,partdesc,sku,batch,batchdate,grnno,grndate,status,bin,bintype,binclass,celltype,qcflag,sum(sqty) from stockdetails\n"
			+ " where pckey='PARENT' and client=?5 and branchcode=?4 and orgid=?1 and branch=?3 and bin=?2 and partno=?6\n"
			+ " and partdesc=?7 \n"
			+ " and sku=?8   group by \n"
			+ " partno,partdesc,sku,grnno,grndate,status,bin,batch,batchdate,bintype,binclass,celltype,qcflag having sum(sqty)>0) a \n"
			+ " group by a.grnno,a.grndate,a.batch,a.batchdate")
	Set<Object[]> getGrnNOByParent(Long orgId, String bin, String branch, String branchCode, String client,
			String partNo, String partDesc, String sku);

}
