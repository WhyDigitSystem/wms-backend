package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsDTO {

	private Long id;
	private String partCode;
	private String partDesc;
	private String sku;
	private String core;
	private String location;
	private String batchNo;
	private String lotNo;
	private int orderQty;
	private int avlQty;
	private int pickQty;
	private int runningQty;
	private String pickQtyPerLocation;
	private String remainingQty;
	private String weight;
	private String rate;
	private String tax;
	private String amount;
	private String remarks;

	private String binClass;
	private String cellType;
	private String clientCode;
	private LocalDate expDate;
	private String pcKey;
	private String ssku;
	private LocalDate stockDate;
}