package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeConversionDetailsDTO {
	private Long id;
	private String partNo;
	private String partDescription;
	private String grnNo;
	private String status;
	private LocalDate grnDate;
	private String sku;
	private String binType;
	private String batchNo;
	private LocalDate batchDate;
	private String lotNo;
	private String bin;
	private int qty;
	private int actualQty;
	private BigDecimal rate;
	private int convertQty;
	private BigDecimal cRate;
	private String cPartNo;
	private String cPartDesc;
	private String cSku;
	private String cBatchNo;
	private LocalDate cBatchDate;
	private String cLotNo;
	private String cbin;
	private String cbinType;
	private String remarks;
	private String qcFlags;

	private String binClass;
	private String cellType;
	private String clientCode;
	private String core;
	private LocalDate expDate;
	private String pckey;
	private String ssku;
	private LocalDate stockDate;

}
