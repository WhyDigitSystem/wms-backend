package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CityDTO;
import com.whydigit.wms.dto.CompanyDTO;
import com.whydigit.wms.dto.CountryDTO;
import com.whydigit.wms.dto.CurrencyDTO;
import com.whydigit.wms.dto.DesignationDTO;
import com.whydigit.wms.dto.FinancialYearDTO;
import com.whydigit.wms.dto.RegionDTO;
import com.whydigit.wms.dto.ScreenNamesDTO;
import com.whydigit.wms.dto.StateDTO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.DesignationVO;
import com.whydigit.wms.entity.FinancialYearVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.ScreenNamesVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface CommonMasterService {

	// Country

	List<CountryVO> getAllCountry(Long orgid); // Method names should be in camelCase

	Optional<CountryVO> getCountryById(Long countryid);
	
	Map<String, Object> createUpdateCountry(CountryDTO countryDTO) throws ApplicationException; // Return the created entity

	void deleteCountry(Long countryid);

	// State

	List<StateVO> getAllgetAllStates(Long orgid);

	Optional<StateVO> getStateById(Long stateid);

	List<StateVO> getStatesByCountry(Long orgid,String country);

	Map<String, Object> createUpdateState(StateDTO stateDTO) throws ApplicationException;


	void deleteState(Long stateid);

	// city

	List<CityVO> getAllgetAllCities(Long orgid);

	List<CityVO> getAllCitiesByState(Long orgid,String state);
	
	Optional<CityVO> getCityById(Long cityid);

	
	Map<String, Object> createUpdateCity(CityDTO cityDTO) throws ApplicationException;


	void deleteCity(Long cityid);
	
	// Currency
	
	List<CurrencyVO> getAllCurrency(Long orgid);

	Optional<CurrencyVO> getCurrencyById(Long currencyid);
	
	Map<String, Object> createUpdateCurrency(CurrencyDTO currencyDTO) throws ApplicationException;

	void deleteCurrency(Long currencyid);
	

	// region
	
	List<RegionVO> getAllRegios();

	List<RegionVO> getAllRegionsByOrgId(Long orgId);

	Optional<RegionVO> getRegionById(Long regionid);

	Map<String, Object> createUpdateRegion(RegionDTO regionDTO) throws ApplicationException;

	void deleteRegion(Long regionid);

	

	// Company

	List<CompanyVO> getAllCompany();

	Optional<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyDTO companyDTO) throws Exception;

	CompanyVO updateCompany(CompanyDTO companyDTO) throws ApplicationException;

	void deleteCompany(Long companyid);

	
	// Global Parameter
	
	Set<Object[]>getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client);
	
	Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid,String userId);
	
	GlobalParameterVO updateGlobaParameter(GlobalParameterVO globalParameterVO);
	
		
	// to getAcces Global Param Dteails
	
	Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid,String userName);
	
	Set<Object[]>getAllAccessCustomerForLogin(Long orgid,String userName,String branchcode);
	
	Set<Object[]>getAllAccessClientForLogin(Long orgid,String userName,String branchcode,String customer);
	
	
	Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException;
	
	List<ScreenNamesVO>getAllScreenNames();

	ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException;
	
	//Designation

	Map<String, Object> createUpdateDesignation(DesignationDTO designationDTO) throws ApplicationException;

	List<DesignationVO> getAllDesignation();
	
	List<DesignationVO> getAllDesignationByOrgId(Long OrdId);
	
	Optional<DesignationVO> getAllDesignationById(Long id);
	
// FINANCIAL YEAR
	
	Map<String, Object> createUpdateFinYear(FinancialYearDTO financialYearDTO) throws ApplicationException;

	List<FinancialYearVO> getAllFInYear();

	List<FinancialYearVO> getAllFInYearByOrgId(Long orgId);

	Optional<FinancialYearVO> getAllFInYearById(Long id);

}
