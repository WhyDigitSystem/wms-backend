package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.DocumentTypeMappingVO;

public interface DocumentTypeMappingRepo extends JpaRepository<DocumentTypeMappingVO, Long> {

//	
//	@Query(nativeQuery = true,value="SELECT \r\n"
//			+ "    b.screenname, \r\n"
//			+ "    b.screencode, \r\n"
//			+ "    b.client, \r\n"
//			+ "    b.clientcode, \r\n"
//			+ "    b.doccode,\r\n"
//			+ "    ?4 finyear,\r\n"
//			+ "    ?2 AS branch,\r\n"
//			+ "    ?3 AS branchcode,\r\n"
//			+ "    ?5 AS finyearidentifier,\r\n"
//			+ "    CONCAT(?3, ?5, b.clientcode, b.doccode) AS prefixfield\r\n"
//			+ "FROM (\r\n"
//			+ "    SELECT clientcode, client, doccode, screencode, screenname \r\n"
//			+ "    FROM m_documenttypedetails  \r\n"
//			+ "    WHERE CONCAT(client, clientcode, screencode, doccode) NOT IN (\r\n"
//			+ "        SELECT CONCAT(client, clientcode, screencode, doccode) \r\n"
//			+ "        FROM m_documenttypemappingdetails \r\n"
//			+ "        WHERE finyear = ?4 and orgid=?1\r\n"
//			+ "          AND branch = ?2\r\n"
//			+ "    ) and orgid=?1\r\n"
//			+ ") b")
//	Set<Object[]> getPendingDoctypeMapping(Long orgId, String branch, String branchCode, String finYear, String finYearIdentifier);
//
//	List<DocumentTypeMappingVO> findByOrgId(Long orgId);
	
	@Query(nativeQuery = true,value = "SELECT b.screenname, \r\n"
			+ "			    b.screencode, \r\n"
			+ "			    b.doccode,\r\n"
			+ "			    ?4 finyear,\r\n"
			+ "			    ?2 AS branch,\r\n"
			+ "			    ?3 AS branchcode,\r\n"
			+ "			    ?5 AS finyearidentifier,\r\n"
			+ "			    CONCAT(?3, ?5,?6,b.doccode) AS prefixfield,\r\n"
			+ "				?6 as clientcode\r\n"
			+ "			FROM (\r\n"
			+ "			    SELECT doccode, screencode, screenname \r\n"
			+ "			    FROM m_documenttype  \r\n"
			+ "			    WHERE CONCAT(screencode, doccode) NOT IN (\r\n"
			+ "			        SELECT CONCAT(screencode, doccode) \r\n"
			+ "			        FROM m_documenttypemappingdetails \r\n"
			+ "			        WHERE finyear = ?4 and orgid = ?1\r\n"
			+ "			          AND branch = ?2 and clientcode=?6\r\n"
			+ "			    ) and orgid = ?1\r\n"
			+ "			) b")
	Set<Object[]> getPendingDoctypeMapping(Long orgId, String branch, String branchCode, String finYear,
			String finYearIdentifier,String clientCode);

	@Query(value = "select a from DocumentTypeMappingVO a where a.orgId=?1")
	List<DocumentTypeMappingVO> findByOrgId(Long orgId);

}
