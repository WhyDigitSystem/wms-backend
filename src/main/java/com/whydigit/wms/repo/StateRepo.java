package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StateVO;


@Repository
public interface StateRepo extends JpaRepository<StateVO, Long> {
	
}
