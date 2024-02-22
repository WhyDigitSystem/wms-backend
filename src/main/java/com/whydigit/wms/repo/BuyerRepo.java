package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.BuyerVO;

public interface BuyerRepo extends JpaRepository<BuyerVO, Long> {

}
