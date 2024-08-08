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
@Table(name = "buyerorder")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerOrderVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyerordergen")
	@SequenceGenerator(name = "buyerordergen", sequenceName = "buyerorderseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerorderid")
	private Long id;

	@Column(name = "orderno")
	private String orderno;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "orderdate")
	private LocalDate orderdate;
	@Column(name = "invoiceno")
	private String invoiceno;
	@Column(name = "refno")
	private String refno;
	@Column(name = "invoicedate")
	private LocalDate invoicedate;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "buyershortname")
	private String buyershortname;
	@Column(name = "currency")
	private String currency;
	@Column(name = "exrate")
	private String exrate;
	@Column(name = "location")
	private String location;
	@Column(name = "billto")
	private String billto;
	@Column(name = "tax")
	private String tax;
	@Column(name = "shipto")
	private String shipto;
	@Column(name = "remarks")
	private String remarks;
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
	@Column(name = "cancelremark")
	private String cancelremark;
	@Column(name = "screencode")
	private String screencode;
	

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerOrderVO")
	private List<BuyerOrderDetailsVO> buyerOrderDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
