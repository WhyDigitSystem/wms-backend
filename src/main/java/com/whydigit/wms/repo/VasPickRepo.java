package com.whydigit.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.VasPickVO;

public interface VasPickRepo extends JpaRepository<VasPickVO, Long> {
	@Query(value = "select * from vaspick where vaspicid=?1", nativeQuery = true)
	Optional<VasPickVO> findVasPickById(Long id);

	@Query(nativeQuery =true,value = "select * from vaspick v where v.orgid=?1 and v.branchcode=?2 and v.client=?3 and v.customer=?4")
	List<VasPickVO> findAllVasPick(Long orgId, String branchCode, String client, String customer);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getVasPickDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

}
