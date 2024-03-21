package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.StateVO;


@Repository
public interface StateRepo extends JpaRepository<StateVO, Long> {

	@Query("select a from StateVO a where a.orgid=?1 and a.country=?2")
	List<StateVO> findByCountry(Long orgid,String country);

	@Query("select a from StateVO a where a.orgid=?1")
	List<StateVO> findAllByOrgId(Long orgid);
	
}
