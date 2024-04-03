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
	@SequenceGenerator(name = "buyergen", sequenceName = "buyerVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "buyerid")
	private Long id;

	@Column(name = "buyer", length = 30)
	private String buyer;

	@Column(name = "buyershortname", length = 30)
	private String buyershortname;

	@Column(name = "buyertype", length = 30)
	private String buyer_type;

	@Column(name = "buyergroupof", length = 30)
	private String buyergroupof;

	@Column(name = "contactperson", length = 30)
	private String contactperson;

	@Column(name = "panno", length = 30)
	private String panno;

	@Column(name = "tanno", length = 30)
	private String tanno;

	@Column(name = "zipcode", length = 30)
	private String zipcode;

	@Column(name = "emailid", length = 30)
	private String emailid;

	@Column(name = "gst", length = 30)
	private String gst;

	@Column(name = "gstno", length = 30)
	private String gstno;

	@Column(name = "mobilenumber1", length = 30)
	private String mobilenumber;

	@Column(name = "addressline1", length = 30)
	private String addressline1;

	@Column(name = "addressline2", length = 30)
	private String addressline2;

	@Column(name = "city", length = 30)
	private String city;

	@Column(name = "state", length = 30)
	private String state;

	@Column(name = "country", length = 30)
	private String country;

	@Column(name = "eccno", length = 30)
	private String eccno;

	@Column(name = "cbranch", length = 30)
	private String cbranch;

	@Column(unique = true)
	private String dupchk;

	@Column(name = "createdby", length = 30)
	private String createdby;

	@Column(name = "modifiedby", length = 30)
	private String updatedby;

	@Column(name = "orgid", length = 30)
	private Long orgId;

	@Column(name = "cancel")
	private boolean cancel;

	@Column(name = "userid", length = 30)
	private String userid;

	@Column(name = "cancelremarks", length = 30)
	private String cancelremark;

	@Column(name = "active")
	private boolean active;

	@Column(name = "branchcode", length = 30)
	private String branchcode;

	@Column(name = "branch", length = 30)
	private String branch;

	@Column(name = "client", length = 30)
	private String client;

	@Column(name = "customer", length = 30)
	private String customer;

	@Column(name = "warehouse", length = 30)
	private String warehouse;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
