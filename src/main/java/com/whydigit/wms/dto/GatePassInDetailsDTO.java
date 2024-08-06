package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GatePassInDetailsDTO {
	private Long id;

	private String sNo;
	private String irNoHaw;
	private String invoiceNo;
	private LocalDate invoiceDate;
	private String partNo;
	private String partCode;
	private String partDescription;
	private String batchNo;
	private String unit;
	private String sku;
	private int invQty;
	private int recQty;
	private int shortQty;
	private int damageQty;
	private int grnQty;
	private String subUnit;
	private int subStockShortQty;
	private int grnPiecesQty;
	private String weight;
	private String rate;
	private String rowNo;
	private String amount;
	private String remarks;
}
