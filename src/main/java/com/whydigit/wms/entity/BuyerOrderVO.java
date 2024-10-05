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
    @Column(name ="docid",unique = true,length =25)
	private String docId;
	@Column(name = "orderno",length =25)
	private String orderNo;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name = "orderdate")
	private LocalDate orderDate;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "refno",length =25)
	private String refNo;
	@Column(name = "invoicedate")
	private LocalDate invoiceDate;
	@Column(name = "refdate")
	private LocalDate refDate;
	@Column(name = "buyershortname",length =150)
	private String buyerShortName;
	@Column(name = "buyer",length =150)
	private String buyer;
	@Column(name = "buyeraddress",length =150)
	private String buyerAddress;
	@Column(name = "currency",length =25)
	private String currency;
	@Column(name = "billtoshortname",length =150)
	private String billToShortName;
	@Column(name = "billto",length =150)
	private String billToName;
	@Column(name = "billtoaddress",length =150)
	private String billToAddress;
	@Column(name = "shiptoshortname",length =150)
	private String shipToShortName;
	@Column(name = "shipto",length =150)
	private String shipToName;
	@Column(name = "remarks",length =150)
	private String remarks;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancelremark",length =150)
	private String cancelRemark;
	@Column(name = "screencode",length =10)
	private String screenCode="BO";
	@Column(name ="screenname",length =25)
	private String screenName="BUYER ORDER";
	@Column(name ="customer",length =150)
	private String customer;
	@Column(name ="client",length =150)
	private String client;
	@Column(name ="finyear",length =10)
	private String finYear;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =10)
	private String branchCode;
	@Column(name = "freeze")
	private boolean freeze=false;
	@Column(name = "totalorderqty")
	private int totalOrderQty;
	@Column(name = "totalavlqty")
	private int totalAvailQty;
	private boolean active = true;
	@Column(name = "warehouse",length =25)
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
