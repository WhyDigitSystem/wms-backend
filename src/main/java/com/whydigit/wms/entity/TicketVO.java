package com.whydigit.wms.entity;

import java.time.LocalDate;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketgen")
	@SequenceGenerator(name = "ticketgen", sequenceName = "ticketseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "ticketid")
	private Long id;

	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "createdby", length = 25)
	private String createdBy;
	@Column(name = "modifiedby", length = 25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks", length = 150)
	private String cancelRemark;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "branchcode", length = 25)
	private String branchCode;
	@Column(name = "branch", length = 25)
	private String branch;
	@Column(name = "client", length = 150)
	private String client;
	@Column(name = "customer", length = 150)
	private String customer;
	@Column(name = "warehouse", length = 25)
	private String warehouse;
	@Column(name = "freeze")
	private boolean freeze = false;
	@Column(name = "finyear", length = 10)
	private String finYear;
	@Column(name = "name", length = 150)
	private String name;
	@Column(name = "email", length = 25)
	private String email;
	@Column(name = "issuedesc", length = 25)
	private String issueDesc;
	@Column(name = "ticketstatus")
	private String ticketStatus;

	@Column(name = "ticketremarks", length = 150)
	private String ticketRemarks;

	@Column(name = "sourceId")
	private Long sourceId;

	@Column(name = "updateddate")
	private LocalDate updatedDate;

	@Column(name = "subject")
	private String subject;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	@Column(name = "companyname")
	private String companyName;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "statusflag")
	private Boolean statusFlag = true;

	@Column(name = "notificationflag")
	private Boolean notificationFlag = false;

	
	@Lob
	@Column(name = "screenshot", columnDefinition = "LONGBLOB")
	private byte[] screenShot;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
