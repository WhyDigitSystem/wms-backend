package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.CityVO;

	public interface CityRepo extends JpaRepository<CityVO, Long> {
		

	}


