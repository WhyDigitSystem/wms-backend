package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReturnDTO {
	private Long id;
	private String transactionType;
	private String entryNo;
	private LocalDate entryDate;
	private LocalDate prDate;
	private String BONo;
	private LocalDate BODate;
	private String PRNo;
	private String buyerName;
	private String buyerType;
	private String supplier;
	private String driverName;
	private String carrier;
	private String modeOfShipment;
	private String vehicleType;
	private String vehicleNo;
	private String contact;
	private String securityPersonName;
	private LocalTime timeIn;
	private LocalTime out;
	private String briefDescOfGoods;
	private int totalReturnQty;

	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	
	private List<SalesReturnDTO> salesReturnDTO;
}
