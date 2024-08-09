package com.whydigit.wms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grngen")
	@SequenceGenerator(name = "grngen", sequenceName = "grnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "grnid")
	private Long id;

	@Column(name = "docid",unique = true)
	private String docId;
	@Builder.Default
	private LocalDate docdate = LocalDate.now();
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "grndate")
	private LocalDate grndDate;
	@Column(name = "gatepassid")
	private String gatePassId;
	@Column(name = "gatepassdate")
	private LocalDate gatePassDate;
	@Column(name = "customerpo")
	private String customerPo;
	@Column(name = "suppliershortname")
	private String supplierShortName;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	@Column(name = "noofpackage")
	private int noOfPackage;
	
	@Builder.Default
	@Column(name = "screencode")
	private String screenCode="GN";
	
	@Builder.Default
	private String screenName="GRN";
	
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	@Builder.Default
	private boolean cancel=false;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	@Builder.Default
	private boolean active=true;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;
	@Column(name = "billofentryno")
	private String billOfEnrtyNo;
	@Column(name = "containerno")
	private String containerNo;
	@Column(name = "fifoflag")
	private String fifoFlag;
	@Column(name = "warehouse")
	private String warehouse;
	@Column(name = "freeze")
	@Builder.Default
	private boolean freeze=false;
	@Column(name = "vas")
	private boolean vas;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "vehicledetails")
	private String vehicleDetails;
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "totalamount")
	private double totalAmount;
	@Column(name = "totalgrnqty")
	private int totalGrnQty;//
	@Column(name = "sealno")
	private String sealNo;
	@Column(name = "vesselno")
	private String vesselNo;
	@Column(name = "hsnno")
	private String hsnNo;
	@Column(name = "securityname")
	private String securityName;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "vessedetails")
	private String vesselDetails;
	@Column(name = "lrno")
	private String lrNo;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "contact")
	private String contact;
	@Column(name = "lrdate")
	private LocalDate lrDate;
	@Column(name = "goodsdescripition")
	private String goodsDescripition;
	@Column(name = "destinatiomfrom")
	private String destinationFrom;
	@Column(name = "destinationto")
	private String destinationTo;
	@Column(name = "noofbins")
	private String noOfBins;
	@Column(name = "invoiceno")
	private String invoiceNo;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	private List<GrnDetailsVO> grnDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
