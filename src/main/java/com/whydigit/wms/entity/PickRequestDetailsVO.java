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
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "sku")
	private String sku;
	@Column(name = "core")
	private String core;
	@Column(name = "bin")
	private String bin;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno")
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
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "status")
	private String status;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "qcflag")
	private String qcFlag;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "pickrequestid")
	private PickRequestVO pickRequestVO;

}