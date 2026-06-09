package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.DeliveryChallanDetailsVO;
import com.whydigit.wms.entity.DeliveryChallanVO;

@Repository
public interface DeliveryChallanDetailsRepo extends JpaRepository<DeliveryChallanDetailsVO, Long>{

	List<DeliveryChallanDetailsVO> findByDeliveryChallanVO(DeliveryChallanVO deliveryChallanVO);

}
