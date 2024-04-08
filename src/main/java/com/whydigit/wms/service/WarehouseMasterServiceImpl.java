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
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationMappingVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.SupplierVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.BuyerRepo;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CellTypeRepo;
import com.whydigit.wms.repo.ClientBranchRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.EmployeeRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationMappingRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SupplierRepo;
import com.whydigit.wms.repo.UnitRepo;
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
	ClientBranchRepo clientBranchRepo;

	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	ClientRepo clientRepo;

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

	// Group

	@Override
	public List<GroupVO> getAllGroup(Long orgid) {
		return groupRepo.findAll(orgid);
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
		if (groupRepo.existsById(groupVO.getId())) {
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
	public List<UnitVO> getAllUnit(Long orgid) {
		return unitRepo.findAll(orgid);
	}

	@Override
	public Optional<UnitVO> getUnitById(Long unitid) {
		return unitRepo.findById(unitid);
	}

	@Override
	public UnitVO createUnit(UnitVO unitVO) {
		unitVO.setCancel(false);
		return unitRepo.save(unitVO);

	}

	@Override
	public Optional<UnitVO> updateUnit(UnitVO unitVO) {
		if (unitRepo.existsById(unitVO.getId())) {
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
	public List<LocationTypeVO> getAllLocationType(Long orgid) {
		return locationTypeRepo.findAll(orgid);
	}

	@Override
	public Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid) {
		return locationTypeRepo.findById(locationtypeid);
	}

	@Override
	public LocationTypeVO createLocationType(LocationTypeVO locationTypeVO) {

		locationTypeVO.setCancel(false);
		locationTypeVO.setDupchk(locationTypeVO.getOrgId() + locationTypeVO.getLocationtype());
		return locationTypeRepo.save(locationTypeVO);
	}

	public Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO) {
		if (locationTypeRepo.existsById(locationTypeVO.getId())) {
			locationTypeVO.setCancel(false);
			locationTypeVO.setDupchk(locationTypeVO.getOrgId() + locationTypeVO.getLocationtype());
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
	public List<CellTypeVO> getAllCellType(Long orgid) {
		return cellTypeRepo.findAll(orgid);
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
		if (cellTypeRepo.existsById(cellTypeVO.getId())) {
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
	public List<BranchVO> getAllBranch(Long orgid) {
		return branchRepo.findAll(orgid);
	}

	@Override
	public Optional<BranchVO> getBranchById(Long branchid) {

		return branchRepo.findById(branchid);
	}

	@Override
	public BranchVO createBranch(BranchVO branchVO) {
		branchVO.setBranch(branchVO.getBranch().toUpperCase());
		branchVO.setBranchcode(branchVO.getBranchcode().toUpperCase());

		branchVO.setDupchk(branchVO.getOrgId() + branchVO.getBranch() + branchVO.getBranchcode());
		return branchRepo.save(branchVO);
	}

	@Override
	public Optional<BranchVO> updateBranch(BranchVO branchVO) {
		if (branchRepo.existsById(branchVO.getId())) {
			branchVO.setUpdatedby(branchVO.getUserid());

			branchVO.setDupchk(branchVO.getOrgId() + branchVO.getBranch() + branchVO.getBranchcode());
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
	public List<CustomerVO> getAllCustomer(Long orgid) {
		return customerRepo.findAll(orgid);
	}

	@Override
	public Optional<CustomerVO> getCustomerById(Long customerid) {
		return customerRepo.findById(customerid);
	}

	@Override
	public CustomerVO createCustomer(CustomerVO customerVO) {
		customerVO.setCancel(false);
		customerVO.setDupchk(customerVO.getOrgId() + customerVO.getCustomer());
		return customerRepo.save(customerVO);
	}

	@Override
	public Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO) {
		if (customerRepo.existsById(customerVO.getId())) {
			customerVO.setDupchk(customerVO.getOrgId() + customerVO.getCustomer());
			return Optional.of(customerRepo.save(customerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteCustomer(Long customerid) {
		customerRepo.deleteById(customerid);
	}

	// Client

	@Override
	public List<ClientVO> getAllClientByCustomer(Long orgid, String customer) {

		return clientRepo.findAllClientByCustomer(orgid, customer);
	}

	@Override
	public List<ClientBranchVO> getAllClientBranchByCustomer(Long orgid, String customer) {

		return clientBranchRepo.findAllBranchByCustomer(orgid, customer);
	}

	@Override
	public Set<Object[]> getAllClientByOrgidAndAccessBranch(Long orgid, String branchcode) {

		return clientRepo.findAllClientByOrgIdAndAccessBranch(orgid, branchcode);
	}

	// Warehouse

	@Override
	public List<WarehouseVO> getAllWarehouse(Long orgid, String branch) {
		return warehouseRepo.findAll(orgid, branch);
	}

	@Override
	public Optional<WarehouseVO> getWarehouseById(Long warehouseid) {
		return warehouseRepo.findById(warehouseid);
	}

	@Override
	public WarehouseVO createWarehouse(WarehouseVO warehouseVO) {
		warehouseVO.setCancel(false);
		warehouseVO.setWarehouse(warehouseVO.getWarehouse().toUpperCase());
		warehouseVO.setBranchcode(warehouseVO.getBranchcode().toUpperCase());
		warehouseVO.setDupchk(warehouseVO.getBranchcode() + warehouseVO.getWarehouse() + warehouseVO.getOrgId());
		return warehouseRepo.save(warehouseVO);
	}

	@Override
	public Optional<WarehouseVO> updateWarehouse(WarehouseVO warehouseVO) {
		if (warehouseRepo.existsById(warehouseVO.getId())) {
			warehouseVO.setDupchk(warehouseVO.getBranchcode() + warehouseVO.getWarehouse() + warehouseVO.getOrgId());
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
	public List<WarehouseLocationVO> getAllWarehouseLocation(Long orgid, String warehouse, String branch) {

		return warehouseLocationRepo.findAll(orgid, warehouse, branch);
	}

	@Override
	public Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid) {

		return warehouseLocationRepo.findById(warehouselocationid);
	}

	@Override
	public Set<Object[]> getAllLocationTypebyOrgIdAndWarehouse(Long orgid, String warehouse) {

		return warehouseLocationRepo.findAllLocationTypeByOrgIdAndWarehouse(orgid, warehouse);
	}

	// get All Row no based on company and Warehouse and Location type
	@Override
	public Set<Object[]> getAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse,
			String locationtype) {

		return warehouseLocationRepo.findAllRownoByOrgIdAndWarehouseAndLocationType(orgid, warehouse, locationtype);
	}

	// get All Level no based on company and Warehouse , Location type and Rowno
	@Override
	public Set<Object[]> getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,
			String locationtype, String rowno) {

		return warehouseLocationRepo.findAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(orgid, warehouse,
				locationtype, rowno);
	}

	// Get All Bins based on Company, warehouse,locationtype,rowno and Level
	@Override
	public Set<Object[]> getAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(Long orgid, String warehouse,
			String locationtype, String rowno, String level) {

		return warehouseLocationDetailsRepo.findAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(orgid,
				warehouse, locationtype, rowno, level);
	}

	@Override
	public WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO) {
		warehouseLocationVO.setActive(true);
		warehouseLocationVO.setCancel(false);
		warehouseLocationVO.setDupchk(warehouseLocationVO.getOrgId() + warehouseLocationVO.getWarehouse()
				+ warehouseLocationVO.getLocationtype() + warehouseLocationVO.getRowno()
				+ warehouseLocationVO.getLevel());
		return warehouseLocationRepo.save(warehouseLocationVO);

	}

	@Override
	public Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO) {
		if (warehouseLocationRepo.existsById(warehouseLocationVO.getId())) {
			warehouseLocationVO.setDupchk(warehouseLocationVO.getOrgId() + warehouseLocationVO.getWarehouse()
					+ warehouseLocationVO.getLocationtype() + warehouseLocationVO.getRowno()
					+ warehouseLocationVO.getLevel());
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
	public List<MaterialVO> getAllMaterialsByOrgIdAndClientAndCbranch(Long orgid, String client, String cbranch) {
		return materialRepo.findAllByOrgIdAndClient(orgid, client, cbranch);
	}

	@Override
	public Optional<MaterialVO> getMaterialById(Long materialid) {
		return materialRepo.findById(materialid);
	}

	@Override
	public MaterialVO createMaterial(MaterialVO materialVO) {
		materialVO.setDupchk(materialVO.getOrgId() + materialVO.getCustomer() + materialVO.getClient()
				+ materialVO.getPartno() + materialVO.getPartdesc());
		;
		return materialRepo.save(materialVO);
	}

	@Override
	public Optional<MaterialVO> updateMaterial(MaterialVO materialVO) {
		if (materialRepo.existsById(materialVO.getId())) {
			materialVO.setDupchk(materialVO.getOrgId() + materialVO.getCustomer() + materialVO.getClient()
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
	public List<BuyerVO> getAllBuyer(Long orgid, String client, String cbranch) {
		return buyerRepo.findAllByOrgIdAndClient(orgid, client, cbranch);
	}

	@Override
	public Optional<BuyerVO> getBuyerById(Long buyerid) {
		return buyerRepo.findById(buyerid);
	}

	@Override
	public BuyerVO createBuyer(BuyerVO buyerVO) {
		buyerVO.setDupchk(buyerVO.getClient() + buyerVO.getOrgId() + buyerVO.getCustomer() + buyerVO.getBuyer()
				+ buyerVO.getBuyershortname());
		return buyerRepo.save(buyerVO);
	}

	@Override
	public Optional<BuyerVO> updateBuyer(BuyerVO buyerVO) {
		if (buyerRepo.existsById(buyerVO.getId())) {
			buyerVO.setDupchk(buyerVO.getClient() + buyerVO.getOrgId() + buyerVO.getCustomer() + buyerVO.getBuyer()
					+ buyerVO.getBuyershortname());
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
	public List<SupplierVO> getAllSupplier(Long orgid, String client, String cbranch) {
		return supplierRepo.findAllByOrgIdAndClient(orgid, client, cbranch);
	}

	@Override
	public Optional<SupplierVO> getSupplierById(Long supplierid) {
		return supplierRepo.findById(supplierid);
	}

	@Override
	public SupplierVO createSupplier(SupplierVO supplierVO) {
		supplierVO.setDupchk(supplierVO.getSuppliertype() + supplierVO.getOrgId() + supplierVO.getCustomer()
				+ supplierVO.getClient() + supplierVO.getSupplier() + supplierVO.getSuppliershortname());
		return supplierRepo.save(supplierVO);
	}

	@Override
	public Optional<SupplierVO> updateSupplier(SupplierVO supplierVO) {
		if (supplierRepo.existsById(supplierVO.getId())) {
			supplierVO.setDupchk(supplierVO.getSuppliertype() + supplierVO.getOrgId() + supplierVO.getCustomer()
					+ supplierVO.getClient() + supplierVO.getSupplier() + supplierVO.getSuppliershortname());
			return Optional.of(supplierRepo.save(supplierVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteSupplier(Long supplierid) {
		supplierRepo.deleteById(supplierid);
	}

	@Override
	public Set<Object[]> getSupplierNameByCustomer(Long orgid, String client, String cbranch) {
		return supplierRepo.findSupplierNameByCustomer(orgid,client,cbranch);
	}

	// LocationMapping

	@Override
	public List<LocationMappingVO> getAllLocationMapping(Long orgid, String client, String branch, String warehouse) {
		return locationMappingRepo.findAll(orgid, client, branch, warehouse);
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
		if (locationMappingRepo.existsById(locationMappingVO.getId())) {
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
	public List<CarrierVO> getAllCarrier(Long orgid, String client, String cbranch) {
		return carrierRepo.findAll(orgid, client, cbranch);
	}

	@Override
	public Optional<CarrierVO> getCarrierById(Long carrierid) {
		return carrierRepo.findById(carrierid);
	}
	
	@Override
	public Set<Object[]> getCarrierNameByCustomer(Long orgid, String client, String cbranch) {
		return carrierRepo.findCarrierNameByCustomer(orgid,client,cbranch);
	}

	@Override
	public CarrierVO createCarrier(CarrierVO carrierVO) {
		carrierVO.setCarrier(carrierVO.getCarrier().toUpperCase());
		carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
		carrierVO.setDupchk(carrierVO.getOrgId() + carrierVO.getCarrier() + carrierVO.getCarriershortname());
		return carrierRepo.save(carrierVO);
	}

	@Override
	public Optional<CarrierVO> updateCarrier(CarrierVO carrierVO) {
		if (carrierRepo.existsById(carrierVO.getId())) {
			carrierVO.setUpdatedby(carrierVO.getUserid());
			carrierVO.setCarrier(carrierVO.getCarrier().toUpperCase());
			carrierVO.setCarriershortname(carrierVO.getCarriershortname().toUpperCase());
			carrierVO.setDupchk(carrierVO.getOrgId() + carrierVO.getCarrier() + carrierVO.getCarriershortname());
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
	public List<EmployeeVO> getAllEmployeeByOrgId(Long orgid) {
		return employeeRepo.findAllEmployeeByOrgId(orgid);
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(Long employeeid) {
		return employeeRepo.findById(employeeid);
	}

	@Override
	public EmployeeVO createEmployee(EmployeeVO employeeVO) {
		employeeVO.setEmployeecode(employeeVO.getEmployeecode().toUpperCase());
		employeeVO.setEmployeename(employeeVO.getEmployeename().toUpperCase());
		employeeVO.setDupchk(employeeVO.getOrgId() + employeeVO.getEmployeecode());
		return employeeRepo.save(employeeVO);
	}

	@Override
	public Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO) {
		if (employeeRepo.existsById(employeeVO.getId())) {
			employeeVO.setUpdatedby(employeeVO.getUserid());
			employeeVO.setEmployeecode(employeeVO.getEmployeecode().toUpperCase());
			employeeVO.setEmployeename(employeeVO.getEmployeename().toUpperCase());
			employeeVO.setDupchk(employeeVO.getOrgId() + employeeVO.getEmployeecode());
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
	public Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid) {
		return employeeRepo.findAllNameAndEmployeeCodeByOrgId(orgid);
	}

	@Override
	public Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid) {
		return customerRepo.findAllCustomerAndClientByOrgId(orgid);
	}

	@Override
	public Set<Object[]> getAllBranchCodeAndBranchByOrgId(String client, Long orgid) {
		return clientBranchRepo.findAllBranchCodeAndBranchByOrgId(client, orgid);
	}

	// find All Warehouse By Branch
	@Override
	public Set<Object[]> getAllWarehouseByOrgidAndBranch(Long orgid, String branchcode) {

		return warehouseRepo.findAllWarehouseByBranch(orgid, branchcode);
	}

	@Override
	public Set<Object[]> getPalletnoByRownoAndLevelAndStartAndEnd(String rowno, String level, int startno, int endno) {

		return warehouseLocationRepo.getPalletnoByRownoAndLevelno(rowno, level, startno, endno);
	}

}
