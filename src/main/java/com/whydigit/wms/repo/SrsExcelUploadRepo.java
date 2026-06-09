package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.SrsExcelUploadVO;
@Repository
public interface SrsExcelUploadRepo extends JpaRepository<SrsExcelUploadVO, Long>{

}
