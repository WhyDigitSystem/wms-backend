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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pickrequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pickrequestgen")
	@SequenceGenerator(name = "pickrequestgen", sequenceName = "pickrequestseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestid")
	private Long id;
	@Column(name = "transactiontype")
	private String transcationtype;
	@Column(name = "buyerrefno")
	private String buyerRefNo;
	@Column(name = "buyerrefdate")
	private LocalDate buyerRefDate;
	@Column(name = "shipmentmethod")
	private String shipmentMethod;
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "buyersreference")
	private String buyersReference;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "clientshortname")
	private String clientShortName;
	@Column(name = "clientaddress")
	private String clientAddress;
	@Column(name = "dispatch")
	private String dispatch;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "customeraddress")
	private String customerAddress;
	@Column(name = "duedays")
	private String dueDays;
	@Column(name = "noofboxes")
	private String noOfBoxes;
	@Column(name = "pickorder")
	private String pickOrder;
	@Column(name = "outtime")
	private String outTime;
	@Column(name = "docid")
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
	@Column(name = "freeze")
	private boolean freeze = true;
	@Column(name = "grndate")
	private LocalDate grnDate = LocalDate.now();

//	summary table
	@Column(name = "totalpickqty")
	private int totalPickQty;
	@Column(name = "totalorderqty")
	private int totalOrderQty;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pickRequestVO")
	private List<PickRequestDetailsVO> pickRequestDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}