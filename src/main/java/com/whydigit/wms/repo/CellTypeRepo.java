package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CellTypeVO;

@Repository
public interface CellTypeRepo extends JpaRepository<CellTypeVO, Long>{


	@Query("select a from CellTypeVO a where a.orgId=?1")
	List<CellTypeVO> findAll(Long orgid);

}
