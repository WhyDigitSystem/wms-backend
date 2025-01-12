package com.whydigit.wms.service;

import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.entity.UserActionVO;
import com.whydigit.wms.entity.UserVO;
import com.whydigit.wms.repo.TokenRepo;
import com.whydigit.wms.repo.UserActionRepo;
import com.whydigit.wms.repo.UserRepo;
import com.whydigit.wms.security.TokenProvider;

@Service
public class UserServiceImpl implements UserService {
	public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserActionRepo userActionRepo;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	TokenRepo tokenRepo;

	public void createUserAction(String userName, long userId, String actionType) {
		try {
			UserActionVO userActionVO = new UserActionVO();
			userActionVO.setUserName(userName);
			userActionVO.setUserId(userId);
			userActionVO.setActionType(actionType);
			userActionRepo.save(userActionVO);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public UserVO getUserById(Long userId) {
		String methodName = "getUserById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(userId)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_ID);
		}
		UserVO userVO = userRepo.getUserById(userId);
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userVO;
	}

	@Override
	public UserVO getUserByUserName(String userName) {
		String methodName = "getUserByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isNotEmpty(userName)) {
			UserVO userVO = userRepo.findByUserName(userName);
			if (ObjectUtils.isEmpty(userVO)) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return userVO;
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_NAME);
		}
	}

	@Override
	public void removeUser(String userName) {
		String methodName = "removeUser()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isNotEmpty(userName)) {
			UserVO userVO = userRepo.findByUserName(userName);
			if (ObjectUtils.isEmpty(userVO)) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
			}
			userVO.setActive(false);
			userVO.setAccountRemovedDate(new Date());
			userRepo.save(userVO);
			createUserAction(userVO.getUserName(), userVO.getUserId(), UserConstants.USER_ACTION_REMOVE_ACCOUNT);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_NAME);
		}
	}

}
