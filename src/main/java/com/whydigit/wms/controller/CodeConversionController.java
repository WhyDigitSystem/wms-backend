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
import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.service.CodeConversionService;

@RestController
@RequestMapping("/api/codeconversion")
public class CodeConversionController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(CodeConversionController.class);

	@Autowired
   CodeConversionService codeConversionService;
	

	// CodeConversion
	@GetMapping("/getAllCodeConversion")
	public ResponseEntity<ResponseDTO> getAllCodeConversion(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String warehouse) {
		String methodName = "getAllCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CodeConversionVO> codeConversionVO = new ArrayList<>();
		try {
			codeConversionVO = codeConversionService.getAllCodeConversion(orgId, finYear, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CodeConversion information get successfully");
			responseObjectsMap.put("codeConversionVO", codeConversionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "CodeConversion information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCodeConversionById")
	public ResponseEntity<ResponseDTO> getCodeConversionById(@RequestParam(required = false) Long id) {
		String methodName = "getCodeConversionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CodeConversionVO codeConversionVO = new CodeConversionVO();

		try {
			codeConversionVO = codeConversionService.getCodeConversionById(id);
			if (codeConversionVO != null) {
				responseObjectsMap.put("codeConversionVO", codeConversionVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" CodeConversion information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " CodeConversion information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					" CodeConversion information retrieval failed.", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCodeConversionDocId")
	public ResponseEntity<ResponseDTO> getCodeConversionDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getCodeConversionDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = codeConversionService.getCodeConversionDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All CodeConversion information retrieved successfully");
			responseObjectsMap.put("CodeConversionDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve CodeConversion information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateCodeConversion")
	public ResponseEntity<ResponseDTO> createUpdateCodeConversion(@RequestBody CodeConversionDTO codeConversionDTO) {
		String methodName = "createUpdateCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> codeConversionVO = codeConversionService.createUpdateCodeConversion(codeConversionDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, codeConversionVO.get("message"));
			responseObjectsMap.put("codeConversionVO", codeConversionVO.get("codeConversionVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	@GetMapping("/getPartNoAndPartDescFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId,
			 @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {

		String methodName = "getPartNoAndPartDescFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getPartNoAndPartDescFromStockForCodeConversion(orgId, branchCode,
					client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and PartDesc from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDesc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndGrnDateFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getGrnNoAndGrnDateFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, 
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse,
			@RequestParam(required = false) String partNo) {

		String methodName = "getGrnNoAndGrnDateFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getGrnNoAndGrnDateFromStockForCodeConversion(orgId,
					  branchCode, client,warehouse, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo from Stock information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBatchNoFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getBatchNoFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, 
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse,
			@RequestParam(required = false) String partNo,
			@RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String binType) {

		String methodName = "getBatchNoFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getBatchNoFromStockForCodeConversion(orgId,
					  branchCode, client,warehouse, partNo,grnNo,binType);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All BatchNo and Batchdate from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve BatchNo and Batchdate from Stock information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinTypeFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getBinTypeFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, 
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse,
			@RequestParam(required = false) String partNo,
			@RequestParam(required = false) String grnNo) {

		String methodName = "getBinTypeFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getBinTypeFromStockForCodeConversion(orgId,
					  branchCode, client,warehouse, partNo,grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All binType from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve binType from Stock information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getBinFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getBinFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, 
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse,
			@RequestParam(required = false) String partNo,
			@RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String binType,
			@RequestParam(required = false) String batchNo) {

		String methodName = "getBinFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getBinFromStockForCodeConversion(orgId,
					  branchCode, client,warehouse, partNo,grnNo,binType,batchNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
		}


	@GetMapping("/getAllFillGridFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getAllFillGridFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, 
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllFillGridFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = codeConversionService.getAllFillGridFromStockForCodeConversion(orgId, branchCode, client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All FillGrid from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FillGrid from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAvlQtyCodeConversion")
	public ResponseEntity<ResponseDTO> getAvlQtyCodeConversion(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String client, @RequestParam(required = true) String branchCode,
			@RequestParam(required = true) String warehouse, @RequestParam(required = true) String branch,
			@RequestParam(required = true) String partNo, @RequestParam(required = true) String partDesc) {
		String methodName = "getAvlQtyCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int avalQty = 0;
		try {
			avalQty = codeConversionService.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
					partDesc);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SQTY information get successfully Id");
			responseObjectsMap.put("avalQty", avalQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "SQTY information get Failed ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
//	@GetMapping("/getBinFromStockForCodeConversion")
//	public ResponseEntity<ResponseDTO> getBinFromStockForCodeConversion(@RequestParam(required = false) Long orgId,
//			 @RequestParam(required = false) String branch,
//			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {
//		String methodName = "getBinFromStockForCodeConversion()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> mov = new ArrayList<>();
//		try {
//			mov = codeConversionService.getBinFromStockForCodeConversion(orgId, branch, branchCode, client);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"All Bin from Stock information retrieved successfully");
//			responseObjectsMap.put("codeConversionVO", mov);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Failed to retrieve Bin from Stock information", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//	
	
//	@GetMapping("/getCpartNoAndCpartDescFromStockForCodeConversion")
//	public ResponseEntity<ResponseDTO> getCpartNoAndCpartDescFromStockForCodeConversion(
//			@RequestParam(required = false) Long orgId, 
//			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
//			@RequestParam(required = false) String client, @RequestParam(required = false) String bin) {
//
//		String methodName = "getCpartNoAndCpartDescFromStockForCodeConversion()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> mov = new ArrayList<>();
//		try {
//			mov = codeConversionService.getCpartNoAndCpartDescFromStockForCodeConversion(orgId, branch, branchCode,
//					client, bin);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"All CPartNo and CPartDesc from Stock information retrieved successfully");
//			responseObjectsMap.put("codeConversionVO", mov);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Failed to retrieve CPartNo and CPartDesc from Stock information", errorMsg);
//		}
//
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	@GetMapping("/getCBinFromStockForCodeConversion")
//	public ResponseEntity<ResponseDTO> getCBinFromStockForCodeConversion(@RequestParam(required = false) Long orgId,
//			 @RequestParam(required = false) String branch,
//			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {
//		String methodName = "getCBinFromStockForCodeConversion()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		List<Map<String, Object>> mov = new ArrayList<>();
//		try {
//			mov = codeConversionService.getCBinFromStockForCodeConversion(orgId, branch, branchCode, client);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"All CBin from Stock information retrieved successfully");
//			responseObjectsMap.put("codeConversionVO", mov);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"Failed to retrieve CBin from Stock information", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

	

	
}
