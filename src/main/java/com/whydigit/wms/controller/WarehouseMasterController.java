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
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
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
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Warehouse Location information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/warehouselocation/company")
	public ResponseEntity<ResponseDTO> getAllWarehouseLocationByCompany(@RequestParam String Company) {

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

	// Get location Type by Company and Warehouse
	@GetMapping("/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllLocationTypeByCompanyAndWarehouse(@RequestParam String company,
			@RequestParam String warehouse) {

		String methodName = "getAllLocationTypeByCompanyAndWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object> Locationtype = new HashSet<Object>();
		try {
			Locationtype = warehouseMasterService.getAllLocationTypebyCompanyAndWarehouse(company, warehouse);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Location Type Founded");
			responseObjectsMap.put("Locationtype", Locationtype);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Location Type not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Location Type not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Get Rowno based on Company warehouse and Location type
	@GetMapping("/rowno/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllRownoByCompanyAndWarehouseAndLocationType(@RequestParam String company,
			@RequestParam String warehouse, @RequestParam String locationtype) {

		String methodName = "getAllRownoByCompanyAndWarehouseAndLocationType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object> Rowno = new HashSet<Object>();
		try {
			Rowno = warehouseMasterService.getAllRownoByCompanyAndWarehouseAndLocationType(company, warehouse,
					locationtype);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Rowno Founded");
			responseObjectsMap.put("Rowno", Rowno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Rowno not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Rowno not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Get Level No based on Company warehouse,Location type and Rowno
	@GetMapping("/levelno/rowno/locationtype/warehouse")
	public ResponseEntity<ResponseDTO> getAllLevelnoByCompanyAndWarehouseAndLocationTypeAndRowno(
			@RequestParam String company, @RequestParam String warehouse, @RequestParam String locationtype,
			@RequestParam String rowno) {
		String methodName = "getAllLevelnoByCompanyAndWarehouseAndLocationTypeAndRowno()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object> Levelno = new HashSet<Object>();
		try {
			Levelno = warehouseMasterService.getAllLevelByCompanyAndWarehouseAndLocationTypeAndRowno(company,warehouse,locationtype, rowno);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Levelno Founded");
			responseObjectsMap.put("Levelno", Levelno);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Levelno not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Levelno not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	// Get Bins based on Company warehouse,Location type, Rowno and level
		@GetMapping("/bins/levelno/rowno/locationtype/warehouse")
		public ResponseEntity<ResponseDTO> getAllbinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(
	            @RequestParam String company, @RequestParam String warehouse, @RequestParam String locationtype,
	            @RequestParam String rowno, @RequestParam String level) {
	        String methodName = "getAllbinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel()";
	        LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	        String errorMsg = null;
	        Map<String, Object> responseObjectsMap = new HashMap<>();
	        ResponseDTO responseDTO = null;
	        Set<Object[]> bins = new HashSet<>();
	        try {
	            bins = warehouseMasterService.getAllBinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(company, warehouse, locationtype, rowno, level);
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
	            formattedBin.put("Rowno", bin[0].toString());
	            formattedBin.put("Level", bin[1].toString());
	            formattedBin.put("Bin", bin[2].toString());
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
				errorMsg = "Warehouse Location not found for Warehouse LocationID: "
						+ warehouseLocationVO.getWarehouselocationid();
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

	// Warehouse Location

	@GetMapping("/material")
	public ResponseEntity<ResponseDTO> getAllMaterials() {
		String methodName = "getAllMaterials()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialVO> materialVO = new ArrayList<>();
		try {
			materialVO = warehouseMasterService.getAllMaterials();
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

	@GetMapping("/material/company/client")
	public ResponseEntity<ResponseDTO> getAllMaterialsByCompanyAndClient(@RequestParam String company,
			@RequestParam String client) {
		String methodName = "getAllMaterialsByCompanyAndClient()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<MaterialVO> materialVO = new ArrayList<>();
		try {
			materialVO = warehouseMasterService.getAllMaterialsByCompanyAndClient(company, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Material founded");
			responseObjectsMap.put("materialVO", materialVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Material not found ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Materials not found", errorMsg);
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
				errorMsg = "Material not found for Material ID: " + materialVO.getMaterialid();
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
		public ResponseEntity<ResponseDTO> getAllBuyer() {
			String methodName = "getAllBuyer()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<BuyerVO> buyerVO = new ArrayList<>();
			try {
				buyerVO = warehouseMasterService.getAllBuyer();
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer information get successfully");
				responseObjectsMap.put("buyerVO", buyerVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Buyer information receive failed",
						errorMsg);
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
					errorMsg = "Buyer not found for Buyer ID: " + buyerVO.getBuyerid();
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

}
