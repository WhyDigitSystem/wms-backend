package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultiplePickDTO {
	
	private String buyerRefNo;
	private LocalDate buyerRefDate;
	private String buyerOrderNo;
	private LocalDate buyerOrderDate;
	private String buyersReference;
	private String invoiceNo;
	private String clientShortName;
	private String clientName;
	private String customerShortName;
	private String customerName;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;

}
