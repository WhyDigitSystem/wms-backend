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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.service.WarehouseMasterService;

@RequestMapping("/api")
@RestController
public class WarehouseMasterController extends BaseController {
	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterController.class);
	
	@Autowired
	WarehouseMasterService warehouseMasterService; 
	
	// Group

		@GetMapping("/group")
		public ResponseEntity<ResponseDTO> getAllGroup() {
			String methodName = "getAllGroup()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<GroupVO> groupVO = new ArrayList<>();
			try {
				groupVO = warehouseMasterService.getAllGroup();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group information get successfully");
				responseObjectsMap.put("groupVO", groupVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Group information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/group/{groupid}")
		public ResponseEntity<ResponseDTO> getGroupById(@PathVariable Long groupid) {
			String methodName = "getGroupById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			GroupVO groupVO = null;
			try {
				groupVO = warehouseMasterService.getGroupById(groupid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group found by Group ID");
				responseObjectsMap.put("groupVO", groupVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "group not found for group ID: " + groupid;
				responseDTO = createServiceResponseError(responseObjectsMap, "group not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/group")
		public ResponseEntity<ResponseDTO> createGroup(@RequestBody GroupVO groupVO) {
			String methodName = "createGroup()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				GroupVO createdGroupVO = warehouseMasterService.createGroup(groupVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group created successfully");
				responseObjectsMap.put("createdGroupVO", createdGroupVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Group creation failed. Group Name already Exist ", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/group")
		public ResponseEntity<ResponseDTO> updateGroup(@RequestBody GroupVO groupVO) {
			String methodName = "updateGroup()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				GroupVO groupvo = warehouseMasterService.updateGroup(groupVO).orElse(null);
				if (groupVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Group Updated successfully");
					responseObjectsMap.put("groupVO", groupvo);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Group not found for ID: " + groupvo.getGroupid();
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Group Update failed, Group Name already Exist", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Group Name Update failed, Group Name already Exist", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		// Unit

		@GetMapping("/unit")
		public ResponseEntity<ResponseDTO> getAllUnit() {
			String methodName = "getAllUnit()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<UnitVO> unitVO = new ArrayList<>();
			try {
				unitVO = warehouseMasterService.getAllUnit();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit information get successfully");
				responseObjectsMap.put("unitVO", unitVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Unit information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/unit/{unitid}")
		public ResponseEntity<ResponseDTO> getUnitById(@PathVariable Long unitid) {
			String methodName = "getUnitById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			UnitVO unitVO = null;
			try {
				unitVO = warehouseMasterService.getUnitById(unitid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit found by Unit ID");
				responseObjectsMap.put("unitVO", unitVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Unit not found for Unit ID: " + unitid;
				responseDTO = createServiceResponseError(responseObjectsMap, "Unit not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/unit")
		public ResponseEntity<ResponseDTO> createUnit(@RequestBody UnitVO unitVO) {
			String methodName = "createUnit()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				UnitVO createdUnitVO = warehouseMasterService.createUnit(unitVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit created successfully");
				responseObjectsMap.put("createdUnitVO", createdUnitVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Unit creation failed. Unit Name already Exist ", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/unit")
		public ResponseEntity<ResponseDTO> updateUnit(@RequestBody UnitVO unitVO) {
			String methodName = "updateUnit()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				UnitVO unitvo = warehouseMasterService.updateUnit(unitVO).orElse(null);
				if (unitVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit Updated successfully");
					responseObjectsMap.put("unitVO", unitvo);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Unit not found for ID: " + unitvo.getUnitid();
					responseDTO = createServiceResponseError(responseObjectsMap,
							"Unit Update failed, Unit Name already Exist", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Unit Name Update failed, Unit Name already Exist", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		// LocationType

		@GetMapping("/locationType")
		public ResponseEntity<ResponseDTO> getAllLocationType() {
			String methodName = "getAllLocationType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<LocationTypeVO> locationTypeVO = new ArrayList<>();
			try {
				locationTypeVO = warehouseMasterService.getAllLocationType();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType information get successfully");
				responseObjectsMap.put("locationTypeVO", locationTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "LocationType information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/locationType/{locationtypeid}")
		public ResponseEntity<ResponseDTO> getLocationTypeById(@PathVariable Long locationtypeid) {
			String methodName = "getLocationTypeById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			LocationTypeVO locationTypeVO = null;
			try {
				locationTypeVO = warehouseMasterService.getLocationTypeById(locationtypeid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType found by LocationType ID");
				responseObjectsMap.put("locationTypeVO", locationTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "LocationType not found for LocationType ID: " + locationtypeid;
				responseDTO = createServiceResponseError(responseObjectsMap, "LocationType not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/locationType")
		public ResponseEntity<ResponseDTO> createLocationType(@RequestBody LocationTypeVO locationTypeVO) {
			String methodName = "createLocationType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				LocationTypeVO createdLocationTypeVO = warehouseMasterService.createLocationType(locationTypeVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType created successfully");
				responseObjectsMap.put("createdLocationTypeVO", createdLocationTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"LocationType creation failed. LocationType Name already Exist ", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/locationType")
		public ResponseEntity<ResponseDTO> updateLocationType(@RequestBody LocationTypeVO locationTypeVO) {
			String methodName = "updateLocationType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				LocationTypeVO locationTypevo = warehouseMasterService.updateLocationType(locationTypeVO).orElse(null);
				if (locationTypeVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType Updated successfully");
					responseObjectsMap.put("locationTypeVO", locationTypevo);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "LocationType not found for ID: " + locationTypevo.getLocationtype();
					responseDTO = createServiceResponseError(responseObjectsMap, "LocationType Update failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "LocationType Name Update failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		// CellType

		@GetMapping("/cellType")
		public ResponseEntity<ResponseDTO> getAllCellType() {
			String methodName = "getAllCellType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<CellTypeVO> cellTypeVO = new ArrayList<>();
			try {
				cellTypeVO = warehouseMasterService.getAllCellType();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType information get successfully");
				responseObjectsMap.put("cellTypeVO", cellTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "CellType information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/cellType/{celltypeid}")
		public ResponseEntity<ResponseDTO> getCellTypeById(@PathVariable Long celltypeid) {
			String methodName = "getCellTypeById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			CellTypeVO cellTypeVO = null;
			try {
				cellTypeVO = warehouseMasterService.getCellTypeById(celltypeid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}

			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType found by CellType ID");
				responseObjectsMap.put("cellTypeVO", cellTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "CellType not found for CellType ID: " + celltypeid;
				responseDTO = createServiceResponseError(responseObjectsMap, "CellType not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/cellType")
		public ResponseEntity<ResponseDTO> createCellType(@RequestBody CellTypeVO cellTypeVO) {
			String methodName = "createCellType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				CellTypeVO createdCellTypeVO = warehouseMasterService.createCellType(cellTypeVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType created successfully");
				responseObjectsMap.put("createdCellTypeVO", createdCellTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "CellType creation failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/cellType")
		public ResponseEntity<ResponseDTO> updateCellType(@RequestBody CellTypeVO cellTypeVO) {
			String methodName = "updateCellType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				CellTypeVO cellTypevo = warehouseMasterService.updateCellType(cellTypeVO).orElse(null);
				if (cellTypeVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType Updated successfully");
					responseObjectsMap.put("cellTypeVO", cellTypevo);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Celltype not found for ID: " + cellTypevo.getCelltypeid();
					responseDTO = createServiceResponseError(responseObjectsMap, "CellType Update failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "CellType Update failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		// Branch
		@GetMapping("/branch")
		public ResponseEntity<ResponseDTO> getAllBranch() {
			String methodName = "getAllBranch()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BranchVO> branchVO = new ArrayList<>();
			try {
				branchVO = warehouseMasterService.getAllBranch();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch information get successfully");
				responseObjectsMap.put("branchVO", branchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Branch information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/branch/{branchid}")
		public ResponseEntity<ResponseDTO> getBranchById(@PathVariable Long branchid) {
			String methodName = "getBranchById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			BranchVO branchVO = null;
			try {
				branchVO = warehouseMasterService.getBranchById(branchid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch found by ID");
				responseObjectsMap.put("Branch", branchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Branch not found for ID: " + branchid;
				responseDTO = createServiceResponseError(responseObjectsMap, "Branch not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/getAllBranchByCompany/{company}")
		public ResponseEntity<ResponseDTO> getAllBranchByCompany(@PathVariable String company) {
			String methodName = "getAllBranchByCompany()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BranchVO> branchVO = null;
			try {
				branchVO = warehouseMasterService.getAllBranchByCompany(company);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "getAllBranchByCompany found by ID");
				responseObjectsMap.put("getAllBranchByCompany", branchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "getAllBranchByCompany not found for ID: " + company;
				responseDTO = createServiceResponseError(responseObjectsMap, "getAllBranchByCompany not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/branch")
		public ResponseEntity<ResponseDTO> createBranch(@RequestBody BranchVO branchVO) {
			String methodName = "createBranch()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				BranchVO createdBranchVO = warehouseMasterService.createBranch(branchVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch created successfully");
				responseObjectsMap.put("branchVO", createdBranchVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Branch creation failed. Branch already Exist ", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/branch")
		public ResponseEntity<ResponseDTO> updateBranch(@RequestBody BranchVO branchVO) {
			String methodName = "updateBranch()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				BranchVO updatedBranchVO = warehouseMasterService.updateBranch(branchVO).orElse(null);
				if (updatedBranchVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch updated successfully");
					responseObjectsMap.put("branchVO", updatedBranchVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Branch not found for BranchID: " + branchVO.getBranchid();
					responseDTO = createServiceResponseError(responseObjectsMap, "Branch update failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "Branch Update failed. Branch already Exisit",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		// Customer

		@GetMapping("/customer")
		public ResponseEntity<ResponseDTO> getAllCustomer() {
			String methodName = "getAllCustomer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<CustomerVO> customerVO = new ArrayList<>();
			try {
				customerVO = warehouseMasterService.getAllCustomer();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer information get successfully");
				responseObjectsMap.put("CustomerVO", customerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Customer information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/customer/{customerid}")
		public ResponseEntity<ResponseDTO> getCustomerById(@PathVariable Long customerid) {
			String methodName = "getCustomerById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			CustomerVO customerVO = null;
			try {
				customerVO = warehouseMasterService.getCustomerById(customerid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer found by ID");
				responseObjectsMap.put("Customer", customerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Customer not found for ID: " + customerid;
				responseDTO = createServiceResponseError(responseObjectsMap, "Customer not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/customer")
		public ResponseEntity<ResponseDTO> createCustomer(@RequestBody CustomerVO customerVO) {
			String methodName = "createCustomer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				CustomerVO createdCustomerVO = warehouseMasterService.createCustomer(customerVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer created successfully");
				responseObjectsMap.put("customerVO", createdCustomerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "Customer and Client already Exist ",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/customer")
		public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody CustomerVO customerVO,@RequestBody ClientVO clientVO) {
			String methodName = "updateCustomer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				CustomerVO updatedCustomerVO = warehouseMasterService.updateCustomer(customerVO,clientVO).orElse(null);
				if (updatedCustomerVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer updated successfully");
					responseObjectsMap.put("customerVO", updatedCustomerVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Customer not found for CustomerID: " + customerVO.getCustomerid();
					responseDTO = createServiceResponseError(responseObjectsMap, "Customer update failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap, "Customer and Client already Exisit",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		// Warehouse
		
		@GetMapping("/warehouse")
		public ResponseEntity<ResponseDTO> getAllWarehouse() {
			String methodName = "getAllWarehouse()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<WarehouseVO> warehouseVO = new ArrayList<>();
			try {
				warehouseVO = warehouseMasterService.getAllWarehouse();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse information get successfully");
				responseObjectsMap.put("warehouseVO", warehouseVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@GetMapping("/warehouse/{warehouseid}")
		public ResponseEntity<ResponseDTO> getWarehouseById(@PathVariable Long warehouseid) {
			String methodName = "getWarehouseById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			WarehouseVO warehouseVO = null;
			try {
				warehouseVO = warehouseMasterService.getWarehouseById(warehouseid).orElse(null);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse found by ID");
				responseObjectsMap.put("Warehouse", warehouseVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse not found for ID: " + warehouseid;
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/warehouse/company")
		public ResponseEntity<ResponseDTO> getAllWarehouseByCompany(@RequestParam String company) {
			String methodName = "getAllWarehouseByCompany()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<WarehouseVO> warehouseVO = null;
			try {
				warehouseVO = warehouseMasterService.getWarehouseByCompany(company);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isEmpty(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse found");
				responseObjectsMap.put("Warehouse", warehouseVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse not found: ";
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse not found", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PostMapping("/warehouse")
		public ResponseEntity<ResponseDTO> createWarehouse(@RequestBody WarehouseVO warehouseVO) {
			String methodName = "createWarehouse()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				WarehouseVO createdWarehouseVO = warehouseMasterService.createWarehouse(warehouseVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse created successfully");
				responseObjectsMap.put("warehouseVO", createdWarehouseVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"WarehouseName, Country and BranchCode already Exist ", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/warehouse")
		public ResponseEntity<ResponseDTO> updateWarehouse(@RequestBody WarehouseVO warehouseVO) {
			String methodName = "updateWarehouse()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			try {
				WarehouseVO updatedWarehouseVO = warehouseMasterService.updateWarehouse(warehouseVO).orElse(null);
				if (updatedWarehouseVO != null) {
					responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse updated successfully");
					responseObjectsMap.put("warehouseVO", updatedWarehouseVO);
					responseDTO = createServiceResponse(responseObjectsMap);
				} else {
					errorMsg = "Warehouse not found for Warehouse ID: " + warehouseVO.getWarehouseid();
					responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse update failed", errorMsg);
				}
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
				responseDTO = createServiceResponseError(responseObjectsMap,
						"WarehouseName, Country and BranchCode already Exist", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		
		// Warehouse Location

	@GetMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> getAllWarehouseLocation() {
		String methodName = "getAllWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		try {
			warehouseLocationVO = warehouseMasterService.getAllWarehouseLocation();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location information get successfully");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/warehouselocation/company")
	public ResponseEntity<ResponseDTO> getAllWarehouseLocationByCompany(@RequestParam String Company){
		
		String methodName = "getAllWarehouseLocationByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		try {
			warehouseLocationVO = warehouseMasterService.getAllWarehouseLocationByCompany(Company);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location found by ID");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "WarehouseLocation not found for Company: " + Company;
			responseDTO = createServiceResponseError(responseObjectsMap, "WarehouseLocation not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/warehouselocation/{warehouselocationid}")
	public ResponseEntity<ResponseDTO> getWarehouseLocationById(@PathVariable Long warehouselocationid) {
		String methodName = "getWarehouseLocationById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		WarehouseLocationVO warehouseLocationVO = null;
		try {
			warehouseLocationVO = warehouseMasterService.getWarehouseLocationById(warehouselocationid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location found by ID");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Warehouse Location not found for ID: " + warehouselocationid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> createWarehouseLocation(@RequestBody WarehouseLocationVO warehouseLocationVO) {
		String methodName = "createWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseLocationVO createWarehouseLocation = warehouseMasterService.createWarehouseLocation(warehouseLocationVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location created successfully");
			responseObjectsMap.put("WarehouseLocation", createWarehouseLocation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location creation", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/warehouselocation")
	public ResponseEntity<ResponseDTO> updateWarehouseLocation(@RequestBody WarehouseLocationVO warehouseLocationVO) {
		String methodName = "updateWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseLocationVO updatedwarehouseLocationVO = warehouseMasterService.updateWarehouseLocation(warehouseLocationVO).orElse(null);
			if (updatedwarehouseLocationVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location updated successfully");
				responseObjectsMap.put("warehouseLocationVO", updatedwarehouseLocationVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse Location not found for Warehouse LocationID: " + warehouseLocationVO.getWarehouselocationid();
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
		
}
