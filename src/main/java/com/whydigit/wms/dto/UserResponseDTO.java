package com.whydigit.wms.dto;

import java.util.Date;

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
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private boolean loginStatus;
	private boolean isActive;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Embedded
	private CreatedUpdatedDate commonDate = new CreatedUpdatedDate();
	private Date accountRemovedDate;
	private String token;
	private String tokenId;
}
