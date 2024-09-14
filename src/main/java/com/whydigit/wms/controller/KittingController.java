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
import com.whydigit.wms.dto.KittingDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.service.KittingService;

@RestController
@RequestMapping("/api/kitting")
public class KittingController extends BaseController{

	@Autowired
	KittingService kittingService;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(KittingController.class);
	
	// KITTING

    @PutMapping("/createUpdateKitting")
    public ResponseEntity<ResponseDTO> createUpdateKitting(@RequestBody KittingDTO kittingDTO) {
        String methodName = "createUpdateKitting()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        try {
            Map<String, Object> createkittingVO = kittingService.createUpdateKitting(kittingDTO);
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createkittingVO.get("message"));
            responseObjectsMap.put("kittingVO", createkittingVO.get("kittingVO"));
            responseDTO = createServiceResponse(responseObjectsMap);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
            responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getAllKitting")
    public ResponseEntity<ResponseDTO> getAllKitting(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String customer) {
        String methodName = "getAllKitting()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<KittingVO> kittingVOs = new ArrayList<>();
        try {
            kittingVOs = kittingService.getAllKitting(orgId, branchCode, client, customer);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "KittingVO Details information retrieved successfully");
            responseObjectsMap.put("kittingVOs", kittingVOs);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "KittingVO Details information retrieval failed", errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getKittingById")
    public ResponseEntity<ResponseDTO> getKittingById(@RequestParam(required = true) Long id) {
        String methodName = "getKittingById()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        KittingVO kittingVO = null;
        try {
            kittingVO = kittingService.getKittingById(id).orElse(null);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isEmpty(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "KittingVO Details found by ID");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            errorMsg = "KittingVO not found for ID: " + id;
            responseDTO = createServiceResponseError(responseObjectsMap, "KittingVO Details not found", errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getKittingInDocId")
    public ResponseEntity<ResponseDTO> getKittingInDocId(
            @RequestParam Long orgId, @RequestParam String finYear, @RequestParam String branch,
            @RequestParam String branchCode, @RequestParam String client) {

        String methodName = "getKittingInDocId()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        String mapp = "";

        try {
            mapp = kittingService.getKittingInDocId(orgId, finYear, branch, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }

        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Kitting information retrieved successfully");
            responseObjectsMap.put("KittingDocId", mapp);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve Kitting information", errorMsg);
        }

        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

	@GetMapping("/getPartNOByChild")
	public ResponseEntity<ResponseDTO> getPartNOByChild(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse) {
		String methodName = "getPartNOByChild()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> kittingVO = new ArrayList<>();
		try {
			kittingVO = kittingService.getPartNOByChild(orgId, branchCode, client, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"PartNOByChild Details information retrieved successfully");
			responseObjectsMap.put("kittingVO", kittingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNOByChild Details",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

    @GetMapping("/getGrnNOByChild")
    public ResponseEntity<ResponseDTO> getGrnNOByChild(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo) {
        String methodName = "getGrnNOByChild()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = kittingService.getGrnNOByChild(orgId, branchCode, client, warehouse,partNo);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNOByChild Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNOByChild Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
    
}
    
    @GetMapping("/getBatchByChild")
    public ResponseEntity<ResponseDTO> getBatchByChild(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo
			,@RequestParam(required = true) String grnNo) {
        String methodName = "getBatchByChild()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = kittingService.getBatchByChild(orgId, branchCode, client, warehouse,partNo,grnNo);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Batch Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve batch Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
    
}
    
    @GetMapping("/getBinByChild")
    public ResponseEntity<ResponseDTO> getBinByChild(@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
			@RequestParam(required = true) String warehouse,@RequestParam(required = true) String partNo
			,@RequestParam(required = true) String grnNo,@RequestParam(required = true) String batch) {
        String methodName = "getBinByChild()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = kittingService.getBinByChild(orgId, branchCode, client, warehouse,partNo,grnNo,batch);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "bin Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve bin Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
    
}
    
    @GetMapping("/getSqtyByKitting")
    public ResponseEntity<ResponseDTO> getSqtyByKitting(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String warehouse, @RequestParam(required = true) String partNo,
            @RequestParam(required = true) String grnNo, @RequestParam(required = true) String batch,
            @RequestParam(required = true) String bin) {
        String methodName = "getSqtyByKitting()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        int avlQty = 0;
        try {
            avlQty = kittingService.getSqtyByKitting(orgId, branchCode, client, warehouse, partNo, grnNo, batch, bin);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sqty information retrieved successfully");
            responseObjectsMap.put("avlQty", avlQty);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Sqty Failed to retrieve Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
    }

    
    @GetMapping("/getPartNOByParent")
    public ResponseEntity<ResponseDTO> getPartNOByParent(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client) {
        String methodName = "getPartNOByParent()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = kittingService.getPartNOByParent(orgId, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNOByParent Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNOByChild Details", errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }
    
   
}
