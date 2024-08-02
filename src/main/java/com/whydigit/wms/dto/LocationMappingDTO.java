package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationMappingDTO {
	private Long id;
	private String branch;
	private String warehouse;
	private String locationType;
	private String clientType;
	private String rowNo;
	private String customer;
	private String levelNo;
	private String client;
	private String createdBy;
	private Long orgId;
	private boolean cancel;
	private String cancelRemark;
	private boolean active;
	private String branchCode;
}