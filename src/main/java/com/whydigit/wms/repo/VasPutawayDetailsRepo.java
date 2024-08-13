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

	@Query(nativeQuery = true,value ="select a.partno,a.partdescrrption,a.grnno,a.picqty,a.bin,a.sku from vaspickdetails a,vaspick b where a.vaspicid=b.vaspicid and b.orgid=?1 and b.branch=?2 and b.client=?3 and b.docid=?4")
	Set<Object[]> getAllDetailsFromVasPickDetailsForVasPutawayDetails(Long orgId, String branch, String client,
			String docId);

	@Query(nativeQuery = true,value="SELECT bin,binclass,celltype,clientcode,core,expdate,pckey,ssku,stockdate,partno,partdescrrption,sku,grnno,grndate,picqty,avlqty from vaspickdetails a , vaspick b where  a.vaspicid=b.vaspicid  and b.orgid=?1 and b.branch=?2 AND b.branchcode=?3 and b.client=?4 ")
	Set<Object[]> getAllFillGridFromVasPutaway(Long orgId, String branch, String branchCode, String client);

}
