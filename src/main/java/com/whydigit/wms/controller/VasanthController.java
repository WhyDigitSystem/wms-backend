package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
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
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.entity.GatePassInVO;
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
	    String methodName = "getGatePassInById()";
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

	
	@GetMapping("getAllVaspick")
	public ResponseEntity<ResponseDTO> getAllVaspick(@RequestParam(required =true) Long orgId,
			@RequestParam(required =true) String branchCode,@RequestParam(required =true) String client,
	@RequestParam(required =true) String customer) {
		String methodName = "getAllVaspick()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<VasPickVO>  vasPickVO = new ArrayList<VasPickVO>();
		try {
			vasPickVO = vasanthService.getAllVaspick(orgId,branchCode,client,customer);
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

}