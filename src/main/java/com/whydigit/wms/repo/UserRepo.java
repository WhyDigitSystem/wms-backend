package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.UserVO;

public interface UserRepo extends JpaRepository<UserVO, Long> {

	boolean existsByUsernameOrEmail(String username, String email);

	@Query("select a from UserVO a where a.username=?1")
	UserVO findByUsername(String username);

	@Query(value = "select u from UserVO u where u.usersid =?1")
	UserVO getUserById(Long usersid);

//	UserVO findByUserNameAndUsersId(String userName, Long usersId);



	
	


}