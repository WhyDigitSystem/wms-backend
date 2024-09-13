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

	private String fromBin;
	private String fromBinClass;
	private String fromBinType;
	private String fromCellType;
	private String fromCore;
	private String partNo;
	private String partDesc;
	private String sku;
	private String grnNo;
	private LocalDate grnDate;
	private String batch;
	private LocalDate batchDate;
	private String toBin;
	private String toBinClass;
	private String toBinType;
	private String toCellType;
	private String toCore;
	private int fromQty;
	private int toQty;
	private LocalDate expDate;
	private String qcFlag;
}
