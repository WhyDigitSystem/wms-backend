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
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private LocalDate expDate;
	private String binType;
	private String bin;
	private String binClass;
	private String cellType;
	private String core;
	private int avlQty;
	private int actualQty;
	private String qcFlag;
}
