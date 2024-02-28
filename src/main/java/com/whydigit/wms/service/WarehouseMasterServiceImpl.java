package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationMappingVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.SupplierVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.UserLoginVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.BuyerRepo;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CellTypeRepo;
import com.whydigit.wms.repo.ClientBranchRepo;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.EmployeeRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationMappingRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SupplierRepo;
import com.whydigit.wms.repo.UnitRepo;
import com.whydigit.wms.repo.UserLoginRepo;
import com.whydigit.wms.repo.WarehouseLocationDetailsRepo;
import com.whydigit.wms.repo.WarehouseLocationRepo;
import com.whydigit.wms.repo.WarehouseRepo;

@Service
public class WarehouseMasterServiceImpl implements WarehouseMasterService {

	@Autowired
	GroupRepo groupRepo;

	@Autowired
	UnitRepo unitRepo;

	@Autowired
	LocationTypeRepo locationTypeRepo;

	@Autowired
	CellTypeRepo cellTypeRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	WarehouseRepo warehouseRepo;

	@Autowired
	WarehouseLocationRepo warehouseLocationRepo;

	@Autowired
	WarehouseLocationDetailsRepo warehouseLocationDetailsRepo;

	@Autowired
	MaterialRepo materialRepo;

	@Autowired
	BuyerRepo buyerRepo;

	@Autowired
	SupplierRepo supplierRepo;

	@Autowired
	LocationMappingRepo locationMappingRepo;

	@Autowired
	CarrierRepo carrierRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	UserLoginRepo userLoginRepo;

	@Autowired
	ClientBranchRepo clientBranchRepo;

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
	public List<WarehouseVO> getWarehouseByCompany(String company) {
		return warehouseRepo.findWarehouseByCompany(company);
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

	// Warehouse Location

	@Override
	public List<WarehouseLocationVO> getAllWarehouseLocation() {

		return warehouseLocationRepo.findAll();
	}

	@Override
	public List<WarehouseLocationVO> getAllWarehouseLocationByCompany(String company) {
		return warehouseLocationRepo.findAllByCompany(company);
	}

	@Override
	public Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid) {

		return warehouseLocationRepo.findById(warehouselocationid);
	}

	@Override
	public Set<Object> getAllLocationTypebyCompanyAndWarehouse(String company, String warehouse) {

		return warehouseLocationRepo.findAllLocationTypeByCompanyAndWarehouse(company, warehouse);
	}

	// get All Row no based on company and Warehouse and Location type
	@Override
	public Set<Object> getAllRownoByCompanyAndWarehouseAndLocationType(String company, String warehouse,
			String locationtype) {

		return warehouseLocationRepo.findAllRownoByCompanyAndWarehouseAndLocationType(company, warehouse, locationtype);
	}

	// get All Level no based on company and Warehouse , Location type and Rowno
	@Override
	public Set<Object> getAllLevelByCompanyAndWarehouseAndLocationTypeAndRowno(String company, String warehouse,
			String locationtype, String rowno) {

		return warehouseLocationRepo.findAllLevelByCompanyAndWarehouseAndLocationTypeAndRowno(company, warehouse,
				locationtype, rowno);
	}

	// Get All Bins based on Company, warehouse,locationtype,rowno and Level
	@Override
	public Set<Object[]> getAllBinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(String company,
			String warehouse, String locationtype, String rowno, String level) {

		return warehouseLocationDetailsRepo.findAllBinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(company,
				warehouse, locationtype, rowno, level);
	}

