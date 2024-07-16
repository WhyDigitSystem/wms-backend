package com.whydigit.wms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDTO {

	private Long id;

	private String warehouse;
	private String branchCode;
	private String branch;
	private Long orgId;
	private boolean active;
	private String createdBy;
	private String updatedBy;
	private boolean cancel;

	private List<WarehouseClientDTO> warehouseClientDTO;
}
