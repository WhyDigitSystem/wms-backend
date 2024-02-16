package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.FinancialYearVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.CellTypeRepo;
import com.whydigit.wms.repo.CityRepo;
import com.whydigit.wms.repo.CompanyRepo;
import com.whydigit.wms.repo.CountryRepository;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.FinancialYearRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.RegionRepo;
import com.whydigit.wms.repo.StateRepo;
import com.whydigit.wms.repo.UnitRepo;
import com.whydigit.wms.repo.WarehouseRepo;

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
	GroupRepo groupRepo;

	@Autowired
	UnitRepo unitRepo;

	@Autowired
	LocationTypeRepo locationTypeRepo;

	@Autowired
	CellTypeRepo cellTypeRepo;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	WarehouseRepo warehouseRepo;
	
	@Autowired
	FinancialYearRepo financialYearRepo;
	
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
	public List<StateVO> getStatesByCountry(String country) {

		return stateRepo.findByCountry(country);
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
		return regionRepo.save(regionVO);
	}

	@Override
	public Optional<RegionVO> updateRegion(RegionVO regionVO) {
		if (regionRepo.existsById(regionVO.getRegionid())) {
			regionVO.setRegioncode(regionVO.getRegioncode().toUpperCase());
			regionVO.setRegionname(regionVO.getRegionname().toUpperCase());
			return Optional.of(regionRepo.save(regionVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteRegion(Long regionid) {
		regionRepo.deleteById(regionid);
	}

	// Group

	@Override
	public List<GroupVO> getAllGroup() {
		return groupRepo.findAll();
	}

	@Override
	public Optional<GroupVO> getGroupById(Long groupid) {
		return groupRepo.findById(groupid);
	}

	@Override
	public GroupVO createGroup(GroupVO groupVO) {
		groupVO.setGroupname(groupVO.getGroupname().toUpperCase());
		return groupRepo.save(groupVO);
	}

	@Override
	public Optional<GroupVO> updateGroup(GroupVO groupVO) {
		if (groupRepo.existsById(groupVO.getGroupid())) {
			groupVO.setGroupname(groupVO.getGroupname().toUpperCase());
			return Optional.of(groupRepo.save(groupVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteGroup(Long groupid) {
		groupRepo.deleteById(groupid);
	}

	// Unit

	@Override
	public List<UnitVO> getAllUnit() {
		return unitRepo.findAll();
	}

	@Override
	public Optional<UnitVO> getUnitById(Long unitid) {
		return unitRepo.findById(unitid);
	}

	@Override
	public UnitVO createUnit(UnitVO unitVO) {
		return unitRepo.save(unitVO);
	}

	@Override
	public Optional<UnitVO> updateUnit(UnitVO unitVO) {
		if (unitRepo.existsById(unitVO.getUnitid())) {
			return Optional.of(unitRepo.save(unitVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteUnit(Long unitid) {
		unitRepo.deleteById(unitid);
	}
	// LocationType

	@Override
	public List<LocationTypeVO> getAllLocationType() {
		return locationTypeRepo.findAll();
	}

	@Override
	public Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid) {
		return locationTypeRepo.findById(locationtypeid);
	}

	@Override
	public LocationTypeVO createLocationType(LocationTypeVO locationTypeVO) {
		return locationTypeRepo.save(locationTypeVO);
	}

	@Override
	public Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO) {
		if (locationTypeRepo.existsById(locationTypeVO.getLocationtypeid())) {
			return Optional.of(locationTypeRepo.save(locationTypeVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteLocationType(Long locationtypeid) {
		locationTypeRepo.deleteById(locationtypeid);
	}

	// CellType

	@Override
	public List<CellTypeVO> getAllCellType() {
		return cellTypeRepo.findAll();
	}

	@Override
	public Optional<CellTypeVO> getCellTypeById(Long celltypeid) {
		return cellTypeRepo.findById(celltypeid);
	}

	@Override
	public CellTypeVO createCellType(CellTypeVO cellTypeVO) {
		return cellTypeRepo.save(cellTypeVO);
	}

	@Override
	public Optional<CellTypeVO> updateCellType(CellTypeVO cellTypeVO) {
		if (cellTypeRepo.existsById(cellTypeVO.getCelltypeid())) {
			return Optional.of(cellTypeRepo.save(cellTypeVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCellType(Long celltypeid) {
		cellTypeRepo.deleteById(celltypeid);
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
		return companyRepo.save(companyVO);
	}

	@Override
	public Optional<CompanyVO> updateCompany(CompanyVO companyVO) {
		if (companyRepo.existsById(companyVO.getCompanyid())) {
			return Optional.of(companyRepo.save(companyVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCompany(Long companyid) {
		companyRepo.deleteById(companyid);
	}

	// Branch

	@Override
	public List<BranchVO> getAllBranch() {
		return branchRepo.findAll();
	}

	public List<BranchVO> getAllBranchByCompany(String company) {
		return branchRepo.findAllByCompany(company);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long branchid) {
		return branchRepo.findById(branchid);
	}

	@Override
	public BranchVO createBranch(BranchVO branchVO) {
		branchVO.setBranchname(branchVO.getBranchname().toUpperCase());
		branchVO.setBranchcode(branchVO.getBranchcode().toUpperCase());
		return branchRepo.save(branchVO);
	}

	@Override
	public Optional<BranchVO> updateBranch(BranchVO branchVO) {
		if (branchRepo.existsById(branchVO.getBranchid())) {
			branchVO.setUpdatedby(branchVO.getUserid());
			branchVO.setDupchk(branchVO.getCompany() + branchVO.getBranchcode());
			return Optional.of(branchRepo.save(branchVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteBranch(Long branchid) {
		branchRepo.deleteById(branchid);
	}

	// Customer&client

	@Override
	public List<CustomerVO> getAllCustomer() {
		return customerRepo.findAll();
	}

	@Override
	public Optional<CustomerVO> getCustomerById(Long customerid) {
		return customerRepo.findById(customerid);
	}

	@Override
	public CustomerVO createCustomer(CustomerVO customerVO) {
		return customerRepo.save(customerVO);
	}


	@Override
	public Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO) {
		if (customerRepo.existsById(customerVO.getCustomerid())) {
			customerVO.setDupchk(customerVO.getCustomer() + customerVO.getCompany());
			clientVO.setClient(clientVO.getClient() + clientVO.getCompany());
			return Optional.of(customerRepo.save(customerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCustomer(Long customerid) {
		customerRepo.deleteById(customerid);
	}

	// Warehouse

	@Override
	public List<WarehouseVO> getAllWarehouse() {
		return warehouseRepo.findAll();
	}

	@Override
	public Optional<WarehouseVO> getWarehouseById(Long warehouseid) {
		return warehouseRepo.findById(warehouseid);
	}

	@Override
	public WarehouseVO createWarehouse(WarehouseVO warehouseVO) {
		warehouseVO.setWarehousename(warehouseVO.getWarehousename().toUpperCase());
		warehouseVO.setBranchcode(warehouseVO.getBranchcode().toUpperCase());
		return warehouseRepo.save(warehouseVO);
	}

	@Override
	public Optional<WarehouseVO> updateWarehouse(WarehouseVO warehouseVO) {
		if (warehouseRepo.existsById(warehouseVO.getWarehouseid())) {
			warehouseVO
					.setDupchk(warehouseVO.getBranchcode() + warehouseVO.getWarehousename() + warehouseVO.getCompany());
			return Optional.of(warehouseRepo.save(warehouseVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteWarehouse(Long warehouseid) {
		warehouseRepo.deleteById(warehouseid);
	}
}
