package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String branch;
	private String branchcode;
	private Long orgId;
	private String addressline1;
	private String addressline2;
	private String pan;
	private String gstin;
	private String state;
	private String city;
	private String pincode;
	private String country;
	private String region;
	private String lccurrency;
	private boolean cancel;
	private String cancelremarks;
	private String createdby;
	private String updatedby;
	@Column(unique = true)
	private String dupchk;
	private boolean active;
	private String userid;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
