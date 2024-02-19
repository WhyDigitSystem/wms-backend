package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.whydigit.wms.entity.WarehouseLocationVO;

@Service
public interface WarehouseMasterService {
	
	List<WarehouseLocationVO> getAllWarehouseLocation(); // Method names should be in camelCase
	
	List<WarehouseLocationVO> getAllWarehouseLocationByCompany(String company);
	
	Optional<WarehouseLocationVO> getWarehouseLocationById(Long warehouselocationid);

	WarehouseLocationVO createWarehouseLocation(WarehouseLocationVO warehouseLocationVO); // Return the created entity
	
	Optional<WarehouseLocationVO> updateWarehouseLocation(WarehouseLocationVO warehouseLocationVO);

	void deleteWarehouseLocation(Long warehouselocationid);

	
	

}
