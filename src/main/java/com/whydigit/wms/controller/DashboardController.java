package com.whydigit.wms.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.service.DashboardService;

@RestController
@RequestMapping("/api/dashboardController")
public class DashboardController extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterController.class);
	
	@Autowired
	DashboardService dashboardService;

	
	@GetMapping("/getStockLowVolume")
	public ResponseEntity<ResponseDTO> getStockLowVolume(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse) {
		String methodName = "getStockLowVolume()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> putawayDashboard = new ArrayList<>();
		try {
			putawayDashboard = dashboardService.getStockLowVolume(orgId, branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock LowVolume information get successfully");
			responseObjectsMap.put("putawayDashboard", putawayDashboard);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock LowVolume information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPutAwayOrderPerDay")
	public ResponseEntity<ResponseDTO> getPutAwayOrderPerDay(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse) {
		String methodName = "getPutAwayOrderPerDay()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> putAwayOrderPerDay = new ArrayList<>();
		try {
			putAwayOrderPerDay = dashboardService.getPutAwayOrderPerDay(orgId, branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway OrderPerDay information get successfully");
			responseObjectsMap.put("putAwayOrderPerDay", putAwayOrderPerDay);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway OrderPerDay information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPickRequestOrderPerDay")
	public ResponseEntity<ResponseDTO> getPickRequestOrderPerDay(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse) {
		String methodName = "getPickRequestOrderPerDay()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> pickRequestOrderPerDay = new ArrayList<>();
		try {
			pickRequestOrderPerDay = dashboardService.getPickRequestOrderPerDay(orgId, branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PickRequest OrderPerDay information get successfully");
			responseObjectsMap.put("pickRequestOrderPerDay", pickRequestOrderPerDay);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PickRequest OrderPerDay information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBinDetails")
	public ResponseEntity<ResponseDTO> getBinDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse,@RequestParam String bin) {
		String methodName = "getBinDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> binDetails = new ArrayList<>();
		try {
			binDetails = dashboardService.getBinDetails(orgId, branchCode, client,warehouse,bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "binDetails information get successfully");
			responseObjectsMap.put("binDetails", binDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "binDetails  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBinDetailsForClientWise")
	public ResponseEntity<ResponseDTO> getBinDetailsForClientWise(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse) {
		String methodName = "getBinDetailsForClientWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> binDetails = new ArrayList<>();
		try {
			binDetails = dashboardService.getBinDetailsForClientWise(orgId, branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BinDetails For ClientWise information get successfully");
			responseObjectsMap.put("binDetails", binDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "BinDetails For ClientWise  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getStorageDetails")
	public ResponseEntity<ResponseDTO> getStorageDetails(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse) {
		String methodName = "getStorageDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> storageDetails = new ArrayList<>();
		try {
			storageDetails = dashboardService.getStorageDetails(orgId, branchCode,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Storage information get successfully");
			responseObjectsMap.put("storageDetails", storageDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Storage  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getInBoundOrderPerMonth")
	public ResponseEntity<ResponseDTO> getGrnOrderDetailsPerMonth(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse,@RequestParam String client,
			@RequestParam int finYear) {
		String methodName = "getGrnOrderDetailsPerMonth()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnVo = new ArrayList<>();
		try {
			grnVo = dashboardService.getGrnOrderDetails(orgId, branchCode,warehouse,client,finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Inbound order month wise information get successfully");
			responseObjectsMap.put("grnVo", grnVo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Inbound order month wise  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getInBoundOrderPerYear")
	public ResponseEntity<ResponseDTO> getGrnOrderDetailsYear(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse,@RequestParam String client,@RequestParam int finYear) {
		String methodName = "getGrnOrderDetailsPerMonth()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnVo = new ArrayList<>();
		try {
			grnVo = dashboardService.getGrnOrderDetailsYear(orgId, branchCode,warehouse,client,finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "InBound order year wise information get successfully");
			responseObjectsMap.put("grnVo", grnVo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "InBound order year wise  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getOutBoundOrderPerMonth")
	public ResponseEntity<ResponseDTO> getOutBoundOrderPerMonth(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse,@RequestParam String client,
		@RequestParam int finYear) {
		String methodName = "getOutBoundOrderPerMonth()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> buyerorderVO = new ArrayList<>();
		try {
			buyerorderVO = dashboardService.getOutBoundOrderPerMonth(orgId, branchCode,warehouse,client,finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Outbound order month wise information get successfully");
			responseObjectsMap.put("buyerorderVO", buyerorderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Outbound order month wise  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getOutBoundOrderPerYear")
	public ResponseEntity<ResponseDTO> getOutBoundOrderPerYear(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse,@RequestParam String client,@RequestParam int finYear) {
		String methodName = "getOutBoundOrderPerYear()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> buyerorderVO = new ArrayList<>();
		try {
			buyerorderVO = dashboardService.getOutBoundOrderPerYear(orgId, branchCode,warehouse,client,finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "OutBound order year wise information get successfully");
			responseObjectsMap.put("buyerorderVO", buyerorderVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "OutBound order year wise  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getHoldMaterialCount")
	public ResponseEntity<ResponseDTO> getHoldMaterialCount(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse,@RequestParam String client) {
		String methodName = "getHoldMaterialCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> holdMaterialCount = new ArrayList<>();
		try {
			holdMaterialCount = dashboardService.getHoldMaterialCount(orgId, branchCode,warehouse,client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Hold Material Count information get successfully");
			responseObjectsMap.put("holdMaterialCount", holdMaterialCount);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Hold Material Count  information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
