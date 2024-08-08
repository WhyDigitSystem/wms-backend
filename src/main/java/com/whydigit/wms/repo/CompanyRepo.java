package com.whydigit.wms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.CompanyVO;


@Repository
public interface CompanyRepo extends JpaRepository<CompanyVO, Long> {

	boolean existsByCompanyCodeAndId(String companyCode, Long id);

	boolean existsByCompanyNameAndId(String companyName, Long id);

	boolean existsByEmployeeCodeAndId(String employeeCode, Long id);

	boolean existsByEmailAndId(String email, Long id);

	boolean existsByPhoneAndId(String phone, Long id);

	boolean existsByCompanyCodeAndCompanyNameAndEmployeeCodeAndEmailAndPhoneAndId(String companyCode,
			String companyName, String employeeCode, String email, String phone, Long id);

	@Query(nativeQuery =true ,value="select * from company  where companyid=?1")
	List<CompanyVO> findByCompany(Long companyid);

}
