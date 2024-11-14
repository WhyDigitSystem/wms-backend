package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grndetailsgen")
	@SequenceGenerator(name = "grndetailsgen", sequenceName = "grndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;

	@Column(name = "qrcode",length =150)
	private String qrCode;
	@Column(name = "lrnohawbno",length =150)
	private String lrNoHawbNo;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "rate")
	private double rate;
	@Column(name = "amount")
	private double amount;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "damageqty")
	private int damageQty;
	@Column(name = "substockqty")
	private int subStockQty;
	@Column(name = "batchqty")
	private int batchQty;
	@Column(name = "binqty")
	private int binQty;
	@Column(name = "nofbins")
	private int noOfBins;
	@Column(name = "pkgs")
	private int pkgs;
	@Column(name = "weight",length =25)
	private String weight;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdt")
	private LocalDate batchDt;
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "shipmentno",length =25)
	private String shipmentNo;
	@Column(name = "sqty")
	private int sQty;
	@Column(name = "grnqty")
	private int grnQty;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "damageremarks",length =150)
	private String damageRemark;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "grnid") // Specify the name of the foreign key column
	private GrnVO grnVO;

}