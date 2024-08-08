package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class WarehouseBranchDTO {
	private Long id;
	private String customerBranchCode;
	private boolean active;
	private boolean cancel;
}
