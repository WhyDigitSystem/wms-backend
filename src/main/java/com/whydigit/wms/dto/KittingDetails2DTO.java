package com.whydigit.wms.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KittingDetails2DTO {
	private Long id;
	private String pPartNo;
	private String pPartDescription;
	private String PBatchNo;
	private String pLotNo;
	private String pGrnNo;
	private LocalDate pGrnDate;
	private boolean qQcflag;	
	private int pQty;
	private LocalDate pExpDate;
}
