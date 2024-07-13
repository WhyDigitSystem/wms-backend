package com.whydigit.wms.service;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.UserVO;

@Service
public interface UserService {

	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	public void createUserAction(String userName, Long userId, String actionType);

	public void removeUser(String userName);

}
