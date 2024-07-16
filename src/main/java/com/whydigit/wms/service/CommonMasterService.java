package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CityDTO;
import com.whydigit.wms.dto.CompanyDTO;
import com.whydigit.wms.dto.CountryDTO;
import com.whydigit.wms.dto.StateDTO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);
	
	//Set<Object[]>getCountryAndCountryid(Long orgid);

	CountryVO createUpdateCountry(CountryDTO countryDTO) throws ApplicationException; // Return the created entity

	Optional<CountryVO> updateCountry(CountryVO countryVO); // The parameter name should be camelCase

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates(Long orgid);

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(Long orgid,String country);

	StateVO createUpdateState(StateDTO stateDTO) throws ApplicationException;


	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities(Long orgid);

	List<CityVO> getAllCitiesByState(Long orgid,String state);
	
	Optional<CityVO> getCityById(Long cityid);

	
	CityVO createUpdateCity(CityDTO cityDTO) throws ApplicationException;

	//Optional<CityVO> updateCity(CityVO cityVO);

	void deleteCity(Long cityid);
	
	// Currency
	
	List<CurrencyVO> getAllCurrency(Long orgid);

	Optional<CurrencyVO> getCurrencyById(Long currencyid);
	
	CurrencyVO createCurrency(CurrencyVO currencyVO);

	Optional<CurrencyVO> updateCurrency(CurrencyVO currencyVO);

	void deleteCurrency(Long currencyid);
	

	// region

	List<RegionVO> getAllRegion(Long orgid);

	Optional<RegionVO> getRegionById(Long regionid);

	RegionVO createRegion(RegionVO regionVO);

	Optional<RegionVO> updateRegion(RegionVO regionVO);

	void deleteRegion(Long regionid);

	

	// Company

	List<CompanyVO> getAllCompany();

	Optional<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyDTO companyDTO) throws Exception;

	Optional<CompanyVO> updateCompany(CompanyVO companyVO);

	void deleteCompany(Long companyid);

	
	// Global Parameter
	
	Set<Object[]>getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client);
	
	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid,String username);
	
	GlobalParameterVO updateGlobaParameter(GlobalParameterVO globalParameterVO);
	
		
	// to getAcces Global Param Dteails
	
	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid,String userName);
	
	Set<Object[]>getAllAccessCustomerForLogin(Long orgid,String userName,String branchcode);
	
	Set<Object[]>getAllAccessClientForLogin(Long orgid,String userName,String branchcode,String customer);
	
	
	
	
	

}
