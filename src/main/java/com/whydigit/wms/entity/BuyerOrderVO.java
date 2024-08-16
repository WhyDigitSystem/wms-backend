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
    @Column(name ="docid")
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
	@Column(name = "currency")
	private String currency;
	@Column(name = "exrate")
	private int exRate;
	@Column(name = "bin")
	private String bin;
	@Column(name = "billto")
	private String billto;
	@Column(name = "tax")
	private String tax;
	@Column(name = "shipto")
	private String shipTo;
	@Column(name = "remarks")
	private String reMarks;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "company")
	private String company;
	@Column(name = "cancel")
	private boolean cancel=false;
	@Column(name = "cancelremark")
	private String cancelRemark;
	@Column(name = "screencode")
	private String screenCode;
	@Column(name ="screenname")
	private String screenName;
	private String customer;
	private String client;
	@Column(name ="finyear")
	private String finYear;
	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "freeze")
	private boolean freeze=true;
	@Column(name = "orderqty")
	private int orderQty;
	@Column(name = "avilqty")
	private int avilQty;
	private boolean active = true;
	private String buyer;
	
	

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyerOrderVO")
	private List<BuyerOrderDetailsVO> buyerOrderDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
