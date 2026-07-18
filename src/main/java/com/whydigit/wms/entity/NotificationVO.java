package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationgen")
	@SequenceGenerator(name = "notificationgen", sequenceName = "notificationseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "notificationid")
	private Long id;

	@Column(name = "ticketid")
	private Long ticketId;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "modifiedy")
	private String updatedBy;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "orgid")
	private Long orgId;

	private String status;

	@Column(name = "notifiedto")
	private String notifiedTo;

	@Column(name = "statusflag")
	private Boolean statusFlag = true;

	@Column(name = "message")
	private String message;
	@Column(name = "isread")
	private boolean isRead = false;
	@Column(name = "isdeleted")
	private boolean isDeleted = false;
	@Column(name = "notificationtype")
	private String notificationType;

	@Column(name = "client")
	private String client;

	@Column(name = "maximumqty")
	private int maximumQty;

	@Column(name = "criticalqty")
	private int criticalQty;

	@Column(name = "sku")
	private String sku;

	@Column(name = "partno")
	private String partno;

	@Column(name = "partdesc")
	private String partDesc;

	@Column(name = "currentdate")
	private LocalDate currentDate = LocalDate.now();

	@Column(name = "warehouse")
	private String warehouse;

	@Column(name = "sqty")
	private int sQty;

	@Column(name = "grnno", length = 25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batch", length = 25)
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	
	@Column(name = "expdate")
	private LocalDate expDate;

	@Column(name = "bin")
	private String bin;
	
	@Column(name = "stockdate")
	private LocalDate stockDate=LocalDate.now();
	
	@Column(name = "sourcescreencode")
	private String sourceScreenCode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
