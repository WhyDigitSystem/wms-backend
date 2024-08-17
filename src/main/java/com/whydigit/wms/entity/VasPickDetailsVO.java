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
@Table(name = "vaspickdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VasPickDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaspickdetailsgen")
	@SequenceGenerator(name = "vaspickdetailsgen", sequenceName = "vaspickdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vaspickdetailsid")
	private Long id;
	@Column(name = "picbin")
	private String picBin;
	@Column(name = "partcode")
	private String partCode;
	@Column(name = "partdescription")
	private String partDescription;
	@Column(name = "partno")
	private String partNo;
	private String sku;
	private String bin;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private String grnDate;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "picqty")
	private int picQty;
	@Column(name = "remaningqty")
	private int remaningQty;
	@Column(name = "manufacturedate")
	private LocalDate manufactureDate;
	@Column(name="qcflag")
	private String qcflag;
	
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "clientcode")
	private String clientCode;	
	@Column(name = "core")
	private String core;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "pckey")
	private String pckey;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="vaspickid")
	private VasPickVO vasPickVO;

}
