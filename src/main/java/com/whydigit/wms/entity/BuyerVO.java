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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "buyergen")
	@SequenceGenerator(name = "buyergen",sequenceName = "buyerVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="buyerid")
	private Long id;
	private String buyer;
	private String buyershortname;
	private String buyertype;
	private String buyergroupof;
	private String contactperson;
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
	private String cbranch;
	@Column(unique = true)
	private String dupchk;
	private String createdby;
	private String modifiedby;
	private Long orgid;
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
