package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CarrierVO;

public interface CarrierRepo extends JpaRepository<CarrierVO, Long>{

	@Query("select a from CarrierVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL' or a.cbranch=?3)")
	List<CarrierVO> findAll(Long orgid, String client, String cbranch);

	@Query(nativeQuery = true,value="select * from carrier c where c.orgid=?1 and c.client=?2 and (c.cbranch='ALL' or c.cbranch =?3) and c.active=1 and c.shipmentmode=?4")
	List<CarrierVO> findCarrierNameByCustomer(Long orgid, String client, String cbranch, String shipmentMode);

	boolean existsByOrgIdAndCarrierShortName(Long orgId, String carrierShortName);

	boolean existsByOrgIdAndCarrier(Long orgId, String carrier);

	
	@Query(value = "select shipmentMode from CarrierVO where orgId=?1 group by shipmentMode")
	Set<Object[]>findmodeOfShipment(Long orgId);

	

}

