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
import com.whydigit.wms.dto.ClientBranchDTO;
import com.whydigit.wms.dto.ClientDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.DocumentTypeDTO;
import com.whydigit.wms.dto.EmployeeDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.SupplierDTO;
import com.whydigit.wms.dto.UnitDTO;
import com.whydigit.wms.dto.WarehouseClientDTO;
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
import com.whydigit.wms.entity.WarehouseClientVO;
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
import com.whydigit.wms.repo.DocumentTypeRepo;
import com.whydigit.wms.repo.EmployeeRepo;
import com.whydigit.wms.repo.GroupRepo;
import com.whydigit.wms.repo.LocationMappingRepo;
import com.whydigit.wms.repo.LocationTypeRepo;
import com.whydigit.wms.repo.MaterialRepo;
import com.whydigit.wms.repo.SupplierRepo;
import com.whydigit.wms.repo.UnitRepo;
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
	public List<UnitVO> getAllUnitByOrgId(Long orgid) {
		
		return unitRepo.findAll(orgid);
	}

	@Override
	public Optional<UnitVO> getUnitById(Long unitid) {
		return unitRepo.findById(unitid);
	}

	@Override
	public UnitVO createUpdateUnit(UnitDTO unitDTO) throws ApplicationException {
		UnitVO unitVO=new UnitVO();
		if(unitDTO.getId()!=null) {
			unitVO=unitRepo.findById(unitDTO.getId()).orElseThrow(()->
			new ApplicationException("This Id Not Found Any Information ."+unitDTO.getId()));
			unitVO.setUpdatedBy(unitDTO.getCreatedBy());
		}else {
			unitVO.setCreatedBy(unitDTO.getCreatedBy());
			unitVO.setUpdatedBy(unitDTO.getCreatedBy());
		}
		getUnitVOAndUnitDTO(unitVO,unitDTO);
		return unitRepo.save(unitVO);
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

			if (locationTypeRepo.existsByLocationTypeAndOrgId(locationTypeDTO.getLocationtype(),
					locationTypeDTO.getOrgId())) {

				String errorMessage = String.format("This LoactionType :%s Already Exists This Organization",
						locationTypeDTO.getLocationtype());
				throw new ApplicationException(errorMessage);

			}
		}

		if (locationTypeDTO.getId() != null) {
			locationTypeVO = locationTypeRepo.findById(locationTypeDTO.getId()).orElseThrow(
					() -> new ApplicationException("LocationType Not Found This Id :" + locationTypeDTO.getOrgId()));

			locationTypeVO.setUpdatedBy(locationTypeDTO.getCreatedBy());

			if (!locationTypeVO.getLocationType().equalsIgnoreCase(locationTypeDTO.getLocationtype())) {
				if (locationTypeRepo.existsByLocationTypeAndOrgId(locationTypeDTO.getLocationtype(),
						locationTypeDTO.getOrgId())) {
 
					String errorMessage = String.format("This LoactionType :%s Already Exists This Organization",
							locationTypeDTO.getLocationtype());
					throw new ApplicationException(errorMessage);

				}
				locationTypeVO.setLocationType(locationTypeDTO.getLocationtype());
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
		locationTypeVO.setLocationType(locationTypeDTO.getLocationtype());
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
	@Transactional
	public BranchVO createUpdateBranch(BranchDTO branchDTO) throws Exception {
		BranchVO branchVO;

		if (ObjectUtils.isEmpty(branchDTO.getId())) {

			if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {

				String errorMessage = String.format("This Branch :%s Already Exists This organization",
						branchDTO.getBranch());
				throw new ApplicationException(errorMessage);
			}

			if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {

				String errorMessage = String.format("This BranchCode :%s Already Exists This organization",
						branchDTO.getBranchCode());
				throw new ApplicationException(errorMessage);
			}

		}
		if (branchDTO.getId() != null) {
			// Update existing branch
			branchVO = branchRepo.findById(branchDTO.getId())
					.orElseThrow(() -> new ApplicationException("Branch not found with id: " + branchDTO.getId()));

			branchVO.setUpdatedBy(branchDTO.getCreatedBy());

			if (!branchVO.getBranch().equalsIgnoreCase(branchDTO.getBranch())) {

				if (branchRepo.existsByBranchAndOrgId(branchDTO.getBranch(), branchDTO.getOrgId())) {

					String errorMessage = String.format("This Branch :%s Already Exists This organization",
							branchDTO.getBranch());
					throw new ApplicationException(errorMessage);
				}
				branchVO.setBranch(branchDTO.getBranch().toUpperCase());

				if (branchDTO.getBranchCode().equalsIgnoreCase(branchDTO.getBranchCode())) {
					if (branchRepo.existsByBranchCodeAndOrgId(branchDTO.getBranchCode(), branchDTO.getOrgId())) {

						String errorMessage = String.format("This BranchCode :%s Already Exists This organization",
								branchDTO.getBranchCode());
						throw new ApplicationException(errorMessage);
					}
					branchVO.setBranchCode(branchDTO.getBranchCode().toUpperCase());

				}

			}

		} else {
			// Create new branch
			branchVO = new BranchVO();
			branchVO.setCreatedBy(branchDTO.getCreatedBy());
			branchVO.setUpdatedBy(branchDTO.getCreatedBy());
		}

		getBranchVOFromBranchDTO(branchVO, branchDTO);

		return branchRepo.save(branchVO);
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
		// branchVO.setDupchk(branchDTO.getOrgId() + branchDTO.getBranchCode() +
		// branchDTO.getBranchCode());
		branchVO.setActive(branchDTO.isActive());
		branchVO.setUserid(branchDTO.getUserid());
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
	public CustomerVO createUpdateCustomer(CustomerDTO customerDTO) throws ApplicationException {
		CustomerVO customerVO;
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
		} else {
			// Create new customer
			customerVO = new CustomerVO();
			customerVO.setCreatedBy(customerDTO.getCreatedBy());
			customerVO.setUpdatedBy(customerDTO.getCreatedBy());
		}

		customerVO = getCustomerVOFromCustomerDTO(customerVO, customerDTO);

		return customerRepo.save(customerVO);

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
		customerVO.setGrouPof(customerDTO.getGrouPof());
		customerVO.setTanNo(customerDTO.getTanNo());
		customerVO.setAddress1(customerDTO.getAddress1());
		customerVO.setAddress2(customerDTO.getAddress2());
		customerVO.setGstNo(customerDTO.getGstNo());
		customerVO.setCity(customerDTO.getCity());
		customerVO.setState(customerDTO.getState());
		customerVO.setCountry(customerDTO.getCountry());
		customerVO.setCancelRemarks(customerDTO.getCancelRemarks());
//	    customerVO.setCreatedBy(customerDTO.getCreatedBy());
//	    customerVO.setUpdatedBy(customerDTO.getCreatedBy());
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
				String errorMessage = String.format("This ClientCode : %s Already Exists This Organization",
						clientDTO.getClientCode());
				throw new ApplicationException(errorMessage);
			}

			clientVO.setClientCode(clientDTO.getClientCode());
			if (clientRepo.existsByClientAndOrgId(clientDTO.getClient(), customerDTO.getOrgId())) {
				String errorMessage = String.format("This ClientName : %s Already Exists This Organization",
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
	public WarehouseVO createUpdateWarehouse(WarehouseDTO warehouseDTO) throws ApplicationException {
		WarehouseVO warehouseVO = new WarehouseVO();

		if (ObjectUtils.isEmpty(warehouseDTO.getId())) {
			if (warehouseRepo.existsByWarehouseAndOrgId(warehouseDTO.getWarehouse(), warehouseDTO.getOrgId())) {

				String errormessage = String.format("This Warehouse : %s Already Exists  This Organization .",
						warehouseDTO.getWarehouse());

				throw new ApplicationException(errormessage);

			}
		}

		if (warehouseDTO.getId() != null) {
			// Update existing warehouse
			warehouseVO = warehouseRepo.findById(warehouseDTO.getId()).orElseThrow(
					() -> new ApplicationException("Warehouse not found with id: " + warehouseDTO.getId()));
			warehouseVO.setUpdatedBy(warehouseDTO.getCreatedBy());
			if (!warehouseVO.getWarehouse().equalsIgnoreCase(warehouseDTO.getWarehouse())) {

				if (warehouseRepo.existsByWarehouseAndOrgId(warehouseDTO.getWarehouse(), warehouseDTO.getOrgId())) {

					String errormessage = String.format("This Warehouse : %s Already Exists  This Organization .",
							warehouseDTO.getWarehouse());

					throw new ApplicationException(errormessage);
				}
				warehouseVO.setWarehouse(warehouseDTO.getWarehouse());

			} else {
				
				warehouseVO.setCreatedBy(warehouseDTO.getCreatedBy());
				warehouseVO.setUpdatedBy(warehouseDTO.getCreatedBy());

			}
			
		}
		warehouseVO = getWarehouseVOFromWarehouseDTO(warehouseVO, warehouseDTO);

		
		return warehouseRepo.save(warehouseVO);
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

		List<WarehouseClientVO> warehouseClientVOs = new ArrayList<WarehouseClientVO>();
		for (WarehouseClientDTO warehouseClientDTO : warehouseDTO.getWarehouseClientDTO()) {
			WarehouseClientVO warehouseClientVO = new WarehouseClientVO();
			if (warehouseClientRepo.existsByClientAndOrgId(warehouseClientDTO.getClient(), warehouseDTO.getOrgId())) {
				String errorMessage = String.format("This ClientName : %s Already Exists This Organization",
						warehouseClientDTO.getClient());
				throw new ApplicationException(errorMessage);
			}
			warehouseClientVO.setClient(warehouseClientDTO.getClient());

			if (warehouseClientRepo.existsByClientCodeAndOrgId(warehouseClientDTO.getClientCode(),
					warehouseDTO.getOrgId())) {
				String errorMessage = String.format("This ClientCode : %s Already Exists This Organization",
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
	public MaterialVO createUpdateMaterial(MaterialDTO materialDTO) throws ApplicationException {
	    
	    MaterialVO materialVO = new MaterialVO();
	    
	    // Check if the materialDTO ID is empty (indicating a new entry)
	    if (ObjectUtils.isEmpty(materialDTO.getId())) {
	        
	        // Validate if material already exists by various unique fields
	        if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(), materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartno())) {
	            throw new ApplicationException("Partno Already exist for this Customer");
	        }
	        
	        if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(), materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartDesc())) {
	            throw new ApplicationException("PartDesc Already exist for this Customer");
	        }
	        materialVO.setCreatedBy(materialDTO.getCreatedBy());
		    materialVO.setUpdatedBy(materialDTO.getCreatedBy());
	        // Set the values from materialDTO to materialVO
	        mapMaterialDtoToMaterialVo(materialDTO, materialVO);
	    } else {
	        // Retrieve the existing MaterialVO from the repository
	        materialVO = materialRepo.findById(materialDTO.getId()).orElseThrow(() -> new ApplicationException("Material not found"));
	        
	        // Validate and update unique fields if changed
	        if (!materialVO.getPartno().equalsIgnoreCase(materialDTO.getPartno())) {
	            if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(), materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartno())) {
	                throw new ApplicationException("Partno Already exist for this Customer");
	            }
	            materialVO.setPartno(materialDTO.getPartno());
	        }
	        
	        if (!materialVO.getPartDesc().equalsIgnoreCase(materialDTO.getPartDesc())) {
	            if (materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(), materialDTO.getCustomer(), materialDTO.getClient(), materialDTO.getPartDesc())) {
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
	    materialVO.setStylecode(materialDTO.getStyleCode());
	    materialVO.setBasesku(materialDTO.getBaseSku());
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
	    materialVO.setBranch(materialDTO.getBranch());
	    materialVO.setBranchCode(materialDTO.getBranchCode());
	    materialVO.setPalletQty(materialDTO.getPalletQty());
	    materialVO.setActive(materialDTO.isActive());
	    materialVO.setLength(materialDTO.getLength());
	    materialVO.setBreadth(materialDTO.getBreadth());
	    materialVO.setHeight(materialDTO.getHeight());
	    materialVO.setWeight(materialDTO.getWeight());
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
	        if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyer(buyerDTO.getOrgId(), buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyer())) {
	            throw new ApplicationException("Buyer already exists for this Customer");
	        }
	        
	        if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyerShortName(buyerDTO.getOrgId(), buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyerShortName())) {
	            throw new ApplicationException("Buyer short name already exists for this Customer");
	        }
	        buyerVO.setUpdatedBy(buyerDTO.getCreatedBy());
	        buyerVO.setCreatedBy(buyerDTO.getCreatedBy());
	        mapBuyerDtoToBuyerVo(buyerDTO, buyerVO);
	    } else {
	        buyerVO = buyerRepo.findById(buyerDTO.getId()).orElseThrow(() -> new ApplicationException("Buyer not found"));
	        
	        if (!buyerVO.getBuyer().equalsIgnoreCase(buyerDTO.getBuyer())) {
	            if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyer(buyerDTO.getOrgId(), buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyer())) {
	                throw new ApplicationException("Buyer already exists for this Customer");
	            }
	            buyerVO.setBuyer(buyerDTO.getBuyer());
	        }
	        
	        if (!buyerVO.getBuyerShortName().equalsIgnoreCase(buyerDTO.getBuyerShortName())) {
	            if (buyerRepo.existsByOrgIdAndCustomerAndClientAndBuyerShortName(buyerDTO.getOrgId(), buyerDTO.getCustomer(), buyerDTO.getClient(), buyerDTO.getBuyerShortName())) {
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
	        if (supplierRepo.existsByOrgIdAndCustomerAndClientAndSupplierAndSupplierType(supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplier(),supplierDTO.getSupplierType())) {
	            throw new ApplicationException("Supplier and Supplier Type already exists for this Customer");
	        }

	        if (supplierRepo.existsByOrgIdAndCustomerAndClientAndAndSupplierShortNameAndSupplierType(supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplierShortName(),supplierDTO.getSupplierType())) {
	            throw new ApplicationException("Supplier ShortName and Supplier Type and already exists for this Customer");
	        }

	        supplierVO.setCreatedBy(supplierDTO.getCreatedBy());
		    supplierVO.setUpdatedBy(supplierDTO.getCreatedBy());
	        // Set the values from supplierDTO to supplierVO
	        mapSupplierDtoToSupplierVo(supplierDTO, supplierVO);
	        message = "Supplier created successfully";

	    } else {

	        // Retrieve the existing SupplierVO from the repository
	        supplierVO = supplierRepo.findById(supplierDTO.getId()).orElseThrow(() -> new ApplicationException("Supplier not found"));

	        // Validate and update unique fields if changed
	        if (!supplierVO.getSupplier().equalsIgnoreCase(supplierDTO.getSupplier())) {
	            if (supplierRepo.existsByOrgIdAndCustomerAndClientAndSupplierAndSupplierType(supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplier(),supplierDTO.getSupplierType())) {
	                throw new ApplicationException("Supplier and Supplier Type already exists for this Customer");
	            }
	            supplierVO.setSupplier(supplierDTO.getSupplier());
	        }

	        if (!supplierVO.getSupplierShortName().equalsIgnoreCase(supplierDTO.getSupplierShortName())) {
	            if (supplierRepo.existsByOrgIdAndCustomerAndClientAndAndSupplierShortNameAndSupplierType(supplierDTO.getOrgId(), supplierDTO.getCustomer(), supplierDTO.getClient(), supplierDTO.getSupplierShortName(),supplierDTO.getSupplierType())) {
	                throw new ApplicationException("Supplier ShortName and Supplier Type already exists for this Customer");
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
		
		Set<Object[]> supplier=supplierRepo.findActiveSupplierNameByCustomer(orgid, client, cbranch);
		
		return getSupplierName(supplier);
	}

	// LocationMapping

	private List<Map<String, Object>> getSupplierName(Set<Object[]> supplier) {
		List<Map<String, Object>> supplierList= new ArrayList<>();
		for(Object[] sup:supplier)
		{
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
		return carrierRepo.findCarrierNameByCustomer(orgid, client, cbranch);
	}

	@Override
	public Map<String, Object> createUpdateCarrier(CarrierDTO carrierDTO) throws ApplicationException {

	    CarrierVO carrierVO = new CarrierVO();
	    String message;

	    // Check if the carrierDTO ID is empty (indicating a new entry)
	    if (ObjectUtils.isEmpty(carrierDTO.getId())) {

	        // Validate if the carrier already exists by unique fields
	    	if (carrierRepo.existsByOrgIdAndCarrier(
                    carrierDTO.getOrgId(),carrierDTO.getCarrier())) {
                throw new ApplicationException("Carrier already exist ");
            }
	    	if (carrierRepo.existsByOrgIdAndCarrierShortName(
                    carrierDTO.getOrgId(), carrierDTO.getCarrierShortName())) {
                throw new ApplicationException("Carrier Short Name already exist ");
            }

	        carrierVO.setCreatedBy(carrierDTO.getCreatedBy());
	        carrierVO.setUpdatedBy(carrierDTO.getCreatedBy());
	        // Set the values from carrierDTO to carrierVO
	        mapCarrierDtoToCarrierVo(carrierDTO, carrierVO);
	        message = "Carrier Created Successfully";

	    } else {

	        // Retrieve the existing CarrierVO from the repository
	        carrierVO = carrierRepo.findById(carrierDTO.getId()).orElseThrow(() -> new ApplicationException("Carrier not found"));

	        // Validate and update unique fields if changed
	        if (!carrierVO.getCarrier().equalsIgnoreCase(carrierDTO.getCarrier())) {
	            if (carrierRepo.existsByOrgIdAndCarrier(
	                    carrierDTO.getOrgId(),carrierDTO.getCarrier())) {
	                throw new ApplicationException("Carrier already exist ");
	            }
	            carrierVO.setCarrier(carrierDTO.getCarrier());
	        }

	        if (!carrierVO.getCarrierShortName().equalsIgnoreCase(carrierDTO.getCarrierShortName())) {
	            if (carrierRepo.existsByOrgIdAndCarrierShortName(
	                    carrierDTO.getOrgId(), carrierDTO.getCarrierShortName())) {
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
	public EmployeeVO createEmployee(EmployeeDTO employeeDTO) throws ApplicationException {
		EmployeeVO employeeVO=new EmployeeVO();
		if(ObjectUtils.isEmpty(employeeDTO.getId())) {
			if(employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(),employeeDTO.getOrgId())) {
				String errorMessage=String.format("This EmployeeCode :%s Already Exists  This Organization", employeeDTO.getEmployeeCode());
				throw new ApplicationException(errorMessage);
			}
		}
		if (employeeDTO.getId() != null) {

			employeeVO = employeeRepo.findById(employeeDTO.getId()).orElseThrow(
					() -> new ApplicationException("ID is Not Found AnyInformation ." + employeeDTO.getId()));

			employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());
			
			if (!employeeVO.getEmployeeCode().equalsIgnoreCase(employeeDTO.getEmployeeCode())) {
				if (employeeRepo.existsByEmployeeCodeAndOrgId(employeeDTO.getEmployeeCode(), employeeDTO.getOrgId())) {
					String errorMessage = String.format("This EmployeeCode :%s Already Exists  This Organization",employeeDTO.getEmployeeCode());
					throw new ApplicationException(errorMessage);
				}
				employeeVO.setEmployeeCode(employeeDTO.getEmployeeCode());
			}
			}else {
				employeeVO.setUpdatedBy(employeeDTO.getCreatedBy());
				employeeVO.setCreatedBy(employeeDTO.getCreatedBy());
				
			}
	
		getEmployeeVOFromEmployeeDTO(employeeVO,employeeDTO);
		return employeeRepo.save(employeeVO);
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
		DocumentTypeVO documentTypeVO=new DocumentTypeVO();
		String message;
		if(ObjectUtils.isEmpty(documentTypeDTO.getId()))
		{
			if (documentTypeRepo.existsByOrgIdAndScreenCode(
					documentTypeDTO.getOrgId(),documentTypeDTO.getScreenCode())) {
                throw new ApplicationException("ScreenCode already exist ");
            }
			
			if (documentTypeRepo.existsByOrgIdAndDocCode(
					documentTypeDTO.getOrgId(),documentTypeDTO.getDocCode())) {
                throw new ApplicationException("Doc Code already exist ");
            }
			
			documentTypeVO.setCreatedBy(documentTypeDTO.getCreatedBy());
			documentTypeVO.setUpdatedBy(documentTypeDTO.getCreatedBy());
			mapDocumentTypeDTOToDocumentTypeVO(documentTypeDTO,documentTypeVO);
			message="Document Type Created successfully";
		}
		else
		{
			documentTypeVO=documentTypeRepo.findById(documentTypeDTO.getId()).orElse(null);
			
			if (!documentTypeVO.getScreenCode().equalsIgnoreCase(documentTypeDTO.getScreenCode())) {
				if (documentTypeRepo.existsByOrgIdAndScreenCode(
						documentTypeDTO.getOrgId(),documentTypeDTO.getScreenCode())) {
	                throw new ApplicationException("ScreenCode already exist ");
	            }
				documentTypeVO.setScreenCode(documentTypeDTO.getScreenCode());
	        }

	        if (!documentTypeVO.getDocCode().equalsIgnoreCase(documentTypeDTO.getDocCode())) {
	        	if (documentTypeRepo.existsByOrgIdAndDocCode(
						documentTypeDTO.getOrgId(),documentTypeDTO.getDocCode())) {
	                throw new ApplicationException("Doc Code already exist ");
	            }
	            documentTypeVO.setDocCode(documentTypeDTO.getDocCode());
	        }

	        documentTypeVO.setUpdatedBy(documentTypeDTO.getCreatedBy());
	        // Update the remaining fields from carrierDTO to carrierVO
	        mapDocumentTypeDTOToDocumentTypeVO(documentTypeDTO, documentTypeVO);
	        message = "Document Type Updated successfully";
			
		}
		return null;
	}

	private void mapDocumentTypeDTOToDocumentTypeVO(DocumentTypeDTO documentTypeDTO, DocumentTypeVO documentTypeVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DocumentTypeVO getDocumentTypeById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DocumentTypeVO> getAllDocumentTypeByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
