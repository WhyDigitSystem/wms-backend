package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMovementDTO {

	private Long id;
	private String type;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	
	private String sku;
	private LocalDate grnDate;
	private String core;

	private List<LocationMovementDetailsDTO> locationMovementDetailsDTO;
}
