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
	
	@Column(name = "frombin",length =25)
	private String fromBin;
	@Column(name = "frombinclass",length =25)
	private String fromBinClass;
	@Column(name = "frombintype",length =25)
	private String fromBinType;
	@Column(name = "fromCelltype",length =25)
	private String fromCellType;
	@Column(name = "fromcore",length =25)
	private String fromCore;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno",length =25)
	private String batch;
	@Column(name = "batchdate")
	private LocalDate batchDate;	
	@Column(name = "tobin",length =25)
	private String toBin;
	@Column(name = "tobinclass",length =25)
	private String toBinClass;
	@Column(name = "tobintype",length =25)
	private String toBinType;
	@Column(name = "tocelltype",length =25)
	private String toCellType;
	@Column(name = "tocore",length =25)
	private String toCore;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainqty")
	private int remainQty;
	@Column(name = "qcflag",length =10)
	private String qcFlag;
	@Column(name = "expdate")
	private LocalDate expDate;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "stockrestateid")
	private StockRestateVO stockRestateVO;
	
	
}
