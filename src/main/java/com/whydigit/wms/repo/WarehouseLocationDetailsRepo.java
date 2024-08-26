package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationDetailsVO;

public interface WarehouseLocationDetailsRepo extends JpaRepository<WarehouseLocationDetailsVO, Long> {

	@Query(nativeQuery = true,value = "select ROW_NUMBER() OVER () AS id, e.rowno,e.level,e.bin,e.core,e.status from warehouselocationdetails e where e.orgid=?1 and e.warehouse=?2 and e.bintype=?3 and e.rowno=?4 and e.level=?5 group by e.rowno,e.level,e.bin,e.core,e.status")
	Set<Object[]> findAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(Long orgid, String warehouse,
			String locationtype, String rowno, String level);

	boolean existsByBinAndOrgIdAndBranchCodeAndWarehouse(String bin, Long orgId, String branchCode, String warehouse);

}
