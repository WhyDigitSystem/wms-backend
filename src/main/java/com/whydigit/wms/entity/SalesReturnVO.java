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
@Table(name = "salesreturn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "salesreturngen")
	@SequenceGenerator(name = "salesreturngen", sequenceName = "salesreturnseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "salesreturnid")
	private Long id;
	@Column(name = "transactiontype")
	private String transactionType;
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "prdate")
	private LocalDate prDate;
	@Column(name = "bono")
	private String BONo;
	@Column(name = "bodate")
	private LocalDate BODate;
	@Column(name = "prno")
	private String PRNo;
	@Column(name = "buyername")
	private String buyerName;
	@Column(name = "buyertype")
	private String buyerType;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "contact")
	private String contact;
	@Column(name = "securitypersonname")
	private String securityPersonName;
	@Column(name = "timein")
	private String timeIn;
	@Column(name = "timeout")
	private String timeOut;
	@Column(name = "briefdescofgoods")
	private String briefDescOfGoods;
	@Column(name = "totalreturnqty")
	private int totalReturnQty;

	@Column(name = "screenname")
	private String screenName ="SALES RETURN";
	@Column(name = "screencode")
	private String screenCode = "SR";
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docid", unique = true)
	private String docId;
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

	

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@OneToMany(mappedBy = "salesReturnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesReturnDetailsVO> salesReturnDetailsVO;

}
