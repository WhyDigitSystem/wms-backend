package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationDetailsVO;

public interface WarehouseLocationDetailsRepo extends JpaRepository<WarehouseLocationDetailsVO, Long> {

	@Query("select e.rowno,e.level,e.bin from WarehouseLocationDetailsVO e where e.company=?1 and e.warehouse=?2 and e.locationtype=?3 and e.rowno=?4 and e.level=?5")
	Set<Object[]> findAllBinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(String company, String warehouse,
			String locationtype, String rowno, String level);

}
