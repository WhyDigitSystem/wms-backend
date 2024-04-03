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
	@SequenceGenerator(name = "putawaydetailsgen", sequenceName = "putawaydetailsVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawaydetailsid")
	private Long id;

	@Column(name = "partno", length = 30)
	private String partno;

	@Column(name = "batch", length = 30)
	private String batch;

	@Column(name = "partdescripition", length = 30)
	private String partdescripition;

	@Column(name = "sku", length = 30)
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

	@Column(name = "location", length = 30)
	private String location;

	@Column(name = "weight", length = 30)
	private String weight;

	@Column(name = "rate", length = 30)
	private String rate;

	@Column(name = "amount", length = 30)
	private String amount;

	@Column(name = "remarks", length = 30)
	private String remarks;

	@Column(name = "locationtype", length = 30)
	private String locationtype;

	@Column(name = "shortqty", length = 30)
	private int shortqty;

	@Column(name = "sqty")
	private int sqty;

	@Column(name = "ssku", length = 30)
	private String ssku;

	@Column(name = "locationclass", length = 30)
	private String locationclass;

	@Column(name = "celltype", length = 30)
	private String celltype;

	@Column(name = "batchdate", length = 30)
	private LocalDate batchdate;

	@Column(name = "status", length = 30)
	private String status;

	@Column(name = "qcflag", length = 30)
	private String qcflag;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "putawayid")
	private PutAwayVO putAwayVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

}
