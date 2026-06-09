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
	@Column(name="partno",length =25)
	private String partNo;
	@Column(name="partdesc",length =150)
	private String partDescription;
	@Column(name="grnno",length =25)
	private String grnNo;
	@Column(name="sku",length =25)
	private String sku;
	@Column(name="bintype",length =25)
	private String binType;
	@Column(name="batchno",length =25)
	private String batchNo;
	@Column(name="batchdate")
	private LocalDate batchDate;
	@Column(name="bin",length =25)
	private String bin;
	@Column(name="avlqty")
	private int avlQty;
	@Column(name="actualqty")
	private int actualQty;
	@Column(name="grndate")
	private LocalDate grnDate;
	@Column(name="expdate")
	private LocalDate expDate;
	@Column(name="binclass",length =25)
	private String binClass;
	@Column(name="celltype",length =25)
	private String cellType;
	@Column(name="core",length =25)
	private String core;
	@Column(name="lotno",length =25)
	private String lotNo;
	@Column(name="qcflag",length =25)
	private String qcFlag;
	@Column(name="status")
	private String status;
	
	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="cyclecountid")
	private CycleCountVO cycleCountVO;
	
	
}
