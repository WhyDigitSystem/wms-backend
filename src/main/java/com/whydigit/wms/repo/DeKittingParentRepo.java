package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.DeKittingParentVO;
import com.whydigit.wms.entity.DeKittingVO;

public interface DeKittingParentRepo extends JpaRepository<DeKittingParentVO, Long>{

	List<DeKittingParentVO> findByDeKittingVO(DeKittingVO deKittingVO);

}
