package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UserVO;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);
	
	Set<Object[]>getCountryAndCountryid(Long orgid);

	CountryVO createCountry(CountryVO countryVO); // Return the created entity

	Optional<CountryVO> updateCountry(CountryVO countryVO); // The parameter name should be camelCase

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates(Long orgid);

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(Long orgid,String country);

	StateVO createState(StateVO stateVO);

	Optional<StateVO> updateState(StateVO stateVO);

	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities(Long orgid);

	List<CityVO> getAllCitiesByState(Long orgid,String state);
	
	Optional<CityVO> getCityById(Long cityid);

	
	CityVO createCity(CityVO cityVO);

	Optional<CityVO> updateCity(CityVO cityVO);

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

	CompanyVO createCompany(CompanyVO companyVO) throws Exception;

	Optional<CompanyVO> updateCompany(CompanyVO companyVO);

	void deleteCompany(Long companyid);

	// Global Parameter
	
	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid,String username);
	
	GlobalParameterVO updateGlobaParameter(GlobalParameterVO globalParameterVO);
	
	// to getAcces Global Param Dteails
	
	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid,String userName);
	
	List<CustomerVO>getAllAccessCustomerForLogin(Long orgid,String userName,String branchcode);
	
	List<ClientVO>getAllAccessClientForLogin(Long orgid,String userName,String branchcode,String customer);
	
	
}
