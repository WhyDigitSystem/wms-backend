package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.HandlingStockaOutVO;

public interface HandlingStockOutRepo extends JpaRepository<HandlingStockaOutVO, Long> {

}
