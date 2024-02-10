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
	private long companyid;
	@Column(unique = true)
	private String name;
	@Column(unique = true)
	private String companycode;
	private String address;
    private String zip;
	private String town;
	private String country;
    private String state;
	private String currency;
	private String phone;
    private String email;
	private String website;
	private String note;
    private String image;
	private String directorname;
	private String capital;
    private String entitytype;
	private String TIN;
	private String PAN;
    private String companyobject;
    private String userid;
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}



