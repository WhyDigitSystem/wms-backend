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
public class PutAwayDTO {

	
	private Long id;
	private String grnNo;
	private LocalDate grnDate;
	private String entryNo;
	private String core;
	private String supplierShortName;
	private String supplier;
	private String modeOfShipment;
	private String carrier;
	private String binType;
	private String status;
	private String lotNo;
	private String enteredPerson;

	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	


	List<PutAwayDetailsDTO> putAwayDetailsDTO;
	
}
