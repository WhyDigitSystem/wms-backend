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
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.service.InwardTransactionService;

@RestController
@RequestMapping("/api")
public class InwardTransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionController.class);

	@Autowired
	InwardTransactionService inwardTransactionService;

	// Grn

	@GetMapping("/grn")
	public ResponseEntity<ResponseDTO> getAllGrn() {
		String methodName = "getAllGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = inwardTransactionService.getAllGrn();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn information get successfully");
			responseObjectsMap.put("grnVO", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/grn/{id}")
	public ResponseEntity<ResponseDTO> getGrnById(@PathVariable Long id) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GrnVO grnVO = null;
		try {
			grnVO = inwardTransactionService.getGrnById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn found by ID");
			responseObjectsMap.put("Grn", grnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Grn not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/grn")
	public ResponseEntity<ResponseDTO> createGrn(@RequestBody GrnDTO grnDTO) {
		String methodName = "createGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GrnVO createdGrnVO = inwardTransactionService.createGrn(grnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn created successfully");
			responseObjectsMap.put("GrnVO", createdGrnVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " Grn & GrnCode already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/grn")
	public ResponseEntity<ResponseDTO> updateGrn(@RequestBody GrnVO grnVO) {
		String methodName = "updateGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GrnVO updatedGrnVO = inwardTransactionService.updateGrn(grnVO).orElse(null);
			if (updatedGrnVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn updated successfully");
				responseObjectsMap.put("GrnVO", updatedGrnVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Grn not found for Grn ID: " + grnVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Grn update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn & GrnCode already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// GatePassIn

	@GetMapping("/gatePassIn")
	public ResponseEntity<ResponseDTO> getAllGatePassIn() {
		String methodName = "getAllGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GatePassInVO> gatePassInVO = new ArrayList<>();
		try {
			gatePassInVO = inwardTransactionService.getAllGatePassIn();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn information get successfully");
			responseObjectsMap.put("gatePassInVO", gatePassInVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/gatePassIn/{id}")
	public ResponseEntity<ResponseDTO> getGatePassInById(@PathVariable Long id) {
		String methodName = "getGatePassInById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GatePassInVO gatePassInVO = null;
		try {
			gatePassInVO = inwardTransactionService.getGatePassInById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn found by ID");
			responseObjectsMap.put("GatePassIn", gatePassInVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "GatePassIn not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/gatePassIn")
	public ResponseEntity<ResponseDTO> createGatePassIn(@RequestBody GatePassInVO gatePassInVO) {
		String methodName = "createGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GatePassInVO createdGatePassInVO = inwardTransactionService.createGatePassIn(gatePassInVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn created successfully");
			responseObjectsMap.put("GatePassInVO", createdGatePassInVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					" GatePassIn, client & customer already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/gatePassIn")
	public ResponseEntity<ResponseDTO> updateGatePassIn(@RequestBody GatePassInVO gatePassInVO) {
		String methodName = "updateGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GatePassInVO updatedGatePassInVO = inwardTransactionService.updateGatePassIn(gatePassInVO).orElse(null);
			if (updatedGatePassInVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn updated successfully");
				responseObjectsMap.put("GatePassInVO", updatedGatePassInVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "GatePassIn not found for Grn ID: " + gatePassInVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn, client & customer already Exist ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PutAway
	@GetMapping("/putaway")
	public ResponseEntity<ResponseDTO> getAllPutAway() {
		String methodName = "getAllPutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PutAwayVO> putAwayVO = new ArrayList<>();
		try {
			putAwayVO = inwardTransactionService.getAllPutAway();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway information get successfully");
			responseObjectsMap.put("putAwayVO", putAwayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/putaway/{id}")
	public ResponseEntity<ResponseDTO> getPutAwayById(@PathVariable Long id) {
		String methodName = "getPutAwayById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PutAwayVO putAwayVO = null;
		try {
			putAwayVO = inwardTransactionService.getPutAwayById(id).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway found by ID");
			responseObjectsMap.put("PutAway", putAwayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "PutAway not found for ID: " + id;
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/putaway")
	public ResponseEntity<ResponseDTO> createPutAway(@RequestBody PutAwayDTO putAwayDTO) {
		String methodName = "createPutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PutAwayVO createdPutAwayVO = inwardTransactionService.createPutAway(putAwayDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway created successfully");
			responseObjectsMap.put("PutAwayVO", createdPutAwayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " PutAway & PutAwayCode already Exist ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/putaway")
	public ResponseEntity<ResponseDTO> updatePutAway(@RequestBody PutAwayVO putAwayVO) {
		String methodName = "updatePutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			PutAwayVO updatedPutAwayVO = inwardTransactionService.updatePutAway(putAwayVO).orElse(null);
			if (updatedPutAwayVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway updated successfully");
				responseObjectsMap.put("PutAwayVO", updatedPutAwayVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "PutAway not found for PutAway ID: " + putAwayVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "PutAway update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway & PutAwayCode already Exist",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
