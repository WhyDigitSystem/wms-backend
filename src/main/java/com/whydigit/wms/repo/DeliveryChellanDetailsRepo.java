package com.whydigit.wms.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DeliveryChellanDetailsVO;

@Repository
public interface DeliveryChellanDetailsRepo extends JpaRepository<DeliveryChellanDetailsVO, Long>{

}
