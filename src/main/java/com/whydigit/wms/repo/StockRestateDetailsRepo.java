package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StockRestateDetailsVO;

@Repository
public interface StockRestateDetailsRepo extends JpaRepository<StockRestateDetailsVO, Long>{

}