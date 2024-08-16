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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "putaway")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "putawaygen")
	@SequenceGenerator(name = "putawaygen", sequenceName = "putawayseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "putawayid")
	private Long id;
	@Column(name = "docdate")
	private LocalDate docDate= LocalDate.now();
	@Column(name = "grnno")
	private String grnNo;
	@Column(name = "docid",unique = true)
	private String docId;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "core")
	private String core;
	@Column(name = "suppliershortname")
	private String supplierShortName;
	@Column(name = "supplier")
	private String supplier;
	@Column(name = "modeofshipment")
	private String modeOfShipment;
	@Column(name = "carrier")
	private String carrier;
	@Column(name = "bintype")
	private String binType;
	@Column(name = "contact")
	private String contact;
	@Column(name = "vehicletype")
	private String vehicleType;
	@Column(name = "vehicleno")
	private String vehicleNo;
	@Column(name = "drivername")
	private String driverName;
	@Column(name = "status")
	private String status;
	@Column(name = "lotno")
	private String lotNo;
	@Column(name = "enteredperson")
	private String enteredPerson;
	@Column(name = "binclass")
	private String binClass;
	@Column(name = "binpick")
	private String binPick;
	@Column(name = "totalgrnqty")
	private int totalGrnQty;
	@Column(name = "totalputawayqty")
	private int totalPutawayQty;
	@Column(name = "screenname")
	private String screenName = "PUTAWAY";
	@Column(name = "screencode")
	private String screenCode ="PC";
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
	private boolean freeze;
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonManagedReference
	@OneToMany(mappedBy = "putAwayVO", cascade = CascadeType.ALL)
	private List<PutAwayDetailsVO> putAwayDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
