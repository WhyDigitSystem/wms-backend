package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationMappingDetailsDTO {
	private Long id;
	private String warehouse;
	private String rowNo;
	private String levelNo;
	private String palletNo;
	private String binStatus;
	private String binSeq;
	private String multiCore;
	private boolean active;
	

}
