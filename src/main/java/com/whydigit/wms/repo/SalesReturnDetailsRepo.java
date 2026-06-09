package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whydigit.wms.entity.SalesReturnDetailsVO;
import com.whydigit.wms.entity.SalesReturnVO;

public interface SalesReturnDetailsRepo extends JpaRepository<SalesReturnDetailsVO, Long> {

	List<SalesReturnDetailsVO> findBySalesReturnVO(SalesReturnVO salesReturnVO);

}
