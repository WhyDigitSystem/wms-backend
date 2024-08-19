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
import com.whydigit.wms.dto.PutAwayDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.GrnVO;
import com.whydigit.wms.entity.PutAwayVO;
import com.whydigit.wms.service.PutawayService;

@RestController
@RequestMapping("/api/putaway")
public class PutawayController extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PutawayController.class);
	
	@Autowired
	PutawayService putawayService;
	
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
				putAwayVO = putawayService.getAllPutAway(orgId, finYear, branch, branchCode, client, warehouse);
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
				putAwayVO = putawayService.getPutAwayById(id);
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
				grnVO = putawayService.getGrnNoForPutAway(orgId, client, branch, branchCode, warehouse);
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
				mapp = putawayService.getPutAwayDocId(orgId, finYear, branch, branchCode, client);
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
				Map<String, Object> putAwayVO = putawayService.createUpdatePutAway(putAwayDTO);
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
				gridDetails = putawayService.getFillGridDetailsForPutaway(orgId, branchCode, warehouse, client, grnNo, binType, binClass, binPick);
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