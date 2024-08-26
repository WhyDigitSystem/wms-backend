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
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.service.SalesReturnService;

@RestController
@RequestMapping("/api/salesReturn")
public class SalesReturnController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(SalesReturnController.class);

	@Autowired
	SalesReturnService salesReturnService;
	
	@GetMapping("/getAllSalesReturnByOrgId")
	public ResponseEntity<ResponseDTO> getAllSalesReturn(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllSalesReturn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SalesReturnVO> salesReturnVO = new ArrayList<>();
		try {
			salesReturnVO = salesReturnService.getAllSalesReturn(orgId, finYear, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sales Return information get successfully ");
			responseObjectsMap.put("salesReturnVO", salesReturnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "sales return information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesReturnById")
	public ResponseEntity<ResponseDTO> getSalesReturnById(@RequestParam(required = false) Long id) {
		String methodName = "getSalesReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesReturnVO salesReturnVO = new SalesReturnVO();

		try {
			salesReturnVO = salesReturnService.getSalesReturnById(id);
			if (salesReturnVO != null) {
				responseObjectsMap.put("salesReturnVO", salesReturnVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" SalesReturn information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " SalesReturn information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " SalesReturn information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSalesReturn")
	public ResponseEntity<ResponseDTO> createUpdateSalesReturn(@RequestBody SalesReturnDTO salesReturnDTO) {
		String methodName = "createUpdateSalesReturn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> salesReturnVO = salesReturnService.createUpdateSalesReturn(salesReturnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, salesReturnVO.get("message"));
			responseObjectsMap.put("salesReturnVO", salesReturnVO.get("salesReturnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesReturnFillGridDetails")
	public ResponseEntity<ResponseDTO> getSalesReturnFillGridDetails(@RequestParam(required = false) String docId,
			@RequestParam(required = false) String client, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branchCode) {

		String methodName = "getSalesReturnFillGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = salesReturnService.getSalesReturnFillGridDetails(docId, client, orgId, branchCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All SalesReturnFillGridDetails information retrieved successfully");
			responseObjectsMap.put("salesReturnDetailsVO", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesReturnFillGridDetails information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getSalesReturnDocId")
	public ResponseEntity<ResponseDTO> getSalesReturnDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getSalesReturnDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = salesReturnService.getSalesReturnDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"SalesReturn Docid information retrieved successfully");
			responseObjectsMap.put("SalesReturnDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve SalesReturn Docid information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
