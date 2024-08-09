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

	private String partDescrrption;

	private String partNo;

	private String sku;

	private String bin;

	private String batchNo;

	private String lotNo;

	private String grnNo;

	private int avlQty;

	private int picQty;

	private int remaningQty;

	private LocalDate manufactureDate;

	private boolean qcflag;

}
