package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DeliveryChallanVO;

@Repository
public interface DeliveryChallanRepo extends JpaRepository<DeliveryChallanVO, Long> {

}
