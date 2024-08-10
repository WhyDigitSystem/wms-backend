package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatePassInDTO {
	
	private Long id;
	private String transactionType;
	private String entryNo;//
	private Long orgId;
	private LocalDate docdate = LocalDate.now();
	private LocalDate dat=LocalDate.now();
	private String supplier;
	private String supplierShortName;
	private String modeOfShipment;
	private String carrier;
	private String carrierTransport;
	private String vehicleType;
	private String vehicleNo;
	private String driverName;
	private String contact;
	private String goodsDescription;
	private String securityName;
	private String lotNo;
	private String createdBy;
	private String company;
	private boolean cancel;
	private String cancelRemark;
	private boolean active;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String finYear;
	private String cancelRemarks;
	private boolean freeze;
	
	private List<GatePassInDetailsDTO> gatePassInDetailsDTO;

}