	@Override
	public WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO) {
		warehouseLocationVO.setActive(true);
		warehouseLocationVO.setCancel(false);
		return warehouseLocationRepo.save(warehouseLocationVO);

	}

	@Override
	public Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO) {
		if (warehouseLocationRepo.existsById(warehouseLocationVO.getWarehouselocationid())) {
			return Optional.of(warehouseLocationRepo.save(warehouseLocationVO));
		} else {
			return Optional.empty();

		}
	}

	@Override
	public void deleteWarehouseLocation(Long warehouselocationid) {
		warehouseLocationRepo.deleteById(warehouselocationid);

	}

	@Override
	public List<MaterialVO> getAllMaterials() {
		return materialRepo.findAll();
	}

	@Override
	public List<MaterialVO> getAllMaterialsByCompanyAndClient(String company, String client) {
		return materialRepo.findAllByCompanyAndClient(company, client);
	}

	@Override
	public Optional<MaterialVO> getMaterialById(Long materialid) {
		return materialRepo.findById(materialid);
	}

	@Override
	public MaterialVO createMaterial(MaterialVO materialVO) {
		materialVO.setDupchk(materialVO.getCompany() + materialVO.getCustomer() + materialVO.getClient()
				+ materialVO.getPartno() + materialVO.getPartdesc());
		;
		return materialRepo.save(materialVO);
	}

	@Override
	public Optional<MaterialVO> updateMaterial(MaterialVO materialVO) {
		if (materialRepo.existsById(materialVO.getMaterialid())) {
			materialVO.setDupchk(materialVO.getCompany() + materialVO.getCustomer() + materialVO.getClient()
					+ materialVO.getPartno() + materialVO.getPartdesc());
			materialVO.setUpdatedby(materialVO.getUpdatedby());
			return Optional.of(materialRepo.save(materialVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteMaterial(Long materialid) {
	}

	// Buyer

	@Override
	public List<BuyerVO> getAllBuyer(String company, String client) {
		return buyerRepo.findAllByCompanyAndClient(company, client);
	}

	@Override
	public Optional<BuyerVO> getBuyerById(Long buyerid) {
		return buyerRepo.findById(buyerid);
	}

	@Override
	public BuyerVO createBuyer(BuyerVO buyerVO) {
		buyerVO.setDupchk(buyerVO.getClient() + buyerVO.getCompany() + buyerVO.getCustomer() + buyerVO.getBuyername()
				+ buyerVO.getBuyershortname());
		return buyerRepo.save(buyerVO);
	}

	@Override
	public Optional<BuyerVO> updateBuyer(BuyerVO buyerVO) {
		if (buyerRepo.existsById(buyerVO.getBuyerid())) {
			buyerVO.setDupchk(buyerVO.getClient() + buyerVO.getCompany() + buyerVO.getCustomer()
					+ buyerVO.getBuyername() + buyerVO.getBuyershortname());
			return Optional.of(buyerRepo.save(buyerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteBuyer(Long buyerid) {
		buyerRepo.deleteById(buyerid);
	}

	// Supplier

	@Override
	public List<SupplierVO> getAllSupplier(String company, String client) {
		return supplierRepo.findAllByCompanyAndClient(company, client);
	}

	@Override
	public Optional<SupplierVO> getSupplierById(Long supplierid) {
		return supplierRepo.findById(supplierid);
	}

	@Override
	public SupplierVO createSupplier(SupplierVO supplierVO) {
		supplierVO.setDupchk(supplierVO.getSuppliertype() + supplierVO.getCompany() + supplierVO.getCustomer()
				+ supplierVO.getClient() + supplierVO.getSuppliername() + supplierVO.getSuppliershortname());
		return supplierRepo.save(supplierVO);
	}

	@Override
	public Optional<SupplierVO> updateSupplier(SupplierVO supplierVO) {
		if (supplierRepo.existsById(supplierVO.getSupplierid())) {
			supplierVO.setDupchk(supplierVO.getSuppliertype() + supplierVO.getCompany() + supplierVO.getCustomer()
					+ supplierVO.getClient() + supplierVO.getSuppliername() + supplierVO.getSuppliershortname());
			return Optional.of(supplierRepo.save(supplierVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteSupplier(Long supplierid) {
		supplierRepo.deleteById(supplierid);
	}

	// LocationMapping

	@Override
	public List<LocationMappingVO> getAllLocationMapping() {
		return locationMappingRepo.findAll();
	}

	@Override
	public Optional<LocationMappingVO> getLocationMappingById(Long locationmappingid) {
		return locationMappingRepo.findById(locationmappingid);
	}

	@Override
	public LocationMappingVO createLocationMapping(LocationMappingVO locationMappingVO) {
		return locationMappingRepo.save(locationMappingVO);
	}

	@Override
	public Optional<LocationMappingVO> updateLocationMapping(LocationMappingVO locationMappingVO) {
		if (locationMappingRepo.existsById(locationMappingVO.getLocationmappingid())) {
			return Optional.of(locationMappingRepo.save(locationMappingVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteLocationMapping(Long locationmappingid) {
		locationMappingRepo.deleteById(locationmappingid);
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
		carrierVO.setCarriername(carrierVO.getCarriername().toUpperCase());
		carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
		carrierVO.setDupchk(carrierVO.getCompany() + carrierVO.getCarriername() + carrierVO.getCarriershortname());
		return carrierRepo.save(carrierVO);
	}

	@Override
	public Optional<CarrierVO> updateCarrier(CarrierVO carrierVO) {
		if (carrierRepo.existsById(carrierVO.getCarrierid())) {
			carrierVO.setUpdatedby(carrierVO.getUserid());
			carrierVO.setCarriername(carrierVO.getCarriername().toUpperCase());
			carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
			carrierVO.setDupchk(carrierVO.getCompany() + carrierVO.getCarriername() + carrierVO.getCarriershortname());
			return Optional.of(carrierRepo.save(carrierVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCarrier(Long carrierid) {
		carrierRepo.deleteById(carrierid);
	}

	// Employee

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(Long employeeid) {
		return employeeRepo.findById(employeeid);
	}

	@Override
	public EmployeeVO createEmployee(EmployeeVO employeeVO) {
		employeeVO.setEmployeecode(employeeVO.getEmployeecode().toUpperCase());
		employeeVO.setEmployeename(employeeVO.getEmployeename().toUpperCase());
		employeeVO.setDupchk(employeeVO.getCompany() + employeeVO.getEmployeecode());
		return employeeRepo.save(employeeVO);
	}

	@Override
	public Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO) {
		if (employeeRepo.existsById(employeeVO.getEmployeeid())) {
			employeeVO.setUpdatedby(employeeVO.getUserid());
			employeeVO.setEmployeecode(employeeVO.getEmployeecode().toUpperCase());
			employeeVO.setEmployeename(employeeVO.getEmployeename().toUpperCase());
			employeeVO.setDupchk(employeeVO.getCompany() + employeeVO.getEmployeecode());
			return Optional.of(employeeRepo.save(employeeVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteEmployee(Long employeeid) {
		employeeRepo.deleteById(employeeid);
	}

	// UserLogin

	@Override
	public List<UserLoginVO> getAllUserLogin() {
		return userLoginRepo.findAll();
	}

	@Override
	public Optional<UserLoginVO> getUserLoginById(Long userloginid) {
		return userLoginRepo.findById(userloginid);
	}

	@Override
	public UserLoginVO createUserLogin(UserLoginVO userLoginVO) {
		userLoginVO.setDupchk(userLoginVO.getCompany() + userLoginVO.getUserid());
		return userLoginRepo.save(userLoginVO);
	}

	@Override
	public Optional<UserLoginVO> updateUserLogin(UserLoginVO userLoginVO) {
		if (userLoginRepo.existsById(userLoginVO.getUserloginid())) {
			userLoginVO.setUpdatedby(userLoginVO.getUserid());
			userLoginVO.setDupchk(userLoginVO.getCompany() + userLoginVO.getUserid());
			return Optional.of(userLoginRepo.save(userLoginVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteUserLogin(Long userloginid) {
		userLoginRepo.deleteById(userloginid);
	}

	@Override
	public Set<Object[]> getAllNameAndEmployeeCodeByCompany(String company) {
		return employeeRepo.findAllNameAndEmployeeCodeByCompany(company);
	}

	@Override
	public Set<Object[]> getAllCustomerAndClientByCompany(String company) {
		return customerRepo.findAllCustomerAndClientByCompany(company);
	}

	@Override
	public Set<Object[]> getAllBranchCodeAndBranchByCompany(String client,String company) {
		return clientBranchRepo.findAllBranchCodeAndBranchByCompany(client,company);
	}

}
