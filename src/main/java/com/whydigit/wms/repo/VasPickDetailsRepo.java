package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPickDetailsVO;
import com.whydigit.wms.entity.VasPickVO;
@Repository
public interface VasPickDetailsRepo extends JpaRepository<VasPickDetailsVO, Long>{

	List<VasPickDetailsVO> findByVasPickVO(VasPickVO vasPickVO);

	
}
