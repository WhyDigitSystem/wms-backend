package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.TokenVO;

@Repository
public interface TokenRepo extends JpaRepository<TokenVO, String>{

	
}
