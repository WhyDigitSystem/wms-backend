package com.whydigit.wms.entity;

import java.math.BigDecimal;
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
	@Column(name = "pallet")
	private String pallet;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdescripition")
	private String partDescripition;
	@Column(name = "grnno")
	private String GRNNo;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "tolocation")
	private String toLocation;
	@Column(name = "fromlocation")
	private String fromLocation;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainingqty")
	private int remainingQty;
	@Column(name = "qcflag")
	private boolean qcFlag;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "locationmovementid")
	private LocationMovementVO locationMovementVO;
}
