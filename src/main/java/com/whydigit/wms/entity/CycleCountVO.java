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

	@Column(name = "screenname",length =25)
	private String screenName = "CYCLE COUNT";
	@Column(name = "screencode",length =10)
	private String screenCode = "CY";
	@Column(name = "docdate")
	private LocalDate docDate=LocalDate.now();
	@Column(name = "docid", unique = true,length =25)
	private String docId;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "client",length =150)
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
	private boolean active;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "freeze")
	private Boolean freeze=true;
	@Column(name = "stockstatus",length =25)
	private String stockStatus;
	@Column(name = "statusflag")
	private String statusFlag;

	@Column(name = "stockdate")
	private LocalDate stockDate=LocalDate.now();

	@OneToMany(mappedBy ="cycleCountVO",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<CycleCountDetailsVO> cycleCountDetailsVO; 
	
	
	
}
