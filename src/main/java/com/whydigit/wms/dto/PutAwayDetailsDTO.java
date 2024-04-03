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
	private String partno;
	private String batch;
	private String partdescripition;
	private String sku;
	private int invqty;
	private int recqty;
	private int putawayqty;
	private int putawaypiecesqty;
	private String location;
	private String weight;
	private String rate;
	private String amount;
	private String remarks;
	private String locationtype;
	private String ssqty;
	private int shortqty;
	private String locationclass;
	private LocalDate batchdate;
	private int sqty;
	private String ssku;
	private String celltype;
	private String status;
}