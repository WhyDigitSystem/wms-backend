package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDetails1DTO {

	private Long id;

	private String pallet;

	private String partNo;

	private String partDescription;

	private String batchNo;

	private String lotNo;

	private String grnNo;

	private LocalDate grnDate;

	private String sku;

	private int avlQty;

	private int qty;

	private int unitRate;

	private int amount;
	
	private boolean qcflag;
}
