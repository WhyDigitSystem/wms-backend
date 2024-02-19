package com.whydigit.wms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whydigit.wms.entity.WarehouseLocationDetailsVO;
import com.whydigit.wms.entity.WarehouseLocationVO;
import com.whydigit.wms.repo.WarehouseLocationDetailsRepo;
import com.whydigit.wms.repo.WarehouseLocationRepo;

@Service
public class WarehouseMasterServiceImpl implements WarehouseMasterService {

	@Autowired
	WarehouseLocationRepo warehouseLocationRepo ;
	
	
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

}
