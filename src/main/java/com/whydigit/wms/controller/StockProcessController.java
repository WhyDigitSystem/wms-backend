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
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.service.StockProcessService;

@RestController
@RequestMapping("/api/stockprocess")
public class StockProcessController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockProcessController.class);

	@Autowired
	StockProcessService stockProcessService;

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
			codeConversionVO = stockProcessService.getAllCodeConversion(orgId, finYear, branch, branchCode, client,
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
			codeConversionVO = stockProcessService.getCodeConversionById(id);
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
			mapp = stockProcessService.getCodeConversionDocId(orgId, finYear, branch, branchCode, client);
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
			Map<String, Object> codeConversionVO = stockProcessService.createUpdateCodeConversion(codeConversionDTO);
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

//	SalesReturn
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
			salesReturnVO = stockProcessService.getAllSalesReturn(orgId, finYear, branch, branchCode, client,
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

	@GetMapping("/getAllSalesReturnById")
	public ResponseEntity<ResponseDTO> getAllSalesReturnById(@RequestParam(required = false) Long id) {
		String methodName = "getAllSalesReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesReturnVO salesReturnVO = new SalesReturnVO();
		try {
			salesReturnVO = stockProcessService.getAllSalesReturnById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseDTO = createServiceResponseError(responseObjectsMap, "SalesReturn information receive failed By Id",
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
			Map<String, Object> salesReturnVO = stockProcessService.createUpdateSalesReturn(salesReturnDTO);
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
	public ResponseEntity<ResponseDTO> getAllDocumentTypesMappingDetailsByDocumentType(
			@RequestParam(required = false) String docId, @RequestParam(required = false) String client,
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branchCode) {

		String methodName = "getSalesReturnFillGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mapp = new ArrayList<>();

		try {
			mapp = stockProcessService.getSalesReturnFillGridDetails(docId, client, orgId, branchCode);
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
			mapp = stockProcessService.getSalesReturnDocId(orgId, finYear, branch, branchCode, client);
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

//	LocationMovement
	@GetMapping("/getAllLocationMovementByOrgId")
	public ResponseEntity<ResponseDTO> getAllLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {
		String methodName = "getAllLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<LocationMovementVO> locationMovementVO = new ArrayList<>();
		try {
			locationMovementVO = stockProcessService.getAllLocationMovement(orgId, finYear, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMovement information get successfully ");
			responseObjectsMap.put("locationMovementVO", locationMovementVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMovement information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllLocationMovementById")
	public ResponseEntity<ResponseDTO> getAllLocationMovementById(@RequestParam(required = false) Long id) {
		String methodName = "getAllLocationMovementById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LocationMovementVO locationMovementVO = new LocationMovementVO();
		try {
			locationMovementVO = stockProcessService.getAllLocationMovementById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"LocationMovement information receive failed By Id", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateLocationMovement")
	public ResponseEntity<ResponseDTO> createUpdateLocationMovement(
			@RequestBody LocationMovementDTO locationMovementDTO) {
		String methodName = "createUpdateLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> locationMovementVO = stockProcessService
					.createUpdateLocationMovement(locationMovementDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, locationMovementVO.get("message"));
			responseObjectsMap.put("locationMovementVO", locationMovementVO.get("locationMovementVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getBinFromStockForLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getBinFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getBinFromStockForLocationMovement(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoAndPartDescFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String finYear,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin) {

		String methodName = "getPartNoAndPartDescFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getPartNoAndPartDescFromStockForLocationMovement(orgId, finYear, branch,
					branchCode, client, bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and PartDesc from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDesc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String finYear,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin,
			@RequestParam(required = false) String partNo, @RequestParam(required = false) String partDesc,
			@RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(orgId, finYear,
					branch, branchCode, client, bin, partNo, partDesc, sku);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo and BatchNo from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo and BatchNo from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getLocationMovementDocId")
	public ResponseEntity<ResponseDTO> getLocationMovementDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getLocationMovementDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = stockProcessService.getLocationMovementDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"LocationMovement Docid information retrieved successfully");
			responseObjectsMap.put("locationMovementDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve LocationMovement Docid information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	DeKitting
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
			deKittingVO = stockProcessService.getAllDeKitting(orgId, finYear, branch, branchCode, client, warehouse);
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

	@GetMapping("/getAllDeKittingById")
	public ResponseEntity<ResponseDTO> getAllDeKittingById(@RequestParam(required = false) Long id) {
		String methodName = "getAllDeKittingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeKittingVO deKittingVO = new DeKittingVO();
		try {
			deKittingVO = stockProcessService.getAllDeKittingById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseDTO = createServiceResponseError(responseObjectsMap, "DeKitting information receive failed By Id",
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
			Map<String, Object> deKittingVO = stockProcessService.createUpdateDeKitting(deKittingDTO);
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
			mapp = stockProcessService.getDeKittingDocId(orgId, finYear, branch, branchCode, client);
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

	@GetMapping("/getPartNoFromStockForDeKitting")
	public ResponseEntity<ResponseDTO> getPartNoFromStockForDeKitting(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getPartNoFromStockForDeKitting()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> de = new ArrayList<>();

		try {
			de = stockProcessService.getPartNoFromStockForDeKitting(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingDetailsVO", de);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve partNo from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartDescAndSkuFromStockForDeKitting")
	public ResponseEntity<ResponseDTO> getPartNoFromStockForDeKitting(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String partNo) {

		String methodName = "getPartDescAndSkuFromStockForDeKitting()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> de = new ArrayList<>();

		try {
			de = stockProcessService.getPartDescAndSkuFromStockForDeKitting(orgId, finYear, branch, branchCode, client,
					partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartDesc and Sku from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingDetailsVO", de);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve partDesc and sku from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinFromStockForDeKitting")
	public ResponseEntity<ResponseDTO> getBinFromStockForDeKitting(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getBinFromStockForDeKitting()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> de = new ArrayList<>();

		try {
			de = stockProcessService.getBinFromStockForDeKitting(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All Bin from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingDetailsVO", de);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String finYear,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin,
			@RequestParam(required = false) String partNo, @RequestParam(required = false) String partDesc,
			@RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKitting(orgId,
					finYear, branch, branchCode, client, bin, partNo, partDesc, sku);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo and BatchNo from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo and BatchNo from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
