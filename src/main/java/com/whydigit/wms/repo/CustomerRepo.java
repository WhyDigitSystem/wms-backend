package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CustomerVO;
@Repository
public interface CustomerRepo extends JpaRepository<CustomerVO, Long>{

}
