package com.whydigit.wms.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonGetter;
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

	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "branchode",length =25)
	private String branchCode;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "addressline1",length =150)
	private String addressLine1;
	@Column(name = "addressline2",length =150)
	private String addressLine2;
	@Column(name = "panno",length =25)
	private String pan;
	@Column(name = "gstin",length =25)
	private String gstIn;
	@Column(name = "phone",length =25)
	private String phone;
	@Column(name = "state",length =25)
	private String state;
	@Column(name = "city",length =25)
	private String city;
	@Column(name = "pincode",length =25)
	private String pinCode;
	@Column(name = "country",length =25)
	private String country;
	@Column(name = "stateno",length =10)
	private String stateNo;
	@Column(name = "statecode",length =10)
	private String stateCode;
	@Column(name = "region",length =25)
	private String region;
	@Column(name = "lccurrency",length =25)
	private String lccurrency;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemarks;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "active")
	private boolean active;

	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}

	// Optionally, if you want to control serialization for 'cancel' field similarly
	@JsonGetter("cancel")
	public String getCancel() {
		return cancel ? "T" : "F";
	}
	
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
