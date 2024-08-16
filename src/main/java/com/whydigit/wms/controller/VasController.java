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
import com.whydigit.wms.dto.VasPickDTO;
import com.whydigit.wms.entity.KittingVO;
import com.whydigit.wms.entity.VasPickVO;
import com.whydigit.wms.service.VasService;

@RestController
@RequestMapping("/api/vascontroller")
public class VasController extends BaseController {

    public static final Logger LOGGER = LoggerFactory.getLogger(VasController.class);

    @Autowired
    VasService vasService;

    // KITTING

    @PutMapping("/createUpdateKitting")
    public ResponseEntity<ResponseDTO> createUpdateKitting(@RequestBody KittingDTO kittingDTO) {
        String methodName = "createUpdateKitting()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        try {
            Map<String, Object> createkittingVO = vasService.createUpdateKitting(kittingDTO);
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
            kittingVOs = vasService.getAllKitting(orgId, branchCode, client, customer);
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
            kittingVO = vasService.getKittingById(id).orElse(null);
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
            mapp = vasService.getKittingInDocId(orgId, finYear, branch, branchCode, client);
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
            @RequestParam(required = true) String bin, @RequestParam(required = true) String branch,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client) {
        String methodName = "getPartNOByChild()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = vasService.getPartNOByChild(orgId, bin, branch, branchCode, client);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNOByChild Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve PartNOByChild Details", errorMsg);
        }
        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/getGrnNOByChild")
    public ResponseEntity<ResponseDTO> getGrnNOByChild(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String bin, @RequestParam(required = true) String branch,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String partNo, @RequestParam(required = true) String partDesc,
            @RequestParam(required = true) String sku) {
        String methodName = "getGrnNOByChild()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = vasService.getGrnNOByChild(orgId, bin, branch, branchCode, client, partNo, partDesc, sku);
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
    @GetMapping("/getSqtyByKitting")
    public ResponseEntity<ResponseDTO> getSqtyByKitting(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String partNo,@RequestParam(required = true) String warehouse,
            @RequestParam(required = true) String grnno) {
        String methodName = "getSqtyByKitting()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> avlQty = new ArrayList<>();
        try {
        	avlQty = vasService.getSqtyByKitting(orgId, branchCode, client, partNo,warehouse,grnno);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Sqty information retrieved successfully");
            responseObjectsMap.put("avlQty", avlQty);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, " Sqty Failed to retrieve  Details", errorMsg);
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
            kittingVO = vasService.getPartNOByParent(orgId, branchCode, client);
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
    
    @GetMapping("/getGrnNOByParent")
    public ResponseEntity<ResponseDTO> getGrnNOByParent(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String bin, @RequestParam(required = true) String branch,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String partNo, @RequestParam(required = true) String partDesc,
            @RequestParam(required = true) String sku) {
        String methodName = "getGrnNOByParent()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> kittingVO = new ArrayList<>();
        try {
            kittingVO = vasService.getGrnNOByParent(orgId, bin, branch, branchCode, client, partNo, partDesc, sku);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GrnNOByParent Details information retrieved successfully");
            responseObjectsMap.put("kittingVO", kittingVO);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve GrnNOByParent Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
    
}
    //VASPICK
    
    @PutMapping("/createUpdateVasPic")
	public ResponseEntity<ResponseDTO> createUpdateVasPic(@RequestBody VasPickDTO vasPicDTO) {
	    String methodName = "createUpdateGRN()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> vasPicVO = vasService.createUpdateVasPic(vasPicDTO);
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
	        vasPickVO = vasService.getVaspickById(id).orElse(null);
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
	    	vasPickGrid = vasService.getVaspickGrid(orgId,branch,branchCode,client,warehouse);
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
			vasPickVO = vasService.getAllVaspick(orgId,branchCode,client,branch,finYear,warehouse);
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
            mapp = vasService.getVasPickDocId(orgId,finYear,branch, branchCode, client);
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
	
	@GetMapping("/getVasPicGridDetails")
    public ResponseEntity<ResponseDTO> getVasPicGridDetails(@RequestParam(required = true) Long orgId,
            @RequestParam(required = true) String branchCode, @RequestParam(required = true) String client,
            @RequestParam(required = true) String warehouse, @RequestParam(required = true) char stateStatus ) {
        String methodName = "getVasPicGridDetails()";
        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
        String errorMsg = null;
        Map<String, Object> responseObjectsMap = new HashMap<>();
        ResponseDTO responseDTO = null;
        List<Map<String, Object>> vaspickGrid = new ArrayList<>();
        try {
        	vaspickGrid = vasService.getVasPicGridDetails(orgId,branchCode, client,warehouse,stateStatus);
        } catch (Exception e) {
            errorMsg = e.getMessage();
            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
        }
        if (StringUtils.isBlank(errorMsg)) {
            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "vaspickGrid Details information retrieved successfully");
            responseObjectsMap.put("vaspickGrid", vaspickGrid);
            responseDTO = createServiceResponse(responseObjectsMap);
        } else {
            responseDTO = createServiceResponseError(responseObjectsMap, "VaspickGrid to retrieve GrnNOByChild Details", errorMsg);
        }
        return ResponseEntity.ok().body(responseDTO);
	} 
}
