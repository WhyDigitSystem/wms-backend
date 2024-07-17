package com.whydigit.wms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.whydigit.wms.dto.CityDTO;
import com.whydigit.wms.dto.CompanyDTO;
import com.whydigit.wms.dto.CountryDTO;
import com.whydigit.wms.dto.ResponseDTO;
import com.whydigit.wms.dto.StateDTO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.service.CommonMasterService;

@RestController
@RequestMapping("/api/commonmaster")
public class CommonMasterController extends BaseController {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterController.class);

	@Autowired
	CommonMasterService commonMasterService;

	@GetMapping("/country")
	public ResponseEntity<ResponseDTO> getAllcountries(@RequestParam Long orgid) {
		String methodName = "getAllCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CountryVO> countryVO = new ArrayList<>();
		try {
			countryVO = commonMasterService.getAllCountry(orgid);
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

//	@GetMapping("/country/countryid")
//	public ResponseEntity<ResponseDTO> getAllcountriesAndCountrid(@RequestParam Long orgid) {
//		String methodName = "getAllcountriesAndCountrid()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> country = new HashSet<>();
//		try {
//			country = commonMasterService.getCountryAndCountryid(orgid);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String, String>> formattedCountry = formattCountry(country);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "countries information get successfully");
//			responseObjectsMap.put("country", formattedCountry);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap, "countries information receive failed",
//					errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}
//
//	private List<Map<String, String>> formattCountry(Set<Object[]> country) {
//		List<Map<String, String>> formattedCountry = new ArrayList<>();
//		for (Object[] formateCountry : country) {
//			Map<String, String> FormatCountry = new HashMap<>();
//			FormatCountry.put("countryid", formateCountry[0].toString());
//			FormatCountry.put("country", formateCountry[1].toString());
//			formattedCountry.add(FormatCountry);
//		}
//		return formattedCountry;
//	}

	@GetMapping("/country/{countryid}")
	public ResponseEntity<ResponseDTO> getCountryById(@PathVariable Long countryid) {
		String methodName = "getCountryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CountryVO CountryVO = null;
		try {
			CountryVO = commonMasterService.getCountryById(countryid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Country found by ID");
			responseObjectsMap.put("Country", CountryVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Country not found for ID: " + countryid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Country not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/createUpdateCountry")
	public ResponseEntity<ResponseDTO> createUpdateCountry(@RequestBody CountryDTO countryDTO) {
		String methodName = "createCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CountryVO createdCountryVO = commonMasterService.createUpdateCountry(countryDTO);
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
				errorMsg = "Country not found for CountryID: " + countryVO.getId();
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
	public ResponseEntity<ResponseDTO> getAllStates(@RequestParam Long orgid) {
		String methodName = "getAllStates()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StateVO> stateVO = new ArrayList<>();
		try {
			stateVO = commonMasterService.getAllgetAllStates(orgid);
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

	@GetMapping("/state/{stateid}")
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

	@GetMapping("/state/country")
	public ResponseEntity<ResponseDTO> getStateByCountry(@RequestParam Long orgid, @RequestParam String country) {
		String methodName = "getStateByCountry()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<StateVO> stateVO = null;
		try {
			stateVO = commonMasterService.getStatesByCountry(orgid, country);
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
	public ResponseEntity<ResponseDTO> createUpdateState(@RequestBody StateDTO stateDTO) {
	    String methodName = "createUpdateState()";
	    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
	    String errorMsg = null;
	    Map<String, Object> responseObjectsMap = new HashMap<>();
	    ResponseDTO responseDTO = null;
	    try {
	        StateVO stateVO = commonMasterService.createUpdateState(stateDTO);
	        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "State created successfully");
	        responseObjectsMap.put("stateVO", stateVO);
	        responseDTO = createServiceResponse(responseObjectsMap);
	    } catch (ApplicationException e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap,
	                "State creation failed. State Name or State Code already exists",
	                errorMsg);
	    } catch (Exception e) {
	        errorMsg = e.getMessage();
	        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
	        responseDTO = createServiceResponseError(responseObjectsMap,
	                "State creation failed due to an unexpected error",
	                errorMsg);
	    }
	    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
	    return ResponseEntity.ok().body(responseDTO);
	}


	// city

	@GetMapping("/city")
	public ResponseEntity<ResponseDTO> getAllCities(@RequestParam Long orgid) {
		String methodName = "getAllCities()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CityVO> cityVO = new ArrayList<>();
		try {
			cityVO = commonMasterService.getAllgetAllCities(orgid);
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

	@GetMapping("/city/state")
	public ResponseEntity<ResponseDTO> getAllCitiesByState(@RequestParam Long orgid, @RequestParam String state) {
		String methodName = "getAllCitiesByState()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CityVO> cityVO = new ArrayList<>();
		try {
			cityVO = commonMasterService.getAllCitiesByState(orgid, state);
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

	@GetMapping("/city/{cityid}")
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

	@PostMapping("/createUpdateCity")
	public ResponseEntity<ResponseDTO> createUpdateCity(@RequestBody CityDTO cityDTO) {
		String methodName = "createCity()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CityVO createdCityVO = commonMasterService.createUpdateCity(cityDTO);
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


	// Region

	@GetMapping("/region")
	public ResponseEntity<ResponseDTO> getAllCRegions(@RequestParam Long orgid) {
		String methodName = "getAllCRegions()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<RegionVO> regionVO = new ArrayList<>();
		try {
			regionVO = commonMasterService.getAllRegion(orgid);
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

	@GetMapping("/region/{regionid}")
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
			if (regionVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Region Updated successfully");
				responseObjectsMap.put("regionvo", regionvo);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "City not found for ID: " + regionvo.getId();
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

	// Currency

	@GetMapping("/currency")
	public ResponseEntity<ResponseDTO> getAllCurrency(@RequestParam Long orgid) {
		String methodName = "getAllCurrency()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CurrencyVO> currencyVO = new ArrayList<>();
		try {
			currencyVO = commonMasterService.getAllCurrency(orgid);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency" + " information get successfully");
			responseObjectsMap.put("currencyVO", currencyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Currency information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// getCUrrencyById

	@GetMapping("/currency/{currencyid}")
	public ResponseEntity<ResponseDTO> getCurrencyById(@PathVariable Long currencyid) {
		String methodName = "getCurrencyById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CurrencyVO currencyVO = null;
		try {
			currencyVO = commonMasterService.getCurrencyById(currencyid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency found by Currency ID");
			responseObjectsMap.put("currencyVO", currencyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "Currency not found for Currency ID: " + currencyid;
			responseDTO = createServiceResponseError(responseObjectsMap, "Currency not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/currency")
	public ResponseEntity<ResponseDTO> createCurrency(@RequestBody CurrencyVO currencyVO) {
		String methodName = "createCurrency()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CurrencyVO currency = commonMasterService.createCurrency(currencyVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency created successfully");
			responseObjectsMap.put("currency", currency);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Currency creation failed. Currency already Exist ", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/currency")
	public ResponseEntity<ResponseDTO> updateCurrency(@RequestBody CurrencyVO currencyVO) {
		String methodName = "updateCurrency()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CurrencyVO updatedCurrencyVO = commonMasterService.updateCurrency(currencyVO).orElse(null);
			if (updatedCurrencyVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Currency updated successfully");
				responseObjectsMap.put("updatedCurrencyVO", updatedCurrencyVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Currency not found for State ID: " + currencyVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap,
						"Currency Update failed, Currency already Exist", errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Currency Update failed, Currency already Exist", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Company

	@GetMapping("/company")
	public ResponseEntity<ResponseDTO> getAllCompany() {
		String methodName = "getAllCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		List<CompanyVO> companyVO = new ArrayList<>();
		try {
			companyVO = commonMasterService.getAllCompany();
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company information get successfully");
			responseObjectsMap.put("companyVO", companyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap, "Company information receive failed",
					errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@GetMapping("/company/{companyid}")
	public ResponseEntity<ResponseDTO> getcompanyById(@PathVariable Long companyid) {
		String methodName = "getCompanyById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		CompanyVO companyVO = null;
		try {
			companyVO = commonMasterService.getCompanyById(companyid).orElse(null);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}

		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company found by Company ID");
			responseObjectsMap.put("companyVO", companyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "company not found for companyID: " + companyid;
			responseDTO = createServiceResponseError(responseObjectsMap, "company not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PostMapping("/company")
	public ResponseEntity<ResponseDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
		String methodName = "createCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CompanyVO createdCompanyVO = commonMasterService.createCompany(companyDTO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Company created successfully");
			responseObjectsMap.put("createdCompanyVO", createdCompanyVO);
			responseDTO = createServiceResponse(responseObjectsMap);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Company creation failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping("/updateCompany")
	public ResponseEntity<ResponseDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {
		String methodName = "updateCompany()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			CompanyVO updatedCompanyVO = commonMasterService.updateCompany(companyDTO);
			if (updatedCompanyVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Customers updated successfully");
				responseObjectsMap.put("CompanyVO", updatedCompanyVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Organization not found for ID: " + companyDTO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}
	// Global Parameter

	@GetMapping("/globalparamBranchByUserName")
	public ResponseEntity<ResponseDTO> getGlobalParameterBranchByUserName(@RequestParam Long orgid,
			@RequestParam String userName) {
		String methodName = "getAllGlobalParameterByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> globalParameters = new HashSet<>();
		try {
			globalParameters = commonMasterService.getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(orgid,
					userName);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> formattedParameters = formattParameter(globalParameters);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Branch information get successfully");
			responseObjectsMap.put("GlopalParameters", formattedParameters);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Branch information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formattParameter(Set<Object[]> globalParameters) {
		List<Map<String, String>> formattedParameters = new ArrayList<>();
		for (Object[] parameters : globalParameters) {
			Map<String, String> param = new HashMap<>();
			param.put("branch", parameters[0].toString());
			param.put("branchcode", parameters[1].toString());
			formattedParameters.add(param);
		}
		return formattedParameters;
	}

	// get Access Customers

	@GetMapping("/globalparamCustomerByUserName")
	public ResponseEntity<ResponseDTO> getGlobalParameterCustomerByUserName(@RequestParam Long orgid,
			@RequestParam String userName, @RequestParam String branchcode) {
		String methodName = "getGlobalParameterCustomerByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> customerVO = new HashSet<>();
		try {
			customerVO = commonMasterService.getAllAccessCustomerForLogin(orgid, userName, branchcode);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getCustomer = formatCustomer(customerVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Customer information get successfully");
			responseObjectsMap.put("GlopalParameterCustomer", getCustomer);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Customer information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	private List<Map<String, String>> formatCustomer(Set<Object[]> customerVO) {
		List<Map<String, String>> getCustomer = new ArrayList<>();
		for (Object[] parameters : customerVO) {
			Map<String, String> param = new HashMap<>();
			param.put("customer", parameters[0].toString());
			getCustomer.add(param);
		}
		return getCustomer;
	}

	@GetMapping("/globalparamClientByUserName")
	public ResponseEntity<ResponseDTO> getGlobalParameterClientByUserName(@RequestParam Long orgid,
			@RequestParam String userName, @RequestParam String branchcode, @RequestParam String customer) {
		String methodName = "getGlobalParameterClientByUserName()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> clientVO = new HashSet<>();
		try {
			clientVO = commonMasterService.getAllAccessClientForLogin(orgid, userName, branchcode, customer);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getClient = format(clientVO);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Client information get successfully");
			responseObjectsMap.put("GlopalParameterClient", getClient);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Client information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// get Global Param

	private List<Map<String, String>> format(Set<Object[]> clientVO) {
		List<Map<String, String>> getClient = new ArrayList<>();
		for (Object[] parameters : clientVO) {
			Map<String, String> param = new HashMap<>();
			param.put("customer", parameters[0].toString());
			getClient.add(param);
		}
		return getClient;
	}

	@GetMapping("/globalparam/username")
	public ResponseEntity<ResponseDTO> getGlobalParamByOrgIdAndUserName(@RequestParam Long orgid,
			@RequestParam String username) {
		String methodName = "getCountryById()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Optional<GlobalParameterVO> globalparam = null;
		try {
			globalparam = commonMasterService.getGlobalParamByOrgIdAndUserName(orgid, username);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isEmpty(errorMsg)) {
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "GlobalParam found by ID");
			responseObjectsMap.put("globalParam", globalparam);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			errorMsg = "GlobalParam not found for ID: ";
			responseDTO = createServiceResponseError(responseObjectsMap, "GlobalParam not found", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

//	@GetMapping("/globalparamCustomerByOrgAndBranchcode")
//	public ResponseEntity<ResponseDTO> globalparamCustomerByOrgAndBranchcode(@RequestParam Long orgid,@RequestParam String branchcode) {
//		String methodName = "globalparamCustomerByOrgAndBranchcode()";
//		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
//		String errorMsg = null;
//		Map<String, Object> responseObjectsMap = new HashMap<>();
//		ResponseDTO responseDTO = null;
//		Set<Object[]> globalCustomer = new HashSet<>();
//		try {
//			globalCustomer = commonMasterService.getGlobalParametersCustomerByOrgIdAndBranchcode(orgid, branchcode);
//		} catch (Exception e) {
//			errorMsg = e.getMessage();
//			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
//		}
//		if (StringUtils.isBlank(errorMsg)) {
//			List<Map<String,String>>formattedCustomers=formattCustomer(globalCustomer);
//			responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Global Parameter Cusomer information get successfully");
//			responseObjectsMap.put("GlopalParametersCustomer", formattedCustomers);
//			responseDTO = createServiceResponse(responseObjectsMap);
//		} else {
//			responseDTO = createServiceResponseError(responseObjectsMap, "Global Parameter Customer information receive failed",
//					errorMsg);
//		}
//		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
//		return ResponseEntity.ok().body(responseDTO);
//	}

//	private List<Map<String, String>> formattCustomer(Set<Object[]> globalCustomer) {
//		List<Map<String, String>> formattedCustomers = new ArrayList<>();
//		for (Object[] customer : globalCustomer) {
//			Map<String, String> cust = new HashMap<>();
//			cust.put("customer", customer[0].toString());
//			formattedCustomers.add(cust);
//		}
//		return formattedCustomers;
//	}
	
	@GetMapping("/getWarehouseNameByOrgIdAndBranchAndClient")
	public ResponseEntity<ResponseDTO> getWarehouseNameByOrgIdAndBranchAndClient(@RequestParam Long orgid,
			@RequestParam String branch, @RequestParam String client) {
		String methodName = "getWarehouseNameByOrgIdAndBranchAndClient()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		Set<Object[]> global = new HashSet<>();
		try {
			global = commonMasterService.getWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		}
		if (StringUtils.isBlank(errorMsg)) {
			List<Map<String, String>> getGlobal = formatt(global);
			responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
					"Global Parameter Warehouse information get successfully");
			responseObjectsMap.put("GlopalParameterWarehouse", getGlobal);
			responseDTO = createServiceResponse(responseObjectsMap);
		} else {
			responseDTO = createServiceResponseError(responseObjectsMap,
					"Global Parameter Warehouse information receive failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// get Global Param

	private List<Map<String, String>> formatt(Set<Object[]> global) {
		List<Map<String, String>> getglobal = new ArrayList<>();
		for (Object[] parameters : global) {
			Map<String, String> param = new HashMap<>();
			param.put("warehouse", parameters[0].toString());
			getglobal.add(param);
		}
		return getglobal;
	}

	@PutMapping("/globalparam")
	public ResponseEntity<ResponseDTO> updateGlobalParam(@RequestBody GlobalParameterVO globalParameterVO) {
		String methodName = "updateGlobalParam()";
		LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		String errorMsg = null;
		Map<String, Object> responseObjectsMap = new HashMap<>();
		ResponseDTO responseDTO = null;
		try {
			GlobalParameterVO gloParameterVO = commonMasterService.updateGlobaParameter(globalParameterVO);
			if (gloParameterVO != null) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Global Parameter Updated successfully");
				responseObjectsMap.put("GlobalParameterVO", gloParameterVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				errorMsg = "Global Parameter not found for ID: " + globalParameterVO.getId();
				responseDTO = createServiceResponseError(responseObjectsMap, "Global Parameter Update failed",
						errorMsg);
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			responseDTO = createServiceResponseError(responseObjectsMap, "Global Parameter Update failed", errorMsg);
		}
		LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		return ResponseEntity.ok().body(responseDTO);
	}

	// Create Client Access

}
