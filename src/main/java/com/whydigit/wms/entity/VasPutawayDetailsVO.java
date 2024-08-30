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
@Table(name = "vasputawaydetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VasPutawayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vasputawaydetailsgen")
	@SequenceGenerator(name = "vasputawaydetailsgen", sequenceName = "vasputawaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "vasputawaydetailsid")
	private Long id;
	@Column(name = "partno")
	private String partNo;
	@Column(name = "partdesc")
	private String partDesc;
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "batchno")
	private String batchNo;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "status")
	private String status ="R";
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "putawayqty")
	private int putAwayQty;
	@Column(name = "frombin")
	private String fromBin;
	@Column(name = "tobin")
	private String toBin;
	@Column(name = "sku")
	private String sku;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "qcflag")
	private String qcFlag;
	@Column(name = "frombinclass")
	private String fromBinClass;
	@Column(name = "frombintype")
	private String fromBinType;
	@Column(name = "fromcelltype")
	private String fromCellType;
	@Column(name = "fromcore")
	private String fromCore;	
	@Column(name = "tobinclass")
	private String toBinClass;
	@Column(name = "tobintype")
	private String toBinType;
	@Column(name = "tocelltype")
	private String toCellType;
	@Column(name = "tocore")
	private String toCore;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "stockdate")
	private LocalDate stockDate;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "vasputawayid")
	private VasPutawayVO vasPutawayVO;
}
