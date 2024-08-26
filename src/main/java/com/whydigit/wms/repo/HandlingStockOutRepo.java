package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.HandlingStockOutVO;

public interface HandlingStockOutRepo extends JpaRepository<HandlingStockOutVO, Long> {

	@Query(nativeQuery = true,value = "select a.* from handlingstockout a where a.sdocid=?1 ")
	List<HandlingStockOutVO> findBySDocid(String docId);

	

}
