package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VasPickDetailsDTO {
	private Long id;

	private String partCode;

	private String partDescription;

	private String partNo;

	private String sku;

	private String bin;

	private String batchNo;

	private String lotNo;

	private String grnNo;
	
	private String grnDate;


	private int avlQty;

	private int picQty;

	private int remaningQty;

	private LocalDate manufactureDate;

	private String qcflag;
	
	private String binClass;
	private String cellType;
	private String clientCode;	
	private String core;
	private LocalDate expDate;
	private String pckey;
	private String ssku;
	private LocalDate stockDate;

}
