package com.whydigit.wms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.CityDTO;
import com.whydigit.wms.dto.CompanyDTO;
import com.whydigit.wms.dto.CountryDTO;
import com.whydigit.wms.dto.RegionDTO;
import com.whydigit.wms.dto.Role;
import com.whydigit.wms.dto.ScreenNamesDTO;
import com.whydigit.wms.dto.StateDTO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CurrencyVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.ScreenNamesVO;
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
import com.whydigit.wms.repo.ScreenNamesRepo;
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
	ScreenNamesRepo screenNamesRepo;

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

		if (ObjectUtils.isEmpty(countryDTO.getId())) {
			if (countryVORepo.existsByCountryNameAndCountryCodeAndOrgId(countryDTO.getCountryName(),
					countryDTO.getCountryCode(), countryDTO.getOrgId())) {
				String errorMessage = String.format(
						"The CountryName: %s and CountryCode: %s already exists This Organization.",
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
		}
		if (countryDTO.getId() != null) {
			// Update existing branch
			countryVO = countryVORepo.findById(countryDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + countryDTO.getId()));
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
			if (!countryVO.getCountryCode().equalsIgnoreCase(countryDTO.getCountryCode())) {
				if (countryVORepo.existsByCountryCodeAndOrgId(countryDTO.getCountryCode(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryCode: %s already exists This Organization.",
							countryDTO.getCountryCode());
					throw new ApplicationException(errorMessage);
				}
				countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
			}
			if (!countryVO.getCountryName().equalsIgnoreCase(countryDTO.getCountryName())) {
				if (countryVORepo.existsByCountryNameAndOrgId(countryDTO.getCountryName(), countryDTO.getOrgId())) {
					String errorMessage = String.format("The CountryName: %s already exists This Organization.",
							countryDTO.getCountryName());
					throw new ApplicationException(errorMessage);
				}
				countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());

			}

		} else {
			// Create new branch
			countryVO = new CountryVO();
			countryVO.setCreatedBy(countryDTO.getCreatedBy());
			countryVO.setUpdatedBy(countryDTO.getCreatedBy());
		}

		getCountryVOFromCounytryDTO(countryVO, countryDTO);

		return countryVORepo.save(countryVO);
	}

	private void getCountryVOFromCounytryDTO(CountryVO countryVO, CountryDTO countryDTO) {
		countryVO.setCountryName(countryDTO.getCountryName().toUpperCase());
		countryVO.setCountryCode(countryDTO.getCountryCode().toUpperCase());
		countryVO.setActive(countryDTO.isActive());
		countryVO.setOrgId(countryDTO.getOrgId());
		countryVO.setCancel(countryDTO.isCancel());

	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getId())) {
			// countryVO.setUpdatedBy(countryVO.getUserId());
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

		countryVORepo.deleteById(countryid);
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
		StateVO stateVO;

		if (ObjectUtils.isEmpty(stateDTO.getId())) {
//		if (stateRepo.existsByStateCodeAndStateNameAndStateNumberAndOrgId(stateDTO.getStateCode(),
//				stateDTO.getStateName(), stateDTO.getStateNumber(), stateDTO.getOrgId())) {
//
//			String errorMessage = String.format(
//					"The StateCode: %s and StateName: %s and StateNumber: %s already exists in this Organization.",
//					stateDTO.getStateCode(), stateDTO.getStateName(), stateDTO.getStateNumber());
//			throw new ApplicationException(errorMessage);
//		}

			if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
				String errorMessage = String.format("The StateCode: %s already exists This Organization.",
						stateDTO.getStateCode());
				throw new ApplicationException(errorMessage);

			}
			if (stateRepo.existsByStateNumberAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {

				String errorMessage = String.format("The StateNumber: %s already exists This Organization.",
						stateDTO.getStateNumber());
				throw new ApplicationException(errorMessage);

			}
			if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {

				String errorMessage = String.format("The StateName: %s already exists This Organization.",
						stateDTO.getStateName());
				throw new ApplicationException(errorMessage);

			}
		}

		if (stateDTO.getId() != null) {
			// Update existing branch
			stateVO = stateRepo.findById(stateDTO.getId())
					.orElseThrow(() -> new ApplicationException("State not found with id: " + stateDTO.getId()));

			stateVO.setUpdatedBy(stateDTO.getCreatedBy());

			if (!stateVO.getStateCode().equalsIgnoreCase(stateDTO.getStateCode())) {
				if (stateRepo.existsByStateCodeAndOrgId(stateDTO.getStateCode(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateCode: %s already exists This Organization.",
							stateDTO.getStateCode());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateCode(stateDTO.getStateCode().toUpperCase());
			}

			if (!stateVO.getStateName().equalsIgnoreCase(stateDTO.getStateName())) {
				if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateName(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateName: %s already exists This Organization.",
							stateDTO.getStateName());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateName(stateDTO.getStateName().toUpperCase());
			}
			if (!stateVO.getStateNumber().equalsIgnoreCase(stateDTO.getStateNumber())) {
				if (stateRepo.existsByStateNameAndOrgId(stateDTO.getStateNumber(), stateDTO.getOrgId())) {
					String errorMessage = String.format("The StateNumber: %s already exists This Organization.",
							stateDTO.getStateNumber());
					throw new ApplicationException(errorMessage);
				}
				stateVO.setStateNumber(stateDTO.getStateNumber().toUpperCase());
			}

		}

		else {
			// Create new branch
			stateVO = new StateVO();

			stateVO.setCreatedBy(stateDTO.getCreatedBy());
			stateVO.setUpdatedBy(stateDTO.getCreatedBy());
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
		stateVO.setActive(stateDTO.isActive());
		stateVO.setCancel(stateDTO.isCancel());
		stateVO.setOrgId(stateDTO.getOrgId());
		// stateVO.setDupchk(stateDTO.getOrgId()+stateDTO.getStateCode()+stateDTO.getStateName());
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

		if (ObjectUtils.isEmpty(cityDTO.getId())) {

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

		}

		if (cityDTO.getId() != null) {
			// Update existing branch
			cityVO = cityRepo.findById(cityDTO.getId())
					.orElseThrow(() -> new ApplicationException("City not found with id: " + cityDTO.getId()));
			cityVO.setUpdatedBy(cityDTO.getCreatedBy());

			if (!cityVO.getCityCode().equalsIgnoreCase(cityDTO.getCityCode())) {
				if (cityRepo.existsByCityCodeAndOrgId(cityDTO.getCityCode(), cityDTO.getOrgId())) {
					String errorMessage = String.format("The CityCode: %s already exists This Organization.",
							cityDTO.getCityCode());
					throw new ApplicationException(errorMessage);
				}
				cityVO.setCityCode(cityDTO.getCityCode().toUpperCase());
			}

			if (!cityVO.getCityName().equalsIgnoreCase(cityDTO.getCityName())) {
				if (cityRepo.existsByCityNameAndOrgId(cityDTO.getCityName(), cityDTO.getOrgId())) {
					String errorMessage = String.format("The CityName: %s already exists This Organization.",
							cityDTO.getCityName());
					throw new ApplicationException(errorMessage);
				}
				cityVO.setCityName(cityDTO.getCityName().toUpperCase());
			}

		} else {
			// Create new branch
			cityVO = new CityVO();
			cityVO.setCreatedBy(cityDTO.getCreatedBy());
			cityVO.setUpdatedBy(cityDTO.getCreatedBy());
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
	public RegionVO createUpdateRegion(RegionDTO regionDTO) throws ApplicationException {

		RegionVO regionVO;
		if (ObjectUtils.isEmpty(regionDTO.getId())) {
			if (regionRepo.existsByRegionNameAndOrgId(regionDTO.getRegionName(), regionDTO.getOrgId())) {

				String errorMessage = String.format("This RegionName:%s Already Exists This Organization",
						regionDTO.getRegionName());
				throw new ApplicationException(errorMessage);
			}
			if (regionRepo.existsByRegionCodeAndOrgId(regionDTO.getRegionCode(), regionDTO.getOrgId())) {

				String errorMessage = String.format("This RegionCode:%s Already Exists This Organization",
						regionDTO.getRegionCode());
				throw new ApplicationException(errorMessage);
			}
		}

		if (regionDTO.getId() != null) {
			regionVO = regionRepo.findById(regionDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Is Not Found Any Information ." + regionDTO.getId()));
			regionVO.setUpdatedBy(regionDTO.getCreatedBy());
			if (!regionVO.getRegionName().equalsIgnoreCase(regionDTO.getRegionName())) {
				if (regionRepo.existsByRegionNameAndOrgId(regionDTO.getRegionName(), regionDTO.getOrgId())) {

					String errorMessage = String.format("This RegionName:%s Already Exists This Organization",
							regionDTO.getRegionName());
					throw new ApplicationException(errorMessage);
				}
				regionVO.setRegionName(regionDTO.getRegionName());
			}
			if (!regionVO.getRegionCode().equalsIgnoreCase(regionDTO.getRegionCode())) {
				if (regionRepo.existsByRegionCodeAndOrgId(regionDTO.getRegionCode(), regionDTO.getOrgId())) {

					String errorMessage = String.format("This RegionCode:%s Already Exists This Organization",
							regionDTO.getRegionCode());
					throw new ApplicationException(errorMessage);
				}
				regionVO.setRegionCode(regionDTO.getRegionCode());
			}
		} else {
			regionVO = new RegionVO(); // corrected this line
			regionVO.setCreatedBy(regionDTO.getCreatedBy());
			regionVO.setUpdatedBy(regionDTO.getCreatedBy());
		}

		getRegionVOAndRegionDTO(regionVO, regionDTO);

		return regionRepo.save(regionVO);
	}

	private void getRegionVOAndRegionDTO(RegionVO regionVO, RegionDTO regionDTO) {
		regionVO.setActive(regionDTO.isActive());
		regionVO.setOrgId(regionDTO.getOrgId());
		regionVO.setCancel(regionDTO.isCancel());
		regionVO.setRegionCode(regionDTO.getRegionCode());
		regionVO.setRegionName(regionDTO.getRegionName());

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

		if (companyRepo.existsByCompanyCodeAndCompanyNameAndEmployeeCodeAndEmailAndPhoneAndId(
				companyDTO.getCompanyCode(), companyDTO.getCompanyName(), companyDTO.getEmployeeCode(),
				companyDTO.getEmail(), companyDTO.getPhone(), companyDTO.getId())) {

			String errorMessage = String.format(
					"The CompanyCode : %s And CompanyName : %s And EmployeeCode : %s And Email : %s And PhoneNumber : %s Already Exists"
							+ "This Organization",
					companyDTO.getCompanyCode(), companyDTO.getCompanyName(), companyDTO.getEmployeeCode(),
					companyDTO.getEmail(), companyDTO.getPhone());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByCompanyCodeAndId(companyDTO.getCompanyCode(), companyDTO.getId())) {

			String errorMessage = String.format("The CompanyCode : %s Already Exists This Organization",
					companyDTO.getCompanyCode());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByCompanyNameAndId(companyDTO.getCompanyName(), companyDTO.getId())) {
			String errorMessage = String.format("The CompanyName : %s Already Exists This Organization",
					companyDTO.getCompanyName());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByEmployeeCodeAndId(companyDTO.getEmployeeCode(), companyDTO.getId())) {
			String errorMessage = String.format("The EmployeeCode : %s Already Exists This Organization",
					companyDTO.getEmployeeCode());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByEmailAndId(companyDTO.getEmail(), companyDTO.getId())) {
			String errorMessage = String.format("The Email : %s Already Exists This Organization",
					companyDTO.getEmail());
			throw new ApplicationException(errorMessage);
		}

		if (companyRepo.existsByPhoneAndId(companyDTO.getPhone(), companyDTO.getId())) {
			String errorMessage = String.format("The PhoneNumber : %s Already Exists This Organization",
					companyDTO.getPhone());
			throw new ApplicationException(errorMessage);
		}

		CompanyVO companyVO = new CompanyVO();
		getCompanyVOFromCompanyDTO(companyVO, companyDTO);
		companyRepo.save(companyVO);

		UserVO userVO = new UserVO();
		userVO.setUserName(companyVO.getEmployeeName());
		userVO.setEmployeeName(companyVO.getEmployeeName());
		userVO.setEmail(companyVO.getEmail());
		userVO.setMobileNo(companyVO.getPhone());
		userVO.setRole(Role.ROLE_USER);
		userVO.setOrgId(companyVO.getId());
		userVO.setCreatedby(companyVO.getCreatedBy());
		userVO.setUpdatedby(companyVO.getCreatedBy());
		userVO.setActive(true);
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
		companyVO.setActive(companyDTO.isActive());
		companyVO.setCancel(companyDTO.isCancel());
		try {
			companyVO.setPassword(encoder.encode(CryptoUtils.getDecrypt(companyDTO.getPassword())));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new ApplicationContextException("Unable To Encode Password");
		}
	}

	public CompanyVO updateCompany(CompanyDTO companyDTO) throws ApplicationException {

		if (ObjectUtils.isEmpty(companyDTO.getId())) {
			throw new ApplicationException("Invalid Company Id");
		}

		CompanyVO companyVO = companyRepo.findById(companyDTO.getId())
				.orElseThrow(() -> new ApplicationException("Company not found for Id: " + companyDTO.getId()));

		mapCompanyDTOToCompanyVO(companyVO, companyDTO);

		return companyRepo.save(companyVO);
	}

	private void mapCompanyDTOToCompanyVO(CompanyVO companyVO, CompanyDTO companyDTO) {
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
		companyVO.setUpdatedBy(companyDTO.getUpdatedBy());
		companyVO.setActive(companyDTO.isActive());
		companyVO.setCancel(companyDTO.isCancel());
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
		return currencyRepo.save(currencyVO);
	}

	@Override
	public Optional<CurrencyVO> updateCurrency(CurrencyVO currencyVO) {
		if (currencyRepo.existsById(currencyVO.getId())) {
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

	@Override
	public Map<String, Object> createUpdateScreenNames(ScreenNamesDTO screenNamesDTO) throws ApplicationException {
		ScreenNamesVO screenNamesVO = new ScreenNamesVO();
		String message = null;

		if (ObjectUtils.isEmpty(screenNamesDTO.getId())) {

			// Validate if responsibility already exists by responsibility name
			if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
				throw new ApplicationException("Screen Name already exists");
			}
			if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
				throw new ApplicationException("Screen Code already exists");
			}

			screenNamesVO.setCreatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			// Set the values from screenNamesDTO to responsibilityVO
			message = "ScreenName Created successfully";

		} else {

			// Retrieve the existing ResponsibilityVO from the repository
			screenNamesVO = screenNamesRepo.findById(screenNamesDTO.getId())
					.orElseThrow(() -> new ApplicationException("Screen Name not found"));

			// Validate and update unique fields if changed
			if (!screenNamesVO.getScreenName().equalsIgnoreCase(screenNamesDTO.getScreenName())) {
				if (screenNamesRepo.existsByScreenName(screenNamesDTO.getScreenName())) {
					throw new ApplicationException("Screen Name already exists");
				}
				screenNamesVO.setScreenName(screenNamesDTO.getScreenName());
			}
			if (!screenNamesVO.getScreenCode().equalsIgnoreCase(screenNamesDTO.getScreenCode())) {
				if (screenNamesRepo.existsByScreenCode(screenNamesDTO.getScreenCode())) {
					throw new ApplicationException("Screen Code already exists");
				}
				screenNamesVO.setScreenCode(screenNamesDTO.getScreenCode());
			}
			screenNamesVO.setActive(screenNamesDTO.isActive());
			screenNamesVO.setUpdatedBy(screenNamesDTO.getCreatedBy());
			// Update the remaining fields from screenNamesDTO to responsibilityVO
			message = "ScreenName Updated successfully";
		}

		screenNamesRepo.save(screenNamesVO);
		Map<String, Object> response = new HashMap<>();
		response.put("screenNamesVO", screenNamesVO);
		response.put("message", message);
		return response;
	}

	@Override
	public List<ScreenNamesVO> getAllScreenNames() {

		return screenNamesRepo.findAll();
	}

	@Override
	public ScreenNamesVO getScreenNamesById(Long id) throws ApplicationException {

		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid Company Id");
		}

		ScreenNamesVO screenNamesVO = screenNamesRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Screen Name not found for Id: " + id));

		return screenNamesVO;
	}

}
