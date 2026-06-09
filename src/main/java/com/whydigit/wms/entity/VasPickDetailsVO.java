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
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "partdescription",length =25)
	private String partDescription;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "avlqty")
	private int avlQty;
	@Column(name = "picqty")
	private int picQty;
	@Column(name = "remaningqty")
	private int remaningQty;
	@Column(name="qcflag",length =10)
	private String qcFlag;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@Column(name = "status",length =10)
	private String status;
	
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="vaspickid")
	private VasPickVO vasPickVO;

}
