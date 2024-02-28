package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

@Service
public interface WarehouseMasterService {

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

	// Branch

	List<BranchVO> getAllBranch();

	List<BranchVO> getAllBranchByCompany(String company);

	Optional<BranchVO> getBranchById(Long branchid);

	BranchVO createBranch(BranchVO branchVO);

	Optional<BranchVO> updateBranch(BranchVO branchVO);

	void deleteBranch(Long branchid);

	// Customer & client

	List<CustomerVO> getAllCustomer();

	Optional<CustomerVO> getCustomerById(Long customerid);

	CustomerVO createCustomer(CustomerVO customerVO);

	Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO);

	void deleteCustomer(Long customerid);

	// Warehouse

	List<WarehouseVO> getAllWarehouse();

	Optional<WarehouseVO> getWarehouseById(Long warehouseid);

	List<WarehouseVO> getWarehouseByCompany(String company);

	WarehouseVO createWarehouse(WarehouseVO warehouseVO);

	Optional<WarehouseVO> updateWarehouse(WarehouseVO warehouseVO);

	void deleteWarehouse(Long warehouseid);

	// Warehouse Location
	List<WarehouseLocationVO> getAllWarehouseLocation(); // Method names should be in camelCase

	List<WarehouseLocationVO> getAllWarehouseLocationByCompany(String company);

	Set<Object> getAllLocationTypebyCompanyAndWarehouse(String company, String warehouse);

	Set<Object> getAllRownoByCompanyAndWarehouseAndLocationType(String company, String warehouse, String locationtype);

	Set<Object> getAllLevelByCompanyAndWarehouseAndLocationTypeAndRowno(String company, String warehouse,
			String locationtype, String rowno);

	Set<Object[]> getAllBinsByCompanyAndWarehouseAndLocationTypeAndRownoAndLevel(String company, String warehouse,
			String locationtype, String rowno, String level);

	Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO); // Return the created entity

	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO);

	void deleteWarehouseLocation(Long warehouselocationid);

	// Material Master

	List<MaterialVO> getAllMaterials(); // Method names should be in camelCase

	List<MaterialVO> getAllMaterialsByCompanyAndClient(String company, String client);

	Optional<MaterialVO> getMaterialById(Long materialid);

	MaterialVO createMaterial(MaterialVO materialVO); // Return the created entity

	Optional<MaterialVO> updateMaterial(MaterialVO materialVO);

	void deleteMaterial(Long materialid);

	// Buyer Master

	List<BuyerVO> getAllBuyer(String company, String client);

	Optional<BuyerVO> getBuyerById(Long buyerid);

	BuyerVO createBuyer(BuyerVO buyerVO);

	Optional<BuyerVO> updateBuyer(BuyerVO buyerVO);

	void deleteBuyer(Long buyerid);

	// Supplier Master

	List<SupplierVO> getAllSupplier(String company, String client);

	Optional<SupplierVO> getSupplierById(Long supplierid);

	SupplierVO createSupplier(SupplierVO supplierVO);

	Optional<SupplierVO> updateSupplier(SupplierVO supplierVO);

	void deleteSupplier(Long supplierid);

	// LocationMapping

	List<LocationMappingVO> getAllLocationMapping();

	Optional<LocationMappingVO> getLocationMappingById(Long locationmappingid);

	LocationMappingVO createLocationMapping(LocationMappingVO locationMappingVO);

	Optional<LocationMappingVO> updateLocationMapping(LocationMappingVO locationMappingVO);

	void deleteLocationMapping(Long locationMappingid);

	// Carrier

	List<CarrierVO> getAllCarrier();

	Optional<CarrierVO> getCarrierById(Long carrierid);

	CarrierVO createCarrier(CarrierVO carrierVO);

	Optional<CarrierVO> updateCarrier(CarrierVO carrierVO);

	void deleteCarrier(Long carrierid);

	// employee

	List<EmployeeVO> getAllEmployee();

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	EmployeeVO createEmployee(EmployeeVO employeeVO);

	Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO);

	void deleteEmployee(Long employeeid);
	
	//UserLogin

	List<UserLoginVO> getAllUserLogin();

	Optional<UserLoginVO> getUserLoginById(Long userloginid);

	UserLoginVO createUserLogin(UserLoginVO userLoginVO);

	Optional<UserLoginVO> updateUserLogin(UserLoginVO userLoginVO);

	void deleteUserLogin(Long userloginid);
	
	 Set<Object[]> getAllNameAndEmployeeCodeByCompany(String company);
	
	 Set<Object[]>  getAllCustomerAndClientByCompany(String company);
	
	 Set<Object[]> getAllBranchCodeAndBranchByCompany(String client,String company);
}
