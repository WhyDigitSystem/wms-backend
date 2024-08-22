package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.KittingDetails1VO;
import com.whydigit.wms.entity.KittingVO;

public interface KittingDetails1Repo extends JpaRepository<KittingDetails1VO, Long>{

	List<KittingDetails1VO> findByKittingVO(KittingVO kittingVO);

}
