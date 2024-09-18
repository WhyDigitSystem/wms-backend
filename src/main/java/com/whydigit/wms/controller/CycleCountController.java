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
import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.service.CycleCountService;

@RestController
@RequestMapping("/api/cycleCount")
public class CycleCountController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CycleCountController.class);
 
	@Autowired 
	CycleCountService cycleCountService; 

	// CYCLECOUNT 

	@PutMapping("/createUpdateCycleCount")
	public ResponseEntity<ResponseDTO> createUpdateCycleCount(@RequestBody CycleCountDTO cycleCountDTO) {
		String methodName = "createUpdateCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> cycleCount = cycleCountService.createUpdateCycleCount(cycleCountDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, cycleCount.get("message"));
			responseObjectsMap.put("cycleCountVO", cycleCount.get("cycleCountVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCycleCountInDocId")
	public ResponseEntity<ResponseDTO> getCycleCountInDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getCycleCountInDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = cycleCountService.getCycleCountInDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CyCleCount DocId information retrieved successfully");
			responseObjectsMap.put("CycleCountInDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount DocId information  retrieve Failed", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getAllCycleCount")
	public ResponseEntity<ResponseDTO> getAllCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String client, @RequestParam(required = true) String branch,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String finYear,
			@RequestParam(required = true) String warehouse) {
		String methodName = "getAllCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CycleCountVO> cycleCountVOs = new ArrayList<CycleCountVO>();
		try {
			cycleCountVOs = cycleCountService.getAllCycleCount(orgId, client, branch, branchCode, finYear, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount Details information get successfully");
			responseObjectsMap.put("cycleCountVO", cycleCountVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "VasPick  Details information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getCycleCountById")
	public ResponseEntity<ResponseDTO> getCycleCountById(@RequestParam(required = true) Long id) {
		String methodName = "getCycleCountById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CycleCountVO cycleCountVO = null;
		try {
			cycleCountVO = cycleCountService.getCycleCountById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCountById Details found by ID");
			responseObjectsMap.put("cycleCountVO", cycleCountVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "vasPickVO not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "CycleCountById Details not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getCycleCountGridDetails")
	public ResponseEntity<ResponseDTO> getCycleCountGridDetails(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String status) {
		String methodName = "getCycleCountGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleCountGrid = new ArrayList<Map<String, Object>>();
		try {
			cycleCountGrid = cycleCountService.getCycleCountGridDetails(orgId, branchCode, client, warehouse,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount GridDetails Details found Successfullly");
			responseObjectsMap.put("cycleCountGrid", cycleCountGrid);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount GridDetails information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getPartNoByCycleCount")
	public ResponseEntity<ResponseDTO> getPartNoByCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String status) {
		String methodName = "getPartNoByCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleCountPartNo = new ArrayList<Map<String, Object>>();
		try {
			cycleCountPartNo = cycleCountService.getPartNoByCycleCount(orgId, branchCode, client, warehouse,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount PartNo Details found Successfullly");
			responseObjectsMap.put("cycleCountPartNo", cycleCountPartNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount PartNo information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getGrnNoByCycleCount")
	public ResponseEntity<ResponseDTO> getGrnNoByCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo,@RequestParam(required = true) String status ) {
		String methodName = "getGrnNoByCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleCountGrnNo = new ArrayList<Map<String, Object>>();
		try {
			cycleCountGrnNo = cycleCountService.getGrnNoByCycleCount(orgId, branchCode, client, warehouse,partNo,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount GrnNo Details found Successfullly");
			responseObjectsMap.put("cycleCountGrnNo", cycleCountGrnNo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount GrnNo information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getBatchByCycleCount")
	public ResponseEntity<ResponseDTO> getBatchByCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo,
			@RequestParam(required = true) String grnNO,@RequestParam(required = true) String status) {
		String methodName = "getBatchByCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleCountBatch = new ArrayList<Map<String, Object>>();
		try {
			cycleCountBatch = cycleCountService.getBatchByCycleCount(orgId, branchCode, client, warehouse,partNo,grnNO,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount Batch Details found Successfullly");
			responseObjectsMap.put("cycleCountBatch", cycleCountBatch);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount Batch information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("getBinDetailsByCycleCount")
	public ResponseEntity<ResponseDTO> getBinDetailsByCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo,
			@RequestParam(required = true) String grnNO,@RequestParam(required = true) String batch,@RequestParam(required = true) String status) {
		String methodName = "getBinDetailsByCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleBinDetails = new ArrayList<Map<String, Object>>();
		try {
			cycleBinDetails = cycleCountService.getBinDetailsByCycleCount(orgId, branchCode, client, warehouse,partNo,grnNO,batch,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount Bin Details found Successfullly");
			responseObjectsMap.put("cycleBinDetails", cycleBinDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount Bin information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getAvlQtyByCycleCount")
	public ResponseEntity<ResponseDTO> getAvlQtyByCycleCount(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo,
			@RequestParam(required = true) String grnNO,@RequestParam(required = true) String batch,
			@RequestParam(required = true) String bin,@RequestParam(required = true) String status
			) {
		String methodName = "getAvlQtyByCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> avlQty = new ArrayList<Map<String, Object>>();
		try {
			avlQty = cycleCountService.getAvlQtyByCycleCount(orgId, branchCode, client, warehouse,partNo,grnNO,batch,bin,status);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount Avl Qty Details found Successfullly");
			responseObjectsMap.put("avlQty", avlQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CycleCount Avl Qty in information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
