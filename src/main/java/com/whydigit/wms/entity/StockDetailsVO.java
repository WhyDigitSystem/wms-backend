package com.whydigit.wms.entity;

import java.math.BigDecimal;
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
	private Long orgId;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "docid",length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate;
	@Column(name = "qtypicked")
	private int pickedQty;
	@Column(name = "rate")
	private BigDecimal rate;
	@Column(name = "amount")
	private double amount;
	@Column(name = "inoutitme")
	private String inouttime;
	@Column(name = "refno",length =25)
	private String refNo;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "qty")
	private int qty;
	@Column(name = "lrhawbhblno",length =25)
	private String lrhawbhblNo;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "stockdate")
	private LocalDate stockDate=LocalDate.now();
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "carrier",length =150)
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
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "partno",length =25)
	private String partno;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "sourcescreencode",length =10)
	private String sourceScreenCode;
	@Column(name = "sourcescreenname",length =25)
	private String sourceScreenName;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "ssku",length =25)
	private String sSku;
	@Column(name = "ssqty",length =25)
	private int ssQty;
	@Column(name = "sourceid")
	private Long sourceId;
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
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "qcflag",length =10)
	private String qcFlag;
	@Column(name = "clientcode",length =25)
	private String clientCode;
	@Column(name = "pamount")
	private double pAmount;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "pgroup",length =25)
	private String pGroup;
	@Column(name = "barcode")
	private String barCode;
	@Column(name = "stylecode",length =25)
	private String styleCode;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "buyerorderno",length =25)
	private String buyerOrderNo;
	@Column(name = "batch",length =25)
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "weight")
	private double weight;
	@Column(name = "pckey",length =10)
	private String pcKey;
	@Column(name = "sdactual")
	private String sdactual;
	@Column(name = "tpartno",length =25)
	private String tPartNo;
	@Column(name = "sdate")
	private LocalDate sDate;
	@Column(name = "cdocdate")
	private LocalDate cDocDate;
	@Column(name = "status",length =10)
	private String status;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "isstatus",length =10)
	private String iStatus;
	@Column(name = "sflag",length =10)
	private String sFlag;
	@Column(name = "lotno",length =25)
	private String lotNo;
	@Column(name = "active")
	private boolean active;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
//	@Column(name = "screencode")
//	private String screenCode;
	@Column(name = "finyear")
	private String finYear;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
