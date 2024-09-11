package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PutawayExcelUploadVO;

@Repository
public interface PutawayExcelUploadRepo extends JpaRepository<PutawayExcelUploadVO, Long> {

	boolean existsByGrnNoAndOrgId(String grnNo, Long orgId);

}
