package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CountryVO;

public interface CountryRepository extends JpaRepository<CountryVO,Long>{

//	@Query("select a.id,a.countryname from CountryVO a where a.orgId=?1")
//	Set<Object[]> findCountryAndCountryid(Long orgId);

	@Query("select a from CountryVO a where orgId=?1")
	List<CountryVO> findAll(Long orgId);

	boolean existsByCountryNameAndCountryCodeAndOrgId(String countryName, String countryCode, Long orgId);

	boolean existsByCountryNameAndOrgId(String countryName, Long orgId);

	boolean existsByCountryCodeAndOrgId(String countryCode, Long orgId);

	

}
