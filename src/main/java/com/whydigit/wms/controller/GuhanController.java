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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.PickRequestDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.service.GuhanSerivce;

@RestController
@RequestMapping("/api/guhan")
public class GuhanController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);

	@Autowired
	GuhanSerivce guhanService;

//	PickRequest
	@GetMapping("/getAllPickRequestByOrgId")
	public ResponseEntity<ResponseDTO> getAllPickRequest(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllPickRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PickRequestVO> pickRequestVO = new ArrayList<>();
		try {
			pickRequestVO = guhanService.getAllPickRequest(orgId, finYear, branch, branchCode, client, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickRequest information get successfully ");
			responseObjectsMap.put("pickRequestVO", pickRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PickRequest information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPickRequestById")
	public ResponseEntity<ResponseDTO> getPickRequestById(@RequestParam(required = false) Long id) {
		String methodName = "getPickRequestById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PickRequestVO pickRequestVO = new PickRequestVO();

		try {
			pickRequestVO = guhanService.getPickRequestById(id);
			if (pickRequestVO != null) {
				responseObjectsMap.put("pickRequestVO", pickRequestVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" PickRequest information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " PickRequest information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " PickRequest information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePickRequest")
	public ResponseEntity<ResponseDTO> createUpdatePickRequest(@RequestBody @Valid PickRequestDTO pickRequestDTO) {
		String methodName = "createUpdatePickRequest()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> pickRequestVO = guhanService.createUpdatePickRequest(pickRequestDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, pickRequestVO.get("message"));
			responseObjectsMap.put("pickRequestVO", pickRequestVO.get("pickRequestVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
