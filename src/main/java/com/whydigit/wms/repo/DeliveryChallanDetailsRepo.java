package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DeliveryChallanDetailsVO;

@Repository
public interface DeliveryChallanDetailsRepo extends JpaRepository<DeliveryChallanDetailsVO, Long>{

}
