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

	private String qrCode; 
	private String lrNoHawbNo; 
	private String invoiceNo; 
	private LocalDate invoiceDate; 
	private String partNo; 
	private String partDesc;
	private String binType;
	private String sku; 
	private int invQty;
	private int recQty;
	private int damageQty;
	private int subStockQty;
	private int batchQty;
	private int binQty;
	private int pkgs; 
	private int noOfBins;
	private double rate;
	private String weight; 
	private String batchNo;
	private LocalDate batchDt; 
	private double amount;
	private String shipmentNo; 
	private LocalDate expdate;
	private double mrp;
	private String damageRemark;
	
}
