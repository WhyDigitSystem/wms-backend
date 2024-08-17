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
	private String bin;
	private String partNo;
	private String partDesc;
	private String grnNo;
	private String batchNo;
	private LocalDate batchDate;
	private String lotNo;
	private String toBin;
	private int fromQty;
	private int toQty;
	private int remainingQty;
	private String qcFlag;
	private String sku;
	private LocalDate grnDate;
	private String binType;
	private String status;
	private String binClass;
	private String cellType;
	private String clientCode;
	private String core;
	private LocalDate expDate;
	private String pcKey;
	private String ssku;
	private LocalDate stockDate;
	private String toBinClass;
	private String toBinType;
	private String toCellType;

}
