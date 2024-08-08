package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.service.OutwardTransactionService;

@RestController
@RequestMapping("/api/outward")
public class OutwardTransactionController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(InwardTransactionController.class);

	@Autowired
	OutwardTransactionService outwardTransactionService;
	
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
				deliveryChallanVO = outwardTransactionService.getAllDeliveryChallan(orgId,finYear,branch,branchCode,client,warehouse);
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
	        	deliveryChallanVO = outwardTransactionService.getDeliveryChallanById(id);
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
	            mapp = outwardTransactionService.getDeliveryChallanDocId(orgId,finYear,branch, branchCode, client);
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

		
		@PutMapping("/updateCreateDeliveryChallan")
		public ResponseEntity<ResponseDTO> updateCreateDeliveryChallan(@Valid @RequestBody DeliveryChallanDTO deliveryChallanDTO) {
			String methodName = "updateCreateDeliveryChallan()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				DeliveryChallanVO deliveryChallanVO = outwardTransactionService.updateCreateDeliveryChallan(deliveryChallanDTO);
				if (deliveryChallanVO != null) {
					boolean isUpdate = deliveryChallanDTO.getId() != null;
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,isUpdate? "DeliveryChallanVO updated successfully" : "DeliveryChallanVO created Successfully");
					responseObjectsMap.put("deliveryChallanVO", deliveryChallanVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					boolean isUpdate = deliveryChallanDTO.getId() != null;
					errorMsg = isUpdate? "DeliveryChallanVO not found for ID: " + deliveryChallanDTO.getId(): "DeliveryChallanVO created failed";
					responseDTO = createServiceResponseError(responseObjectsMap,isUpdate? "DeliveryChallanVO update failed" : "DeliveryChallanVO created failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				boolean isUpdate = deliveryChallanDTO.getId() != null;
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,isUpdate? "DeliveryChallanVO update failed" : "DeliveryChallanVO created failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		
		
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
				vasPutawayVO = outwardTransactionService.getAllVasPutaway(orgId,finYear,branch,branchCode,client,warehouse);
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
	            vasPutawayVO = outwardTransactionService.getVasPutawayById(id);
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
	            mapp = outwardTransactionService.getVasPutawayDocId(orgId,finYear,branch, branchCode, client);
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
		
		@PutMapping("/updateCreateVasPutaway")
		public ResponseEntity<ResponseDTO> updateCreateVasPutaway(@Valid @RequestBody VasPutawayDTO vasPutawayDTO) {
			String methodName = "updateCreateVasPutaway()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				VasPutawayVO vasPutawayVO = outwardTransactionService.updateCreateVasPutaway(vasPutawayDTO);
				if (vasPutawayVO != null) {
					boolean isUpdate = vasPutawayDTO.getId() != null;
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE,isUpdate? "VasPutaway updated successfully" : "VasPutaway created Successfully");
					responseObjectsMap.put("vasPutawayVO", vasPutawayVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					boolean isUpdate = vasPutawayDTO.getId() != null;
					errorMsg = isUpdate? "VasPutawayVO not found for ID: " + vasPutawayDTO.getId(): "VasPutaway created failed";
					responseDTO = createServiceResponseError(responseObjectsMap,isUpdate? "VasPutaway update failed" : "VasPutaway created failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				boolean isUpdate = vasPutawayDTO.getId() != null;
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,isUpdate? "VasPutaway update failed" : "VasPutaway created failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
}
