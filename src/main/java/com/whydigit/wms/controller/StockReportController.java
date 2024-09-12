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
import com.whydigit.wms.service.StockReportService;

@RestController
@RequestMapping("/api/Reports")
public class StockReportController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockReportController.class);

	@Autowired
	StockReportService stockReportService;

	@GetMapping("/getStockConsolidation")
	public ResponseEntity<ResponseDTO> getStockConsolidation(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client,
			@RequestParam(required = true) String partNo) {
		String methodName = "getStockConsolidation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getConsolidateStockDetails(orgId, branchCode, warehouse, customer, client,
					partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetails  found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetails information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockReportBinAndBatchWise")
	public ResponseEntity<ResponseDTO> getStockReportBinAndBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client,
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String batch,
			@RequestParam(required = true) String bin, @RequestParam(required = true) String status) {
		String methodName = "getStockReportBinAndBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getStockReportBinAndBatchWise(orgId, branchCode, warehouse, customer,
					client, partNo, batch, bin, status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockDetails found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "StockDetails information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoForStockReportBinAndBatchWise")
	public ResponseEntity<ResponseDTO> getPartNoForStockReportBinAndBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client) {
		String methodName = "getPartNoForStockReportBinAndBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getPartNoForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
					customer, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo from StockDetails found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"PartNo from StockDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBatchForStockReportBinAndBatchWise")
	public ResponseEntity<ResponseDTO> getBatchForStockReportBinAndBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client,
			@RequestParam(required = true) String partNo) {
		String methodName = "getBatchForStockReportBinAndBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getBatchForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
					customer, client, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Batch from StockDetails found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Batch from StockDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinForStockReportBinAndBatchWise")
	public ResponseEntity<ResponseDTO> getBinForStockReportBinAndBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client,
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String batch) {
		String methodName = "getBinForStockReportBinAndBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getBinForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
					customer, client, partNo, batch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bin from StockDetails found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Bin from StockDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStatusForStockReportBinAndBatchWise")
	public ResponseEntity<ResponseDTO> getBinForStockReportBinAndBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String customer, @RequestParam(required = true) String client,
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String batch,
			@RequestParam(required = true) String bin) {
		String methodName = "getStatusForStockReportBinAndBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getStatusForStockReportBinAndBatchWise(orgId, branchCode, warehouse,
					customer, client, partNo, batch,bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Status from StockDetails found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Status from StockDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
