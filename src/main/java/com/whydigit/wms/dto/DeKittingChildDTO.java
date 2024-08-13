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
public class DeKittingChildDTO {
	private Long id;
	private String partNo;
	private String partDesc;
	private String bin;
	private String batchNo;
	private String lotNo;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private int qty;
	private BigDecimal unitRate;
	private BigDecimal amount;
	private String status;
	private boolean qcFlag;
	
	private String binClass;
	private String cellType;
	private String clientCode;
	private String core;
	private LocalDate expDate;
	private String pcKey;
	private String ssku;
	private LocalDate stockDate;
}
