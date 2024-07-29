package com.whydigit.wms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.UserVO;

@Service
public interface UserService {

	

	public void createUserAction(String userName, Long userId, String actionType);

	public void removeUser(String userName);
	
	

}
