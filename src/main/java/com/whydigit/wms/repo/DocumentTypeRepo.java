package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.DocumentTypeVO;

public interface DocumentTypeRepo extends JpaRepository<DocumentTypeVO, Long> {

	boolean existsByOrgIdAndScreenCode(Long orgId, String screenCode);

	boolean existsByOrgIdAndDocCode(Long orgId, String docCode);

}
