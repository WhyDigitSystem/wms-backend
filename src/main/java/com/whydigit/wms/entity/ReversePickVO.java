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
	@Column(name = "buyerrefno")
	private String buyerRefNo;
	@Column(name = "buyerrefdate")
	private LocalDate buyerRefDate;
	@Column(name = "pickrequestdocid")
	private String pickRequestDocId;
	@Column(name = "pickrequestdocdate")
	private LocalDate pickRequestDocDate;
	@Column(name = "boamendment")
	private String boAmendment;
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "buyerorderdate")
	private String buyerOrderDate;
	@Column(name = "buyersreference")
	private String buyersReference;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "clientshortname")
	private String clientShortName;
	@Column(name = "clientname")
	private String clientName;
	@Column(name = "clientaddress")
	private String clientAddress;
	@Column(name = "customershortname")
	private String customerShortName;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customeraddress")
	private String customerAddress;
	@Column(name = "pickorder")
	private String pickOrder;
	@Column(name = "intime")
	private String inTime;
	@Column(name = "docid",unique = true)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer")
	private String customer;
	@Column(name = "client")
	private String client;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "status")
	private String status;
	@Column(name = "freeze")
	private boolean freeze ;
	
	@Column(name = "screenname")
	private String screenName="REVERSE PICK";
	@Column(name = "screencode")
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
