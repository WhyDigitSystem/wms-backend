package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.whydigit.wms.dto.CreatedUpdatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branchgen")
	@SequenceGenerator(name = "branchgen", sequenceName = "brancseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "branchid")
	private Long id;

	@Column(name = "branch")
	private String branch;
	@Column(name = "branchcode")
	private String branchcode;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "addressline1")
	private String addressline1;
	@Column(name = "addressline2")
	private String addressline2;
	@Column(name = "panno")
	private String pan;
	@Column(name = "gstin")
	private String gstin;
	@Column(name = "phone")
	private String phone;
	@Column(name = "state")
	private String state;
	@Column(name = "city")
	private String city;
	@Column(name = "pincode")
	private String pincode;
	@Column(name = "country")
	private String country;
	@Column(name = "stateno")
	private String stateno;
	@Column(name = "statecode")
	private String statecode;
	@Column(name = "region")
	private String region;
	@Column(name = "lccurrency")
	private String lccurrency;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelremarks;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "active")
	private boolean active;
	@Column(name = "userid")
	private String userid;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
