package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.ResponsibilityVO;
import com.whydigit.wms.entity.ScreensVO;

public interface ScreensRepo extends JpaRepository<ScreensVO, Long>{

	List<ScreensVO> findByResponsibilityVO(ResponsibilityVO responsibilityVO);

}
