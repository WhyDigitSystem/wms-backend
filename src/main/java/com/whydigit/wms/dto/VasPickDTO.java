package com.whydigit.wms.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VasPickDTO {
	private Long id;
	private String picBin;
	private String screenName;
	private String screenCode;
	private LocalDate docDate = LocalDate.now();
	private String docId;
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
	
	private List<VasPickDetailsDTO> vasPickDetailsDTO;

}
