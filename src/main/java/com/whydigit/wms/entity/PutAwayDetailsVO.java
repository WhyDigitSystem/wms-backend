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
@Table(name = "putawaydetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayDetailsVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "putawaydetailsgen")
	@SequenceGenerator(name = "putawaydetailsgen", sequenceName = "putawaydetailsseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawaydetailsid")
	private Long id;
	
	@Column(name = "partno")
	private String partNo;
	@Column(name = "batch")
	private String batch;
	@Column(name = "partdescripition")
	private String partDescripition;
	@Column(name = "sku")
	private String sku;
	@Column(name = "invqty")
	private int invQty;
	@Column(name = "recqty")
	private int recQty;
	@Column(name = "ssqty")
	private int sSqty;
	@Column(name = "putawayqty")
	private int putAwayQty;
	@Column(name = "putawaypieceqty")
	private int putAwayPiecesQty;
	@Column(name = "bin")
	private String bin;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private double amount;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "sqty")
	private int sQty;
	@Column(name = "ssku")
	private String sSku;
	@Column(name = "binclass")
	private String binclass;
	@Column(name = "celltype")
	private String cellType;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "status")
	private String status;
	@Column(name = "qcflag")
	private String qcFlag;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "putawayid")
	private PutAwayVO putAwayVO;


}
