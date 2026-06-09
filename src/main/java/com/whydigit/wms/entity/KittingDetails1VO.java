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
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDescription;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "qty")
	private int qty;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "expdate")
	private LocalDate expDate;

	@ManyToOne
	@JoinColumn(name = "kittingid")
	@JsonBackReference
	private KittingVO kittingVO;

}
