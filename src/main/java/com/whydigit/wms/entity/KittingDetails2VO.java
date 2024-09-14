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

//Parent table
@Entity
@Table(name = "kittingdetails2")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KittingDetails2VO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kittingdetails2gen")
	@SequenceGenerator(name = "kittingdetails2gen", sequenceName = "kittingdetails2seq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "kitting2id")
	private Long id;
	@Column(name="partno")
	private String ppartNo;
	@Column(name="partdesc")
	private String ppartDesc;
	@Column(name = "sku")
	private String psku;
	@Column(name="grnno")
	private String pgrnNo;
	@Column(name="grndate")
	private LocalDate pgrnDate;
	@Column(name="batchno")
	private String pbatchNo;
	@Column(name = "batchdate")
	private LocalDate pbatchDate;
	@Column(name = "bin")
	private String pbin;
	@Column(name = "bintype")
	private String pbinType;
	@Column(name = "binclass")
	private String pbinClass;
	@Column(name = "celltype")
	private String pcellType;
	@Column(name = "core")
	private String pcore;
	@Column(name="qty")
	private int pqty;
	@Column(name="lotno")
	private String plotNo;
	@Column(name="qcflag")
	private String pqcflag;	
	@Column(name = "expdate")
	private LocalDate pexpDate;
	@Column(name = "stokcdate")
	private LocalDate pstockDate;
	
	@ManyToOne
	@JoinColumn(name ="kittingid")
	@JsonBackReference
	private KittingVO kittingVO;

	
}
