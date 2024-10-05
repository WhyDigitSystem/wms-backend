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
	@Column(name = "transactiontype",length =25)
	private String transactionType;
	@Column(name = "entryno",length =25)
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "prdate")
	private LocalDate prDate;
	@Column(name = "bono",length =25)
	private String boNo;
	@Column(name = "bodate")
	private LocalDate boDate;
	@Column(name = "prno",length =25)
	private String prNo;
	@Column(name = "buyername",length =150)
	private String buyerName;
	@Column(name = "buyertype",length =25)
	private String buyerType;
	@Column(name = "supplier",length =150)
	private String supplier;
	@Column(name = "drivername",length =150)
	private String driverName;
	@Column(name = "carrier",length =150)
	private String carrier;
	@Column(name = "modeofshipment",length =25)
	private String modeOfShipment;
	@Column(name = "vehicletype",length =25)
	private String vehicleType;
	@Column(name = "vehicleno",length =25)
	private String vehicleNo;
	@Column(name = "contact",length =25)
	private String contact;
	@Column(name = "securitypersonname",length =25)
	private String securityPersonName;
	@Column(name = "timein")
	private String timeIn;
	@Column(name = "timeout")
	private String timeOut;
	@Column(name = "briefdescofgoods",length =25)
	private String briefDescOfGoods;
	@Column(name = "totalreturnqty")
	private int totalReturnQty;

	@Column(name = "screenname",length =25)
	private String screenName ="SALES RETURN";
	@Column(name = "screencode",length =10)
	private String screenCode = "SR";
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docid", unique = true)
	private String docId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length=150)
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
	private boolean freeze;
	

	

	@Embedded
	@Builder.Default
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();

	@OneToMany(mappedBy = "salesReturnVO", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<SalesReturnDetailsVO> salesReturnDetailsVO;

}
