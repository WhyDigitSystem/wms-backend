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
import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.entity.CycleCountVO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.service.VasanthService;



@RestController
@RequestMapping("/api/vasanth")
public class VasanthController  extends BaseController{
	public static final Logger LOGGER = LoggerFactory.getLogger(VasanthController.class);
	
	@Autowired
	VasanthService vasanthService;
	
	@PutMapping("/createUpdateVasPic")
	public ResponseEntity<ResponseDTO> createUpdateVasPic(@RequestBody VasPickDTO vasPicDTO) {
	    String methodName = "createUpdateGRN()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> vasPicVO = vasanthService.createUpdateVasPic(vasPicDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, vasPicVO.get("message"));
	        responseObjectsMap.put("vasPicVO", vasPicVO.get("vasPickVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getVaspickById")
	public ResponseEntity<ResponseDTO> getVaspickById(@RequestParam(required = true) Long id) {
	    String methodName = "getVaspickById()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    VasPickVO vasPickVO = null;
	    try {
	        vasPickVO = vasanthService.getVaspickById(id).orElse(null);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }
	    if (StringUtils.isEmpty(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VasPick Details found by ID");
	        responseObjectsMap.put("vasPickVO", vasPickVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
	        errorMsg = "vasPickVO not found for ID: " + id;
	        responseDTO = createServiceResponseError(responseObjectsMap, "VasPick Details not found", errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("getVaspickGrid")
	public ResponseEntity<ResponseDTO> getVaspickGrid(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branch,@RequestParam(required = true) String branchCode,
			@RequestParam(required = true) String client,@RequestParam(required = true) String warehouse
			) {
	    String methodName = "getVaspickGrid()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    List<Map<String, Object>> vasPickGrid=new ArrayList<Map<String,Object>>();
	    try {
	    	vasPickGrid = vasanthService.getVaspickGrid(orgId,branch,branchCode,client,warehouse);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	    }
	    if (StringUtils.isEmpty(errorMsg)) {
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VaspickGrid Details found Successfull");
	        responseObjectsMap.put("VaspickGrid", vasPickGrid);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "VaspickGrid Details retrieve  failed", errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
	
	
	@GetMapping("getAllVaspick")
	public ResponseEntity<ResponseDTO> getAllVaspick(@RequestParam(required =true) Long orgId,
			@RequestParam(required =true) String branchCode,@RequestParam(required =true) String client,
	@RequestParam(required =true) String branch,@RequestParam(required =true) String finYear,@RequestParam(required =true) String warehouse) {
		String methodName = "getAllVaspick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VasPickVO>  vasPickVO = new ArrayList<VasPickVO>();
		try {
			vasPickVO = vasanthService.getAllVaspick(orgId,branchCode,client,branch,finYear,warehouse);
		} 
		catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VasPick Details information get successfully");
			responseObjectsMap.put("vasPickVO", vasPickVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "VasPick  Details information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getVasPickDocId")
    public ResponseEntity<ResponseDTO> getVasPickDocId(
    		@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client) {
        
        String methodName = "getVasPickDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        String mapp="";
        
        try {
            mapp = vasanthService.getVasPickDocId(orgId,finYear,branch, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VasPickDocId information retrieved successfully");
            responseObjectsMap.put("KittingDocId", mapp);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "VasPickDocId  retrieve failed", errorMsg);
        }
        
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
	
	

	//CYCLECOUNT	
	
	@PutMapping("/createUpdateCycleCount")
	public ResponseEntity<ResponseDTO> createUpdateCycleCount(@RequestBody CycleCountDTO cycleCountDTO) {
	    String methodName = "createUpdateCycleCount()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> cycleCount = vasanthService.createUpdateCycleCount(cycleCountDTO);
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
    public ResponseEntity<ResponseDTO> getCycleCountInDocId(
    		@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client) {
        
        String methodName = "getCycleCountInDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        String mapp="";
        
        try {
            mapp = vasanthService.getCycleCountInDocId(orgId,finYear,branch, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kitting information retrieved successfully");
            responseObjectsMap.put("CycleCountInDocId", mapp);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Kitting to retrieve BuyerOrderDocId information", errorMsg);
        }
        
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
	
	@GetMapping("getAllCycleCount")
	public ResponseEntity<ResponseDTO> getAllCycleCount(@RequestParam(required =true) Long orgId,
			@RequestParam(required =true) String client,@RequestParam(required =true) String branch,
	@RequestParam(required =true) String branchCode,@RequestParam(required =true) String finYear,@RequestParam(required =true) String warehouse) {
		String methodName = "getAllCycleCount()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CycleCountVO>  cycleCountVOs = new ArrayList<CycleCountVO>();
		try {
			cycleCountVOs = vasanthService.getAllCycleCount(orgId,client,branch,branchCode,finYear,warehouse);
		} 
		catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CycleCount Details information get successfully");
			responseObjectsMap.put("cycleCountVO", cycleCountVOs);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "VasPick  Details information receive failed", errorMsg);
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
	    	cycleCountVO = vasanthService.getCycleCountById(id).orElse(null);
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
}
