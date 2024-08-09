package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VasPutawayDetailsDTO {

	private Long id;
	private String partNo;
	private String partDescription;
	private String grnNo;
	private int invQty;
	private int putAwayQty;
	private String fromBin;
	private String bin;
	private String sku;
	private String remarks;
	private boolean qcFlags;

	
}
