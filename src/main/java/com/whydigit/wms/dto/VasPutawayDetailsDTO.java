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
	private String partDesc;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private String toBin;
	private String toBinType;
	private String remarks;
	private String toBinClass;
	private String toCellType;
	private String toCore;
	private String fromBin;
	private String fromBinType;
	private String fromBinClass;
	private String fromCellType;
	private String fromCore;
	private LocalDate expDate;
	private LocalDate stockDate;
	private int invQty;
	private int putAwayQty;
	private String qcFlag;
	
}
