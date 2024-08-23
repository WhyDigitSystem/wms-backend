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
@Table(name = "reversepickdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReversePickDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reversepickdetailsgen")
	@SequenceGenerator(name = "reversepickdetailsgen", sequenceName = "reversepickdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "reversepickdetailsid")
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
	@Column(name = "pickqty")
	private int pickQty;
	@Column(name = "revisedqty")
	private int revisedQty;
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

	@ManyToOne
	@JoinColumn(name = "reversepickid")
	@JsonBackReference
	private ReversePickVO reversePickVO;

}
