package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationVO;

public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Long> {

	
//	@Query(nativeQuery = true,value="select * from WarehouseLocationVO where company=?1 ")
	List<WarehouseLocationVO> findAllByCompany(String company);

}
