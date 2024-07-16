package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CityDTO;
import com.whydigit.wms.dto.CompanyDTO;
import com.whydigit.wms.dto.CountryDTO;
import com.whydigit.wms.dto.Role;
import com.whydigit.wms.dto.StateDTO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UserVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CityRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CompanyRepo;
import com.whydigit.wms.repo.CountryRepository;
import com.whydigit.wms.repo.CurrencyRepo;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.EmployeeRepo;
import com.whydigit.wms.repo.FinancialYearRepo;
import com.whydigit.wms.repo.GlobalParameterRepo;
import com.whydigit.wms.repo.RegionRepo;
import com.whydigit.wms.repo.StateRepo;
import com.whydigit.wms.repo.UserBranchAccessRepo;
import com.whydigit.wms.repo.UserClientAccessRepo;
import com.whydigit.wms.repo.UserRepo;
import com.whydigit.wms.util.CryptoUtils;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(CommonMasterServiceImpl.class);

	@Autowired
	CountryRepository countryVORepo;

	@Autowired
	CurrencyRepo currencyRepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	CityRepo cityRepo;

	@Autowired
	RegionRepo regionRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	GlobalParameterRepo globalParameterRepo;

	@Autowired
	CarrierRepo carrierRepo;

	@Autowired
	UserBranchAccessRepo userBranchAccessRepo;

	@Autowired
	UserClientAccessRepo userClientAccessRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ClientRepo clientRepo;

	// Country

	@Override
	public List<CountryVO> getAllCountry(Long orgid) {
		return countryVORepo.findAll(orgid);
	}

	@Override
	public Optional<CountryVO> getCountryById(Long countryid) {
		return countryVORepo.findById(countryid);
	}


	@Override
	public CountryVO createUpdateCountry(CountryDTO countryDTO) throws ApplicationException {

		CountryVO countryVO;

		if (countryVORepo.existsByCountryNameAndCountryCodeAndOrgId(countryDTO.getCountryName(),
				countryDTO.getCountryCode(), countryDTO.getOrgId())) {
			String errorMessage = String.format("The CountryName: %s and CountryCode: %s already exists This Organization.",
					countryDTO.getCountryName(), countryDTO.getCountryCode());
			throw new ApplicationException(errorMessage);
		}

		if (countryVORepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
			String errorMessage = String.format("The CountryName: %s already exists This Organization.",
					countryDTO.getCountryName());
			throw new ApplicationException(errorMessage);
		}

		if (countryVORepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
			String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
					countryDTO.getCountryCode());
			throw new ApplicationException(errorMessage);
		}

		if (countryDTO.getId() != null) {
			// Update existing branch
			countryVO = countryVORepo.findById(countryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + countryDTO.getId()));
		} else {
			// Create new branch
			countryVO = new CountryVO();
		}

		getCountryVOFromCounytryDTO(countryVO, countryDTO);

		return countryVORepo.save(countryVO);
	}

	private void getCountryVOFromCounytryDTO(CountryVO countryVO, CountryDTO countryDTO) {

		countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());
		countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
		countryVO.setActive(countryDTO.isActive());
		countryVO.setOrgId(countryDTO.getOrgId());
	//	countryVO.setUserId(countryDTO.getUserId());
		countryVO.setDupchk(countryDTO.getOrgId() + countryDTO.getCountryName() + countryDTO.getCountryCode());
		countryVO.setCreatedBy(countryDTO.getCreatedBy());
		countryVO.setUpdatedBy(countryDTO.getCreatedBy());
		countryVO.setCancel(countryDTO.isCancel());

	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getId())) {
			//countryVO.setUpdatedBy(countryVO.getUserId());
			countryVO.setCountryName(countryVO.getCountryName().toUpperCase());
			countryVO.setCountryCode(countryVO.getCountryCode().toUpperCase());
			countryVO.setDupchk(countryVO.getCountryCode() + countryVO.getCountryName());
			return Optional.of(countryVORepo.save(countryVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCountry(Long countryid) {

	}

	// State

	@Override
	public List<StateVO> getAllgetAllStates(Long orgid) {
		return stateRepo.findAllByOrgId(orgid);
	}

	@Override
	public Optional<StateVO> getStateById(Long stateid) {
		return stateRepo.findById(stateid);
	}

	@Override
	public List<StateVO> getStatesByCountry(Long orgid, String country) {

		return stateRepo.findByCountry(orgid, country);
	}

	@Override
	@Transactional
	public StateVO createUpdateState(StateDTO stateDTO) throws ApplicationException {
		StateVO stateVO ;
		
		if (stateRepo.existsByStateCodeAndStateNameAndStateNumberAndOrgId(
		        stateDTO.getStateCode(),
		        stateDTO.getStateName(),
		        stateDTO.getStateNumber(),
		        stateDTO.getOrgId())) {

		    String errorMessage = String.format(
		        "The StateCode: %s and StateName: %s and StateNumber: %s already exists in this Organization.",
		        stateDTO.getStateCode(),
		        stateDTO.getStateName(),
		        stateDTO.getStateNumber()
		    );
		    throw new ApplicationException(errorMessage);
		}
		
		if(stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(),stateDTO.getOrgId())) {
			String errorMessage = String.format("The StateCode: %s already exists This Organization.",
					stateDTO.getStateCode());
			throw new ApplicationException(errorMessage);
			
		}if(stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(),stateDTO.getOrgId())) {
			
			String errorMessage = String.format("The StateNumber: %s already exists This Organization.",
					stateDTO.getStateNumber());
			throw new ApplicationException(errorMessage);
			
		}if(stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(),stateDTO.getOrgId())) {
		

			String errorMessage = String.format("The StateName: %s already exists This Organization.",
					stateDTO.getStateName());
			throw new ApplicationException(errorMessage);
			
		}
		
		

		if (stateDTO.getId() != null) {
			// Update existing branch
			stateVO = stateRepo.findById(stateDTO.getId())
					.orElseThrow(() -> new ApplicationException("State not found with id: " + stateDTO.getId()));
		} else {
			// Create new branch
			stateVO = new StateVO();
		}

		getStateVOFromStateDTO(stateVO, stateDTO);

		return stateRepo.save(stateVO);
	}
	
	private void getStateVOFromStateDTO(StateVO stateVO, StateDTO stateDTO) {
		
		stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
		stateVO.setStateName(stateDTO.getStateName().toUpperCase());
		stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());
		stateVO.setCountry(stateDTO.getCountry().toUpperCase());
		stateVO.setRegion(stateDTO.getRegion().toUpperCase());
		stateVO.setCreatedBy(stateDTO.getCreatedBy());
		stateVO.setUpdatedBy(stateDTO.getCreatedBy());
		stateVO.setActive(stateDTO.isActive());
		stateVO.setCancel(stateDTO.isCancel());
		stateVO.setOrgId(stateDTO.getOrgId());
	//	stateVO.setDupchk(stateDTO.getOrgId()+stateDTO.getStateCode()+stateDTO.getStateName());
	}

	
	@Override
	public void deleteState(Long countryid) {
		stateRepo.deleteById(countryid);
	}

	// City

	@Override
	public List<CityVO> getAllgetAllCities(Long orgid) {
		return cityRepo.findAll(orgid);
	}

	@Override
	public List<CityVO> getAllCitiesByState(Long orgid, String state) {

		return cityRepo.findAll(orgid, state);
	}

	@Override
	public Optional<CityVO> getCityById(Long cityid) {
		return cityRepo.findById(cityid);
	}

	@Override
	@Transactional
	public CityVO createUpdateCity(CityDTO cityDTO) throws ApplicationException {
		CityVO cityVO;
		if (cityRepo.existsByCityCodeAndCityNameAndOrgId(cityDTO.getCityCode(), cityDTO.getCityName(),
				cityDTO.getOrgId())) {

			String errorMessage = String.format(
					"The CityCode: %s and CityName: %s already exists in this Organization.",
					cityDTO.getCityCode(), cityDTO.getCityName(), cityDTO.getOrgId());
			throw new ApplicationException(errorMessage);
		}

		if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
			String errorMessage = String.format("The CityCode: %s already exists This Organization.",
					cityDTO.getCityCode());
			throw new ApplicationException(errorMessage);

		}
		if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {

			String errorMessage = String.format("The CityName: %s already exists This Organization.",
					cityDTO.getCityName());
			throw new ApplicationException(errorMessage);

		}

		if (cityDTO.getId() != null) {
			// Update existing branch
			cityVO = cityRepo.findById(cityDTO.getId())
					.orElseThrow(() -> new ApplicationException("City not found with id: " + cityDTO.getId()));
		} else {
			// Create new branch
			cityVO = new CityVO();
		}

		getCityVOFromCityDTO(cityVO, cityDTO);

		return cityRepo.save(cityVO);
	}

	private void getCityVOFromCityDTO(CityVO cityVO, CityDTO cityDTO) {

		cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
		cityVO.setCityName(cityDTO.getCityName().toUpperCase());
		cityVO.setCountry(cityDTO.getCountry().toUpperCase());
		cityVO.setState(cityDTO.getState().toUpperCase());
		cityVO.setActive(cityDTO.isActive());
	//	cityVO.setDupchk(cityDTO.getOrgId() + cityDTO.getCityName() + cityDTO.getCityCode());
		cityVO.setCreatedBy(cityDTO.getCreatedBy());
		cityVO.setUpdatedBy(cityDTO.getCreatedBy());
		cityVO.setOrgId(cityDTO.getOrgId());
		cityVO.setCancel(cityDTO.isCancel());

	}


	@Override
	public void deleteCity(Long cityid) {
		cityRepo.deleteById(cityid);
	}

	// Region

	@Override
	public List<RegionVO> getAllRegion(Long orgid) {
		return regionRepo.findAll(orgid);
	}

	@Override
	public Optional<RegionVO> getRegionById(Long Regionid) {
		return regionRepo.findById(Regionid);
	}

	@Override
	public RegionVO createRegion(RegionVO regionVO) {
		regionVO.setCancel(false);
		regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
		regionVO.setRegionname(regionVO.getRegionname().toUpperCase());
		regionVO.setActive(true);
		regionVO.setDocid("MAA");
		regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegionname() + regionVO.getOrgId());
		return regionRepo.save(regionVO);
	}

	@Override
	public Optional<RegionVO> updateRegion(RegionVO regionVO) {
		if (regionRepo.existsById(regionVO.getId())) {
			regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
			regionVO.setRegionname(regionVO.getRegionname().toUpperCase());
			regionVO.setUpdatedby(regionVO.getUserid());
			regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegionname() + regionVO.getOrgId());
			return Optional.of(regionRepo.save(regionVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteRegion(Long regionid) {
		regionRepo.deleteById(regionid);
	}

	// Company

	@Override
	public List<CompanyVO> getAllCompany() {
		return companyRepo.findAll();
	}

	@Override
	public Optional<CompanyVO> getCompanyById(Long companyid) {
		return companyRepo.findById(companyid);
	}

	@Override
	@Transactional
	public CompanyVO createCompany(CompanyDTO companyDTO) throws Exception {

		if (companyRepo.existsByCompanyCode(companyDTO.getCompanyCode())) {
			throw new ApplicationException("The CompanyCode Already Exists");
		}

		if (companyRepo.existsByCompanyName(companyDTO.getCompanyName())) {
			throw new ApplicationException("The CompanyName Already Exists");
		}

		if (companyRepo.existsByEmployeeCode(companyDTO.getEmployeeCode())) {
			throw new ApplicationException("The EmployeeCode Already Exists");
		}

		CompanyVO companyVO = new CompanyVO();
		getCompanyVOFromCompanyDTO(companyVO, companyDTO);
		companyRepo.save(companyVO);

		UserVO userVO = new UserVO();
		userVO.setUserName(companyVO.getCompanyName());
		userVO.setEmployeeName(companyVO.getEmployeeName());
		userVO.setEmail(companyVO.getEmail());
		userVO.setMobileNo(companyVO.getPhone());
		userVO.setRole(Role.ROLE_USER);
		userVO.setCreatedby(companyVO.getCreatedBy());
		userVO.setUpdatedby(companyVO.getCreatedBy());
		userVO.setIsActive(true);
		userVO.setLoginStatus(false);
		userVO.setCompanyVO(companyVO);
//	        UserLoginRolesVO userLoginRolesVO=new UserLoginRolesVO();
//	        userLoginRolesVO.setRole(userVO.getRole());
//	        UserLoginBranchAccessibleVO userLoginBranchAccessibleVO=new UserLoginBranchAccessibleVO();
//	        userLoginBranchAccessibleVO.setBranch(companyVO.getCompanyName());
//	        userLoginBranchAccessibleVO.setBranchcode(companyVO.getCompanyCode());

		try {
			userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException("Unable To Encode Password");
		}

		userRepo.save(userVO);

		return companyVO;
	}

	private void getCompanyVOFromCompanyDTO(CompanyVO companyVO, CompanyDTO companyDTO) {
		companyVO.setCompanyCode(companyDTO.getCompanyCode());
		companyVO.setCompanyName(companyDTO.getCompanyName());
		companyVO.setCountry(companyDTO.getCountry());
		companyVO.setCurrency(companyDTO.getCurrency());
		companyVO.setMainCurrency(companyDTO.getMainCurrency());
		companyVO.setAddress(companyDTO.getAddress());
		companyVO.setZip(companyDTO.getZip());
		companyVO.setCity(companyDTO.getCity());
		companyVO.setState(companyDTO.getState());
		companyVO.setPhone(companyDTO.getPhone());
		companyVO.setEmail(companyDTO.getEmail());
		companyVO.setWebSite(companyDTO.getWebSite());
		companyVO.setNote(companyDTO.getNote());
		companyVO.setEmployeeCode(companyDTO.getEmployeeCode());
		companyVO.setEmployeeName(companyDTO.getEmployeeName());
		companyVO.setCreatedBy(companyDTO.getCreatedBy());
		companyVO.setUpdatedBy(companyDTO.getCreatedBy());
		companyVO.setCancel(companyDTO.isCancel());
		try {
			companyVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException("Unable To Encode Password");
		}
	}

	@Override
	public Optional<CompanyVO> updateCompany(CompanyVO companyVO) {
		if (companyRepo.existsById(companyVO.getId())) {
			companyVO.setCreatedBy(companyVO.getCreatedBy());
			return Optional.of(companyRepo.save(companyVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCompany(Long companyid) {
		companyRepo.deleteById(companyid);
	}

// Currency 
	@Override
	public List<CurrencyVO> getAllCurrency(Long orgid) {

		return currencyRepo.findAll(orgid);
	}

	@Override
	public Optional<CurrencyVO> getCurrencyById(Long currencyid) {

		return currencyRepo.findById(currencyid);
	}

	@Override
	public CurrencyVO createCurrency(CurrencyVO currencyVO) {
		currencyVO.setCancel(false);
		currencyVO.setDupchk(currencyVO.getOrgId() + currencyVO.getCountry() + currencyVO.getCurrency());
		return currencyRepo.save(currencyVO);
	}

	@Override
	public Optional<CurrencyVO> updateCurrency(CurrencyVO currencyVO) {
		if (currencyRepo.existsById(currencyVO.getId())) {
			currencyVO.setDupchk(currencyVO.getCountry() + currencyVO.getCurrency());
			return Optional.of(currencyRepo.save(currencyVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCurrency(Long currencyid) {
		currencyRepo.deleteById(currencyid);

	}

	// Global Parametre

	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, String username) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, username);
	}

	@Override
	public Set<Object[]> getWarehouseNameByOrgIdAndBranchAndClient(Long orgid, String branch, String client) {
		return globalParameterRepo.findWarehouseNameByOrgIdAndBranchAndClient(orgid, branch, client);
	}

	// Change Global Parameter or update Parameters
	@Override
	public GlobalParameterVO updateGlobaParameter(GlobalParameterVO globalParameterVO) {

		GlobalParameterVO existingRecord = globalParameterRepo.findGlobalParam(globalParameterVO.getOrgId(),
				globalParameterVO.getUserid());

		if (existingRecord != null) {
			// If the record exists, it's a PUT operation
			existingRecord.setBranch(globalParameterVO.getBranch());
			existingRecord.setBranchcode(globalParameterVO.getBranchcode());
			existingRecord.setCustomer(globalParameterVO.getCustomer());
			existingRecord.setClient(globalParameterVO.getClient());
			existingRecord.setWarehouse(globalParameterVO.getWarehouse());
			existingRecord.setOrgId(globalParameterVO.getOrgId());

			return globalParameterRepo.save(existingRecord);
		} else {
			// If the record doesn't exist, it's a POST operation
			return globalParameterRepo.save(globalParameterVO);
		}

	}

	// get access Branch
	@Override
	public Set<Object[]> getGlobalParametersBranchAndBranchCodeByOrgIdAndUserName(Long orgid, String userName) {

		return userBranchAccessRepo.findGlobalParametersBranchByUserName(orgid, userName);
	}

	@Override
	public Set<Object[]> getAllAccessCustomerForLogin(Long orgid, String userName, String branchcode) {

		return customerRepo.findAllAccessCustomerByUserName(orgid, userName, branchcode);
	}

	@Override
	public Set<Object[]> getAllAccessClientForLogin(Long orgid, String userName, String branchcode, String customer) {
		// TODO Auto-generated method stub
		return clientRepo.findAllAccessClientByUserName(orgid, userName, branchcode, customer);
	}

}
