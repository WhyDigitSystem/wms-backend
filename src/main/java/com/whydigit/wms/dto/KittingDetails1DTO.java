package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//child table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDetails1DTO {

	private Long id;
	private String partNo;
	private String partDesc;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private String binType;
	private String bin;
	private String binClass;
	private String cellType;
	private String core;
	private int avlQty;
	private int qty;
	private LocalDate expDate;
}
