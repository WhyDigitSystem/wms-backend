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

	@Query(value = "SELECT partno, partdesc, sku " +
            "FROM stockdetails " +
            "WHERE orgid = ?1 " +
            "  AND client = ?3 " +
            "  AND branchcode = ?2 " +
            "  AND warehouse = ?4 " +
            "  AND status = 'R' " +
            "  AND pckey = 'CHILD' " +
            "GROUP BY partno, partdesc, sku",
    nativeQuery = true)
	Set<Object[]> getPartNOByChild(Long orgId, String branchCode, String client, String warehouse);

	@Query(nativeQuery =true,value = "SELECT grnno,grndate\r\n"
			+ "FROM stockdetails\r\n"
			+ "WHERE orgid =?1\r\n"
			+ "  AND client =?3\r\n"
			+ "  AND branchcode =?2\r\n"
			+ "  AND warehouse =?4\r\n"
			+ "  AND status = 'R'\r\n"
			+ "  AND pckey = 'CHILD'\r\n"
			+ "  AND partno=?5 \r\n"
			+ "GROUP BY grnno,grndate\r\n"
			+ "")
	Set<Object[]> getGrnNOByChild(Long orgId, String branchCode, String client, String warehouse, String partNo);
	
	
	@Query(value = "SELECT batch, batchdate, expdate, id " +
            "FROM (" +
            "    SELECT " +
            "        batch, " +
            "        batchdate, " +
            "        expdate, " +
            "        ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id " +
            "    FROM stockdetails " +
            "    WHERE orgid = ?1 " +
            "      AND client = ?3 " +
            "      AND branchcode = ?2 " +
            "      AND warehouse = ?4 " +
            "      AND status = 'R' " +
            "      AND pckey = 'CHILD' " +
            "      AND partno = ?5 " +
            "      AND grnno = ?6 " +
            "    GROUP BY batch, batchdate, expdate, partdesc, partno" +
            ") AS subquery",
 nativeQuery = true)
	Set<Object[]> getBatch(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNo);

	
	
	@Query(value ="SELECT bin, bintype, lotno, celltype, binclass, core, qcflag\r\n"
			+ "FROM stockdetails\r\n"
			+ "WHERE orgid = ?1\r\n"
			+ "  AND client = ?3\r\n"
			+ "  AND branchcode = ?2\r\n"
			+ "  AND warehouse = ?4\r\n"
			+ "  AND partno = ?5\r\n"
			+ "  AND grnno = ?6\r\n"
			+ "  AND status = 'R'\r\n"
			+ "  AND pckey = 'CHILD'\r\n"
			+ "GROUP BY bin, bintype, lotno, celltype, binclass, core, qcflag\r\n"
			+ "",nativeQuery =true)
	Set<Object[]> getBin(Long orgId, String branchCode, String client, String warehouse, String partNo, String grnNo);

}
