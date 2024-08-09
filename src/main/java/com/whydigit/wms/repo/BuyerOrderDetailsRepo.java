package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.BuyerOrderDetailsVO;
import com.whydigit.wms.entity.BuyerOrderVO;
@Repository
public interface BuyerOrderDetailsRepo extends JpaRepository<BuyerOrderDetailsVO, Long>{

	List<BuyerOrderDetailsVO> findByBuyerOrderVO(BuyerOrderVO buyerOrderVO);

}
