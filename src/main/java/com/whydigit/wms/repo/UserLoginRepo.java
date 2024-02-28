package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.UserLoginVO;

@Repository
public interface UserLoginRepo extends JpaRepository<UserLoginVO, Long> {

	 Set<Object[]> findAllNameAndEmployeeCodeByCompany(String company);

}
