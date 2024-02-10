package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.repo.CityRepo;
import com.whydigit.wms.repo.CountryVORepository;
import com.whydigit.wms.repo.StateRepo;

@Service
public class CommonMasterServiceImpl implements CommonMasterService {
	
	@Autowired
	CountryVORepository countryVORepo;
	
	@Autowired
	StateRepo stateRepo;
	
	@Autowired
	CityRepo cityRepo;
	
	

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
		return countryVORepo.save(countryVO);
		
	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getCountryid())) {
			countryVO.setCountryname(countryVO.getCountryname().toUpperCase());
			countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
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
	public StateVO createState(StateVO stateVO) {
		stateVO.setStatename(stateVO.getStatename().toUpperCase());
		stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
		return stateRepo.save(stateVO);
	}

	@Override
	public Optional<StateVO> updateState(StateVO stateVO) {
		if (stateRepo.existsById(stateVO.getStateid())) {
			stateVO.setStatename(stateVO.getStatename().toUpperCase());
			stateVO.setStatecode(stateVO.getStatecode().toUpperCase());
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
		return cityRepo.save(cityVO);
	}

	@Override
	public Optional<CityVO> updateCity(CityVO cityVO) {
		if (cityRepo.existsById(cityVO.getCityid())) {
			cityVO.setCitycode(cityVO.getCitycode().toUpperCase());
			cityVO.setCityname(cityVO.getCityname().toUpperCase());
			return Optional.of(cityRepo.save(cityVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCity(Long cityid) {
		cityRepo.deleteById(cityid);
	}


	


	
	
	
	
}
