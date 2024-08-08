package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.HandlingStockInVO;

public interface HandlingStockInRepo extends JpaRepository<HandlingStockInVO, Long> {

}
