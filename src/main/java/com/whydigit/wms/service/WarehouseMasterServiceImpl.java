package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.BranchDTO;
import com.whydigit.wms.dto.BuyerDTO;
import com.whydigit.wms.dto.CarrierDTO;
import com.whydigit.wms.dto.CellTypeDTO;
import com.whydigit.wms.dto.ClientBranchDTO;
import com.whydigit.wms.dto.ClientDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.DocumentTypeDTO;
import com.whydigit.wms.dto.DocumentTypeDetailsDTO;
import com.whydigit.wms.dto.DocumentTypeMappingDTO;
import com.whydigit.wms.dto.DocumentTypeMappingDetailsDTO;
import com.whydigit.wms.dto.EmployeeDTO;
import com.whydigit.wms.dto.GroupDTO;
import com.whydigit.wms.dto.LocationMappingDTO;
import com.whydigit.wms.dto.LocationMappingDetailsDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.SupplierDTO;
import com.whydigit.wms.dto.UnitDTO;
import com.whydigit.wms.dto.WarehouseClientDTO;
import com.whydigit.wms.dto.WarehouseDTO;
import com.whydigit.wms.dto.WarehouseLocationDTO;
import com.whydigit.wms.dto.WarehouseLocationDetailsDTO;
import com.whydigit.wms.entity.BranchVO;
import com.whydigit.wms.entity.BuyerVO;
import com.whydigit.wms.entity.CarrierVO;
import com.whydigit.wms.entity.CellTypeVO;
import com.whydigit.wms.entity.ClientBranchVO;
import com.whydigit.wms.entity.ClientVO;
import com.whydigit.wms.entity.CustomerVO;
import com.whydigit.wms.entity.DocumentTypeDetailsVO;
import com.whydigit.wms.entity.DocumentTypeMappingDetailsVO;
import com.whydigit.wms.entity.DocumentTypeMappingVO;
import com.whydigit.wms.entity.DocumentTypeVO;
import com.whydigit.wms.entity.EmployeeVO;
import com.whydigit.wms.entity.GroupVO;
import com.whydigit.wms.entity.LocationMappingDetailsVO;
import com.whydigit.wms.entity.LocationMappingVO;
import com.whydigit.wms.entity.LocationTypeVO;
import com.whydigit.wms.entity.MaterialVO;
import com.whydigit.wms.entity.SupplierVO;
import com.whydigit.wms.entity.UnitVO;
import com.whydigit.wms.entity.WarehouseClientVO;
import com.whydigit.wms.entity.WarehouseLocationDetailsVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.entity.WarehouseVO;
import com.whydigit.wms.exception.ApplicationException;
import com.whydigit.wms.repo.BranchRepo;
import com.whydigit.wms.repo.BuyerRepo;
import com.whydigit.wms.repo.CarrierRepo;
import com.whydigit.wms.repo.CellTypeRepo;
import com.whydigit.wms.repo.ClientBranchRepo;
import com.whydigit.wms.repo.ClientRepo;
import com.whydigit.wms.repo.CustomerRepo;
import com.whydigit.wms.repo.DocumentTypeDetailsRepo;
import com.whydigit.wms.repo.DocumentTypeMappingDetailsRepo;
import com.whydigit.wms.repo.DocumentTypeMappingRepo;
import com.whydigit.wms.repo.DocumentTypeRepo;
import com.whydigit.wms.repo.EmployeeRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationMappingDetailsRepo;
import com.whydigit.wms.repo.LocationMappingRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SupplierRepo;
import com.whydigit.wms.repo.UnitRepo;
import com.whydigit.wms.repo.WarehouseBranchRepo;
import com.whydigit.wms.repo.WarehouseClientRepo;
import com.whydigit.wms.repo.WarehouseLocationDetailsRepo;
import com.whydigit.wms.repo.WarehouseLocationRepo;
import com.whydigit.wms.repo.WarehouseRepo;

@Service
public class WarehouseMasterServiceImpl implements WarehouseMasterService {

	public static final Logger LOGGER = LoggerFactory.getLogger(WarehouseMasterService.class);

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
	WarehouseClientRepo warehouseClientRepo;

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
	DocumentTypeRepo documentTypeRepo;

	@Autowired
	DocumentTypeDetailsRepo documentTypeDetailsRepo;

	@Autowired
	DocumentTypeMappingRepo documentTypeMappingRepo;

	@Autowired
	DocumentTypeMappingDetailsRepo documentTypeMappingDetailsRepo;

	@Autowired
	WarehouseBranchRepo warehouseBranchRepo;

	@Autowired
	LocationMappingDetailsRepo locationMappingDetailsRepo;

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
	public Map<String, Object> createUpdateGroup(GroupDTO groupDTO) throws ApplicationException {
		GroupVO groupVO;
		String message;

		if (ObjectUtils.isEmpty(groupDTO.getId())) {
			if (groupRepo.existsByGroupNameAndOrgId(groupDTO.getGroupName(), groupDTO.getOrgId())) {
				String errorMessage = String.format("This GroupName: %s already  in this organization.",
						groupDTO.getGroupName());
				throw new ApplicationException(errorMessage);
			}
			if (groupRepo.existsByCompanyAndOrgId(groupDTO.getCompany(), groupDTO.getOrgId())) {
				String errorMessage = String.format("This Company: %s already  in this organization.",
						groupDTO.getCompany());
				throw new ApplicationException(errorMessage);
			}
			groupVO = new GroupVO();
			groupVO.setCreatedBy(groupDTO.getCreatedBy());
			groupVO.setUpdatedBy(groupDTO.getCreatedBy());
			message = "Group creation successfully";
		} else {
			groupVO = groupRepo.findById(groupDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id is not found. Invalid Id: " + groupDTO.getId()));
			groupVO.setUpdatedBy(groupDTO.getCreatedBy());
			if (!groupVO.getGroupName().equalsIgnoreCase(groupDTO.getGroupName())) {
				if (groupRepo.existsByGroupNameAndOrgId(groupDTO.getGroupName(), groupDTO.getOrgId())) {
					String errorMessage = String.format("This GroupName: %s already  in this organization.",
							groupDTO.getGroupName());
					throw new ApplicationException(errorMessage);
				}
				groupVO.setGroupName(groupDTO.getGroupName());
			}
			if (!groupVO.getCompany().equalsIgnoreCase(groupDTO.getCompany())) {
				if (groupRepo.existsByCompanyAndOrgId(groupDTO.getCompany(), groupDTO.getOrgId())) {
					String errorMessage = String.format("This Company: %s already  in this organization.",
							groupDTO.getCompany());
					throw new ApplicationException(errorMessage);
				}
				groupVO.setCompany(groupDTO.getCompany());
			}
			message = "Group update successfully";
		}

		getGroupVOFromGroupDTO(groupVO, groupDTO);
		groupRepo.save(groupVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("groupVO", groupVO);
		return response;
	}

	private void getGroupVOFromGroupDTO(GroupVO groupVO, GroupDTO groupDTO) {
		groupVO.setGroupName(groupDTO.getGroupName());
		groupVO.setCompany(groupDTO.getCompany());
		groupVO.setOrgId(groupDTO.getOrgId());
		groupVO.setActive(groupDTO.isActive());
		groupVO.setCancel(groupDTO.isCancel());
	}

	@Override
	public void deleteGroup(Long groupid) {
		groupRepo.deleteById(groupid);
	}

	// Unit

	@Override
	public List<UnitVO> getAllUnitByOrgId(Long orgid) {

		return unitRepo.findAll(orgid);
	}

	@Override
	public Optional<UnitVO> getUnitById(Long unitid) {
		return unitRepo.findById(unitid);
	}

	@Override
	public Map<String, Object> createUpdateUnit(UnitDTO unitDTO) throws ApplicationException {
		UnitVO unitVO;
		String message;

		if (unitDTO.getId() != null) {
			unitVO = unitRepo.findById(unitDTO.getId()).orElseThrow(
					() -> new ApplicationException("This Id Not Found Any Information: " + unitDTO.getId()));
			unitVO.setUpdatedBy(unitDTO.getCreatedBy());
			message = "Unit Updated Successfully";
		} else {
			unitVO = new UnitVO();
			unitVO.setCreatedBy(unitDTO.getCreatedBy());
			unitVO.setUpdatedBy(unitDTO.getCreatedBy());
			message = "Unit Created Successfully";
		}

		getUnitVOAndUnitDTO(unitVO, unitDTO);
		unitRepo.save(unitVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("unitVO", unitVO);
		return response;
	}

	private void getUnitVOAndUnitDTO(UnitVO unitVO, UnitDTO unitDTO) {
		unitVO.setUnitName(unitDTO.getUnitName());
		unitVO.setUnitType(unitDTO.getUnitType());
		unitVO.setActive(unitDTO.isActive());
		unitVO.setCancel(unitDTO.isCancel());
		unitVO.setOrgId(unitDTO.getOrgId());
	}

	@Override
	public List<UnitVO> getAllUnit() {
		return unitRepo.findAll();
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
	public LocationTypeVO createUpdateLocationType(LocationTypeDTO locationTypeDTO) throws ApplicationException {
		LocationTypeVO locationTypeVO;
		if (ObjectUtils.isEmpty(locationTypeDTO.getId())) {

			if (locationTypeRepo.existsByBinTypeAndOrgId(locationTypeDTO.getBinType(), locationTypeDTO.getOrgId())) {

				String errorMessage = String.format("This LoactionType :%s Already Exists This Organization",
						locationTypeDTO.getBinType());
				throw new ApplicationException(errorMessage);

			}
		}

		if (locationTypeDTO.getId() != null) {
			locationTypeVO = locationTypeRepo.findById(locationTypeDTO.getId()).orElseThrow(
					() -> new ApplicationException("LocationType Not Found This Id :" + locationTypeDTO.getOrgId()));

			locationTypeVO.setUpdatedBy(locationTypeDTO.getCreatedBy());

			if (!locationTypeVO.getBinType().equalsIgnoreCase(locationTypeDTO.getBinType())) {
				if (locationTypeRepo.existsByBinTypeAndOrgId(locationTypeDTO.getBinType(),
						locationTypeDTO.getOrgId())) {

					String errorMessage = String.format("This LoactionType :%s Already Exists This Organization",
							locationTypeDTO.getBinType());
					throw new ApplicationException(errorMessage);

				}
				locationTypeVO.setBinType(locationTypeDTO.getBinType());
			}

		} else {
			locationTypeVO = new LocationTypeVO();
			locationTypeVO.setUpdatedBy(locationTypeDTO.getCreatedBy());
			locationTypeVO.setCreatedBy(locationTypeDTO.getCreatedBy());
		}

		getLocationTypeVOFromLocationTypeDTO(locationTypeVO, locationTypeDTO);

		return locationTypeRepo.save(locationTypeVO);

	}

	private void getLocationTypeVOFromLocationTypeDTO(LocationTypeVO locationTypeVO, LocationTypeDTO locationTypeDTO) {
		locationTypeVO.setOrgId(locationTypeDTO.getOrgId());
		locationTypeVO.setActive(locationTypeDTO.isActive());
		locationTypeVO.setCancel(locationTypeDTO.isCancel());
		locationTypeVO.setBinType(locationTypeDTO.getBinType());
	}

	@Override
	public void deleteLocationType(Long locationtypeid) {
		locationTypeRepo.deleteById(locationtypeid);
	}

	// CellType

	@Override
	public List<CellTypeVO> getAllCellTypeByOrgId(Long orgId) {
		return cellTypeRepo.findAll(orgId);
	}

	@Override
	public Optional<CellTypeVO> getCellTypeById(Long cellTypeId) {
		return cellTypeRepo.findById(cellTypeId);
	}

	@Override
	public Map<String, Object> createUpdateCellType(CellTypeDTO cellTypeDTO) throws ApplicationException {
		CellTypeVO cellTypeVO;
		String message;

		if (ObjectUtils.isEmpty(cellTypeDTO.getId())) {
			if (cellTypeRepo.existsByCellTypeAndOrgId(cellTypeDTO.getCelltype(), cellTypeDTO.getOrgId())) {
				String errorMessage = String.format("This CellType: %s Already Exists By This Organization.",
						cellTypeDTO.getCelltype());
				throw new ApplicationException(errorMessage);
			}
			message = "Cell Type Creation Successfully";
			cellTypeVO = new CellTypeVO();
			cellTypeVO.setCreatedBy(cellTypeDTO.getCreatedBy());
			cellTypeVO.setUpdatedBy(cellTypeDTO.getCreatedBy());
		} else {
			cellTypeVO = cellTypeRepo.findById(cellTypeDTO.getId()).orElseThrow(() -> new ApplicationException(
					"This Id Is Not Found Any Information, Invalid Id: " + cellTypeDTO.getId()));
			cellTypeVO.setUpdatedBy(cellTypeDTO.getCreatedBy());
			if (!cellTypeVO.getCellType().equalsIgnoreCase(cellTypeDTO.getCelltype())) {
				if (cellTypeRepo.existsByCellTypeAndOrgId(cellTypeDTO.getCelltype(), cellTypeDTO.getOrgId())) {
					String errorMessage = String.format("This CellType: %s Already Exists By This Organization.",
							cellTypeDTO.getCelltype());
					throw new ApplicationException(errorMessage);
				}
				cellTypeVO.setCellType(cellTypeDTO.getCelltype());
			}
			message = "Cell Type Updation Successfully";
		}

		getCellTypeVOFromCellTypeDTO(cellTypeVO, cellTypeDTO);

		cellTypeRepo.save(cellTypeVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("cellTypeVO", cellTypeVO);

		return response;
	}

	private void getCellTypeVOFromCellTypeDTO(CellTypeVO cellTypeVO, CellTypeDTO cellTypeDTO) {
		cellTypeVO.setCellType(cellTypeDTO.getCelltype());
		cellTypeVO.setActive(cellTypeDTO.isActive());
		cellTypeVO.setCancel(cellTypeDTO.isCancel());
		cellTypeVO.setOrgId(cellTypeDTO.getOrgId());
	}

	@Override
	public List<CellTypeVO> getAllCellType() {
		return cellTypeRepo.findAll();
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
	@Transactional
	public Map<String, Object> createUpdateBranch(BranchDTO branchDTO) throws Exception {
		BranchVO branchVO;
		String message = null;

		if (ObjectUtils.isEmpty(branchDTO.getId())) {
			// Check if the branch already exists for creation
			if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
						branchDTO.getBranch());
				throw new ApplicationException(errorMessage);
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
				String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
						branchDTO.getBranchCode());
				throw new ApplicationException(errorMessage);
			}

			// Create new branch
			branchVO = new BranchVO();
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());
			message = "Branch Created Successfully";
		} else {
			// Update existing branch
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + branchDTO.getId()));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranch().equalsIgnoreCase(branchDTO.getBranch())) {
				if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This Branch: %s Already Exists in This Organization",
							branchDTO.getBranch());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranch(branchDTO.getBranch().toUpperCase());
			}

