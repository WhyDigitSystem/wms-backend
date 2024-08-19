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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stockrestatedetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRestateDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockrestatedetailsgen")
	@SequenceGenerator(name = "stockrestatedetailsgen", sequenceName = "stockrestatedetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockrestatedetailsid")
	private Long id;
	
	@Column(name = "frombin")
	private String fromBin;
	@Column(name = "frombinclass")
	private String fromBinClass;
	@Column(name = "frombintype")
	private String fromBinType;
	@Column(name = "fromCelltype")
	private String fromCellType;
	@Column(name = "fromcore")
	private String fromCore;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "sku")
	private String sku;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno")
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;	
	@Column(name = "tobin")
	private String toBin;
	@Column(name = "tobinclass")
	private String toBinClass;
	@Column(name = "tobintype")
	private String toBinType;
	@Column(name = "tocelltype")
	private String toCellType;
	@Column(name = "tocore")
	private String toCore;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainqty")
	private int remainQty;
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "expdate")
	private LocalDate expDate;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "stockrestateid")
	private StockRestateVO stockRestateVO;
	
	
}
