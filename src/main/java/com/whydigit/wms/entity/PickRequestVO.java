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
	private long id;

	@Column(name = "transactiontype")
	private String transcationtype;
	@Column(name = "docid")
	private String docid;
	@Column(name = "buyerrefno")
	private String buyerrefno;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "shipmentmethod")
	private String shipmentmethod;
	@Column(name = "buyerorderno")
	private String buyerorderno;
	@Column(name = "buyersreference")
	private String buyersreference;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "client")
	private String client;
	@Column(name = "clientshortname")
	private String clientshortname;
	@Column(name = "clientaddress")
	private String clientaddress;
	@Column(name = "dispatch")
	private String dispatch;
	@Column(name = "customername")
	private String customername;
	@Column(name = "customeraddress")
	private String customeraddress;
	@Column(name = "duedays")
	private String duedays;
	@Column(name = "noofboxes")
	private String noofboxes;
	@Column(name = "pickorder")
	private String pickorder;
	@Column(name = "outtime")
	private String outtime;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremarks")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "screencode")
	private String screencode;
	@Column(name ="orgid")
	private String orgId;
	@Column(name="branchcode")
	private String branch;
	
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pickRequestVO")
	private List<PickRequestDetailsVO> pickRequestDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
