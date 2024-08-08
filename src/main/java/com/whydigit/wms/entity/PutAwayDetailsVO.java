package com.whydigit.wms.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

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
	private String partno;
	@Column(name = "batch")
	private String batch;
	@Column(name = "partdescripition")
	private String partdescripition;
	@Column(name = "sku")
	private String sku;
	@Column(name = "invqty")
	private int invqty;
	@Column(name = "recqty")
	private int recqty;
	@Column(name = "ssqty")
	private int ssqty;
	@Column(name = "putawayqty")
	private int putawayqty;
	@Column(name = "putawaypieceqty")
	private int putawaypiecesqty;
	@Column(name = "location")
	private String location;
	@Column(name = "weight")
	private String weight;
	@Column(name = "rate")
	private String rate;
	@Column(name = "amount")
	private String amount;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "locationtype")
	private String locationtype;
	@Column(name = "shortqty")
	private int shortqty;
	@Column(name = "sqty")
	private int sqty;
	@Column(name = "ssku")
	private String ssku;
	@Column(name = "locationclass")
	private String locationclass;
	@Column(name = "celltype")
	private String celltype;
	@Column(name = "batchdate")
	private LocalDate batchdate;
	@Column(name = "status")
	private String status;
	@Column(name = "qcflag")
	private String qcflag;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "putawayid")
	private PutAwayVO putAwayVO;

	

}
