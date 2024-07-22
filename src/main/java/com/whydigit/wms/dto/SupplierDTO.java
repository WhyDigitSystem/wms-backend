package com.whydigit.wms.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
	
	private Long id;
	private String supplier;
	private String supplierShortName;
	private String supplierType;
	private String supplierGroupOf;
	private String category;
	private String panNo;
	private String tanNo;
	private String contactPerson;
	private String landLineNo;
	private String mobileNo;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String cbranch;
	private String state;
	private String country;
	private String zipCode;
	private String email;
	private String eccNo;
	private String rangeAddress;
	private String createdBy;
	private Long orgId;
	private boolean active;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;

}
