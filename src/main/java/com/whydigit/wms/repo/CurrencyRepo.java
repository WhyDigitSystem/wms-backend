package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CurrencyVO;


@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyVO, Long> {
	

}

