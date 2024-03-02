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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.service.InwardTransactionService;

@RestController
@RequestMapping("/api")
public class InwardTransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionController.class);

	@Autowired
	InwardTransactionService inwardTransactionService;

	// Grn

	@GetMapping("/grn")
	public ResponseEntity<ResponseDTO> getAllGrn() {
		String methodName = "getAllGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = inwardTransactionService.getAllGrn();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn information get successfully");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/grn/{grnid}")
	public ResponseEntity<ResponseDTO> getGrnById(@PathVariable Long grnid) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GrnVO grnVO = null;
		try {
			grnVO = inwardTransactionService.getGrnById(grnid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn found by ID");
			responseObjectsMap.put("Grn", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Grn not found for ID: " + grnid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/grn")
	public ResponseEntity<ResponseDTO> createGrn(@RequestBody GrnVO grnVO) {
		String methodName = "createGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GrnVO createdGrnVO = inwardTransactionService.createGrn(grnVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn created successfully");
			responseObjectsMap.put("GrnVO", createdGrnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " Grn & GrnCode already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/grn")
	public ResponseEntity<ResponseDTO> updateGrn(@RequestBody GrnVO grnVO) {
		String methodName = "updateGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GrnVO updatedGrnVO = inwardTransactionService.updateGrn(grnVO).orElse(null);
			if (updatedGrnVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn updated successfully");
				responseObjectsMap.put("GrnVO", updatedGrnVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Grn not found for Grn ID: " + grnVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Grn update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn & GrnCode already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
