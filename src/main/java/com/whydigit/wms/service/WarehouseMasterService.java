package com.whydigit.wms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.whydigit.wms.dto.BranchDTO;
import com.whydigit.wms.dto.BuyerDTO;
import com.whydigit.wms.dto.CarrierDTO;
import com.whydigit.wms.dto.CellTypeDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.DocumentTypeDTO;
import com.whydigit.wms.dto.DocumentTypeMappingDTO;
import com.whydigit.wms.dto.EmployeeDTO;
import com.whydigit.wms.dto.GroupDTO;
import com.whydigit.wms.dto.LocationMappingDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.SupplierDTO;
import com.whydigit.wms.dto.UnitDTO;
import com.whydigit.wms.dto.WarehouseDTO;
import com.whydigit.wms.dto.WarehouseLocationDTO;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.DocumentTypeMappingVO;
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

	public Map<String, Object> createUpdateGroup(GroupDTO groupDTO) throws ApplicationException;

	void deleteGroup(Long groupid);

	// Unit

	List<UnitVO> getAllUnitByOrgId(Long orgid);
	
	List<UnitVO> getAllUnit();

	Optional<UnitVO> getUnitById(Long unitid);

	Map<String, Object> createUpdateUnit(UnitDTO unitDTO) throws ApplicationException;

	void deleteUnit(Long unitid);

	// Location Type

	List<LocationTypeVO> getAllLocationType(Long orgid);

	Optional<LocationTypeVO> getLocationTypeById(Long locationtypeid);

	LocationTypeVO createUpdateLocationType(LocationTypeDTO locationTypeDTO) throws ApplicationException;

	//Optional<LocationTypeVO> updateLocationType(LocationTypeVO locationTypeVO);

	void deleteLocationType(Long locationtypeid);

	// CellType

	List<CellTypeVO> getAllCellTypeByOrgId(Long orgId);

	Optional<CellTypeVO> getCellTypeById(Long cellTypeId);

	List<CellTypeVO> getAllCellType();

	Map<String, Object> createUpdateCellType(CellTypeDTO cellTypeDTO) throws ApplicationException;


	void deleteCellType(Long celltypeid);

	// Branch

	List<BranchVO> getAllBranch(Long orgid);

	Optional<BranchVO> getBranchById(Long branchid);

	Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception;

	
	void deleteBranch(Long branchid);

	// Customer & client

	List<CustomerVO> getAllCustomer(Long orgid);

	Optional<CustomerVO> getCustomerById(Long customerid);

	Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) throws ApplicationException;

	Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO);
	
	List<Map<String, Object>> getClientAndClientCodeByOrgId(Long orgId);

	void deleteCustomer(Long customerid);

	// Client

	List<ClientVO> getAllClientByCustomer(Long orgid, String customer);
	
	List<ClientVO> getAllClientByOrgId(Long orgId);

	List<ClientBranchVO> getAllClientBranchByCustomer(Long orgid, String customer);

	Set<Object[]> getAllClientByOrgidAndAccessBranch(Long orgid, String branchcode);

	// Warehouse

	List<WarehouseVO> getAllWarehouse(Long orgid, String branch);

	Optional<WarehouseVO> getWarehouseById(Long warehouseid);

	Set<Object[]> getAllWarehouseByOrgidAndBranch(Long orgid, String branchcode);

	Map<String, Object> createUpdateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException;
	
	List<WarehouseVO> getAllWarehouse(Long orgId);


	// Warehouse Location
	List<WarehouseLocationVO> getAllWarehouseLocation(Long orgid, String branch); // Method names
																									// should be in																					// camelCase

	Set<Object[]> getAllLocationTypebyOrgIdAndWarehouse(Long orgid, String warehouse);

	Set<Object[]> getAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse, String locationtype);

	Set<Object[]> getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,
			String locationtype, String rowno);

	Set<Object[]> getAllBinsByOrgIdAndWarehouseAndLocationTypeAndRownoAndLevel(Long orgid, String warehouse,
			String locationtype, String rowno, String level);

	WarehouseLocationVO getWarehouseLocationById(Long id);

	Map<String, Object> createUpdateWarehouseLocation(WarehouseLocationDTO warehouseLocationDTO) throws ApplicationException;


	void deleteWarehouseLocation(Long warehouselocationid);

	
	// Material Master

	List<MaterialVO> getAllMaterialsByOrgIdAndClientAndCbranch(Long orgid, String client, String cbranch);

	Optional<MaterialVO> getMaterialById(Long materialid);

	MaterialVO createUpdateMaterial(MaterialDTO materialDTO) throws ApplicationException; // Return the created entity

	List<Map<String, Object>> getPartNo(Long orgId, String client, String branch, String branchCode, String customer);
	
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

	public Map<String, Object> createUpdateLocationMapping(LocationMappingDTO locationMappingDTO) throws ApplicationException;
	
	void deleteLocationMapping(Long locationMappingid);

	// Carrier

	List<CarrierVO> getAllCarrier(Long orgid, String client, String cbranch);
	
	Optional<CarrierVO> getCarrierById(Long carrierid);

	Map<String, Object> createUpdateCarrier(CarrierDTO carrierDTO)throws ApplicationException;

	void deleteCarrier(Long carrierid);
	
	List<Map<String, Object>> getAllModeOfShipment(Long orgId);
	
	List<CarrierVO> getCarrierNameByCustomer(Long orgid, String client, String cbranch,String shipmentMode);

	// employee
	
	List<EmployeeVO> getAllEmployee();

	List<EmployeeVO> getAllEmployeeByOrgId(Long orgId);

	Optional<EmployeeVO> getEmployeeById(Long employeeid);

	Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException;

	void deleteEmployee(Long employeeid);

	// UserLogin

	Set<Object[]> getAllNameAndEmployeeCodeByOrgId(Long orgid);

	Set<Object[]> getAllCustomerAndClientByOrgId(Long orgid);

	Set<Object[]> getAllBranchCodeAndBranchByOrgId(String client, Long orgid);

	// Create Pallet No
	Set<Object[]> getPalletnoByRownoAndLevelAndStartAndEnd(String rowno, String level, int startno, int endno);
	
	
	
	// Document Type 
	
	Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO)throws ApplicationException;
	
	DocumentTypeVO getDocumentTypeById(Long id) throws ApplicationException;
	
	List<DocumentTypeVO>getAllDocumentTypeByOrgId(Long orgId);
	
	List<Map<String, Object>>getClientAndClientCodeForDocTypeFillGrid(Long orgId,String screenCode);
	
	List<Map<String, Object>>getPendingDocumentTypeMapping(Long orgId,String branch,String branchCode,String finYear,String finYearIdentifier);
	
	Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)throws ApplicationException;
	
	List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId);
	
	DocumentTypeMappingVO getDocumentTypeMappingById(Long id)throws ApplicationException;

	List<Map<String, Object>> getToBinDetails(Long orgId, String branchCode, String client, String warehouse);
	
	
	void uploadMaterials(MultipartFile[] files, Long orgId, String customer, String client, String warehouse,
			String branch, String branchCode, String createdBy) throws ApplicationException, EncryptedDocumentException, IOException;

	int getTotalRows();

	int getSuccessfulUploads();

	

	

}
