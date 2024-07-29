package com.whydigit.wms.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.BranchDTO;
import com.whydigit.wms.dto.BuyerDTO;
import com.whydigit.wms.dto.CarrierDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.DocumentTypeDTO;
import com.whydigit.wms.dto.EmployeeDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.SupplierDTO;
import com.whydigit.wms.dto.UnitDTO;
import com.whydigit.wms.dto.WarehouseDTO;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.DocumentTypeVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationMappingVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.SupplierVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.exception.ApplicationException;

@Service
public interface WarehouseMasterService {

	// Group

	List<GroupVO> getAllGroup(Long orgid);

	Optional<GroupVO> getGroupById(Long groupid);

	GroupVO createGroup(GroupVO groupVO);

	Optional<GroupVO> updateGroup(GroupVO groupVO);

	void deleteGroup(Long groupid);

	// Unit

	List<UnitVO> getAllUnitByOrgId(Long orgid);
	
	List<UnitVO> getAllUnit();

	Optional<UnitVO> getUnitById(Long unitid);

	UnitVO createUpdateUnit(UnitDTO unitDTO) throws ApplicationException;

	void deleteUnit(Long unitid);

	// Location Type

	List<LocationTypeVO> getAllLocationType(Long orgid);

	Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid);

	LocationTypeVO createUpdateLocationType(LocationTypeDTO locationTypeDTO) throws ApplicationException;

	//Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO);

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

	BranchVO createUpdateBranch(BranchDTO branchDTO) throws Exception;

	
	void deleteBranch(Long branchid);

	// Customer & client

	List<CustomerVO> getAllCustomer(Long orgid);

	Optional<CustomerVO> getCustomerById(Long customerid);

	CustomerVO createUpdateCustomer(CustomerDTO customerDTO) throws ApplicationException;

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

	WarehouseVO createUpdateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException;


	// Warehouse Location
	List<WarehouseLocationVO> getAllWarehouseLocation(Long orgid, String warehouse, String branch); // Method names
																									// should be in
																									// camelCase

	Set<Object[]> getAllLocationTypebyOrgIdAndWarehouse(Long orgid, String warehouse);

	Set<Object[]> getAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse, String locationtype);

	Set<Object[]> getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,
			String locationtype, String rowno);

	Set<Object[]> getAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(Long orgid, String warehouse,
			String locationtype, String rowno, String level);

	Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO); // Return the created entity

	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO);

	void deleteWarehouseLocation(Long warehouselocationid);

	
	// Material Master

	List<MaterialVO> getAllMaterialsByOrgIdAndClientAndCbranch(Long orgid, String client, String cbranch);

	Optional<MaterialVO> getMaterialById(Long materialid);

	MaterialVO createUpdateMaterial(MaterialDTO materialDTO) throws ApplicationException; // Return the created entity

	// Buyer Master

	List<BuyerVO> getAllBuyer(Long orgid, String client, String cbranch);

	Optional<BuyerVO> getBuyerById(Long buyerid);

	BuyerVO createUpdateBuyer(BuyerDTO buyerDTO) throws ApplicationException;

	// Supplier Master

	List<SupplierVO> getAllSupplier(Long orgid, String client, String cbranch);

	Optional<SupplierVO> getSupplierById(Long supplierid);

	Map<String, Object> createUpdateSupplier(SupplierDTO supplierDTO) throws ApplicationException;

	List<Map<String, Object>> getActiveSupplierNameByCustomer(Long orgid, String client, String cbranch);

	// LocationMapping

	List<LocationMappingVO> getAllLocationMapping(Long orgid, String client, String branch, String warehouse);

	Optional<LocationMappingVO> getLocationMappingById(Long locationmappingid);

	LocationMappingVO createLocationMapping(LocationMappingVO locationMappingVO);

	Optional<LocationMappingVO> updateLocationMapping(LocationMappingVO locationMappingVO);

	void deleteLocationMapping(Long locationMappingid);

	// Carrier

	List<CarrierVO> getAllCarrier(Long orgid, String client, String cbranch);

	Optional<CarrierVO> getCarrierById(Long carrierid);

	Map<String, Object> createUpdateCarrier(CarrierDTO carrierDTO)throws ApplicationException;

	void deleteCarrier(Long carrierid);
	
	Set<Object[]> getCarrierNameByCustomer(Long orgid, String client, String cbranch);

	// employee
	
	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	EmployeeVO createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	//Optional<EmployeeVO> updateEmployee(EmployeeVO employeeVO);

	void deleteEmployee(Long employeeid);

	// UserLogin

	Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid);

	Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid);

	Set<Object[]> getAllBranchCodeAndBranchByOrgId(String client, Long orgid);

	// Create Pallet No
	Set<Object[]> getPalletnoByRownoAndLevelAndStartAndEnd(String rowno, String level, int startno, int endno);
	
	
	
	// Document Type 
	
	Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO)throws ApplicationException;
	
	DocumentTypeVO getDocumentTypeById(Long id);
	
	List<DocumentTypeVO>getAllDocumentTypeByOrgId(Long orgId);



}
