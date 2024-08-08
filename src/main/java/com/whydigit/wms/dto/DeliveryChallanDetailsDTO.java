package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryChallanDetailsDTO {

    private Long id;
	private String pickRequestNo;
	private LocalDate prDate;
	private String partNo;
	private String partDescription;
	private String outBoundBin;
	private int shippedQty;
	private int unitRate;
	private int skuValue;
	private BigDecimal discount;
	private int tax;
	private int gatTax;
	private BigDecimal amount;
	private BigDecimal sgst;
	private BigDecimal cgst;
	private BigDecimal totalGst;
	private BigDecimal billAmount;
	private String remarks;
	private boolean qcFlags;

}
