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

	@Column(name = "supplier")
	private String supplier;
	@Column(name = "suppliershortname")
	private String supplierShortName;
	@Column(name = "suppliertype")
	private String supplierType;
	@Column(name = "suppliergroupof")
	private String supplierGroupOf;
	@Column(name = "category")
	private String category;
	@Column(name = "panno")
	private String panNo;
	@Column(name = "tanno")
	private String tanNo;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "landlinenumber")
	private String landLineNo;
	@Column(name = "mobileno")
	private String mobileNo;
	@Column(name = "addressline1")
	private String addressLine1;
	@Column(name = "addressline2")
	private String addressLine2;
	@Column(name = "city")
	private String city;
	@Column(name = "cbranch")
	private String cbranch;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "zipcode")
	private String zipCode;
	@Column(name = "email")
	private String email;
	@Column(name = "eccno")
	private String eccNo;
	@Column(name = "rangeaddress")
	private String rangeAddress;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "modifiedby")
	private String updatedBy;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "cancelremarks")
	private String cancelRemark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode")
	private String branchCode;
	@Column(name = "branch")
	private String branch;
	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;
	@Column(name = "warehouse")
	private String warehouse;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