			if (!branchVO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {
				if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {
					String errorMessage = String.format("This BranchCode: %s Already Exists in This Organization",
							branchDTO.getBranchCode());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
			}

			message = "Branch Updated Successfully";
		}

		getBranchVOFromBranchDTO(branchVO, branchDTO);
		branchRepo.save(branchVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("branchVO", branchVO);
		return response;
	}

	private void getBranchVOFromBranchDTO(BranchVO branchVO, BranchDTO branchDTO) {
		branchVO.setBranch(branchDTO.getBranch().toUpperCase());
		branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());
		branchVO.setOrgId(branchDTO.getOrgId());
		branchVO.setAddressLine1(branchDTO.getAddressLine1());
		branchVO.setAddressLine2(branchDTO.getAddressLine2());
		branchVO.setPan(branchDTO.getPan());
		branchVO.setGstIn(branchDTO.getGstIn());
		branchVO.setPhone(branchDTO.getPhone());
		branchVO.setState(branchDTO.getState().toUpperCase());
		branchVO.setCity(branchDTO.getCity().toUpperCase());
		branchVO.setPinCode(branchDTO.getPinCode());
		branchVO.setCountry(branchDTO.getCountry().toUpperCase());
		branchVO.setStateNo(branchDTO.getStateNo().toUpperCase());
		branchVO.setStateCode(branchDTO.getStateCode().toUpperCase());
		branchVO.setLccurrency(branchDTO.getLccurrency());
		branchVO.setCancelRemarks(branchDTO.getCancelRemarks());
		branchVO.setActive(branchDTO.isActive());
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
	@Transactional
	public Map<String, Object> createUpdateCustomer(CustomerDTO customerDTO) throws ApplicationException {
		CustomerVO customerVO;
		String message = null;

		if (ObjectUtils.isEmpty(customerDTO.getId())) {
			if (customerRepo.existsByPanNoAndOrgId(customerDTO.getPanNo(), customerDTO.getOrgId())) {
				String errorMessage = String.format("This PanNumber: %s Already Exists in This Organization",
						customerDTO.getPanNo());
				throw new ApplicationException(errorMessage);
			}

			if (customerRepo.existsByCustomerNameAndOrgId(customerDTO.getCustomerName(), customerDTO.getOrgId())) {
				String errorMessage = String.format("This CustomerName: %s Already Exists in This Organization",
						customerDTO.getCustomerName());
				throw new ApplicationException(errorMessage);
			}

			if (customerRepo.existsByCustomerShortNameAndOrgId(customerDTO.getCustomerShortName(),
					customerDTO.getOrgId())) {
				String errorMessage = String.format("This CustomerShortName: %s Already Exists in This Organization",
						customerDTO.getCustomerShortName());
				throw new ApplicationException(errorMessage);
			}

			message = "Customers Creation Successfully";
		}

		if (customerDTO.getId() != null) {
			// Update existing customer
			customerVO = customerRepo.findById(customerDTO.getId())
					.orElseThrow(() -> new ApplicationException("Customer not found with id: " + customerDTO.getId()));

			customerVO.setUpdatedBy(customerDTO.getUpdatedBy());

			if (!customerVO.getCustomerName().equalsIgnoreCase(customerDTO.getCustomerName())) {
				if (customerRepo.existsByCustomerNameAndOrgId(customerDTO.getCustomerName(), customerDTO.getOrgId())) {
					String errorMessage = String.format("This CustomerName: %s Already Exists in This Organization",
							customerDTO.getCustomerName());
					throw new ApplicationException(errorMessage);
				}
				customerVO.setCustomerName(customerDTO.getCustomerName());
			}

			if (!customerVO.getCustomerShortName().equalsIgnoreCase(customerDTO.getCustomerShortName())) {
				if (customerRepo.existsByCustomerShortNameAndOrgId(customerDTO.getCustomerShortName(),
						customerDTO.getOrgId())) {
					String errorMessage = String.format(
							"This CustomerShortName: %s Already Exists in This Organization",
							customerDTO.getCustomerShortName());
					throw new ApplicationException(errorMessage);
				}
				customerVO.setCustomerShortName(customerDTO.getCustomerShortName());
			}

			if (!customerVO.getPanNo().equalsIgnoreCase(customerDTO.getPanNo())) {
				if (customerRepo.existsByPanNoAndOrgId(customerDTO.getPanNo(), customerDTO.getOrgId())) {
					String errorMessage = String.format("This PanNumber: %s Already Exists in This Organization",
							customerDTO.getPanNo());
					throw new ApplicationException(errorMessage);
				}
				customerVO.setPanNo(customerDTO.getPanNo());
			}

			message = "Customers Updation Successfully";
		} else {
			// Create new customer
			customerVO = new CustomerVO();
			customerVO.setCreatedBy(customerDTO.getCreatedBy());
			customerVO.setUpdatedBy(customerDTO.getCreatedBy());
		}

		customerVO = getCustomerVOFromCustomerDTO(customerVO, customerDTO);
		customerRepo.save(customerVO);

		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("customerVO", customerVO);
		return response;
	}

	private CustomerVO getCustomerVOFromCustomerDTO(CustomerVO customerVO, CustomerDTO customerDTO)
			throws ApplicationException {
		customerVO.setCustomerName(customerDTO.getCustomerName());
		customerVO.setOrgId(customerDTO.getOrgId());
		customerVO.setCustomerShortName(customerDTO.getCustomerShortName());
		customerVO.setPanNo(customerDTO.getPanNo());
		customerVO.setContactPerson(customerDTO.getContactPerson());
		customerVO.setMobileNumber(customerDTO.getMobileNumber());
		customerVO.setGstRegistration(customerDTO.getGstRegistration());
		customerVO.setEmailId(customerDTO.getEmailId());
		customerVO.setGroupOf(customerDTO.getGroupOf());
		customerVO.setTanNo(customerDTO.getTanNo());
		customerVO.setAddress1(customerDTO.getAddress1());
		customerVO.setAddress2(customerDTO.getAddress2());
		customerVO.setGstNo(customerDTO.getGstNo());
		customerVO.setCity(customerDTO.getCity());
		customerVO.setState(customerDTO.getState());
		customerVO.setCountry(customerDTO.getCountry());
		customerVO.setCancelRemarks(customerDTO.getCancelRemarks());
		customerVO.setActive(customerDTO.isActive());
		customerVO.setCancel(customerDTO.isCancel());

		if (customerDTO.getId() != null) {
			List<ClientVO> clientvos = clientRepo.findByCustomerVO(customerVO);
			clientRepo.deleteAll(clientvos);
			List<ClientBranchVO> clientBranchvos = clientBranchRepo.findByCustomerVO(customerVO);
			clientBranchRepo.deleteAll(clientBranchvos);
		}

		List<ClientVO> clientVOs = new ArrayList<>();
		for (ClientDTO clientDTO : customerDTO.getClientDTO()) {
			ClientVO clientVO = new ClientVO();
			if (clientRepo.existsByClientCodeAndOrgId(clientDTO.getClientCode(), customerDTO.getOrgId())) {
				String errorMessage = String.format("This ClientCode: %s Already Exists This Organization",
						clientDTO.getClientCode());
				throw new ApplicationException(errorMessage);
			}
			clientVO.setClientCode(clientDTO.getClientCode());

			if (clientRepo.existsByClientAndOrgId(clientDTO.getClient(), customerDTO.getOrgId())) {
				String errorMessage = String.format("This ClientName: %s Already Exists This Organization",
						clientDTO.getClient());
				throw new ApplicationException(errorMessage);
			}
			clientVO.setClient(clientDTO.getClient());

			clientVO.setClientType(clientDTO.getClientType());
			clientVO.setFifofife(clientDTO.getFifofife());
			clientVO.setOrgId(customerVO.getOrgId());
			clientVO.setCustomerVO(customerVO);
			clientVOs.add(clientVO);
		}
		customerVO.setClientVO(clientVOs);

		List<ClientBranchVO> clientBranchVOs = new ArrayList<>();
		for (ClientBranchDTO branchDTO : customerDTO.getClientBranchDTO()) {
			ClientBranchVO clientBranchVO = new ClientBranchVO();
			clientBranchVO.setBranchCode(branchDTO.getBranchCode());
			clientBranchVO.setBranch(branchDTO.getBranch());
			clientBranchVO.setOrgId(customerVO.getOrgId());
			clientBranchVO.setCustomerVO(customerVO);
			clientBranchVOs.add(clientBranchVO);
		}
		customerVO.setClientBranchVO(clientBranchVOs);

		return customerVO;
	}

	@Override
	public Optional<CustomerVO> updateCustomer(CustomerVO customerVO, ClientVO clientVO) {
		if (customerRepo.existsById(customerVO.getId())) {
			// customerVO.setDupchk(customerVO.getOrgId() + customerVO.getCustomerName());
			return Optional.of(customerRepo.save(customerVO));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<Map<String, Object>> getClientAndClientCodeByOrgId(Long orgId) {
		Set<Object[]> getClientDetails = clientRepo.getClientDetailsByOrgId(orgId);

		return getClient(getClientDetails);
	}

	private List<Map<String, Object>> getClient(Set<Object[]> getClientDetails) {
		List<Map<String, Object>> status = new ArrayList<>();
		for (Object[] ps : getClientDetails) {
			Map<String, Object> values = new HashMap<>();
			values.put("client", ps[0] != null ? ps[0].toString() : "");
			values.put("clientCode", ps[1] != null ? ps[1].toString() : "");
			status.add(values);
		}
		return status;
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
	@Transactional
	public Map<String, Object> createUpdateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException {
	    WarehouseVO warehouseVO = new WarehouseVO();
	    String message=null;

	    if (ObjectUtils.isEmpty(warehouseDTO.getId())) {
	        // Save new warehouse
	        if (warehouseRepo.existsByWarehouseAndOrgId(warehouseDTO.getWarehouse(), warehouseDTO.getOrgId())) {
	            String errormessage = String.format("This Warehouse : %s Already Exists in This Organization.",
	                    warehouseDTO.getWarehouse());
	            throw new ApplicationException(errormessage);
	        }
	        message = "Warehouse created successfully.";
	    } else {
	        // Update existing warehouse
	        warehouseVO = warehouseRepo.findById(warehouseDTO.getId()).orElseThrow(
	                () -> new ApplicationException("Warehouse not found with id: " + warehouseDTO.getId()));
	        warehouseVO.setUpdatedBy(warehouseDTO.getCreatedBy());

	        if (!warehouseVO.getWarehouse().equalsIgnoreCase(warehouseDTO.getWarehouse())) {
	            if (warehouseRepo.existsByWarehouseAndOrgId(warehouseDTO.getWarehouse(), warehouseDTO.getOrgId())) {
	                String errormessage = String.format("This Warehouse : %s Already Exists in This Organization.",
	                        warehouseDTO.getWarehouse());
	                throw new ApplicationException(errormessage);
	            }
	            warehouseVO.setWarehouse(warehouseDTO.getWarehouse());
	        }
	        message = "Warehouse updated successfully.";
	    }

	    warehouseVO = getWarehouseVOFromWarehouseDTO(warehouseVO, warehouseDTO);
	    warehouseRepo.save(warehouseVO);

	    // Create a map to return the warehouseVO and the message
	    Map<String, Object> responseMap = new HashMap<>();
	    responseMap.put("warehouseVO", warehouseVO);
	    responseMap.put("message", message);

	    return responseMap;
	}

	private WarehouseVO getWarehouseVOFromWarehouseDTO(WarehouseVO warehouseVO, WarehouseDTO warehouseDTO)
			throws ApplicationException {
		warehouseVO.setWarehouse(warehouseDTO.getWarehouse().toUpperCase());
		warehouseVO.setBranchCode(warehouseDTO.getBranchCode().toUpperCase());
		warehouseVO.setBranch(warehouseDTO.getBranch().toUpperCase());
		warehouseVO.setOrgId(warehouseDTO.getOrgId());
		warehouseVO.setActive(warehouseDTO.isActive());
		warehouseVO.setCreatedBy(warehouseDTO.getCreatedBy());
		warehouseVO.setUpdatedBy(warehouseDTO.getCreatedBy());
		warehouseVO.setCancel(warehouseDTO.isCancel());

		if (warehouseDTO.getId() != null) {
			List<WarehouseClientVO> warehouseClientVOs = warehouseClientRepo.findByWarehouseVO(warehouseVO);
			warehouseClientRepo.deleteAll(warehouseClientVOs);
		}

		List<WarehouseClientVO> warehouseClientVOs = new ArrayList<>();
		for (WarehouseClientDTO warehouseClientDTO : warehouseDTO.getWarehouseClientDTO()) {
			WarehouseClientVO warehouseClientVO = new WarehouseClientVO();

			if (warehouseClientRepo.existsByClientAndOrgId(warehouseClientDTO.getClient(), warehouseDTO.getOrgId())) {
				String errorMessage = String.format("This ClientName : %s Already Exists in This Organization",
						warehouseClientDTO.getClient());
				throw new ApplicationException(errorMessage);
			}
			warehouseClientVO.setClient(warehouseClientDTO.getClient());

			if (warehouseClientRepo.existsByClientCodeAndOrgId(warehouseClientDTO.getClientCode(),
					warehouseDTO.getOrgId())) {
				String errorMessage = String.format("This ClientCode : %s Already Exists in This Organization",
						warehouseClientDTO.getClientCode());
				throw new ApplicationException(errorMessage);
			}
			warehouseClientVO.setClientCode(warehouseClientDTO.getClientCode());
			warehouseClientVO.setActive(warehouseClientDTO.isActive());
			warehouseClientVO.setCancel(warehouseClientDTO.isCancel());
			warehouseClientVO.setOrgId(warehouseDTO.getOrgId());
			warehouseClientVO.setWarehouseVO(warehouseVO);
			warehouseClientVOs.add(warehouseClientVO);
		}
		warehouseVO.setWarehouseClientVO(warehouseClientVOs);
		return warehouseVO;
	}

	// Warehouse Location

	@Override
	public List<WarehouseLocationVO> getAllWarehouseLocation(Long orgid,String branch) {

		return warehouseLocationRepo.findAllByOrgId(orgid, branch);
	}

	@Override
	public WarehouseLocationVO getWarehouseLocationById(Long warehouselocationid) {
		
		WarehouseLocationVO locationVO= new WarehouseLocationVO();
		locationVO=warehouseLocationRepo.findById(warehouselocationid).orElse(null);
		return locationVO;
	}

	@Override
	public Set<Object[]> getAllLocationTypebyOrgIdAndWarehouse(Long orgid, String warehouse) {

		return warehouseLocationRepo.findAllLocationTypeByOrgIdAndWarehouse(orgid, warehouse);
	}

	// get All Row no based on company and Warehouse and Location type
	@Override
	public Set<Object[]> getAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse,
			String locationtype) {

		return warehouseLocationRepo.findAllRownoByOrgIdAndWarehouseAndBinType(orgid, warehouse, locationtype);
	}

	// get All Level no based on company and Warehouse , Location type and Rowno
	@Override
	public Set<Object[]> getAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,
			String locationtype, String rowno) {

		return warehouseLocationRepo.findAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowNo(orgid, warehouse,
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
	public Map<String, Object> createUpdateWarehouseLocation(WarehouseLocationDTO warehouseLocationDTO)
			throws ApplicationException {
		WarehouseLocationVO warehouseLocationVO = new WarehouseLocationVO();
		String message = "";

		if (ObjectUtils.isEmpty(warehouseLocationDTO.getId())) {
			warehouseLocationVO.setCreatedBy(warehouseLocationDTO.getCreatedBy());
			warehouseLocationVO.setUpdatedBy(warehouseLocationDTO.getCreatedBy());
			mapWarehouseLocationDTOToWarehouseLocationVO(warehouseLocationDTO, warehouseLocationVO);
			message = "Warehouse Location Created SuccessFully";
		} else {
			warehouseLocationVO = warehouseLocationRepo.findById(warehouseLocationDTO.getId()).orElse(null);
			warehouseLocationVO.setUpdatedBy(warehouseLocationDTO.getCreatedBy());
			mapWarehouseLocationDTOToWarehouseLocationVO(warehouseLocationDTO, warehouseLocationVO);
			message = "Warehouse Location Updated SuccessFully";
		}
		warehouseLocationRepo.save(warehouseLocationVO);
		Map<String, Object> response = new HashMap<>();
		response.put("warehouseLocationVO", warehouseLocationVO);
		response.put("message", message);
		return response;

	}

	private void mapWarehouseLocationDTOToWarehouseLocationVO(WarehouseLocationDTO warehouseLocationDTO,
			WarehouseLocationVO warehouseLocationVO) throws ApplicationException {
		warehouseLocationVO.setBranch(warehouseLocationDTO.getBranch());
		warehouseLocationVO.setBranchCode(warehouseLocationDTO.getBranchCode());
		warehouseLocationVO.setWarehouse(warehouseLocationDTO.getWarehouse());
		warehouseLocationVO.setBinType(warehouseLocationDTO.getBinType());
		warehouseLocationVO.setRowNo(warehouseLocationDTO.getRowNo());
		warehouseLocationVO.setLevel(warehouseLocationDTO.getLevel());
		warehouseLocationVO.setCellFrom(warehouseLocationDTO.getCellFrom());
		warehouseLocationVO.setCellTo(warehouseLocationDTO.getCellTo());
		warehouseLocationVO.setCreatedBy(warehouseLocationDTO.getCreatedBy());
		warehouseLocationVO.setOrgId(warehouseLocationDTO.getOrgId());
		List<WarehouseLocationDetailsVO> warehouseLocationDetailsVO = new ArrayList<>();
		if (ObjectUtils.isNotEmpty(warehouseLocationDTO.getId())) {
			warehouseLocationDetailsVO = warehouseLocationVO.getWarehouseLocationDetailsVO();
			List<WarehouseLocationDetailsDTO> warehouseLocationDetailsDTO = warehouseLocationDTO
					.getWarehouseLocationDetailsDTO();
			for (WarehouseLocationDetailsDTO warehouseLocationDetailsDTO2 : warehouseLocationDetailsDTO) {
				if (ObjectUtils.isEmpty(warehouseLocationDetailsDTO2.getId())) {
					WarehouseLocationDetailsVO warehouseLocationDetailsVO1 = new WarehouseLocationDetailsVO();

					if (warehouseLocationDetailsRepo.existsByBinAndOrgIdAndBranchCodeAndWarehouse(
							warehouseLocationDetailsDTO2.getBin(), warehouseLocationDTO.getOrgId(),
							warehouseLocationDTO.getBranchCode(), warehouseLocationDTO.getWarehouse())) {
						String errorMessage = String.format("Bin : %s Already Exists in This Warehouse",
								warehouseLocationDetailsDTO2.getBin());
						throw new ApplicationException(errorMessage);
					}
					warehouseLocationDetailsVO1.setBin(warehouseLocationDetailsDTO2.getBin());
					warehouseLocationDetailsVO1.setBinCategory(warehouseLocationDetailsDTO2.getBinCategory());
					warehouseLocationDetailsVO1.setStatus(warehouseLocationDetailsDTO2.getStatus());
					warehouseLocationDetailsVO1.setCore(warehouseLocationDetailsDTO2.getCore());
					warehouseLocationDetailsVO1.setBranch(warehouseLocationDTO.getBranch());
					warehouseLocationDetailsVO1.setBranchCode(warehouseLocationDTO.getBranchCode());
					warehouseLocationDetailsVO1.setWarehouse(warehouseLocationDTO.getWarehouse());
					warehouseLocationDetailsVO1.setBinType(warehouseLocationDTO.getBinType());
					warehouseLocationDetailsVO1.setRowNo(warehouseLocationDTO.getRowNo());
					warehouseLocationDetailsVO1.setLevel(warehouseLocationDTO.getLevel());
					warehouseLocationDetailsVO1.setCellFrom(warehouseLocationDTO.getCellFrom());
					warehouseLocationDetailsVO1.setCellTo(warehouseLocationDTO.getCellTo());
					warehouseLocationDetailsVO1.setOrgId(warehouseLocationDTO.getOrgId());
					warehouseLocationDetailsVO1.setWarehouseLocationVO(warehouseLocationVO);
					warehouseLocationDetailsVO.add(warehouseLocationDetailsVO1);
				} else {
					WarehouseLocationDetailsVO warehouseLocationDetailsVO1 = warehouseLocationDetailsRepo
							.findById(warehouseLocationDetailsDTO2.getId()).orElse(null);
					if (!warehouseLocationDetailsVO1.getBin().equalsIgnoreCase(warehouseLocationDetailsDTO2.getBin())) {
						if (warehouseLocationDetailsRepo.existsByBinAndOrgIdAndBranchCodeAndWarehouse(
								warehouseLocationDetailsDTO2.getBin(), warehouseLocationDTO.getOrgId(),
								warehouseLocationDTO.getBranchCode(), warehouseLocationDTO.getWarehouse())) {
							String errorMessage = String.format("Bin : %s Already Exists in This Warehouse",
									warehouseLocationDetailsDTO2.getBin());
							throw new ApplicationException(errorMessage);
						}
						warehouseLocationDetailsVO1.setBin(warehouseLocationDetailsDTO2.getBin());
					}

					warehouseLocationDetailsVO1.setBinCategory(warehouseLocationDetailsDTO2.getBinCategory());
					warehouseLocationDetailsVO1.setStatus(warehouseLocationDetailsDTO2.getStatus());
					warehouseLocationDetailsVO1.setCore(warehouseLocationDetailsDTO2.getCore());
					warehouseLocationDetailsVO1.setBranch(warehouseLocationDTO.getBranch());
					warehouseLocationDetailsVO1.setBranchCode(warehouseLocationDTO.getBranchCode());
					warehouseLocationDetailsVO1.setWarehouse(warehouseLocationDTO.getWarehouse());
					warehouseLocationDetailsVO1.setBinType(warehouseLocationDTO.getBinType());
					warehouseLocationDetailsVO1.setRowNo(warehouseLocationDTO.getRowNo());
					warehouseLocationDetailsVO1.setLevel(warehouseLocationDTO.getLevel());
					warehouseLocationDetailsVO1.setCellFrom(warehouseLocationDTO.getCellFrom());
					warehouseLocationDetailsVO1.setCellTo(warehouseLocationDTO.getCellTo());
					warehouseLocationDetailsVO1.setOrgId(warehouseLocationDTO.getOrgId());
					warehouseLocationDetailsVO1.setWarehouseLocationVO(warehouseLocationVO);
					warehouseLocationDetailsVO.add(warehouseLocationDetailsVO1);
				}
			}
		} else {
			List<WarehouseLocationDetailsDTO> warehouseLocationDetailsDTO = warehouseLocationDTO
					.getWarehouseLocationDetailsDTO();
			for (WarehouseLocationDetailsDTO warehouseLocationDetailsDTO2 : warehouseLocationDetailsDTO) {
				if (ObjectUtils.isEmpty(warehouseLocationDetailsDTO2.getId())) {
					WarehouseLocationDetailsVO warehouseLocationDetailsVO1 = new WarehouseLocationDetailsVO();

					if (warehouseLocationDetailsRepo.existsByBinAndOrgIdAndBranchCodeAndWarehouse(
							warehouseLocationDetailsDTO2.getBin(), warehouseLocationDTO.getOrgId(),
							warehouseLocationDTO.getBranchCode(), warehouseLocationDTO.getWarehouse())) {
						String errorMessage = String.format("Bin : %s Already Exists in This Warehouse",
								warehouseLocationDetailsDTO2.getBin());
						throw new ApplicationException(errorMessage);
					}
					warehouseLocationDetailsVO1.setBin(warehouseLocationDetailsDTO2.getBin());
					warehouseLocationDetailsVO1.setBinCategory(warehouseLocationDetailsDTO2.getBinCategory());
					warehouseLocationDetailsVO1.setStatus(warehouseLocationDetailsDTO2.getStatus());
					warehouseLocationDetailsVO1.setCore(warehouseLocationDetailsDTO2.getCore());
					warehouseLocationDetailsVO1.setBranch(warehouseLocationDTO.getBranch());
					warehouseLocationDetailsVO1.setBranchCode(warehouseLocationDTO.getBranchCode());
					warehouseLocationDetailsVO1.setWarehouse(warehouseLocationDTO.getWarehouse());
					warehouseLocationDetailsVO1.setBinType(warehouseLocationDTO.getBinType());
					warehouseLocationDetailsVO1.setRowNo(warehouseLocationDTO.getRowNo());
					warehouseLocationDetailsVO1.setLevel(warehouseLocationDTO.getLevel());
					warehouseLocationDetailsVO1.setCellFrom(warehouseLocationDTO.getCellFrom());
					warehouseLocationDetailsVO1.setCellTo(warehouseLocationDTO.getCellTo());
					warehouseLocationDetailsVO1.setOrgId(warehouseLocationDTO.getOrgId());
					warehouseLocationDetailsVO1.setWarehouseLocationVO(warehouseLocationVO);
					warehouseLocationDetailsVO.add(warehouseLocationDetailsVO1);
				}
			}
		}
		warehouseLocationVO.setWarehouseLocationDetailsVO(warehouseLocationDetailsVO);

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
	public MaterialVO createUpdateMaterial(MaterialDTO materialDTO) throws ApplicationException {

		MaterialVO materialVO = new MaterialVO();

		// Check if the materialDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(materialDTO.getId())) {

			// Validate if material already exists by various unique fields
			if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(),
					materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartno())) {
				throw new ApplicationException("Partno Already exist for this Customer");
			}

			if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(),
					materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartDesc())) {
				throw new ApplicationException("PartDesc Already exist for this Customer");
			}
			materialVO.setCreatedBy(materialDTO.getCreatedBy());
			materialVO.setUpdatedBy(materialDTO.getCreatedBy());
			// Set the values from materialDTO to materialVO
			mapMaterialDtoToMaterialVo(materialDTO, materialVO);
		} else {
			// Retrieve the existing MaterialVO from the repository
			materialVO = materialRepo.findById(materialDTO.getId())
					.orElseThrow(() -> new ApplicationException("Material not found"));

			// Validate and update unique fields if changed
			if (!materialVO.getPartno().equalsIgnoreCase(materialDTO.getPartno())) {
				if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(),
						materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartno())) {
					throw new ApplicationException("Partno Already exist for this Customer");
				}
				materialVO.setPartno(materialDTO.getPartno());
			}

