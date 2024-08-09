package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CodeConversionVO;

@Repository
public interface CodeConversionRepo extends JpaRepository<CodeConversionVO, Long>{

}
