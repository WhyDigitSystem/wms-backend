package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.LocationMappingDetailsVO;
import com.whydigit.wms.entity.LocationMappingVO;

public interface LocationMappingDetailsRepo extends JpaRepository<LocationMappingDetailsVO, Long> {

	List<LocationMappingDetailsVO> findByLocationMappingVO(LocationMappingVO locationMappingVO);

	boolean existsByBinAndWarehouse(String bin, String warehouse);

}
