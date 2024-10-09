package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

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
	@Column(name = "createdby")
	private Long createdBy;
	@Column(name = "modifiedby")
	private Long updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active=true;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "freeze")
	private boolean freeze=false;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "issuedesc")
	private String issueDesc;
	@Column(name = "ticketstatus")
	private String ticketStatus;
	
	@Column(name = "ticketremarks")
	private String ticketRemarks;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();


}
