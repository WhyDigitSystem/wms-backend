package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UserVO;
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
	public Set<Object[]> getCountryAndCountryid(Long orgid) {
		// TODO Auto-generated method stub
		return countryVORepo.findCountryAndCountryid(orgid);
	}

	@Override
	public CountryVO createCountry(CountryVO countryVO) {

		countryVO.setCancel(false);
		countryVO.setCountry(countryVO.getCountry().toUpperCase());
		countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
		countryVO.setActive(true);
		countryVO.setCancel(false);
		countryVO.setDupchk(countryVO.getOrgId() + countryVO.getCountrycode() + countryVO.getCountry());
		return countryVORepo.save(countryVO);

	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getId())) {
			countryVO.setUpdatedby(countryVO.getUserid());
			countryVO.setCountry(countryVO.getCountry().toUpperCase());
			countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
			countryVO.setDupchk(countryVO.getCountrycode() + countryVO.getCountry());
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
	public StateVO createState(StateVO stateVO) {
		stateVO.setCancel(false);
		stateVO.setState(stateVO.getState().toUpperCase());
		stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
		stateVO.setActive(true);
		stateVO.setDupchk(stateVO.getOrgId() + stateVO.getStatecode() + stateVO.getState());
		return stateRepo.save(stateVO);
	}

	@Override
	public Optional<StateVO> updateState(StateVO stateVO) {
		if (stateRepo.existsById(stateVO.getId())) {
			stateVO.setUpdatedby(stateVO.getUserid());
			stateVO.setState(stateVO.getState().toUpperCase());
			stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
			stateVO.setDupchk(stateVO.getOrgId() + stateVO.getStatecode() + stateVO.getState());
			return Optional.of(stateRepo.save(stateVO));
		} else {
			return Optional.empty();
		}
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
	public CityVO createCity(CityVO cityVO) {
		cityVO.setCancel(false);
		cityVO.setCitycode(cityVO.getCitycode().toUpperCase());
		cityVO.setCity(cityVO.getCity().toUpperCase());
		cityVO.setActive(true);
		cityVO.setDupchk(cityVO.getCitycode() + cityVO.getCity() + cityVO.getOrgId());
		return cityRepo.save(cityVO);
	}

	@Override
	public Optional<CityVO> updateCity(CityVO cityVO) {
		if (cityRepo.existsById(cityVO.getId())) {
			cityVO.setCitycode(cityVO.getCitycode().toUpperCase());
			cityVO.setCity(cityVO.getCity().toUpperCase());
			cityVO.setUpdatedby(cityVO.getUserid());
			cityVO.setDupchk(cityVO.getCitycode() + cityVO.getCity() + cityVO.getOrgId());
			return Optional.of(cityRepo.save(cityVO));
		} else {
			return Optional.empty();
		}
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
		regionVO.setRegion(regionVO.getRegion().toUpperCase());
		regionVO.setActive(true);
		regionVO.setDocid("MAA");
		regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegion() + regionVO.getOrgId());
		return regionRepo.save(regionVO);
	}

	@Override
	public Optional<RegionVO> updateRegion(RegionVO regionVO) {
		if (regionRepo.existsById(regionVO.getId())) {
			regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
			regionVO.setRegion(regionVO.getRegion().toUpperCase());
			regionVO.setUpdatedby(regionVO.getUserid());
			regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegion() + regionVO.getOrgId());
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
	public CompanyVO createCompany(CompanyVO companyVO) throws Exception {
		companyVO.setCancel(false);
		companyVO.setEmployeecode(companyVO.getEmployeecode().toUpperCase());
		companyVO.setEmployeeName(companyVO.getEmployeeName().toUpperCase());
		companyVO.setActive(true);
		CompanyVO company = companyRepo.save(companyVO);
		EmployeeVO emp = new EmployeeVO();
		emp.setEmployeecode(company.getEmployeecode());
		emp.setEmployeeName(company.getEmployeeName());
		emp.setOrgId(company.getId());
		employeeRepo.save(emp);
		UserVO userVO = new UserVO();
		userVO.setUserName(company.getEmployeecode());
		userVO.setOrgId(company.getId());
		userVO.setUserType("ROLE_ADMIN");
		userVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(company.getPassword())));
		userRepo.save(userVO);
		return company;
	}

	@Override
	public Optional<CompanyVO> updateCompany(CompanyVO companyVO) {
		if (companyRepo.existsById(companyVO.getId())) {
			companyVO.setUpdatedby(companyVO.getUserid());
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

	//
	@Override
	public Optional<GlobalParameterVO> getGlobalParamByOrgIdAndUserName(Long orgid, String username) {

		return globalParameterRepo.findGlobalParamByOrgIdAndUserName(orgid, username);
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
