package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CurrencyVO;


@Repository
public interface CurrencyRepo extends JpaRepository<CurrencyVO, Long> {

	@Query("select a from CurrencyVO a where a.orgId=?1")
	List<CurrencyVO> findAll(Long orgid);
}