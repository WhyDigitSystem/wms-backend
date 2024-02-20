package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.CellTypeRepo;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.UnitRepo;
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
	WarehouseLocationRepo warehouseLocationRepo ;
	
	@Autowired
	MaterialRepo materialRepo;
	
	
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
			// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return warehouseLocationRepo.findAllByCompany(company);
	}

	@Override
	public Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid) {
		
		return warehouseLocationRepo.findById(warehouselocationid);
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
		// TODO Auto-generated method stub
		return materialRepo.findAll();
	}

	@Override
	public List<MaterialVO> getAllMaterialsByCompanyAndClient(String company,String client) {
		// TODO Auto-generated method stub
		return materialRepo.findAllByCompanyAndClient(company,client);
	}

	@Override
	public Optional<MaterialVO> getMaterialById(Long materialid) {
		// TODO Auto-generated method stub
		return materialRepo.findById(materialid);
	}

	@Override
	public MaterialVO createMaterial(MaterialVO materialVO) {
		// TODO Auto-generated method stub
		materialVO.setDupchk(materialVO.getCompany()+materialVO.getCustomer()+materialVO.getClient()+materialVO.getPartno()+materialVO.getPartdesc());;
		return materialRepo.save(materialVO);
	}

	@Override
	public Optional<MaterialVO> updateMaterial(MaterialVO materialVO) {
		if (materialRepo.existsById(materialVO.getMaterialid())) {
			materialVO.setDupchk(materialVO.getCompany()+materialVO.getCustomer()+materialVO.getClient()+materialVO.getPartno()+materialVO.getPartdesc());
			materialVO.setUpdatedby(materialVO.getUpdatedby());
			return Optional.of(materialRepo.save(materialVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void deleteMaterial(Long materialid) {
		// TODO Auto-generated method stub
	}

}
