package com.whydigit.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.KittingVO;

@Repository
public interface KittingRepo extends JpaRepository<KittingVO, Long> {
	@Query(nativeQuery = true, value = "select * from kitting k where k.orgid=?1 and k.branchcode=?2 and k.client=?3 and k.customer=?4")
	List<KittingVO> findAllKiting(Long orgId, String branchCode, String client, String customer);

	@Query(nativeQuery = true, value = "select * from kitting where kittingid=?1")
	Optional<KittingVO> findKittingById(Long id);

	@Query(nativeQuery = true,value ="select concat(prefixfield,lpad(lastno,6,0)) AS docid from m_documenttypemappingdetails where orgid=?1 and finyear=?2 and branchcode=?3 and client=?4 and screencode=?5")
	String getKittingInDocId(Long orgId, String finYear, String branchCode, String client, String screenCode);

}
