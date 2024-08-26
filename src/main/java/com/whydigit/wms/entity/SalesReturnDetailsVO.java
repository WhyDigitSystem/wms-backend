package com.whydigit.wms.entity;

import java.math.BigDecimal;
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
@Table(name = "salesreturndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesreturndetailsgen")
	@SequenceGenerator(name = "salesreturndetailsgen", sequenceName = "salesreturndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesreturndetailsid")
	private Long id;
	@Column(name = "lrno")
	private String LRNo;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "sku")
	private String sku;
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "retqty")
	private int retQty;
	@Column(name = "damageqty")
	private int damageQty;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "noofbin")
	private String noOfBin;
	@Column(name = "binqty")
	private int binQty;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "qcflag")
	private boolean qcFlag;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "salesreturnid")
	private SalesReturnVO salesReturnVO;
		
	}

