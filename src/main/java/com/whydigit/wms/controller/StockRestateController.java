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
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.StockRestateDTO;
import com.whydigit.wms.entity.StockRestateVO;
import com.whydigit.wms.service.StockRestateService;

@RestController
@RequestMapping("/api/stockRestate")
public class StockRestateController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(StockRestateController.class);

	@Autowired
	StockRestateService stockRestateService;

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
			stockRestateVO = stockRestateService.getAllStockRestate(orgId, finYear, branch, branchCode, client,
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
			stockRestateVO = stockRestateService.getStockRestateById(id);
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
			mapp = stockRestateService.getStockRestateDocId(orgId, finYear, branch, branchCode, client);
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
			Map<String, Object> stockRestateVO = stockRestateService.createStockRestate(stockRestateDTO);
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
			fromBinDetails = stockRestateService.getfromBinForStockRestate(orgId, branchCode, warehouse, client,
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
			partNoDetails = stockRestateService.getPartNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
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
			grnNoDetails = stockRestateService.getGrnNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
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
			batchNoDetails = stockRestateService.getBatchNoDetailsForStockRestate(orgId, branchCode, warehouse, client,
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
			fromQty = stockRestateService.getFromQtyForStockRestate(orgId, branchCode, warehouse, client,
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
			toBinDetails = stockRestateService.getToBinDetailsForStockRestate(orgId, branchCode, warehouse, client);
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
			@RequestParam String tranferFromFlag, @RequestParam String tranferToFlag,
			@RequestParam(required = false) String entryNo) {
		String methodName = "getFillGridDetailsForStockRestate()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> fillGridDetails = new ArrayList<>();

		try {
			fillGridDetails = stockRestateService.getFillGridDetailsForStockRestate(orgId, branchCode, warehouse,
					client, tranferFromFlag, tranferToFlag, entryNo);
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

	@PostMapping("/ExcelUploadForStockRestate")
	public ResponseEntity<ResponseDTO> ExcelUploadForStockRestate(@RequestParam MultipartFile[] files,
			com.whydigit.wms.dto.CustomerAttachmentType type, @RequestParam(required = false) Long orgId,
			@RequestParam(required = false) String createdBy, String customer, String client, String finYear,
			String branch, String branchCode, String warehouse) {
		String methodName = "ExcelUploadForStockRestate()";
		int totalRows = 0;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		int successfulUploads = 0;
		ResponseDTO responseDTO = null;
		try {
			// Call service method to process Excel upload
			stockRestateService.ExcelUploadForStockRestate(files, type, orgId, createdBy, customer, client, finYear,
					branch, branchCode, warehouse);

			// Retrieve the counts after processing
			totalRows = stockRestateService.getTotalRows(); // Get total rows processed
			successfulUploads = stockRestateService.getSuccessfulUploads(); // Get successful uploads count
			// Construct success response
			responseObjectsMap.put("statusFlag", "Ok");
			responseObjectsMap.put("status", true);
			responseObjectsMap.put("totalRows", totalRows);
			responseObjectsMap.put("successfulUploads", successfulUploads);
			Map<String, Object> paramObjectsMap = new HashMap<>();
			paramObjectsMap.put("message", "Excel Upload For srs successful");
			responseObjectsMap.put("paramObjectsMap", paramObjectsMap);
			responseDTO = createServiceResponse(responseObjectsMap);

		} catch (Exception e) {

			String errorMsg = e.getMessage();
			LOGGER.error(CommonConstant.EXCEPTION, methodName, e);
			responseObjectsMap.put("statusFlag", "Error");
			responseObjectsMap.put("status", false);
			responseObjectsMap.put("errorMessage", errorMsg);

			responseDTO = createServiceResponseError(responseObjectsMap, "Excel Upload For Srs Failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createStockFreeze")
	public ResponseEntity<ResponseDTO> createStockFreeze(@RequestParam Long orgId, @RequestParam String branch,
			@RequestParam String branchCode, @RequestParam String client, @RequestParam String freezeStatus) {
		String methodName = "createStockFreeze()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			String freezeMessage = stockRestateService.stockFreeze(orgId, branch, branchCode, client, freezeStatus);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, freezeMessage);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getStockFreezeStatus")
	public ResponseEntity<ResponseDTO> getStockFreezeStatus(@RequestParam Long orgId, @RequestParam String branch,
			@RequestParam String branchCode, @RequestParam String client) {
		String methodName = "getStockFreezeStatus()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			boolean freezeStatus = stockRestateService.getStockFreezeStatus(orgId, branch, branchCode, client);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, freezeStatus);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	
	

}
