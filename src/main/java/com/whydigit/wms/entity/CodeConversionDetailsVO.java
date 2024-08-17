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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "codeconversiondetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeConversionDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codeconversiondetailsgen")
	@SequenceGenerator(name = "codeconversiondetailsgen", sequenceName = "codeconversiondetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "codeconversiondetailsid")
	private Long id;
	
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "status")
	private String status ="R";
	@Column(name = "sku")
	private String sku;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "bin")
	private String bin;
	@Column(name = "qty")
	private int qty;
	@Column(name = "actualqty")
	private int actualQty;
	@Column(name = "rate")
	private BigDecimal rate;
	@Column(name = "convertqty")
	private int convertQty;
	@Column(name = "crate")
	private BigDecimal cRate;
	@Column(name = "cpartno")
	private String cPartNo;
	@Column(name = "cpartdesc")
	private String cPartDesc;
	
	@Column(name = "csku")
	private String cSku;
	@Column(name = "cbatchno")
	private String cBatchNo;
	@Column(name = "cbatchdate")
	private LocalDate cBatchDate;
	@Column(name = "clotno")
	private String cLotNo;
	@Column(name = "cbin")
	private String cbin;
	@Column(name = "cbintype")
	private String cbinType;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "qcflags")
	private String qcFlags;
	
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "clientcode")
	private String clientCode;	
	@Column(name = "core")
	private String core;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "pckey")
	private String pckey;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "codeconversionid")
	private CodeConversionVO codeConversionVO;

}
