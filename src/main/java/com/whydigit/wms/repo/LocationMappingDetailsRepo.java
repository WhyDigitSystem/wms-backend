package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.LocationMappingDetailsVO;
import com.whydigit.wms.entity.LocationMappingVO;

public interface LocationMappingDetailsRepo extends JpaRepository<LocationMappingDetailsVO, Long> {

	List<LocationMappingDetailsVO> findByLocationMappingVO(LocationMappingVO locationMappingVO);

	boolean existsByBinAndWarehouse(String bin, String warehouse);

	@Query(nativeQuery = true,value="select bin,bintype,binclass,core,celltype from wv_locationstatus \r\n"
			+ "where orgid=?1 and branchcode=?2 and warehouse=?3 and client=?4 and bin=?5 \r\n"
			+ "group by bin,bintype,binclass,core,celltype")
	Set<Object[]> getBinDetails(Long orgId, String branchCode, String warehouse, String client, String stringCellValue);

}
