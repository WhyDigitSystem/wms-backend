package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.GlobalParameterVO;

public interface GlobalParameterRepo extends JpaRepository<GlobalParameterVO, Long> {

}
