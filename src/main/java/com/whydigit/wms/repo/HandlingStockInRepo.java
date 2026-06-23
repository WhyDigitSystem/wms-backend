package com.whydigit.wms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.whydigit.wms.entity.HandlingStockInVO;

public interface HandlingStockInRepo extends JpaRepository<HandlingStockInVO, Long> {

	List<HandlingStockInVO> findBySdocid(String docId);


	@Transactional
	@Modifying
	@Query(value =
	    "DELETE FROM handlingstockin WHERE grnno = :grnNo",
	    nativeQuery = true)
	int deleteGrnNo(@Param("grnNo") String grnNo);

}