			if (!materialVO.getPartDesc().equalsIgnoreCase(materialDTO.getPartDesc())) {
				if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(),
						materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartDesc())) {
					throw new ApplicationException("PartDesc Already exist for this Customer");
				}
				materialVO.setPartDesc(materialDTO.getPartDesc());
			}

			materialVO.setUpdatedBy(materialDTO.getCreatedBy());
			// Update the remaining fields from materialDTO to materialVO
			mapMaterialDtoToMaterialVo(materialDTO, materialVO);
		}

		return materialRepo.save(materialVO);
	}

	private void mapMaterialDtoToMaterialVo(MaterialDTO materialDTO, MaterialVO materialVO) {
		materialVO.setItemType(materialDTO.getItemType());
		materialVO.setPartno(materialDTO.getPartno());
		materialVO.setPartDesc(materialDTO.getPartDesc());
		materialVO.setCustPartno(materialDTO.getCustPartno());
		materialVO.setGroupName(materialDTO.getGroupName());
		materialVO.setBarcode(materialDTO.getBarcode());
		materialVO.setStyleCode(materialDTO.getStyleCode());
		materialVO.setBaseSku(materialDTO.getBaseSku());
		materialVO.setPurchaseUnit(materialDTO.getPurchaseUnit());
		materialVO.setStorageUnit(materialDTO.getStorageUnit());
		materialVO.setFsn(materialDTO.getFsn());
		materialVO.setSaleUnit(materialDTO.getSaleUnit());
		materialVO.setType(materialDTO.getType());
		materialVO.setSku(materialDTO.getSku());
		materialVO.setSkuQty(materialDTO.getSkuQty());
		materialVO.setSsku(materialDTO.getSsku());
		materialVO.setSskuQty(materialDTO.getSskuQty());
		materialVO.setWeightofSkuAndUom(materialDTO.getWeightOfSkuAndUom());
		materialVO.setHsnCode(materialDTO.getHsnCode());
		materialVO.setParentChildKey(materialDTO.getParentChildKey());
		materialVO.setCbranch(materialDTO.getCbranch());
		materialVO.setCriticalStockLevel(materialDTO.getCriticalStockLevel());
		materialVO.setStatus(materialDTO.getStatus());
		materialVO.setOrgId(materialDTO.getOrgId());
		materialVO.setCustomer(materialDTO.getCustomer());
		materialVO.setClient(materialDTO.getClient());
		materialVO.setWarehouse(materialDTO.getWarehouse());
		materialVO.setWeightofSkuAndUom(materialDTO.getWeightOfSkuAndUom());
		materialVO.setBranch(materialDTO.getBranch());
		materialVO.setBranchCode(materialDTO.getBranchCode());
		materialVO.setPalletQty(materialDTO.getPalletQty());
		materialVO.setActive(materialDTO.isActive());
		materialVO.setLength(materialDTO.getLength());
		materialVO.setBreadth(materialDTO.getBreadth());
		materialVO.setHeight(materialDTO.getHeight());
		materialVO.setWeight(materialDTO.getWeight());
	}

	@Override
	public List<Map<String, Object>> getPartNo(Long orgId, String client, String branch, String branchCode,
			String customer) {
		Set<Object[]> getPartNumber = materialRepo.findPartNo(orgId, client, branch, branchCode, customer);

		return getPartNo(getPartNumber);
	}

	private List<Map<String, Object>> getPartNo(Set<Object[]> getPartNumber) {
		List<Map<String, Object>> partNoDetails = new ArrayList<>();
		for (Object[] ps : getPartNumber) {
			Map<String, Object> part = new HashMap<>();
			part.put("parNo", ps[0] != null ? ps[0].toString() : "");
			part.put("sku", ps[1] != null ? ps[1].toString() : "");
			part.put("partdescription", ps[2] != null ? ps[2].toString() : "");
			partNoDetails.add(part);
		}
		return partNoDetails;
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
	public BuyerVO createUpdateBuyer(BuyerDTO buyerDTO) throws ApplicationException {
		BuyerVO buyerVO = new BuyerVO();

		// Check if the buyerDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(buyerDTO.getId())) {

			// Validate if buyer already exists by various unique fields
			if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyer(buyerDTO.getOrgId(), buyerDTO.getCustomer(),
					buyerDTO.getClient(), buyerDTO.getBuyer())) {
				throw new ApplicationException("Buyer already exists for this Customer");
			}

			if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyerShortName(buyerDTO.getOrgId(),
					buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyerShortName())) {
				throw new ApplicationException("Buyer short name already exists for this Customer");
			}
			buyerVO.setUpdatedBy(buyerDTO.getCreatedBy());
			buyerVO.setCreatedBy(buyerDTO.getCreatedBy());
			mapBuyerDtoToBuyerVo(buyerDTO, buyerVO);
		} else {
			buyerVO = buyerRepo.findById(buyerDTO.getId())
					.orElseThrow(() -> new ApplicationException("Buyer not found"));

			if (!buyerVO.getBuyer().equalsIgnoreCase(buyerDTO.getBuyer())) {
				if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyer(buyerDTO.getOrgId(), buyerDTO.getCustomer(),
						buyerDTO.getClient(), buyerDTO.getBuyer())) {
					throw new ApplicationException("Buyer already exists for this Customer");
				}
				buyerVO.setBuyer(buyerDTO.getBuyer());
			}

			if (!buyerVO.getBuyerShortName().equalsIgnoreCase(buyerDTO.getBuyerShortName())) {
				if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyerShortName(buyerDTO.getOrgId(),
						buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyerShortName())) {
					throw new ApplicationException("Buyer short name already exists for this Customer");
				}
				buyerVO.setBuyerShortName(buyerDTO.getBuyerShortName());

			}
			buyerVO.setUpdatedBy(buyerDTO.getCreatedBy());
			mapBuyerDtoToBuyerVo(buyerDTO, buyerVO);
		}

		return buyerRepo.save(buyerVO);
	}

	private void mapBuyerDtoToBuyerVo(BuyerDTO buyerDTO, BuyerVO buyerVO) {
		buyerVO.setBuyer(buyerDTO.getBuyer());
		buyerVO.setBuyerShortName(buyerDTO.getBuyerShortName());
		buyerVO.setBuyerType(buyerDTO.getBuyerType());
		buyerVO.setBuyerGroupOf(buyerDTO.getBuyerGroupOf());
		buyerVO.setContactPerson(buyerDTO.getContactPerson());
		buyerVO.setPanNo(buyerDTO.getPanNo());
		buyerVO.setTanNo(buyerDTO.getTanNo());
		buyerVO.setZipCode(buyerDTO.getZipCode());
		buyerVO.setEmail(buyerDTO.getEmail());
		buyerVO.setGst(buyerDTO.getGst());
		buyerVO.setGstNo(buyerDTO.getGstNo());
		buyerVO.setMobileNo(buyerDTO.getMobileNo());
		buyerVO.setAddressLine1(buyerDTO.getAddressLine1());
		buyerVO.setAddressLine2(buyerDTO.getAddressLine2());
		buyerVO.setCity(buyerDTO.getCity());
		buyerVO.setState(buyerDTO.getState());
		buyerVO.setCountry(buyerDTO.getCountry());
		buyerVO.setEccNo(buyerDTO.getEccNo());
		buyerVO.setCbranch(buyerDTO.getCbranch());
		buyerVO.setOrgId(buyerDTO.getOrgId());
		buyerVO.setActive(buyerDTO.isActive());
		buyerVO.setBranchCode(buyerDTO.getBranchCode());
		buyerVO.setBranch(buyerDTO.getBranch());
		buyerVO.setClient(buyerDTO.getClient());
		buyerVO.setCustomer(buyerDTO.getCustomer());
		buyerVO.setWarehouse(buyerDTO.getWarehouse());
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
	public Map<String, Object> createUpdateSupplier(SupplierDTO supplierDTO) throws ApplicationException {

		SupplierVO supplierVO = new SupplierVO();
		String message;
		// Check if the supplierDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(supplierDTO.getId())) {

			// Validate if supplier already exists by various unique fields
			if (supplierRepo.existsByOrgIdAndCustomerAndClientAndSupplierAndSupplierType(supplierDTO.getOrgId(),
					supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplier(),
					supplierDTO.getSupplierType())) {
				throw new ApplicationException("Supplier and Supplier Type already exists for this Customer");
			}

			if (supplierRepo.existsByOrgIdAndCustomerAndClientAndAndSupplierShortNameAndSupplierType(
					supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(),
					supplierDTO.getSupplierShortName(), supplierDTO.getSupplierType())) {
				throw new ApplicationException(
						"Supplier ShortName and Supplier Type and already exists for this Customer");
			}

			supplierVO.setCreatedBy(supplierDTO.getCreatedBy());
			supplierVO.setUpdatedBy(supplierDTO.getCreatedBy());
			// Set the values from supplierDTO to supplierVO
			mapSupplierDtoToSupplierVo(supplierDTO, supplierVO);
			message = "Supplier created successfully";

		} else {

			// Retrieve the existing SupplierVO from the repository
			supplierVO = supplierRepo.findById(supplierDTO.getId())
					.orElseThrow(() -> new ApplicationException("Supplier not found"));

			// Validate and update unique fields if changed
			if (!supplierVO.getSupplier().equalsIgnoreCase(supplierDTO.getSupplier())) {
				if (supplierRepo.existsByOrgIdAndCustomerAndClientAndSupplierAndSupplierType(supplierDTO.getOrgId(),
						supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplier(),
						supplierDTO.getSupplierType())) {
					throw new ApplicationException("Supplier and Supplier Type already exists for this Customer");
				}
				supplierVO.setSupplier(supplierDTO.getSupplier());
			}

			if (!supplierVO.getSupplierShortName().equalsIgnoreCase(supplierDTO.getSupplierShortName())) {
				if (supplierRepo.existsByOrgIdAndCustomerAndClientAndAndSupplierShortNameAndSupplierType(
						supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(),
						supplierDTO.getSupplierShortName(), supplierDTO.getSupplierType())) {
					throw new ApplicationException(
							"Supplier ShortName and Supplier Type already exists for this Customer");
				}
				supplierVO.setSupplierShortName(supplierDTO.getSupplierShortName());
			}

			supplierVO.setUpdatedBy(supplierDTO.getCreatedBy());
			// Update the remaining fields from supplierDTO to supplierVO
			mapSupplierDtoToSupplierVo(supplierDTO, supplierVO);
			message = "Supplier updated successfully";
		}
		supplierRepo.save(supplierVO);

		Map<String, Object> response = new HashMap<>();
		response.put("supplierVO", supplierVO);
		response.put("message", message);
		return response;
	}

	private void mapSupplierDtoToSupplierVo(SupplierDTO supplierDTO, SupplierVO supplierVO) {
		supplierVO.setSupplier(supplierDTO.getSupplier());
		supplierVO.setSupplierShortName(supplierDTO.getSupplierShortName());
		supplierVO.setSupplierType(supplierDTO.getSupplierType());
		supplierVO.setSupplierGroupOf(supplierDTO.getSupplierGroupOf());
		supplierVO.setCategory(supplierDTO.getCategory());
		supplierVO.setPanNo(supplierDTO.getPanNo());
		supplierVO.setTanNo(supplierDTO.getTanNo());
		supplierVO.setContactPerson(supplierDTO.getContactPerson());
		supplierVO.setLandLineNo(supplierDTO.getLandLineNo());
		supplierVO.setMobileNo(supplierDTO.getMobileNo());
		supplierVO.setAddressLine1(supplierDTO.getAddressLine1());
		supplierVO.setAddressLine2(supplierDTO.getAddressLine2());
		supplierVO.setCity(supplierDTO.getCity());
		supplierVO.setCbranch(supplierDTO.getCbranch());
		supplierVO.setState(supplierDTO.getState());
		supplierVO.setCountry(supplierDTO.getCountry());
		supplierVO.setZipCode(supplierDTO.getZipCode());
		supplierVO.setEmail(supplierDTO.getEmail());
		supplierVO.setEccNo(supplierDTO.getEccNo());
		supplierVO.setRangeAddress(supplierDTO.getRangeAddress());
		supplierVO.setOrgId(supplierDTO.getOrgId());
		supplierVO.setActive(supplierDTO.isActive());
		supplierVO.setBranchCode(supplierDTO.getBranchCode());
		supplierVO.setBranch(supplierDTO.getBranch());
		supplierVO.setClient(supplierDTO.getClient());
		supplierVO.setCustomer(supplierDTO.getCustomer());
		supplierVO.setWarehouse(supplierDTO.getWarehouse());
	}

	@Override
	public List<Map<String, Object>> getActiveSupplierNameByCustomer(Long orgid, String client, String cbranch) {

		Set<Object[]> supplier = supplierRepo.findActiveSupplierNameByCustomer(orgid, client, cbranch);

		return getSupplierName(supplier);
	}

	// LocationMapping

	private List<Map<String, Object>> getSupplierName(Set<Object[]> supplier) {
		List<Map<String, Object>> supplierList = new ArrayList<>();
		for (Object[] sup : supplier) {
			Map<String, Object> suppliers = new HashMap<>();
			suppliers.put("supplierName", sup[0] != null ? sup[0].toString() : "");
			suppliers.put("supplierShortName", sup[1] != null ? sup[1].toString() : "");
			supplierList.add(suppliers);
		}

		return supplierList;
	}

	@Override
	public List<LocationMappingVO> getAllLocationMapping(Long orgid, String client, String branch, String warehouse) {
		return locationMappingRepo.findAll(orgid, client, branch, warehouse);
	}

	@Override
	public Optional<LocationMappingVO> getLocationMappingById(Long locationmappingid) {
		return locationMappingRepo.findById(locationmappingid);
	}

	@Override
	public Map<String, Object> createUpdateLocationMapping(LocationMappingDTO locationMappingDTO)
			throws ApplicationException {
		LocationMappingVO locationMappingVO;
		String message = null;
		if (ObjectUtils.isEmpty(locationMappingDTO.getId())) {

			if (locationMappingRepo.existsByWarehouseAndOrgId(locationMappingDTO.getWarehouse(),
					locationMappingDTO.getOrgId())) {
				String errorMessage = String.format("This Warehouse :%s Already Exists By This Organization.",
						locationMappingDTO.getWarehouse());
				throw new ApplicationException(errorMessage);
			}

			if (locationMappingRepo.existsByRowNoAndOrgId(locationMappingDTO.getRowNo(),
					locationMappingDTO.getOrgId())) {
				String errorMessage = String.format("This RowNo :%s Already Exists By This Organization.",
						locationMappingDTO.getWarehouse());
				throw new ApplicationException(errorMessage);
			}
			if (locationMappingRepo.existsByLevelNoAndOrgId(locationMappingDTO.getLevelNo(),
					locationMappingDTO.getOrgId())) {
				String errorMessage = String.format("This LevelNo :%s Already Exists By This Organization.",
						locationMappingDTO.getWarehouse());
				throw new ApplicationException(errorMessage);
			}

			locationMappingVO = new LocationMappingVO();
			locationMappingVO.setCreatedBy(locationMappingDTO.getCreatedBy());
			locationMappingVO.setUpdatedBy(locationMappingDTO.getCreatedBy());
			message = "Location Mapping Creation Successfully";

		} else {

			locationMappingVO = locationMappingRepo.findById(locationMappingDTO.getId())
					.orElseThrow(() -> new ApplicationException(
							"This Id Is Not Found Any Information,Invalid Id" + locationMappingDTO.getId()));
			locationMappingVO.setUpdatedBy(locationMappingDTO.getCreatedBy());

			if (!locationMappingVO.getWarehouse().equalsIgnoreCase(locationMappingDTO.getWarehouse())) {
				if (locationMappingRepo.existsByWarehouseAndOrgId(locationMappingDTO.getWarehouse(),
						locationMappingDTO.getOrgId())) {
					String errorMessage = String.format("This Warehouse :%s Already Exists By This Organization.",
							locationMappingDTO.getWarehouse());
					throw new ApplicationException(errorMessage);
				}
				locationMappingVO.setWarehouse(locationMappingDTO.getWarehouse());
			}
			if (!locationMappingVO.getRowNo().equalsIgnoreCase(locationMappingDTO.getRowNo())) {
				if (locationMappingRepo.existsByRowNoAndOrgId(locationMappingDTO.getRowNo(),
						locationMappingDTO.getOrgId())) {
					String errorMessage = String.format("This RowNo :%s Already Exists By This Organization.",
							locationMappingDTO.getWarehouse());
					throw new ApplicationException(errorMessage);
				}
				locationMappingVO.setRowNo(locationMappingDTO.getRowNo());
			}
			if (!locationMappingVO.getLevelNo().equalsIgnoreCase(locationMappingDTO.getLevelNo())) {
				if (locationMappingRepo.existsByLevelNoAndOrgId(locationMappingDTO.getLevelNo(),
						locationMappingDTO.getOrgId())) {
					String errorMessage = String.format("This LevelNo :%s Already Exists By This Organization.",
							locationMappingDTO.getWarehouse());
					throw new ApplicationException(errorMessage);
				}
				locationMappingVO.setLevelNo(locationMappingDTO.getLevelNo());
			}
			message = "Location Mapping Updation Successfully";
		}
		getLocationMappingVOFromLocationMappingDTO(locationMappingVO, locationMappingDTO);
		locationMappingRepo.save(locationMappingVO);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("message", message);
		response.put("locationMappingVO", locationMappingVO);
		return response;
	}

	private LocationMappingVO getLocationMappingVOFromLocationMappingDTO(LocationMappingVO locationMappingVO,
			LocationMappingDTO locationMappingDTO) {

		locationMappingVO.setBranch(locationMappingDTO.getBranch());
		locationMappingVO.setWarehouse(locationMappingDTO.getWarehouse());
		locationMappingVO.setBinType(locationMappingDTO.getBinType());
		locationMappingVO.setClientType(locationMappingDTO.getClientType());
		locationMappingVO.setClient(locationMappingDTO.getClient());
		locationMappingVO.setRowNo(locationMappingDTO.getRowNo());
		locationMappingVO.setCustomer(locationMappingDTO.getCustomer());
		locationMappingVO.setLevelNo(locationMappingDTO.getLevelNo());
		locationMappingVO.setClient(locationMappingDTO.getClient());
		locationMappingVO.setOrgId(locationMappingDTO.getOrgId());
		locationMappingVO.setCancel(locationMappingDTO.isCancel());
		locationMappingVO.setCancelRemark(locationMappingDTO.getCancelRemark());
		locationMappingVO.setActive(locationMappingDTO.isActive());
		locationMappingVO.setBranchCode(locationMappingDTO.getBranchCode());

		if (locationMappingDTO.getId() != null) {

			List<LocationMappingDetailsVO> detailsVOs = locationMappingDetailsRepo
					.findByLocationMappingVO(locationMappingVO);
			locationMappingDetailsRepo.deleteAll(detailsVOs);

		}

		List<LocationMappingDetailsVO> detailsVOList = new ArrayList<LocationMappingDetailsVO>();
		for (LocationMappingDetailsDTO locationMappingDetailsDTO : locationMappingDTO.getLocationMappingDetailsDTO()) {

			LocationMappingDetailsVO detailsVO = new LocationMappingDetailsVO();
			detailsVO.setRowNo(locationMappingDetailsDTO.getRowNo());
			detailsVO.setBin(locationMappingDetailsDTO.getBin());
			detailsVO.setBinStatus(locationMappingDetailsDTO.getBinStatus());
			detailsVO.setBinSeq(locationMappingDetailsDTO.getBinSeq());
			detailsVO.setLevelNo(locationMappingDetailsDTO.getLevelNo());
			detailsVO.setCore(locationMappingDetailsDTO.getCore());
			detailsVO.setBinCategory(locationMappingDetailsDTO.getBinCategory());
			detailsVO.setActive(locationMappingDetailsDTO.isActive());

			detailsVO.setLocationMappingVO(locationMappingVO);
			detailsVOList.add(detailsVO);
		}

		locationMappingVO.setLocationMappingDetails(detailsVOList);
		return locationMappingVO;

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
		return carrierRepo.findCarrierNameByCustomer(orgid, client, cbranch);
	}

	@Override
	public Map<String, Object> createUpdateCarrier(CarrierDTO carrierDTO) throws ApplicationException {

		CarrierVO carrierVO = new CarrierVO();
		String message;

		// Check if the carrierDTO ID is empty (indicating a new entry)
		if (ObjectUtils.isEmpty(carrierDTO.getId())) {

			// Validate if the carrier already by unique fields
			if (carrierRepo.existsByOrgIdAndCarrier(carrierDTO.getOrgId(), carrierDTO.getCarrier())) {
				throw new ApplicationException("Carrier already exist ");
			}
			if (carrierRepo.existsByOrgIdAndCarrierShortName(carrierDTO.getOrgId(), carrierDTO.getCarrierShortName())) {
				throw new ApplicationException("Carrier Short Name already exist ");
			}

			carrierVO.setCreatedBy(carrierDTO.getCreatedBy());
			carrierVO.setUpdatedBy(carrierDTO.getCreatedBy());
			// Set the values from carrierDTO to carrierVO
			mapCarrierDtoToCarrierVo(carrierDTO, carrierVO);
			message = "Carrier Created Successfully";

		} else {

			// Retrieve the existing CarrierVO from the repository
			carrierVO = carrierRepo.findById(carrierDTO.getId())
					.orElseThrow(() -> new ApplicationException("Carrier not found"));

			// Validate and update unique fields if changed
			if (!carrierVO.getCarrier().equalsIgnoreCase(carrierDTO.getCarrier())) {
				if (carrierRepo.existsByOrgIdAndCarrier(carrierDTO.getOrgId(), carrierDTO.getCarrier())) {
					throw new ApplicationException("Carrier already exist ");
				}
				carrierVO.setCarrier(carrierDTO.getCarrier());
			}

			if (!carrierVO.getCarrierShortName().equalsIgnoreCase(carrierDTO.getCarrierShortName())) {
				if (carrierRepo.existsByOrgIdAndCarrierShortName(carrierDTO.getOrgId(),
						carrierDTO.getCarrierShortName())) {
					throw new ApplicationException("Carrier Short Name already exist ");
				}
				carrierVO.setCarrierShortName(carrierDTO.getCarrierShortName());
			}

			carrierVO.setUpdatedBy(carrierDTO.getCreatedBy());
			// Update the remaining fields from carrierDTO to carrierVO
			mapCarrierDtoToCarrierVo(carrierDTO, carrierVO);
			message = "Carrier Updated successfully";
		}

		carrierRepo.save(carrierVO);
		Map<String, Object> response = new HashMap<>();
		response.put("carrierVO", carrierVO);
		response.put("message", message);
		return response;
	}

	private void mapCarrierDtoToCarrierVo(CarrierDTO carrierDTO, CarrierVO carrierVO) {
		carrierVO.setCarrier(carrierDTO.getCarrier());
		carrierVO.setCarrierShortName(carrierDTO.getCarrierShortName());
		carrierVO.setShipmentMode(carrierDTO.getShipmentMode());
		carrierVO.setCbranch(carrierDTO.getCbranch());
		carrierVO.setClient(carrierDTO.getClient());
		carrierVO.setOrgId(carrierDTO.getOrgId());
		carrierVO.setActive(carrierDTO.isActive());
		carrierVO.setCustomer(carrierDTO.getCustomer());
		carrierVO.setWarehouse(carrierDTO.getWarehouse());
		carrierVO.setBranch(carrierDTO.getBranch());
		carrierVO.setBranchCode(carrierDTO.getBranchCode());
		// Additional fields mapping if any
	}

	@Override
	public void deleteCarrier(Long carrierid) {
		carrierRepo.deleteById(carrierid);
	}

	// Employee

	@Override
	public List<EmployeeVO> getAllEmployeeByOrgId(Long orgId) {
		return employeeRepo.findAllEmployeeByOrgId(orgId);
	}

	@Override
	public List<EmployeeVO> getAllEmployee() {
		return employeeRepo.findAll();
	}

	@Override
	public Optional<EmployeeVO> getEmployeeById(Long employeeid) {
		return employeeRepo.findById(employeeid);
	}

	@Override
	public Map<String, Object> createEmployee(EmployeeDTO employeeDTO) throws ApplicationException {
		EmployeeVO employeeVO;
		String message = null;

		if (ObjectUtils.isEmpty(employeeDTO.getId())) {
			// Check for existing employee by employee code within the organization
			if (employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {
				String errorMessage = String.format("This EmployeeCode: %s Already Exists in This Organization",
						employeeDTO.getEmployeeCode());
				throw new ApplicationException(errorMessage);
			}
			// Create new employee
			employeeVO = new EmployeeVO();
			employeeVO.setCreatedBy(employeeDTO.getCreatedBy());
			employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());
			message = "Employee Creation Successfully";
		} else {
			// Update existing employee
			employeeVO = employeeRepo.findById(employeeDTO.getId()).orElseThrow(
					() -> new ApplicationException("ID is Not Found Any Information: " + employeeDTO.getId()));

			employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());

			if (!employeeVO.getEmployeeCode().equalsIgnoreCase(employeeDTO.getEmployeeCode())) {
				if (employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {
					String errorMessage = String.format("This EmployeeCode: %s Already Exists in This Organization",
							employeeDTO.getEmployeeCode());
					throw new ApplicationException(errorMessage);
				}
				employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
			}
			message = "Employee Update Successfully";
		}

		// Map the remaining fields
		getEmployeeVOFromEmployeeDTO(employeeVO, employeeDTO);

		// Save the entity
		employeeRepo.save(employeeVO);

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("employeeVO", employeeVO);

		return response;
	}

	private void getEmployeeVOFromEmployeeDTO(EmployeeVO employeeVO, EmployeeDTO employeeDTO) {
		employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
		employeeVO.setEmployeeName(employeeDTO.getEmployeeName());
		employeeVO.setGender(employeeDTO.getGender());
		employeeVO.setBranch(employeeDTO.getBranch());
		employeeVO.setBranchCode(employeeDTO.getBranchCode());
		employeeVO.setDepartment(employeeDTO.getDepartment());
		employeeVO.setDesignation(employeeDTO.getDesignation());
		employeeVO.setDateOfBirth(employeeDTO.getDateOfBirth());
		employeeVO.setJoiningDate(employeeDTO.getJoiningdate());
		employeeVO.setOrgId(employeeDTO.getOrgId());
		employeeVO.setCancel(employeeDTO.isCancel());
		employeeVO.setActive(employeeDTO.isActive());
		employeeVO.setCancelRemark(employeeDTO.getCancelRemark());
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

	@Override
	public Map<String, Object> createUpdateDocumentType(DocumentTypeDTO documentTypeDTO) throws ApplicationException {
		DocumentTypeVO documentTypeVO = new DocumentTypeVO();
		String message;
		if (ObjectUtils.isEmpty(documentTypeDTO.getId())) {
			if (documentTypeRepo.existsByOrgIdAndScreenCode(documentTypeDTO.getOrgId(),
					documentTypeDTO.getScreenCode())) {
				throw new ApplicationException("ScreenCode already exist ");
			}

			if (documentTypeRepo.existsByOrgIdAndDocCode(documentTypeDTO.getOrgId(), documentTypeDTO.getDocCode())) {
				throw new ApplicationException("Doc Code already exist ");
			}

			List<DocumentTypeDetailsVO> documentTypeDetailsVO = new ArrayList<>();
			if (documentTypeDTO.getDocumentTypeDetailsDTO() != null) {
				for (DocumentTypeDetailsDTO documentTypeDetailsDTO : documentTypeDTO.getDocumentTypeDetailsDTO()) {
					DocumentTypeDetailsVO documentTypeDetailsVO1 = new DocumentTypeDetailsVO();
					documentTypeDetailsVO1.setClient(documentTypeDetailsDTO.getClient());
					documentTypeDetailsVO1.setClientCode(documentTypeDetailsDTO.getClientCode());
					documentTypeDetailsVO1.setDocCode(documentTypeDTO.getDocCode());
					documentTypeDetailsVO1.setScreenCode(documentTypeDTO.getScreenCode());
					documentTypeDetailsVO1.setScreenName(documentTypeDTO.getScreenName());
					documentTypeDetailsVO1.setOrgId(documentTypeDTO.getOrgId());
					documentTypeDetailsVO1.setDocumentTypeVO(documentTypeVO);
					documentTypeDetailsVO.add(documentTypeDetailsVO1);
				}
			}
			documentTypeVO.setDocumentTypeDetailsVO(documentTypeDetailsVO);
			documentTypeVO.setDocCode(documentTypeDTO.getDocCode());
			documentTypeVO.setScreenCode(documentTypeDTO.getScreenCode());
			documentTypeVO.setCreatedBy(documentTypeDTO.getCreatedBy());
			documentTypeVO.setUpdatedBy(documentTypeDTO.getCreatedBy());
			mapDocumentTypeDTOToDocumentTypeVO(documentTypeDTO, documentTypeVO);
			message = "Document Type Created successfully";
		} else {
			documentTypeVO = documentTypeRepo.findById(documentTypeDTO.getId()).orElse(null);

			if (!documentTypeVO.getScreenCode().equalsIgnoreCase(documentTypeDTO.getScreenCode())) {
				if (documentTypeRepo.existsByOrgIdAndScreenCode(documentTypeDTO.getOrgId(),
						documentTypeDTO.getScreenCode())) {
					throw new ApplicationException("ScreenCode already exist ");
				}
				documentTypeVO.setScreenCode(documentTypeDTO.getScreenCode());
			}

			if (!documentTypeVO.getDocCode().equalsIgnoreCase(documentTypeDTO.getDocCode())) {
				if (documentTypeRepo.existsByOrgIdAndDocCode(documentTypeDTO.getOrgId(),
						documentTypeDTO.getDocCode())) {
					throw new ApplicationException("Doc Code already exist ");
				}
				documentTypeVO.setDocCode(documentTypeDTO.getDocCode());
			}

			List<DocumentTypeDetailsVO> documentTypeDetailsVO = documentTypeVO.getDocumentTypeDetailsVO();
			if (documentTypeDTO.getDocumentTypeDetailsDTO() != null) {

				for (DocumentTypeDetailsDTO documentTypeDetailsDTO : documentTypeDTO.getDocumentTypeDetailsDTO()) {
					DocumentTypeDetailsVO documentTypeDetailsVO1 = new DocumentTypeDetailsVO();
					if (ObjectUtils.isEmpty(documentTypeDetailsDTO.getId())) {
						documentTypeDetailsVO1.setClient(documentTypeDetailsDTO.getClient());
						documentTypeDetailsVO1.setClientCode(documentTypeDetailsDTO.getClientCode());
						documentTypeDetailsVO1.setDocCode(documentTypeDTO.getDocCode());
						documentTypeDetailsVO1.setScreenCode(documentTypeDTO.getScreenCode());
						documentTypeDetailsVO1.setScreenName(documentTypeDTO.getScreenName());
						documentTypeDetailsVO1.setOrgId(documentTypeDTO.getOrgId());
						documentTypeDetailsVO1.setDocumentTypeVO(documentTypeVO);
						documentTypeDetailsVO.add(documentTypeDetailsVO1);
					} else {
						documentTypeDetailsVO1 = documentTypeDetailsRepo.findById(documentTypeDetailsDTO.getId())
								.orElse(null);
						documentTypeDetailsVO1.setClient(documentTypeDetailsDTO.getClient());
						documentTypeDetailsVO1.setClientCode(documentTypeDetailsDTO.getClientCode());
						documentTypeDetailsVO1.setDocCode(documentTypeDTO.getDocCode());
						documentTypeDetailsVO1.setScreenCode(documentTypeDTO.getScreenCode());
						documentTypeDetailsVO1.setScreenName(documentTypeDTO.getScreenName());
						documentTypeDetailsVO1.setOrgId(documentTypeDTO.getOrgId());
						documentTypeDetailsVO1.setDocumentTypeVO(documentTypeVO);
						documentTypeDetailsVO.add(documentTypeDetailsVO1);
					}
				}
			}
			documentTypeVO.setDocumentTypeDetailsVO(documentTypeDetailsVO);
			documentTypeVO.setUpdatedBy(documentTypeDTO.getCreatedBy());
			// Update the remaining fields from carrierDTO to carrierVO
			mapDocumentTypeDTOToDocumentTypeVO(documentTypeDTO, documentTypeVO);
			message = "Document Type Updated successfully";

		}
		documentTypeRepo.save(documentTypeVO);
		Map<String, Object> response = new HashMap<>();
		response.put("documentTypeVO", documentTypeVO);
		response.put("message", message);
		return response;
	}

	private void mapDocumentTypeDTOToDocumentTypeVO(DocumentTypeDTO documentTypeDTO, DocumentTypeVO documentTypeVO) {

		documentTypeVO.setDescription(documentTypeDTO.getDescription());
		documentTypeVO.setOrgId(documentTypeDTO.getOrgId());
		documentTypeVO.setScreenName(documentTypeDTO.getScreenName());
	}

	@Override
	public DocumentTypeVO getDocumentTypeById(Long id) throws ApplicationException {
		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid DocumentType Id");
		}
		DocumentTypeVO documentTypeVO = documentTypeRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Document Type not found for Id: " + id));

		return documentTypeVO;
	}

	@Override
	public List<DocumentTypeVO> getAllDocumentTypeByOrgId(Long orgId) {

		return documentTypeRepo.findAllByOrgId(orgId);
	}

	@Override
	public Map<String, Object> createDocumentTypeMapping(DocumentTypeMappingDTO documentTypeMappingDTO)
			throws ApplicationException {
		String message;
		DocumentTypeMappingVO documentTypeMappingVO = new DocumentTypeMappingVO();
		documentTypeMappingVO.setBranch(documentTypeMappingDTO.getBranch());
		documentTypeMappingVO.setBranchCode(documentTypeMappingDTO.getBranchCode());
		documentTypeMappingVO.setFinYear(documentTypeMappingDTO.getFinYear());
		documentTypeMappingVO.setFinYearIdentifier(documentTypeMappingDTO.getFinYearIdentifier());
		documentTypeMappingVO.setOrgId(documentTypeMappingDTO.getOrgId());
		documentTypeMappingVO.setCreatedBy(documentTypeMappingDTO.getCreatedBy());
		documentTypeMappingVO.setUpdatedBy(documentTypeMappingDTO.getCreatedBy());

		List<DocumentTypeMappingDetailsVO> documentTypeMappingDetailsVO = new ArrayList<>();

		if (documentTypeMappingDTO.getDocumentTypeMappingDetailsDTO() != null) {
			for (DocumentTypeMappingDetailsDTO documentTypeMappingDetailsDTO : documentTypeMappingDTO
					.getDocumentTypeMappingDetailsDTO()) {
				DocumentTypeMappingDetailsVO documentTypeMappingDetailsVO1 = new DocumentTypeMappingDetailsVO();
				documentTypeMappingDetailsVO1.setScreenCode(documentTypeMappingDetailsDTO.getScreenCode());
				documentTypeMappingDetailsVO1.setScreenName(documentTypeMappingDetailsDTO.getScreenName());
				documentTypeMappingDetailsVO1.setClient(documentTypeMappingDetailsDTO.getClient());
				documentTypeMappingDetailsVO1.setClientCode(documentTypeMappingDetailsDTO.getClientCode());
				documentTypeMappingDetailsVO1.setDocCode(documentTypeMappingDetailsDTO.getDocCode());
				documentTypeMappingDetailsVO1.setBranch(documentTypeMappingDetailsDTO.getBranch());
				documentTypeMappingDetailsVO1.setBranchCode(documentTypeMappingDetailsDTO.getBranchCode());
				documentTypeMappingDetailsVO1.setPrefixField(documentTypeMappingDetailsDTO.getPrefixField());
				documentTypeMappingDetailsVO1.setFinYear(documentTypeMappingDetailsDTO.getFinYear());
				documentTypeMappingDetailsVO1
						.setFinYearIdentifier(documentTypeMappingDetailsDTO.getFinYearIdentifier());
				documentTypeMappingDetailsVO1.setConcatenation(documentTypeMappingDetailsDTO.getClient()
						+ documentTypeMappingDetailsDTO.getClientCode() + documentTypeMappingDetailsDTO.getScreenCode()
						+ documentTypeMappingDetailsDTO.getDocCode());
				documentTypeMappingDetailsVO1.setOrgId(documentTypeMappingDTO.getOrgId());
				documentTypeMappingDetailsVO1.setDocumentTypeMappingVO(documentTypeMappingVO);
				documentTypeMappingDetailsVO.add(documentTypeMappingDetailsVO1);
			}
		}
		documentTypeMappingVO.setDocumentTypeMappingDetailsVO(documentTypeMappingDetailsVO);
		documentTypeMappingRepo.save(documentTypeMappingVO);
		message = "Document Type created Successfully";
		Map<String, Object> response = new HashMap<>();
		response.put("documentTypeMappingVO", documentTypeMappingVO);
		response.put("message", message);
		return response;

	}

	@Override
	public List<Map<String, Object>> getPendingDocumentTypeMapping(Long orgId, String branch, String branchCode,
			String finYear, String finYearIdentifier) {

		Set<Object[]> pendingDocTypeDetails = documentTypeMappingRepo.getPendingDoctypeMapping(orgId, branch,
				branchCode, finYear, finYearIdentifier);
		return getPendingDocType(pendingDocTypeDetails);
	}

	private List<Map<String, Object>> getPendingDocType(Set<Object[]> pendingDocTypeDetails) {
		List<Map<String, Object>> doctypeMappingDetails = new ArrayList<>();
		for (Object[] sup : pendingDocTypeDetails) {
			Map<String, Object> doctype = new HashMap<>();
			doctype.put("screenName", sup[0] != null ? sup[0].toString() : "");
			doctype.put("screenCode", sup[1] != null ? sup[1].toString() : "");
			doctype.put("client", sup[2] != null ? sup[2].toString() : "");
			doctype.put("clientCode", sup[3] != null ? sup[3].toString() : "");
			doctype.put("docCode", sup[4] != null ? sup[4].toString() : "");
			doctype.put("finYear", sup[5] != null ? sup[5].toString() : "");
			doctype.put("branch", sup[6] != null ? sup[6].toString() : "");
			doctype.put("branchCode", sup[7] != null ? sup[7].toString() : "");
			doctype.put("finYearIdentifier", sup[8] != null ? sup[8].toString() : "");
			doctype.put("prefixField", sup[9] != null ? sup[9].toString() : "");
			doctypeMappingDetails.add(doctype);
		}

		return doctypeMappingDetails;
	}

	@Override

	public List<WarehouseVO> getAllWarehouse(Long orgId) {
		return warehouseRepo.findAllWarehouse(orgId);
	}

	public List<ClientVO> getAllClientByOrgId(Long orgId) {

		return clientRepo.getAllClientByOrgId(orgId);
	}

	@Override
	public List<Map<String, Object>> getClientAndClientCodeForDocTypeFillGrid(Long orgId, String screenCode) {

		Set<Object[]> getClientDetails = clientRepo.getClientDetailsForDocType(orgId, screenCode);

		return clientDetails(getClientDetails);
	}

	private List<Map<String, Object>> clientDetails(Set<Object[]> getClientDetails) {
		List<Map<String, Object>> clientList = new ArrayList<>();
		for (Object[] clientdetails : getClientDetails) {
			Map<String, Object> list = new HashMap<>();
			list.put("client", clientdetails[0] != null ? clientdetails[0].toString() : "");
			list.put("clientCode", clientdetails[1] != null ? clientdetails[1].toString() : "");
			clientList.add(list);
		}
		return clientList;
	}

	@Override
	public List<DocumentTypeMappingVO> getAllDocumentTypeMapping(Long orgId) {

		return documentTypeMappingRepo.findByOrgId(orgId);
	}

	@Override
	public DocumentTypeMappingVO getDocumentTypeMappingById(Long id) throws ApplicationException {
		if (ObjectUtils.isEmpty(id)) {
			throw new ApplicationException("Invalid DocumentTypeMapping Id");
		}
		DocumentTypeMappingVO documentTypeMappingVO = documentTypeMappingRepo.findById(id)
				.orElseThrow(() -> new ApplicationException("Document Type Mapping not found for Id: " + id));

		return documentTypeMappingVO;
	}
}
