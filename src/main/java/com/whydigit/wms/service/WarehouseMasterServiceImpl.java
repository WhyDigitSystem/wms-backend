package com.whydigit.wms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.dto.BranchDTO;
import com.whydigit.wms.dto.ClientBranchDTO;
import com.whydigit.wms.dto.ClientDTO;
import com.whydigit.wms.dto.CustomerDTO;
import com.whydigit.wms.dto.LocationTypeDTO;
import com.whydigit.wms.dto.MaterialDTO;
import com.whydigit.wms.dto.WarehouseClientDTO;
import com.whydigit.wms.dto.WarehouseDTO;
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
		
		MaterialVO materialVO= new MaterialVO();
		if(ObjectUtils.isEmpty(materialDTO.getId()))
		{
			if(materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(),materialDTO.getCustomer() , materialDTO.getClient(),materialDTO.getPartno()))
			{
				throw new ApplicationException("Partno Already exist for this Customer");
			}
			if(materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(),materialDTO.getCustomer() , materialDTO.getClient(),materialDTO.getPartDesc()))
			{
				throw new ApplicationException("PartDesc Already exist for this Customer");
			}
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
	        materialVO.setCreatedBy(materialDTO.getCreatedBy());
	        materialVO.setUpdatedBy(materialDTO.getCreatedBy());
	        materialVO.setLength(materialDTO.getLength());
	        materialVO.setBreadth(materialDTO.getBreadth());
	        materialVO.setHeight(materialDTO.getHeight());
	        materialVO.setWeight(materialDTO.getWeight());
		}
		else
		{
			materialVO=materialRepo.findById(materialDTO.getId()).get();
			
			if(!materialVO.getPartno().equalsIgnoreCase(materialDTO.getPartno()))
			{
				if(materialRepo.existsByOrgIdAndCustomerAndClientAndPartno(materialDTO.getOrgId(),materialDTO.getCustomer() , materialDTO.getClient(),materialDTO.getPartno()))
				{
					throw new ApplicationException("Partno Already exist for this Customer");
				}
				materialVO.setPartno(materialDTO.getPartno());
			}
			if(!materialVO.getPartDesc().equalsIgnoreCase(materialDTO.getPartDesc()))
			{
				if(materialRepo.existsByOrgIdAndCustomerAndClientAndPartDesc(materialDTO.getOrgId(),materialDTO.getCustomer() , materialDTO.getClient(),materialDTO.getPartDesc()))
				{
					throw new ApplicationException("PartDesc Already exist for this Customer");
				}
				materialVO.setPartDesc(materialDTO.getPartDesc());
			}
			materialVO.setItemType(materialDTO.getItemType());
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
	        materialVO.setCreatedBy(materialDTO.getCreatedBy());
	        materialVO.setUpdatedBy(materialDTO.getCreatedBy());
	        materialVO.setLength(materialDTO.getLength());
	        materialVO.setBreadth(materialDTO.getBreadth());
	        materialVO.setHeight(materialDTO.getHeight());
	        materialVO.setWeight(materialDTO.getWeight());
		}
		
		return materialRepo.save(materialVO);
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
		return supplierRepo.findSupplierNameByCustomer(orgid, client, cbranch);
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
		return carrierRepo.findCarrierNameByCustomer(orgid, client, cbranch);
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
