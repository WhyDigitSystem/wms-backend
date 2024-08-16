package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDTO {

	private Long id;
	private String transactionType;
	private String buyerRefNo;
	private LocalDate buyerRefDate;
	private String shipmentMethod;
	private String buyerOrderNo;
	private String buyersReference;
	private String invoiceNo;
	private String clientShortName;
	private String clientAddress;
	private String dispatch;
	private String customerName;
	private String customerAddress;
	private String dueDays;
	private String noOfBoxes;
	private String pickOrder;
	private String outTime;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String status;
	private String createdBy;
	private String updatedBy;
	private String freeze;
	

	List<PickRequestDetailsDTO> pickRequestDetailsDTO;
}