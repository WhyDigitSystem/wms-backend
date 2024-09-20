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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.MultipleBODTO;
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
	public ResponseEntity<ResponseDTO> getAllBuyerOrderByOrgId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String warehouse) {
		String methodName = "getAllBuyerOrderByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BuyerOrderVO> buyerOrderVOs = new ArrayList<>();
		try {
			buyerOrderVOs = buyerOrderService.getAllBuyerOrderByOrgId(orgId, finYear, branch, branchCode, client,
					warehouse);
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
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String batch) {
		String methodName = "getAvlQtyByBO()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int avalQty = 0;
		try {
			avalQty = buyerOrderService.getAvlQtyByBO(orgId, client, branchCode, warehouse, branch, partNo, batch);
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
			@RequestParam(required = true) String warehouse) {
		String methodName = "getBoSkuDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> skuDetails = new ArrayList<Map<String, Object>>();
		try {
			skuDetails = buyerOrderService.getBoSkuDetails(orgId, branchCode, client, warehouse);
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

	@GetMapping("/getPartNoByBuyerOrder")
	public ResponseEntity<ResponseDTO> getPartNoByBuyerOrder(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse) {
		String methodName = "getPartNoByBuyerOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partNoDetails = new ArrayList<Map<String, Object>>();
		try {
			partNoDetails = buyerOrderService.getPartNoByBuyerOrder(orgId, branchCode, client, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo Details information get successfully Id");
			responseObjectsMap.put("partNoDetails", partNoDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PartNo Details  information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAvlQtyForBuyerOrder")
	public ResponseEntity<ResponseDTO> getAvlQtyForBuyerOrder(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String partNo,
			@RequestParam(required = true) String batchNo) {
		String methodName = "getAvlQtyForBuyerOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int avalQty = 0;
		try {
			avalQty = buyerOrderService.getAvlQtyForBuyerOrder(orgId, branchCode, client, warehouse, partNo, batchNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "avlQty Details information get successfully Id");
			responseObjectsMap.put("avlQty", avalQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "avlQty Details  information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/ExcelUploadForBuyerOrder")
	public ResponseEntity<ResponseDTO> ExcelUploadForBuyerOrder(@RequestParam MultipartFile[] files,
			com.whydigit.wms.dto.CustomerAttachmentType type, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String createdBy, String customer, String client, String finYear, String branch, String branchCode, String warehouse) {
		String methodName = "ExcelUploadForBuyerOrder()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			buyerOrderService.ExcelUploadForBo(files, type, orgId, createdBy, customer,  client,  finYear,  branch,  branchCode, warehouse);

			// Retrieve the counts after processing
			totalRows = buyerOrderService.getTotalRows(); // Get total rows processed
			successfulUploads = buyerOrderService.getSuccessfulUploads(); // Get successful uploads count
			// Construct success response
			responseObjectsMap.put("statusFlag", "Ok");
			responseObjectsMap.put("status", true);
			responseObjectsMap.put("totalRows", totalRows);
			responseObjectsMap.put("successfulUploads", successfulUploads);
			Map<String, Object> paramObjectsMap = new HashMap<>();
			paramObjectsMap.put("message", "Excel Upload For BuyerOrder successful");
			responseObjectsMap.put("paramObjectsMap", paramObjectsMap);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
			responseObjectsMap.put("statusFlag", "Error");
			responseObjectsMap.put("status", false);
			responseObjectsMap.put("errorMessage", errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For BuyerOrder Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@PutMapping("/createMultipleBuyerOrder")
	public ResponseEntity<ResponseDTO> createMultipleBuyerOrder(@RequestBody List<MultipleBODTO> multipleBODTO) {
		String methodName = "createMultipleBuyerOrder()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdMultipleBuyerOrderVO = buyerOrderService.createMultipleBuyerOrder(multipleBODTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdMultipleBuyerOrderVO.get("message"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getPendingBuyerOrderDetails")
	public ResponseEntity<ResponseDTO> getPendingBuyerOrderDetails(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode,@RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String client,@RequestParam(required = true) String finYear) {
		String methodName = "getPendingBuyerOrderDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> pendingOrderDetails = new ArrayList<Map<String, Object>>();
		try {
			pendingOrderDetails = buyerOrderService.getPendingBuyerOrderDetails(orgId, branchCode, warehouse, client, finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pending Order Details information get successfully");
			responseObjectsMap.put("pendingOrderDetails", pendingOrderDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Pending Order Details information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBuyerorderDashboard")
	public ResponseEntity<ResponseDTO> getBuyerorderDashboard(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode,@RequestParam(required = true) String warehouse,
			@RequestParam(required = true) String client,@RequestParam(required = true) String finYear) {
		String methodName = "getBuyerorderDashboard()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> buyerorderDashboard = new ArrayList<Map<String, Object>>();
		try {
			buyerorderDashboard = buyerOrderService.getBuyerorderDashboard(orgId, branchCode, warehouse, client, finYear);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyerorder Dashboard  Details information get successfully");
			responseObjectsMap.put("buyerorderDashboard", buyerorderDashboard);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Buyerorder Dashboard Details information get Failed ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
}
