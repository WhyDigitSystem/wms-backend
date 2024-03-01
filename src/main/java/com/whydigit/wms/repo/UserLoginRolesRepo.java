package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.UserLoginRolesVO;

public interface UserLoginRolesRepo extends JpaRepository<UserLoginRolesVO, Long> {

}
