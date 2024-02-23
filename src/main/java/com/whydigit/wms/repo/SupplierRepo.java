package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.SupplierVO;

public interface SupplierRepo extends JpaRepository<SupplierVO, Long> {

	List<SupplierVO> findAllByCompanyAndClient(String company, String client);

}
