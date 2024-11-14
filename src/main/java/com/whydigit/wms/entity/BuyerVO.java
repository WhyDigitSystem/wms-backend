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

	@Column(name = "buyer",length =150)
	private String buyer;
	@Column(name = "buyershortname",length =150)
	private String buyerShortName;
	@Column(name = "buyertype",length =25)
	private String buyerType;
	@Column(name = "buyergroupof",length =25)
	private String buyerGroupOf;
	@Column(name = "contactperson",length =150)
	private String contactPerson;
	@Column(name = "panno",length =25)
	private String panNo;
	@Column(name = "tanno",length =25)
	private String tanNo;
	@Column(name = "zipcode",length =10)
	private String zipCode;
	@Column(name = "email",length =150)
	private String email;
	@Column(name = "gst",length =25)
	private String gst;
	@Column(name = "gstno",length =25)
	private String gstNo;
	@Column(name = "mobileno",length =25)
	private String mobileNo;
	@Column(name = "addressline1",length =150)
	private String addressLine1;
	@Column(name = "addressline2",length =150)
	private String addressLine2;
	@Column(name = "city",length =25)
	private String city;
	@Column(name = "state",length =25)
	private String state;
	@Column(name = "country",length =25)
	private String country;
	@Column(name = "eccno",length =25)
	private String eccNo;
	@Column(name = "cbranch",length =10)
	private String cbranch;
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
	@Column(name = "branchcode",length =10)
	private String branchCode;
	@Column(name = "branch",length =25)
	private String branch;
	@Column(name = "client",length =25)
	private String client;
	@Column(name = "customer",length =150)
	private String customer;
	@Column(name = "warehouse",length =25)
	private String warehouse;

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
