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
	private String partdescription; 
	private String sku; 
	private String invqty; 
	private String recqty; 
	private String shortqty; 
	private String damageqty; 
	private String substockqty; 
	private String pallteqty; 
	private String noofpallet; 
	private String pkgs; 
	private String weight; 
	private String batchno; 
	private LocalDate batchdt; 
	private String qcflag; 
	private String shipmentno; 
	private String sqty; 
}
