package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.PutAwayDetailsVO;
import com.whydigit.wms.entity.PutAwayVO;

public interface PutAwayDetailsRepo extends JpaRepository<PutAwayDetailsVO, Long> {

	List<PutAwayDetailsVO> findByPutAwayVO(PutAwayVO putAwayVO);

}
