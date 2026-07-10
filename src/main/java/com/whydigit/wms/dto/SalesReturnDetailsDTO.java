package com.whydigit.wms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

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
	private String qcFlag;
	private String binType;
	private String cellType;
	private String ssku;
	private String status;
	private String core;
	private String bin;
	private String binClass;
}
