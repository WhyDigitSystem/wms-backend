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
@Table(name = "gatepassindetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePassInDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gatepassindetailsgen")
	@SequenceGenerator(name = "gatepassindetailsgen", sequenceName = "gatepassindetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatepassindetailsid")
	private Long id;

	@Column(name = "sno",length =25)
	private String sNo;
	@Column(name = "orgid")
	private String orgId;
	@Column(name = "irnohaw",length =25)
	private String irNoHaw;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "partcode",length =25)
	private String partCode;
	@Column(name = "partdescription",length =150)
	private String partDescription;
	@Column(name = "batchno",length =25)
	private String batchNo;
	@Column(name = "unit",length =25)
	private String unit;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "damageqty")
	private int damageQty;
	@Column(name = "grnqty")
	private int grnQty;
	@Column(name = "subunit",length =25)
	private String subUnit;
	@Column(name = "substockshortqty")
	private int subStockShortQty;
	@Column(name = "grnpieceqty")
	private int grnPiecesQty;
	@Column(name = "weight")
	private double weight;
	@Column(name = "rate")
	private double rate;
	@Column(name = "rowno",length =25)
	private String rowNo;
	@Column(name = "amount")
	private double amount;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "batchdate")
	private LocalDate batchDate;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "gatepassid")
	private GatePassInVO gatePassInVO;

}
