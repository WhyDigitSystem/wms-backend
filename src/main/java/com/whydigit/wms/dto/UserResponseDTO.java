package com.whydigit.wms.dto;

import java.util.Date;

import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	private Long usersid;
	private String username;
	private String employeename;
	private String email;
	private String usertype;
	private Long orgid;
	private String customer;
	private String warehouse;
	private String branch;
	private String client;
	private boolean loginstatus;
	private String active;
	private Role role;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	private Date accountremoveddate;
	private String token;
	private String tokenid;
}
