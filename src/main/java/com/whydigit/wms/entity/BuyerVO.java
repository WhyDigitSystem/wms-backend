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
@Table(name = "buyer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long buyerid;
	private String buyername;
	private String buyershortname;
	private String buyertype;
	private String groupof;
	private String contactperson;
	private String landlinenumber;
	private String panno;
	private String tanno;
	private String zipcode;
	private String emailid;
	private String gst;
	private String gstno;
	private String mobilenumber;
	private String addressline1;
	private String addressline2;
	private String city;
	private String state;
	private String country;
	private String eccno;
	private String range;
	private String rangeaddress;
	private String rangeaddress1;
	private String division;
	private String commissionerate;
	private String controlbranch;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String updatedby;
	private String company;
	private boolean cancel;
	private String userid;
	private String cancelremark;
	private boolean active;
	private String branchcode;
	private String branchname;
	private String client;
	private String customer;
	private String warehouse;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
