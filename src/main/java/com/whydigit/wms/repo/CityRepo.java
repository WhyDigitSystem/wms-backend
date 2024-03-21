package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CityVO;

	public interface CityRepo extends JpaRepository<CityVO, Long> {

		@Query("select a from CityVO a where a.orgid=?1")
		List<CityVO> findAll(Long orgid);

		@Query("select a from CityVO a where a.orgid=?1 and a.state=?2")
		List<CityVO> findAll(Long orgid, String state);
		

	}


