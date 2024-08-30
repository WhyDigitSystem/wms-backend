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
public class SalesReturnDetailsDTO {
	private Long id;
	private String LRNo;
	private String invoiceNo;
	private String partNo;
	private String partDesc;
	private String sku;
	private int pickQty;
	private int retQty;
	private int damageQty;
	private String batchNo;
	private LocalDate batchDate;
	private LocalDate expDate;
	private String noOfBin;
	private int binQty;
	private String remarks;
	private boolean qcFlag;
	
}
