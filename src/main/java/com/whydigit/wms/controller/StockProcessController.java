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
import com.whydigit.wms.dto.CycleCountDTO;
import com.whydigit.wms.dto.DeKittingDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.entity.CodeConversionVO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.DeKittingVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.entity.StockRestateVO;
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

	@GetMapping("/getSalesReturnById")
	public ResponseEntity<ResponseDTO> getSalesReturnById(@RequestParam(required = false) Long id) {
		String methodName = "getSalesReturnById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SalesReturnVO salesReturnVO = new SalesReturnVO();

		try {
			salesReturnVO = stockProcessService.getSalesReturnById(id);
			if (salesReturnVO != null) {
				responseObjectsMap.put("salesReturnVO", salesReturnVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" SalesReturn information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " SalesReturn information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " SalesReturn information retrieval failed.",
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
	public ResponseEntity<ResponseDTO> getSalesReturnFillGridDetails(@RequestParam(required = false) String docId,
			@RequestParam(required = false) String client, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branchCode) {

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

	@GetMapping("/getLocationMovementById")
	public ResponseEntity<ResponseDTO> getLocationMovementById(@RequestParam(required = false) Long id) {
		String methodName = "getLocationMovementById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LocationMovementVO locationMovementVO = new LocationMovementVO();

		try {
			locationMovementVO = stockProcessService.getLocationMovementById(id);
			if (locationMovementVO != null) {
				responseObjectsMap.put("locationMovementVO", locationMovementVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" LocationMovement information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " LocationMovement information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					" LocationMovement information retrieval failed.", errorMsg);
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
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client) {

		String methodName = "getBinFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getBinFromStockForLocationMovement(orgId, branch, branchCode, client);
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

	@GetMapping("/getToBinFromLocationStatusForLocationMovement")
	public ResponseEntity<ResponseDTO> getToBinFromLocationStatusForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String warehouse) {

		String methodName = "getToBinFromLocationStatusForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getToBinFromLocationStatusForLocationMovement(orgId, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All ToBin from LocationStatus retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve ToBin from LocationStatus information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllForLocationMovementDetailsFillGrid")
	public ResponseEntity<ResponseDTO> getAllForLocationMovementDetailsFillGrid(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getAllForLocationMovementDetailsFillGrid()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getAllForLocationMovementDetailsFillGrid(orgId, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All bin details from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve all bin details from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin, @RequestParam(required = false) String partNo,
			@RequestParam(required = false) String partDesc, @RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getGrnNoAndBatchAndBatchDateAndLotNoFromStockForLocationMovement(orgId, branch,
					branchCode, client, bin, partNo, partDesc, sku);
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

	@GetMapping("/getPartNoAndPartDescFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescFromStockForLocationMovement(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin) {

		String methodName = "getPartNoAndPartDescFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getPartNoAndPartDescFromStockForLocationMovement(orgId, branch, branchCode,
					client, bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and Part Desc from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementDetailsVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDEsc from Stock information", errorMsg);
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

	@GetMapping("/getAvlQtyFromStockForLocationMovement")
	public ResponseEntity<ResponseDTO> getAvlQtyFromStockForLocationMovement(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin,
			@RequestParam(required = false) String partDesc, @RequestParam(required = false) String sku,
			@RequestParam(required = false) String partNo, @RequestParam(required = false) String grnNo,
			@RequestParam(required = false) String lotNo) {

		String methodName = "getAvlQtyFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int mov = 0;

		try {
			mov = stockProcessService.getAvlQtyFromStockForLocationMovement(orgId, branch, branchCode, client, bin,
					partDesc, sku, partNo, grnNo, lotNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"AvlQty from Stock information retrieved successfully");
			responseObjectsMap.put("locationMovementVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve AvlQty from Stock information", errorMsg);
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

	@GetMapping("/getDeKittingById")
	public ResponseEntity<ResponseDTO> getDeKittingById(@RequestParam(required = false) Long id) {
		String methodName = "getDeKittingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DeKittingVO deKittingVO = new DeKittingVO();

		try {
			deKittingVO = stockProcessService.getDeKittingById(id);
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

	@GetMapping("/getPartNoFromStockForDeKittingParent")
	public ResponseEntity<ResponseDTO> getPartNoFromStockForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {
		String methodName = "getPartNoFromStockForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> de = new ArrayList<>();
		try {
			de = stockProcessService.getPartNoFromStockForDeKittingParent(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and PartDesc from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingDetailsVO", de);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and PartDesc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinFromStockForDeKittingParent")
	public ResponseEntity<ResponseDTO> getBinFromStockForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getBinFromStockForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> de = new ArrayList<>();

		try {
			de = stockProcessService.getBinFromStockForDeKittingParent(orgId, finYear, branch, branchCode, client);
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

	@GetMapping("/getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent")
	public ResponseEntity<ResponseDTO> getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKititngParent(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String finYear,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client, @RequestParam(required = false) String bin,
			@RequestParam(required = false) String partNo, @RequestParam(required = false) String partDesc,
			@RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();

		try {
			mov = stockProcessService.getGrnNoAndBatchAndBatchDateAndLotNoAndExpDateFromStockForDeKittingParent(orgId,
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

	@GetMapping("/getAvlQtyFromStockForDeKittingParent")
	public ResponseEntity<ResponseDTO> getAvlQtyFromStockForDeKittingParent(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String finYear, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin, @RequestParam(required = false) String partDesc,
			@RequestParam(required = false) String sku, @RequestParam(required = false) String partNo,
			@RequestParam(required = false) String grnNo, @RequestParam(required = false) String lotNo) {

		String methodName = "getAvlQtyFromStockForDeKittingParent()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int mov = 0;
		try {
			mov = stockProcessService.getAvlQtyFromStockForDeKittingParent(orgId, finYear, branch, branchCode, client,
					bin, partDesc, sku, partNo, grnNo, lotNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"AvlQty from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingParentVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve AvlQty from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {

		String methodName = "getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getPartNoAndPartDescAndSkuFromMaterialForDeKittingChild(orgId, branch, branchCode,
					client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All PartNo and sku from Stock information retrieved successfully");
			responseObjectsMap.put("deKittingChildVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo and sku from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// StockRestate
	@GetMapping("/getAllStockRestate")
	public ResponseEntity<ResponseDTO> getAllStockRestate(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String warehouse) {
		String methodName = "getAllStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StockRestateVO> stockRestateVO = new ArrayList<>();
		try {
			stockRestateVO = stockProcessService.getAllStockRestate(orgId, finYear, branch, branchCode, client,
					warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "StockRestate information get successfully");
			responseObjectsMap.put("stockRestateVO", stockRestateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "StockRestate information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockRestateById")
	public ResponseEntity<ResponseDTO> getStockRestateById(@RequestParam(required = false) Long id) {
		String methodName = "getStockRestateById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		StockRestateVO stockRestateVO = new StockRestateVO();

		try {
			stockRestateVO = stockProcessService.getStockRestateById(id);
			if (stockRestateVO != null) {
				responseObjectsMap.put("stockRestateVO", stockRestateVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" StockRestate information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " StockRestate information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " StockRestate information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getStockRestateDocId")
	public ResponseEntity<ResponseDTO> getStockRestateDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getStockRestateDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = stockProcessService.getStockRestateDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All StockRestate information retrieved successfully");
			responseObjectsMap.put("StockRestateDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve StockRestate information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createStockRestate")
	public ResponseEntity<ResponseDTO> createStockRestate(@RequestBody StockRestateDTO stockRestateDTO) {
		String methodName = "createStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> stockRestateVO = stockProcessService.createStockRestate(stockRestateDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, stockRestateVO.get("message"));
			responseObjectsMap.put("stockRestateVO", stockRestateVO.get("restateVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFromBinDetailsForStockRestate")
	public ResponseEntity<ResponseDTO> getFromBinDetailsForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag) {
		String methodName = "getBinFromStockForLocationMovement()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fromBinDetails = new ArrayList<>();

		try {
			fromBinDetails = stockProcessService.getfromBinForStockRestate(orgId, branchCode, warehouse, client,
					tranferFromFlag);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Bin Details from Stock information retrieved successfully");
			responseObjectsMap.put("fromBinDetails", fromBinDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPartNoDetailsForStockRestate")
	public ResponseEntity<ResponseDTO> getPartNoDetailsForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag, @RequestParam String fromBin) {
		String methodName = "getPartNoDetailsForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> partNoDetails = new ArrayList<>();

		try {
			partNoDetails = stockProcessService.getPartNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
					tranferFromFlag, fromBin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PartNo Details from Stock information retrieved successfully");
			responseObjectsMap.put("partNoDetails", partNoDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve PartNo from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnNoDetailsForStockRestate")
	public ResponseEntity<ResponseDTO> getGrnNoDetailsForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag, @RequestParam String fromBin, @RequestParam String partNo) {
		String methodName = "getGrnNoDetailsForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> grnNoDetails = new ArrayList<>();

		try {
			grnNoDetails = stockProcessService.getGrnNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
					tranferFromFlag, fromBin, partNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"GrnNo Details from Stock information retrieved successfully");
			responseObjectsMap.put("grnNoDetails", grnNoDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve GrnNo from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getbatchNoDetailsForStockRestate")
	public ResponseEntity<ResponseDTO> getbatchNoDetailsForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag, @RequestParam String fromBin, @RequestParam String partNo,
			@RequestParam String grnNo) {
		String methodName = "getbatchNoDetailsForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> batchNoDetails = new ArrayList<>();

		try {
			batchNoDetails = stockProcessService.getBatchNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
					tranferFromFlag, fromBin, partNo, grnNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"BatchNo Details from Stock information retrieved successfully");
			responseObjectsMap.put("batchNoDetails", batchNoDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve BatchNo from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFromQtyForStockRestate")
	public ResponseEntity<ResponseDTO> getFromQtyForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag, @RequestParam String fromBin, @RequestParam String partNo,
			@RequestParam String grnNo, @RequestParam String batchNo) {
		String methodName = "getFromQtyForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		int fromQty = 0;
		try {
			fromQty = stockProcessService.getFromQtyForStockRestate(orgId, branchCode, warehouse, client,
					tranferFromFlag, fromBin, partNo, grnNo, batchNo);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"FromQty Details from Stock information retrieved successfully");
			responseObjectsMap.put("fromQty", fromQty);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve FromQty from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getToBinDetails")
	public ResponseEntity<ResponseDTO> getToBinDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String warehouse, @RequestParam String client, @RequestParam String tranferFromFlag) {
		String methodName = "getToBinDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> toBinDetails = new ArrayList<>();

		try {
			toBinDetails = stockProcessService.getToBinDetailsForStockRestate(orgId, branchCode, warehouse, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Bin Details from Stock information retrieved successfully");
			responseObjectsMap.put("toBinDetails", toBinDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getFillGridDetailsForStockRestate")
	public ResponseEntity<ResponseDTO> getFillGridDetailsForStockRestate(@RequestParam Long orgId,
			@RequestParam String branchCode, @RequestParam String warehouse, @RequestParam String client,
			@RequestParam String tranferFromFlag, @RequestParam String tranferToFlag) {
		String methodName = "getFillGridDetailsForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fillGridDetails = new ArrayList<>();

		try {
			fillGridDetails = stockProcessService.getFillGridDetailsForStockRestate(orgId, branchCode, warehouse,
					client, tranferFromFlag, tranferToFlag);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"fill Grid Details  from Stock information retrieved successfully");
			responseObjectsMap.put("fillGridDetails", fillGridDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve fill Grid Details  from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// CYCLECOUNT

	@PutMapping("/createUpdateCycleCount")
	public ResponseEntity<ResponseDTO> createUpdateCycleCount(@RequestBody CycleCountDTO cycleCountDTO) {
		String methodName = "createUpdateCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> cycleCount = stockProcessService.createUpdateCycleCount(cycleCountDTO);
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
			mapp = stockProcessService.getCycleCountInDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kitting information retrieved successfully");
			responseObjectsMap.put("CycleCountInDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Kitting to retrieve BuyerOrderDocId information", errorMsg);
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
			cycleCountVOs = stockProcessService.getAllCycleCount(orgId, client, branch, branchCode, finYear, warehouse);
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
			cycleCountVO = stockProcessService.getCycleCountById(id).orElse(null);
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
			@RequestParam(required = true) String warehouse) {
		String methodName = "getCycleCountGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> cycleCountGrid = new ArrayList<Map<String, Object>>();
		try {
			cycleCountGrid = stockProcessService.getCycleCountGridDetails(orgId, branchCode, client, warehouse);
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

}
