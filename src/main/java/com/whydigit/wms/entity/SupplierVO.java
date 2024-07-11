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
	private String suppliershortname;
	@Column(name = "suppliertype")
	private String suppliertype;
	@Column(name = "suppliergroupof")
	private String suppliergroupof;
	@Column(name = "category")
	private String category;
	@Column(name = "panno")
	private String panno;
	@Column(name = "tanno")
	private String tanno;
	@Column(name = "contactperson")
	private String contactperson;
	@Column(name = "landlinenumber")
	private String landlinenumber;
	@Column(name = "mobilenumber")
	private String mobilenumber;
	@Column(name = "addressline1")
	private String addressline1;
	@Column(name = "addressline2")
	private String addressline2;
	@Column(name = "city")
	private String city;
	@Column(name = "cbranch")
	private String cbranch;
	@Column(name = "state")
	private String state;
	@Column(name = "country")
	private String country;
	@Column(name = "zipcode")
	private String zipcode;
	@Column(name = "emailid")
	private String emailid;
	@Column(name = "eccno")
	private String eccno;
	@Column(name = "rangeaddress")
	private String rangeaddress;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "createdby")
	private String createdby;
	@Column(name = "modifiedby")
	private String updatedby;
	@Column(name = "orgid")
	private Long orgId;
	@Column(name = "cancel")
	private boolean cancel;
	@Column(name = "userid")
	private String userid;
	@Column(name = "cancelremarks")
	private String cancelremark;
	@Column(name = "active")
	private boolean active;
	@Column(name = "branchcode")
	private String branchcode;
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
