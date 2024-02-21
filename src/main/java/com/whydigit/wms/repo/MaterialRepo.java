package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.MaterialVO;

public interface MaterialRepo extends JpaRepository<MaterialVO, Long>{

	
	List<MaterialVO> findAllByCompanyAndClient(String company, String client);

}
