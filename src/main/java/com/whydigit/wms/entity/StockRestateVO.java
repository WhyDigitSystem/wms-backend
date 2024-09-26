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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stockrestate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRestateVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stockrestategen")
	@SequenceGenerator(name = "stockrestategen", sequenceName = "stockrestateseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "stockrestateid")
	private Long id;
	@Column(name = "docid",unique = true)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	@Column(name = "transferfrom")
	private String transferFrom;
	@Column(name = "transferto")
	private String transferTo;
	@Column(name = "transferfromflag")
	private String transferFromFlag;
	@Column(name = "transfertoflag")
	private String transferToFlag;
	@Column(name = "entryno")
	private String entryNo;
	@Column(name = "screenname")
	private String screenName = "STOCK RESTATE";
	@Column(name = "screencode")
	private String screenCode ="SRS";
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
	
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
	
	@JsonManagedReference
	@OneToMany(mappedBy = "stockRestateVO", cascade = CascadeType.ALL)
	private List<StockRestateDetailsVO> stockRestateDetailsVO;
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
