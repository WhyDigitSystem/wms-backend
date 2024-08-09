package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DepartmentVO;
@Repository
public interface DepartmentVRepo extends JpaRepository<DepartmentVO, Long>{

}
