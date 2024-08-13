package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PickRequestVO;
@Repository
public interface PickRequestRepo extends JpaRepository<PickRequestVO, Long>{

}
