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
	@SequenceGenerator(name = "branchgen", sequenceName = "branchVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "branchid")
	private Long id;

	@Column(name = "branch", length = 30)
	private String branch;

	@Column(name = "branchcode", length = 30)
	private String branchcode;

	@Column(name = "orgid", length = 30)
	private Long orgId;

	@Column(name = "addressline1", length = 30)
	private String addressline1;

	@Column(name = "addressline2", length = 30)
	private String addressline2;

	@Column(name = "panno", length = 30)
	private String pan;

	@Column(name = "gstin", length = 30)
	private String gstin;

	@Column(name = "phone", length = 30)
	private String phone; 
	
	@Column(name = "state", length = 30)
	private String state;

	@Column(name = "city", length = 30)
	private String city;

	@Column(name = "pincode", length = 30)
	private String pincode;

	@Column(name = "country", length = 30)
	private String country;

	@Column(name = "stateno", length = 30)
	private String stateno;

	@Column(name = "statecode", length = 30)
	private String statecode;

	@Column(name = "region", length = 30)
	private String region;

	@Column(name = "lccurrency", length = 30)
	private String lccurrency;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremarks;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "active")
	private boolean active;

	@Column(name = "userid", length = 30)
	private String userid;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
