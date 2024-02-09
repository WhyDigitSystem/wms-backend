package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BranchVO;


@Repository
public interface BranchRepo  extends JpaRepository<BranchVO, Long> {

}


