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
import org.springframework.web.bind.annotation.RestController;

import com.whydigit.wms.common.CommonConstant;
import com.whydigit.wms.common.UserConstants;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.service.CommonMasterService;

@RestController
@RequestMapping("/api")
public class CommonMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);

	@Autowired
	CommonMasterService commonMasterService;

	@GetMapping("/country")
	public ResponseEntity<ResponseDTO> getAllcountries() {
		String methodName = "getAllCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CountryVO> countryVO = new ArrayList<>();
		try {
			countryVO = commonMasterService.getAllCountry();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "countries information get successfully");
			responseObjectsMap.put("countryVO", countryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "countries information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/country/{id}")
	public ResponseEntity<ResponseDTO> getCountryById(@PathVariable Long countryid) {
		String methodName = "getCountryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CountryVO countryVO = null;
		try {
			countryVO = commonMasterService.getCountryById(countryid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Country found by ID");
			responseObjectsMap.put("countryVO", countryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Countries not found for CountryID: " + countryid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Country not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/country")
	public ResponseEntity<ResponseDTO> createCountry(@RequestBody CountryVO countryVO) {
		String methodName = "createCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CountryVO createdCountryVO = commonMasterService.createCountry(countryVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Country created successfully");
			responseObjectsMap.put("countryVO", createdCountryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Country creation failed. Country Name or Country Code already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/country")
	public ResponseEntity<ResponseDTO> updateCountry(@RequestBody CountryVO countryVO) {
		String methodName = "updateCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CountryVO updatedCountryVO = commonMasterService.updateCountry(countryVO).orElse(null);
			if (updatedCountryVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Country updated successfully");
				responseObjectsMap.put("countryVO", updatedCountryVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Country not found for CountryID: " + countryVO.getCountryid();
				responseDTO = createServiceResponseError(responseObjectsMap, "Country update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Country Update failed, Country Name or Country Code already Exisit", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// State

	@GetMapping("/state")
	public ResponseEntity<ResponseDTO> getAllStates() {
		String methodName = "getAllStates()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StateVO> stateVO = new ArrayList<>();
		try {
			stateVO = commonMasterService.getAllgetAllStates();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "State information get successfully");
			responseObjectsMap.put("stateVO", stateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "States information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// getStateByStateId

	@GetMapping("/state/{id}")
	public ResponseEntity<ResponseDTO> getStateById(@PathVariable Long stateid) {
		String methodName = "getStateById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		StateVO stateVO = null;
		try {
			stateVO = commonMasterService.getStateById(stateid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "States found by State ID");
			responseObjectsMap.put("stateVO", stateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "State not found for State ID: " + stateid;
			responseDTO = createServiceResponseError(responseObjectsMap, "States not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/state/country/{country}")
	public ResponseEntity<ResponseDTO> getStateByCountry(@PathVariable String country) {
		String methodName = "getStateByCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StateVO> stateVO = null;
		try {
			stateVO = commonMasterService.getStatesByCountry(country);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "States found by Country");
			responseObjectsMap.put("stateVO", stateVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "State not found for country: " + country;
			responseDTO = createServiceResponseError(responseObjectsMap, "States not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/state")
	public ResponseEntity<ResponseDTO> createState(@RequestBody StateVO stateVO) {
		String methodName = "createState()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StateVO statevO = commonMasterService.createState(stateVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "State created successfully");
			responseObjectsMap.put("stateVO", statevO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"State creation failed. State Name or State Code already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/state")
	public ResponseEntity<ResponseDTO> updateState(@RequestBody StateVO stateVO) {
		String methodName = "updateState()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			StateVO updatedStateVO = commonMasterService.updateState(stateVO).orElse(null);
			if (updatedStateVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "State updated successfully");
				responseObjectsMap.put("countryVO", updatedStateVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "State not found for State ID: " + stateVO.getStateid();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"State Update failed, StateName and State Code Should Not Duplicate", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"State Update failed, StateName and State Code Should Not Duplicate", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// city

	@GetMapping("/city")
	public ResponseEntity<ResponseDTO> getAllCities() {
		String methodName = "getAllCities()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CityVO> cityVO = new ArrayList<>();
		try {
			cityVO = commonMasterService.getAllgetAllCities();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "city information get successfully");
			responseObjectsMap.put("cityVO", cityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "city information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/city/{id}")
	public ResponseEntity<ResponseDTO> getCityById(@PathVariable Long cityid) {
		String methodName = "getCityById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CityVO cityVO = null;
		try {
			cityVO = commonMasterService.getCityById(cityid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "City found by City ID");
			responseObjectsMap.put("cityVO", cityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "City not found for City ID: " + cityid;
			responseDTO = createServiceResponseError(responseObjectsMap, "City not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/city")
	public ResponseEntity<ResponseDTO> createCity(@RequestBody CityVO cityVO) {
		String methodName = "createCity()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CityVO createdCityVO = commonMasterService.createCity(cityVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "city created successfully");
			responseObjectsMap.put("cityVO", createdCityVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"City creation failed, CityName and City Code Should Not Duplicate", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/city")
	public ResponseEntity<ResponseDTO> updateCity(@RequestBody CityVO cityVO) {
		String methodName = "updateCity()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CityVO updatedCityVO = commonMasterService.updateCity(cityVO).orElse(null);
			if (updatedCityVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "City Updated successfully");
				responseObjectsMap.put("cityVO", updatedCityVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "City not found for ID: " + cityVO.getCityid();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"City Update failed, CityName and City Code Should Not Duplicate", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"City Update failed, CityName and City Code Should Not Duplicate", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Region

	@GetMapping("/region")
	public ResponseEntity<ResponseDTO> getAllCRegions() {
		String methodName = "getAllCRegions()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RegionVO> regionVO = new ArrayList<>();
		try {
			regionVO = commonMasterService.getAllRegion();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Region information get successfully");
			responseObjectsMap.put("regionVO", regionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Region information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/region/{id}")
	public ResponseEntity<ResponseDTO> getRegionById(@PathVariable Long regionid) {
		String methodName = "getRegionById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		RegionVO regionVO = null;
		try {
			regionVO = commonMasterService.getRegionById(regionid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Region found by Region ID");
			responseObjectsMap.put("regionVO", regionVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Region not found for Region ID: " + regionid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Region not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/region")
	public ResponseEntity<ResponseDTO> createRegion(@RequestBody RegionVO regionVO) {
		String methodName = "createRegion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			RegionVO regionvo = commonMasterService.createRegion(regionVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Region created successfully");
			responseObjectsMap.put("regionvo", regionvo);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Region creation failed, Region and Region Code Should Not Duplicate", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/region")
	public ResponseEntity<ResponseDTO> updateRegion(@RequestBody RegionVO regionVO) {
		String methodName = "updateRegion()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			RegionVO regionvo = commonMasterService.updateRegion(regionVO).orElse(null);
			if (regionvo != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Region Updated successfully");
				responseObjectsMap.put("regionvo", regionvo);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "City not found for ID: " + regionvo.getRegionid();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Region Update failed, Region and Region Code Should Not Duplicate", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Region Update failed, Region and Region Code Should Not Duplicate", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

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
			groupVO = commonMasterService.getAllGroup();
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

	@GetMapping("/group/{id}")
	public ResponseEntity<ResponseDTO> getGroupById(@PathVariable Long groupid) {
		String methodName = "getGroupById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		GroupVO groupVO = null;
		try {
			groupVO = commonMasterService.getGroupById(groupid).orElse(null);
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
			GroupVO createdGroupVO = commonMasterService.createGroup(groupVO);
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
			GroupVO groupvo = commonMasterService.updateGroup(groupVO).orElse(null);
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
			unitVO = commonMasterService.getAllUnit();
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

	@GetMapping("/unit/{id}")
	public ResponseEntity<ResponseDTO> getUnitById(@PathVariable Long unitid) {
		String methodName = "getUnitById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		UnitVO unitVO = null;
		try {
			unitVO = commonMasterService.getUnitById(unitid).orElse(null);
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
			UnitVO createdUnitVO = commonMasterService.createUnit(unitVO);
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
			UnitVO unitvo = commonMasterService.updateUnit(unitVO).orElse(null);
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
			locationTypeVO = commonMasterService.getAllLocationType();
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

	@GetMapping("/locationType/{id}")
	public ResponseEntity<ResponseDTO> getLocationTypeById(@PathVariable Long locationtypeid) {
		String methodName = "getLocationTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		LocationTypeVO locationTypeVO = null;
		try {
			locationTypeVO = commonMasterService.getLocationTypeById(locationtypeid).orElse(null);
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
			LocationTypeVO createdLocationTypeVO = commonMasterService.createLocationType(locationTypeVO);
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
			LocationTypeVO locationTypevo = commonMasterService.updateLocationType(locationTypeVO).orElse(null);
			if (locationTypeVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "LocationType Updated successfully");
				responseObjectsMap.put("locationTypeVO", locationTypevo);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "LocationType not found for ID: " + locationTypevo.getLocationtype();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"LocationType Update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"LocationType Name Update failed", errorMsg);
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
			cellTypeVO = commonMasterService.getAllCellType();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType information get successfully");
			responseObjectsMap.put("cellTypeVO", cellTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "CellType information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/cellType/{id}")
	public ResponseEntity<ResponseDTO> getCellTypeById(@PathVariable Long celltypeid) {
		String methodName = "getCellTypeById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CellTypeVO cellTypeVO = null;
		try {
			cellTypeVO = commonMasterService.getCellTypeById(celltypeid).orElse(null);
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
			CellTypeVO createdCellTypeVO = commonMasterService.createCellType(cellTypeVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType created successfully");
			responseObjectsMap.put("createdCellTypeVO", createdCellTypeVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CellType creation failed", errorMsg);
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
			CellTypeVO cellTypevo= commonMasterService.updateCellType(cellTypeVO).orElse(null);
			if (cellTypeVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "CellType Updated successfully");
				responseObjectsMap.put("cellTypeVO", cellTypevo);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Celltype not found for ID: " + cellTypevo.getCelltypeid();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"CellType Update failed", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"CellType Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
}
