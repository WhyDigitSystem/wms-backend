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
	@SequenceGenerator(name = "gatepassindetailsgen", sequenceName = "gatepassindetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatepassindetailsid")
	private Long id;

	@Column(name = "sno", length = 30)
	private String sno;
	@Column(name = "orgid", length = 30)
	private String orgId;
	@Column(name = "lrnohaw", length = 30)
	private String lrnohaw;
	@Column(name = "invoiceno", length = 30)
	private String invoiceno;
	@Column(name = "invoicedate", length = 30)
	private LocalDate invoicedate;
	@Column(name = "partno", length = 30)
	private String partno;
	@Column(name = "partcode", length = 30)
	private String partcode;
	@Column(name = "partdescription", length = 30)
	private String partdescription;
	@Column(name = "batchno", length = 30)
	private String batchno;
	@Column(name = "unit", length = 30)
	private String unit;
	@Column(name = "sku", length = 30)
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
	@Column(name = "subunit", length = 30)
	private String subunit;
	@Column(name = "substockshortqty")
	private int substockshortqty;
	@Column(name = "grnpieceqty")
	private int grnpiecesqty;
	@Column(name = "weight", length = 30)
	private String weight;
	@Column(name = "rate", length = 30)
	private String rate;
	@Column(name = "rowno", length = 30)
	private String rowno;
	@Column(name = "amount", length = 30)
	private String amount;
	@Column(name = "remarks", length = 30)
	private String remarks;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "gatepassid")
	private GatePassInVO gatePassInVO;

}
