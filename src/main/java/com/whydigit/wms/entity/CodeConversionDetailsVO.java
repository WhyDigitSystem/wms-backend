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
	
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "lotno",length =25)
	private String lotNo;
	@Column(name = "bin",length =25)
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
	@Column(name = "cpartno",length =25)
	private String cPartNo;
	@Column(name = "cpartdesc",length =150)
	private String cPartDesc;
	
	@Column(name = "csku",length =25)
	private String cSku;
	@Column(name = "cbatchno",length =25)
	private String cBatchNo;
	@Column(name = "cbatchdate")
	private LocalDate cBatchDate;
	@Column(name = "clotno",length =25)
	private String cLotNo;
	@Column(name = "cbin",length =25)
	private String cbin;
	@Column(name = "cbintype",length =25)
	private String cbinType;
	@Column(name = "remarks",length =150)
	private String remarks;
	
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@Column(name = "cbinclass",length =25)
	private String cBinClass;
	@Column(name = "ccelltype",length =25)
	private String cCellType;
	@Column(name = "ccore",length =25)
	private String cCore;
	@Column(name = "cexpdate")
	private LocalDate cExpDate;
	@Column(name = "cstockdate")
	private LocalDate cStockDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "codeconversionid")
	private CodeConversionVO codeConversionVO;

}
