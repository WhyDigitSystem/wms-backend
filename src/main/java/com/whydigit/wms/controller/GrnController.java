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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.service.GrnService;
import com.whydigit.wms.service.WarehouseMasterService;

@RestController
@RequestMapping("/api/grn")
public class GrnController extends BaseController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(GrnController.class);
	
	@Autowired
	GrnService grnService;
	
	@Autowired
	WarehouseMasterService warehouseMasterService;
	
	@GetMapping("/getAllModeOfShipment")
	public ResponseEntity<ResponseDTO> getAllModeOfShipment(@RequestParam Long orgId) {
		String methodName = "getAllModeOfShipment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> modOfShipments = new ArrayList<>();
		try {
			modOfShipments = warehouseMasterService.getAllModeOfShipment(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ModeOfShipment information get successfully");
			responseObjectsMap.put("modOfShipments", modOfShipments);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ModeOfShipment information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	
	@GetMapping("/getGatePassDetailsByGatePassNo")
	public ResponseEntity<ResponseDTO> getGatePassDetailsByGatePassNo(@RequestParam Long orgid,
			@RequestParam String client, @RequestParam String entryno, @RequestParam Long docid,
			@RequestParam String branchcode) {
		String methodName = "getGatePassDetailsByGatePassNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String,Object>> grn = new ArrayList<>();
		try {
			grn = grnService.getGatePassDetailsByGatePassNo(orgid, client, entryno, docid, branchcode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"getGatePassDetailsByGatePassNo information get successfully");
			responseObjectsMap.put("getGatePassDetailsByGatePassNo", grn);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"getGatePassDetailsByGatePassNo information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	

	// Grn

	@GetMapping("/getAllGrn")
	public ResponseEntity<ResponseDTO> getAllGrn(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String warehouse) {
		String methodName = "getAllGrn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = grnService.getAllGrn(orgId, finYear, branch, branchCode, client, warehouse);
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

	@GetMapping("/getGRNDocid")
	public ResponseEntity<ResponseDTO> getGRNDocid(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branchCode, @RequestParam String client) {
		String methodName = "getGRNDocid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String grnDocid = "";
		String screenCode = "GN";
		try {
			grnDocid = grnService.getGRNdocid(orgId, finYear, branchCode, client, screenCode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn Docid information get successfully");
			responseObjectsMap.put("grnDocid", grnDocid);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn Docid information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnById")
	public ResponseEntity<ResponseDTO> getGrnById(@RequestParam Long id) {
		String methodName = "getGrnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GrnVO grnVO = null;
		try {
			grnVO = grnService.getGrnById(id);
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

	@PutMapping("/createUpdateGRN")
	public ResponseEntity<ResponseDTO> createUpdateGRN(@RequestBody GrnDTO grnDTO) {
		String methodName = "createUpdateGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> grnVO = grnService.createUpdateGrn(grnDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, grnVO.get("message"));
			responseObjectsMap.put("grnVO", grnVO.get("grnVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGatePassInNoForPedningGRN")
	public ResponseEntity<ResponseDTO> getGatePassInNoForPedningGRN(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String finYear, @RequestParam String client) {
		String methodName = "getGatePassInNoForPedningGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GatePassInVO> gatePassInVO = new ArrayList<>();
		try {
			gatePassInVO = grnService.getGatepassInNoForPendingGRN(orgId, branchCode, finYear, client);
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

	@GetMapping("/getGatePassInDetailsForPendingGRN")
	public ResponseEntity<ResponseDTO> getGatePassInGridDetailsForPedningGRN(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String gatePassDocId) {
		String methodName = "getGatePassInGridDetailsForPedningGRN()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> gatePassInDetailsVO = new ArrayList<>();
		try {
			gatePassInDetailsVO = grnService.getGatepassInGridDetailsForPendingGRN(orgId, finYear,
					branchCode, client, gatePassDocId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GatePassIn information get successfully");
			responseObjectsMap.put("gatePassInVO", gatePassInDetailsVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "GatePassIn information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	

	

	@PostMapping("/ExcelUploadForGrn")
	public ResponseEntity<ResponseDTO> ExcelUploadForGrn(@RequestParam MultipartFile[] files,
			com.whydigit.wms.dto.CustomerAttachmentType type, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String createdBy,@RequestParam(required = false) String customer, @RequestParam(required = false)  String client, @RequestParam(required = false)  String finYear, 
			 @RequestParam(required = false) String branch, @RequestParam(required = false)  String branchCode, @RequestParam(required = false)  String warehouse) {
		String methodName = "ExcelUploadForGrn()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			grnService.ExcelUploadForGrn(files, type, orgId, createdBy,customer,client,finYear,branch, branchCode, warehouse);

			// Retrieve the counts after processing
			totalRows = grnService.getTotalRows(); // Get total rows processed
			successfulUploads = grnService.getSuccessfulUploads(); // Get successful uploads count
			// Construct success response
			responseObjectsMap.put("statusFlag", "Ok");
			responseObjectsMap.put("status", true);
			responseObjectsMap.put("totalRows", totalRows);
			responseObjectsMap.put("successfulUploads", successfulUploads);
			Map<String, Object> paramObjectsMap = new HashMap<>();
			paramObjectsMap.put("message", "Excel Upload For  Grn successful");
			responseObjectsMap.put("paramObjectsMap", paramObjectsMap);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
			responseObjectsMap.put("statusFlag", "Error");
			responseObjectsMap.put("status", false);
			responseObjectsMap.put("errorMessage", errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Grn Failed", errorMsg);
      }
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	@GetMapping("/getGrnStatusForDashBoard")
	public ResponseEntity<ResponseDTO> getGrnStatusForDashBoard(@RequestParam Long orgId,
			@RequestParam String finYear, @RequestParam String branchCode, @RequestParam String client,
			 @RequestParam String warehouse) {
		String methodName = "getGrnStatusForDashBoard()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnDashboard = new ArrayList<>();
		try {
			grnDashboard = grnService.getGrnStatusForDashBoard(orgId, finYear,
					branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Grn information get successfully");
			responseObjectsMap.put("grnDashboard", grnDashboard);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Grn information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}



}
