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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cyclecount")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleCountVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cyclecountgen")
	@SequenceGenerator(name = "cyclecountgen", sequenceName = "cyclecountseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "cyclecountid")
	private Long id;

	@Column(name = "screenname")
	private String screenName = "CYCLE COUNT";
	@Column(name = "screencode")
	private String screenCode = "CY";
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
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
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemarks;
	@Column(name = "freeze")
	private String freeze;
	@Column(name = "cyclecountno")
	private String cycleCountNo;
	@Column(name = "cyclecountdate")
	private LocalDate cycleCountDate;

	@OneToMany(mappedBy ="cycleCountVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<CycleCountDetailsVO> cycleCountDetailsVO; 
	
	
	
}
