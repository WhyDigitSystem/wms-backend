package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CodeConversionDetailsVO;
import com.whydigit.wms.entity.CodeConversionVO;

@Repository
public interface CodeConversionDetailsRepo extends JpaRepository<CodeConversionDetailsVO, Long>{

	List<CodeConversionDetailsVO> findByCodeConversionVO(CodeConversionVO codeConversionVO);

}
