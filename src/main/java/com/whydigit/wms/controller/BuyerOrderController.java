package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.service.BuyerOrderService;

@RestController
@RequestMapping("/api/buyerOrder")
public class BuyerOrderController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(BuyerOrderController.class);

	@Autowired
	BuyerOrderService buyerOrderService;

//	BUYERORDER

	@PutMapping("/createUpdateBuyerOrder")
	public ResponseEntity<ResponseDTO> createUpdateBuyerOrder(@RequestBody BuyerOrderDTO buyerOrderDTO) {
		String methodName = "createUpdateBuyerOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdBuyerOrderVO = buyerOrderService.createUpdateBuyerOrder(buyerOrderDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdBuyerOrderVO.get("message"));
			responseObjectsMap.put("buyerOrderVO", createdBuyerOrderVO.get("buyerOrderVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllBuyerOrderByOrgId")
	public ResponseEntity<ResponseDTO> getAllBuyerOrderByOrgId(@RequestParam(required = true) Long orgId) {
		String methodName = "getAllBuyerOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BuyerOrderVO> buyerOrderVOs = new ArrayList<>();
		try {
			buyerOrderVOs = buyerOrderService.getAllBuyerOrderByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrder information get successfully By OrgId");
			responseObjectsMap.put("buyerOrderVO", buyerOrderVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "BuyerOrder information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllBuyerOrderById")
	public ResponseEntity<ResponseDTO> getAllBuyerOrderById(@RequestParam(required = true) Long id) {
		String methodName = "getAllBuyerOrderById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		ResponseDTO responseDTO;
		Map<String, Object> responseObjectsMap = new HashMap<>();

		try {
			// Fetch the buyer order by ID
			Optional<BuyerOrderVO> optionalBuyerOrder = buyerOrderService.getAllBuyerOrderById(id);

			if (optionalBuyerOrder.isPresent()) {
				// Data found, prepare success response
				BuyerOrderVO buyerOrderVO = optionalBuyerOrder.get();
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"BuyerOrder information fetched successfully for Id: " + id);
				responseObjectsMap.put("buyerOrderVO", buyerOrderVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				// No data found, prepare response indicating no data
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "No BuyerOrder information found for Id: " + id);
				responseDTO = createServiceResponse(responseObjectsMap); // or createServiceResponseError() if preferred
			}
		} catch (Exception e) {
			// Exception occurred, prepare error response
			String errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg, e);
			responseDTO = createServiceResponseError(responseObjectsMap, "BuyerOrder information retrieval failed",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBuyerOrderDocId")
	public ResponseEntity<ResponseDTO> getBuyerOrderDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getBuyerOrderDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = buyerOrderService.getBuyerOrderDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrderDocId information retrieved successfully");
			responseObjectsMap.put("BuyerOrderDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve BuyerOrderDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAvlQty")
	public ResponseEntity<ResponseDTO> getAvlQtyByBO(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String client, @RequestParam(required = true) String branchCode,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String branch,
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String partDesc,
			@RequestParam(required = true) String batch) {
		String methodName = "getAvlQtyByBO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int avalQty = 0;
		try {
			avalQty = buyerOrderService.getAvlQtyByBO(orgId, client, branchCode, warehouse, branch, partNo, partDesc,
					batch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "getAvlQtyByBO information get successfully Id");
			responseObjectsMap.put("avalQty", avalQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "getAvlQtyByBO information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBoSkuDetails")
	public ResponseEntity<ResponseDTO> getBoSkuDetails(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String batch, @RequestParam(required = true) String warehouse) {
		String methodName = "getBoSkuDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> skuDetails = new ArrayList<Map<String, Object>>();
		try {
			skuDetails = buyerOrderService.getBoSkuDetails(orgId, branchCode, client, batch, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bo Sku Details information get successfully Id");
			responseObjectsMap.put("skuDetails", skuDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bo Sku Details  information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBatchByBuyerOrder")
	public ResponseEntity<ResponseDTO> getBatchByBuyerOrder(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String partNo) {
		String methodName = "getBatchByBuyerOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> skuDetails = new ArrayList<Map<String, Object>>();
		try {
			skuDetails = buyerOrderService.getBatchByBuyerOrder(orgId, branchCode, client, warehouse, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bo Batch Details information get successfully Id");
			responseObjectsMap.put("skuDetails", skuDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Bo Batch Details  information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
