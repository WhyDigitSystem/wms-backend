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
	@SequenceGenerator(name = "grndetailsgen", sequenceName = "grndetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grndetailsid")
	private Long id;

	@Column(name = "qrcode", length = 30)
	private String qrcode;

	@Column(name = "lrnohawbno", length = 30)
	private String lrnohawbno;

	@Column(name = "invoiceno", length = 30)
	private String invoiceno;

	@Column(name = "invoicedate", length = 30)
	private LocalDate invoicedate;

	@Column(name = "partno", length = 30)
	private String partno;

	@Column(name = "partdesc", length = 30)
	private String partdesc;

	@Column(name = "locationtype", length = 30)
	private String locationtype;

	@Column(name = "rate", length = 30)
	private String rate;

	@Column(name = "amount", length = 30)
	private String amount;

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

	@Column(name = "substockqty")
	private int substockqty;

	@Column(name = "batchqty")
	private int batchqty;

	@Column(name = "palletqty")
	private int palletqty;

	@Column(name = "noofpallet", length = 30)
	private String noofpallet;

	@Column(name = "pkgs", length = 30)
	private String pkgs;

	@Column(name = "weight", length = 30)
	private String weight;

	@Column(name = "warehouse", length = 30)
	private String warehouse;

	@Column(name = "batchno", length = 30)
	private String batchno;

	@Column(name = "batchdt", length = 30)
	private LocalDate batchdt;

	@Column(name = "qcflag", length = 30)
	private String qcflag;

	@Column(name = "shipmentno", length = 30)
	private String shipmentno;

	@Column(name = "sqty")
	private int sqty;

	@Column(name = "grnqty")
	private int grnqty;

	@Column(name = "batchpalletno", length = 30)
	private String batchpalletno;

	@Column(name = "expdate", length = 30)
	private LocalDate expdate;

	@Column(name = "mrp", length = 30)
	private String mrp;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "grn_id") // Specify the name of the foreign key column
	private GrnVO grnVO;

}