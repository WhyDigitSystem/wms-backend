package com.whydigit.wms.service;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.ChangePasswordFormDTO;
import com.whydigit.wms.dto.LoginFormDTO;
import com.whydigit.wms.dto.RefreshTokenDTO;
import com.whydigit.wms.dto.ResetPasswordFormDTO;
import com.whydigit.wms.dto.SignUpFormDTO;
import com.whydigit.wms.dto.UserResponseDTO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest);

	public UserResponseDTO login(LoginFormDTO loginRequest);

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	

	

}
