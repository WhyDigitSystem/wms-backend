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

import com.fasterxml.jackson.annotation.JsonGetter;
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
    @Column(name ="docid",unique = true)
	private String docId;
	@Column(name = "orderno")
	private String orderNo;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name = "orderdate")
	private LocalDate orderDate;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "refno")
	private String refNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "buyershortname")
	private String buyerShortName;
	private String buyer;
	@Column(name = "buyeraddress")
	private String buyerAddress;
	@Column(name = "currency")
	private String currency;
	@Column(name = "billtoshortname")
	private String billToShortName;
	@Column(name = "billto")
	private String billToName;
	@Column(name = "billtoaddress")
	private String billToAddress;
	@Column(name = "shiptoshortname")
	private String shipToShortName;
	@Column(name = "shipto")
	private String shipToName;
	@Column(name = "remarks")
	private String remarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancelremark")
	private String cancelRemark;
	@Column(name = "screencode")
	private String screenCode="BO";
	@Column(name ="screenname")
	private String screenName="BUYER ORDER";
	private String customer;
	private String client;
	@Column(name ="finyear")
	private String finYear;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "freeze")
	private boolean freeze=false;
	@Column(name = "totalorderqty")
	private int totalOrderQty;
	@Column(name = "totalavlqty")
	private int totalAvailQty;
	private boolean active = true;
	@Column(name = "warehouse")
	private String warehouse;
	

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerOrderVO")
	private List<BuyerOrderDetailsVO> buyerOrderDetailsVO;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
