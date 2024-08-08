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
import com.whydigit.wms.dto.BranchDTO;
import com.whydigit.wms.dto.BuyerDTO;
import com.whydigit.wms.dto.CarrierDTO;
import com.whydigit.wms.dto.CellTypeDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.DocumentTypeDTO;
import com.whydigit.wms.dto.DocumentTypeMappingDTO;
import com.whydigit.wms.dto.EmployeeDTO;
import com.whydigit.wms.dto.GroupDTO;
import com.whydigit.wms.dto.LocationMappingDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.SupplierDTO;
import com.whydigit.wms.dto.UnitDTO;
import com.whydigit.wms.dto.WarehouseDTO;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.DocumentTypeVO;
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

@RequestMapping("/api/warehousemastercontroller")
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

	@PutMapping("/createUpdateGroup")
	public ResponseEntity<ResponseDTO> createUpdateGroup(@RequestBody GroupDTO groupDTO) {
		String methodName = "createUpdateGroup()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdGroupVO = warehouseMasterService.createUpdateGroup(groupDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdGroupVO.get("message"));
			responseObjectsMap.put("createdGroupVO", createdGroupVO.get("groupVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}


	// Unit

	@GetMapping("/getAllUnitByOrgId")
	public ResponseEntity<ResponseDTO> getAllUnitByOrgId(@RequestParam Long orgid) {
		String methodName = "getAllUnitByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UnitVO> unitVO = new ArrayList<>();
		try {
			unitVO = warehouseMasterService.getAllUnitByOrgId(orgid);
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

	@PutMapping("/createUpdateUnit")
	public ResponseEntity<ResponseDTO> createUpdateUnit(@RequestBody UnitDTO unitDTO) {
	    String methodName = "createUpdateUnit()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> createdUnitVO = warehouseMasterService.createUpdateUnit(unitDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdUnitVO.get("message"));
	        responseObjectsMap.put("unitVO", createdUnitVO.get("unitVO")); // Corrected key
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}


	@GetMapping("/getAllUnit")
	public ResponseEntity<ResponseDTO> getAllUnit() {
		String methodName = "getAllUnit()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<UnitVO> unitVO = new ArrayList<UnitVO>();
		try {
			unitVO = warehouseMasterService.getAllUnit();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Unit Information Get Sucessfully");
			responseObjectsMap.put("unitVO", unitVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Unit not found for Unit ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "Unit Information not found", errorMsg);
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

	@PutMapping("/createUpdateLocationType")
	public ResponseEntity<ResponseDTO> createUpdateLocationType(@RequestBody LocationTypeDTO locationTypeDTO) {
		String methodName = "createUpdateLocationType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			LocationTypeVO createdLocationType = warehouseMasterService.createUpdateLocationType(locationTypeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType created successfully");
			responseObjectsMap.put("createdLocationTypeVO", createdLocationType);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}


	// CellType

	@GetMapping("/getAllCellTypeByOrgId")
	public ResponseEntity<ResponseDTO> getAllCellTypeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllCellTypeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CellTypeVO> cellTypeVO = new ArrayList<>();
		try {
			cellTypeVO = warehouseMasterService.getAllCellTypeByOrgId(orgId);
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

	@GetMapping("/getAllCellType")
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

	
	@GetMapping("/cellType/{cellTypeId}")
	public ResponseEntity<ResponseDTO> getCellTypeById(@PathVariable Long cellTypeId) {
		String methodName = "getCellTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CellTypeVO cellTypeVO = null;
		try {
			cellTypeVO = warehouseMasterService.getCellTypeById(cellTypeId).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType found by CellType ID");
			responseObjectsMap.put("cellTypeVO", cellTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "CellType not found for CellType ID: " + cellTypeId;
			responseDTO = createServiceResponseError(responseObjectsMap, "CellType not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateCellType")
	public ResponseEntity<ResponseDTO> createUpdateCellType(@RequestBody CellTypeDTO cellTypeDTO) {
		String methodName = "createCellType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdCellTypeVO = warehouseMasterService.createUpdateCellType(cellTypeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdCellTypeVO.get("message"));
			responseObjectsMap.put("createdCellTypeVO", createdCellTypeVO.get("cellTypeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
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
 
	@PostMapping("/createUpdateBranch")
	public ResponseEntity<ResponseDTO> createUpdateBranch(@RequestBody BranchDTO branchDTO) {
		String methodName = "createBranch()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdBranchVO = warehouseMasterService.createUpdateBranch(branchDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdBranchVO.get("message"));
			responseObjectsMap.put("branchVO", createdBranchVO.get("branchVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
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

	@PostMapping("/createUpdateCustomer")
	public ResponseEntity<ResponseDTO> createUpdateCustomer(@RequestBody CustomerDTO customerDTO) {
		String methodName = "createUpdateCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdCustomerVO = warehouseMasterService.createUpdateCustomer(customerDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customer created successfully");
			responseObjectsMap.put("customerVO", createdCustomerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/getClientAndClientCodeByOrgId")
	public ResponseEntity<ResponseDTO> getClientAndClientCodeByOrgId(@RequestParam Long orgId) {
		String methodName = "getClientAndClientCodeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> customerVO = new ArrayList<>();
		try {
			customerVO = warehouseMasterService.getClientAndClientCodeByOrgId(orgId);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Clint And ClientCode information get successfully By OrgId");
			responseObjectsMap.put("CustomerVO", customerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Clint And ClientCode information receive failed",
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
	public ResponseEntity<ResponseDTO> getAllWarehouse(@RequestParam Long orgId) {
		String methodName = "getAllWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<WarehouseVO> warehouseVO = new ArrayList<>();
		try {
			warehouseVO = warehouseMasterService.getAllWarehouse(orgId);
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
	

	@GetMapping("/getAllWarehouseByOrgId")
	public ResponseEntity<ResponseDTO> getAllWarehouseByOrgId(@RequestParam Long orgid, @RequestParam String branch) {
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

	@PutMapping("/createUpdateWarehouse")
	public ResponseEntity<ResponseDTO> createUpdateWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
		String methodName = "createWarehouse()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			WarehouseVO createdWarehouseVO = warehouseMasterService.createUpdateWarehouse(warehouseDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Warehouse created successfully");
			responseObjectsMap.put("warehouseVO", createdWarehouseVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
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
			List<Map<String, String>> getAlllocation = fomratLocation(Locationtype);
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
			List<Map<String, String>> getAllRow = fomratRow(Rowno);
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
			List<Map<String, String>> getAllLevel = fomratLevel(Levelno);
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
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
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

	@PutMapping("/createUpdateMaterial")
	public ResponseEntity<ResponseDTO> createUpdateMaterial(@RequestBody MaterialDTO materialDTO) {
		String methodName = "createUpdateMaterial()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			MaterialVO createMaterialVO = warehouseMasterService.createUpdateMaterial(materialDTO);
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
	
	@GetMapping("/getPartNo")
	public ResponseEntity<ResponseDTO> getPartNo(
			@RequestParam(required = true) Long orgId,
			@RequestParam(required = true) String client,
			@RequestParam(required = true) String branch,
			@RequestParam(required = true) String branchCode,
			@RequestParam(required = true) String customer) {
		String methodName = "getPartNo()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> getPart = new ArrayList<>();
		try {
			getPart = warehouseMasterService.getPartNo(orgId,client,branch,branchCode,customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "PartNo found successfully");
			responseObjectsMap.put("PartNo", getPart);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = " not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "PartNo not found", errorMsg);
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

	@PutMapping("/buyer")
	public ResponseEntity<ResponseDTO> createUpdateBuyer(@RequestBody BuyerDTO buyerDTO) {
		String methodName = "createUpdateBuyer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			BuyerVO createdBuyerVO = warehouseMasterService.createUpdateBuyer(buyerDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Buyer created successfully");
			responseObjectsMap.put("BuyerVO", createdBuyerVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
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

	@GetMapping("/getActiveSupplierNameByCustomer")
	public ResponseEntity<ResponseDTO> getActiveSupplierNameByCustomer(@RequestParam Long orgid,
			@RequestParam String client, @RequestParam String cbranch) {
		String methodName = "getActiveSupplierNameByCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<Map<String, Object>> supplier = new ArrayList<>();
		try {
			supplier = warehouseMasterService.getActiveSupplierNameByCustomer(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Supplier Name Founded");
			responseObjectsMap.put("Supplier", supplier);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Supplier Name not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Supplier Name not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/createUpdateSupplier")
	public ResponseEntity<ResponseDTO> createUpdateSupplier(@RequestBody SupplierDTO supplierDTO) {
		String methodName = "createUpdateSupplier()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdSupplierVO = warehouseMasterService.createUpdateSupplier(supplierDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdSupplierVO.get("message") );
			responseObjectsMap.put("SupplierVO", createdSupplierVO.get("supplierVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,errorMsg, errorMsg);
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

	@PutMapping("/createUpdateLocationMapping")
	public ResponseEntity<ResponseDTO> createUpdateLocationMapping(@RequestBody LocationMappingDTO locationMappingDTO) {
		String methodName = "createUpdateLocationMapping()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdLocationMappingVO = warehouseMasterService
					.createUpdateLocationMapping(locationMappingDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdLocationMappingVO.get("message"));
			responseObjectsMap.put("LocationMappingVO", createdLocationMappingVO.get("locationMappingVO"));
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

	@GetMapping("/getCarrierNameByCustomer")
	public ResponseEntity<ResponseDTO> getCarrierNameByCustomer(@RequestParam Long orgid, @RequestParam String client,
			@RequestParam String cbranch) {
		String methodName = "getCarrierNameByCustomer()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> carrier = new HashSet<>();
		try {
			carrier = warehouseMasterService.getCarrierNameByCustomer(orgid, client, cbranch);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			List<Map<String, String>> formattedBranches = formattBranchess(carrier);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Carrier Name Founded");
			responseObjectsMap.put("Carrier", formattedBranches);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Carrier Name not found";
			responseDTO = createServiceResponseError(responseObjectsMap, "Carrier Name not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formattBranchess(Set<Object[]> carrier) {
		List<Map<String, String>> formattedBranches = new ArrayList<>();
		for (Object[] carrierVO : carrier) {
			Map<String, String> formattedBranch = new HashMap<>();
			formattedBranch.put("carriername", carrierVO[0].toString());
//			formattedBranch.put("carriershortname", supplierVO[1].toString());
			formattedBranches.add(formattedBranch);
		}
		return formattedBranches;
	}

	@PutMapping("/createUpdateCarrier")
	public ResponseEntity<ResponseDTO> createUpdateCarrier(@RequestBody CarrierDTO carrierDTO) {
	    String methodName = "createUpdateCarrier()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> createdCarrierVO = warehouseMasterService.createUpdateCarrier(carrierDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, createdCarrierVO.get("message"));
	        responseObjectsMap.put("CarrierVO", createdCarrierVO.get("carrierVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}

	// Employee

	@GetMapping("/getAllEmployeeByOrgId")
	public ResponseEntity<ResponseDTO> getAllEmployeeByOrgId(@RequestParam Long orgId) {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = warehouseMasterService.getAllEmployeeByOrgId(orgId);
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

	@GetMapping("/getAllEmployee")
	public ResponseEntity<ResponseDTO> getAllEmployee() {
		String methodName = "getAllEmployeeByOrgId()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<EmployeeVO> employeeVO = new ArrayList<>();
		try {
			employeeVO = warehouseMasterService.getAllEmployee();
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

	@PutMapping("/createUpdateEmployee")
	public ResponseEntity<ResponseDTO> createUpdateEmployee(@RequestBody EmployeeDTO employeeDTO) {
		String methodName = "createEmployee()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			Map<String, Object> createdEmployeeVO = warehouseMasterService.createEmployee(employeeDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,createdEmployeeVO.get("message") );
			responseObjectsMap.put("employeeVO", createdEmployeeVO.get("createdEmployeeVO"));
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg,
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

	// Get Palletno from Rowno,Level,And Start and End No

	@GetMapping("/getPalletno")
	public ResponseEntity<ResponseDTO> getPalletno(@RequestParam String rowno, @RequestParam String level,
			@RequestParam int startno, @RequestParam int endno) {
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
	
	// Document type
	
	@PutMapping("/createUpdateDocumentType")
	public ResponseEntity<ResponseDTO> createUpdateDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) {
	    String methodName = "createUpdateDocumentType()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> documentType1 = warehouseMasterService.createUpdateDocumentType(documentTypeDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentType1.get("message"));
	        responseObjectsMap.put("documentTypeVO", documentType1.get("documentTypeVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/documentTypeById")
	public ResponseEntity<ResponseDTO> getdocumentTypeById(@RequestParam Long id) {
		String methodName = "getdocumentTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		DocumentTypeVO documentTypeVO = new DocumentTypeVO();
		try {
			documentTypeVO = warehouseMasterService.getDocumentTypeById(id);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"DocumentType information get successfully");
			responseObjectsMap.put("documentTypeVO", documentTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"DocumentTypeVO information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getAllDocumentType")
	public ResponseEntity<ResponseDTO> getAllDocumentType(@RequestParam Long orgid) {
		String methodName = "getAllDocumentType()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<DocumentTypeVO> documentTypeVO = new ArrayList<>();
		try {
			documentTypeVO = warehouseMasterService.getAllDocumentTypeByOrgId(orgid);	
			} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
			responseObjectsMap.put("documentTypeVO", documentTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PutMapping("/createDocumentTypeMapping")
	public ResponseEntity<ResponseDTO> createDocumentTypeMapping(@RequestBody DocumentTypeMappingDTO documentTypeMappingDTO) {
	    String methodName = "createDocumentTypeMapping()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        Map<String, Object> documentTypeMappingVO = warehouseMasterService.createDocumentTypeMapping(documentTypeMappingDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentTypeMappingVO.get("message"));
	        responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO.get("documentTypeMappingVO"));
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}
	
	@GetMapping("/getGRNDocId")
	public ResponseEntity<ResponseDTO> getGRNDocId(@RequestParam String branch,@RequestParam String client,@RequestParam String finYear) {
		String methodName = "getGRNDocId()";
		String screenCode="PC";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		String grnDocId="";
		try {
			 grnDocId = warehouseMasterService.getDocIdForGRN(branch, client, finYear, screenCode);	
			} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
			responseObjectsMap.put("grnDocId", grnDocId);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	
}
