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
	
	@Column(name = "partno",length =25)
	private String partNo;
	@Column(name = "batch",length =25)
	private String batch;
	@Column(name = "partdesc",length =150)
	private String partDesc;
	@Column(name = "sku",length =25)
	private String sku;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
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
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "remarks",length =25)
	private String remarks;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "shortqty")
	private int shortQty;
	@Column(name = "grnQty")
	private int grnQty;
	@Column(name = "ssku",length =25)
	private String sSku;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "celltype",length =25)
	private String cellType;
	@Column(name = "batchdate")
	private LocalDate batchDate;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "expdate")
	private LocalDate expDate;
	@Column(name = "qcflag",length =25)
	private String qcFlag;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "putawayid")
	private PutAwayVO putAwayVO;


}
