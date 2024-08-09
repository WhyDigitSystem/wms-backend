package com.whydigit.wms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerOrderDetailsDTO {
	private Long id;
	private String partNo;
	private String partDesc;
	private Long qty;
	private String batchNo;
	private Long availQty;
	private String sku;
	private String remarks;
	private boolean qcflag;
}
