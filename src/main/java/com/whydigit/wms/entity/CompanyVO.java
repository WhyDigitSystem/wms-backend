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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "companygen")
	@SequenceGenerator(name = "companygen",sequenceName = "companyVO",initialValue = 1000000001,allocationSize = 1)
	@Column(name="companyid")
	private Long id;
	
	@Column(unique = true)
	private String companycode;

	@Column(unique = true)
	private String company;
	
	@Column(name="country")
    private String country;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="address")
	private String address;
	
	@Column(name="zipcode")
	private String zipcode;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="email")
	private String email;
	
	@Column(name="website")
	private String website;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="userid")
    private String userid;
	
	@Column(name="active")
    private boolean active;
	
	@Column(unique = true)
	private String dupchk;
	
	@Column(name="employeename")
	private String employeeName;
	
	@Column(name="employeecode")
	private String employeecode;
	
	@Column(name="password")
	private String password;
	
	@Column(name="createdby")
	private String createdby;
	
	@Column(name="modifiedby")
	private String updatedby;
	
	@Column(name="cancel")
	private boolean cancel;
	
    @Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
}



