package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDTO {
	private Long id;
	private String screenName;
	private LocalDate docDate;
	private Long orgId;
	private String customer;
	private String client;
	private String finYear;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String createdBy;
	private boolean active;
	private boolean cancel;
	private String cancelRemarks;
	private boolean freeze;
	private String refNo;
	private LocalDate refDate;
	
	private List<KittingDetails1DTO> kittingDetails1DTO;
	private List<KittingDetails2DTO> kittingDetails2DTO;
	
}
