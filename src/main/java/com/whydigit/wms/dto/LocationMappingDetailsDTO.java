package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationMappingDetailsDTO {
	private Long id;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String binType;
	private String clientType;
	private String rowNo;
	private String levelNo;
	private String client;
	private boolean cancel;
	private String bin;
	private String lstatus;
	private String cellCategory;
	private String core;
	private boolean active;
	

}
