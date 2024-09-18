package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.LocationMovementVO;

public interface LocationMovementRepo extends JpaRepository<LocationMovementVO, Long> {

	@Query(nativeQuery = true, value = "select concat(prefixfield,lpad(lastno,6,'0')) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	String getLocationMovementDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select * from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client =?4 and screencode=?5")
	DocumentTypeMappingDetailsVO findByOrgIdAndFinYearAndBranchCodeAndClientAndScreenCode(Long orgId, String finYear,
			String branchCode, String client, String screenCode);

	@Query(nativeQuery = true, value = "select * from locationmovement where orgid=?1 and finyear=?2 and branch =?3 and branchcode=?4 and client=?5 and warehouse =?6 order by docid desc")
	List<LocationMovementVO> findAllLocationMovement(Long orgId, String finYear, String branch, String branchCode,
			String client, String warehouse);

	@Query(nativeQuery = true, value = "select * from locationmovement where locationmovementid =?1")
	LocationMovementVO findLocationMovementById(Long id);

	@Query(nativeQuery = true, value = "select a.bintype,a.binclass,a.celltype,a.bin,a.core from(\r\n"
			+ "			(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and status='R'\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            )) a group by a.bintype,a.binclass,a.celltype,a.bin,a.core")
	Set<Object[]> findBinFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client);

	@Query(nativeQuery = true, value = "select a.partno,a.partdesc,a.sku from(\r\n"
			+ "			(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 and status='R'\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            )) a group by a.partno,a.partdesc,a.sku")
	Set<Object[]> findPartNoAndPartDescFromStockForLocationMovement(Long orgId, String branch, String branchCode,
			String client, String bin);

	@Query(nativeQuery = true, value = "select a.GRNNO,a.GRNDATE from(\r\n"
			+ "			(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 AND PARTNO=?6 and status='R' \r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            )) a group by a.GRNNO,a.GRNDATE")
	Set<Object[]> findGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(Long orgId, String branch,
			String branchCode, String client, String bin, String partNo);

	@Query(nativeQuery = true, value = "SELECT \r\n"
			+ "    bin,\r\n"
			+ "    binclass,\r\n"
			+ "    celltype,\r\n"
			+ "    core,\r\n"
			+ "    expdate,\r\n"
			+ "    pckey,\r\n"
			+ "    partno,\r\n"
			+ "    partdesc,\r\n"
			+ "    sku,\r\n"
			+ "    grnno,\r\n"
			+ "    batch,\r\n"
			+ "    batchdate,\r\n"
			+ "    grndate,\r\n"
			+ "    SUM(sqty) AS sqty,\r\n"
			+ "    bintype,\r\n"
			+ "    ROW_NUMBER() OVER (ORDER BY partdesc, partno) AS id\r\n"
			+ "FROM \r\n"
			+ "    stockdetails \r\n"
			+ "WHERE \r\n"
			+ "    orgid = ?1\r\n"
			+ "    AND branch = ?2\r\n"
			+ "    AND branchcode = ?3\r\n"
			+ "    AND client = ?4\r\n"
			+ "    AND status = 'R' \r\n"
			+ "    AND (?5 IS NULL OR ?5 NOT IN (\r\n"
			+ "        SELECT \r\n"
			+ "            entryno \r\n"
			+ "        FROM \r\n"
			+ "            lmexcelupload\r\n"
			+ "        WHERE \r\n"
			+ "            orgid = ?1\r\n"
			+ "            AND branch = ?2\r\n"
			+ "            AND branchcode = ?3\r\n"
			+ "            AND client = ?4\r\n"
			+ "        GROUP BY \r\n"
			+ "            entryno\r\n"
			+ "    ))\r\n"
			+ "GROUP BY \r\n"
			+ "    bin,\r\n"
			+ "    binclass,\r\n"
			+ "    celltype,\r\n"
			+ "    core,\r\n"
			+ "    expdate,\r\n"
			+ "    pckey,\r\n"
			+ "    partno,\r\n"
			+ "    partdesc,\r\n"
			+ "    sku,\r\n"
			+ "    grnno,\r\n"
			+ "    batch,\r\n"
			+ "    batchdate,\r\n"
			+ "    grndate,\r\n"
			+ "    bintype\r\n"
			+ "HAVING \r\n"
			+ "    SUM(sqty) > 0\r\n"
			+ "\r\n"
			+ "UNION\r\n"
			+ "\r\n"
			+ "SELECT  \r\n"
			+ "    b.frombin bin,\r\n"
			+ "    a.binclass binclass,\r\n"
			+ "    a.celltype celltype,\r\n"
			+ "    a.core core,\r\n"
			+ "    a.expdate expdate,\r\n"
			+ "    a.pckey pckey,\r\n"
			+ "    b.partno partno,\r\n"
			+ "    a.partdesc partdesc,\r\n"
			+ "    a.sku sku,\r\n"
			+ "    b.grnno grnno,\r\n"
			+ "    b.batchno batch,\r\n"
			+ "    a.batchdate batchdate,\r\n"
			+ "    a.grndate,\r\n"
			+ "    SUM(a.sqty) AS sqty,\r\n"
			+ "    b.frombintype bintype,\r\n"
			+ "    ROW_NUMBER() OVER (ORDER BY a.partdesc, a.partno) AS id\r\n"
			+ "FROM \r\n"
			+ "    stockdetails a\r\n"
			+ "JOIN \r\n"
			+ "    lmexcelupload b \r\n"
			+ "    ON a.orgid = b.orgid\r\n"
			+ "    AND a.partno = b.partno\r\n"
			+ "    AND a.grnno = b.grnno\r\n"
			+ "    AND a.batch = b.batchno\r\n"
			+ "    AND a.bin = b.frombin\r\n"
			+ "WHERE \r\n"
			+ "    a.orgid = ?1\r\n"
			+ "    AND a.branch = ?2\r\n"
			+ "    AND a.branchcode = ?3\r\n"
			+ "    AND a.client = ?4\r\n"
			+ "    AND a.status = 'R'\r\n"
			+ "    AND (a.partno, a.grnno, a.batch, a.bin) IN (\r\n"
			+ "        SELECT \r\n"
			+ "            partno, grnno, batchno, frombin \r\n"
			+ "        FROM \r\n"
			+ "            lmexcelupload \r\n"
			+ "        WHERE \r\n"
			+ "            orgid = ?1\r\n"
			+ "            AND branch = ?2\r\n"
			+ "            AND branchcode = ?3\r\n"
			+ "            AND client = ?4\r\n"
			+ "            AND entryno = ?5\r\n"
			+ "            AND ?5 NOT IN (\r\n"
			+ "                SELECT \r\n"
			+ "                    entryno \r\n"
			+ "                FROM \r\n"
			+ "                    locationmovement \r\n"
			+ "                WHERE \r\n"
			+ "                    orgid = ?1\r\n"
			+ "                    AND branch = ?2\r\n"
			+ "                    AND branchcode = ?3\r\n"
			+ "                    AND entryno IS NOT NULL\r\n"
			+ "                    AND client = ?4\r\n"
			+ "                GROUP BY entryno\r\n"
			+ "            )\r\n"
			+ "        GROUP BY partno, grnno, batchno, frombin\r\n"
			+ "    )\r\n"
			+ "GROUP BY \r\n"
			+ "    b.frombin,\r\n"
			+ "    a.binclass,\r\n"
			+ "    a.celltype,\r\n"
			+ "    a.core,\r\n"
			+ "    a.expdate,\r\n"
			+ "    a.pckey,\r\n"
			+ "    b.partno,\r\n"
			+ "    a.partdesc,\r\n"
			+ "    a.sku,\r\n"
			+ "    b.grnno,\r\n"
			+ "    b.batchno,\r\n"
			+ "    a.batchdate,\r\n"
			+ "    a.grndate,\r\n"
			+ "    b.frombintype\r\n")
	Set<Object[]> findAllForLocationMovementDetailsFillGrid(Long orgId, String branch, String branchCode,
			String client, String entryNo);

	@Query(nativeQuery = true, value = "select cast(a.sqty as unsigned)sqty from(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty)sqty from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 AND PARTNO=?6 and grnno=?7 and batch=?8 OR batch is NULL\r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            ) a")
	public int findAvlQtyFromStockForLocationMovement(Long orgId, String branch, String branchCode, String client, String bin,
			String partNo, String grnNo, String batchNo);

	@Query(nativeQuery = true, value = "select bin,bintype,binclass,celltype,core from wv_locationstatus where orgid=?1 and branch=?2 and branchcode=?3 and client =?4 and warehouse=?5 GROUP BY bin,bintype,binclass,celltype,core")
	Set<Object[]> findToBinFromLocationStatusForLocationMovement(Long orgId, String branch, String branchCode,
			String client, String warehouse);

	@Query(nativeQuery = true, value = "select a.batch,a.batchdate,a.expdate from(\r\n"
			+ "			(\r\n"
			+ "            select partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag,sum(sqty) from stockdetails \r\n"
			+ "			where orgid=?1 and branch=?2 and branchcode=?3 and client=?4 and bin=?5 AND PARTNO=?6 and grnno=?7 and status='R' \r\n"
			+ "			group by partno,partdesc,sku,grnno,grndate,batch,batchdate,expdate,bintype,binclass,celltype,core,bin,qcflag having sum(sqty)>0\r\n"
			+ "            )) a group by a.batch,a.batchdate,a.expdate")
	Set<Object[]> findBatchNoAndBatchDateFromStockForLocationMovement(Long orgId, String branch, String branchCode,
			String client, String bin, String partNo, String grnNo);

	

}
