package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;

public interface GrnDetailsRepo extends JpaRepository<GrnDetailsVO, Long> {

	List<GrnDetailsVO> findByGrnVO(GrnVO grnVO);

}
