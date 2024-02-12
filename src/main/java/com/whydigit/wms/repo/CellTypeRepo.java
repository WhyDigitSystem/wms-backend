package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CellTypeVO;

@Repository
public interface CellTypeRepo extends JpaRepository<CellTypeVO, Long>{

}
