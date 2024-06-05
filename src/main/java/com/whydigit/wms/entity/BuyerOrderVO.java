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
	@SequenceGenerator(name = "buyerordergen", sequenceName = "buyerorderVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerorderid")
	private Long id;

	@Column(name = "orderno", length = 30)
	private String orderno;
	@Column(name = "docdate")
	private LocalDate docdate;
	@Column(name = "orderdate")
	private LocalDate orderdate;
	@Column(name = "invoiceno", length = 30)
	private String invoiceno;
	@Column(name = "refno", length = 30)
	private String refno;
	@Column(name = "invoicedate")
	private LocalDate invoicedate;
	@Column(name = "refdate")
	private LocalDate refdate;
	@Column(name = "buyershortname", length = 30)
	private String buyershortname;
	@Column(name = "currency", length = 30)
	private String currency;
	@Column(name = "exrate", length = 30)
	private String exrate;
	@Column(name = "location", length = 30)
	private String location;
	@Column(name = "billto", length = 30)
	private String billto;
	@Column(name = "tax", length = 30)
	private String tax;
	@Column(name = "shipto", length = 30)
	private String shipto;
	@Column(name = "remarks", length = 30)
	private String remarks;
	@Column(unique = true, length = 30)
	private String dupchk;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "company", length = 30)
	private String company;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "cancelremark")
	private String cancelremark;
	@Column(name = "screencode", length = 30)
	private String screencode;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerOrderVO")
	private List<BuyerOrderDetailsVO> buyerOrderDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
