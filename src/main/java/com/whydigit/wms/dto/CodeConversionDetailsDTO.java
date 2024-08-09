package com.whydigit.wms.dto;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.whydigit.wms.entity.CodeConversionVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeConversionDetailsDTO {
    private Long id;
	private String partNo;
	private String partDescription;
	private String grnNo;
	private String sku;
	private String binType;
	private String batchNo;
	private String lotNo;
	private String pallet;
	private int qty;
	private int actualQty;
	private BigDecimal rate;
	private BigDecimal convertQty;
	private BigDecimal cRate;
	private String cPartNo;
	private String cPartDesc;
	private String cSku;
	private String cBatchNo;
	private String cLotNo;
	private String cbin;
	private String remarks;
	private boolean qcFlags;
	
	
}
