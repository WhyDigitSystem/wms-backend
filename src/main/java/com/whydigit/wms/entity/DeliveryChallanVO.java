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
	
	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "buyerorderno",length =150)
	private String buyerOrderNo;
	@Column(name = "pickrequestdate")
	private LocalDate pickReqDate;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "containerno",length =25)
	private String containerNO;
	@Column(name = "vechileno",length =25)
	private String vechileNo;
	@Column(name = "exciseinvoiceno",length =25)
	private String exciseInvoiceNo;
	@Column(name = "commercialinvoiceno",length =25)
	private String commercialInvoiceNo;
	@Column(name = "bodate")
	private LocalDate boDate;
	@Column(name = "buyer")
	private String buyer;
	@Column(name = "deliveryterms",length =250)
	private String deliveryTerms;
	@Column(name = "payterms",length =250)
	private String payTerms;
	@Column(name = "grwaiverno",length =25)
	private String grWaiverNo;
	@Column(name = "grwaiverdate")
	private LocalDate grWaiverDate;
	@Column(name = "bankname",length =25)
	private String bankName;
	@Column(name = "grwaiverclosuredate")
	private LocalDate grWaiverClosureDate;
	@Column(name = "gatepassno",length =25)
	private String gatePassNo;
	@Column(name = "gatepassdate")
	private LocalDate gatePassDate;
	@Column(name = "insuranceno",length =25)
	private String insuranceNo;
	@Column(name = "billto",length =150)
	private String billTo;
	@Column(name = "shipto",length =150)
	private String shipTo;
	@Column(name = "automailergroup",length =25)
	private String automailerGroup;
	@Column(name = "docketno",length =25)
	private String docketNo;
	@Column(name = "noofboxes",length =25)
	private String noOfBoxes;
	@Column(name = "pkguom",length =25)
	private String pkgUom;
	@Column(name = "grossweight")
	private String grossWeight;
	@Column(name = "gwtuom",length =25)
	private String gwtUom;
	@Column(name = "transportname",length =150)
	private String transportName;
	@Column(name = "transporterdate")
	private LocalDate transporterDate;
	@Column(name = "packingslipno",length =25)
	private String packingSlipNo;
	@Column(name = "bin",length =25)
	private String bin;
	@Column(name = "taxtype",length =25)
	private String taxType;
	@Column(name = "remarks",length =150)
	private String remarks;
	
	@Column(name = "screenname",length =25)
	private String screenName ="DeliveryChallan" ;
	@Column(name = "screencode",length =10)
	private String screenCode ="DC";
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active = true;
	@Column(name = "cancel")
	private boolean cancel = false;
	@Column(name = "cancelremarks",length =150)
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
