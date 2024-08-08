package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationTypeDTO {
	private Long id;
	private String binType;
	private String createdBy;
	private Long orgId;
	private boolean cancel;
	private boolean active;

}
