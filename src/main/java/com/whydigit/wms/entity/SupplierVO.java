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
@Table(name = "supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suppliergen")
	@SequenceGenerator(name = "suppliergen", sequenceName = "supplierseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "supplierid")
	private Long id;
	@Column(name = "supplier",length =150)
	private String supplier;
	@Column(name = "suppliershortname",length =25)
	private String supplierShortName;
	@Column(name = "suppliertype",length =25)
	private String supplierType;
	@Column(name = "suppliergroupof",length =25)
	private String supplierGroupOf;
	@Column(name = "category",length =25)
	private String category;
	@Column(name = "panno",length =25)
	private String panNo;
	@Column(name = "tanno",length =25)
	private String tanNo;
	@Column(name = "contactperson",length =150)
	private String contactPerson;
	@Column(name = "landlinenumber",length =25)
	private String landLineNo;
	@Column(name = "mobileno",length =25)
	private String mobileNo;
	@Column(name = "addressline1",length =150)
	private String addressLine1;
	@Column(name = "addressline2",length =150)
	private String addressLine2;
	@Column(name = "city",length =25)
	private String city;
	@Column(name = "cbranch",length =25)
	private String cbranch;
	@Column(name = "state",length =25)
	private String state;
	@Column(name = "country",length =25)
	private String country;
	@Column(name = "zipcode",length =10)
	private String zipCode;
	@Column(name = "email",length =25)
	private String email;
	@Column(name = "eccno",length =25)
	private String eccNo;
	@Column(name = "rangeaddress",length =150)
	private String rangeAddress;
	@Column(name = "createdby",length =25)
	private String createdBy;
	@Column(name = "modifiedby",length =25)
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks",length =150)
	private String cancelRemark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode",length =25)
	private String branchCode;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "client",length =150)
	private String client;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "warehouse",length =25)
	private String warehouse;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	
	@JsonGetter("active")
	public String getActive() {
		return active ? "Active" : "In-Active";
	}
}
