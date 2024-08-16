package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.GatePassInDTO;
import com.whydigit.wms.dto.GrnDTO;
import com.whydigit.wms.dto.LocationMovementDTO;
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.SalesReturnDTO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.service.InwardTransactionService;
import com.whydigit.wms.service.WarehouseMasterService;

@RestController
@RequestMapping("/api/inward")
public class InwardTransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionController.class);

	@Autowired
	InwardTransactionService inwardTransactionService;

	@Autowired
	WarehouseMasterService warehouseMasterService;

//	@GetMapping("/getAllGatePassNumberByClientAndBranch")
//	public ResponseEntity<ResponseDTO> getAllGatePassNumberByClientAndBranch(@RequestParam Long orgid,
//			@RequestParam String client, @RequestParam String customer, @RequestParam String branchcode) {
//		String methodName = "getAllGatePassNumberByClientAndBranch()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> grn = new HashSet<>();
//		try {
//			grn = inwardTransactionService.getAllGatePassNumberByClientAndBranch(orgid, client, customer, branchcode);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String, String>> formattedParameters = formatParameter(grn);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//					"getAllGatePassNumberByClientAndBranch information get successfully");
//			responseObjectsMap.put("getAllGatePassNumberByClientAndBranch", formattedParameters);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap,
//					"getAllGatePassNumberByClientAndBranch information receive failed", errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formatParameter(Set<Object[]> grn) {
//		List<Map<String, String>> formattedParameters = new ArrayList<>();
//		for (Object[] parameters : grn) {
//			Map<String, String> param = new HashMap<>();
//			param.put("entryno", parameters[0].toString());
//			param.put("gatepassid", parameters[1].toString());
//			param.put("gatepassdate", parameters[2].toString());
//			param.put("suppliershortname", parameters[3].toString());
//			param.put("supplier", parameters[4].toString());
//			param.put("modeofshipment", parameters[5].toString());
//			param.put("carrier", parameters[6].toString());
//			param.put("vehicletype", parameters[7].toString());
//			param.put("contact", parameters[8].toString());
//			param.put("drivername", parameters[9].toString());
//			param.put("securityname", parameters[10].toString());
//			param.put("goodsdescription", parameters[11].toString());
//			param.put("vehicleno", parameters[12].toString());
//			formattedParameters.add(param);
//		}
//		return formattedParameters;
//	}

	@GetMapping("/getGatePassDetailsByGatePassNo")
	public ResponseEntity<ResponseDTO> getGatePassDetailsByGatePassNo(@RequestParam Long orgid,
			@RequestParam String client, @RequestParam String entryno, @RequestParam Long docid,
			@RequestParam String branchcode) {
		String methodName = "getGatePassDetailsByGatePassNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> grn = new HashSet<>();
		try {
			grn = inwardTransactionService.getGatePassDetailsByGatePassNo(orgid, client, entryno, docid, branchcode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> formattParameters = formateeParameter(grn);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"getGatePassDetailsByGatePassNo information get successfully");
			responseObjectsMap.put("getGatePassDetailsByGatePassNo", formattParameters);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"getGatePassDetailsByGatePassNo information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formateeParameter(Set<Object[]> grn) {
		List<Map<String, String>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : grn) {
			Map<String, String> param = new HashMap<>();
			param.put("lrnohaw", parameters[0].toString());
			param.put("invoiceno", parameters[1].toString());
			param.put("invoicedate", parameters[2].toString());
			param.put("partno", parameters[3].toString());
			param.put("partdesc", parameters[4].toString());
			param.put("sku", parameters[5].toString());
			param.put("invqty", parameters[6].toString());
			param.put("recqty", parameters[7].toString());
			param.put("damageqty", parameters[8].toString());
			param.put("batchno", parameters[9].toString());
			param.put("weight", parameters[10].toString());
			param.put("rate", parameters[11].toString());
			param.put("rowno", parameters[12].toString());
			formattedParameters.add(param);
		}
		return formattedParameters;
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
			grnVO = inwardTransactionService.getAllGrn(orgId, finYear, branch, branchCode, client, warehouse);
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
			grnDocid = inwardTransactionService.getGRNdocid(orgId, finYear, branchCode, client, screenCode);
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
			grnVO = inwardTransactionService.getGrnById(id);
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
			Map<String, Object> grnVO = inwardTransactionService.createUpdateGrn(grnDTO);
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

	// GatePassIn

	@PutMapping("/createUpdateGatePassIn")
	public ResponseEntity<ResponseDTO> createUpdateGatePassIn(@RequestBody GatePassInDTO gatePassInDTO) {
		String methodName = "createUpdateGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdGatePassInVO = inwardTransactionService.createUpdateGatePassIn(gatePassInDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdGatePassInVO.get("message"));
			responseObjectsMap.put("GatePassInVO", createdGatePassInVO.get("gatePassInVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/gatePassIn")
	public ResponseEntity<ResponseDTO> getAllGatePassIn(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String finYear, @RequestParam String client) {
		String methodName = "getAllGatePassIn()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GatePassInVO> gatePassInVO = new ArrayList<>();
		try {
			gatePassInVO = inwardTransactionService.getAllGatePassIn(orgId, branchCode, finYear, client);
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
			gatePassInVO = inwardTransactionService.getGatepassInNoForPendingGRN(orgId, branchCode, finYear, client);
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
			gatePassInDetailsVO = inwardTransactionService.getGatepassInGridDetailsForPendingGRN(orgId, finYear,
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

	@GetMapping("/getGatePassInDocId")
	public ResponseEntity<ResponseDTO> getGatePassInDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {

		String methodName = "getGatePassInDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";

		try {
			mapp = inwardTransactionService.getGatePassInDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrderDocId information retrieved successfully");
			responseObjectsMap.put("GatePassInDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Failed to retrieve BuyerOrderDocId information", errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Get ALL ModeOfShipment

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

	private List<Map<String, String>> formattParameter(Set<Object[]> gatepass) {
		List<Map<String, String>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : gatepass) {
			Map<String, String> param = new HashMap<>();
			param.put("partno", parameters[0].toString());
			param.put("partdesc", parameters[1].toString());
			param.put("sku", parameters[2].toString());
			param.put("ssku", parameters[3].toString());
			formattedParameters.add(param);
		}
		return formattedParameters;
	}

	// PutAway
	@GetMapping("/getAllPutAway")
	public ResponseEntity<ResponseDTO> getAllPutAway(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client,
			@RequestParam String warehouse) {
		String methodName = "getAllPutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PutAwayVO> putAwayVO = new ArrayList<>();
		try {
			putAwayVO = inwardTransactionService.getAllPutAway(orgId, finYear, branch, branchCode, client, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway information get successfully");
			responseObjectsMap.put("PutAwayVO", putAwayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPutAwayById")
	public ResponseEntity<ResponseDTO> getPutAwayById(@RequestParam(required = false) Long id) {
		String methodName = "getPutAwayById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		PutAwayVO putAwayVO = new PutAwayVO();

		try {
			putAwayVO = inwardTransactionService.getPutAwayById(id);
			if (putAwayVO != null) {
				responseObjectsMap.put("putAwayVO", putAwayVO);
				responseDTO = createServiceResponseMsg(responseObjectsMap,
						" PutAway information retrieved successfully.");
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, " PutAway information not found.",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " PutAway information retrieval failed.",
					errorMsg);
		}

		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getGrnForPutaway")
	public ResponseEntity<ResponseDTO> getGrnForPutaway(@RequestParam Long orgId, @RequestParam String client,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String warehouse) {
		String methodName = "getGrnForPutaway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GrnVO> grnVO = new ArrayList<>();
		try {
			grnVO = inwardTransactionService.getGrnNoForPutAway(orgId, client, branch, branchCode, warehouse);
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

	@GetMapping("/getPutAwayDocId")
	public ResponseEntity<ResponseDTO> getPutAwayDocId(@RequestParam Long orgId, @RequestParam String finYear,
			@RequestParam String branch, @RequestParam String branchCode, @RequestParam String client) {
		String methodName = "getPutAwayDocId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String mapp = "";
		try {
			mapp = inwardTransactionService.getPutAwayDocId(orgId, finYear, branch, branchCode, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All PutAway information retrieved successfully");
			responseObjectsMap.put("PutAwayDocId", mapp);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PutAway information",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdatePutAway")
	public ResponseEntity<ResponseDTO> createUpdatePutAway(@RequestBody PutAwayDTO putAwayDTO) {
		String methodName = "createUpdatePutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> putAwayVO = inwardTransactionService.createUpdatePutAway(putAwayDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, putAwayVO.get("message"));
			responseObjectsMap.put("putAwayVO", putAwayVO.get("putAwayVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getPutawayGridDetails")
	public ResponseEntity<ResponseDTO> getPutawayGridDetails(@RequestParam Long orgId, @RequestParam String branchCode,
			@RequestParam String warehouse, @RequestParam String client, @RequestParam String grnNo,
			@RequestParam String binType, @RequestParam String binClass, @RequestParam String binPick) {
		String methodName = "getPutawayGridDetails()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> gridDetails = new ArrayList<>();
		try {
			gridDetails = inwardTransactionService.getFillGridDetailsForPutaway(orgId, branchCode, warehouse, client, grnNo, binType, binClass, binPick);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutaWay Grid information get successfully");
			responseObjectsMap.put("gridDetails", gridDetails);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PutaWay Grid information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

}
