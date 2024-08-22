package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeKittingDTO {

	private Long id;
	private String transactionType;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private String freeze;
	private boolean active = true;
	private LocalDate grnDate = LocalDate.now();
	
	private List<DeKittingParentDTO> deKittingParentDTO;
	
	private List<DeKittingChildDTO> deKittingChildDTO;
}