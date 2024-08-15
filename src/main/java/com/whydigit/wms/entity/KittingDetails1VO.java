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
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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
	@Column(name="bin")
	private String pallet;
	@Column(name="partno")
	private String partNo;
	@Column(name="partdescription")
	private String partDescription;
	@Column(name="batchno")
	private String batchNo;
	@Column(name="lotno")
	private String lotNo;
	@Column(name="grnno")
	private String grnNo;
	@Column(name="grndate")
	private LocalDate grnDate;
	@Column(name="sku")
	private String sku;
	@Column(name="avlqty")
	private int avlQty;
	@Column(name="qty")
	private int qty;
	@Column(name="unitrate")
	private int unitRate;
	@Column(name="amount")
	private int amount;
	@Column(name="qcflag")
	private boolean qcflag;
	
	@ManyToOne
	@JoinColumn(name ="kittingid")
	@JsonBackReference
	private KittingVO kittingVO;

}
