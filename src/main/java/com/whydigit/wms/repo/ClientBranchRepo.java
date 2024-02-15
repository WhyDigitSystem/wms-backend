package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.ClientBranchVO;

public interface ClientBranchRepo extends JpaRepository<ClientBranchVO, Long> {

}
