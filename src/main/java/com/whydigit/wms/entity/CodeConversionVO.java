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
@Table(name = "codeconversion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeConversionVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "codeconversiongen")
	@SequenceGenerator(name = "codeconversiongen", sequenceName = "codeconversionseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "codeconversionid")
	private Long id;
	
	@Column(name = "docid",unique = true)
	private String docId;
	@Column(name = "docdate")
	private LocalDate docDate = LocalDate.now();
	
	@Column(name = "screenname")
	private String screenName ="CODE CONVERSION" ;
	@Column(name = "screencode")
	private String screenCode ="CC";
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
	@OneToMany(mappedBy = "codeConversionVO", cascade = CascadeType.ALL)
	private List<CodeConversionDetailsVO> codeConversionDetailsVO;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
