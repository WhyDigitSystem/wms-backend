package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CityRepo;
import com.whydigit.wms.repo.CompanyRepo;
import com.whydigit.wms.repo.CountryRepository;
import com.whydigit.wms.repo.FinancialYearRepo;
import com.whydigit.wms.repo.GlobalParameterRepo;
import com.whydigit.wms.repo.RegionRepo;
import com.whydigit.wms.repo.StateRepo;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {

	@Autowired
	CountryRepository countryVORepo;

	@Autowired
	StateRepo stateRepo;

	@Autowired
	CityRepo cityRepo;

	@Autowired
	RegionRepo regionRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	FinancialYearRepo financialYearRepo;

	@Autowired
	GlobalParameterRepo globalParameterRepo;

	@Autowired
	CarrierRepo carrierRepo;


	// Country

	@Override
	public List<CountryVO> getAllCountry() {
		return countryVORepo.findAll();
	}

	@Override
	public Optional<CountryVO> getCountryById(Long countryid) {
		return countryVORepo.findById(countryid);
	}

	@Override
	public CountryVO createCountry(CountryVO countryVO) {

		countryVO.setCountryname(countryVO.getCountryname().toUpperCase());
		countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
		countryVO.setActive(true);
		countryVO.setActive(true);
		countryVO.setDupchk(countryVO.getCountrycode() + countryVO.getCountryname());
		return countryVORepo.save(countryVO);

	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getCountryid())) {
			countryVO.setUpdatedby(countryVO.getUserid());
			countryVO.setCountryname(countryVO.getCountryname().toUpperCase());
			countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
			countryVO.setDupchk(countryVO.getCountrycode() + countryVO.getCountryname());
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
	public List<StateVO> getAllgetAllStates() {
		return stateRepo.findAll();
	}

	@Override
	public Optional<StateVO> getStateById(Long stateid) {
		return stateRepo.findById(stateid);
	}

	@Override
	public List<StateVO> getStatesByCountry(String country) {

		return stateRepo.findByCountry(country);
	}

	@Override
	public StateVO createState(StateVO stateVO) {
		stateVO.setStatename(stateVO.getStatename().toUpperCase());
		stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
		stateVO.setActive(true);
		stateVO.setActive(true);
		stateVO.setDupchk(stateVO.getStatecode() + stateVO.getStatename() + stateVO.getCompany());
		return stateRepo.save(stateVO);
	}

	@Override
	public Optional<StateVO> updateState(StateVO stateVO) {
		if (stateRepo.existsById(stateVO.getStateid())) {
			stateVO.setUpdatedby(stateVO.getUserid());
			stateVO.setStatename(stateVO.getStatename().toUpperCase());
			stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
			stateVO.setDupchk(stateVO.getStatecode() + stateVO.getStatename() + stateVO.getCompany());
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
	public List<CityVO> getAllgetAllCities() {
		return cityRepo.findAll();
	}

	@Override
	public Optional<CityVO> getCityById(Long cityid) {
		return cityRepo.findById(cityid);
	}

	@Override
	public CityVO createCity(CityVO cityVO) {
		cityVO.setCitycode(cityVO.getCitycode().toUpperCase());
		cityVO.setCityname(cityVO.getCityname().toUpperCase());
		cityVO.setActive(true);
		cityVO.setActive(true);
		cityVO.setDupchk(cityVO.getCitycode() + cityVO.getCityname() + cityVO.getCompany());
		return cityRepo.save(cityVO);
	}

	@Override
	public Optional<CityVO> updateCity(CityVO cityVO) {
		if (cityRepo.existsById(cityVO.getCityid())) {
			cityVO.setCitycode(cityVO.getCitycode().toUpperCase());
			cityVO.setCityname(cityVO.getCityname().toUpperCase());
			cityVO.setUpdatedby(cityVO.getUserid());
			cityVO.setDupchk(cityVO.getCitycode() + cityVO.getCityname() + cityVO.getCompany());
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
	public List<RegionVO> getAllRegion() {
		return regionRepo.findAll();
	}

	@Override
	public Optional<RegionVO> getRegionById(Long Regionid) {
		return regionRepo.findById(Regionid);
	}

	@Override
	public RegionVO createRegion(RegionVO regionVO) {
		regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
		regionVO.setRegionname(regionVO.getRegionname().toUpperCase());
		regionVO.setActive(true);
		regionVO.setActive(true);
		regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegionname() + regionVO.getCompany());
		return regionRepo.save(regionVO);
	}

	@Override
	public Optional<RegionVO> updateRegion(RegionVO regionVO) {
		if (regionRepo.existsById(regionVO.getRegionid())) {
			regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
			regionVO.setRegionname(regionVO.getRegionname().toUpperCase());
			regionVO.setUpdatedby(regionVO.getUserid());
			regionVO.setDupchk(regionVO.getRegioncode() + regionVO.getRegionname() + regionVO.getCompany());
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
	public CompanyVO createCompany(CompanyVO companyVO) {
		companyVO.setActive(true);
		companyVO.setActive(true);
		return companyRepo.save(companyVO);
	}

	@Override
	public Optional<CompanyVO> updateCompany(CompanyVO companyVO) {
		if (companyRepo.existsById(companyVO.getCompanyid())) {
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

	@Override
	public GlobalParameterVO createGlobaParameter(GlobalParameterVO globalParam) {
		return globalParameterRepo.save(globalParam);
	}

	@Override
	public Optional<GlobalParameterVO> updateGlobaParameter(GlobalParameterVO globalParameterVO) {
		if (globalParameterRepo.existsById(globalParameterVO.getGlobalid())) {
			return Optional.of(globalParameterRepo.save(globalParameterVO));
		} else {
			return Optional.empty();
		}
	}
}
