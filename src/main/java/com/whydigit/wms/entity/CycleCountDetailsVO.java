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
	private Long partNo;
	@Column(name="paretdescription")
	private String paretDescription;
	@Column(name="grnno")
	private Long grnNo;
	@Column(name="sku")
	private String sku;
	@Column(name="bintype")
	private String binType;
	@Column(name="batchno")
	private int batchNo;
	@Column(name="batchdate")
	private LocalDate batchDate;
	@Column(name="bin")
	private String bin;
	@Column(name="qty")
	private int qty;
	@Column(name="actualqty")
	private int actualQty;
	@Column(name="pqcflag")
	private boolean qQcflag;	
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name ="cyclecountid")
	private CycleCountVO cycleCountVO;
	
	
}
