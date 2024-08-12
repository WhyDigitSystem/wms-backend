package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.DeKittingChildVO;
import com.whydigit.wms.entity.DeKittingVO;

public interface DeKittingChildRepo extends JpaRepository<DeKittingChildVO, Long>{

	List<DeKittingChildVO> findByDeKittingVO(DeKittingVO deKittingVO);

}
