package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.LocationTypeVO;

@Repository
public interface LocationTypeRepo extends JpaRepository<LocationTypeVO, Long> {

	@Query("select a from LocationTypeVO a where a.orgid=?1")
	List<LocationTypeVO> findAll(Long orgid);

}
