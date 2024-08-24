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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.service.GatePassInService;
import com.whydigit.wms.service.WarehouseMasterService;

@RestController
@RequestMapping("/api/gatePassIn")
public class GatePassInController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(GatePassInController.class);

	@Autowired
	WarehouseMasterService warehouseMasterService;

	@Autowired
	GatePassInService gatePassInService;

	@GetMapping("/getAllModeOfShipment")
	public ResponseEntity<ResponseDTO> getAllModeOfShipment(@RequestParam Long orgId) {
		String methodName = "getAllModeOfShipment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> modOfShipments = new ArrayList<>();
		try {
			modOfShipments = warehouseMasterService.getAllModeOfShipment(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ModeOfShipment information get successfully");
			responseObjectsMap.put("modOfShipments", modOfShipments);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ModeOfShipment information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// GatePassIn

	@PutMapping("/createUpdateGatePassIn")
	public ResponseEntity<ResponseDTO> createUpdateGatePassIn(@RequestBody GatePassInDTO gatePassInDTO) {
		String methodName = "createUpdateGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdGatePassInVO = gatePassInService.createUpdateGatePassIn(gatePassInDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdGatePassInVO.get("message"));
			responseObjectsMap.put("GatePassInVO", createdGatePassInVO.get("gatePassInVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/gatePassIn")
	public ResponseEntity<ResponseDTO> getAllGatePassIn(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String finYear, @RequestParam String client) {
		String methodName = "getAllGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GatePassInVO> gatePassInVO = new ArrayList<>();
		try {
			gatePassInVO = gatePassInService.getAllGatePassIn(orgId, branchCode, finYear, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn information get successfully");
			responseObjectsMap.put("gatePassInVO", gatePassInVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/gatePassIn/{id}")
	public ResponseEntity<ResponseDTO> getGatePassInById(@PathVariable Long id) {
		String methodName = "getGatePassInById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GatePassInVO gatePassInVO = null;
		try {
			gatePassInVO = gatePassInService.getGatePassInById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn found by ID");
			responseObjectsMap.put("GatePassIn", gatePassInVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "GatePassIn not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGatePassInDocId")
	public ResponseEntity<ResponseDTO> getGatePassInDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getGatePassInDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = gatePassInService.getGatePassInDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrderDocId information retrieved successfully");
			responseObjectsMap.put("GatePassInDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve BuyerOrderDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
