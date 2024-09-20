package com.whydigit.wms.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locationmovement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMovementVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationmovementgen")
	@SequenceGenerator(name = "locationmovementgen", sequenceName = "locationmovementseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "locationmovementid")
	private Long id;
	@Column(name = "screenname")
	private String screenName ="LOCATION MOVEMENT";
	@Column(name = "screencode")
	private String screenCode = "LM";
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "docid",unique = true)
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
	@Column(name="movedqty")
	private Integer movedQty;
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "freeze")
	private boolean freeze = true;
	
	
	@OneToMany(mappedBy = "locationMovementVO",cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<LocationMovementDetailsVO> locationMovementDetailsVO;
}
