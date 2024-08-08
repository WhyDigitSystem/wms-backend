package com.whydigit.wms.dto;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class BuyerDTO {
	
	
	private Long id;
	private String buyer;
	private String buyerShortName;
	private String buyerType;
	private String buyerGroupOf;
	private String contactPerson;
	private String panNo;
	private String tanNo;
	private String zipCode;
	private String email;
	private String gst;
	private String gstNo;
	private String mobileNo;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String country;
	private String eccNo;
	private String cbranch;
	private String createdBy;
	private Long orgId;
	private boolean active;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;
}
