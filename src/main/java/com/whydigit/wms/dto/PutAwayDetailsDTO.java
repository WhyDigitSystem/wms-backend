package com.whydigit.wms.dto;

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
	private String invqty;
	private String recqty;
	private String putawayqty;
	private String putawaypiecesqty;
	private String location;
	private String weight;
	private String rate;
	private String amount;
	private String remarks;

}