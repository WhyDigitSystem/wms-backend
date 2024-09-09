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
//Child table
@Table(name = "kittingdetails1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KittingDetails1VO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kittingdetails1gen")
	@SequenceGenerator(name = "kittingdetails1gen", sequenceName = "kittingdetails1seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "kitting1id")
	private Long id;
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
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "core")
	private String core;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "qty")
	private int qty;
	@Column(name = "bin")
	private String bin;
	@Column(name = "expdate")
	private LocalDate expDate;

	@ManyToOne
	@JoinColumn(name = "kittingid")
	@JsonBackReference
	private KittingVO kittingVO;

}
