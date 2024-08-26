package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReversePickDetailsDTO {

	private String partNo;
	private String partDesc;
	private String sku;
	private String core;
	private String bin;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private int orderQty;
	private int pickQty;
	private int revisedQty;
	private String remarks;
	private String binClass;
	private String binType;
	private String cellType;
	private LocalDate expDate;
	private String qcFlag;

}
