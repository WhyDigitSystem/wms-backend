package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.LocationMappingVO;

public interface LocationMappingRepo extends JpaRepository<LocationMappingVO, Long>{
	
	@Query("select a from LocationMappingVO a where a.orgId=?1 and a.client=?2 and a.branch=?3 and a.warehouse=?4")
	List<LocationMappingVO> findAll(Long orgid, String client, String branch, String warehouse);

	boolean existsByWarehouseAndOrgId(String warehouse, Long long1);

	boolean existsByRowNoAndOrgId(String rowNo, Long orgId);

	boolean existsByLevelNoAndOrgId(String levelNo, Long orgId);

	@Query(nativeQuery = true, value = "select bin,binclass,celltype,core,bintype from wv_locationstatus where orgid=?1 and branchcode=?2 and client=?3 and warehouse=?4 ")
	Set<Object[]> getToBinDetailsVasPutaway(Long orgId, String branchCode, String client, String warehouse);
	

}
