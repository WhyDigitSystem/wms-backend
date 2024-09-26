package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrnDTO {
	
	private Long id;
	private String entryNo;
	private LocalDate entryDate;
	private LocalDate grnDate;
	private String gatePassId;
	private LocalDate gatePassDate;
	private String customerPo;
	private String supplierShortName;
	private String supplier;
	private String carrier;
	private String lotNo;
	private String modeOfShipment;
	private String createdBy;
	private Long orgId;
	private String branchCode;
	private String branch;
	private String client;
	private String customer;
	private String billOfEnrtyNo;
	private String containerNo;
	private String fifoFlag;
	private String warehouse;
	private boolean vas;
	private String vehicleNo;
	private String vehicleDetails;
	private String finYear;
	private String sealNo;
	private String vesselNo;
	private String hsnNo;
	private String securityName;
	private String vehicleType;
	private String vesselDetails;
	private String lrNo;
	private String driverName;
	private String contact;
	private LocalDate lrDate;
	private String goodsDescripition;
	private String destinationFrom;
	private String destinationTo;
	private String noOfBins;
	private String invoiceNo;
	private String remarks;
	List<GrnDetailsDTO> grnDetailsDTO;
	

}
