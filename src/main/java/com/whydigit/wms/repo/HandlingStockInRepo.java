package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.HandlingStockInVO;

public interface HandlingStockInRepo extends JpaRepository<HandlingStockInVO, Long> {

	List<HandlingStockInVO> findBySdocid(String docId);

}
