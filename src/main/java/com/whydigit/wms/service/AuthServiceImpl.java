package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.wms.common.AuthConstant;
import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ChangePasswordFormDTO;
import com.whydigit.wms.dto.LoginFormDTO;
import com.whydigit.wms.dto.RefreshTokenDTO;
import com.whydigit.wms.dto.ResetPasswordFormDTO;
import com.whydigit.wms.dto.SignUpFormDTO;
import com.whydigit.wms.dto.UserLoginBranchAccessDTO;
import com.whydigit.wms.dto.UserLoginClientAccessDTO;
import com.whydigit.wms.dto.UserLoginRoleAccessDTO;
import com.whydigit.wms.dto.UserResponseDTO;
import com.whydigit.wms.entity.TokenVO;
import com.whydigit.wms.entity.UserLoginBranchAccessibleVO;
import com.whydigit.wms.entity.UserLoginClientAccessVO;
import com.whydigit.wms.entity.UserLoginRolesVO;
import com.whydigit.wms.entity.UserVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.TokenRepo;
import com.whydigit.wms.repo.UserActionRepo;
import com.whydigit.wms.repo.UserRepo;
import com.whydigit.wms.security.TokenProvider;
import com.whydigit.wms.util.CryptoUtils;

@Service
public class AuthServiceImpl implements AuthService {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserActionRepo userActionRepo;
	
	@Autowired
	ClientRepo clientRepo;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	TokenRepo tokenRepo;

	@Autowired
	UserService userService;

	@Override
	public void signup(SignUpFormDTO signUpRequest) {
		String methodName = "signup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(signUpRequest) || StringUtils.isBlank(signUpRequest.getEmail())
				|| StringUtils.isBlank(signUpRequest.getUserName())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_REGISTER_INFORMATION);
		} else if (userRepo.existsByUserNameOrEmail(signUpRequest.getUserName(), signUpRequest.getEmail())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_ALREADY_REGISTERED);
		}
		UserVO userVO = getUserVOFromSignUpFormDTO(signUpRequest);
		userRepo.save(userVO);
		userService.createUserAction(userVO.getUserName(), userVO.getUsersId(), UserConstants.USER_ACTION_ADD_ACCOUNT);
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}
	
