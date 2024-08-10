package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;

import com.whydigit.wms.entity.SalesReturnVO;

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
	private String partDescripition;
	private String unit;
	private int pickQty;
	private int retQty;
	private int damageQty;
	private String batchNo;
	private LocalDate batchDate;
	private LocalDate expDate;
	private String noOfPallet;
	private int palletQty;
	private BigDecimal weight;
	private BigDecimal rate;
	private BigDecimal amount;
	private BigDecimal insAmt;
	private String remarks;
	private boolean qcFlag;
	private String status;
	
}
