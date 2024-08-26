package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

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
	private String bin;
	private String batchNo;
	private String lotNo;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private int avlQty;
	private int qty;
	private BigDecimal unitRate;
	private BigDecimal amount;
	private String status;
	private String qcFlag;
	private String binClass;
	private String cellType;
	private String clientCode;
	private String core;
	private LocalDate expDate;
	private String pcKey;
	private String ssku;
	private LocalDate stockDate;
}
