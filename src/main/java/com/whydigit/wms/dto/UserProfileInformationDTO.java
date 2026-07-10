package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileInformationDTO {
	private Long id;

	private String fullName;

	private String email;

	private Long phoneNumber;

	private String companyName;
	private String address;

	private String bioInformation;

	private String createdBy;
	private Long userId;
}
