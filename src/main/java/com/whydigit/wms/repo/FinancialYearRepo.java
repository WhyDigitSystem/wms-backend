package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.FinancialYearVO;

public interface FinancialYearRepo extends JpaRepository<FinancialYearVO, Long> {

	@Query(nativeQuery = true,value = "select * from finyear where company=?1")
	List<FinancialYearVO> findFinyearByCompany(String company);

	@Query(value ="select * from financialyear where orgid=?1",nativeQuery =true)
	List<FinancialYearVO> findFinancialYearByOrgId(Long orgId);

	@Query(value ="select a from FinancialYearVO a where a.orgId=?1 and a.active=true and a.closed=false",nativeQuery =true)
	List<FinancialYearVO> findAllActiveFinYear(Long orgId);

}
