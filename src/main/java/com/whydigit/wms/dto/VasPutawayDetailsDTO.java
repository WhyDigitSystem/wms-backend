package com.whydigit.wms.dto;

import java.time.LocalDate;

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
	private LocalDate grnDate;
	private int invQty;
	private int putAwayQty;
	private String fromBin;
	private String bin;
	private String sku;
	private String remarks;
	private boolean qcFlags;
	private String status ;
	private String binClass;
	private String cellType;
	private String clientCode;
	private String core;
	private LocalDate expDate;
	private String pckey;
	private String ssku;
	private LocalDate stockDate;
	
}
