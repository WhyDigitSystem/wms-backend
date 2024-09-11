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

	@GetMapping("/getStockReportBinWise")
	public ResponseEntity<ResponseDTO> getStockReportBinWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String bin,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String customer,
			@RequestParam(required = true) String client, @RequestParam(required = true) String partNo) {
		String methodName = "getStockReportBinWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getStockReportBinWise(orgId, branchCode, bin, warehouse, customer, client,
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
	
	@GetMapping("/getStockReportBatchWise")
	public ResponseEntity<ResponseDTO> getStockReportBatchWise(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String batch,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String customer,
			@RequestParam(required = true) String client, @RequestParam(required = true) String partNo) {
		String methodName = "getStockReportBatchWise()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getStockReportBatchWise(orgId, branchCode,batch, warehouse, customer, client,
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
	
	@GetMapping("/getStockLedger")
	public ResponseEntity<ResponseDTO> getStockLedger(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode,@RequestParam(required = true) String warehouse, 
			@RequestParam(required = true) String customer,@RequestParam(required = true) String client, 
			@RequestParam(required = true) String startDate,@RequestParam(required = true) String endDate,@RequestParam(required = true) String partNo) {
		String methodName = "getStockLedger()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> stockDetails = new ArrayList<Map<String, Object>>();
		try {
			stockDetails = stockReportService.getStockLedger(orgId, branchCode, warehouse, customer, client, startDate, endDate, partNo);

		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Stock Ledger Details  found Successfullly");
			responseObjectsMap.put("stockDetails", stockDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Stock Ledger Details information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
