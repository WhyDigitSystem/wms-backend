package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
	private Long id;
	private String groupName;
	private boolean active;
	private Long orgId;
	private String createdBy;
	private boolean cancel;
}
