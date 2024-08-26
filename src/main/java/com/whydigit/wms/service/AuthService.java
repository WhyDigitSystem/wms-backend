package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.ChangePasswordFormDTO;
import com.whydigit.wms.dto.LoginFormDTO;
import com.whydigit.wms.dto.RefreshTokenDTO;
import com.whydigit.wms.dto.ResetPasswordFormDTO;
import com.whydigit.wms.dto.ResponsibilityDTO;
import com.whydigit.wms.dto.RolesDTO;
import com.whydigit.wms.dto.SignUpFormDTO;
import com.whydigit.wms.dto.UserResponseDTO;
import com.whydigit.wms.entity.ResponsibilityVO;
import com.whydigit.wms.entity.RolesVO;
import com.whydigit.wms.entity.UserVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface AuthService {

	public void signup(SignUpFormDTO signUpRequest);

	public UserResponseDTO login(LoginFormDTO loginRequest);

	public void logout(String userName);

	public void changePassword(ChangePasswordFormDTO changePasswordRequest);

	public void resetPassword(ResetPasswordFormDTO resetPasswordRequest);

	public RefreshTokenDTO getRefreshToken(String userName, String tokenId) throws ApplicationException;
	
	List<Map<String, Object>> getResponsibilityForRolesByOrgId(Long orgId);
	
	Map<String, Object> createUpdateRoles(RolesDTO rolesDTO) throws ApplicationException;
	
	public List<RolesVO> getAllRoles(Long orgId);

	public List<RolesVO> getAllActiveRoles(Long orgId);
	
	RolesVO getRolesById(Long id) throws ApplicationException;
	
	Map<String, Object> createUpdateResponsibilities(ResponsibilityDTO responsibilityDTO) throws ApplicationException;
	
	public List<ResponsibilityVO> getAllResponsibility(Long orgId);

	public List<ResponsibilityVO> getAllActiveResponsibility(Long orgId);
	
	ResponsibilityVO getResponsibilityById(Long id) throws ApplicationException;
	
	List<UserVO>getAllUsersByOrgId(Long orgId);
	
	public UserVO getUserById(Long userId);

	public UserVO getUserByUserName(String userName);

	

}
