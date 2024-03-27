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
public class GrnDetailsDTO {

	private String qrcode; 
	private String lrnohawbno; 
	private String invoiceno; 
	private LocalDate invoicedate; 
	private String partno; 
	private String partdesc;
	private String locationtype;
	private String sku; 
	private int invqty;
	private int recqty;
	private int shortqty;
	private int damageqty;
	private int substockqty;
	private int batchqty;
	private int palletqty;
	private String pkgs; 
	private String rate;
	private String weight; 
	private String batchno; 
	private LocalDate batchdt; 
	private String qcflag; 
	private String amount;
	private String shipmentno; 
	private String warehouse;
	private int sqty; 
}
