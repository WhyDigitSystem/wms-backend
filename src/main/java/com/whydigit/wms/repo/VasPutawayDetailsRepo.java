package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.VasPutawayDetailsVO;
import com.whydigit.wms.entity.VasPutawayVO;

@Repository
public interface VasPutawayDetailsRepo extends JpaRepository<VasPutawayDetailsVO, Long> {

	List<VasPutawayDetailsVO> findByVasPutawayVO(VasPutawayVO vasPutawayVO);


}
