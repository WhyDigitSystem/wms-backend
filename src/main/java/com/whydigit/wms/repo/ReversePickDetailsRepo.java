package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.ReversePickDetailsVO;
import com.whydigit.wms.entity.ReversePickVO;

@Repository
public interface ReversePickDetailsRepo extends JpaRepository<ReversePickDetailsVO, Long> {

	List<ReversePickDetailsVO> findByReversePickVO(ReversePickVO reversePickVO);

	@Query(value = "SELECT COALESCE(SUM(revisedqty),0) FROM reversepickdetails WHERE partno=?1 AND batchno=?2", nativeQuery = true)
	Integer getAlreadyReversedQty(String partNo, String batchNo);

}
