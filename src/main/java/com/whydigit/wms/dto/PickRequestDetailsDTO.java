package com.whydigit.wms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class PickRequestDetailsDTO {


	private String partNo;
	private String partDesc;
	private String sku;
	private String core;
	private String bin;
	private String batchNo;
	private LocalDate batchDate;
	private String lotNo;
	private int orderQty;
	private int avlQty;
	private int pickQty;
	private int runningQty;
	private int pickQtyPerBin;
	private int remainingQty;
	private String remarks;
	private String binClass;
	private String binType;
	private String cellType;
	private String status;
	private LocalDate stockDate;
	private LocalDate expDate;
}