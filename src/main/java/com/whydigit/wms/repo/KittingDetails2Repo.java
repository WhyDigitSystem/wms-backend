package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.KittingDetails2VO;
@Repository
public interface KittingDetails2Repo extends JpaRepository<KittingDetails2VO, Long>{

}
