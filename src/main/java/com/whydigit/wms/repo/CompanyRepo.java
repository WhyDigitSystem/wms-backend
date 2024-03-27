package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CompanyVO;


@Repository
public interface CompanyRepo extends JpaRepository<CompanyVO, Long> {

}
