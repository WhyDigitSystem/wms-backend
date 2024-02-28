package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.UserLoginBranchAccessibleVO;

public interface UserBranchAccessRepo extends JpaRepository<UserLoginBranchAccessibleVO, Long> {

}
