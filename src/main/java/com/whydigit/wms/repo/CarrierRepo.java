package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.CarrierVO;

public interface CarrierRepo extends JpaRepository<CarrierVO, Long>{

	@Query("select a from CarrierVO a where a.orgId=?1 and a.client=?2 and (a.cbranch='ALL' or a.cbranch=?3)")
	List<CarrierVO> findAll(Long orgid, String client, String cbranch);

	@Query(nativeQuery = true,value="select c.carrier from carrier c where c.orgid=?1 and c.client=?2 and c.cbranch =?3 and c.active=1")
	Set<Object[]> findCarrierNameByCustomer(Long orgid, String client, String cbranch);

	boolean existsByOrgIdAndCarrierShortName(Long orgId, String carrierShortName);

	boolean existsByOrgIdAndCarrier(Long orgId, String carrier);

	
	@Query(value = "SELECT shipmentmode " +
            "FROM carrier " +
            "WHERE active = true " +
            "GROUP BY shipmentmode", 
    nativeQuery = true)
	List<CarrierVO> findmodeOfShipment();

	@Query(nativeQuery = true,value ="select shipmentmode from carrier where active=true")
	List<CarrierVO> getActiveShipment(String shipmentMode);
}

