package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.ScreenNamesVO;

public interface ScreenNamesRepo extends JpaRepository<ScreenNamesVO, Long> {

	boolean existsByScreenName(String screenName);

	boolean existsByScreenCode(String screenCode);

}
