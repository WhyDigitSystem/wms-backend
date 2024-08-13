package com.whydigit.wms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

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
	private int invQty;
	private int recQty;
	private int putAwayQty;
	private int putAwayPiecesQty;
	private String bin;
	private double weight;
	private double rate;
	private double amount;
	private String remarks;
	private String binType;
	private int sQty;
	private String sSku;
	private String cellType;
	private LocalDate batchDate;

}