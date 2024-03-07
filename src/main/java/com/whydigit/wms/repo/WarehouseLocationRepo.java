package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationVO;

public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Long> {

	
	@Query("select a from WarehouseLocationVO a where orgId=?1 and a.warehouse=?2 and a.branch=?3")
	List<WarehouseLocationVO> findAll(Long orgid, String warehouse, String branch);

	@Query("select e.locationtype from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2")
	Set<Object> findAllLocationTypeByOrgIdAndWarehouse(Long orgid, String warehouse);

	@Query("select e.rowno from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2 and e.locationtype=?3")
	Set<Object> findAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse, String locationtype);
	
	@Query("select e.level from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2 and e.locationtype=?3 and e.rowno=?4")
	Set<Object> findAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,String locationtype, String rowno);

	

	

}
