package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CodeConversionDetailsVO;

@Repository
public interface CodeConversionDetailsRepo extends JpaRepository<CodeConversionDetailsVO, Long>{

}
