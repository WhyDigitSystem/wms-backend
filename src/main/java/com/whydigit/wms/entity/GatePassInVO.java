package com.whydigit.wms.entity;

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

	@Column(name = "transactiontype")
	private String transactionType;
	@Column(name = "entryno")
	private String entryNo;//
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "docid")
	private String docId;
	@Column(name = "docdate")
	private LocalDate docdate = LocalDate.now();
	@Column(name = "date")
	private LocalDate date;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "suppliershortname")
	private String supplierShortName;
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "carriertransport")
	private String carrierTransport;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "contact")
	private String contact;
	@Column(name = "goodsdescription")
	private String goodsDescription;
	@Column(name = "securityname")
	private String securityName;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "company")
	private String company;

	@Column(name = "cancel")
	private boolean cancel=false;

	@Column(name = "cancelremarks")
	private String cancelRemark;

	@Column(name = "active")
	private boolean active=true;

	@Column(name = "branchcode")
	private String branchCode;

	@Column(name = "branch")
	private String branch;

	@Column(name = "screencode")
	private String screenCode="GP";

	@Column(name = "client")
	private String client;

	@Column(name = "customer")
	private String customer;

	@Column(name = "screenname")
	private String screenName="GATEPASSIN";
	@Column(name = "finyear")
	private String finYear;
	@Column(name = "freeze")
	private boolean freeze=false;


	@JsonManagedReference
	@OneToMany(mappedBy = "gatePassInVO", cascade = CascadeType.ALL)
	private List<GatePassInDetailsVO> gatePassDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	
}
