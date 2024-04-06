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
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVO {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companygen")
	@SequenceGenerator(name = "companygen", sequenceName = "companyVO", initialValue = 1000000001, allocationSize = 1)
	@Column(name = "companyid")
	private Long id;

	@Column(unique = true, length = 30)
	private String companycode;
	@Column(unique = true, length = 30)
	private String companyname;
	@Column(name = "country", length = 30)
	private String country;
	@Column(name = "currency", length = 30)
	private String currency;
	@Column(name = "maincurrency", length = 30)
	private String mainCurrency;
	@Column(name = "address", length = 30)
	private String address;
	@Column(name = "zipcode", length = 30)
	private String zip;
	@Column(name = "city", length = 30)
	private String city;
	@Column(name = "state", length = 30)
	private String state;
	@Column(name = "phone", length = 30)
	private String phone;
	@Column(name = "email", length = 30)
	private String email;
	@Column(name = "website", length = 30)
	private String webSite;
	@Column(name = "notes", length = 30)
	private String note;
	@Column(name = "userid", length = 30)
	private String userid;
	@Column(name = "active")
	private boolean active;
	@Column(unique = true)
	private String dupchk;
	@Column(name = "employeename", length = 30)
	private String employeeName;
	@Column(name = "employeecode", length = 30)
	private String employeecode;
	@Column(name = "password", length = 255)
	private String password;
	@Column(name = "createdby", length = 30)
	private String createdby;
	@Column(name = "modifiedby", length = 30)
	private String updatedby;
	@Column(name = "cancel")
	private boolean cancel;

	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}
