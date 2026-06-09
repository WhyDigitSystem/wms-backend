package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.PickRequestDetailsVO;
import com.whydigit.wms.entity.PickRequestVO;

@Repository
public interface PickRequestDetailsRepo extends JpaRepository<PickRequestDetailsVO, Long>{

	List<PickRequestDetailsVO> findByPickRequestVO(PickRequestVO pickRequestVO);

}
