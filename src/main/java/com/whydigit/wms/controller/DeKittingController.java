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
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.service.DeKittingService;

@RestController
@RequestMapping("/api/deKitting")
public class DeKittingController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(DeKittingController.class);

	@Autowired
	DeKittingService deKittingService;

	@GetMapping("/getAllDeKittingByOrgId")
	public ResponseEntity<ResponseDTO> getAllDeKitting(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllDeKitting()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DeKittingVO> deKittingVO = new ArrayList<>();
		try {
			deKittingVO = deKittingService.getAllDeKitting(orgId, finYear, branch, branchCode, client, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeKitting information get successfully ");
			responseObjectsMap.put("deKittingVO", deKittingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "DeKitting information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeKittingById")
	public ResponseEntity<ResponseDTO> getDeKittingById(@RequestParam(required = false) Long id) {
		String methodName = "getDeKittingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeKittingVO deKittingVO = new DeKittingVO();

		try {
			deKittingVO = deKittingService.getDeKittingById(id);
			if (deKittingVO != null) {
				responseObjectsMap.put("deKittingVO", deKittingVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" DeKitting information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " DeKitting information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " DeKitting information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateDeKitting")
	public ResponseEntity<ResponseDTO> createUpdateDeKitting(@RequestBody DeKittingDTO deKittingDTO) {
		String methodName = "createUpdateDeKitting()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> deKittingVO = deKittingService.createUpdateDeKitting(deKittingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deKittingVO.get("message"));
			responseObjectsMap.put("deKittingVO", deKittingVO.get("deKittingVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getDeKittingDocId")
	public ResponseEntity<ResponseDTO> getDeKittingDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getDeKittingDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = deKittingService.getDeKittingDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeKitting Docid information retrieved successfully");
			responseObjectsMap.put("deKittingDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve DeKitting Docid information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoFromStockForDeKittingParent")
	public ResponseEntity<ResponseDTO> getPartNoFromStockForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client) {
		String methodName = "getPartNoFromStockForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partNoDetails = new ArrayList<>();
		try {
			partNoDetails = deKittingService.getPartNoFromStockForDeKittingParent(orgId, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and PartDesc from Stock information retrieved successfully");
			responseObjectsMap.put("partNoDetails", partNoDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDesc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinForDeKittingParent")
	public ResponseEntity<ResponseDTO> getBinForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,@RequestParam(required = false) String partNo, @RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String batchNo) {

		String methodName = "getBinForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> binDetails = new ArrayList<>();

		try {
			binDetails = deKittingService.getBinFromStockForDeKittingParent(orgId, branch, branchCode, client, partNo, grnNo, batchNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("binDetails", binDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBatchNoForDeKittingParent")
	public ResponseEntity<ResponseDTO> getBatchNoForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,@RequestParam(required = false) String partNo, @RequestParam(required = false) String grnNo) {

		String methodName = "getBatchNoForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> batchDetails = new ArrayList<>();

		try {
			batchDetails = deKittingService.getBatchAndBatchFromStockForDeKittingParent(orgId, branch, branchCode, client, partNo, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("batchDetails", batchDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGrnDetailsForDekittingParent")
	public ResponseEntity<ResponseDTO> getGrnDetailsForDekittingParent(
			@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,@RequestParam(required = false) String partNo) {
		String methodName = "getGrnDetailsForDekittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnDetails = new ArrayList<>();

		try {
			grnDetails = deKittingService.getGrnNoFromStockForDeKittingParent(orgId, branch, branchCode, client, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo and BatchNo from Stock information retrieved successfully");
			responseObjectsMap.put("grnDetails", grnDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo and BatchNo from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAvlQtyForDeKittingParent")
	public ResponseEntity<ResponseDTO> getAvlQtyForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,@RequestParam(required = false) String partNo, @RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String batchNo,@RequestParam(required = false) String bin
			) {
		String methodName = "getAvlQtyForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int avlQty = 0 ;
		try {
			avlQty = deKittingService.getAvlQtyFromStockForDeKittingParent(orgId, branch, branchCode, client, partNo, grnNo, batchNo, bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"AvlQty from Stock information retrieved successfully");
			responseObjectsMap.put("avlQty", avlQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve AvlQty from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoforDeKittingChild")
	public ResponseEntity<ResponseDTO> getPartNoforDeKittingChild(
			@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {
		String methodName = "getPartNoforDeKittingChild()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partNoChild = new ArrayList<>();
		try {
			partNoChild = deKittingService.getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(orgId, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and sku from Stock information retrieved successfully");
			responseObjectsMap.put("partNoChild", partNoChild);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and sku from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
