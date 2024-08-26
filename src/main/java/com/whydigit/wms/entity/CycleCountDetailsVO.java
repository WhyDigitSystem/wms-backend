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
@Table(name = "cyclecountdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleCountDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cyclecountdetailsgen")
	@SequenceGenerator(name = "cyclecountdetailsgen", sequenceName = "cyclecountdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "cyclecountdetailsid")
	private Long id;
	@Column(name="partno")
	private String partNo;
	@Column(name="partdesc")
	private String partDescription;
	@Column(name="grnno")
	private String grnNo;
	@Column(name="sku")
	private String sku;
	@Column(name="bintype")
	private String binType;
	@Column(name="batchno")
	private String batchNo;
	@Column(name="batchdate")
	private LocalDate batchDate;
	@Column(name="bin")
	private String bin;
	@Column(name="avlqty")
	private int avlQty;
	@Column(name="actualqty")
	private int actualQty;
	@Column(name="grndate")
	private LocalDate grnDate;
	@Column(name="expdate")
	private LocalDate expDate;
	@Column(name="binclass")
	private String binClass;
	@Column(name="celltype")
	private String cellType;
	@Column(name="core")
	private String core;
	@Column(name="lotno")
	private String lotNo;
	@Column(name="qcflag")
	private String qcFlag;
	@Column(name="status")
	private String status;
	
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="cyclecountid")
	private CycleCountVO cycleCountVO;
	
	
}
