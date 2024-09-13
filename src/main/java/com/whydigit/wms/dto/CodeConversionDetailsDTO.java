package com.whydigit.wms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeConversionDetailsDTO {
	private Long id;
	private String partNo;
	private String partDesc;
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
	private String cLotNo;
	private String cbin;
	private String cbinType;
	private String remarks;
	private String qcFlag;
	private String binClass;
	private String cellType;
	private String core;
	private LocalDate expDate;

	private String cBinClass;
	private String cCellType;
	private String cCore;
}
