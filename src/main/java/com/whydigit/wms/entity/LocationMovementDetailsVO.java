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
@Table(name = "locationmovementdetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMovementDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationmovementdetailsgen")
	@SequenceGenerator(name = "locationmovementdetailsgen", sequenceName = "locationmovementdetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "locationmovementdetailsid")
	private Long id;
	@Column(name = "bin")
	private String bin;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdescripition")
	private String partDesc;
	@Column(name = "grnno")
	private String GRNNo;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "tobin")
	private String toBin;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainingqty")
	private int remainingQty;
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "sku")
	private String sku;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "status")
	private String status = "R";
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
	private String pcKey;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "stokcdate")
	private LocalDate stockDate;
	@Column(name = "tobinclass")
	private String toBinClass;
	@Column(name = "tobintype")
	private String toBinType;
	@Column(name = "tocelltype")
	private String toCellType;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "locationmovementid")
	private LocationMovementVO locationMovementVO;
}
