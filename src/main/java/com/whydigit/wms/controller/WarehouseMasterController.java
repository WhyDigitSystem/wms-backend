package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationMappingVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.SupplierVO;
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
	public ResponseEntity<ResponseDTO> getAllGroup(@RequestParam Long orgid) {
		String methodName = "getAllGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<GroupVO> groupVO = new ArrayList<>();
		try {
			groupVO = warehouseMasterService.getAllGroup(orgid);
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
				errorMsg = "Group not found for ID: " + groupvo.getId();
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
	public ResponseEntity<ResponseDTO> getAllUnit(@RequestParam Long orgid) {
		String methodName = "getAllUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UnitVO> unitVO = new ArrayList<>();
		try {
			unitVO = warehouseMasterService.getAllUnit(orgid);
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
				errorMsg = "Unit not found for ID: " + unitvo.getId();
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
	public ResponseEntity<ResponseDTO> getAllLocationType(@RequestParam Long orgid) {
		String methodName = "getAllLocationType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<LocationTypeVO> locationTypeVO = new ArrayList<>();
		try {
			locationTypeVO = warehouseMasterService.getAllLocationType(orgid);
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
		String methodName = "createOrUpdateLocationType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			 LocationTypeVO createdLocationType = warehouseMasterService.createLocationType(locationTypeVO);
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType created successfully");
				responseObjectsMap.put("createdLocationTypeVO", createdLocationType);
				responseDTO = createServiceResponse(responseObjectsMap);
			}
		 catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationType creation/update failed",
					errorMsg);
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
			LocationTypeVO updatelocationTypeVO = warehouseMasterService.updateLocationType(locationTypeVO).orElse(null);
			if (updatelocationTypeVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Location Type Updated successfully");
				responseObjectsMap.put("locationtype", updatelocationTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Location Type not found for ID: " + locationTypeVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Location Type Update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "CellType Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// CellType

	@GetMapping("/cellType")
	public ResponseEntity<ResponseDTO> getAllCellType(@RequestParam Long orgid) {
		String methodName = "getAllCellType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CellTypeVO> cellTypeVO = new ArrayList<>();
		try {
			cellTypeVO = warehouseMasterService.getAllCellType(orgid);
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
				errorMsg = "Celltype not found for ID: " + cellTypevo.getId();
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
	public ResponseEntity<ResponseDTO> getAllBranch(@RequestParam Long orgid) {
		String methodName = "getAllBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BranchVO> branchVO = new ArrayList<>();
		try {
			branchVO = warehouseMasterService.getAllBranch(orgid);
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
				errorMsg = "Branch not found for BranchID: " + branchVO.getId();
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
	public ResponseEntity<ResponseDTO> getAllCustomer(@RequestParam Long orgid) {
		String methodName = "getAllCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CustomerVO> customerVO = new ArrayList<>();
		try {
			customerVO = warehouseMasterService.getAllCustomer(orgid);
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
	public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody CustomerVO customerVO,
			@RequestBody ClientVO clientVO) {
		String methodName = "updateCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CustomerVO updatedCustomerVO = warehouseMasterService.updateCustomer(customerVO, clientVO).orElse(null);
			if (updatedCustomerVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer updated successfully");
				responseObjectsMap.put("customerVO", updatedCustomerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Customer not found for CustomerID: " + customerVO.getId();
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

	// Client

	@GetMapping("/client")
	public ResponseEntity<ResponseDTO> getAllClientByCustomer(@RequestParam Long orgid, @RequestParam String customer) {
		String methodName = "getAllClientByCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ClientVO> clientVO = new ArrayList<>();
		try {
			clientVO = warehouseMasterService.getAllClientByCustomer(orgid, customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Client information get successfully");
			responseObjectsMap.put("clientVO", clientVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Client information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Client Branch
	@GetMapping("/client/branch")
	public ResponseEntity<ResponseDTO> getAllBranchByCustomer(@RequestParam Long orgid, @RequestParam String customer) {
		String methodName = "getAllBranchByCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<ClientBranchVO> clientBranchVO = new ArrayList<>();
		try {
			clientBranchVO = warehouseMasterService.getAllClientBranchByCustomer(orgid, customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Client Branch information get successfully");
			responseObjectsMap.put("clientBranchVO", clientBranchVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Client Branch information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/client/branchaccess")
	public ResponseEntity<ResponseDTO> getAllClientByAccessBranch(@RequestParam Long orgid,
			@RequestParam String branchcode) {
		String methodName = "getAllClientByAccessBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> client = new HashSet<>();
		try {
			client = warehouseMasterService.getAllClientByOrgidAndAccessBranch(orgid, branchcode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getClient = formatClient(client);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Client  information get successfully");
			responseObjectsMap.put("clientVO", getClient);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Client information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatClient(Set<Object[]> client) {
		List<Map<String, String>> getClient = new ArrayList<>();
		for (Object[] cl : client) {
			Map<String, String> h = new HashMap<>();
			h.put("Client", cl[0].toString());
			h.put("ClientCode", cl[1].toString());
			getClient.add(h);
		}
		return getClient;
	}

	// Warehouse

	@GetMapping("/warehouse")
	public ResponseEntity<ResponseDTO> getAllWarehouse(@RequestParam Long orgid, @RequestParam String branch) {
		String methodName = "getAllWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseVO> warehouseVO = new ArrayList<>();
		try {
			warehouseVO = warehouseMasterService.getAllWarehouse(orgid, branch);
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

	// Get ALL Warehosue by branch
	@GetMapping("/warehouse/branch")
	public ResponseEntity<ResponseDTO> getAllWarehouseByBranch(@RequestParam Long orgid,
			@RequestParam String branchcode) {
		String methodName = "getAllWarehouseByBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> warehouseVO = new HashSet<>();
		try {
			warehouseVO = warehouseMasterService.getAllWarehouseByOrgidAndBranch(orgid, branchcode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getAllWarehosue = formateWarehouse(warehouseVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse information get successfully");
			responseObjectsMap.put("Warehouse", getAllWarehosue);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formateWarehouse(Set<Object[]> warehouseVO) {
		List<Map<String, String>> getAllWarehosue = new ArrayList<>();
		for (Object[] war : warehouseVO) {
			Map<String, String> getware = new HashMap<>();
			getware.put("Warehouse", war[0].toString());
			getAllWarehosue.add(getware);
		}
		return getAllWarehosue;
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
				errorMsg = "Warehouse not found for Warehouse ID: " + warehouseVO.getId();
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
	public ResponseEntity<ResponseDTO> getAllWarehouseLocation(@RequestParam Long orgid, @RequestParam String warehouse,
			@RequestParam String branch) {
		String methodName = "getAllWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseLocationVO> warehouseLocationVO = new ArrayList<>();
		try {
			warehouseLocationVO = warehouseMasterService.getAllWarehouseLocation(orgid, warehouse, branch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location information get successfully");
			responseObjectsMap.put("warehouseLocationVO", warehouseLocationVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Get location Type by Company and Warehouse
	@GetMapping("/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllLocationTypeByCompanyAndWarehouse(@RequestParam Long orgid,
			@RequestParam String warehouse) {

		String methodName = "getAllLocationTypeByCompanyAndWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> Locationtype = new HashSet<>();
		try {
			Locationtype = warehouseMasterService.getAllLocationTypebyOrgIdAndWarehouse(orgid, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>>getAlllocation=fomratLocation(Locationtype);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Location Type Founded");
			responseObjectsMap.put("Locationtype", getAlllocation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Location Type not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Location Type not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> fomratLocation(Set<Object[]> locationtype) {
		List<Map<String, String>> getAlllocation = new ArrayList<>();
		for (Object[] war : locationtype) {
			Map<String, String> getware = new HashMap<>();
			getware.put("ltype", war[0].toString());
			getAlllocation.add(getware);
		}
		return getAlllocation;
	}

	// Get Rowno based on Company warehouse and Location type
	@GetMapping("/rowno/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllRownoByCompanyAndWarehouseAndLocationType(@RequestParam Long orgid,
			@RequestParam String warehouse, @RequestParam String locationtype) {

		String methodName = "getAllRownoByCompanyAndWarehouseAndLocationType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> Rowno = new HashSet<>();
		try {
			Rowno = warehouseMasterService.getAllRownoByOrgIdAndWarehouseAndLocationType(orgid, warehouse,
					locationtype);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>>getAllRow=fomratRow(Rowno);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Rowno Founded");
			responseObjectsMap.put("Rowno", getAllRow);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Rowno not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Rowno not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> fomratRow(Set<Object[]> rowno) {
		List<Map<String, String>> getAllRow = new ArrayList<>();
		for (Object[] war : rowno) {
			Map<String, String> getware = new HashMap<>();
			getware.put("rowno", war[0].toString());
			getAllRow.add(getware);
		}
		return getAllRow;
	}

	// Get Level No based on Company warehouse,Location type and Rowno
	@GetMapping("/levelno/rowno/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllLevelnoByCompanyAndWarehouseAndLocationTypeAndRowno(
			@RequestParam Long orgid, @RequestParam String warehouse, @RequestParam String locationtype,
			@RequestParam String rowno) {
		String methodName = "getAllLevelnoByCompanyAndWarehouseAndLocationTypeAndRowno()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> Levelno = new HashSet<>();
		try {
			Levelno = warehouseMasterService.getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(orgid, warehouse,
					locationtype, rowno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>>getAllLevel=fomratLevel(Levelno);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Levelno Founded");
			responseObjectsMap.put("Levelno", getAllLevel);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Levelno not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Levelno not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> fomratLevel(Set<Object[]> levelno) {
		List<Map<String, String>> getAllLevel = new ArrayList<>();
		for (Object[] war : levelno) {
			Map<String, String> getware = new HashMap<>();
			getware.put("level", war[0].toString());
			getAllLevel.add(getware);
		}
		return getAllLevel;
	}

	// Get Bins based on Company warehouse,Location type, Rowno and level
	@GetMapping("/bins/levelno/rowno/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllbinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(
			@RequestParam Long orgid, @RequestParam String warehouse, @RequestParam String locationtype,
			@RequestParam String rowno, @RequestParam String level) {
		String methodName = "getAllbinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> bins = new HashSet<>();
		try {
			bins = warehouseMasterService.getAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(orgid, warehouse,
					locationtype, rowno, level);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedBins = formatBins(bins);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Bins Founded");
			responseObjectsMap.put("Bins", formattedBins);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Bins not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Bins not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatBins(Set<Object[]> bins) {
		List<Map<String, String>> formattedBins = new ArrayList<>();
		for (Object[] bin : bins) {
			Map<String, String> formattedBin = new HashMap<>();
			formattedBin.put("id", bin[0].toString());
			formattedBin.put("rowno", bin[1].toString());
			formattedBin.put("level", bin[2].toString());
			formattedBin.put("bin", bin[3].toString());
			formattedBin.put("core", bin[4].toString());
			formattedBin.put("status", bin[5].toString());
			formattedBins.add(formattedBin);
		}
		return formattedBins;
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
			WarehouseLocationVO createWarehouseLocation = warehouseMasterService
					.createWarehouseLocation(warehouseLocationVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location created successfully");
			responseObjectsMap.put("WarehouseLocation", createWarehouseLocation);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location creation Failed",
					errorMsg);
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
			WarehouseLocationVO updatedwarehouseLocationVO = warehouseMasterService
					.updateWarehouseLocation(warehouseLocationVO).orElse(null);
			if (updatedwarehouseLocationVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse Location updated successfully");
				responseObjectsMap.put("warehouseLocationVO", updatedwarehouseLocationVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Warehouse Location not found for Warehouse LocationID: " + warehouseLocationVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location update failed",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Warehouse Location Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Material

	@GetMapping("/material")
	public ResponseEntity<ResponseDTO> getAllMaterials(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String cbranch) {
		String methodName = "getAllMaterials()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialVO> materialVO = new ArrayList<>();
		try {
			materialVO = warehouseMasterService.getAllMaterialsByOrgIdAndClientAndCbranch(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Material information get successfully");
			responseObjectsMap.put("materialVO", materialVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Material information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/material/{materialid}")
	public ResponseEntity<ResponseDTO> getMaterialById(@PathVariable Long materialid) {
		String methodName = "getMaterialById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		MaterialVO materialVO = null;
		try {
			materialVO = warehouseMasterService.getMaterialById(materialid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Material found by ID");
			responseObjectsMap.put("materialVO", materialVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Material not found for ID: " + materialid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Material not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/material")
	public ResponseEntity<ResponseDTO> createMaterial(@RequestBody MaterialVO materialVO) {
		String methodName = "createMaterial()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			MaterialVO createMaterialVO = warehouseMasterService.createMaterial(materialVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Material created successfully");
			responseObjectsMap.put("Material", createMaterialVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Material creation Failed, Material Already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/material")
	public ResponseEntity<ResponseDTO> updateMaterial(@RequestBody MaterialVO materialVO) {
		String methodName = "updateWarehouseLocation()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			MaterialVO updatedmaterMaterialVO = warehouseMasterService.updateMaterial(materialVO).orElse(null);
			if (updatedmaterMaterialVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Material updated successfully");
				responseObjectsMap.put("updatedmaterMaterialVO", updatedmaterMaterialVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Material not found for Material ID: " + materialVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Material update failed, Material Already Exist", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Material Update failed,Material Already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Buyer

	@GetMapping("/buyer")
	public ResponseEntity<ResponseDTO> getAllBuyer(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String cbranch) {
		String methodName = "getAllBuyer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<BuyerVO> buyerVO = new ArrayList<>();
		try {
			buyerVO = warehouseMasterService.getAllBuyer(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer information get successfully");
			responseObjectsMap.put("buyerVO", buyerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Buyer information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/buyer/{buyerid}")
	public ResponseEntity<ResponseDTO> getBuyerById(@PathVariable Long buyerid) {
		String methodName = "getBuyerById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		BuyerVO buyerVO = null;
		try {
			buyerVO = warehouseMasterService.getBuyerById(buyerid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer found by ID");
			responseObjectsMap.put("Buyer", buyerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Buyer not found for ID: " + buyerid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Buyer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/buyer")
	public ResponseEntity<ResponseDTO> createBuyer(@RequestBody BuyerVO buyerVO) {
		String methodName = "createBuyer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BuyerVO createdBuyerVO = warehouseMasterService.createBuyer(buyerVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer created successfully");
			responseObjectsMap.put("BuyerVO", createdBuyerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"BuyerName, BuyerShortName and BranchCode Client already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/buyer")
	public ResponseEntity<ResponseDTO> updateBuyer(@RequestBody BuyerVO buyerVO) {
		String methodName = "updateBuyer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BuyerVO updatedBuyerVO = warehouseMasterService.updateBuyer(buyerVO).orElse(null);
			if (updatedBuyerVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer updated successfully");
				responseObjectsMap.put("BuyerVO", updatedBuyerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Buyer not found for Buyer ID: " + buyerVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Buyer update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"BuyerName, BuyerShortName and Client already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Supplier

	@GetMapping("/supplier")
	public ResponseEntity<ResponseDTO> getAllSupplier(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String cbranch) {
		String methodName = "getAllSupplier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<SupplierVO> supplierVO = new ArrayList<>();
		try {
			supplierVO = warehouseMasterService.getAllSupplier(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier information get successfully");
			responseObjectsMap.put("supplierVO", supplierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Supplier information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/supplier/{supplierid}")
	public ResponseEntity<ResponseDTO> getSupplierById(@PathVariable Long supplierid) {
		String methodName = "getSupplierById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		SupplierVO supplierVO = null;
		try {
			supplierVO = warehouseMasterService.getSupplierById(supplierid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier found by ID");
			responseObjectsMap.put("Supplier", supplierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Supplier not found for ID: " + supplierid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Supplier not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/supplier")
	public ResponseEntity<ResponseDTO> createSupplier(@RequestBody SupplierVO supplierVO) {
		String methodName = "createSupplier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			SupplierVO createdSupplierVO = warehouseMasterService.createSupplier(supplierVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier created successfully");
			responseObjectsMap.put("SupplierVO", createdSupplierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SupplierName, SupplierShortName, SupplierCode & Client already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/supplier")
	public ResponseEntity<ResponseDTO> updateSupplier(@RequestBody SupplierVO supplierVO) {
		String methodName = "updateSupplier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			SupplierVO updatedSupplierVO = warehouseMasterService.updateSupplier(supplierVO).orElse(null);
			if (updatedSupplierVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier updated successfully");
				responseObjectsMap.put("SupplierVO", updatedSupplierVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Supplier not found for Supplier ID: " + supplierVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Supplier update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"SupplierName, SupplierShortName and Client already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// LocationMapping

	@GetMapping("/locationmapping")
	public ResponseEntity<ResponseDTO> getAllLocationMapping(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String branch, @RequestParam String warehouse) {
		String methodName = "getAllLocationMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<LocationMappingVO> locationMappingVO = new ArrayList<>();
		try {
			locationMappingVO = warehouseMasterService.getAllLocationMapping(orgid, client, branch, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMapping information get successfully");
			responseObjectsMap.put("locationMappingVO", locationMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMapping information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/locationmapping/{locationmappingid}")
	public ResponseEntity<ResponseDTO> getLocationMappingById(@PathVariable Long locationmappingid) {
		String methodName = "getLocationMappingById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LocationMappingVO locationMappingVO = null;
		try {
			locationMappingVO = warehouseMasterService.getLocationMappingById(locationmappingid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMapping found by ID");
			responseObjectsMap.put("LocationMapping", locationMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "LocationMapping not found for ID: " + locationmappingid;
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMapping not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/Locationmapping")
	public ResponseEntity<ResponseDTO> createLocationMapping(@RequestBody LocationMappingVO locationMappingVO) {
		String methodName = "createLocationMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LocationMappingVO createdLocationMappingVO = warehouseMasterService
					.createLocationMapping(locationMappingVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMapping created successfully");
			responseObjectsMap.put("LocationMappingVO", createdLocationMappingVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMapping & branch already Exist ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/locationmapping")
	public ResponseEntity<ResponseDTO> updateLocationMapping(@RequestBody LocationMappingVO locationMappingVO) {
		String methodName = "updateLocationMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LocationMappingVO updatedLocationMappingVO = warehouseMasterService.updateLocationMapping(locationMappingVO)
					.orElse(null);
			if (updatedLocationMappingVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationMapping updated successfully");
				responseObjectsMap.put("LocationMappingVO", updatedLocationMappingVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "LocationMapping not found for LocationMapping ID: " + locationMappingVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "LocationMapping update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "LocationMapping & branch already Exist",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// Carrier

	@GetMapping("/carrier")
	public ResponseEntity<ResponseDTO> getAllCarrier(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String cbranch) {
		String methodName = "getAllCarrier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CarrierVO> carrierVO = new ArrayList<>();
		try {
			carrierVO = warehouseMasterService.getAllCarrier(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Carrier information get successfully");
			responseObjectsMap.put("carrierVO", carrierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Carrier information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/carrier/{carrierid}")
	public ResponseEntity<ResponseDTO> getCarrierById(@PathVariable Long carrierid) {
		String methodName = "getCarrierById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CarrierVO carrierVO = null;
		try {
			carrierVO = warehouseMasterService.getCarrierById(carrierid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Carrier found by ID");
			responseObjectsMap.put("Carrier", carrierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Carrier not found for ID: " + carrierid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Carrier not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/carrier")
	public ResponseEntity<ResponseDTO> createCarrier(@RequestBody CarrierVO carrierVO) {
		String methodName = "createCarrier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CarrierVO createdCarrierVO = warehouseMasterService.createCarrier(carrierVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Carrier created successfully");
			responseObjectsMap.put("CarrierVO", createdCarrierVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Carrier, Company & CarrierShortName already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/carrier")
	public ResponseEntity<ResponseDTO> updateCarrier(@RequestBody CarrierVO carrierVO) {
		String methodName = "updateCarrier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CarrierVO updatedCarrierVO = warehouseMasterService.updateCarrier(carrierVO).orElse(null);
			if (updatedCarrierVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Carrier updated successfully");
				responseObjectsMap.put("CarrierVO", updatedCarrierVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Carrier not found for Carrier ID: " + carrierVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Carrier update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Carrier, Company & CarrearShortName already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// Employee

	@GetMapping("/employee")
	public ResponseEntity<ResponseDTO> getAllEmployee(@RequestParam Long orgid) {
		String methodName = "getAllEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = warehouseMasterService.getAllEmployeeByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee information get successfully");
			responseObjectsMap.put("employeeVO", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/employee/{employeeid}")
	public ResponseEntity<ResponseDTO> getEmployeeById(@PathVariable Long employeeid) {
		String methodName = "getEmployeeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		EmployeeVO employeeVO = null;
		try {
			employeeVO = warehouseMasterService.getEmployeeById(employeeid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee found by ID");
			responseObjectsMap.put("Employee", employeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Employee not found for ID: " + employeeid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Employee not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/employee")
	public ResponseEntity<ResponseDTO> createEmployee(@RequestBody EmployeeVO employeeVO) {
		String methodName = "createEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmployeeVO createdEmployeeVO = warehouseMasterService.createEmployee(employeeVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee created successfully");
			responseObjectsMap.put("EmployeeVO", createdEmployeeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, " Company & EmployeeCode already Exist ",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/employee")
	public ResponseEntity<ResponseDTO> updateEmployee(@RequestBody EmployeeVO employeeVO) {
		String methodName = "updateEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			EmployeeVO updatedEmployeeVO = warehouseMasterService.updateEmployee(employeeVO).orElse(null);
			if (updatedEmployeeVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Employee updated successfully");
				responseObjectsMap.put("EmployeeVO", updatedEmployeeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Employee not found for Employee ID: " + employeeVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Employee update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Company & EmployeeCode already Exist",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//getAllNameAndEmployeeCodeByCompany

	@GetMapping("/getAllNameAndEmployeeCodeByCompany")
	public ResponseEntity<ResponseDTO> getAllNameAndEmployeeCodeByCompany(@RequestParam Long orgid) {

		String methodName = "getAllNameAndEmployeeCodeByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> employeeVO = new HashSet<>();
		try {
			employeeVO = warehouseMasterService.getAllNameAndEmployeeCodeByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedEmployees = formatEmployees(employeeVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "employee Founded");
			responseObjectsMap.put("Employee", formattedEmployees);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "employee not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "employee not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatEmployees(Set<Object[]> employeeVO) {
		List<Map<String, String>> formattedEmployees = new ArrayList<>();
		for (Object[] employee : employeeVO) {
			Map<String, String> formattedEmployee = new HashMap<>();
			formattedEmployee.put("EmployeeCode", employee[0].toString());
			formattedEmployee.put("EmployeeName", employee[1].toString());
			formattedEmployees.add(formattedEmployee);
		}
		return formattedEmployees;
	}

	// findAllCustomerAndClientByCompany

	@GetMapping("/getAllCustomerAndClientByCompany")
	public ResponseEntity<ResponseDTO> getAllCustomerAndClientByCompany(@RequestParam Long orgid) {

		String methodName = "getAllCustomerAndClientByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> customerVO = new HashSet<>();
		try {
			customerVO = warehouseMasterService.getAllCustomerAndClientByOrgId(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedCustomers = formatCustomers(customerVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "customer Founded");
			responseObjectsMap.put("Customer", formattedCustomers);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "customer not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "customer not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatCustomers(Set<Object[]> customerVO) {
		List<Map<String, String>> formattedCustomers = new ArrayList<>();
		for (Object[] customer : customerVO) {
			Map<String, String> formattedCustomer = new HashMap<>();
			formattedCustomer.put("Customer", customer[0].toString());
			formattedCustomer.put("Client", customer[1].toString());
			formattedCustomers.add(formattedCustomer);
		}
		return formattedCustomers;
	}

	// getAllBranchCodeAndBranchByCompany

	@GetMapping("/getAllBranchCodeAndBranchByCompany")
	public ResponseEntity<ResponseDTO> getAllBranchCodeAndBranchByCompany(@RequestParam String client,
			@RequestParam Long orgid) {

		String methodName = "getAllBranchCodeAndBranchByCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> branchVO = new HashSet<>();
		try {
			branchVO = warehouseMasterService.getAllBranchCodeAndBranchByOrgId(client, orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedBranches = formatBranches(branchVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Branch Founded");
			responseObjectsMap.put("Branch", formattedBranches);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "branch not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "branch not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatBranches(Set<Object[]> branchVO) {
		List<Map<String, String>> formattedBranches = new ArrayList<>();
		for (Object[] branch : branchVO) {
			Map<String, String> formattedBranch = new HashMap<>();
			formattedBranch.put("BranchCode", branch[0].toString());
			formattedBranches.add(formattedBranch);
		}
		return formattedBranches;
	}
	
	
	// Get Palletno from Rowno,Level,And Start and End no
	
	@GetMapping("/getPalletno")
	public ResponseEntity<ResponseDTO> getPalletno(@RequestParam String rowno,@RequestParam String level,@RequestParam int startno,@RequestParam int endno) {
		String methodName = "getPalletno()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> pallet = new HashSet<>();
		try {
			pallet = warehouseMasterService.getPalletnoByRownoAndLevelAndStartAndEnd(rowno, level, startno, endno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> palletno = formatPallet(pallet);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Pallet Founded");
			responseObjectsMap.put("pallet", palletno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Pallet not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Pallet not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatPallet(Set<Object[]> pallet) {
		List<Map<String, String>> palletno = new ArrayList<>();
		for (Object[] plt : pallet) {
			Map<String, String> formattedplt = new HashMap<>();
			formattedplt.put("id", plt[0].toString());
			formattedplt.put("Bin", plt[1].toString());
			formattedplt.put("cellcategory", plt[2].toString());
			formattedplt.put("status", plt[3].toString());
			formattedplt.put("core", plt[4].toString());
			palletno.add(formattedplt);
		}
		return palletno;
	}

}
