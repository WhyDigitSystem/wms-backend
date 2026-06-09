package com.whydigit.wms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseLocationDTO {
	
	private Long id;
	private String branch;
	private String branchCode;
	private String warehouse;
	private String binType;
	private String rowNo;
	private String level;
	private String cellFrom;
	private String cellTo;
	private String createdBy;
	private Long orgId;

	private List<WarehouseLocationDetailsDTO> warehouseLocationDetailsDTO;

}
