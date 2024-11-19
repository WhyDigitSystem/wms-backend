package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	
	private Long id;
	private Long createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String warehouse;
	private String finYear;
	private String name;
	private String email;
	private String issueDesc;	
	private String ticketRemarks;

}
