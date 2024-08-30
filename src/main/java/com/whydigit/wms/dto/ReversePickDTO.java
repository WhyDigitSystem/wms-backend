package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReversePickDTO {

	private Long id;
	private String buyerRefNo;
	private LocalDate buyerRefDate;
	private String buyerOrderNo;
	private String buyerOrderDate;
	private String pickRequestDocId;
	private LocalDate pickRequestDocDate;
	private String buyersReference;
	private String invoiceNo;
	private String clientShortName;
	private String clientName;
	private String clientAddress;
	private String customerShortName;
	private String customerName;
	private String customerAddress;
	private String pickOrder;
	private String inTime;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private String status;
	private String boAmendment;
	
	private List<ReversePickDetailsDTO> reversePickDetailsDTO;

}
