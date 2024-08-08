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
@Table(name = "handlingstockaout")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockaOutVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handlingstockoutgen")
	@SequenceGenerator(name = "handlingstockingen", sequenceName = "handlingstockoutseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockoutid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno")
	private String refno;
	@Column(name = "partno")
	private String partno;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "partdesc")
	private String partdesc;
	@Column(name = "rpqty")
	private int rpqty;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "pickrequestno")
	private String pickrequestno;
	@Column(name = "pickrequestdate")
	private LocalDate pickrequestdate;
	@Column(name = "buyerorderno")
	private String buyerorderno;
	@Column(name = "sku")
	private String sku;
	@Column(name = "buyerorderdate")
	private LocalDate buyerorderdate;
	@Column(name = "pickqty")
	private int pickqty;
	@Column(name = "buyerordno")
	private String buyerordno;
	@Column(name = "sdocid")
	private String sdocid;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremark")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "screencode")
	private String screencode;
	@Column(name = "client")
	private String client;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}