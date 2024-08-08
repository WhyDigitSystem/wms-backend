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
	private String createdBy;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "qtypicked")
	private int pickedQty;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "inoutitme")
	private String inouttime;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "qty")
	private int qty;
	@Column(name = "lrhawbhblno")
	private String lrhawbhblNo;
	@Column(name = "client")
	private String client;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "oqty")
	private int oQty;
	@Column(name = "rqty")
	private int rQty;
	@Column(name = "dqty")
	private int dQty;
	@Column(name = "cqty")
	private int cQty;
	@Column(name = "uqty")
	private int uQty;
	@Column(name = "branch")
	private String branch;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "sourcescreen")
	private String sourceScreen;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "customer")
	private String customer;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "core")
	private String core;
	@Column(name = "toty")
	private int toQty;
	@Column(name = "bin")
	private String bin;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "sku")
	private String sku;
	@Column(name = "ssku")
	private String sSku;
	@Column(name = "ssqty")
	private int ssQty;
	@Column(name = "sqty")
	private int sQty;
	@Column(name = "pqty")
	private int pQty;
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "damageqty")
	private int damageQty;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "clientcode")
	private String clientCode;
	@Column(name = "pamount")
	private String pAmount;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "pgroup")
	private String pGroup;
	@Column(name = "barcode")
	private String barCode;
	@Column(name = "stylecode")
	private String styleCode;
	@Column(name = "expdate")
	private String expDate;
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "batch")
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "weight")
	private String weight;
	@Column(name = "pckey")
	private String pcKey;
	@Column(name = "sdactual")
	private String sdactual;
	@Column(name = "tpartno")
	private String tPartNo;
	@Column(name = "sdate")
	private LocalDate sDate;
	@Column(name = "cdocdate")
	private LocalDate cDocDate;
	@Column(name = "status")
	private String status;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "isstatus")
	private String iStatus;
	@Column(name = "sflag")
	private String sFlag;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "active")
	private boolean active;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "screencode")
	private String screenCode;
	@Column(name = "finyear")
	private String finYear;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}