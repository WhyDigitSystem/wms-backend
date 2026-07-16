package com.whydigit.wms.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

	private Long id;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;
	private String finYear;
	private String ticketRemarks;
	@Email(message = "Invalid Email")
	private String email;
	private String companyName;	
	private String ticketStatus;
	private String subject;
	private String description;
	private String userName;

	// private byte[] screenShot;



}
