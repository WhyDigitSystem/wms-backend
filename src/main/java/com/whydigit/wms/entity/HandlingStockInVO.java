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
@Table(name = "handlingstockin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlingStockInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "handlingstockingen")
	@SequenceGenerator(name = "handlingstockingen", sequenceName = "handlingstockinseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "handlingstockinid")
	private Long id;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "refno",length =25)
	private String refno;
	@Column(name = "grnno",length =25)
	private String grnno;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "partno",length =25)
	private String partno;
	@Column(name = "grndate")
	private LocalDate grndate;
	@Column(name = "partdesc",length =150)
	private String partdesc;
	@Column(name = "rpqty")
	private int rpqty;
	@Column(name = "noofpallet")
	private int noofpallet;
	@Column(name = "locationtype",length =25)
	private String locationtype;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "ssqty")
	private int ssqty;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;
	@Column(name = "damageqty")
	private int damageqty;
	@Column(name = "shortqty")
	private int shortqty;
	@Column(name = "palletqty")
	private int palletqty;
	@Column(name = "rate")
	private double rate;
	@Column(name = "amount")
	private double amount;
	@Column(name = "qcflag")
	private String qcflag;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "finyr",length =10)
	private String finyr;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchid")
	private String branchid;
	@Column(name = "branchcode",length =25)
	private String branchcode;
	@Column(name = "customer",length =25)
	private String customer;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "client",length =25)
	private String client;
	@Column(name = "palletcount")
	private int palletcount;
	@Column(name = "indcno",length =25)
	private String indcno;
	@Column(name = "lrhawbhblno",length =25)
	private String lrhawbhblno;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "sdocid",length =25)
	private String sdocid;
	@Column(name = "stockdate")
	private LocalDate stockdate;
	@Column(name = "sdocdate")
	private LocalDate sdocdate;
	@Column(name = "sourcescreen",length =25)
	private String sourcescreen;
	@Column(name = "expdate")
	private LocalDate expdate;
	@Column(name = "batchno",length =25)
	private String batchno;
	@Column(name = "batchdt")
	private LocalDate batchdt;
	@Column(name = "modifiedby",length =25)
	private String createdby;
	@Column(name = "updatedby",length =25)
	private String updatedby;
	@Column(name = "company",length =25)
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid",length =25)
	private String userid;
	@Column(name = "cancelremark",length =150)
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "ssku",length =25)
	private String ssku;
	@Column(name = "screencode",length =10)
	private String screencode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
