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
@Table(name = "stockdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockdetailsgen")
	@SequenceGenerator(name = "stockdetailsgen", sequenceName = "stockdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockdetailsid")
	private Long id;

	@Column(name = "cancel")
	private String cancel;
	@Column(name = "orgid")
	private String orgId;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "cancelremarks")
	private String cancelremarks;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "docid")
	private String docid;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "qtypicked")
	private int pickedqty;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "inoutitme")
	private String inouttime;
	@Column(name = "refno")
	private String refno;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "qty")
	private int qty;
	@Column(name = "lrhawbhblno")
	private String lrhawbhblno;
	@Column(name = "client")
	private String client;
	@Column(name = "stockdate")
	private LocalDate stockdate;
	@Column(name = "grnno")
	private String grnno;
	@Column(name = "grndate")
	private LocalDate grndate;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "oqty")
	private int oqty;
	@Column(name = "rqty")
	private int rqty;
	@Column(name = "dqty")
	private int dqty;
	@Column(name = "cqty")
	private int cqty;
	@Column(name = "uqty")
	private int uqty;
	@Column(name = "branch")
	private String branch;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partdesc")
	private String partdesc;
	@Column(name = "sourcescreen")
	private String sourcescreen;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "customer")
	private String customer;
	@Column(name = "locationtype")
	private String locationtype;
	@Column(name = "celltype")
	private String celltype;
	@Column(name = "core")
	private String core;
	@Column(name = "toqty")
	private int toqty;
	@Column(name = "pallet")
	private String pallet;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "sku")
	private String sku;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "ssqty")
	private int ssqty;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "pqty")
	private int pqty;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;
	@Column(name = "damageqty")
	private int damageqty;
	@Column(name = "shortqty")
	private int shortqty;
	@Column(name = "qcflag")
	private String qcflag;
	@Column(name = "subtypecode")
	private String subtypecode;
	@Column(name = "pamount")
	private String pamount;
	@Column(name = "locationclass")
	private String locationclass;
	@Column(name = "pgroup")
	private String pgroup;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "stylecode")
	private String stylecode;
	@Column(name = "expdate")
	private String expdate;
	@Column(name = "buyerorderno")
	private String buyerorderno;
	@Column(name = "batch")
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchdate;
	@Column(name = "weight")
	private String weight;
	@Column(name = "pckey")
	private String pckey;
	@Column(name = "sdactual")
	private String sdactual;
	@Column(name = "tpartno")
	private String tpartno;
	@Column(name = "taskid")
	private String taskid;
	@Column(name = "sdate")
	private LocalDate sdate;
	@Column(name = "cdocdate")
	private LocalDate cdocdate;
	@Column(name = "status")
	private String status;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "isstatus")
	private String istatus;
	@Column(name = "sflag")
	private String sflag;
	@Column(name = "lotno")
	private String lotno;
	@Column(name = "company")
	private String company;
	@Column(name = "userid")
	private String userid;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchname")
	private String branchname;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "screencode")
	private String screencode;
	@Column(name = "finyear")
	private String finyear;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}