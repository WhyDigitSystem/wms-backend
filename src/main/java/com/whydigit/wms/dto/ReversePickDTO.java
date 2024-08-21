package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReversePickDTO {

	private Long id;
	private String pickRequestId;
	private String dispatch;
	private String buyerOrderNo;
	private String buyerOrderRefNo;
	private String shipmentMethod;
	private String refNo;
	private LocalDate buyerOrderRefDate;
	private int noOfBoxes;
	private int dueDays;
	private String clientName;
	private String customerName;
	private String outTime;
	private String clientAddress;
	private String customerAddress;
	private String status;
	private String boAmmount;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private String cancelRemarks;

	private List<ReversePickDetailsDTO> reversePickDetailsDTO;

}
