package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CityVO;

	public interface CityRepo extends JpaRepository<CityVO, Long> {
		@Query(value = "select c from CityVO c where c.userid=?1")
		List<CityVO> getCityByUserId(long userid);

	}


