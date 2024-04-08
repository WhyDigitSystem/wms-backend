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
@Table(name = "handlingStockaOut")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockaOutVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handlingstockoutgen")
	@SequenceGenerator(name = "handlingstockingen", sequenceName = "handlingstockoutVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockoutid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno", length = 30)
	private String refno;
	@Column(name = "partno", length = 30)
	private String partno;
	@Column(name = "refdate", length = 30)
	private LocalDate refdate;
	@Column(name = "partdesc", length = 30)
	private String partdesc;
	@Column(name = "rpqty")
	private int rpqty;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "pickrequestno", length = 30)
	private String pickrequestno;
	@Column(name = "pickrequestdate", length = 30)
	private LocalDate pickrequestdate;
	@Column(name = "buyerorderno", length = 30)
	private String buyerorderno;
	@Column(name = "sku", length = 30)
	private String sku;
	@Column(name = "buyerorderdate", length = 30)
	private LocalDate buyerorderdate;
	@Column(name = "pickqty")
	private int pickqty;
	@Column(name = "buyerordno", length = 30)
	private String buyerordno;
	@Column(name = "sdocid", length = 30)
	private String sdocid;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "company", length = 30)
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "cancelremark", length = 30)
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "screencode", length = 30)
	private String screencode;
	@Column(name = "client", length = 30)
	private String client;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}