private UserVO getUserVOFromSignUpFormDTO(SignUpFormDTO signUpFormDTO){
		
		UserVO userVO = new UserVO();
        userVO.setUserName(signUpFormDTO.getUserName());
        try {
        	  userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt( signUpFormDTO.getPassword())));
        }catch (Exception e) {
        	LOGGER.error(e.getMessage());
        	throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
        userVO.setEmployeeName(signUpFormDTO.getEmployeeName());
        userVO.setNickName(signUpFormDTO.getNickName());
        userVO.setEmail(signUpFormDTO.getEmail());
        userVO.setMobileNo(signUpFormDTO.getMobileNo());
        userVO.setUserType(signUpFormDTO.getUserType());
        userVO.setIsActive(signUpFormDTO.getIsActive());
        
        List<UserLoginRolesVO>rolesVO=new ArrayList<>();
        if(signUpFormDTO.getRoleAccessDTO()!=null)
        {
        	for(UserLoginRoleAccessDTO accessDTO:signUpFormDTO.getRoleAccessDTO()) 
        	{
        		UserLoginRolesVO loginRolesVO=new UserLoginRolesVO();
        		loginRolesVO.setRole(accessDTO.getRole());
        		loginRolesVO.setStartdate(accessDTO.getStartdate());
        		loginRolesVO.setEnddate(accessDTO.getEnddate());
        		rolesVO.add(loginRolesVO);
        	}
        }
        
        userVO.setRoleAccessVO(rolesVO);
        
        List<UserLoginClientAccessVO> clientAccessVOList = new ArrayList<>();
        if (signUpFormDTO.getClientAccessDTOList() != null) 
        {
            for (UserLoginClientAccessDTO clientAccessDTO : signUpFormDTO.getClientAccessDTOList())
            {
                UserLoginClientAccessVO clientAccessVO = new UserLoginClientAccessVO();
                clientAccessVO.setClient(clientAccessDTO.getClient());
                clientAccessVO.setCustomer(clientAccessDTO.getCustomer());
                clientAccessVO.setUserVO(userVO);
                clientAccessVOList.add(clientAccessVO);
            }	
        }
        userVO.setClientAccessVO(clientAccessVOList);
        
        List<UserLoginBranchAccessibleVO>branchAccessList=new ArrayList<>();
        if (signUpFormDTO.getBranchAccessDTOList() != null) {
            for (UserLoginBranchAccessDTO userLoginBranchAccessDTO : signUpFormDTO.getBranchAccessDTOList()) {
                UserLoginBranchAccessibleVO branchAccessibleVO = new UserLoginBranchAccessibleVO();
                branchAccessibleVO.setBranch(userLoginBranchAccessDTO.getBranch());
                branchAccessibleVO.setBranchcode(userLoginBranchAccessDTO.getBranchcode());
                branchAccessibleVO.setUserVO(userVO);
                branchAccessList.add(branchAccessibleVO);
            }
        }
        userVO.setBranchAccessibleVO(branchAccessList);

        return userVO;
    }

	@Override
	public UserResponseDTO login(LoginFormDTO loginRequest) {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(loginRequest) || StringUtils.isBlank(loginRequest.getUserName())
				|| StringUtils.isBlank(loginRequest.getPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGIN_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(loginRequest.getUserName());
		
		if (ObjectUtils.isNotEmpty(userVO)) {
			if (compareEncodedPasswordWithEncryptedPassword(loginRequest.getPassword(), userVO.getPassword())) {
				updateUserLoginInformation(userVO);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(
					UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND_AND_ASKING_SIGNUP);
		}
		UserResponseDTO userResponseDTO = mapUserVOToDTO(userVO);
		TokenVO tokenVO = tokenProvider.createToken(userVO.getUsersId(), loginRequest.getUserName());
		userResponseDTO.setToken(tokenVO.getToken());
		userResponseDTO.setTokenId(tokenVO.getId());
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return userResponseDTO;
	}
	
	/**
	 * @param encryptedPassword -> Data from user;
	 * @param encodedPassword   ->Data from DB;
	 * @return
	 */
	private boolean compareEncodedPasswordWithEncryptedPassword(String encryptedPassword, String encodedPassword) {
		boolean userStatus = false;
		try {
			userStatus = encoder.matches(CryptoUtils.getDecrypt(encryptedPassword), encodedPassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
		}
		return userStatus;
	}

	@Override
	public void logout(String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (StringUtils.isBlank(userName)) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_USER_LOGOUT_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(userName);
		if (ObjectUtils.isNotEmpty(userVO)) {
			updateUserLogOutInformation(userVO);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void changePassword(ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(changePasswordRequest) || StringUtils.isBlank(changePasswordRequest.getUserName())
				|| StringUtils.isBlank(changePasswordRequest.getOldPassword())
				|| StringUtils.isBlank(changePasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_CHANGE_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(changePasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			if (compareEncodedPasswordWithEncryptedPassword(changePasswordRequest.getOldPassword(),
					userVO.getPassword())) {
				try {
					userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(changePasswordRequest.getNewPassword())));
				} catch (Exception e) {
					throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
				}
				userRepo.save(userVO);
				userService.createUserAction(userVO.getUserName(), userVO.getUsersId(),
						UserConstants.USER_ACTION_TYPE_CHANGE_PASSWORD);
			} else {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_OLD_PASSWORD_MISMATCH);
			}
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		if (ObjectUtils.isEmpty(resetPasswordRequest) || StringUtils.isBlank(resetPasswordRequest.getUserName())
				|| StringUtils.isBlank(resetPasswordRequest.getNewPassword())) {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_INVALID_RESET_PASSWORD_INFORMATION);
		}
		UserVO userVO = userRepo.findByUserName(resetPasswordRequest.getUserName());
		if (ObjectUtils.isNotEmpty(userVO)) {
			try {
				userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(resetPasswordRequest.getNewPassword())));
			} catch (Exception e) {
				throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
			}
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getUsersId(),
					UserConstants.USER_ACTION_TYPE_RESET_PASSWORD);
		} else {
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	}

	@Override
	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException {
		UserVO userVO = userRepo.findByUserName(userName);
		RefreshTokenDTO refreshTokenDTO = null;
		if (ObjectUtils.isEmpty(userVO)) {
			throw new ApplicationException(UserConstants.ERRROR_MSG_USER_INFORMATION_NOT_FOUND);
		}
		TokenVO tokenVO = tokenRepo.findById(tokenId).orElseThrow(() -> new ApplicationException("Invalid Token Id."));
		if (tokenVO.getExpDate().compareTo(new Date()) > 0) {
			tokenVO = tokenProvider.createRefreshToken(tokenVO, userVO);
			refreshTokenDTO = RefreshTokenDTO.builder().token(tokenVO.getToken()).tokenId(tokenVO.getId()).build();
		} else {
			tokenRepo.delete(tokenVO);
			throw new ApplicationException(AuthConstant.REFRESH_TOKEN_EXPIRED_MESSAGE);
		}
		return refreshTokenDTO;
	}

//	private UserVO getUserVOFromSignUpFormDTO(SignUpFormDTO signUpRequest) {
//		UserVO userVO = new UserVO();
//		userVO.setUserName(signUpRequest.getUserName());
//		userVO.setEmail(signUpRequest.getEmail());
//		try {
//			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(signUpRequest.getPassword())));
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage());
//			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_ENCODE_USER_PASSWORD);
//		}
//		userVO.setRole(Role.ROLE_USER);
//		userVO.setEmployeeName(signUpRequest.getEmployeeName());
//		userVO.setUserType(signUpRequest.getUserType());
//		userVO.setNickName(signUpRequest.getNickName());
//		userVO.setMobileNo(signUpRequest.getMobileNo());
//		userVO.setActive(true);
//		return userVO;
//	}
	
	
	

	
	

	

	/**
	 * @param userVO
	 */
	private void updateUserLoginInformation(UserVO userVO) {
		try {
			userVO.setLoginStatus(true);
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getUsersId(),
					UserConstants.USER_ACTION_TYPE_LOGIN);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	private void updateUserLogOutInformation(UserVO userVO) {
		try {
			userVO.setLoginStatus(false);
			userRepo.save(userVO);
			userService.createUserAction(userVO.getUserName(), userVO.getUsersId(),
					UserConstants.USER_ACTION_TYPE_LOGOUT);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException(UserConstants.ERRROR_MSG_UNABLE_TO_UPDATE_USER_INFORMATION);
		}
	}

	public static UserResponseDTO mapUserVOToDTO(UserVO userVO) {
		UserResponseDTO userDTO = new UserResponseDTO();
		userDTO.setUsersId(userVO.getUsersId());
		userDTO.setBranch(userVO.getBranch());
		userDTO.setEmployeeName(userVO.getEmployeeName());
		userDTO.setCustomer(userVO.getCustomer());
		userDTO.setClient(userVO.getClient());
		userDTO.setOrgId(userVO.getOrgId());
		userDTO.setWarehouse(userVO.getWarehouse());
		userDTO.setUserType(userVO.getUserType());
		userDTO.setEmail(userVO.getEmail());
		userDTO.setUserName(userVO.getUserName());
		userDTO.setLoginStatus(userVO.isLoginStatus());
		userDTO.setIsActive(userVO.getIsActive());
		userDTO.setRole(userVO.getRole());
		userDTO.setCommonDate(userVO.getCommonDate());
		userDTO.setAccountRemovedDate(userVO.getAccountRemovedDate());
		return userDTO;
	}

	
}
