package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CycleCountDetailsDTO {

	private Long id;
	private Long partNo;
	private String paretDescription;
	private Long grnNo;
	private String sku;
	private String binType;
	private int batchNo;
	private LocalDate batchDate;
	private String bin;
	private int qty;
	private int actualQty;
	private boolean qQcflag;
}
