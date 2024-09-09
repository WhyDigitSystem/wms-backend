package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BoExcelUploadVO;
@Repository
public interface BoExcelUploadRepo extends JpaRepository<BoExcelUploadVO, Long>{

	boolean existsByOrderNoAndOrgId(Integer orderNo, Long orgId);

}
