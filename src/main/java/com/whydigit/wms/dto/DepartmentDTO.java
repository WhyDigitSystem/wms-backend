package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
	private Long id;
	private String departmentName;
	private String code;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private String updatedBy;
	private boolean cancel;
}
