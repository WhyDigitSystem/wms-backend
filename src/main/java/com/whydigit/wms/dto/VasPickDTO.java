package com.whydigit.wms.dto;

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
	private char stateStatus;
	private String stockState;
	private String status;

	private List<VasPickDetailsDTO> vasPickDetailsDTO;

}
