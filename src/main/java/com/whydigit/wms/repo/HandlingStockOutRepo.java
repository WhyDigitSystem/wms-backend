package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.HandlingStockOutVO;

public interface HandlingStockOutRepo extends JpaRepository<HandlingStockOutVO, Long> {

}
