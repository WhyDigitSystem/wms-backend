package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.CityVO;
import com.whydigit.wms.entity.CompanyVO;
import com.whydigit.wms.entity.CountryVO;
import com.whydigit.wms.entity.GlobalParameterVO;
import com.whydigit.wms.entity.RegionVO;
import com.whydigit.wms.entity.StateVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CellTypeRepo;
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
	WarehouseRepo warehouseRepo;

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
		countryVO.setDupchk(countryVO.getCountrycode() + countryVO.getCountryname() + countryVO.getCompany());
		return countryVORepo.save(countryVO);

	}

	@Override
	public Optional<CountryVO> updateCountry(CountryVO countryVO) {
		if (countryVORepo.existsById(countryVO.getCountryid())) {
			countryVO.setUpdatedby(countryVO.getUserid());
			countryVO.setCountryname(countryVO.getCountryname().toUpperCase());
			countryVO.setCountrycode(countryVO.getCountrycode().toUpperCase());
			countryVO.setDupchk(countryVO.getCountrycode() + countryVO.getCountryname() + countryVO.getCompany());
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
		groupVO.setActive(true);
		groupVO.setActive(true);
		groupVO.setDupchk(groupVO.getGroupname() + groupVO.getCompany());
		return groupRepo.save(groupVO);
	}

	@Override
	public Optional<GroupVO> updateGroup(GroupVO groupVO) {
		if (groupRepo.existsById(groupVO.getGroupid())) {
			groupVO.setGroupname(groupVO.getGroupname().toUpperCase());
			groupVO.setUpdatedby(groupVO.getUserid());
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
		unitVO.setActive(true);
		unitVO.setActive(true);
		unitVO.setDupchk(unitVO.getUnitname() + unitVO.getCompany());
		return unitRepo.save(unitVO);
	}

	@Override
	public Optional<UnitVO> updateUnit(UnitVO unitVO) {
		if (unitRepo.existsById(unitVO.getUnitid())) {
			unitVO.setUpdatedby(unitVO.getUserid());
			unitVO.setDupchk(unitVO.getUnitname() + unitVO.getCompany());
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
		locationTypeVO.setActive(true);
		locationTypeVO.setActive(true);
		locationTypeVO.setDupchk(locationTypeVO.getLocationtype() + locationTypeVO.getCompany());
		return locationTypeRepo.save(locationTypeVO);
	}

	@Override
	public Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO) {
		if (locationTypeRepo.existsById(locationTypeVO.getLocationtypeid())) {
			locationTypeVO.setUpdatedby(locationTypeVO .getUserid());
			locationTypeVO.setDupchk(locationTypeVO.getLocationtype() + locationTypeVO.getCompany());
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
		cellTypeVO.setActive(true);
		cellTypeVO.setActive(true);
		cellTypeVO.setDupchk(cellTypeVO.getCelltype() + cellTypeVO.getCompany());
		return cellTypeRepo.save(cellTypeVO);
	}

	@Override
	public Optional<CellTypeVO> updateCellType(CellTypeVO cellTypeVO) {
		if (cellTypeRepo.existsById(cellTypeVO.getCelltypeid())) {
			cellTypeVO.setUpdatedby(cellTypeVO .getUserid());
			cellTypeVO.setDupchk(cellTypeVO.getCelltype() + cellTypeVO.getCompany());
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
		companyVO.setActive(true);
		companyVO.setActive(true);
		return companyRepo.save(companyVO);
	}

	@Override
	public Optional<CompanyVO> updateCompany(CompanyVO companyVO) {
		if (companyRepo.existsById(companyVO.getCompanyid())) {
			companyVO.setUpdatedby(companyVO .getUserid());
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
		branchVO.setActive(true);
		branchVO.setActive(true);
		branchVO.setDupchk(branchVO.getBranchname() +branchVO.getBranchcode() + branchVO.getCompany());
		return branchRepo.save(branchVO);
	}

	@Override
	public Optional<BranchVO> updateBranch(BranchVO branchVO) {
		if (branchRepo.existsById(branchVO.getBranchid())) {
			branchVO.setUpdatedby(branchVO.getUserid());
			branchVO.setDupchk(branchVO.getBranchname() +branchVO.getBranchcode() + branchVO.getCompany());			branchVO.setBranchname(branchVO.getBranchname().toUpperCase());
			branchVO.setBranchcode(branchVO.getBranchcode().toUpperCase());
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
	public CustomerVO createCustomer(CustomerVO customerVO, ClientVO clientVO) {
		customerVO.setDupchk(customerVO.getCustomer() + customerVO.getCompany());
		customerVO.setActive(true);
		customerVO.setActive(true);
		customerVO.setDupchk(customerVO.getCustomer() +customerVO.getCustomershortname() + customerVO.getCompany());
		clientVO.setClient(clientVO.getClient() + clientVO.getCompany());
		return customerRepo.save(customerVO);
	}

	@Override
	public Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO) {
		if (customerRepo.existsById(customerVO.getCustomerid())) {
			customerVO.setUpdatedby(customerVO.getUserid());
			customerVO.setDupchk(customerVO.getCustomer() +customerVO.getCustomershortname() + customerVO.getCompany());
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
		warehouseVO.setDupchk(warehouseVO.getBranchcode() + warehouseVO.getWarehousename() + warehouseVO.getCompany());
		warehouseVO.setActive(true);
		warehouseVO.setActive(true);
		warehouseVO.setWarehousename(warehouseVO.getWarehousename().toUpperCase());
		warehouseVO.setBranchcode(warehouseVO.getBranchcode().toUpperCase());
		return warehouseRepo.save(warehouseVO);
	}

	@Override
	public void deleteWarehouse(Long warehouseid) {
		warehouseRepo.deleteById(warehouseid);
	}

	// Carrier

	@Override
	public List<CarrierVO> getAllCarrier() {
		return carrierRepo.findAll();
	}

	@Override
	public Optional<CarrierVO> getCarrierById(Long carrierid) {
		return carrierRepo.findById(carrierid);
	}

	@Override
	public CarrierVO createCarrier(CarrierVO carrierVO) {
		carrierVO.setDupchk(carrierVO.getCompany() + carrierVO.getCarriername() + carrierVO.getCarriershortname()+ carrierVO.getControlbranch());
		carrierVO.setCarriername(carrierVO.getCarriername().toUpperCase());
		carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
		carrierVO.setCancel(false);
		carrierVO.setActive(true);
		return carrierRepo.save(carrierVO);
	}

	@Override
	public Optional<CarrierVO> updateCarrier(CarrierVO carrierVO) {
		if (carrierRepo.existsById(carrierVO.getCarrierid())) {
			carrierVO.setUpdatedby(carrierVO.getUserid());
			carrierVO.setDupchk(carrierVO.getCompany() + carrierVO.getCarriername() + carrierVO.getCarriershortname()+ carrierVO.getControlbranch());
			carrierVO.setCarriername(carrierVO.getCarriername().toUpperCase());
			carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
			return Optional.of(carrierRepo.save(carrierVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCarrier(Long carrierid) {
		carrierRepo.deleteById(carrierid);
	}

}
