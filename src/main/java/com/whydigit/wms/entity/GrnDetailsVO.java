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
@Table(name = "grndetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDetailsVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grndetailsgen")
	@SequenceGenerator(name = "grndetailsgen", sequenceName = "grndetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;

	@Column(name = "qrcode")
	private String qrcode;
	@Column(name = "lrnohawbno")
	private String lrnohawbno;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "invoicedate")
	private LocalDate invoicedate;
	@Column(name = "partno")
	private String partno;
	@Column(name = "partdesc")
	private String partdesc;
	@Column(name = "locationtype")
	private String locationtype;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private String amount;
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
	@Column(name = "substockqty")
	private int substockqty;
	@Column(name = "batchqty")
	private int batchqty;
	@Column(name = "palletqty")
	private int palletqty;
	@Column(name = "noofpallet")
	private String noofpallet;
	@Column(name = "pkgs")
	private String pkgs;
	@Column(name = "weight")
	private String weight;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "batchno")
	private String batchno;
	@Column(name = "batchdt")
	private LocalDate batchdt;
	@Column(name = "qcflag")
	private String qcflag;
	@Column(name = "shipmentno")
	private String shipmentno;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "grnqty")
	private int grnqty;
	@Column(name = "batchpalletno")
	private String batchpalletno;

	@Column(name = "expdate")
	private LocalDate expdate;


	@Column(name = "mrp")
	private String mrp;

	@Column(name = "cancelremarks")
	private String cancelremark;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "grn_id") // Specify the name of the foreign key column
	private GrnVO grnVO;

}