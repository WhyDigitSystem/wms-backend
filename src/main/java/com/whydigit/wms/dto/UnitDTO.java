package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDTO {
	private Long id;

	private String unitName;
	private String unitType;
	private boolean active;
	private String createdBy;
	private Long orgId;
	private boolean cancel;
	private String uom;
}
   