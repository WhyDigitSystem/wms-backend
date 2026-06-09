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
import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.PickRequestVO;
import com.whydigit.wms.service.DeliveryChallanService;

@RestController
@RequestMapping("/api/deliverychallan")
public class DeliveryChallanController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(DeliveryChallanController.class);

	@Autowired
	DeliveryChallanService deliveryChallanService;
	
	
	//DeliveryChallan
	
			@GetMapping("/getAllDeliveryChallan")
			public ResponseEntity<ResponseDTO> getAllDeliveryChallan(@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client,@RequestParam String warehouse) {
				String methodName = "getAllDeliveryChallan()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<DeliveryChallanVO> deliveryChallanVO = new ArrayList<>();
				try {
					deliveryChallanVO = deliveryChallanService.getAllDeliveryChallan(orgId,finYear,branch,branchCode,client,warehouse);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeliveryChallan information get successfully");
					responseObjectsMap.put("DeliveryChallanVO", deliveryChallanVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap, "DeliveryChallan information receive failed", errorMsg);
				}
				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
			
			
			@GetMapping("/getDeliveryChallanById")
			public ResponseEntity<ResponseDTO> getDeliveryChallanById(@RequestParam(required = false) Long id) {
		        String methodName = "getDeliveryChallanById()";
		        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		        String errorMsg = null;
		        Map<String, Object> responseObjectsMap = new HashMap<>();
		        ResponseDTO responseDTO = null;
		        DeliveryChallanVO deliveryChallanVO = new DeliveryChallanVO();

		        try {
		        	deliveryChallanVO = deliveryChallanService.getDeliveryChallanById(id);
		            if (deliveryChallanVO != null) {
		                responseObjectsMap.put("deliveryChallanVO", deliveryChallanVO);
		                responseDTO = createServiceResponseMsg(responseObjectsMap, " DeliveryChallan information retrieved successfully.");
		            } else {
		                responseDTO = createServiceResponseError(responseObjectsMap, " DeliveryChallan information not found.", errorMsg);
		            }
		        } catch (Exception e) {
		            errorMsg = e.getMessage();
		            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		            responseDTO = createServiceResponseError(responseObjectsMap, " DeliveryChallan information retrieval failed.", errorMsg);
		        }

		        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		        return ResponseEntity.ok().body(responseDTO);
		    }
			

			
			@GetMapping("/getDeliveryChallanDocId")
		    public ResponseEntity<ResponseDTO> getDeliveryChallanDocId(
		    		@RequestParam Long orgId,@RequestParam String finYear,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client) {
		        
		        String methodName = "getDeliveryChallanDocId()";
		        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		        String errorMsg = null;
		        Map<String, Object> responseObjectsMap = new HashMap<>();
		        ResponseDTO responseDTO = null;
		        String mapp="";
		        
		        try {
		            mapp = deliveryChallanService.getDeliveryChallanDocId(orgId,finYear,branch, branchCode, client);
		        } catch (Exception e) {
		            errorMsg = e.getMessage();
		            LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        }
		        
		        if (StringUtils.isBlank(errorMsg)) {
		            responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "All DeliveryChallan information retrieved successfully");
		            responseObjectsMap.put("DeliveryChallanDocId", mapp);
		            responseDTO = createServiceResponse(responseObjectsMap);
		        } else {
		            responseDTO = createServiceResponseError(responseObjectsMap, "Failed to retrieve DeliveryChallan information", errorMsg);
		        }
		        
		        LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		        return ResponseEntity.ok().body(responseDTO);
		    }
			
			@PutMapping("/createUpdatedDeliveryChallan")
			public ResponseEntity<ResponseDTO> createUpdatedDeliveryChallan(@RequestBody DeliveryChallanDTO deliveryChallanDTO) {
			    String methodName = "createUpdatedDeliveryChallan()";
			    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			    String errorMsg = null;
			    Map<String, Object> responseObjectsMap = new HashMap<>();
			    ResponseDTO responseDTO = null;
			    try {
			        Map<String, Object> deliveryChallanVO = deliveryChallanService.createUpdateDeliveryChallan(deliveryChallanDTO);
			        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, deliveryChallanVO.get("message"));
			        responseObjectsMap.put("deliveryChallanVO", deliveryChallanVO.get("deliveryChallanVO"));
			        responseDTO = createServiceResponse(responseObjectsMap);
			    } catch (Exception e) {
			        errorMsg = e.getMessage();
			        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			    }
			    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			    return ResponseEntity.ok().body(responseDTO);
			}
			
			@GetMapping("/getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan")
			public ResponseEntity<ResponseDTO> getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(
					@RequestParam(required = false) Long orgId,
					@RequestParam(required = false) String branch,
					@RequestParam(required = false) String branchCode,
					@RequestParam(required = false) String client,
					@RequestParam(required = false) String warehouse,
					@RequestParam(required = false) String buyerOrderNo ) {

				String methodName = "getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<Map<String, Object>> mov = new ArrayList<>();

				try {
					mov = deliveryChallanService.getDocidDocdatePartnoPartDescFromPickRequestForDeliveryChallan(orgId,
							branch, branchCode, client, warehouse,buyerOrderNo);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
							"All Details from DeliveryChallan information retrieved successfully");
					responseObjectsMap.put("deliveryChallanVO", mov);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Failed to retrieve All Details from DeliveryChallan information", errorMsg);
				}

				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
			@GetMapping("/getAllPickRequestFromDeliveryChallan")
			public ResponseEntity<ResponseDTO> getAllPickRequestFromDeliveryChallan(@RequestParam Long orgId,@RequestParam String branch,@RequestParam String branchCode,@RequestParam String client,@RequestParam String warehouse) {
				String methodName = "getAllPickRequestFromDeliveryChallan()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<PickRequestVO> pickRequestVO = new ArrayList<>();
				try {
					pickRequestVO = deliveryChallanService.getAllPickRequestFromDeliveryChallan(orgId,branch,branchCode,client,warehouse);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "DeliveryChallan information get successfully");
					responseObjectsMap.put("pickRequestVO", pickRequestVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap, "DeliveryChallan information receive failed", errorMsg);
				}
				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
		
			
			@GetMapping("/getBuyerShipToBillToFromBuyerOrderForDeliveryChallan")
			public ResponseEntity<ResponseDTO> getBuyerShipToBillToFromBuyerOrderForDeliveryChallan(
					@RequestParam(required = false) Long orgId,
					@RequestParam(required = false) String branch,
					@RequestParam(required = false) String branchCode,
					@RequestParam(required = false) String client,
					@RequestParam(required = false) String buyerOrderNo) {

				String methodName = "getBuyerShipToBillToFromBuyerOrderForDeliveryChallan()";
				LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
				String errorMsg = null;
				Map<String, Object> responseObjectsMap = new HashMap<>();
				ResponseDTO responseDTO = null;
				List<Map<String, Object>> mov = new ArrayList<>();

				try {
					mov = deliveryChallanService.getBuyerShipToBillToFromBuyerOrderForDeliveryChallan(orgId,
							 branch,branchCode, client,buyerOrderNo);
				} catch (Exception e) {
					errorMsg = e.getMessage();
					LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				}
				if (StringUtils.isBlank(errorMsg)) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
							"All BuyerDetails from BuyerOrder information retrieved successfully");
					responseObjectsMap.put("vasPutawayVO", mov);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Failed to retrieve BuyerDetails from BuyerOrder information", errorMsg);
				}

				LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
				return ResponseEntity.ok().body(responseDTO);
			}
			
}
