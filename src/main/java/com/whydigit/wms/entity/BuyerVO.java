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
@Table(name = "buyer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerVO {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "buyergen")
	@SequenceGenerator(name = "buyergen", sequenceName = "buyerseq", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerid")
	private Long id;

	@Column(name = "buyer")
	private String buyer;
	@Column(name = "buyershortname")
	private String buyerShortName;
	@Column(name = "buyertype")
	private String buyerType;
	@Column(name = "buyergroupof")
	private String buyerGroupOf;
	@Column(name = "contactperson")
	private String contactPerson;
	@Column(name = "panno")
	private String panNo;
	@Column(name = "tanno")
	private String tanNo;
	@Column(name = "zipcode")
	private String zipCode;
	@Column(name = "email")
	private String email;
	@Column(name = "gst")
	private String gst;
	@Column(name = "gstno")
	private String gstNo;
	@Column(name = "mobileno")
	private String mobileNo;
	@Column(name = "addressline1")
	private String addressLine1;
	@Column(name = "addressline2")
	private String addressLine2;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "eccno")
	private String eccNo;
	@Column(name = "cbranch")
	private String cbranch;
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
