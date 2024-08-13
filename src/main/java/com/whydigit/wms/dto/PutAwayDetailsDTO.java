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
	private String partDescripition;
	private String sku;
	private int invQty;
	private int recQty;
	private int sSqty;
	private int putAwayQty;
	private int putAwayPiecesQty;
	private String bin;
	private String weight;
	private String rate;
	private double amount;
	private String remarks;
	private String binType;
	private int shortQty;
	private int sQty;
	private String sSku;
	private String binclass;
	private String cellType;
	private LocalDate batchDate;
	private String status;
	private String qcFlag;

}