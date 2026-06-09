package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMovementDetailsDTO {
	private Long id;
	private String partNo;
	private String partDesc;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private String fromBin;
	private String fromBinType;
	private String fromBinClass;
	private String fromCellType;
	private String fromCore;
	private String toBin;
	private String toBinClass;
	private String toBinType;
	private String toCellType;
	private String toCore;
	private int fromQty;
	private int toQty;
	private int remainingQty;
	private String qcFlag;
	private String status;
	private LocalDate expDate;

}
