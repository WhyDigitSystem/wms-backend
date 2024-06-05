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
	@SequenceGenerator(name = "stockdetailsgen", sequenceName = "stockdetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockdetailsid")
	private Long id;

	@Column(name = "cancel", length = 30)
	private String cancel;
	@Column(name = "orgid", length = 30)
	private String orgId;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "cancelremarks", length = 30)
	private String cancelremarks;
	@Column(name = "branchcode", length = 30)
	private String branchcode;
	@Column(name = "docid", length = 30)
	private String docid;
	@Column(name = "docdate", length = 30)
	private LocalDate docdate;
	@Column(name = "qtypicked")
	private int pickedqty;
	@Column(name = "rate", length = 30)
	private String rate;
	@Column(name = "amount", length = 30)
	private String amount;
	@Column(name = "inoutitme", length = 30)
	private String inouttime;
	@Column(name = "refno", length = 30)
	private String refno;
	@Column(name = "refdate", length = 30)
	private LocalDate refdate;
	@Column(name = "qty")
	private int qty;
	@Column(name = "lrhawbhblno", length = 30)
	private String lrhawbhblno;
	@Column(name = "client", length = 30)
	private String client;
	@Column(name = "stockdate", length = 30)
	private LocalDate stockdate;
	@Column(name = "grnno", length = 30)
	private String grnno;
	@Column(name = "grndate", length = 30)
	private LocalDate grndate;
	@Column(name = "carrier", length = 30)
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
	@Column(name = "branch", length = 30)
	private String branch;
	@Column(name = "partno", length = 30)
	private String partno;
	@Column(name = "partdesc", length = 30)
	private String partdesc;
	@Column(name = "sourcescreen", length = 30)
	private String sourcescreen;
	@Column(name = "remarks", length = 30)
	private String remarks;
	@Column(name = "customer", length = 30)
	private String customer;
	@Column(name = "locationtype", length = 30)
	private String locationtype;
	@Column(name = "celltype", length = 30)
	private String celltype;
	@Column(name = "core", length = 30)
	private String core;
	@Column(name = "toqty")
	private int toqty;
	@Column(name = "pallet", length = 30)
	private String pallet;
	@Column(name = "warehouse", length = 30)
	private String warehouse;
	@Column(name = "sku", length = 30)
	private String sku;
	@Column(name = "ssku", length = 30)
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
	@Column(name = "qcflag", length = 30)
	private String qcflag;
	@Column(name = "subtypecode", length = 30)
	private String subtypecode;
	@Column(name = "pamount", length = 30)
	private String pamount;
	@Column(name = "locationclass", length = 30)
	private String locationclass;
	@Column(name = "pgroup", length = 30)
	private String pgroup;
	@Column(name = "barcode", length = 30)
	private String barcode;
	@Column(name = "stylecode", length = 30)
	private String stylecode;
	@Column(name = "expdate", length = 30)
	private String expdate;
	@Column(name = "buyerorderno", length = 30)
	private String buyerorderno;
	@Column(name = "batch", length = 30)
	private String batch;
	@Column(name = "batchdate", length = 30)
	private LocalDate batchdate;
	@Column(name = "weight", length = 30)
	private String weight;
	@Column(name = "pckey", length = 30)
	private String pckey;
	@Column(name = "sdactual", length = 30)
	private String sdactual;
	@Column(name = "tpartno", length = 30)
	private String tpartno;
	@Column(name = "taskid", length = 30)
	private String taskid;
	@Column(name = "sdate", length = 30)
	private LocalDate sdate;
	@Column(name = "cdocdate", length = 30)
	private LocalDate cdocdate;
	@Column(name = "status", length = 30)
	private String status;
	@Column(name = "invoiceno", length = 30)
	private String invoiceno;
	@Column(name = "isstatus", length = 30)
	private String istatus;
	@Column(name = "sflag", length = 30)
	private String sflag;
	@Column(name = "lotno", length = 30)
	private String lotno;
	@Column(name = "company", length = 30)
	private String company;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchname", length = 30)
	private String branchname;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "screencode", length = 30)
	private String screencode;
	@Column(name = "finyear", length = 30)
	private String finyear;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}