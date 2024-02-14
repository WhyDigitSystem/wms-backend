package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.CountryVO;

public interface CountryRepository extends JpaRepository<CountryVO,Long>{

}
