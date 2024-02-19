package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.UnitVO;
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
		
		List<WarehouseVO>getWarehouseByCompany(String company);

		WarehouseVO createWarehouse(WarehouseVO warehouseVO);

		Optional<WarehouseVO> updateWarehouse(WarehouseVO warehouseVO);

		void deleteWarehouse(Long warehouseid);
	
	// Warehouse Location
	List<WarehouseLocationVO> getAllWarehouseLocation(); // Method names should be in camelCase
	
	List<WarehouseLocationVO> getAllWarehouseLocationByCompany(String company);
	
	Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO); // Return the created entity
	
	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO);

	void deleteWarehouseLocation(Long warehouselocationid);

	
	

}
