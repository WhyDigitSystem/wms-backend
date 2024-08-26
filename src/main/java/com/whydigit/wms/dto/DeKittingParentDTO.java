package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeKittingParentDTO {
	
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
	private int avlQty;
	private int qty;
	private LocalDate expDate;
}
