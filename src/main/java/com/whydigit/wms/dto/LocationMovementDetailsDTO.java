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
	private String partDescripition;
	private String GRNNo;
	private String batchNo;
	private LocalDate batchDate;
	private String lotNo;
	private String toBin;
	private int fromQty;
	private int toQty;
	private int remainingQty;
	private boolean qcFlag;
	private String sku;
	private LocalDate grnDate;
	private String binType;
	private String core;
	private String binClass;
	private LocalDate expDate;
	private String status;
	
}
