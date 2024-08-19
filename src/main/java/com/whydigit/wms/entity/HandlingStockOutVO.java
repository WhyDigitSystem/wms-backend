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
@Table(name = "handlingstockout")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockOutVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handlingstockoutgen")
	@SequenceGenerator(name = "handlingstockingen", sequenceName = "handlingstockoutseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockoutid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "rpqty")
	private int rpQty;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "sqty")
	private int sQty;
	@Column(name = "pickrequestno")
	private String pickRequestNo;
	@Column(name = "pickrequestdate")
	private LocalDate pickRequestDate;
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "sku")
	private String sku;
	@Column(name = "buyerorderdate")
	private LocalDate buyerOrderDate;
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "sdocid")
	private String sDocid;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremark")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "screencode")
	private String screenCode;
	@Column(name = "client")
	private String client;
	@Column(name = "buyerordno")
	private String buyerOrdNo;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}