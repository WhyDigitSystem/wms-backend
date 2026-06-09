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
	@Column(name = "grnno",length =25)
	private String grnNo;
	@Column(name = "docid",unique = true,length =25)
	private String docId;
	@Column(name = "grndate")
	private LocalDate grnDate;
	@Column(name = "entryno",length =25)
	private String entryNo;
	@Column(name = "entrydate")
	private LocalDate entryDate;
	@Column(name = "core",length =25)
	private String core;
	@Column(name = "suppliershortname",length =150)
	private String supplierShortName;
	@Column(name = "supplier",length =150)
	private String supplier;
	@Column(name = "modeofshipment",length =25)
	private String modeOfShipment;
	@Column(name = "carrier",length =150)
	private String carrier;
	@Column(name = "bintype",length =25)
	private String binType;
	@Column(name = "contact",length =25)
	private String contact;
	@Column(name = "vehicletype",length =25)
	private String vehicleType;
	@Column(name = "vehicleno",length =25)
	private String vehicleNo;
	@Column(name = "drivername",length =25)
	private String driverName;
	@Column(name = "status",length =25)
	private String status;
	@Column(name = "lotno",length =25)
	private String lotNo;
	@Column(name = "enteredperson",length =25)
	private String enteredPerson;
	@Column(name = "binclass",length =25)
	private String binClass;
	@Column(name = "binpick",length =25)
	private String binPick;
	@Column(name = "totalgrnqty")
	private int totalGrnQty;
	@Column(name = "totalputawayqty")
	private int totalPutawayQty;
	@Column(name = "screenname",length =25)
	private String screenName = "PUTAWAY";
	@Column(name = "screencode",length =25)
	private String screenCode ="PC";
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =25)
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
