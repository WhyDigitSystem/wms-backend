package com.whydigit.wms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private Long usersId;
	private String userName;
	private String employeeName;
	private String email;
	private String userType;
	private Long orgId;
	private String customer;
	private String warehouse;
	private String branch;
	private String client;
	private boolean loginStatus;
	private String isActive;
	private Role role;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	private Date accountRemovedDate;
	private String token;
	private String tokenId;
}
