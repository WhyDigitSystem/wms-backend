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
@Table(name = "gatepassin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GatePassInVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gatepassingen")
	@SequenceGenerator(name = "gatepassingen", sequenceName = "gatepassinseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "gatepassinid")
	private Long id;

	@Column(name = "transactiontype",length =25)
	private String transactionType;
	@Column(name = "entryno",length =25)
	private String entryNo;//
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docdate = LocalDate.now();
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "supplier",length =150)
	private String supplier;
	@Column(name = "suppliershortname",length =150)
	private String supplierShortName;
	@Column(name = "modeofshipment",length =25)
	private String modeOfShipment;
	@Column(name = "carrier",length =150)
	private String carrier;
	@Column(name = "carriertransport",length =150)
	private String carrierTransport;
	@Column(name = "vehicletype",length =25)
	private String vehicleType;
	@Column(name = "vehicleno",length =25)
	private String vehicleNo;
	@Column(name = "drivername",length =25)
	private String driverName;
	@Column(name = "contact")
	private String contact;
	@Column(name = "goodsdescription",length =150)
	private String goodsDescription;
	@Column(name = "securityname",length =25)
	private String securityName;
	@Column(name = "lotno",length =25)
	private String lotNo;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "company",length =25)
	private String company;

	@Column(name = "cancel")
	private boolean cancel=false;

	@Column(name = "cancelremarks",length =150)
	private String cancelRemark;

	@Column(name = "active")
	private boolean active=true;

	@Column(name = "branchcode",length =25)
	private String branchCode;

	@Column(name = "branch",length =25)
	private String branch;

	@Column(name = "screencode",length =10)
	private String screenCode="GP";

	@Column(name = "client",length =150)
	private String client;

	@Column(name = "customer",length =150)
	private String customer;

	@Column(name = "screenname",length =25)
	private String screenName="GATEPASSIN";
	@Column(name = "finyear",length =10)
	private String finYear;
	@Column(name = "freeze")
	private boolean freeze=false;


	@JsonManagedReference
	@OneToMany(mappedBy = "gatePassInVO", cascade = CascadeType.ALL)
	private List<GatePassInDetailsVO> gatePassDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}
