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
@Table(name = "supplier")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String supplier;
	private String suppliershortname;
	private String suppliertype;
	private String suppliergroupof;
	private String category;
	private String panno;
	private String tanno;
	private String contactperson;
	private String landlinenumber;
	private String mobilenumber;
	private String addressline1;
	private String addressline2;
	private String city;
	private String cbranch;
	private String state;
	private String country;
	private String zipcode;
	private String emailid;
	private String eccno;
	private String rangeaddress;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private Long orgId;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String branchcode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
