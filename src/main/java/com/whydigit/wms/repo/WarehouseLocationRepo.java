package com.whydigit.wms.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whydigit.wms.entity.WarehouseLocationVO;

public interface WarehouseLocationRepo extends JpaRepository<WarehouseLocationVO, Long> {

	
	@Query("select a from WarehouseLocationVO a where orgId=?1 and a.warehouse=?2 and a.branch=?3")
	List<WarehouseLocationVO> findAll(Long orgid, String warehouse, String branch);

	@Query("select e.locationtype from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2")
	Set<Object[]> findAllLocationTypeByOrgIdAndWarehouse(Long orgid, String warehouse);

	@Query("select e.rowno from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2 and e.locationtype=?3")
	Set<Object[]> findAllRownoByOrgIdAndWarehouseAndLocationType(Long orgid, String warehouse, String locationtype);
	
	@Query("select e.level from WarehouseLocationVO e where e.orgId=?1 and e.warehouse=?2 and e.locationtype=?3 and e.rowno=?4")
	Set<Object[]> findAllLevelByOrgIdAndWarehouseAndLocationTypeAndRowno(Long orgid, String warehouse,String locationtype, String rowno);

	@Query(nativeQuery = true,value = "WITH RECURSIVE NumberSequence AS (\r\n"
			+ "  SELECT 1 AS level\r\n"
			+ "  UNION ALL\r\n"
			+ "  SELECT level + 1\r\n"
			+ "  FROM NumberSequence\r\n"
			+ "  WHERE level < ?4\r\n"
			+ ")\r\n"
			+ "SELECT \r\n"
			+ "  ROW_NUMBER() OVER () AS id,\r\n"
			+ "  CONCAT(\r\n"
			+ "    ?1, \r\n"
			+ "    '-',\r\n"
			+ "    ?2, \r\n"
			+ "    CASE \r\n"
			+ "      WHEN level < 10 THEN CONCAT('0', level)\r\n"
			+ "      ELSE CAST(level AS CHAR)\r\n"
			+ "    END\r\n"
			+ "  ) AS pallet,\r\n"
			+ "  'ACTIVE' AS cellcategory,\r\n"
			+ "  'T' AS status,\r\n"
			+ "  'Multi' AS core\r\n"
			+ "FROM NumberSequence\r\n"
			+ "WHERE level BETWEEN ?3 AND ?4")
	Set<Object[]> getPalletnoByRownoAndLevelno(String rowno, String level, int startno, int endno);

	boolean existByOrgIdAndWarehouseAndBinTypeAndRowNoAndLevel(String string, String string2, String level);
}
