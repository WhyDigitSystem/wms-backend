package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PutAwayDetailsDTO {
	private String partNo;
	private String batch;
	private String partDesc;
	private String sku;
	private String invoiceNo;
	private int invQty;
	private int recQty;
	private int putAwayQty;
	private int putAwayPiecesQty;
	private String bin;
	private String remarks;
	private String binType;
	private int grnQty;
	private String sSku;
	private String cellType;
	private LocalDate expdate;
	private LocalDate batchDate;

}