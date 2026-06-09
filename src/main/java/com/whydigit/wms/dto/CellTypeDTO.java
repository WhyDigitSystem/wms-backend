package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellTypeDTO {

	private Long id;
	private String celltype;
	private boolean active;
	private String createdBy;
	private Long orgId;
	private boolean cancel;
}
