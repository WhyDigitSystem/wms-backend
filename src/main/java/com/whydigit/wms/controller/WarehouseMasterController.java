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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.WarehouseLocationDetailsVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.service.WarehouseMasterService;

@RequestMapping("/api")
@RestController
public class WarehouseMasterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterController.class);
	
	@Autowired
	WarehouseMasterService warehouseMasterService; 

	@GetMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> getAllWarehouseLocation() {
		String methodName = "getAllWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		try {
			warehouseLocationVO = warehouseMasterService.getAllWarehouseLocation();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location information get successfully");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/warehouselocation/company")
	public ResponseEntity<ResponseDTO> getAllWarehouseLocationByCompany(@RequestParam String Company){
		
		String methodName = "getAllWarehouseLocationByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		try {
			warehouseLocationVO = warehouseMasterService.getAllWarehouseLocationByCompany(Company);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location found by ID");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "WarehouseLocation not found for Company: " + Company;
			responseDTO = createServiceResponseError(responseObjectsMap, "WarehouseLocation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/warehouselocation/{warehouselocationid}")
	public ResponseEntity<ResponseDTO> getWarehouseLocationById(@PathVariable Long warehouselocationid) {
		String methodName = "getWarehouseLocationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		WarehouseLocationVO warehouseLocationVO = null;
		try {
			warehouseLocationVO = warehouseMasterService.getWarehouseLocationById(warehouselocationid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location found by ID");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Warehouse Location not found for ID: " + warehouselocationid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> createWarehouseLocation(@RequestBody WarehouseLocationVO warehouseLocationVO) {
		String methodName = "createWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseLocationVO createWarehouseLocation = warehouseMasterService.createWarehouseLocation(warehouseLocationVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location created successfully");
			responseObjectsMap.put("WarehouseLocation", createWarehouseLocation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location creation", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> updateWarehouseLocation(@RequestBody WarehouseLocationVO warehouseLocationVO) {
		String methodName = "updateWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseLocationVO updatedwarehouseLocationVO = warehouseMasterService.updateWarehouseLocation(warehouseLocationVO).orElse(null);
			if (updatedwarehouseLocationVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location updated successfully");
				responseObjectsMap.put("warehouseLocationVO", updatedwarehouseLocationVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse Location not found for Warehouse LocationID: " + warehouseLocationVO.getWarehouselocationid();
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
		
}
