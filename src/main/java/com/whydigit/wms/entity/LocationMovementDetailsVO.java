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
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partdesc",length =150)
	private String partDesc;
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
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "tobin",length =25)
	private String toBin;
	@Column(name = "tobinclass",length =25)
	private String toBinClass;
	@Column(name = "tobintype",length =25)
	private String toBinType;
	@Column(name = "tocelltype",length =25)
	private String toCellType;
	@Column(name = "tocore",length =25)
	private String toCore;
	@Column(name = "fromqty")
	private int fromQty;
	@Column(name = "toqty")
	private int toQty;
	@Column(name = "remainingqty")
	private int remainingQty;
	@Column(name = "qcflag",length =25)
	private String qcFlag;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "expdate")
	private LocalDate expDate;
	

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "locationmovementid")
	private LocationMovementVO locationMovementVO;
}
