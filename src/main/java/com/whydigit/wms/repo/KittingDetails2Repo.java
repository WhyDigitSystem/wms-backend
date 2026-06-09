package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.KittingDetails2VO;
import com.whydigit.wms.entity.KittingVO;
@Repository
public interface KittingDetails2Repo extends JpaRepository<KittingDetails2VO, Long>{

	List<KittingDetails2VO> findByKittingVO(KittingVO kittingVO);

}
