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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BuyerOrderDTO {

	private Long id;
	private Long orgId;
	private String orderNo;
	private LocalDate orderDate;
	private String refNo;
	private LocalDate refDate;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String buyerShortName;
	private String buyer;
	private String billToShortName;
	private String billToName;
	private String shipToShortName;
	private String shipToName;
	private String createdBy;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	
	private String warehouse;
	
	private List<BuyerOrderDetailsDTO> buyerOrderDetailsDTO;
	
}
