package com.whydigit.wms.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.whydigit.wms.entity.NotificationVO;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationVO, Long> {

	@Query("Select n from NotificationVO n   WHERE " + "n.orgId = ?1 " + "AND n.partno = ?2 " + "AND n.partDesc = ?3 "
			+ "AND n.client = ?4 " + "AND n.branchCode = ?5 " + "AND n.warehouse = ?6 " + "AND n.notificationType = ?7")
	NotificationVO findByCheck(Long orgId, String partno, String partDesc, String client, String branchCode,
			String warehouse, String type);

	@Query(nativeQuery =true,value ="select m.client,m.partno,m.partdesc,m.sku,m.criticalqty,sum(s.sqty) as qty,'Low Stock' as types,m.notificationid,''as grnno,null AS grndate,\r\n"
			+ "'' as batch,null AS batchdate,null AS expdate,'' AS bin,''status,null last_sale_date,null days_since_sale,null days_until_expiration from notification m inner join stockdetails s on m.partno = s.partno and m.client = s.client and m.orgid = s.orgid where m.orgid =?1 \r\n"
			+ "and m.branchcode =?2\r\n"
			+ "and m.client =?3 and m.warehouse =?4 and m.isread=0 and m.notificationtype='LowAndMaximum' group by m.client,m.partno,m.partdesc,m.sku,m.criticalqty,m.notificationid\r\n"
			+ " having SUM(s.sqty) <= m.criticalqty\r\n"
			+ " union all\r\n"
			+ "select m.client,m.partno,m.partdesc,m.sku,m.maximumqty,sum(s.sqty) as qty,'Maximum Stock' as types,m.notificationid,''as grnno,null AS grndate,\r\n"
			+ "'' as batch,null AS batchdate,null AS expdate,'' AS bin,''status,null last_sale_date,null days_since_sale,null days_until_expiration from notification m inner join stockdetails s on m.partno = s.partno and m.client = s.client and m.orgid = s.orgid where m.orgid =?1 and m.branchcode =?2\r\n"
			+ "and m.client =?3 and m.warehouse =?4 and m.isread=0 and m.notificationtype='LowAndMaximum' group by m.client,m.partno,m.partdesc,m.sku,m.maximumqty,m.notificationid having SUM(s.sqty) > m.maximumqty\r\n"
			+ " union all\r\n"
			+ " select '' as client,partno,partdesc,sku,0 qtyLimit ,SUM(sqty) AS sqty,'Hold Stock' as types,notificationid,grnno,grndate,batch,batchdate,expdate,bin,''status,null last_sale_date,null days_since_sale,null days_until_expiration from notification where \r\n"
			+ "orgid =?1 and warehouse =?4 and branchcode =?2 and client =?3 and status in ( 'H','D') and isread=0 and notificationtype='HoldAndDamage'\r\n"
			+ "group by partno, partdesc, sku, grnno, grndate, batch, batchdate, expdate, bin,notificationid having SUM(sqty) > 0\r\n"
			+ "union all\r\n"
			+ "SELECT'' , s.partno,MAX(s.partdesc) AS partdesc,'',0 qtyLimit,SUM(s.sqty) AS total_qty,'Slow Move' as types ,pr.notificationid,''as grnno,null AS grndate,\r\n"
			+ "'' as batch,null AS batchdate, MAX(s.expdate) AS expdate,'' AS bin,MAX(s.status) AS status,pr.last_sale_date,\r\n"
			+ "    DATEDIFF(CURDATE(), pr.last_sale_date) AS days_since_sale,null days_until_expiration FROM stockdetails s JOIN (SELECT n1.partno,n1.notificationid,\r\n"
			+ "n1.stockdate AS last_sale_date FROM notification n1 INNER JOIN (SELECT\r\n"
			+ "partno,MAX(stockdate) AS last_sale_date FROM notification WHERE sourcescreencode = 'PR' and isread=0 and notificationtype='Slow Move'\r\n"
			+ "GROUP BY partno) n2 ON n1.partno = n2.partno AND n1.stockdate = n2.last_sale_date WHERE n1.sourcescreencode = 'PR'\r\n"
			+ ") pr ON pr.partno = s.partno WHERE s.client = ?3 AND s.warehouse = ?4 AND s.orgid = ?1 AND s.branchcode = ?2\r\n"
			+ "  AND s.status = 'R' GROUP BY s.partno,pr.last_sale_date,pr.notificationid HAVING SUM(s.sqty) > 0 AND (pr.last_sale_date IS NULL OR DATEDIFF(CURDATE(), pr.last_sale_date) > 20)\r\n"
			+ "union all\r\n"
			+ "SELECT'' , s.partno,MAX(s.partdesc) AS partdesc,'',0 qtyLimit,SUM(s.sqty) AS total_qty,'Dead Move' as types ,pr.notificationid,''as grnno,null AS grndate,\r\n"
			+ "'' as batch,null AS batchdate, MAX(s.expdate) AS expdate,'' AS bin,MAX(s.status) AS status,pr.last_sale_date,\r\n"
			+ "    DATEDIFF(CURDATE(), pr.last_sale_date) AS days_since_sale,null days_until_expiration FROM stockdetails s JOIN (SELECT n1.partno,n1.notificationid,\r\n"
			+ "n1.stockdate AS last_sale_date FROM notification n1 INNER JOIN (SELECT\r\n"
			+ "partno,MAX(stockdate) AS last_sale_date FROM notification WHERE sourcescreencode = 'PR' and isread=0 and notificationtype='Slow Move'\r\n"
			+ "GROUP BY partno) n2 ON n1.partno = n2.partno AND n1.stockdate = n2.last_sale_date WHERE n1.sourcescreencode = 'PR'\r\n"
			+ ") pr ON pr.partno = s.partno WHERE s.client = ?3 AND s.warehouse = ?4 AND s.orgid = ?1 AND s.branchcode = ?2\r\n"
			+ "  AND s.status = 'R' GROUP BY s.partno,pr.last_sale_date,pr.notificationid HAVING SUM(s.sqty) > 0 AND (pr.last_sale_date IS NULL OR DATEDIFF(CURDATE(), pr.last_sale_date) > 60)\r\n"
			+ "union all\r\n"
			+ "SELECT '' ,partno,partdesc,sku,0 qtyLimit,SUM(sqty) AS total_quantity,'Near Expiry' as types,notificationid,grnno,grndate, batch,batchdate,expdate,bin,''status,null last_sale_date,null days_since_sale,DATEDIFF(expdate, CURDATE()) AS days_until_expiration\r\n"
			+ " FROM notification WHERE orgid =?1 AND branchcode =?2 and isread=0 and notificationtype='Near Expiry' AND client =?3 AND warehouse =?4 AND DATEDIFF(expdate, CURDATE()) < 20 GROUP BY \r\n"
			+ "partno,partdesc,sku,batch,batchdate,grnno,grndate,expdate,bin,notificationid having  SUM(sqty)>0")
	Set<Object[]> getNotificationDetails(Long orgId, String branchCode, String client, String warehouse);

	@Query(value = "select * from notification where notificationid=?2 and orgid=?1", nativeQuery = true)
	NotificationVO findByNotificationIdAndOrgId(Long orgId, Long notificationId);

}
