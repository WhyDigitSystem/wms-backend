package com.whydigit.wms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DesignationVO;

@Repository
public interface DesignationRepo extends JpaRepository<DesignationVO, Long>{

	boolean existsByDesignationAndOrgId(String desination, Long orgId);

	@Query(value = "select * from designation where orgid=?1",nativeQuery =true)
	List<DesignationVO> getAllDesignationByOrgId(Long ordId);

	@Query(value = "select * from designation where designationid=?1",nativeQuery =true)
	Optional<DesignationVO> getAllDesignationById(Long id);

}
