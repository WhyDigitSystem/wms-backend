package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationVO;

public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Long> {

	
//	@Query(nativeQuery = true,value="select * from WarehouseLocationVO where company=?1 ")
	List<WarehouseLocationVO> findAllByCompany(String company);

	@Query("select e.locationtype from WarehouseLocationVO e where e.company=?1 and e.warehouse=?2")
	Set<Object> findAllLocationTypeByCompanyAndWarehouse(String company, String warehouse);

	@Query("select e.rowno from WarehouseLocationVO e where e.company=?1 and e.warehouse=?2 and e.locationtype=?3")
	Set<Object> findAllRownoByCompanyAndWarehouseAndLocationType(String company, String warehouse, String locationtype);
	
	@Query("select e.level from WarehouseLocationVO e where e.company=?1 and e.warehouse=?2 and e.locationtype=?3 and e.rowno=?4")
	Set<Object> findAllLevelByCompanyAndWarehouseAndLocationTypeAndRowno(String company, String warehouse,String locationtype, String rowno);

}
