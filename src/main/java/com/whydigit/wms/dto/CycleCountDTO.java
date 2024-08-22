package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CycleCountDTO {
private Long id;
	
	private LocalDate docDate;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private String cancelRemarks;
	private String freeze;
	private String cycleCountNo;
	private LocalDate cycleCountDate;
	
	private List<CycleCountDetailsDTO> cycleCountDetailsDTO;
}
