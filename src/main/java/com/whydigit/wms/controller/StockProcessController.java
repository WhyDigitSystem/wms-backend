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

	@GetMapping("/getPartNoAndPartDescFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getPartNoAndPartDescFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin) {

		String methodName = "getPartNoAndPartDescFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getPartNoAndPartDescFromStockForCodeConversion(orgId, branch, branchCode, client,
					bin);
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

	@GetMapping("/getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin, @RequestParam(required = false) String partNo,
			@RequestParam(required = false) String partDesc, @RequestParam(required = false) String sku) {

		String methodName = "getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getGrnNoAndBinTypeAndBatchAndBatchDateAndLotNoFromStockForCodeConversion(orgId,
					branch, branchCode, client, bin, partNo, partDesc, sku);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All GrnNo and BatchNo and Bintype and Batchdate and lotno from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieveGrnNo and BatchNo and Bintype and Batchdate and lotno from Stock information",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getBinFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getBinFromStockForCodeConversion(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client) {
		String methodName = "getBinFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getBinFromStockForCodeConversion(orgId, branch, branchCode, client);
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
					"Failed to retrieve Bin from Stock information", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getAllFillGridFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getAllFillGridFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client) {
		String methodName = "getAllFillGridFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getAllFillGridFromStockForCodeConversion(orgId, branch, branchCode, client);
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

	@GetMapping("/getCpartNoAndCpartDescFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getCpartNoAndCpartDescFromStockForCodeConversion(
			@RequestParam(required = false) Long orgId, @RequestParam(required = false) String branch,
			@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
			@RequestParam(required = false) String bin) {

		String methodName = "getCpartNoAndCpartDescFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getCpartNoAndCpartDescFromStockForCodeConversion(orgId, branch, branchCode,
					client, bin);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All CPartNo and CPartDesc from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve CPartNo and CPartDesc from Stock information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getCBinFromStockForCodeConversion")
	public ResponseEntity<ResponseDTO> getCBinFromStockForCodeConversion(@RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String branch, @RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String client) {
		String methodName = "getCBinFromStockForCodeConversion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> mov = new ArrayList<>();
		try {
			mov = stockProcessService.getCBinFromStockForCodeConversion(orgId, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"All CBin from Stock information retrieved successfully");
			responseObjectsMap.put("codeConversionVO", mov);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve CBin from Stock information", errorMsg);
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
			avalQty = stockProcessService.getAvlQtyCodeConversion(orgId, client, branchCode, warehouse, branch, partNo,
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

}
