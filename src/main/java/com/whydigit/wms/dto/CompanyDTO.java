package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
	
	private Long id;
	private String companyCode;
	private String companyName;
	private String country;
	private String currency;
	private String mainCurrency;
	private String address;
	private String zip;
	private String city;
	private String state;
	private String phone;
	private String email;
	private String webSite;
	private String note;
	//private String userId;
	private String employeeName;
	private String employeeCode;
	private String password;
	private String createdBy;
	private String updatedBy;
	private Boolean cancel;
	private Boolean active;

}
