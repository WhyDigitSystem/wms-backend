package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);

	CountryVO createCountry(CountryVO countryVO); // Return the created entity

	Optional<CountryVO> updateCountry(CountryVO countryVO); // The parameter name should be camelCase

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates();

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(String country);

	StateVO createState(StateVO stateVO);

	Optional<StateVO> updateState(StateVO stateVO);

	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities();

	Optional<CityVO> getCityById(Long cityid);

	CityVO createCity(CityVO cityVO);

	Optional<CityVO> updateCity(CityVO cityVO);

	void deleteCity(Long cityid);

	// region

	List<RegionVO> getAllRegion();

	Optional<RegionVO> getRegionById(Long regionid);

	RegionVO createRegion(RegionVO regionVO);

	Optional<RegionVO> updateRegion(RegionVO regionVO);

	void deleteRegion(Long regionid);

	

	// Company

	List<CompanyVO> getAllCompany();

	Optional<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyVO companyVO);

	Optional<CompanyVO> updateCompany(CompanyVO companyVO);

	void deleteCompany(Long companyid);

	
	// Global Parameter
	
	GlobalParameterVO createGlobaParameter(GlobalParameterVO globalParam);

	Optional<GlobalParameterVO> updateGlobaParameter(GlobalParameterVO globalParameterVO);
	
	
}


