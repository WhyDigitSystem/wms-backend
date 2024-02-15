package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UnitVO;

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

	// Group

	List<GroupVO> getAllGroup();

	Optional<GroupVO> getGroupById(Long groupid);

	GroupVO createGroup(GroupVO groupVO);

	Optional<GroupVO> updateGroup(GroupVO groupVO);

	void deleteGroup(Long groupid);

	// Unit

	List<UnitVO> getAllUnit();

	Optional<UnitVO> getUnitById(Long unitid);

	UnitVO createUnit(UnitVO unitVO);

	Optional<UnitVO> updateUnit(UnitVO unitVO);

	void deleteUnit(Long unitid);

	// Location Type

	List<LocationTypeVO> getAllLocationType();

	Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid);

	LocationTypeVO createLocationType(LocationTypeVO locationTypeVO);

	Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO);

	void deleteLocationType(Long locationtypeid);

	// CellType

	List<CellTypeVO> getAllCellType();

	Optional<CellTypeVO> getCellTypeById(Long celltypeid);

	CellTypeVO createCellType(CellTypeVO cellTypeVO);

	Optional<CellTypeVO> updateCellType(CellTypeVO cellTypeVO);

	void deleteCellType(Long celltypeid);

	// Company 
	
	List<CompanyVO> getAllCompany();

	Optional<CompanyVO> getCompanyById(Long companyid);

	CompanyVO createCompany(CompanyVO companyVO);

	Optional<CompanyVO> updateCompany(CompanyVO companyVO);

	void deleteCompany(Long companyid);


	// Branch 
	
	List<BranchVO> getAllBranch();
	
	List<BranchVO> getAllBranchByCompany(String company);

	Optional<BranchVO> getBranchById(Long branchid);

	BranchVO createBranch(BranchVO branchVO);

	Optional<BranchVO> updateBranch(BranchVO branchVO);

	void deleteBranch(Long branchid);
}
