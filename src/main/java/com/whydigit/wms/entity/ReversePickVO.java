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
	
	@Column(name = "pickrequesid")
	private String pickRequestId;
	@Column(name = "dispatch")
	private String dispatch;
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "buyerorderrefno")
	private String buyerOrderRefNo;
	@Column(name = "shipmentmethod")
	private String shipmentMethod;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "buyerorderrefdate")
	private LocalDate buyerOrderRefDate;
	@Column(name = "noofboxes")
	private int noOfBoxes;
	@Column(name = "duedays")
	private int dueDays;
	@Column(name = "clientname")
	private String clientName;
	@Column(name = "customername")
	private String customerName;
	@Column(name = "outtime")
	private String outTime;
	@Column(name = "clientaddress")
	private String clientAddress;
	@Column(name = "customeraddress")
	private String customerAddress;
	private String status;
	@Column(name = "boammount")
	private String boAmmount;

	@Column(name = "screenname")
	private String screenName = "REVERSEPICK";
	@Column(name = "screencode")
	private String screenCode = "RP";
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name = "docid", unique = true)
	private String docId;
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
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "freeze")
	private String freeze;
	
	
	@OneToMany(mappedBy ="reversePickVO",cascade =CascadeType.ALL)
	@JsonManagedReference
    private List<ReversePickDetailsVO> reversePickDetailsVO;
	
	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
