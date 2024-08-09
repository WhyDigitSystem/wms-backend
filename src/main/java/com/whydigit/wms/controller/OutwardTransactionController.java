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
import com.whydigit.wms.dto.BuyerOrderDTO;
import com.whydigit.wms.dto.CodeConversionDTO;
import com.whydigit.wms.dto.DeliveryChallanDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.VasPutawayDTO;
import com.whydigit.wms.entity.BuyerOrderVO;
import com.whydigit.wms.entity.DeliveryChallanVO;
import com.whydigit.wms.entity.VasPutawayVO;
import com.whydigit.wms.service.OutwardTransactionService;

@RestController
@RequestMapping("/api/outward")
public class OutwardTransactionController extends BaseController{

	public static final Logger LOGGER = LoggerFactory.getLogger(OutwardTransactionController.class);

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
		
		@PutMapping("/createUpdatedDeliveryChallan")
		public ResponseEntity<ResponseDTO> createUpdatedDeliveryChallan(@RequestBody DeliveryChallanDTO deliveryChallanDTO) {
		    String methodName = "createUpdatedDeliveryChallan()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		    String errorMsg = null;
		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO = null;
		    try {
		        Map<String, Object> deliveryChallanVO = outwardTransactionService.createUpdateDeliveryChallan(deliveryChallanDTO);
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
		
		

		@PutMapping("/createUpdateVasPutaway")
		public ResponseEntity<ResponseDTO> createUpdateVasPutaway(@RequestBody VasPutawayDTO vasPutawayDTO) {
		    String methodName = "createUpdateVasPutaway()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		    String errorMsg = null;
		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO = null;
		    try {
		        Map<String, Object> vasPutawayVO = outwardTransactionService.createUpdateVasPutaway(vasPutawayDTO);
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
		
	//	
		
		@PutMapping("/createUpdateBuyerOrder")
		public ResponseEntity<ResponseDTO> createUpdateBuyerOrder(@RequestBody BuyerOrderDTO buyerOrderDTO) {
			String methodName = "createUpdateGatePassIn()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				Map<String, Object> createdBuyerOrderVO = outwardTransactionService.createUpdateBuyerOrder(buyerOrderDTO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdBuyerOrderVO.get("message"));
				responseObjectsMap.put("buyerOrderVO", createdBuyerOrderVO.get("buyerOrderVO"));
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAllBuyerOrderByOrgId")
		public ResponseEntity<ResponseDTO> getAllBuyerOrderByOrgId(@RequestParam(required = true) Long orgId) {
			String methodName = "getAllBuyerOrderByOrgId()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BuyerOrderVO> buyerOrderVOs = new ArrayList<>();
			try {
				buyerOrderVOs = outwardTransactionService.getAllBuyerOrderByOrgId(orgId);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrder information get successfully By OrgId");
				responseObjectsMap.put("buyerOrderVO", buyerOrderVOs);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "BuyerOrder information get Failed ",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAllBuyerOrderById")
		public ResponseEntity<ResponseDTO> getAllBuyerOrderById(@RequestParam(required = true) Long id) {
			String methodName = "getAllBuyerOrderById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BuyerOrderVO> buyerOrderVOs = new ArrayList<>();
			try {
				buyerOrderVOs = outwardTransactionService.getAllBuyerOrderById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "BuyerOrder information get successfully Id");
				responseObjectsMap.put("buyerOrderVO", buyerOrderVOs);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "BuyerOrder information get Failed ",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAvlQty")
		public ResponseEntity<ResponseDTO> getAvlQty(@RequestParam(required =true) Long orgId,
				@RequestParam(required =true) String client,
				@RequestParam(required =true) String branchCode,
				@RequestParam(required =true)String warehouse,
				@RequestParam(required =true) String branch,
				@RequestParam(required =true) String partNo,
				@RequestParam(required =true) String partDesc) {
			String methodName = "getAvlQty()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			int avalQty = 0;
			try {
				avalQty = outwardTransactionService.getAvlQty(orgId,client,branchCode,warehouse,branch,partNo,partDesc);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "SQTY information get successfully Id");
				responseObjectsMap.put("avalQty", avalQty);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "SQTY information get Failed ",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
}
