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
@Table(name = "deliverychallan")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeliveryChallanVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverychallangen")
	@SequenceGenerator(name = "deliverychallangen", sequenceName = "deliverychallanseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "deliverychallanid")
	private Long id;
	
	@Column(name = "docid",unique = true)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "buyerorderno")
	private String buyerOrderNo;
	@Column(name = "pickrequestdate")
	private LocalDate pickReqDate;
	@Column(name = "invoiceno")
	private String invoiceNo;
	@Column(name = "containerno")
	private String containerNO;
	@Column(name = "vechileno")
	private String vechileNo;
	@Column(name = "exciseinvoiceno")
	private String exciseInvoiceNo;
	@Column(name = "commercialinvoiceno")
	private String commercialInvoiceNo;
	@Column(name = "bodate")
	private LocalDate boDate;
	@Column(name = "buyer")
	private String buyer;
	@Column(name = "deliveryterms")
	private String deliveryTerms;
	@Column(name = "payterms")
	private String payTerms;
	@Column(name = "grwaiverno")
	private String grWaiverNo;
	@Column(name = "grwaiverdate")
	private LocalDate grWaiverDate;
	@Column(name = "bankname")
	private String bankName;
	@Column(name = "grwaiverclosuredate")
	private LocalDate grWaiverClosureDate;
	@Column(name = "gatepassno")
	private String gatePassNo;
	@Column(name = "gatepassdate")
	private LocalDate gatePassDate;
	@Column(name = "insuranceno")
	private String insuranceNo;
	@Column(name = "billto")
	private String billTo;
	@Column(name = "shipto")
	private String shipTo;
	@Column(name = "automailergroup")
	private String automailerGroup;
	@Column(name = "docketno")
	private String docketNo;
	@Column(name = "noofboxes")
	private String noOfBoxes;
	@Column(name = "pkguom")
	private String pkgUom;
	@Column(name = "grossweight")
	private String grossWeight;
	@Column(name = "gwtuom")
	private String gwtUom;
	@Column(name = "transportname")
	private String transportName;
	@Column(name = "transporterdate")
	private LocalDate transporterDate;
	@Column(name = "packingslipno")
	private String packingSlipNo;
	@Column(name = "bin")
	private String bin;
	@Column(name = "taxtype")
	private String taxType;
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "screenname")
	private String screenName ="DeliveryChallan" ;
	@Column(name = "screencode")
	private String screenCode ="DC";
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

	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonManagedReference
	@OneToMany(mappedBy = "deliveryChallanVO", cascade = CascadeType.ALL)
	private List<DeliveryChallanDetailsVO> deliveryChallanDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
