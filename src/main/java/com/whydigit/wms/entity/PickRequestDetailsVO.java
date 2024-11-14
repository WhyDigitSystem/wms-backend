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
@Table(name = "pickrequestdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pickrequestdetailsgen")
	@SequenceGenerator(name = "pickrequestdetailsgen", sequenceName = "pickrequestdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestdetailsid")
	private Long id;

	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "orderqty")
	private int orderQty;
	@Column(name = "avlqty")
	private int availQty;
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "pickqtyperbin")
	private int pickQtyPerBin;
	@Column(name = "remainingqty")
	private int remainingQty;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "ssku",length =25)
	private String ssku;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "qcflag",length =25)
	private String qcFlag;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pickrequestid")
	private PickRequestVO pickRequestVO;

}