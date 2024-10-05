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
	@SequenceGenerator(name = "handlingstockoutgen", sequenceName = "handlingstockoutseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockoutid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno",length =25)
	private String refNo;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "rpqty")
	private int rpQty;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "warehouse",length =150)
	private String warehouse;
	@Column(name = "sqty")
	private int sQty;
	@Column(name = "pickrequestno",length =25)
	private String pickRequestNo;
	@Column(name = "pickrequestdate")
	private LocalDate pickRequestDate;
	@Column(name = "buyerorderno",length =25)
	private String buyerOrderNo;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "buyerorderdate")
	private LocalDate buyerOrderDate;
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "sdocid",length =25)
	private String sDocid;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "screencode",length =10)
	private String screenCode;
	@Column(name = "client",length =25)
	private String client;
	@Column(name = "buyerordno",length =25)
	private String buyerOrdNo;
	@Column(name = "buyerorddate")
	private LocalDate buyerOrdDate;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}