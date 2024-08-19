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
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.service.VasPutawayService;


@RestController
@RequestMapping("/api/vasputaway")
public class VasPutawayController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(VasPutawayController.class);

	@Autowired
	VasPutawayService vasPutawayService;
	
	//VASPutaway
	
			@GetMapping("/getAllVasPutaway")
			public ResponseEntity<ResponseDTO> getAllVasPutaway(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client,@RequestParam String warehouse) {
				String methodName = "getAllVasPutaway()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<VasPutawayVO> vasPutawayVO = new ArrayList<>();
				try {
					vasPutawayVO = vasPutawayService.getAllVasPutaway(orgId,finYear,branch,branchCode,client,warehouse);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "VASPutaway information get successfully");
					responseObjectsMap.put("vasPutawayVO", vasPutawayVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap, "VASPutaway information receive failed", errorMsg);
				}
				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
			@GetMapping("/getVasPutawayById")
			public ResponseEntity<ResponseDTO> getVasPutawayById(@RequestParam(required = false) Long id) {
		        String methodName = "getVasPutawayById()";
		        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		        String errorMsg = null;
		        Map<String, Object> responseObjectsMap = new HashMap<>();
		        ResponseDTO responseDTO = null;
		        VasPutawayVO vasPutawayVO = new VasPutawayVO();

		        try {
		            vasPutawayVO = vasPutawayService.getVasPutawayById(id);
		            if (vasPutawayVO != null) {
		                responseObjectsMap.put("vasPutawayVO", vasPutawayVO);
		                responseDTO = createServiceResponseMsg(responseObjectsMap, "VASPutaway information retrieved successfully.");
		            } else {
		                responseDTO = createServiceResponseError(responseObjectsMap, "VASPutaway information not found.", errorMsg);
		            }
		        } catch (Exception e) {
		            errorMsg = e.getMessage();
		            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		            responseDTO = createServiceResponseError(responseObjectsMap, "VASPutaway information retrieval failed.", errorMsg);
		        }

		        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		        return ResponseEntity.ok().body(responseDTO);
		    }
			
			
			@GetMapping("/getVasPutawayDocId")
		    public ResponseEntity<ResponseDTO> getVasPutawayDocId(
		    		@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client) {
		        
		        String methodName = "getVasPutawayDocId()";
		        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		        String errorMsg = null;
		        Map<String, Object> responseObjectsMap = new HashMap<>();
		        ResponseDTO responseDTO = null;
		        String mapp="";
		        
		        try {
		            mapp = vasPutawayService.getVasPutawayDocId(orgId,finYear,branch, branchCode, client);
		        } catch (Exception e) {
		            errorMsg = e.getMessage();
		            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        }
		        
		        if (StringUtils.isBlank(errorMsg)) {
		            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All VasPutAway information retrieved successfully");
		            responseObjectsMap.put("VasPutAwayDocId", mapp);
		            responseDTO = createServiceResponse(responseObjectsMap);
		        } else {
		            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve VasPutAway information", errorMsg);
		        }
		        
		        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		        return ResponseEntity.ok().body(responseDTO);
		    }
			
			

			@PutMapping("/createUpdateVasPutaway")
			public ResponseEntity<ResponseDTO> createUpdateVasPutaway(@RequestBody VasPutawayDTO vasPutawayDTO) {
			    String methodName = "createUpdateVasPutaway()";
			    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			    String errorMsg = null;
			    Map<String, Object> responseObjectsMap = new HashMap<>();
			    ResponseDTO responseDTO = null;
			    try {
			        Map<String, Object> vasPutawayVO = vasPutawayService.createUpdateVasPutaway(vasPutawayDTO);
			        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, vasPutawayVO.get("message"));
			        responseObjectsMap.put("vasPutawayVO", vasPutawayVO.get("vasPutawayVO"));
			        responseDTO = createServiceResponse(responseObjectsMap);
			    } catch (Exception e) {
			        errorMsg = e.getMessage();
			        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			    }
			    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			    return ResponseEntity.ok().body(responseDTO);
			}
			
			@GetMapping("/getDocIdFromVasPickForVasPutaway")
			public ResponseEntity<ResponseDTO> getDocIdFromVasPickForVasPutaway(
					@RequestParam(required = false) Long orgId,
					@RequestParam(required = false) String branch,
					@RequestParam(required = false) String client) {

				String methodName = "getDocIdFromVasPickForVasPutaway()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<Map<String, Object>> mov = new ArrayList<>();

				try {
					mov = vasPutawayService.getDocIdFromVasPickForVasPutaway(orgId,
							 branch, client);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
							"All Docid from VasPick information retrieved successfully");
					responseObjectsMap.put("vasPutawayVO", mov);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Failed to retrieve DocId from VasPick information", errorMsg);
				}

				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
			
			
//			@GetMapping("/getAllDetailsFromVasPickDetailsForVasPutawayDetails")
//			public ResponseEntity<ResponseDTO> getAllDetailsFromVasPickForVasPutawayDetails(
//					@RequestParam(required = false) Long orgId,
//					@RequestParam(required = false) String branch,
//					@RequestParam(required = false) String client,
//					@RequestParam(required = false) String docid) {
//
//				String methodName = "getAllDetailsFromVasPickDetailsForVasPutawayDetails()";
//				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//				String errorMsg = null;
//				Map<String, Object> responseObjectsMap = new HashMap<>();
//				ResponseDTO responseDTO = null;
//				List<Map<String, Object>> mov = new ArrayList<>();
//
//				try {
//					mov = vasPutawayService.getAllDetailsFromVasPickDetailsForVasPutawayDetails(orgId,
//							 branch, client,docid);
//				} catch (Exception e) {
//					errorMsg = e.getMessage();
//					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//				}
//				if (StringUtils.isBlank(errorMsg)) {
//					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
//							"All Details from VasPutaway information retrieved successfully");
//					responseObjectsMap.put("vasPutawayVO", mov);
//					responseDTO = createServiceResponse(responseObjectsMap);
//				} else {
//					responseDTO = createServiceResponseError(responseObjectsMap,
//							"Failed to retrieve All Details from VasPutaway information", errorMsg);
//				}
//
//				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//				return ResponseEntity.ok().body(responseDTO);
//			}
			
			@GetMapping("/getAllFillGridFromVasPutaway")
			public ResponseEntity<ResponseDTO> getAllFillGridFromVasPutaway(@RequestParam(required = false) Long orgId,
					 @RequestParam(required = false) String branch,
					@RequestParam(required = false) String branchCode, @RequestParam(required = false) String client,
					@RequestParam(required = false) String docId) {
				String methodName = "getAllFillGridFromVasPutaway()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
		    List<Map<String, Object>> mov = new ArrayList<>();
		    try {
					mov = vasPutawayService.getAllFillGridFromVasPutaway(orgId, branch, branchCode,
							client,docId);
		      } catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
							"All FillGrid from VasPutaway information retrieved successfully");
		      responseObjectsMap.put("vasPutawayVO", mov);
		      responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Failed to retrieve FillGrid from VasPutaway information", errorMsg);
				}
				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
			
}
