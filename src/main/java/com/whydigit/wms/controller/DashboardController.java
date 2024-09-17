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
	
}
