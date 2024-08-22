package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDTO {


	private Long id;
	private String buyerRefNo;
	private LocalDate buyerRefDate;
	private String buyerOrderNo;
	private String buyersReference;
	private String invoiceNo;
	private String clientShortName;
	private String clientName;
	private String clientAddress;
	private String customerShortName;
	private String customerName;
	private String customerAddress;
	private String pickOrder;
	private String outTime;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private String status;
	
	List<PickRequestDetailsDTO> pickRequestDetailsDTO;
}