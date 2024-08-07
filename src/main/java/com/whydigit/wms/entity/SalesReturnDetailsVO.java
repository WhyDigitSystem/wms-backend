package com.whydigit.wms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
	@Column(name = "partdescripition")
	private String partDescripition;
	@Column(name = "unit")
	private String unit;
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
	@Column(name = "noofpallet")
	private String noOfPallet;
	@Column(name = "palletqty")
	private int palletQty;
	@Column(name = "weight")
	private BigDecimal weight;
	@Column(name = "rate")
	private BigDecimal rate;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "insamt")
	private BigDecimal insAmt;
	@Column(name = "remarks")
	private String remarks;
}
