package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDetails2DTO {
	private Long id;
	private String partNo;
	private String partDesc;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batchNo;
	private LocalDate batchDate;
	private String bin;
	private String binType;
	private String binClass;
	private String cellType;
	private String core;
	private String lotNo;
	private boolean qcflag;	
	private int qty;
	private LocalDate expDate;
}
