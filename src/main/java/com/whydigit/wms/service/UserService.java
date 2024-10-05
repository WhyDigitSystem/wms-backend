package com.whydigit.wms.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

	

	public void createUserAction(String userName, Long userId, String actionType);
	
	public void createUserLoginAction(String userName, Long userId, String actionType, HttpServletRequest httpRequest);

	public void removeUser(String userName);
	
	

}
