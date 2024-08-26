package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.ReversePickDTO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.entity.ReversePickVO;
import com.whydigit.wms.service.ReversePickService;

@RestController
@RequestMapping("/api/reversePick")
public class ReversePickController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(ReversePickController.class);

	@Autowired
	ReversePickService reversePickService;

	// REVERSEPICK

	@PutMapping("/createUpdateReversePick")
	public ResponseEntity<ResponseDTO> createUpdateReversePick(@RequestBody ReversePickDTO reversePickDTO) {
		String methodName = "createUpdateReversePick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> reversePick = reversePickService.createUpdateReversePick(reversePickDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, reversePick.get("message"));
			responseObjectsMap.put("reversePickVO", reversePick.get("reversePickVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {

			String errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getReversePickDocId")
	public ResponseEntity<ResponseDTO> getReversePickDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getReversePickDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = reversePickService.getReversePickDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"reverse pick docId information retrieved successfully");
			responseObjectsMap.put("reversePickDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "reverse pick docId information get failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getAllReversePick")
	public ResponseEntity<ResponseDTO> getAllReversePick(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String client, @RequestParam(required = true) String branch,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String finYear,
			@RequestParam(required = true) String warehouse) {
		String methodName = "getAllReversePick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ReversePickVO> reversePickVO = new ArrayList<ReversePickVO>();
		try {
			reversePickVO = reversePickService.getAllReversePick(orgId, client, branch, branchCode, finYear, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ReversePick Details information get successfully");
			responseObjectsMap.put("reversePickVO", reversePickVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"ReversePick  Details information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getReversePickById")
	public ResponseEntity<ResponseDTO> getReversePickById(@RequestParam(required = false) Long id) {
		String methodName = "getReversePickById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		ReversePickVO reversePickVO = new ReversePickVO();

		try {
			reversePickVO = reversePickService.getReversePickById(id);
			if (reversePickVO != null) {
				responseObjectsMap.put("reversePickVO", reversePickVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" ReversePick information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " ReversePick information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " ReversePick information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getPickRequestDetailsForReversePick")
	public ResponseEntity<ResponseDTO> getPickRequestDetailsForReversePick(@RequestParam(required = true) Long orgId,
			@RequestParam String finYear, @RequestParam String branch, @RequestParam String branchCode,
			@RequestParam String client) {
		String methodName = "getPickRequestDetailsForReversePick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PickRequestVO> pickRequestVO = new ArrayList<PickRequestVO>();
		try {
			pickRequestVO = reversePickService.getPickRequestDetailsForReversePick(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pick Request Details information get successfully");
			responseObjectsMap.put("pickRequestVO", pickRequestVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Pick Request Details information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getFillGridDetailsForReversePick")
	public ResponseEntity<ResponseDTO> getFillGridDetailsForReversePick(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			@RequestParam String pickRequestDocId) {
		String methodName = "getFillGridDetailsForReversePick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fillGridDetails = new ArrayList<>();
		try {
			fillGridDetails = reversePickService.getPickRequestFillDetailsForReversePick(orgId, branchCode, client, pickRequestDocId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"fill Grid Details  from Stock information retrieved successfully");
			responseObjectsMap.put("fillGridDetails", fillGridDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve fill Grid Details  from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
