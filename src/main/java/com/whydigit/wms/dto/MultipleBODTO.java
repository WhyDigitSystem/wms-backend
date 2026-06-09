package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleBODTO {
	
	private Long orgId;
	private String orderNo;
	private LocalDate orderDate;
	private String refNo;
	private LocalDate refDate;
	private String buyerName;
	private String billToName;
	private String shipToName;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String createdBy;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse; 
}
