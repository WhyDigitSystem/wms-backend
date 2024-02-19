package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.WarehouseVO;

@Repository
public interface WarehouseRepo  extends JpaRepository<WarehouseVO, Long> {

	@Query(nativeQuery = true,value="select a.* from warehouse a where a.company=?1")
	List<WarehouseVO> findWarehouseByCompany(String company);

}

