package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

@Service
public interface WarehouseMasterService {

	// Group

	List<GroupVO> getAllGroup(Long orgid);

	Optional<GroupVO> getGroupById(Long groupid);

	GroupVO createGroup(GroupVO groupVO);

	Optional<GroupVO> updateGroup(GroupVO groupVO);

	void deleteGroup(Long groupid);

	// Unit

	List<UnitVO> getAllUnit(Long orgid);

	Optional<UnitVO> getUnitById(Long unitid);

	UnitVO createUnit(UnitVO unitVO);

	Optional<UnitVO> updateUnit(UnitVO unitVO);

	void deleteUnit(Long unitid);

	// Location Type

	List<LocationTypeVO> getAllLocationType(Long orgid);

	Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid);

	Optional<LocationTypeVO> findLocationTypeById(Long id);

	LocationTypeVO createLocationType(LocationTypeVO locationTypeVO);

	LocationTypeVO updateLocationType(LocationTypeVO locationTypeVO);

	void deleteLocationType(Long locationtypeid);

	// CellType

	List<CellTypeVO> getAllCellType(Long orgid);

	Optional<CellTypeVO> getCellTypeById(Long celltypeid);

	CellTypeVO createCellType(CellTypeVO cellTypeVO);

	Optional<CellTypeVO> updateCellType(CellTypeVO cellTypeVO);

	void deleteCellType(Long celltypeid);

	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	BranchVO createBranch(BranchVO branchVO);

	Optional<BranchVO> updateBranch(BranchVO branchVO);

	void deleteBranch(Long branchid);

	// Customer & client

	List<CustomerVO> getAllCustomer(Long orgid);

	Optional<CustomerVO> getCustomerById(Long customerid);

	CustomerVO createCustomer(CustomerVO customerVO);

	Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO);

	void deleteCustomer(Long customerid);

	// Client

	List<ClientVO> getAllClientByCustomer(Long orgid, String customer);

	List<ClientBranchVO> getAllClientBranchByCustomer(Long orgid, String customer);

	Set<Object[]> getAllClientByOrgidAndAccessBranch(Long orgid, String branchcode);

	// Warehouse

	List<WarehouseVO> getAllWarehouse(Long orgid, String branch);

	Optional<WarehouseVO> getWarehouseById(Long warehouseid);

	Set<Object[]> getAllWarehouseByOrgidAndBranch(Long orgid, String branchcode);

	WarehouseVO createWarehouse(WarehouseVO warehouseVO);

	Optional<WarehouseVO> updateWarehouse(WarehouseVO warehouseVO);

	void deleteWarehouse(Long warehouseid);

	// Warehouse Location
	List<WarehouseLocationVO> getAllWarehouseLocation(Long orgid, String warehouse, String branch); // Method names
																									// should be in
																									// camelCase

	Set<Object> getAllLocationTypebyOrgIdAndWarehouse(Long orgid, String warehouse);

	Set<Object> getAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse, String locationtype);

	Set<Object> getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse, String locationtype,
			String rowno);

	Set<Object[]> getAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(Long orgid, String warehouse,
			String locationtype, String rowno, String level);

	Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO); // Return the created entity

	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO);

	void deleteWarehouseLocation(Long warehouselocationid);

	// Material Master

	List<MaterialVO> getAllMaterialsByOrgIdAndClientAndCbranch(Long orgid, String client, String cbranch);

	Optional<MaterialVO> getMaterialById(Long materialid);

	MaterialVO createMaterial(MaterialVO materialVO); // Return the created entity

	Optional<MaterialVO> updateMaterial(MaterialVO materialVO);

	void deleteMaterial(Long materialid);

	// Buyer Master

	List<BuyerVO> getAllBuyer(Long orgid, String client, String cbranch);

	Optional<BuyerVO> getBuyerById(Long buyerid);

	BuyerVO createBuyer(BuyerVO buyerVO);

	Optional<BuyerVO> updateBuyer(BuyerVO buyerVO);

	void deleteBuyer(Long buyerid);

	// Supplier Master

	List<SupplierVO> getAllSupplier(Long orgid, String client, String cbranch);

	Optional<SupplierVO> getSupplierById(Long supplierid);

	SupplierVO createSupplier(SupplierVO supplierVO);

	Optional<SupplierVO> updateSupplier(SupplierVO supplierVO);

	void deleteSupplier(Long supplierid);

	// LocationMapping

	List<LocationMappingVO> getAllLocationMapping(Long orgid, String client, String branch, String warehouse);

	Optional<LocationMappingVO> getLocationMappingById(Long locationmappingid);

	LocationMappingVO createLocationMapping(LocationMappingVO locationMappingVO);

	Optional<LocationMappingVO> updateLocationMapping(LocationMappingVO locationMappingVO);

	void deleteLocationMapping(Long locationMappingid);

	// Carrier

	List<CarrierVO> getAllCarrier(Long orgid, String client, String cbranch);

	Optional<CarrierVO> getCarrierById(Long carrierid);

	CarrierVO createCarrier(CarrierVO carrierVO);

	Optional<CarrierVO> updateCarrier(CarrierVO carrierVO);

	void deleteCarrier(Long carrierid);

	// employee

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgid);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	EmployeeVO createEmployee(EmployeeVO employeeVO);

	Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO);

	void deleteEmployee(Long employeeid);

	// UserLogin

	Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid);

	Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid);

	Set<Object[]> getAllBranchCodeAndBranchByOrgId(String client, Long orgid);

}
