package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationMappingDetailsDTO {
	private Long id;
	private String rowNo;
	private String levelNo;
	private String bin;
	private String binStatus;
	private String binCategory;
	private String binSeq;
	private String core;
	private boolean active;
	

}
