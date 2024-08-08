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
	@Column(name = "refno")
	private String refno;
	@Column(name = "grnno")
	private String grnno;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "partno")
	private String partno;
	@Column(name = "grndate")
	private LocalDate grndate;
	@Column(name = "partdesc")
	private String partdesc;
	@Column(name = "rpqty")
	private int rpqty;
	@Column(name = "noofpallet")
	private String noofpallet;
	@Column(name = "locationtype")
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
	private String rate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "qcflag")
	private String qcflag;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "finyr")
	private String finyr;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchid")
	private String branchid;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "client")
	private String client;
	@Column(name = "palletcount")
	private String palletcount;
	@Column(name = "indcno")
	private String indcno;
	@Column(name = "lrhawbhblno")
	private String lrhawbhblno;
	@Column(name = "sku")
	private String sku;
	@Column(name = "sdocid")
	private String sdocid;
	@Column(name = "stockdate")
	private LocalDate stockdate;
	@Column(name = "sdocdate")
	private LocalDate sdocdate;
	@Column(name = "sourcescreen")
	private String sourcescreen;
	@Column(name = "expdate")
	private String expdate;
	@Column(name = "batchno")
	private String batchno;
	@Column(name = "batchdt")
	private String batchdt;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "modifiedby")
	private String createdby;
	@Column(name = "updatedby")
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
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "screencode")
	private String screencode;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
