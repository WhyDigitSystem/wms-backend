package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CycleCountDetailsDTO {

	private Long id;
	private String partNo;
	private String partDescription;
	private String grnNo;
	private String sku;
	private String binType;
	private String batchNo;
	private LocalDate batchDate;
	private String bin;
	private int avlQty;
	private int actualQty;
	private LocalDate grnDate;
	private LocalDate expDate;
	private String binClass;
	private String cellType;
	private String core;
	private String lotNo;
	private String qcFlag;
	private String status;
}
