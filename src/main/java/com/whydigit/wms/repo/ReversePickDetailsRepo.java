package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.ReversePickDetailsVO;
import com.whydigit.wms.entity.ReversePickVO;

@Repository
public interface ReversePickDetailsRepo extends JpaRepository<ReversePickDetailsVO, Long>{

	List<ReversePickDetailsVO> findByReversePickVO(ReversePickVO reversePickVO);


}
