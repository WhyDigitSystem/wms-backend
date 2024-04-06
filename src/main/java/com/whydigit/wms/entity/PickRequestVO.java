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
	@SequenceGenerator(name = "pickrequestgen", sequenceName = "pickrequestVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "pickrequestid")
	private long id;

	@Column(name = "transactiontype", length = 30)
	private String transcationtype;
	@Column(name = "docid", length = 30)
	private String docid;
	@Column(name = "buyerrefno", length = 30)
	private String buyerrefno;
	@Column(name = "docdate", length = 30)
	private LocalDate docdate;
	@Column(name = "shipmentmethod", length = 30)
	private String shipmentmethod;
	@Column(name = "buyerorderno", length = 30)
	private String buyerorderno;
	@Column(name = "buyersreference", length = 30)
	private String buyersreference;
	@Column(name = "invoiceno", length = 30)
	private String invoiceno;
	@Column(name = "clientname", length = 30)
	private String clientname;
	@Column(name = "clientshortname", length = 30)
	private String clientshortname;
	@Column(name = "clientaddress", length = 30)
	private String clientaddress;
	@Column(name = "dispatch", length = 30)
	private String dispatch;
	@Column(name = "customername", length = 30)
	private String customername;
	@Column(name = "customeraddress", length = 30)
	private String customeraddress;
	@Column(name = "duedays", length = 30)
	private String duedays;
	@Column(name = "noofboxes", length = 30)
	private String noofboxes;
	@Column(name = "pickorder", length = 30)
	private String pickorder;
	@Column(name = "outtime", length = 30)
	private String outtime;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "company", length = 30)
	private String company;
	@Column(name = "cancel", length = 30)
	private boolean cancel;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "screencode", length = 30)
	private String screencode;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pickRequestVO")
	private List<PickRequestDetailsVO> pickRequestDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
