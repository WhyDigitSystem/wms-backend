package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GrnVO;

public interface GrnRepo extends JpaRepository<GrnVO, Long> {

	@Query(value = "SELECT g.entryno,g.docid,g.docdate,g.suppliershortname,g.supplier,g.modeofshipment,g.carrier,g.vehicletype,g.contact,g.drivername,g.securityname,g.goodsdescription,g.vehicleno FROM GatePassInVO g WHERE g.orgId = ?1 AND g.client = ?2 AND g.customer = ?3 AND g.branchcode = ?4 ")
	Set<Object[]> findAllGatePassNumberByClientAndBranch(Long orgId, String client, String customer, String branchcode);

}
