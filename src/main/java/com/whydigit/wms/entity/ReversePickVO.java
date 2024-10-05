package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reversepick")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReversePickVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reversepickgen")
	@SequenceGenerator(name = "reversepickgen", sequenceName = "reversepickseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "reversepickid")
	private Long id;
	@Column(name = "buyerrefno",length =25)
	private String buyerRefNo;
	@Column(name = "buyerrefdate")
	private LocalDate buyerRefDate;
	@Column(name = "pickrequestdocid")
	private String pickRequestDocId;
	@Column(name = "pickrequestdocdate")
	private LocalDate pickRequestDocDate;
	@Column(name = "boamendment",length =25)
	private String boAmendment;
	@Column(name = "buyerorderno",length =25)
	private String buyerOrderNo;
	@Column(name = "buyerorderdate")
	private String buyerOrderDate;
	@Column(name = "buyersreference",length =25)
	private String buyersReference;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "clientshortname",length =25)
	private String clientShortName;
	@Column(name = "clientname",length =150)
	private String clientName;
	@Column(name = "clientaddress",length =150)
	private String clientAddress;
	@Column(name = "customershortname",length =25)
	private String customerShortName;
	@Column(name = "customername",length =150)
	private String customerName;
	@Column(name = "customeraddress",length =150)
	private String customerAddress;
	@Column(name = "pickorder",length =25)
	private String pickOrder;
	@Column(name = "intime")
	private String inTime;
	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =10)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "freeze")
	private boolean freeze ;
	
	@Column(name = "screenname",length =25)
	private String screenName="REVERSE PICK";
	@Column(name = "screencode",length =10)
	private String screenCode="RP";
	
	@Column(name = "totalpickqty")
	private int totalPickQty;
	@Column(name = "totalrevisedqty")
	private int totalRevisedQty;
	
	
	@OneToMany(mappedBy ="reversePickVO",cascade =CascadeType.ALL)
	@JsonManagedReference
    private List<ReversePickDetailsVO> reversePickDetailsVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
