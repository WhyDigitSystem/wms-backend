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

	@Column(name = "sno")
	private String sno;
	@Column(name = "orgid")
	private String orgId;
	@Column(name = "lrnohaw")
	private String lrnohaw;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "invoicedate")
	private LocalDate invoicedate;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partcode")
	private String partcode;
	@Column(name = "partdescription")
	private String partdescription;
	@Column(name = "batchno")
	private String batchno;
	@Column(name = "unit")
	private String unit;
	@Column(name = "sku")
	private String sku;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;
	@Column(name = "shortqty")
	private int shortqty;
	@Column(name = "damageqty")
	private int damageqty;
	@Column(name = "grnqty")
	private int grnqty;
	@Column(name = "subunit")
	private String subunit;
	@Column(name = "substockshortqty")
	private int substockshortqty;
	@Column(name = "grnpieceqty")
	private int grnpiecesqty;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "rowno")
	private String rowno;
	@Column(name = "amount")
	private String amount;
	@Column(name = "remarks")
	private String remarks;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "gatepassid")
	private GatePassInVO gatePassInVO;

}
