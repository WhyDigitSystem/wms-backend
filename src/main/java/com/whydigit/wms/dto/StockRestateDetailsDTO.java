package com.whydigit.wms.dto;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRestateDetailsDTO {

    private Long id;
	private String bin;
	private String binClass;
	private String binType;
	private String cellType;
	private String partNo;
	private String partDesc;
	private String grnNo;
	private LocalDate grnDate;
	private String sku;
	private String toBin;
	private String toBinClass;
	private String toBinType;
	private int fromQty;
	private int toQty;
	private int remainQty;
	private String noted;
	private boolean qcFlags;
	private LocalDate expDate;
	private String status;
}
