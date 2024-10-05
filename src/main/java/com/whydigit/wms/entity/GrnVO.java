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

	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	@Builder.Default
	private LocalDate docDate = LocalDate.now();
	@Column(name = "entryno",length =25)
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "gatepassid",length =25)
	private String gatePassId;
	@Column(name = "gatepassdate")
	private LocalDate gatePassDate;
	@Column(name = "customerpo",length =25)
	private String customerPo;
	@Column(name = "suppliershortname",length =150)
	private String supplierShortName;
	@Column(name = "supplier",length =150)
	private String supplier;
	@Column(name = "carrier",length =150)
	private String carrier;
	@Column(name = "lotno",length =25)
	private String lotNo;
	@Column(name = "modeofshipment",length =25)
	private String modeOfShipment;
	@Column(name = "noofpackage")
	private int noOfPackage;
	
	@Builder.Default
	@Column(name = "screencode",length =10)
	private String screenCode="GN";
	
	@Builder.Default
	private String screenName="GRN";
	
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	@Builder.Default
	private boolean cancel=false;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemark;
	@Column(name = "active")
	@Builder.Default
	private boolean active=true;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "billofentryno",length =25)
	private String billOfEnrtyNo;
	@Column(name = "containerno",length =25)
	private String containerNo;
	@Column(name = "fifoflag",length =25)
	private String fifoFlag;
	@Column(name = "warehouse",length =25)
	private String warehouse;
	@Column(name = "freeze")
	private boolean freeze=false;
	@Column(name = "vas")
	private boolean vas;
	@Column(name = "vehicleno",length =25)
	private String vehicleNo;
	@Column(name = "vehicledetails",length =150)
	private String vehicleDetails;
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "totalamount")
	private double totalAmount;
	@Column(name = "totalgrnqty")
	private int totalGrnQty;//
	@Column(name = "sealno",length =25)
	private String sealNo;
	@Column(name = "vesselno",length =25)
	private String vesselNo;
	@Column(name = "hsnno",length =25)
	private String hsnNo;
	@Column(name = "securityname",length =25)
	private String securityName;
	@Column(name = "vehicletype",length =25)
	private String vehicleType;
	@Column(name = "vessedetails",length =150)
	private String vesselDetails;
	@Column(name = "lrno",length =25)
	private String lrNo;
	@Column(name = "drivername",length =25)
	private String driverName;
	@Column(name = "contact",length =25)
	private String contact;
	@Column(name = "lrdate")
	private LocalDate lrDate;
	@Column(name = "goodsdescripition",length =150)
	private String goodsDescripition;
	@Column(name = "destinatiomfrom",length =150)
	private String destinationFrom;
	@Column(name = "destinationto",length =150)
	private String destinationTo;
	@Column(name = "noofbins",length =25)
	private String noOfBins;
	@Column(name = "invoiceno",length =25)
	private String invoiceNo;
	@Column(name = "remarks",length =150)
	private String remarks;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "grnVO", cascade = CascadeType.ALL)
	private List<GrnDetailsVO> grnDetailsVO;

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
