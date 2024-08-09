package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

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
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.GatePassInVO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.LocationMovementVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.entity.SalesReturnVO;
import com.whydigit.wms.service.InwardTransactionService;

@RestController
@RequestMapping("/api/inward")
public class InwardTransactionController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionController.class);

	@Autowired
	InwardTransactionService inwardTransactionService;

	

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

	@GetMapping("/getAllModeOfShipment")
	public ResponseEntity<ResponseDTO> getAllModeOfShipment() {
		String methodName = "getAllModeOfShipment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CarrierVO> carrierVO = new ArrayList<>();
		try {
			carrierVO = inwardTransactionService.getAllModeOfShipment();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ModeOfShipment information get successfully");
			responseObjectsMap.put("CarrierVO", carrierVO);
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

	@GetMapping("/getActiveShipment")
	public ResponseEntity<ResponseDTO> getActiveShipment(@RequestParam(required = true) String shipmentMode) {
		String methodName = "getActiveShipment()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CarrierVO> carrierVO = new ArrayList<>();
		try {
			carrierVO = inwardTransactionService.getActiveShipment(shipmentMode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "ActiveShipment information get successfully");
			responseObjectsMap.put("CarrierVO", carrierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "ActiveShipment information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// PutAway
	@GetMapping("/getAllPutAway")
	public ResponseEntity<ResponseDTO> getAllPutAway(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client,@RequestParam String warehouse) {
		String methodName = "getAllPutAway()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<PutAwayVO> putAwayVO = new ArrayList<>();
		try {
			putAwayVO = inwardTransactionService.getAllPutAway(orgId,finYear,branch,branchCode,client,warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway information get successfully");
			responseObjectsMap.put("PutAwayVO", putAwayVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "PutAway information receive failed", errorMsg);
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
                responseDTO = createServiceResponseMsg(responseObjectsMap, " PutAway information retrieved successfully.");
            } else {
                responseDTO = createServiceResponseError(responseObjectsMap, " PutAway information not found.", errorMsg);
            }
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, " PutAway information retrieval failed.", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
	
	
	@GetMapping("/getPutAwayDocId")
    public ResponseEntity<ResponseDTO> getPutAwayDocId(
    		@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client) {
        
        String methodName = "getPutAwayDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        String mapp="";
        
        try {
            mapp = inwardTransactionService.getPutAwayDocId(orgId,finYear,branch, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All PutAway information retrieved successfully");
            responseObjectsMap.put("PutAwayDocId", mapp);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PutAway information", errorMsg);
        }
        
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
	
	
	

	private List<Map<String, String>> formatParameterr(Set<Object[]> putaway) {
		List<Map<String, String>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : putaway) {
			Map<String, String> param = new HashMap<>();
			param.put("grnno", getStringValue(parameters[0]));
			param.put("grndate", getStringValue(parameters[1]));
			param.put("entryno", getStringValue(parameters[3]));
			param.put("entrydate", getStringValue(parameters[4]));
			param.put("vehicleno", getStringValue(parameters[5]));
			param.put("drivername", getStringValue(parameters[6]));
			param.put("contact", getStringValue(parameters[7]));
			param.put("goodsdescripition", getStringValue(parameters[8]));
			param.put("modeofshipment", getStringValue(parameters[9]));
			param.put("vehicletype", getStringValue(parameters[10]));
			param.put("securityname", getStringValue(parameters[11]));
			param.put("putqty", getStringValue(parameters[12]));
			formattedParameters.add(param);
		}
		return formattedParameters;
	}

	private String getStringValue(Object obj) {
		return obj != null ? obj.toString() : " ";
	}

//	@PostMapping("/putaway")
//	public ResponseEntity<ResponseDTO> createPutAway(@RequestBody PutAwayDTO putAwayDTO) {
//		String methodName = "createPutAway()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		try {
//			PutAwayVO createdPutAwayVO = inwardTransactionService.createPutAway(putAwayDTO);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PutAway created successfully");
//			responseObjectsMap.put("PutAwayVO", createdPutAwayVO);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//			responseDTO = createServiceResponseError(responseObjectsMap, " PutAway & PutAwayCode already Exist ",
//					errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

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
			salesReturnVO = inwardTransactionService.getAllSalesReturn(orgId, finYear, branch, branchCode, client,
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
			salesReturnVO = inwardTransactionService.getAllSalesReturnById(id);
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

	@PutMapping("/updateCreateSalesReturn")
	public ResponseEntity<ResponseDTO> updateCreateSalesReturn(@Valid @RequestBody SalesReturnDTO salesReturnDTO) {
		String methodName = "updateCreateSalesReturn()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			SalesReturnVO salesReturnVO = inwardTransactionService.updateCreateSalesReturn(salesReturnDTO);
			boolean isUpdate = salesReturnDTO.getId() != null;

			if (salesReturnVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						isUpdate ? "SalesReturn updated successfully" : "SalesReturn created successfully");
				responseObjectsMap.put("salesReturnVO", salesReturnVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = isUpdate ? "SalesReturn not found for ID: " + salesReturnDTO.getId()
						: "SalesReturn creation failed";
				responseDTO = createServiceResponseError(responseObjectsMap,
						isUpdate ? "SalesReturn update failed" : "SalesReturn creation failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			boolean isUpdate = salesReturnDTO.getId() != null;
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					isUpdate ? "SalesReturn update failed" : "SalesReturn creation failed", errorMsg);
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
			mapp = inwardTransactionService.getSalesReturnFillGridDetails(docId, client, orgId, branchCode);
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
			locationMovementVO = inwardTransactionService.getAllLocationMovement(orgId, finYear, branch, branchCode,
					client, warehouse);
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
			locationMovementVO = inwardTransactionService.getAllLocationMovementById(id);
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

	@PutMapping("/updateCreateLocationMovement")
	public ResponseEntity<ResponseDTO> updateCreateLocationMovement(
			@Valid @RequestBody LocationMovementDTO locationMovementDTO) {
		String methodName = "updateCreateLocationMovement()";

		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;

		try {
			LocationMovementVO locationMovementVO = inwardTransactionService
					.updateCreateLocationMovement(locationMovementDTO);
			boolean isUpdate = locationMovementDTO.getId() != null;

			if (locationMovementVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						isUpdate ? "LocationMovement updated successfully" : "LocationMovement created successfully");
				responseObjectsMap.put("locationMovementVO", locationMovementVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = isUpdate ? "LocationMovement not found for ID: " + locationMovementDTO.getId()
						: "LocationMovement creation failed";
				responseDTO = createServiceResponseError(responseObjectsMap,
						isUpdate ? "LocationMovement update failed" : "LocationMovement creation failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			boolean isUpdate = locationMovementDTO.getId() != null;
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					isUpdate ? "LocationMovement update failed" : "LocationMovement creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
