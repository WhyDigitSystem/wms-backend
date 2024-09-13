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
	private String binType;
	private String partDescription;
	private String partNo;
	private String sku;
	private String bin;
	private String batchNo;
	private LocalDate batchDate;
	private String grnNo;
	private LocalDate grnDate;
	private int avlQty;
	private int picQty;
	private int remaningQty;
	private String qcflag;
	private String binClass;
	private String cellType;
	private String core;
	private LocalDate expDate;

	
	
}
