package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.SupplierVO;

public interface SupplierRepo extends JpaRepository<SupplierVO, Long> {

	@Query("select a from SupplierVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL' or a.cbranch=?3)")
	List<SupplierVO> findAllByOrgIdAndClient(Long orgid, String client, String cbranch);

}
