package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.GrnDetailsVO;
import com.whydigit.wms.entity.GrnVO;

public interface GrnDetailsRepo extends JpaRepository<GrnDetailsVO, Long> {

	List<GrnDetailsVO> findByGrnVO(GrnVO grnVO);



	@Query(nativeQuery = true,value="Select bintype from wv_locationstatus where bintype=?1 and orgid=?2 and branchcode=?3 and warehouse=?4 and client =?5 group By bintype ")
	String getBinType(String binType, Long orgId, String branchCode, String warehouse, String client);

	@Query(nativeQuery = true,value="Select bin from wv_locationstatus where bin=?1 and bintype=?2 and orgid=?3 and branchcode=?4 and warehouse=?5 and client =?6 group By bin ")
	String getBinNo(String binNo1, String binType1, Long orgId, String branchCode, String warehouse, String client);

}
