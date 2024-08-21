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
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.service.LocationMovementService;

@RestController
@RequestMapping("/api/locationMovement")
public class LocationMovementController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(LocationMovementController.class);

	@Autowired
	LocationMovementService locationMovementService;

	@GetMapping("/getAllLocationMovementByOrgId")
	public ResponseEntity<ResponseDTO> getAllLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<LocationMovementVO> locationMovementVO = new ArrayList<>();
		try {
			locationMovementVO = locationMovementService.getAllLocationMovement(orgId, finYear, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMovement information get successfully ");
			responseObjectsMap.put("locationMovementVO", locationMovementVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMovement information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLocationMovementById")
	public ResponseEntity<ResponseDTO> getLocationMovementById(@RequestParam(required = false) Long id) {
		String methodName = "getLocationMovementById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LocationMovementVO locationMovementVO = new LocationMovementVO();

		try {
			locationMovementVO = locationMovementService.getLocationMovementById(id);
			if (locationMovementVO != null) {
				responseObjectsMap.put("locationMovementVO", locationMovementVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" LocationMovement information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " LocationMovement information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					" LocationMovement information retrieval failed.", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateLocationMovement")
	public ResponseEntity<ResponseDTO> createUpdateLocationMovement(
			@RequestBody LocationMovementDTO locationMovementDTO) {
		String methodName = "createUpdateLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> locationMovementVO = locationMovementService
					.createUpdateLocationMovement(locationMovementDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, locationMovementVO.get("message"));
			responseObjectsMap.put("locationMovementVO", locationMovementVO.get("locationMovementVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getBinFromStockForLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client) {

		String methodName = "getBinFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = locationMovementService.getBinFromStockForLocationMovement(orgId, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToBinFromLocationStatusForLocationMovement")
	public ResponseEntity<ResponseDTO> getToBinFromLocationStatusForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {

		String methodName = "getToBinFromLocationStatusForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = locationMovementService.getToBinFromLocationStatusForLocationMovement(orgId, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All ToBin from LocationStatus retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ToBin from LocationStatus information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllForLocationMovementDetailsFillGrid")
	public ResponseEntity<ResponseDTO> getAllForLocationMovementDetailsFillGrid(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getAllForLocationMovementDetailsFillGrid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = locationMovementService.getAllForLocationMovementDetailsFillGrid(orgId, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All bin details from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve all bin details from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin, @RequestParam(required = false) String partNo,
			@RequestParam(required = false) String partDesc, @RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = locationMovementService.getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(orgId, branch,
					branchCode, client, bin, partNo, partDesc, sku);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo and BatchNo from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo and BatchNo from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoAndPartDescFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin) {

		String methodName = "getPartNoAndPartDescFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = locationMovementService.getPartNoAndPartDescFromStockForLocationMovement(orgId, branch, branchCode,
					client, bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and Part Desc from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDEsc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLocationMovementDocId")
	public ResponseEntity<ResponseDTO> getLocationMovementDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getLocationMovementDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = locationMovementService.getLocationMovementDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"LocationMovement Docid information retrieved successfully");
			responseObjectsMap.put("locationMovementDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve LocationMovement Docid information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAvlQtyFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getAvlQtyFromStockForLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin,
			@RequestParam(required = false) String partDesc, @RequestParam(required = false) String sku,
			@RequestParam(required = false) String partNo, @RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String lotNo) {

		String methodName = "getAvlQtyFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int mov = 0;

		try {
			mov = locationMovementService.getAvlQtyFromStockForLocationMovement(orgId, branch, branchCode, client, bin,
					partDesc, sku, partNo, grnNo, lotNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"AvlQty from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve AvlQty from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}