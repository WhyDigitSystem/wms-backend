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
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyVO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String companycode;
	@Column(unique = true)
	private String companyname;
    private String country;
	private String currency;
	private String address;
	private String zipcode;
	private String city;
	private String state;
	private String phone;
	private String email;
	private String website;
	private String notes;
    private String userid;
    private boolean active;
	@Column(unique = true)
	private String dupchk;
	private String employeeName;
	private String employeecode;
	private String password;
	private String createdby;
	private String updatedby;
	private boolean cancel;
	
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}



