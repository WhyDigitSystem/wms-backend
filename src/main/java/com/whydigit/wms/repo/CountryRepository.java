package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CountryVO;

public interface CountryRepository extends JpaRepository<CountryVO,Long>{

	@Query("select a.id,a.country from CountryVO a where a.orgid=?1")
	Set<Object[]> findCountryAndCountryid(Long orgid);

	@Query("select a from CountryVO a where orgid=?1")
	List<CountryVO> findAll(Long orgid);

	

}
