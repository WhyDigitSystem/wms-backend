package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.AuthConstant;
import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ChangePasswordFormDTO;
import com.whydigit.wms.dto.LoginFormDTO;
import com.whydigit.wms.dto.RefreshTokenDTO;
import com.whydigit.wms.dto.ResetPasswordFormDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.ResponsibilityDTO;
import com.whydigit.wms.dto.RolesDTO;
import com.whydigit.wms.dto.SignUpFormDTO;
import com.whydigit.wms.dto.UserResponseDTO;
import com.whydigit.wms.entity.ResponsibilityVO;
import com.whydigit.wms.entity.RolesVO;
import com.whydigit.wms.entity.ScreenNamesVO;
import com.whydigit.wms.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<ResponseDTO> signup(@Valid @RequestBody SignUpFormDTO signUpRequest) {
		String methodName = "signup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.signup(signUpRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, signUpRequest.getEmail(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.SIGNUP_REGISTERED_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.SIGNUP_REGISTERED_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginFormDTO loginRequest) {
		String methodName = "login()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UserResponseDTO userResponseDTO = null;
		try {
			userResponseDTO = authService.login(loginRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, loginRequest.getUserName(),
					errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGIN_SUCCESS_MESSAGE);
			responseObjectsMap.put(UserConstants.KEY_USER_VO, userResponseDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_LOGIN_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/logout")
	public ResponseEntity<ResponseDTO> logout(@RequestParam String userName) {
		String methodName = "logout()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.logout(userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.USER_LOGOUT_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.USER_LOGOUT_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/changePassword")
	public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody ChangePasswordFormDTO changePasswordRequest) {
		String methodName = "changePassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.changePassword(changePasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					changePasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.CHANGE_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.CHANGE_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordFormDTO resetPasswordRequest) {
		String methodName = "resetPassword()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			authService.resetPassword(resetPasswordRequest);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName,
					resetPasswordRequest.getUserName(), errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, UserConstants.RESET_PASSWORD_SUCCESS_MESSAGE);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, UserConstants.RESET_PASSWORD_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getRefreshToken")
	public ResponseEntity<ResponseDTO> getRefreshToken(@RequestParam String userName, @RequestParam String tokenId) {
		String methodName = "getRefreshToken()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();
		try {
			refreshTokenDTO = authService.getRefreshToken(userName, tokenId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME_WITH_USER_NAME, methodName, userName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, AuthConstant.REFRESH_TOKEN_SUCCESS_MESSAGE);
			responseObjectsMap.put(CommonConstant.REFRESH_TOKEN, refreshTokenDTO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, AuthConstant.REFRESH_TOKEN_FAILED_MESSAGE,
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateResponsibility")
	public ResponseEntity<ResponseDTO> createUpdateResponsibility(@RequestBody ResponsibilityDTO responsibilityDTO) {
		String methodName = "createUpdateResponsibility()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> responsibile = authService.createUpdateResponsibilities(responsibilityDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, responsibile.get("message") );
			responseObjectsMap.put("responsibilityVO", responsibile.get("responsibilityVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getResponsibilityForRolesByOrgId")
	public ResponseEntity<ResponseDTO> getResponsibilityForRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getResponsibilityForRolesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> responsibilityVO = new ArrayList<>();
		try {
			responsibilityVO = authService.getResponsibilityForRolesByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Responsibility Founded");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Responsibility not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Responsibility not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createUpdateRoles")
	public ResponseEntity<ResponseDTO> createUpdateRoles(@RequestBody RolesDTO rolesDTO) {
		String methodName = "createUpdateResponsibility()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> roles = authService.createUpdateRoles(rolesDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, roles.get("message") );
			responseObjectsMap.put("rolesVO", roles.get("rolesVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allRolesByOrgId")
	public ResponseEntity<ResponseDTO> getAllRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllScreenNames()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RolesVO> rolesVO = new ArrayList<>();
		try {
			rolesVO = authService.getAllRoles(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allActiveRolesByOrgId")
	public ResponseEntity<ResponseDTO> getAllActiveRolesByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllActiveRolesByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RolesVO> rolesVO = new ArrayList<>();
		try {
			rolesVO = authService.getAllActiveRoles(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/rolesById")
	public ResponseEntity<ResponseDTO> getRolesById(@RequestParam Long id) {
		String methodName = "getRolesById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RolesVO rolesVO = new RolesVO();
		try {
			rolesVO = authService.getRolesById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Roles information get successfully");
			responseObjectsMap.put("rolesVO", rolesVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Roles information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/allActiveResponsibilityByOrgId")
	public ResponseEntity<ResponseDTO> getAllActiveResponsibilityByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllActiveResponsibilityByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ResponsibilityVO> resposResponsibilityVO = new ArrayList<>();
		try {
			resposResponsibilityVO = authService.getAllActiveResponsibility(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("resposResponsibilityVO", resposResponsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/allResponsibilityByOrgId")
	public ResponseEntity<ResponseDTO> getAllResponsibilityByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllResponsibilityByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ResponsibilityVO> responsibilityVO = new ArrayList<>();
		try {
			responsibilityVO = authService.getAllResponsibility(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/responsibilityById")
	public ResponseEntity<ResponseDTO> getResponsibilityById(@RequestParam Long id) {
		String methodName = "getResponsibilityById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ResponsibilityVO responsibilityVO = new ResponsibilityVO();
		try {
			responsibilityVO = authService.getResponsibilityById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Responsibility information get successfully");
			responseObjectsMap.put("responsibilityVO", responsibilityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Responsibility information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
