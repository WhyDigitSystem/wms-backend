package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.UserLoginClientAccessVO;

public interface UserClientAccessRepo extends JpaRepository<UserLoginClientAccessVO,Long> {

}
