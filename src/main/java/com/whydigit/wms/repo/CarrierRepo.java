package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CarrierVO;

public interface CarrierRepo extends JpaRepository<CarrierVO, Long>{

	@Query("select a from CarrierVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL' or a.cbranch=?3)")
	List<CarrierVO> findAll(Long orgid, String client, String cbranch);

}
