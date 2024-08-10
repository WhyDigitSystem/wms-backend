package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.KittingVO;

@Repository
public interface KittingRepo extends JpaRepository<KittingVO, Long>{

}
