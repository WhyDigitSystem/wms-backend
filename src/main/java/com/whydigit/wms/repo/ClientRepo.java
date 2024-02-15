package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.ClientVO;

public interface ClientRepo extends JpaRepository<ClientVO, Long>{

}
